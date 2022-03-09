/***********************************************************************
 * REDapp - AlbertaCities.java
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

public enum AlbertaCities implements Cities {
	Airdrie("Airdrie", "AB-12"),
	Athabasca("Athabasca", "AB-10"),
	Banff("Banff", "AB-49"),
	Barrhead("Barrhead", "AB-2"),
	Beaverlodge("Beaverlodge", "AB-57"),
	BowIsland("Bow Island", "AB-43"),
	BowValley_ProvincialPark("Bow Valley (Provincial Park)", "AB-42"),
	Breton("Breton", "AB-36"),
	Brooks("Brooks", "AB-58"),
	Calgary("Calgary", "AB-52"),
	Calgary_OlympicPark("Calgary (Olympic Park)", "AB-61"),
	Camrose("Camrose", "AB-18"),
	Canmore("Canmore", "AB-3"),
	Cardston("Cardston", "AB-64"),
	Claresholm("Claresholm", "AB-60"),
	Cochrane("Cochrane", "AB-1"),
	ColdLake("Cold Lake", "AB-23"),
	Coronation("Coronation", "AB-59"),
	Crowsnest("Crowsnest", "AB-17"),
	DraytonValley("Drayton Valley", "AB-48"),
	Drumheller("Drumheller", "AB-62"),
	Edmonton("Edmonton", "AB-50"),
	Edmonton_IntlAprt("Edmonton (Int'l Aprt)", "AB-71"),
	Edson("Edson", "AB-72"),
	ElkIsland_NationalPark("Elk Island (National Park)", "AB-63"),
	Esther("Esther", "AB-67"),
	FortChipewyan("Fort Chipewyan", "AB-28"),
	FortMcMurray("Fort McMurray", "AB-20"),
	GardenCreek("Garden Creek", "AB-65"),
	GrandeCache("Grande Cache", "AB-45"),
	GrandePrairie("Grande Prairie", "AB-31"),
	HendricksonCreek("Hendrickson Creek", "AB-37"),
	HighLevel("High Level", "AB-24"),
	HighRiver("High River", "AB-4"),
	Highvale("Highvale", "AB-68"),
	Hinton("Hinton", "AB-14"),
	Jasper("Jasper", "AB-70"),
	Kananaskis_NakiskaRidgetop("Kananaskis (Nakiska Ridgetop)", "AB-34"),
	LacLaBiche("Lac La Biche", "AB-32"),
	Lacombe("Lacombe", "AB-39"),
	Lethbridge("Lethbridge", "AB-30"),
	Lloydminster("Lloydminster", "AB-15"),
	MedicineHat("Medicine Hat", "AB-51"),
	MildredLake("Mildred Lake", "AB-33"),
	MilkRiver("Milk River", "AB-19"),
	Nordegg("Nordegg", "AB-38"),
	Okotoks("Okotoks", "AB-11"),
	Onefour("Onefour", "AB-35"),
	PeaceRiver("Peace River", "AB-25"),
	PincherCreek("Pincher Creek", "AB-46"),
	RainbowLake("Rainbow Lake", "AB-21"),
	RedDeer("Red Deer", "AB-29"),
	RedEarthCreek("Red Earth Creek", "AB-40"),
	RockyMountainHouse("Rocky Mountain House", "AB-16"),
	SlaveLake("Slave Lake", "AB-54"),
	Stavely("Stavely", "AB-44"),
	Stettler("Stettler", "AB-5"),
	StonyPlain("Stony Plain", "AB-22"),
	Strathmore("Strathmore", "AB-6"),
	Suffield("Suffield", "AB-47"),
	Sundre("Sundre", "AB-53"),
	Taber("Taber", "AB-7"),
	ThreeHills("Three Hills", "AB-69"),
	Vauxhall("Vauxhall", "AB-27"),
	Vegreville("Vegreville", "AB-26"),
	Wainwright("Wainwright", "AB-55"),
	WatertonPark("Waterton Park", "AB-66"),
	Westlock("Westlock", "AB-8"),
	Wetaskiwin("Wetaskiwin", "AB-9"),
	Whitecourt("Whitecourt", "AB-56"),
	WillowCreek_ProvincialPark("Willow Creek (Provincial Park)", "AB-41");
	
	private String link;
	private String name;
	
	AlbertaCities(String n, String l) {
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
		AlbertaCities[] array = AlbertaCities.values();
		for (AlbertaCities city : array)
			values.add(city.name);
		return values;
	}
}
