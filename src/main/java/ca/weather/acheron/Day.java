/***********************************************************************
 * REDapp - Day.java
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
import java.util.List;

/**
 * Stores information about a days weather information.
 * 
 * @author Travis Redpath
 *
 */
public class Day {
	private String date;
	private double min_temp;
	private double max_temp;
	private double rh;
	private double precip;
	private double min_ws;
	private double max_ws;
	private double wd;
	private static final DecimalFormat formatter = new DecimalFormat("#.#");
	
	Day(String date, double minTemp, double maxTemp, double rh, double precip, double minWs, double maxWs, double wd) {
		this.date = date;
		this.min_temp = minTemp;
		this.max_temp = maxTemp;
		this.rh = rh;
		this.precip = precip;
		this.min_ws = minWs;
		this.max_ws = maxWs;
		this.wd = wd;
	}
	
	/**
	 * Get the date of this day.
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Get this days minimum temperature.
	 * 
	 * @return the minimum temperature
	 */
	public double getMinTemperature() {
		return min_temp;
	}
	
	/**
	 * Get this days maximum temperature.
	 * 
	 * @return the maximum temperature
	 */
	public double getMaxTemperature() {
		return max_temp;
	}
	
	/**
	 * Get this days minimum relative humidity.
	 * 
	 * @return the minimum relative humidity
	 */
	public double getRelativeHumidity() {
		return rh;
	}
	
	/**
	 * Get this days precipitation.
	 * 
	 * @return the precipitation for the day
	 */
	public double getPrecipitation() {
		return precip;
	}
	
	/**
	 * Get this days minimum wind speed.
	 * 
	 * @return the minimum wind speed
	 */
	public double getMinWindSpeed() {
		return min_ws;
	}
	
	/**
	 * Get this days maximum wind speed.
	 * 
	 * @return the maximum wind speed
	 */
	public double getMaxWindSpeed() {
		return max_ws;
	}
	
	/**
	 * Get this days average wind direction.
	 * 
	 * @return the average wind direction
	 */
	public double getWindDirection() {
		return wd;
	}
	
	/**
	 * Write the days data to a line in a text file.
	 * 
	 * @param wrtr an open handle to a writable file
	 * @throws IOException thrown if the file is unwritable
	 */
	public void writeToFile(BufferedWriter wrtr) throws IOException {
		wrtr.write(date + "," + formatter.format(min_temp) + "," + formatter.format(max_temp) + "," + formatter.format(rh) + "," + formatter.format(precip) + "," + formatter.format(min_ws) + "," + formatter.format(max_ws) + "," + formatter.format(wd) + "\r\n");
	}
	
	/**
	 * Write a list of days to a file.
	 * 
	 * @param path the name of the file to write to. If it already exists it will be overwritten
	 * @param list the list of days to write
	 * @throws IOException thrown if the given filename is not writable
	 */
	public static void writeListToFile(String path, List<Day> list) throws IOException {
		File fl = new File(path);
		FileWriter fw = new FileWriter(fl.getAbsoluteFile(), false);
		BufferedWriter bw = new BufferedWriter(fw);
		writeHeader(bw);
		for (Day d : list) {
			d.writeToFile(bw);
		}
		bw.close();
	}
	
	private static void writeHeader(BufferedWriter wrtr) throws IOException {
		wrtr.write("daily,min_temp,max_temp,rh,precip,min_ws,max_ws,wd\r\n");
	}
}
