/***********************************************************************
 * REDapp - Calculator.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ca.hss.general.WebDownloader;
import ca.hss.times.TimeZoneInfo;
import ca.hss.times.WorldLocation;
import ca.weather.forecast.Model;
import ca.weather.forecast.Province;
import ca.weather.forecast.Time;

/**
 * A class for computing collecting Canadian weather forecast information.
 * 
 * @author Travis Redpath
 *
 */
public class Calculator {
	private List<LocationWeather> locations;
	private List<Integer> members;
	static String basePath = "http://dd.weather.gc.ca/ensemble/naefs/xml/";
	private Model model = Model.CUSTOM;
	private Time time = Time.MIDNIGHT;
	private TimeZoneInfo timezone;
	private Calendar date;
	private boolean ignorePrecipitation = false;
	private int percentile = -1;
	private static String saveDir = null;
	private boolean dataError = false;

	public Calculator() {
		locations = new ArrayList<LocationWeather>();
		members = new ArrayList<Integer>();
		timezone = WorldLocation.defaultTimeZone();
		date = Calendar.getInstance();
	}

	/**
	 * Set the location for which to calculate the weather data.
	 * @param loc The location.
	 */
	public void setLocation(String loc) {
		locations.clear();
		locations.add(new LocationWeather(loc));
	}

	/**
	 * Set a
	 * @param locs
	 */
	public void setLocations(String ... locs) {
		locations.clear();
		for (String s : locs)
			locations.add(new LocationWeather(s));
	}

	/**
	 * Set the percentile value to use when calculating forecast data.
	 * 
	 * @param perc The new percentile value.
	 */
	public void setPercentile(int perc) {
		percentile = perc;
	}

	/**
	 * Add a location to the list of places to calculate weather data for.
	 * @param loc The new location.
	 */
	public void addLocation(String loc) {
		locations.add(new LocationWeather(loc));
	}

	/**
	 * Get the list of weather data for each location.
	 * @return An unmodifiable list of location weather data.
	 */
	public List<LocationWeather> getLocationsWeatherData() {
		return Collections.unmodifiableList(locations);
	}

	/**
	 * Get the number of locations.
	 * @return The number of locations.
	 */
	public int numberOfLocations() {
		return locations.size();
	}

	/**
	 * Get weather information for a location at a particular index.
	 * @param index The index of the location to get.
	 * @return The location or null if index was invalid.
	 */
	public LocationWeather getLocationsWeatherData(int index) {
		if (index >= locations.size() || index < 0)
			return null;
		return locations.get(index);
	}

	/**
	 * Add a member ID.
	 * @param member The new member id.
	 */
	public void addMember(int member) {
		members.add(Integer.valueOf(member));
	}

	/**
	 * Clear the member IDs.
	 */
	public void clearMembers() {
		members.clear();
	}

	/**
	 * Get the member IDs.
	 * @return An unmodifiable list of member IDs.
	 */
	public List<Integer> getMembers() {
		return Collections.unmodifiableList(members);
	}

	/**
	 * Set the model to use.
	 * @param mod The model.
	 */
	public void setModel(Model mod) {
		model = mod;
	}

	/**
	 * Get the model.
	 * @return The model.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Set the time to compute weather data for (either noon or midnight).
	 * @param tm
	 */
	public void setTime(Time tm) {
		time = tm;
	}

	/**
	 * Get the time.
	 * @return
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * Set the date to calculate weather information for.
	 * @param cal
	 */
	public void setDate(Calendar cal) {
		date = cal;
	}

	/**
	 * Get the date.
	 * @return
	 */
	public Calendar getDate() {
		return date;
	}


	public boolean calculate()
	{
		return calculate(percentile);
	}

	/**
	 * Calculates the weather information for each location.
	 * @return False if the inputs are not yet set.
	 */
	//TODO ensure there is a network connection.
	boolean calculate(int hack)
			{
		List<Integer> membersToUse = new ArrayList<Integer>();
		dataError = false;
		if (model == Model.CUSTOM) {
			for (Integer i : members)
				membersToUse.add(i);
		}
		else if (model == Model.GEM_DETER)
			membersToUse.add(22);
		else if (model == Model.GEM)
			for (int i = 1; i < 22; i++)
				membersToUse.add(i);
		else if (model == Model.NCEP)
			for (int i = 23; i <= 43; i++)
				membersToUse.add(i);
		else
			for (int i = 1; i <= 43; i++)
				membersToUse.add(i);
		if (membersToUse.size() == 0)
			return false;
		for (LocationWeather loc : locations) {
			try {
				loc.setPercentile(hack);
				boolean b = loc.calculate(membersToUse, model, members, timezone, date, time, ignorePrecipitation);
				if (!b)
				    dataError = true;
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Was there an error in the last downloaded ensemble data set.
	 * @return True if there was an error, false otherwise.
	 */
	public boolean isDataError() { return dataError; }

	/**
	 * Get a list of all locations that list weather data on the Weather Offices website.
	 * @return A list of locations.
	 * @throws MalformedURLException Thrown if there was an unknown network error.
	 * @throws IOException Thrown if there was an unknown network error.
	 * @throws NoNetworkConnectionException Thrown if there is not currently a network connection.
	 */
	//TODO ensure there is a network connection.
	public static List<String> scrapeLocations() throws MalformedURLException, IOException {
		List<String> list = new ArrayList<String>();

		String path = basePath;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dateFormat.format(cal.getTime());
		path = path + date;
		path = path + "/00/APCP-SFC/raw/";
		String htmlPath = WebDownloader.download(new URL(path));
		Pattern pat = Pattern.compile("\"[0-9]+_GEPS-NAEFS-RAW_(\\S+)_APCP-SFC_000-384.xml.bz2\"", Pattern.CASE_INSENSITIVE);
		try (BufferedReader br = new BufferedReader(new FileReader(htmlPath))) {
			String line;
			while ((line = br.readLine()) != null) {
				Matcher mat = pat.matcher(line);
				while (mat.find()) {
					try {
						list.add(mat.group(1).replace('_', ' '));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return list;
	}

	public static class LocationSmall {
		public String locationName;
		public String country;
		public double latitude;
		public double longitude;
		public double elevation;
	}

	private static List<LocationSmall> locationList = null;
	public static synchronized List<LocationSmall> getLocations() {
		if (locationList != null)
			return locationList;
		locationList = new ArrayList<>();
		String path = "https://dd.weather.gc.ca/ensemble/doc/naefs/xml/locations.xml";
		String out_loc;
		
		try {
			URL website = new URL(path);
			out_loc = WebDownloader.download(website);
			if (saveDir != null && saveDir.length() > 0) {
				Path out = Paths.get(saveDir);
				Files.createDirectories(out);
				out = out.resolve("locations.xml");
				Files.copy(Paths.get(out_loc), out, StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get(out_loc));
				out_loc = out.toString();
			}
		}
		catch (IOException e) {
			return locationList;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document dom;
		try {
			db = dbf.newDocumentBuilder();
			dom = db.parse(out_loc);
		}
		catch (ParserConfigurationException|SAXException|IOException e) {
			return locationList;
		}
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("location");
		if (nl == null || nl.getLength() == 0)
			return null;
		Node n = nl.item(0);
		while (n != null) {
			Element el;
			try {
				el = (Element)n;
			}
			catch (Exception e) {
				n = n.getNextSibling();
				continue;
			}
			if (el.getTagName().equals("location"))
				locationList.add(parseLocation(el));

			n = n.getNextSibling();
		}
		return locationList;
	}

	public static List<LocationSmall> getOfflineLocations() {
		if (locationList != null) {
			return locationList;
		}
		locationList = new ArrayList<LocationSmall>();
		
		if (saveDir != null && saveDir.length() > 0) {
			File locations = new File(saveDir + "\\locations.xml");
			String out_loc = locations.getAbsolutePath();
			
			if (Files.exists(Paths.get(out_loc))) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db;
				Document dom;
				try {
					db = dbf.newDocumentBuilder();
					dom = db.parse(out_loc);
				} catch (ParserConfigurationException e) {
					return null;
				} catch (SAXException e) {
					return null;
				} catch (IOException e) {
					return null;
				}
				//try {
					Element docEle = dom.getDocumentElement();
					NodeList nl = docEle.getElementsByTagName("location");
					if (nl == null || nl.getLength() == 0)
						return null;
					//Element header = (Element)nl.item(0);
					Node n = nl.item(0);
					//Node n = header.getFirstChild();
					while (n != null) {
						Element el;
						try {
							el = (Element)n;
						}
						catch (Exception e) {
							n = n.getNextSibling();
							continue;
						}
						if (el.getTagName().equals("location"))
							locationList.add(parseLocation(el));
		
						n = n.getNextSibling();
					}
				/*}
				catch (Exception e) {
					return null;
				}*/
			}
		}

		return locationList;
	}
	
	public static List<LocationSmall> getLocations(Province prov) {
		List<LocationSmall> list = getLocations();
		List<LocationSmall> provList = new ArrayList<LocationSmall>();
		Iterator<LocationSmall> it = list.iterator();
		String abbv = prov.abbreviation();
		while (it.hasNext()) {
			LocationSmall place = it.next();
			int charMarker = place.locationName.length();
			char[] placeChar = place.locationName.toCharArray();
			if (placeChar[--charMarker] == 'A' || placeChar[--charMarker] == 'a') {
				if (placeChar[--charMarker] == 'C' || placeChar[--charMarker] == 'c') {
					--charMarker;
					char[] chars = new char[] { placeChar[--charMarker], placeChar[--charMarker] };
					char tmp = chars[0];
					chars[0] = chars[1];
					chars[1] = tmp;
					String provinceID = new String(chars);
					if (provinceID.compareToIgnoreCase(abbv) == 0)
						provList.add(place);
				}
			}
		}
		return provList;
	}

	private static String getValue(Element el) {
		String retval = null;
		if (el.hasChildNodes()) {
			Node nn = el.getFirstChild();
			while (nn != null) {
				if ((retval = nn.getNodeValue()) != null)
					break;
				nn = nn.getNextSibling();
			}
		}
		else
			retval = el.getNodeValue();
		return retval;
	}

	private static LocationSmall parseLocation(Element el) {
		Node n = el.getFirstChild();
		LocationSmall ls = new LocationSmall();
		ls.locationName = el.getAttribute("file_desc").replace('_', ' ');
		ls.country = el.getAttribute("pays_country");
		while (n != null) {
			Element ele = null;
			try {
				ele = (Element)n;
			}
			catch (Exception e) {
			}
			if (ele != null) {
				if (ele.getTagName().equals("latitude"))
					ls.latitude = Double.parseDouble(getValue(ele));
				else if (ele.getTagName().equals("longitude"))
					ls.longitude = Double.parseDouble(getValue(ele));
				else if (ele.getTagName().equals("altitude")){
					String elevationStr = getValue(ele);
					ls.elevation = elevationStr != null ? Double.parseDouble(elevationStr) : Double.NaN;
				}
			}
			n = n.getNextSibling();
		}
		return ls;
	}

	/**
	 * Saves the weather data for each location.
	 * @param directory The directory to save the data to.
	 * @return This function will return false if at least one of the locations data was unable to be saved.
	 */
	public boolean saveWeather(String directory) {
		boolean retval = true;
		for (LocationWeather loc : locations) {
			File fl = new File(directory + "/" + loc.getLocation()+ "/" + model.toString() );
			if (!fl.exists()) {
				if (!fl.mkdirs())
					return false;
			}
			try {
				if (!loc.saveData(fl.getAbsolutePath()))
					retval = false;
			}
			catch (IOException e) {
				e.printStackTrace();
				retval = false;
			}
		}
		return retval;
	}

	public boolean saveHourlyWeather(String directory) {
		boolean retval = true;
		for (LocationWeather loc : locations) {
			File fl = new File(directory);
			if (!fl.exists()) {
				if (!fl.mkdirs())
					return false;
			}
			try {
				if (!loc.saveHourlyData(fl.getAbsolutePath()))
					retval = false;
			}
			catch (IOException e) {
				e.printStackTrace();
				retval = false;
			}
		}
		return retval;
	}
	
	public boolean saveHourlyWeather(String directory, Model model) {
		boolean retval = true;
		for (LocationWeather loc : locations) {
			File fl = new File(directory);
			if (!fl.exists()) {
				if (!fl.mkdirs())
					return false;
			}
			try {
				if (!loc.saveHourlyData(fl.getAbsolutePath(), model))
					retval = false;
			}
			catch (IOException e) {
				e.printStackTrace();
				retval = false;
			}
		}
		return retval;
	}

	public TimeZoneInfo getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZoneInfo timezone) {
		this.timezone = timezone;
	}

	public void setIgnorePrecipitation(boolean ignore) {
		this.ignorePrecipitation = ignore;
	}

	public void setBasePath(String url){
		if(!url.isEmpty())
			basePath = url;
	}
	
	public static void setSaveDir(String dir) {
		saveDir = dir;
	}
	
	public static String getSaveDir() {
		return saveDir;
	}
}
