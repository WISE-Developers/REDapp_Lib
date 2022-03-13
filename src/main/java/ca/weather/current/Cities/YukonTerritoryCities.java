/***********************************************************************
 * REDapp - YukonTerritoryCities.java
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

public enum YukonTerritoryCities implements Cities {
	BeaverCreek("Beaver Creek", "YT-15"),
	BurwashLanding("Burwash Landing", "YT-7"),
	Carcross("Carcross", "YT-2"),
	Carmacks("Carmacks", "YT-17"),
	Dawson("Dawson", "YT-6"),
	Dempster_Highway("Dempster (Highway)", "YT-4"),
	Faro("Faro", "YT-12"),
	HainesJunction("Haines Junction", "YT-5"),
	KluaneLake("Kluane Lake", "YT-1"),
	Mayo("Mayo", "YT-10"),
	OldCrow("Old Crow", "YT-11"),
	Rancheria("Rancheria", "YT-3"),
	RockRiver("Rock River", "YT-9"),
	RossRiver("Ross River", "YT-8"),
	Teslin("Teslin", "YT-14"),
	WatsonLake("Watson Lake", "YT-13"),
	Whitehorse("Whitehorse", "YT-16");
	
	private String link;
	private String name;
	
	YukonTerritoryCities(String n, String l) {
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
		YukonTerritoryCities[] array = YukonTerritoryCities.values();
		for (YukonTerritoryCities city : array)
			values.add(city.name);
		return values;
	}
	
}
