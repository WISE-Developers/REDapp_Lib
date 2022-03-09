/***********************************************************************
 * REDapp - NorthwestTerritoriesCities.java
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

public enum NorthwestTerritoriesCities implements Cities {
	Aklavik("Aklavik", "NT-13"),
	ColvilleLake("Colville Lake", "NT-26"),
	Deline("Deline", "NT-22"),
	Detah("Detah", "NT-1"),
	Ekati_LacdeGras("Ekati (Lac de Gras)", "NT-14"),
	Enterprise("Enterprise", "NT-2"),
	FortGoodHope("Fort Good Hope", "NT-5"),
	FortLiard("Fort Liard", "NT-29"),
	FortMcPherson("Fort McPherson", "NT-10"),
	FortProvidence("Fort Providence", "NT-27"),
	FortResolution("Fort Resolution", "NT-3"),
	FortSimpson("Fort Simpson", "NT-4"),
	FortSmith("Fort Smith", "NT-17"),
	Gameti("Gameti", "NT-18"),
	HayRiver("Hay River", "NT-8"),
	IndinRiver("Indin River", "NT-28"),
	Inuvik("Inuvik", "NT-30"),
	Lutselke("Lutselke", "NT-31"),
	NahanniButte("Nahanni Butte", "NT-12"),
	NormanWells("Norman Wells", "NT-21"),
	Paulatuk("Paulatuk", "NT-16"),
	SachsHarbour("Sachs Harbour", "NT-19"),
	TroutLake("Trout Lake", "NT-15"),
	Tuktoyaktuk("Tuktoyaktuk", "NT-20"),
	Tulita("Tulita", "NT-11"),
	Ulukhaktok("Ulukhaktok", "NT-7"),
	Wekweeti("Wekweeti", "NT-9"),
	Whati("Whati", "NT-6"),
	Wrigley("Wrigley", "NT-23"),
	Yellowknife("Yellowknife", "NT-24");
	
	private String link;
	private String name;
	
	NorthwestTerritoriesCities(String n, String l) {
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
		NorthwestTerritoriesCities[] array = NorthwestTerritoriesCities.values();
		for (NorthwestTerritoriesCities city : array)
			values.add(city.name);
		return values;
	}
	
}

