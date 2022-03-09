/***********************************************************************
 * REDapp - NovaScotiaCities.java
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

public enum NovaScotiaCities implements Cities {
	Amherst("Amherst", "NS-33"),
	AnnapolisRoyal("Annapolis Royal", "NS-2"),
	Antigonish("Antigonish", "NS-3"),
	BaccaroPoint("Baccaro Point", "NS-37"),
	Baddeck("Baddeck", "NS-4"),
	BeaverIsland("Beaver Island", "NS-36"),
	Bridgetown("Bridgetown", "NS-5"),
	Bridgewater("Bridgewater", "NS-6"),
	BrierIsland("Brier Island", "NS-27"),
	CapeGeorge("Cape George", "NS-41"),
	Caribou("Caribou", "NS-34"),
	Cheticamp("Ch�ticamp", "NS-38"),
	Digby("Digby", "NS-20"),
	Economy("Economy", "NS-7"),
	FourchuHead("Fourchu Head", "NS-24"),
	GrandEtang("Grand �tang", "NS-32"),
	Greenwood("Greenwood", "NS-35"),
	Guysborough("Guysborough", "NS-8"),
	Halifax("Halifax", "NS-19"),
	Halifax_Shearwater("Halifax (Shearwater)", "NS-40"),
	HartIsland("Hart Island", "NS-23"),
	Ingonish("Ingonish", "NS-16"),
	Kejimkujik_NationalPark("Kejimkujik (National Park)", "NS-42"),
	Kentville("Kentville", "NS-17"),
	Liverpool("Liverpool", "NS-39"),
	Lunenburg("Lunenburg", "NS-21"),
	MalayFalls("Malay Falls", "NS-22"),
	NewGlasgow("New Glasgow", "NS-1"),
	NorthEastMargaree("North East Margaree", "NS-18"),
	NorthMountain_CapeBreton("North Mountain (Cape Breton)", "NS-43"),
	Parrsboro("Parrsboro", "NS-13"),
	PortHawkesbury("Port Hawkesbury", "NS-26"),
	SheetHarbour("Sheet Harbour", "NS-10"),
	Shelburne("Shelburne", "NS-11"),
	StPeters("St. Peter's", "NS-12"),
	Sydney("Sydney", "NS-31"),
	Tatamagouche("Tatamagouche", "NS-14"),
	Tracadie("Tracadie", "NS-28"),
	Truro("Truro", "NS-25"),
	WesternHead("Western Head", "NS-30"),
	Windsor("Windsor", "NS-15"),
	Yarmouth("Yarmouth", "NS-29");
	
	private String link;
	private String name;
	
	NovaScotiaCities(String n, String l) {
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
		NovaScotiaCities[] array = NovaScotiaCities.values();
		for (NovaScotiaCities city : array)
			values.add(city.name);
		return values;
	}
}
