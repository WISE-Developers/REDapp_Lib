/***********************************************************************
 * REDapp - NewfoundLandLabradorCities.java
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

public enum NewfoundLandLabradorCities implements Cities {
	Badger("Badger", "NL-34"),
	BayRoberts("Bay Roberts", "NL-43"),
	Bonavista("Bonavista", "NL-14"),
	Buchans("Buchans", "NL-4"),
	Burgeo("Burgeo", "NL-31"),
	Campbellton("Campbellton", "NL-2"),
	CapeRace("Cape Race", "NL-28"),
	Cartwright("Cartwright", "NL-32"),
	CartwrightJunction_TransLabradorHwy("Cartwright Junction (Trans-Labrador Hwy)", "NL-47"),
	ChannelPortauxBasques("Channel - Port aux Basques", "NL-17"),
	ChurchillFalls("Churchill Falls", "NL-21"),
	Clarenville("Clarenville", "NL-1"),
	CornerBrook("Corner Brook", "NL-41"),
	DanielsHarbour("Daniel's Harbour", "NL-33"),
	DeerLake("Deer Lake", "NL-39"),
	Englee("Englee", "NL-44"),
	Gander("Gander", "NL-16"),
	GrandBank("Grand Bank", "NL-5"),
	GrandFallsWindsor("Grand Falls-Windsor", "NL-6"),
	GrosMorne("Gros Morne", "NL-7"),
	GullIslandRapids_TransLabradorHwy("Gull Island Rapids (Trans-Labrador Hwy)", "NL-48"),
	HappyValleyGooseBay("Happy Valley-Goose Bay", "NL-23"),
	Hopedale("Hopedale", "NL-38"),
	LAnseauLoup("L'Anse-au-Loup", "NL-45"),
	LaScie("La Scie", "NL-22"),
	LabradorCity("Labrador City", "NL-20"),
	Lewisporte("Lewisporte", "NL-9"),
	Makkovik("Makkovik", "NL-25"),
	MarbleMountain("Marble Mountain", "NL-26"),
	MarysHarbour("Mary's Harbour", "NL-29"),
	Marystown("Marystown", "NL-3"),
	MusgraveHarbour("Musgrave Harbour", "NL-10"),
	Nain("Nain", "NL-40"),
	NewWesValley("New-Wes-Valley", "NL-42"),
	Placentia("Placentia", "NL-30"),
	PortauChoix("Port au Choix", "NL-11"),
	Rigolet("Rigolet", "NL-46"),
	RockyHarbour("Rocky Harbour", "NL-13"),
	StAlbans("St Alban's", "NL-8"),
	StAnthony("St. Anthony", "NL-37"),
	StJohns("St. John's", "NL-24"),
	StLawrence("St. Lawrence", "NL-36"),
	Stephenville("Stephenville", "NL-27"),
	TerraNova_NationalPark("Terra Nova (National Park)", "NL-15"),
	Twillingate("Twillingate", "NL-35"),
	WabushLake("Wabush Lake", "NL-12"),
	Winterland("Winterland", "NL-19"),
	Wreckhouse("Wreckhouse", "NL-18");
	
	private String link;
	private String name;
	
	NewfoundLandLabradorCities(String n, String l) {
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
		NewfoundLandLabradorCities[] array = NewfoundLandLabradorCities.values();
		for (NewfoundLandLabradorCities city : array)
			values.add(city.name);
		return values;
	}
}
