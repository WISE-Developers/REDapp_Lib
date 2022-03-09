/***********************************************************************
 * REDapp - Time.java
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

package ca.weather.forecast;

/**
 * The possible forecast times for weather from Environment Canada.
 * 
 * @author Travis Redpath
 *
 */
public enum Time {
	MIDNIGHT("00", 0),
	NOON("12", 12);
	
	public final String text;
	public final int time;
	
	private Time(String text, int time) {
		this.text = text;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public static Time fromString(String value) {
		for (Time t : Time.values())
			if (t.toString().equals(value))
				return t;
		for (Time t : Time.values())
			if ((t.toString() + "Z").equals(value))
				return t;
		return MIDNIGHT;
	}
}
