/***********************************************************************
 * REDapp - PrinceEdwardIslandCities.java
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

package ca.weather.current.Cities;

import java.util.ArrayList;

public enum PrinceEdwardIslandCities implements Cities {
	Charlottetown("Charlottetown", "PE-5"),
	EastPoint("East Point", "PE-6"),
	MaplePlains("Maple Plains", "PE-2"),
	NorthCape("North Cape", "PE-1"),
	StPetersBay("St. Peters Bay", "PE-4"),
	Summerside("Summerside", "PE-3");
	
	private String link;
	private String name;
	
	PrinceEdwardIslandCities(String n, String l) {
		link = l;
		name = n;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getName() {
		return name;
	}
	public static ArrayList<String> valuesAsStrings(){
		ArrayList<String> values = new ArrayList<String>();
		PrinceEdwardIslandCities[] array = PrinceEdwardIslandCities.values();
		for (PrinceEdwardIslandCities city : array)
			values.add(city.name);
		return values;
	}
}
