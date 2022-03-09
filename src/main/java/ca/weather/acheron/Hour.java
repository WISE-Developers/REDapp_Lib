/***********************************************************************
 * REDapp - Hour.java
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Stores information about an hours weather information.
 * 
 * @author Travis Redpath
 *
 */
public class Hour {
	private Calendar date;
	private double temp;
	private double rh;
	private double precip;
	private double ws;
	private double wd;
	private boolean interpolated;
	private boolean error = false;
	private static final DecimalFormat formatter = new DecimalFormat("#.#");
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	Hour(String date, int hour, double temp, double rh, double precip, double ws, double wd) {
		this.date = Calendar.getInstance();
		synchronized (dateFormatter) {
			try {
				this.date.setTime(dateFormatter.parse(date));
			} catch (ParseException e) {
			}
		}
		this.date.set(Calendar.HOUR_OF_DAY, hour);
		this.date.set(Calendar.MINUTE, 0);
		this.temp = temp;
		this.rh = rh;
		this.precip = precip;
		this.ws = ws;
		this.wd = wd;
		this.interpolated = false;
	}

	Hour(Calendar date, double temp, double rh, double precip, double ws, double wd) {
		this.date = date;
		this.date.set(Calendar.MINUTE, 0);
		this.temp = temp;
		this.rh = rh;
		this.precip = precip;
		this.ws = ws;
		this.wd = wd;
		this.interpolated = false;
	}
	
	/**
	 * Get whether or not this hours data was interpolated.
	 * @return
	 */
	public boolean isInterpolated() {
		return interpolated;
	}
	
	/**
	 * Set whether or not the hours data was interpolated.
	 * @param interpolated
	 */
	public void setInterpolated(boolean interpolated) {
		this.interpolated = interpolated;
	}
	
	/**
	 * Was an error found in the imported weather.
	 */
	public boolean isError() { return error; }
	
	/**
	 * Set if there was an error in the imported weather.
	 * @param error True if there was an error.
	 */
	public void setError(boolean error) { this.error = error; }

	/**
	 * Get this hours date and time as a string.
	 * 
	 * @return the date and time
	 */
	public String getDate() {
		synchronized (dateFormatter) {
			return dateFormatter.format(date.getTime());
		}
	}

	/**
	 * Get this hours date and time as a Calendar.
	 * 
	 * @return the date and time
	 */
	public Calendar getCalendarDate() {
		return date;
	}

	/**
	 * Get this hours hour.
	 * 
	 * @return the hour
	 */
	public int getHour() {
		return date.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Get this hours temperature.
	 * 
	 * @return the temperature
	 */
	public double getTemperature() {
		return temp;
	}

	/**
	 * Get this hours relative humidity.
	 * 
	 * @return the relative humidity
	 */
	public double getRelativeHumidity() {
		return rh;
	}

	/**
	 * Get this hours precipitation.
	 * 
	 * @return the precipitation
	 */
	public double getPrecipitation() {
		return precip;
	}

	/**
	 * Set this hours precipitation.
	 * 
	 * @param precip the new precipitation value
	 */
	void setPrecipitation(double precip) {
		this.precip = precip;
	}

	/**
	 * Get this hours wind speed.
	 * 
	 * @return the wind speed
	 */
	public double getWindSpeed() {
		return ws;
	}

	/**
	 * Get this hours wind direction.
	 * 
	 * @return the wind direction
	 */
	public double getWindDirection() {
		return wd;
	}

	/**
	 * Write this hours weather information to a file.
	 * 
	 * @param wrtr an open handle to a writable file
	 * @throws IOException thrown if the file is not writable
	 */
	private void writeToFile(BufferedWriter wrtr) throws IOException {
		wrtr.write(getDate() + "," + getHour() + "," + formatter.format(temp) + "," + formatter.format(rh) + "," + formatter.format(precip) + "," + formatter.format(ws) + "," + formatter.format(wd) + "\r\n");
	}

	/**
	 * Write this hours weather information to a file with a different value for the hour than what is stored.
	 * 
	 * @param wrtr an open handle to a writable file
	 * @param houroverride the hour to write instead of the one stored
	 * @throws IOException thrown if the file is not writable
	 */
	private void writeToFile(BufferedWriter wrtr, int houroverride) throws IOException {
		wrtr.write(getDate() + "," + houroverride + "," + formatter.format(temp) + "," + formatter.format(rh) + "," + formatter.format(precip) + "," + formatter.format(ws) + "," + formatter.format(wd) + "\r\n");
	}

	private static void writeHeader(BufferedWriter wrtr) throws IOException {
		wrtr.write("hourly,hour,temp,rh,precip,ws,wd\r\n");
	}

	static void writeListToFile(String path, List<Hour> list) throws IOException {
		File fl = new File(path);
		//overwrite any existing data
		FileWriter fw = new FileWriter(fl.getAbsoluteFile(), false);
		BufferedWriter bw = new BufferedWriter(fw);
		writeHeader(bw);
		int hour = list.get(0).getHour();
		int skip = 0;
		//fill in missing hours at the beginning of the data
		//by just copying the first hour's data to all missing hours
		if (hour > 0) {
			if (hour < 12) {
				for (int i = 0; i < hour; i++) {
					list.get(0).writeToFile(bw, i);
				}
			}
			else {
				skip = 24 - hour;
			}
		}
		for (Hour d : list) {
			if (skip > 0) {
				skip--;
				continue;
			}
			d.writeToFile(bw);
		}
		Hour last = list.get(list.size() - 1);
		int lasthour = last.getHour();
		while (lasthour < 23) {
			lasthour++;
			Hour newlast = new Hour(last.getDate(), lasthour, last.getTemperature(), last.getRelativeHumidity(), 0, last.getWindSpeed(), last.getWindDirection());
			newlast.writeToFile(bw);
		}
		bw.close();
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm MM/dd/yyyy");
		return format.format(date.getTime());
	}
}
