/***********************************************************************
 * REDapp - NewBrunswickCities.java
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

public enum NewBrunswickCities implements Cities {
	BasCaraquet("Bas-Caraquet", "NB-22"),
	Bathurst("Bathurst", "NB-28"),
	Bouctouche("Bouctouche", "NB-1"),
	Campbellton("Campbellton", "NB-2"),
	Charlo("Charlo", "NB-30"),
	Chipman("Chipman", "NB-3"),
	Dalhousie("Dalhousie", "NB-26"),
	Doaktown("Doaktown", "NB-4"),
	Edmundston("Edmundston", "NB-32"),
	Fredericton("Fredericton", "NB-29"),
	Fundy_NationalPark("Fundy (National Park)", "NB-5"),
	GrandFalls("Grand Falls", "NB-6"),
	GrandManan("Grand Manan", "NB-27"),
	Hopewell("Hopewell", "NB-8"),
	Kouchibouguac("Kouchibouguac", "NB-9"),
	Miramichi("Miramichi", "NB-25"),
	MiscouIsland("Miscou Island", "NB-31"),
	Moncton("Moncton", "NB-36"),
	MountCarleton_ProvincialPark("Mount Carleton (Provincial Park)", "NB-10"),
	Oromocto("Oromocto", "NB-11"),
	PointEscuminac("Point Escuminac", "NB-34"),
	PointLepreau("Point Lepreau", "NB-33"),
	Quispamsis("Quispamsis", "NB-12"),
	Richibucto("Richibucto", "NB-13"),
	Rogersville("Rogersville", "NB-14"),
	Sackville("Sackville", "NB-15"),
	SaintAndrews("Saint Andrews", "NB-18"),
	SaintJohn("Saint John", "NB-23"),
	SaintQuentin("Saint-Quentin", "NB-16"),
	Shediac("Shediac", "NB-17"),
	StLeonard("St. Leonard", "NB-24"),
	StStephen("St. Stephen", "NB-35"),
	Sussex("Sussex", "NB-19"),
	TracadieSheila("Tracadie-Sheila", "NB-20"),
	Woodstock("Woodstock", "NB-21");
	
	private String link;
	private String name;
	
	NewBrunswickCities(String n, String l) {
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
		NewBrunswickCities[] array = NewBrunswickCities.values();
		for (NewBrunswickCities city : array)
			values.add(city.name);
		return values;
	}
	
}
