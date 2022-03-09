/***********************************************************************
 * REDapp - ManitobaCities.java
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

public enum ManitobaCities implements Cities {
	Altona("Altona", "MB-3"),
	Arnes("Arnes", "MB-21"),
	BachelorsIsland("Bachelors Island", "MB-43"),
	BerensRiver("Berens River", "MB-54"),
	Bissett("Bissett", "MB-33"),
	Bloodvein("Bloodvein", "MB-37"),
	Brandon("Brandon", "MB-52"),
	Brochet("Brochet", "MB-53"),
	Carberry("Carberry", "MB-35"),
	Carman("Carman", "MB-65"),
	Churchill("Churchill", "MB-42"),
	Dauphin("Dauphin", "MB-58"),
	Deerwood("Deerwood", "MB-49"),
	Delta("Delta", "MB-40"),
	DominionCity("Dominion City", "MB-2"),
	Emerson("Emerson", "MB-47"),
	FisherBranch("Fisher Branch", "MB-24"),
	FlinFlon("Flin Flon", "MB-60"),
	Gillam("Gillam", "MB-64"),
	Gimli("Gimli", "MB-62"),
	GodsLake("Gods Lake", "MB-63"),
	GrandBeach("Grand Beach", "MB-9"),
	GrandRapids("Grand Rapids", "MB-57"),
	Gretna("Gretna", "MB-48"),
	HuntersPoint("Hunters Point", "MB-50"),
	IslandLake("Island Lake", "MB-59"),
	LeafRapids("Leaf Rapids", "MB-22"),
	LittleGrandRapids("Little Grand Rapids", "MB-18"),
	LynnLake("Lynn Lake", "MB-41"),
	McCreary("McCreary", "MB-15"),
	Melita("Melita", "MB-45"),
	Minnedosa("Minnedosa", "MB-10"),
	Morden("Morden", "MB-17"),
	Morris("Morris", "MB-11"),
	NorwayHouse("Norway House", "MB-25"),
	OakPoint("Oak Point", "MB-14"),
	OxfordHouse("Oxford House", "MB-27"),
	PilotMound("Pilot Mound", "MB-19"),
	Pinawa("Pinawa", "MB-44"),
	PineFalls("Pine Falls", "MB-4"),
	PoplarPiver("Poplar River", "MB-5"),
	PortagelaPrairie("Portage la Prairie", "MB-29"),
	Pukatawagan("Pukatawagan", "MB-67"),
	Richer("Richer", "MB-6"),
	Roblin("Roblin", "MB-32"),
	Shamattawa("Shamattawa", "MB-39"),
	Shilo("Shilo", "MB-61"),
	ShoalLake("Shoal Lake", "MB-28"),
	SnowLake("Snow Lake", "MB-12"),
	Souris("Souris", "MB-16"),
	Sprague("Sprague", "MB-23"),
	Steinbach("Steinbach", "MB-13"),
	SwanRiver("Swan River", "MB-46"),
	TadouleLake("Tadoule Lake", "MB-51"),
	ThePas("The Pas", "MB-30"),
	Thompson("Thompson", "MB-34"),
	TurtleMountain_ProvincialPark("Turtle Mountain (Provincial Park)", "MB-66"),
	VictoriaBeach("Victoria Beach", "MB-55"),
	Virden("Virden", "MB-20"),
	Vita("Vita", "MB-7"),
	Wasagaming("Wasagaming", "MB-31"),
	Whiteshell("Whiteshell", "MB-8"),
	Winkler("Winkler", "MB-26"),
	Winnipeg("Winnipeg", "MB-38"),
	Winnipeg_TheForks("Winnipeg (The Forks)", "MB-36"),
	YorkFactory("York Factory", "MB-56");
	
	private String link;
	private String name;
	
	ManitobaCities(String n, String l) {
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
		ManitobaCities[] array = ManitobaCities.values();
		for (ManitobaCities city : array)
			values.add(city.name);
		return values;
	}
}
