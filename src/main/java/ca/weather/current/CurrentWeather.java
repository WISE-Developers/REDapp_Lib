/***********************************************************************
 * REDapp - CurrentWeather.java
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

package ca.weather.current;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import ca.hss.general.WebDownloader;
import ca.weather.current.Cities.Cities;
import ca.weather.forecast.InvalidXMLException;

/**
 * Get current weather information from Environment Canada's RSS feed.
 * 
 * @author Travis
 */
public class CurrentWeather extends Thread {
	private String basePath = "https://weather.gc.ca/rss/city/";
	private String observed;
	private String condition;
	private String temperature;
	private String pressure;
	private String visibility;
	private String humidity;
	private String windchill;
	private String dewpoint;
	private String wind;
	private String airquality;
	private String humidex;
	private Cities city;
	private boolean valid = true;
	private Semaphore initMutex = new Semaphore(1);

	/**
	 * Get the observed time and location.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getObserved() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return observed;
	}
	/**
	 * Get the observed time.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getObservedTime() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (observed == null)
			return null;
		Pattern pat = Pattern.compile("([12]?[0-9]{1}:[0-9]{2}(?:\\sAM|\\sPM|))", Pattern.CASE_INSENSITIVE);
		Matcher mat = pat.matcher(observed);
		String retval = null;

		if (mat.find()) {
			try {
				retval = mat.group(1).trim();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retval;
	}
	/**
	 * Get the observed date.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getObservedDate() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (observed == null)
			return null;
		Pattern pat = Pattern.compile("([0-9]{2}\\s[A-Za-z]+\\s[0-9]{4})", Pattern.CASE_INSENSITIVE);
		Matcher mat = pat.matcher(observed);
		String retval = null;

		if (mat.find()) {
			try {
				retval = mat.group(1).trim();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retval;
	}
	public Date getObservedDateTime() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (observed == null)
			return null;
		int index;
		for (index = 0; index < observed.length(); index++) {
			if (Character.isDigit(observed.charAt(index)))
				break;
		}
		if (index == observed.length())
			return null;
		String subs = observed.substring(index);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a z EEEE dd MMMM yyyy");
		Date d;
		try {
			d = sdf.parse(subs);
		} catch (ParseException e) {
			return null;
		}
		return d;
	}
	/**
	 * Get the condition.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getCondition() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return condition;
	}
	private static String imageName(String condition, boolean isNight) {
		Integer imagepath = null;
		String cond = condition.toLowerCase();
		//0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 40
		if (cond.equals("mostly cloudy") ||
				cond.equals("mainly cloudy")) {
			imagepath = 3;
		}
		else if  (cond.equals("a mix of sun and cloud") ||
				cond.equals("partly cloudy")) {
			imagepath = 2;
		}
		else if (cond.contains("cloudy") ||
				cond.contains("overcast")) {
			imagepath = 10;
		}
		else if (cond.equals("chance of thundershower")) {
			imagepath = 9;
		}
		else if (cond.contains("thundershower")) {
			imagepath = 19;
		}
		else if (cond.contains("mist") ||
				cond.contains("fog")) {
			if (isNight)
				imagepath = 23;
			else
				imagepath = 24;
		}
		else if (cond.equals("chance of flurries") ||
				cond.equals("light snowshower")) {
			imagepath = 8;
		}
		else if (cond.equals("chance of showers") ||
				cond.contains("light rain") ||
				cond.equals("periods of rain") ||
				cond.equals("rain")) {
			imagepath = 12;
		}
		else if (cond.equals("rain at times heavy")) {
			imagepath = 13;
		}
		else if (cond.equals("sunny") ||
				cond.equals("clear")) {
			imagepath = 0;
		}
		else if (cond.contains("cloudy") && (cond.contains("snow or rain") || cond.contains("rain or snow"))) {
			imagepath = 7;
		}
		else if (cond.contains("snow or rain")) {
			imagepath = 15;
		}
		else if (cond.contains("light snow") ||
				cond.equals("a few flurries") ||
				cond.equals("flurries") ||
				cond.equals("periods of snow")) {
			imagepath = 16;
		}
		else if (cond.equals("precipitation")) {
			imagepath = 11;
		}
		else if (cond.equals("snow")) {
			imagepath = 17;
		}
		else if (cond.equals("snow at times heavy")) {
			imagepath = 18;
		}
		else if (cond.equals("ice crystals")) {
			imagepath = 26;
		}
		else if (cond.equals("ice pellets")) {
			imagepath = 27;
		}
		else if (cond.contains("light drizzle")) {
			imagepath = 28;
		}
		else if (cond.equals("light rainshower")) {
			imagepath = 6;
		}
		else if (cond.equals("drifting snow")) {
			imagepath = 25;
		}
		else if (cond.contains("freezing rain")) {
			imagepath = 14;
		}
		else if (cond.contains("increasing cloud")) {
			imagepath = 4;
		}
		else if (cond.equals("clearing")) {
			imagepath = 5;
		}
		else if (cond.equals("a few clouds") ||
				cond.equals("mainly sunny") ||
				cond.equals("mainly clear")) {
			imagepath = 1;
		}
		else if (cond.contains("storm") && cond.contains("snow")) {
			imagepath = 40;
		}
		else if (cond.contains("tornado")) {
			imagepath = 41;
		}
		else if (cond.contains("breezy")) {
			imagepath = 43;
		}
		else if (cond.contains("hurricane")) {
			imagepath = 48;
		}
		else if (cond.contains("fire") || cond.contains("smoke")) {
			imagepath = 44;
		}
		if (imagepath == null)
			return null;
		if (isNight && imagepath < 10)
			imagepath += 30;
		String temp = "00" + imagepath;
		return temp.substring(temp.length() - 2, temp.length());
	}
	public Image getConditionImage() throws InterruptedException {
		initMutex.acquire();
		if (condition == null || condition.length() == 0)
			return null;
		String imagepath = imageName(condition, false);
		if (imagepath == null)
			return null;
		Image img = null;
		try {
			URL url = new URL("https://www.weather.gc.ca/weathericons/small/" + imagepath + ".png");
			img = ImageIO.read(url);
			return img;
		} catch (IOException e) {
		}
		return img;
	}
	
	/**
	 * Get the temperature string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getTemperatureString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return temperature;
	}
	/**
	 * Get the temperature units.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getTempratureUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (temperature == null)
			return null;
		int index = temperature.indexOf('\u00b0');
		if (index < 0)
			return "";
		return temperature.substring(index);
	}
	/**
	 * Get the temperature.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getTemperature() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (temperature == null)
			return null;
		int index = temperature.indexOf('\u00b0');
		if (index < 0)
			return Double.valueOf(temperature);
		return Double.valueOf(temperature.substring(0, index));
	}
	/**
	 * Get the pressure/tendency string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getPressureString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return pressure;
	}
	/**
	 * Get the pressure.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getPressure() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (pressure == null)
			return null;
		return Double.valueOf(pressure.replaceAll("[^\\d]", ""));
	}
	/**
	 * Get the units of the pressure.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getPressureUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (pressure == null)
			return null;
		int index = pressure.indexOf(' ');
		String temp = pressure.substring(index).trim();
		index = pressure.indexOf(' ');
		return temp.substring(0, index);
	}
	/**
	 * Get the tendency of the pressure.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getPressureTendency() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (pressure == null)
			return null;
		int index = pressure.indexOf(' ');
		String temp = pressure.substring(index).trim();
		index = temp.indexOf(' ');
		return temp.substring(index).trim();
	}
	/**
	 * Get the visibility string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getVisibilityString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return visibility;
	}
	/**
	 * Get the visibility distance.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getVisibility() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (visibility == null)
			return null;
		return Double.valueOf(visibility.replaceAll("[^\\d]", ""));
	}
	/**
	 * Get the units of the visibility.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getVisibilityUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (visibility == null)
			return null;
		int index = visibility.indexOf(' ');
		if (index < 0)
			return "";
		return visibility.substring(index).trim();
	}
	/**
	 * Get the humidity string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getHumidityString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return humidity;
	}
	/**
	 * Get the humidity.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getHumidity() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (humidity == null)
			return null;
		return Double.valueOf(humidity.replaceAll("[^\\d]", ""));
	}
	/**
	 * Get the units of the humidity.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getHumidityUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (humidity == null)
			return null;
		int index = humidity.indexOf(' ');
		if (index < 0)
			return "";
		return humidity.substring(index).trim();
	}
	/**
	 * Get the windchill string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getWindchillString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return windchill;
	}
	/**
	 * Get the windchill.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getWindchill() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (windchill == null)
			return null;
		return Double.valueOf(windchill);
	}
	/**
	 * Get the dewpoint string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getDewpointString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return dewpoint;
	}
	/**
	 * Get the dewpoint.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getDewpoint() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (dewpoint == null)
			return null;
		return Double.valueOf(dewpoint.replaceAll("[^\\d]", ""));
	}
	/**
	 * Get the units of the dewpoint.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getDewpointUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (dewpoint == null)
			return null;
		int index = dewpoint.indexOf('\u00b0');
		if (index < 0)
			return "";
		return dewpoint.substring(index);
	}
	/**
	 * Get the wind speed and direction string.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getWindString() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return wind;
	}
	/**
	 * Get the wind direction as a string (S, E, NW, WSW, ...).
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getWindDirection() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (wind == null)
			return null;
		Double speed = getWindSpeed();
		if (speed == null || speed == 0)
			return "None";
		if (wind.equalsIgnoreCase("km/h"))
			return "Not Reported";
		int index = wind.indexOf(' ');
		if (index < 0)
			return "Not Reported";
		return wind.substring(0, index);
	}
	public Double getWindDirectionAngle() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (wind == null)
			return null;
		String windDir = getWindDirection();
		if (windDir.equalsIgnoreCase("N"))
			return 0.0;
		else if (windDir.equalsIgnoreCase("NNE"))
			return 22.5;
		else if (windDir.equalsIgnoreCase("NE"))
			return 45.0;
		else if (windDir.equalsIgnoreCase("ENE"))
			return 67.5;
		else if (windDir.equalsIgnoreCase("E"))
			return 90.0;
		else if (windDir.equalsIgnoreCase("ESE"))
			return 112.5;
		else if (windDir.equalsIgnoreCase("SE"))
			return 135.0;
		else if (windDir.equalsIgnoreCase("SSE"))
			return 157.5;
		else if (windDir.equalsIgnoreCase("S"))
			return 180.0;
		else if (windDir.equalsIgnoreCase("SSW"))
			return 202.5;
		else if (windDir.equalsIgnoreCase("SW"))
			return 225.0;
		else if (windDir.equalsIgnoreCase("WSW"))
			return 247.5;
		else if (windDir.equalsIgnoreCase("W"))
			return 270.0;
		else if (windDir.equalsIgnoreCase("WNW"))
			return 292.5;
		else if (windDir.equalsIgnoreCase("NW"))
			return 315.5;
		else if (windDir.equalsIgnoreCase("NNW"))
			return 337.5;
		else if (getWindSpeed() == null || getWindSpeed() == 0)
			return 0.0;
		return null;
	}
	/**
	 * Get the wind speed.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Double getWindSpeed() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (wind == null)
			return null;
		if (wind.equalsIgnoreCase("km/h"))
			return null;
		int index = wind.indexOf("km/h");
		if (index == 0)
			return null;
		String sub1 = wind.substring(0, index - 1).trim();
		int index2 = sub1.indexOf(' ');
		String sub2;
		if (index2 >= 0)
			sub2 = sub1.substring(index2);
		else
			sub2 = sub1;
		sub2 = sub2.trim();
		return Double.parseDouble(sub2.replaceAll("[^\\d]", ""));
	}
	/**
	 * Get the units of the wind speed.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getWindSpeedUnits() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (wind == null)
			return null;
		int index = wind.lastIndexOf(' ');
		if (index < 0)
			return "";
		return wind.substring(index).trim();
	}
	/**
	 * Get the air quality.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public String getAirQuality() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		return airquality;
	}
	/**
	 * Get the air quality health index.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return This return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Integer getAirQualityHealthIndex() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (airquality == null)
			return null;
		return Integer.valueOf(airquality);
	}

	/**
	 * Get the humidex.
	 *
	 * <strong>WARNING:</strong>: this method will block until the xml data has finished being loaded.
	 * @return The return value will be null if the value was not reported.
	 * @throws InterruptedException
	 * @throws InvalidXMLException
	 */
	public Integer getHumidex() throws InterruptedException, InvalidXMLException {
		initMutex.acquire();
		if (!valid) throw new InvalidXMLException();
		initMutex.release();
		if (humidex == null)
			return null;
		return Integer.valueOf(humidex);
	}

	/**
	 *
	 * @param c The city to get the current forecast for.
	 * @throws InterruptedException
	 */
	public CurrentWeather(Cities c) throws InterruptedException {
		city = c;
		initMutex.acquire();
		this.start();
	}

	/**
	 *
	 * @param c The city to get the current forecast for.
	 * @param c The base url from which to get the data.
	 * @throws InterruptedException
	 */
	public CurrentWeather(Cities c, String basePath) throws InterruptedException {
		city = c;
		if(!basePath.isEmpty())
			this.basePath = basePath;
		initMutex.acquire();
		this.start();
	}

	@Override
	public void run() {
		boolean started = false;
		valid = true;
		String path = basePath + city.getLink().toLowerCase() + "_e.xml";
		String htmlPath;
		try {
			htmlPath = WebDownloader.download(new URL(path));
		} catch (MalformedURLException e1) {
			valid = false;
			initMutex.release();
			return;
		} catch (IOException e1) {
			valid = false;
			initMutex.release();
			return;
		}
		Pattern timepat = Pattern.compile("<b>Observed at:</b>([A-Za-z0-9:\\s\\(\\)'\\.\u0232\u0233\u0224\u0244\u0206-]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern condpat = Pattern.compile("<b>Condition:</b>([A-Za-z\\s]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern temppat = Pattern.compile("<b>Temperature:</b>([A-Z0-9\\.&;\\s-]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern presspat = Pattern.compile("<b>Pressure(?: / Tendency|):</b>([0-9A-Za-z\\.\\s]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern vispat = Pattern.compile("<b>Visibility:</b>([0-9A-Za-z\\.\\s]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern humpat = Pattern.compile("<b>Humidity:</b>([0-9A-Za-z%\\s\\.]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern windcpat = Pattern.compile("<b>Wind Chill:</b>([0-9\\.\\s-]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern dewpat = Pattern.compile("<b>Dewpoint:</b>([A-Z0-9\\.&;\\s-]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern windpat = Pattern.compile("<b>Wind:</b>([A-Za-z0-9\\.\\s/]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern airpat = Pattern.compile("<b>Air Quality Health Index:</b>([A-Za-z0-9\\.\\s-]+)<br/>", Pattern.CASE_INSENSITIVE);
		Pattern humidexpat = Pattern.compile("<b>Humidex:</b>([A-Za-z0-9\\.\\s-]+)<br/>", Pattern.CASE_INSENSITIVE);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(htmlPath));
		} catch (FileNotFoundException e1) {
			valid = false;
			initMutex.release();
			return;
		}
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			valid = false;
			initMutex.release();
			if (br != null) {
				try {
					br.close();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return;
		}
		while (line != null) {
			if (line.trim().startsWith("<summary") && line.contains("<![CDATA[")) {
				started = true;
			}
			if (started) {
				Matcher timemat = timepat.matcher(line);
				Matcher condmat = condpat.matcher(line);
				Matcher tempmat = temppat.matcher(line);
				Matcher pressmat = presspat.matcher(line);
				Matcher vismat = vispat.matcher(line);
				Matcher hummat = humpat.matcher(line);
				Matcher windcmat = windcpat.matcher(line);
				Matcher dewmat = dewpat.matcher(line);
				Matcher windmat = windpat.matcher(line);
				Matcher airmat = airpat.matcher(line);
				Matcher humidexmat = humidexpat.matcher(line);

				if (timemat.find()) {
					try {
						observed = timemat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						return;
					}
				}
				else if (condmat.find()) {
					try {
						condition = condmat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (tempmat.find()) {
					try {
						temperature = tempmat.group(1).trim().replace("&deg;", "\u00b0");
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (pressmat.find()) {
					try {
						pressure = pressmat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (vismat.find()) {
					try {
						visibility = vismat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (hummat.find()) {
					try {
						humidity = hummat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (windcmat.find()) {
					try {
						windchill = windcmat.group(1).trim().replace("&deg;", "\u00b0");
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (dewmat.find()) {
					try {
						dewpoint = dewmat.group(1).trim().replace("&deg;", "\u00b0");
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (windmat.find()) {
					try {
						wind = windmat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (airmat.find()) {
					try {
						airquality = airmat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				else if (humidexmat.find()) {
					try {
						humidex = humidexmat.group(1).trim();
					}
					catch (Exception e) {
						valid = false;
						initMutex.release();
						if (br != null) {
							try {
								br.close();
							}
							catch (Exception ex) { }
						}
						return;
					}
				}
				//else
				//	started = false;
			}
			try {
				line = br.readLine();
			} catch (IOException e) {
				break;
			}
		}
		try {
			br.close();
		} catch (IOException e) {
		}
		initMutex.release();
	}
}
