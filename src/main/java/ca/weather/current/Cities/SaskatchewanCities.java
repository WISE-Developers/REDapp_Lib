/***********************************************************************
 * REDapp - SaskatchewanCities.java
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

public enum SaskatchewanCities implements Cities {
	Assiniboia("Assiniboia", "SK-51"),
	Biggar("Biggar", "SK-2"),
	Broadview("Broadview", "SK-48"),
	BuffaloNarrows("Buffalo Narrows", "SK-39"),
	Canora("Canora", "SK-3"),
	Carlyle("Carlyle", "SK-4"),
	CollinsBay("Collins Bay", "SK-30"),
	Coronach("Coronach", "SK-42"),
	CypressHills_ProvincialPark("Cypress Hills (Provincial Park)", "SK-29"),
	Eastend("Eastend", "SK-45"),
	Elbow("Elbow", "SK-18"),
	Estevan("Estevan", "SK-53"),
	FortQuAppelle("Fort Qu'Appelle", "SK-5"),
	HudsonBay("Hudson Bay", "SK-17"),
	Humboldt("Humboldt", "SK-6"),
	IndianHead("Indian Head", "SK-43"),
	Kamsack("Kamsack", "SK-7"),
	KeyLake("Key Lake", "SK-20"),
	Kindersley("Kindersley", "SK-21"),
	LaLoche("La Loche", "SK-13"),
	LaRonge("La Ronge", "SK-38"),
	LastMountainLake_Sanctuary("Last Mountain Lake (Sanctuary)", "SK-35"),
	Leader("Leader", "SK-52"),
	Lloydminster("Lloydminster", "SK-56"),
	LuckyLake("Lucky Lake", "SK-14"),
	MapleCreek("Maple Creek", "SK-16"),
	MeadowLake("Meadow Lake", "SK-22"),
	Melfort("Melfort", "SK-46"),
	Melville("Melville", "SK-8"),
	MooseJaw("Moose Jaw", "SK-24"),
	Moosomin("Moosomin", "SK-9"),
	Nipawin("Nipawin", "SK-47"),
	NorthBattleford("North Battleford", "SK-34"),
	Outlook("Outlook", "SK-37"),
	Oxbow("Oxbow", "SK-10"),
	PelicanNarrows("Pelican Narrows", "SK-55"),
	PrinceAlbert("Prince Albert", "SK-27"),
	Regina("Regina", "SK-32"),
	Rockglen("Rockglen", "SK-54"),
	Rosetown("Rosetown", "SK-23"),
	Rosthern("Rosthern", "SK-1"),
	Saskatoon("Saskatoon", "SK-40"),
	Scott("Scott", "SK-26"),
	Shaunavon("Shaunavon", "SK-11"),
	SouthendReindeer("Southend Reindeer", "SK-50"),
	Spiritwood("Spiritwood", "SK-25"),
	StonyRapids("Stony Rapids", "SK-36"),
	SwiftCurrent("Swift Current", "SK-41"),
	Tisdale("Tisdale", "SK-12"),
	UraniumCity("Uranium City", "SK-44"),
	ValMarie("Val Marie", "SK-28"),
	WaskesiuLake("Waskesiu Lake", "SK-15"),
	Watrous("Watrous", "SK-49"),
	Weyburn("Weyburn", "SK-31"),
	Wynyard("Wynyard", "SK-19"),
	Yorkton("Yorkton", "SK-33");
	
	private String link;
	private String name;
	
	SaskatchewanCities(String n, String l) {
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
		SaskatchewanCities[] array = SaskatchewanCities.values();
		for (SaskatchewanCities city : array)
			values.add(city.name);
		return values;
	}
}
