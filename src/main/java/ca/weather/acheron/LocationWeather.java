/***********************************************************************
 * REDapp - LocationWeather.java
 * Copyright (C) 2015-2022 The REDapp Development Team
 * Homepage: http://redapp.org
 * 
 * REDapp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * REDapp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with REDapp. If not see <http://www.gnu.org/licenses/>. 
 **********************************************************************/

package ca.weather.acheron;

import static ca.weather.forecast.Model.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import ca.hss.general.WebDownloader;
import ca.hss.times.TimeZoneInfo;
import ca.weather.forecast.Model;
import ca.weather.forecast.Time;
import ca.weather.internal.Forecast;
import ca.weather.internal.XMLFile;
import ca.weather.internal.Forecast.ModelDoesNotExistException;

/**
 * Weather information for a specific location.
 * 
 * @author Travis
 *
 */
public class LocationWeather {
	private String location;
	private List<Day> day_data = null;
	private List<Hour> hour_data = null;
	private List<List<Hour>> memberHourData = null;
	private List<List<Day>> memberDayData = null;
	private List<Integer> members = null;
	private List<Double> daily_wdirs = new ArrayList<Double>();
	private Model model = Model.CUSTOM;
	private Calendar date;
	private Time time = Time.MIDNIGHT;
	private int percentile = 50;

	private XMLFile temp_file;
	private XMLFile rh_file;
	private XMLFile apcp_file;
	private XMLFile wind_file;
	private XMLFile wdir_file;

	/**
	 *
	 * @param location
	 *            must be in the format CITY PV CA where PV is the two letter
	 *            provincial code with a space in between each item.
	 */
	LocationWeather(String location) {
		this.location = location.replace(' ', '_');
		memberHourData = new ArrayList<List<Hour>>(43);
		memberDayData = new ArrayList<List<Day>>(43);
		for (int i = 0; i < 43; i++) {
			memberHourData.add(new ArrayList<Hour>());
			memberDayData.add(new ArrayList<Day>());
		}
	}

	void setPercentile(int value) {
		percentile = value;
	}

    /**
     * Restrict a double value to a certain range.
     * @param value The value to constrain.
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @return min if value < min, max if max < value, otherwise value.
     */
    private static double constrainToRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

	/**
	 * Get the calculated 50th percentile daily weather values.
	 *
	 * @return An unmodifiable list of daily weather data.
	 */
	public List<Day> getDayData() {
		return Collections.unmodifiableList(day_data);
	}

	/**
	 * Get the calculated 50th percentile hourly weather values.
	 *
	 * @return An unmodifiable list of hourly weather data.
	 */
	public List<Hour> getHourData() {
		return Collections.unmodifiableList(hour_data);
	}

	/**
	 * Get the model used in the calculation.
	 *
	 * @return
	 */
	public Model getModel() {
		return model;
	}

	public int getDayCount() {
		return day_data.size();
	}

	/**
	 * Get the members used in the calculation.
	 *
	 * @return
	 */
	public List<Integer> getMembers() {
		return Collections.unmodifiableList(members);
	}

	/**
	 * Get the location (with underscores instead of spaces).
	 *
	 * @return
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Get a human readable name for this location.
	 */
	public String toString() {
		return location.replace('_', ' ');
	}

	private double getPercentileValueFromList(List<Double> value_list,
			double index) {
		if (value_list.size() < 1)
			return -1;
		int index_high = (int) Math.ceil(index);
		int index_low = (int) Math.floor(index);
		double i = index - index_low;
		return ((1 - i) * value_list.get(index_low).doubleValue())
				+ (i * value_list.get(index_high).doubleValue());
	}

	@SuppressWarnings("unused")
	private double getUpperConfidenceIntervalValueFromList(
			List<Double> value_list) {
		double mean = 0;
		double stddev = 0;
		for (double d : value_list)
			mean = mean + d;
		mean = mean / ((double) value_list.size());
		if (value_list.size() >= 2)
			for (double d : value_list)
				stddev = stddev + Math.pow(d - mean, 2);
		stddev = Math.sqrt(stddev / ((double) value_list.size()));
		return mean - 1.96 * stddev / Math.sqrt((double) value_list.size());
	}

	@SuppressWarnings("unused")
	private double getUpper5thConfidenceIntervalValueFromList(
			List<Double> value_list) {
		double mean = 0;
		double stddev = 0;
		for (double d : value_list)
			mean = mean + d;
		mean = mean / ((double) value_list.size());
		if (value_list.size() >= 2)
			for (double d : value_list)
				stddev = stddev + Math.pow(d - mean, 2);
		stddev = Math.sqrt(stddev / ((double) value_list.size()));
		return mean + 0.06275 * stddev / Math.sqrt((double) value_list.size());
	}

	@SuppressWarnings("unused")
	private double getLower5thConfidenceIntervalValueFromList(
			List<Double> value_list) {
		double mean = 0;
		double stddev = 0;
		for (double d : value_list)
			mean = mean + d;
		mean = mean / ((double) value_list.size());
		if (value_list.size() >= 2)
			for (double d : value_list)
				stddev = stddev + Math.pow(d - mean, 2);
		stddev = Math.sqrt(stddev / ((double) value_list.size()));
		return mean - 0.06275 * stddev / Math.sqrt((double) value_list.size());
	}

	private double getPredominantAverageWindDir(List<Double> value_list) {
		if (value_list.size() < 1)
			return -1;
		List<Double> v0 = new ArrayList<Double>();
		List<Double> v45 = new ArrayList<Double>();
		List<Double> v90 = new ArrayList<Double>();
		List<Double> v135 = new ArrayList<Double>();
		List<Double> v180 = new ArrayList<Double>();
		List<Double> v225 = new ArrayList<Double>();
		List<Double> v270 = new ArrayList<Double>();
		List<Double> v315 = new ArrayList<Double>();

		for (double dir : value_list) {
			if (dir <= 22.5)
				v0.add(dir);
			else if (dir <= (45.0 + 22.5))
				v45.add(dir);
			else if (dir <= (90.0 + 22.5))
				v90.add(dir);
			else if (dir <= (135.0 + 22.5))
				v135.add(dir);
			else if (dir <= (180.0 + 22.5))
				v180.add(dir);
			else if (dir <= (225.0 + 22.5))
				v225.add(dir);
			else if (dir <= (270.0 + 22.5))
				v270.add(dir);
			else if (dir <= (315.0 + 22.5))
				v315.add(dir);
			else
				v0.add(dir - 360.0);
		}
		double m = 0;
		List<List<Double>> v_set = new ArrayList<List<Double>>();
		v_set.add(v0);
		v_set.add(v45);
		v_set.add(v90);
		v_set.add(v135);
		v_set.add(v180);
		v_set.add(v225);
		v_set.add(v270);
		v_set.add(v315);
		List<Double> m_list = null;
		for (List<Double> l : v_set) {
			if (l.size() > m) {
				m = l.size();
				m_list = l;
			}
		}
		double avg = 0;
		for (double d : m_list) {
			avg = avg + d;
		}
		avg = avg / ((double) m_list.size());
		if (avg < 0.0)
			avg = avg + 360.0;
		return avg;
	}

	private double normalizeAngle(double angle) {
		if (angle >= 0.0) {
			if (angle < 360.0)
				return angle;
			return angle % 360.0;
		}
		return (angle % 360.0) + 360.0;
	}

	@SuppressWarnings("unused")
	private double averageAngle(double start_ws, double end_ws,
			double start_angle, double end_angle, int lower_bound,
			int upper_bound, int iter) {
		if (iter == lower_bound)
			return start_angle;
		if (iter == upper_bound)
			return end_angle;
		boolean bb1 = (start_ws < 0.0001) && (start_angle < 0.0001);
		boolean bb2 = (end_ws < 0.0001) && (end_angle < 0.0001);
		if (bb1)
			return end_angle;
		if (bb2)
			return start_angle;
		double wdir_diff = normalizeAngle(end_angle - start_angle);
		if ((start_ws >= 0.0001) && (end_ws >= 0.0001) && (wdir_diff < 181.0)
				&& (wdir_diff > 179.0)) {
			if (iter < (upper_bound - lower_bound))
				return start_angle;
			return end_angle;
		}
		if (wdir_diff > 181.0)
			wdir_diff -= 360.0;
		double perc1 = ((double) (iter - lower_bound))
				/ ((double) (upper_bound - lower_bound));
		double wdir = start_angle + perc1 * wdir_diff;
		return normalizeAngle(wdir);
	}

	private XMLFile getFileOfType(XMLFileType type, String location) {
		String url = Calculator.basePath;
		String dt = "0000" + date.get(Calendar.YEAR);
		dt = dt.substring(dt.length() - 4);
		String month = "00" + (date.get(Calendar.MONTH) + 1);
		month = month.substring(month.length() - 2);
		dt += month;
		String day = "00" + date.get(Calendar.DAY_OF_MONTH);
		day = day.substring(day.length() - 2);
		dt += day;
		url = url + dt + "/" + time.toString()
				+ "/" + type.toString() + "/raw/";
		String filename = dt + time.toString()
				+ "_GEPS-NAEFS-RAW_" + location + "_" + type.toString()
				+ "_000-384.xml.bz2";
		URL u;
		String out_loc;
		try {
			u = new URL(url + filename);
			out_loc = WebDownloader.download(u);
			out_loc = BZipExtractor.extract(out_loc);
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return new XMLFile(out_loc, type);
	}
	
	/**
	 * Normalize a wind direction in compass degrees to be in [0, 360).
	 * @param direction The starting wind direction.
	 * @return A wind direction that is in [0, 360).
	 */
	private static double normalizeWindDirection(double direction) {
	    double ret = direction % 360.0;
	    ret = (ret + 360.0) % 360.0;
	    return ret;
	}

	/**
	 *
	 * @param membersToUse
	 * @param model
	 * @param members
	 * @param timezone
	 * @param date
	 * @param time
	 * @param ignorePrecip
	 * @throws InterruptedException
	 * @returns True if all imported data was valid, false if there were invalid weather values.
	 */
	boolean calculate(List<Integer> membersToUse, Model model, List<Integer> members, TimeZoneInfo timezone,
			Calendar date, Time time, boolean ignorePrecip) throws InterruptedException {
	    boolean retval = true;
		this.model = model;
		this.members = membersToUse;
		this.date = date;
		this.time = time;
		//start the xml files downloading and extracting
		temp_file = getFileOfType(XMLFileType.TEMPERATURE, location);
		rh_file = getFileOfType(XMLFileType.RELATIVE_HUMIDITY, location);
		apcp_file = getFileOfType(XMLFileType.PRECIPITATION, location);
		wind_file = getFileOfType(XMLFileType.WIND_SPEED, location);
		wdir_file = getFileOfType(XMLFileType.WIND_DIRECTION, location);
		//create the ridiculous number of variables that are needed
		hour_data = new ArrayList<Hour>();
		day_data = new ArrayList<Day>();
		double location_50 = (membersToUse.size() - 1) * (((double)percentile) / 100.0);
		double location_inv_50 = (membersToUse.size() - 1) * (((double)(100 - percentile)) / 100.0);
		double hours_apcp_50 = 0.0;
		double acc_apcp = 0.0;
		double curr_apcp = 0.0;
		double hours_rh_50 = 0.0;
		double hours_temp_50 = 0.0;
		double hours_wind_50 = 0.0;
		double hours_wdir = 0;
		Forecast temp_forecast, rh_forecast, apcp_forecast, wind_forecast, wdir_forecast;
		String tm2 = "";
		long forecast_local_hr = 0;
		//clear any previous calculations
		for (int i = 0; i < memberHourData.size(); i++)
			memberHourData.get(i).clear();
		//synchronize with the jobs downloading the xml files
		temp_file.join();
		rh_file.join();
		apcp_file.join();
		wind_file.join();
		wdir_file.join();
		//iterarte over the data to calculate the daily and hourly values
		for (int i = 0; i < temp_file.getForecastHourCount(); i++) {
			//get the forecast for the current hour
			temp_forecast = temp_file.getForecastAt(i);
			rh_forecast = rh_file.getForecastAt(i);
			apcp_forecast = apcp_file.getForecastAt(i);
			wind_forecast = wind_file.getForecastAt(i);
			wdir_forecast = wdir_file.getForecastAt(i);
			int exists = 0;
			for (int k = 0; k < membersToUse.size(); k++) {
				if (temp_forecast.modelExists(membersToUse.get(k)))
					exists++;
			}
			if (exists == 0)
				break;

			//get the zulu time
			int forecast_zulu_hr = Integer.parseInt(apcp_forecast.getValidTime().substring(8, 10));
			//convert it to local time
			long temp_forecast_local_hr = forecast_zulu_hr + timezone.getTimezoneOffset().getTotalHours() + timezone.getDSTAmount().getTotalHours();
			//clamp the time to [0, 23]
			int dayoffset = 0;
			while (temp_forecast_local_hr < 0) {
				temp_forecast_local_hr += 24;
				dayoffset -= 1;
			}
			while (temp_forecast_local_hr > 23) {
				temp_forecast_local_hr -= 24;
				dayoffset += 1;
			}
			forecast_local_hr = temp_forecast_local_hr;

			//get the date
			String part = apcp_forecast.getValidTime().substring(0, 8);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar dt = Calendar.getInstance();
			try {
				dt.setTime(sdf.parse(part));
			} catch (Exception e) {
				continue;
			}
			dt.add(Calendar.DAY_OF_YEAR, dayoffset);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			tm2 = sdf.format(dt.getTime());

			for (int member = 0; member < memberHourData.size(); member++) {
				try {
					memberHourData.get(member).add(
							new Hour(tm2, (int) forecast_local_hr,
									temp_forecast.get(member + 1),
									rh_forecast.get(member + 1),
									apcp_forecast.get(member + 1),
									wind_forecast.get(member + 1),
									wdir_forecast.get(member + 1)));
				} catch (ModelDoesNotExistException e) {
				}
			}

			//get the data for the requested members and order it lowest to highest
			List<Double> temp_model_list = temp_forecast.getValuesForMembers(membersToUse);
			Collections.sort(temp_model_list);
			List<Double> rh_model_list = rh_forecast.getValuesForMembers(membersToUse);
			Collections.sort(rh_model_list);
			List<Double> apcp_model_list = apcp_forecast.getValuesForMembers(membersToUse);
			Collections.sort(apcp_model_list);
			List<Double> wind_model_list = wind_forecast.getValuesForMembers(membersToUse);
			Collections.sort(wind_model_list);
			List<Double> wdir_model_list = wdir_forecast.getValuesForMembers(membersToUse);

			//get the precipitation
			curr_apcp = getPercentileValueFromList(apcp_model_list, location_inv_50);
			hours_apcp_50 = Math.max(curr_apcp - acc_apcp, 0.0);
			acc_apcp = curr_apcp;

			//get the hours RH at the (100 - 50)th percentile
			hours_rh_50 = getPercentileValueFromList(rh_model_list, location_inv_50);

			//get the hours temperature
			hours_temp_50 = getPercentileValueFromList(temp_model_list, location_50);

			//get the hours wind speed
			hours_wind_50 = getPercentileValueFromList(wind_model_list, location_50);

			//get the hours wind direction
			hours_wdir = normalizeWindDirection(getPredominantAverageWindDir(wdir_model_list));

			//save the current hours forecast
			//store the hours 50th percentile data
			Hour h = new Hour(tm2, (int) forecast_local_hr, hours_temp_50, hours_rh_50,
			        ignorePrecip ? 0.0 : hours_apcp_50, hours_wind_50, hours_wdir);
			//check to see if any of the imported values were invalid.
			if (hours_rh_50 < 0.0 || hours_rh_50 > 100.0 ||
			        hours_temp_50 < -50.0 || hours_temp_50 > 60.0 ||
			        hours_wind_50 < 0.0) {
			    h.setError(true);
			    retval = false;
			}
			hour_data.add(h);
		}
		calculateDailyWindDirection();
		fillInGaps();
		calculateDaily();
		calculateDailyMembers();
		return retval;
	}

	private void fillInGaps() {
		double temp50[] = new double[hour_data.size()];
		double rh50[] = new double[hour_data.size()];
		double ws50[] = new double[hour_data.size()];
		double times[] = new double[hour_data.size()];
		for (int i = 0; i < hour_data.size(); i++) {
			times[i] = i * 6.0;
			temp50[i] = hour_data.get(i).getTemperature();
			rh50[i] = hour_data.get(i).getRelativeHumidity();
			ws50[i] = hour_data.get(i).getWindSpeed();
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SplineInterpolator spline = new SplineInterpolator();
		PolynomialSplineFunction funct50 = spline.interpolate(times, temp50);
		PolynomialSplineFunction funcr50 = spline.interpolate(times, rh50);
		PolynomialSplineFunction funcw50 = spline.interpolate(times, ws50);
		for (int i = hour_data.size() - 1; i > 0; i--) {
			Hour data50 = hour_data.get(i - 1);
			int insertAt = i;
			Calendar c = (Calendar)data50.getCalendarDate().clone();
			for (int j = 1; j < 6; j++) {
				c.add(Calendar.HOUR_OF_DAY, 1);
				int hour = data50.getHour() + j;
				int dayOffset = 0;
				while (hour < 0) {
					hour += 24;
					dayOffset += 1;
				}
				while (hour > 23) {
					hour -= 23;
					dayOffset -= 1;
				}
				double wdir = data50.getWindDirection();
				double temp = funct50.value(((i - 1) * 6) + j);
				double rh = funcr50.value(((i - 1) * 6) + j);
				double wind = funcw50.value(((i - 1) * 6) + j);
				wdir = constrainToRange(wdir, 0, Double.MAX_VALUE);
				rh = constrainToRange(rh, 0.0, 100.0);
				temp = constrainToRange(temp, -50.0, 60.0);
				Calendar c2 = (Calendar)c.clone();
				c2.add(Calendar.DAY_OF_YEAR, dayOffset);
				String tm2 = format.format(c2.getTime());
				Hour h = new Hour(tm2, (int)data50.getHour() + j, temp, rh, 0, wind, wdir);
				h.setInterpolated(true);
				hour_data.add(insertAt, h);
				insertAt++;
			}
		}
	}

	private void calculateDailyWindDirection() {
		List<Double> wdir_daily_set = new ArrayList<Double>();
		Calendar dt = (Calendar)hour_data.get(0).getCalendarDate().clone();
		for (int i = 0; i < hour_data.size(); i++) {
			Hour h = hour_data.get(i);
			if (h.getCalendarDate().get(Calendar.DAY_OF_YEAR) != dt.get(Calendar.DAY_OF_YEAR)) {
				double wdir = getPredominantAverageWindDir(wdir_daily_set);
				daily_wdirs.add(wdir);
				wdir_daily_set.clear();
			}

			Forecast wdir_forecast = wdir_file.getForecastAt(i);
			List<Double> wdir_model_list = wdir_forecast.getValuesForMembers(members);
			for (Double d : wdir_model_list)
				wdir_daily_set.add(d);
		}
	}

	private void calculateDaily() {
		Calendar dt = (Calendar)hour_data.get(0).getCalendarDate().clone();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String tm2 = sdf.format(dt.getTime());
		double min_temp_50 =  10000.0;
		double max_temp_50 = -10000.0;
		double min_wind_50 =  10000.0;
		double max_wind_50 = -10000.0;
		double precip_50 = 0;
		double min_rh_50 = 10000.0;
		int day = 0;
		for (int i = 0; i < hour_data.size(); i++) {
			Hour h = hour_data.get(i);
			if (h.getCalendarDate().get(Calendar.DAY_OF_YEAR) != dt.get(Calendar.DAY_OF_YEAR)) {
				//calculate/store data
				double wdir = daily_wdirs.get(day);
				Day d = new Day(tm2, min_temp_50, max_temp_50, min_rh_50, precip_50, min_wind_50,
						max_wind_50, wdir);
				day_data.add(d);
				//clear data
				day++;
				min_temp_50 =  10000.0;
				max_temp_50 = -10000.0;
				min_wind_50 =  10000.0;
				max_wind_50 = -10000.0;
				min_rh_50 = 10000.0;
				precip_50 = 0;

				dt = (Calendar)h.getCalendarDate().clone();
				tm2 = sdf.format(dt.getTime());
			}

			if (h.getRelativeHumidity() < min_rh_50)
				min_rh_50 = h.getRelativeHumidity();
			if (h.getTemperature() < min_temp_50)
				min_temp_50 = h.getTemperature();
			if (h.getTemperature() > max_temp_50)
				max_temp_50 = h.getTemperature();
			if (h.getWindSpeed() < min_wind_50)
				min_wind_50 = h.getWindSpeed();
			if (h.getWindSpeed() > max_wind_50)
				max_wind_50 = h.getWindSpeed();
			precip_50 += h.getPrecipitation();
		}
	}

	private void calculateDailyMembers() {
		double maxTemp;
		double minTemp;
		double maxWind;
		double minWind;
		double precip;
		double minRH;
		List<Double> wdir = new ArrayList<Double>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < memberDayData.size(); i++) {
			precip = 0.0;
			maxTemp = -Double.MAX_VALUE;
			minTemp = Double.MAX_VALUE;
			maxWind = -Double.MAX_VALUE;
			minWind = Double.MAX_VALUE;
			minRH = Double.MAX_VALUE;
			wdir.clear();
			Calendar dt = memberHourData.get(i).get(0).getCalendarDate();
			for (int j = 0; j < memberHourData.get(i).size(); j++) {
				Hour h = memberHourData.get(i).get(j);
				if (h.getCalendarDate().equals(dt)) {
					precip += h.getPrecipitation();
					wdir.add(h.getWindDirection());
					if (h.getTemperature() < minTemp)
						minTemp = h.getTemperature();
					if (h.getTemperature() > maxTemp)
						maxTemp = h.getTemperature();
					if (h.getWindSpeed() < minWind)
						minWind = h.getWindSpeed();
					if (h.getWindSpeed() > maxWind)
						maxWind = h.getWindSpeed();
					if (h.getRelativeHumidity() < minRH)
						minRH = h.getRelativeHumidity();
				}
			}
			String tm2 = sdf.format(dt.getTime());
			Day d = new Day(tm2, minTemp, maxTemp, minRH, precip, minWind, maxWind, getPredominantAverageWindDir(wdir));
			memberDayData.get(i).add(d);
		}
	}

	/**
	 * Saves the daily and hourly weather data for this location.
	 *
	 * @param directory
	 *            The directory to store the information in.
	 * @return True if the weather information was saved, false if there was no
	 *         data to save.
	 * @throws IOException
	 *             Thrown if {@link directory} does not exist or is not a
	 *             directory or if subdirectories cannot be created.
	 */
	boolean saveData(String directory) throws IOException {
		if (hour_data == null || hour_data.size() <= 0)
			return false;
		if (day_data == null || day_data.size() <= 0)
			return false;
		File fl = new File(directory);
		if (!fl.isDirectory())
			throw new IOException("Path is not a directory");
		fl = new File(directory + "/hourly");
		if (!fl.exists()) {
			if (!fl.mkdir())
				return false;
		}
		File fl2 = new File(directory + "/daily");
		if (!fl2.exists()) {
			if (!fl2.mkdir())
				return false;
		}

		String dateString = new SimpleDateFormat("yyyyMMdd").format(date
				.getTime());
		if (members.size() > 1) {
			String fname, middle;
			if (model == CUSTOM)
				middle = "_CUSTOM_";
			else if (model == GEM)
				middle = "_GEM_";
			else if (model == BOTH)
				middle = "_BOTH_";
			else
				middle = "_NCEP_";
			fname = fl.getAbsolutePath() + "/" + dateString + time.toString()
					+ middle + "P50_" + location + ".csv";
			Hour.writeListToFile(fname, hour_data);
			fname = fl2.getAbsolutePath() + "/" + dateString + time.toString()
					+ middle + "P50_" + location + ".csv";
			Day.writeListToFile(fname, day_data);
		}
		if (model == CUSTOM) {
			for (int i = 0; i < memberHourData.size(); i++) {
				if (members.contains(i + 1)) {
					String filename = fl.getAbsolutePath() + "/" + dateString
							+ time.toString() + "_MEMBER_" + (i + 1) + "_"
							+ location + ".csv";
					Hour.writeListToFile(filename, memberHourData.get(i));
					filename = fl2.getAbsolutePath() + "/" + dateString
							+ time.toString() + "_MEMBER_" + (i + 1) + "_"
							+ location + ".csv";
					Day.writeListToFile(filename, memberDayData.get(i));
				}
			}
		}

		return true;
	}

	private List<Hour> buildInterpolatedList(List<Hour> original) {
		List<Hour> hours2 = new ArrayList<Hour>(original.size());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		double[] temp = new double[original.size()];
		double[] rh = new double[original.size()];
		double[] ws = new double[original.size()];
		double[] times = new double[original.size()];
		for (int j = 0; j < original.size(); j++) {
			times[j] = j * 6.0;
			temp[j] = original.get(j).getTemperature();
			rh[j] = original.get(j).getRelativeHumidity();
			ws[j] = original.get(j).getWindSpeed();
		}
		SplineInterpolator spline = new SplineInterpolator();
		PolynomialSplineFunction funct = spline.interpolate(times, temp);
		PolynomialSplineFunction funcr = spline.interpolate(times, rh);
		PolynomialSplineFunction funcw = spline.interpolate(times, ws);
		for (int j = original.size() - 1; j > 0; j--) {
			Hour data = original.get(j - 1);
			int insertAt = 0;
			Calendar c = (Calendar)data.getCalendarDate().clone();
			for (int k = 1; k < 6; k++) {
				c.add(Calendar.HOUR_OF_DAY, 1);
				int hour = data.getHour() + k;
				int dayOffset = 0;
				while (hour < 0) {
					hour += 24;
					dayOffset += 1;
				}
				while (hour > 23) {
					hour -= 23;
					dayOffset -= 1;
				}
				double wdir = data.getWindDirection();
				double temper = funct.value(((j - 1) * 6) + k);
				double rher = funcr.value(((j - 1) * 6) + k);
				double winder = funcw.value(((j - 1) * 6) + k);
				Calendar c2 = (Calendar)c.clone();
				c2.add(Calendar.DAY_OF_YEAR, dayOffset);
				String tm2 = format.format(c2.getTime());
				Hour h = new Hour(tm2, (int)data.getHour() + k, temper, rher, 0, winder, wdir);
				h.setInterpolated(true);
				hours2.add(insertAt, h);
				insertAt++;
			}
			hours2.add(0, data);
		}
		return hours2;
	}

	boolean saveHourlyData(String directory) throws IOException {
		if (hour_data == null || hour_data.size() <= 0)
			return false;
		if (day_data == null || day_data.size() <= 0)
			return false;
		File fl = new File(directory);
		if (!fl.isDirectory())
			throw new IOException("Path is not a directory");

		String dateString = new SimpleDateFormat("yyyyMMdd").format(date.getTime());
		if (time == Time.MIDNIGHT)
			dateString = dateString + "00";
		else
			dateString = dateString + "12";
		if (model == GEM_DETER) {
			String fname = fl.getAbsolutePath() + "/" + location + "_" + dateString + "_GEM_DET.csv";
			Hour.writeListToFile(fname, hour_data);
		}
		else if (members.size() > 1) {
			String fname, middle;
			if (model == CUSTOM)
				middle = "_CUSTOM_";
			else if (model == GEM)
				middle = "_GEM_";
			else if (model == BOTH)
				middle = "_BOTH_";
			else
				middle = "_NCEP_";
			middle += "P" + String.valueOf(percentile);
			fname = fl.getAbsolutePath() + "/" + location + "_" + dateString + middle + ".csv";
			Hour.writeListToFile(fname, hour_data);
		}
		if (model == CUSTOM) {
			for (int i = 0; i < memberHourData.size(); i++) {
				if (members.contains(i + 1)) {
					String filename = fl.getAbsolutePath() + "/" + location + "_"
							+ dateString + "_MEMBER_" + (i + 1) + ".csv";
					List<Hour> hours = memberHourData.get(i);
					List<Hour> hours2 = buildInterpolatedList(hours);
					Hour.writeListToFile(filename, hours2);
				}
			}
		}

		return true;
	}
	
	boolean saveHourlyData(String directory, Model model) throws IOException {
		if (hour_data == null || hour_data.size() <= 0)
			return false;
		if (day_data == null || day_data.size() <= 0)
			return false;
		File fl = new File(directory);
		if (!fl.isDirectory())
			throw new IOException("Path is not a directory");

		String dateString = new SimpleDateFormat("yyyyMMdd").format(date.getTime());
		if (time == Time.MIDNIGHT)
			dateString = dateString + "00";
		else
			dateString = dateString + "12";
		if (model == GEM_DETER) {
			String fname = fl.getAbsolutePath() + "/" + location + "_" + dateString + "_GEM_22_DETERMINISTIC.csv";
			Hour.writeListToFile(fname, hour_data);
		}
		else if (members.size() > 1) {
			String fname, middle;
			if (model == CUSTOM)
				middle = "_CUSTOM_";
			else if (model == GEM)
				middle = "_GEM_";
			else if (model == BOTH)
				middle = "_BOTH_";
			else
				middle = "_NCEP_";
			middle += "P" + String.valueOf(percentile);
			fname = fl.getAbsolutePath() + "/" + location + "_" + dateString + middle + ".csv";
			Hour.writeListToFile(fname, hour_data);
		}
		if ((model == Model.GEM || model == Model.NCEP)) {
			int j = -1;
			int i = -1;
			
			if(model == Model.GEM) {
				i = 1;
				j = 21;
			} else if (model == Model.NCEP) {
				i = 23;
				j = 43;
			}
			
			for (int k = i; k <= j; k++) {
				String filename = fl.getAbsolutePath() + "/" + location + "_"
						+ dateString;
				
				if (k == 1) {
					filename += "_GEM_1_CONTROL.csv";
				}
				else if (k == 23) {
					filename += "_NCEP_23_CONTROL.csv";
				}
				else {
					if (model == Model.GEM)
						filename += "_GEM_" + (k) + ".csv";
					else if (model == Model.NCEP)
						filename += "_NCEP_" + (k) + ".csv";
				}
				
				List<Hour> hours = memberHourData.get(k - 1);
				List<Hour> hours2 = buildInterpolatedList(hours);
				Hour.writeListToFile(filename, hours2);
			}
		}

		return true;
	}
}
