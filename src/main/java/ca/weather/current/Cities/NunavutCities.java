/***********************************************************************
 * REDapp - NunavutCities.java
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

public enum NunavutCities implements Cities {
	Alert("Alert", "NU-22"),
	ArcticBay("Arctic Bay", "NU-10"),
	Arviat("Arviat", "NU-20"),
	BakerLake("Baker Lake", "NU-14"),
	CambridgeBay("Cambridge Bay", "NU-15"),
	CapeDorset("Cape Dorset", "NU-2"),
	Chesterfield("Chesterfield", "NU-17"),
	ClydeRiver("Clyde River", "NU-18"),
	CoralHarbour("Coral Harbour", "NU-9"),
	Ennadai("Ennadai", "NU-19"),
	Eureka("Eureka", "NU-11"),
	GjoaHaven("Gjoa Haven", "NU-24"),
	GriseFiord("Grise Fiord", "NU-12"),
	HallBeach("Hall Beach", "NU-4"),
	Igloolik("Igloolik", "NU-23"),
	Iqaluit("Iqaluit", "NU-21"),
	Kimmirut("Kimmirut", "NU-26"),
	Kugaaruk("Kugaaruk", "NU-13"),
	Kugluktuk("Kugluktuk", "NU-16"),
	Nanisivik("Nanisivik", "NU-1"),
	Pangnirtung("Pangnirtung", "NU-7"),
	PondInlet("Pond Inlet", "NU-25"),
	Qikiqtarjuaq("Qikiqtarjuaq", "NU-5"),
	RankinInlet("Rankin Inlet", "NU-28"),
	RepulseBay("Repulse Bay", "NU-3"),
	Resolute("Resolute", "NU-27"),
	Sanikiluaq("Sanikiluaq", "NU-29"),
	Taloyoak("Taloyoak", "NU-8"),
	WhaleCove("Whale Cove", "NU-6");
	
	private String link;
	private String name;
	
	NunavutCities(String n, String l) {
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
		NunavutCities[] array = NunavutCities.values();
		for (NunavutCities city : array)
			values.add(city.name);
		return values;
	}
}
