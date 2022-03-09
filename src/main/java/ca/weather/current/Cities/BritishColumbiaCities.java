/***********************************************************************
 * REDapp - BritishColumbiaCities.java
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

public enum BritishColumbiaCities implements Cities {
	HundredMileHouse("100 Mile House", "BC-7"),
	Abbotsford("Abbotsford", "BC-81"),
	Agassiz("Agassiz", "BC-70"),
	Atlin("Atlin", "BC-67"),
	BellaBella("Bella Bella", "BC-94"),
	BellaCoola("Bella Coola", "BC-18"),
	BlueRiver("Blue River", "BC-22"),
	BurnsLake("Burns Lake", "BC-43"),
	CacheCreek("Cache Creek", "BC-55"),
	CampbellRiver("Campbell River", "BC-19"),
	Cassiar("Cassiar", "BC-6"),
	Castlegar("Castlegar", "BC-21"),
	Chetwynd("Chetwynd", "BC-23"),
	Chilliwack("Chilliwack", "BC-24"),
	Clearwater("Clearwater", "BC-12"),
	Clinton("Clinton", "BC-91"),
	Comox("Comox", "BC-61"),
	Courtenay("Courtenay", "BC-92"),
	Cranbrook("Cranbrook", "BC-77"),
	Creston("Creston", "BC-26"),
	CumminsLakes_ProvincialPark("Cummins Lakes (Provincial Park)", "BC-95"),
	DawsonCreek("Dawson Creek", "BC-25"),
	DeaseLake("Dease Lake", "BC-14"),
	DomeCreek("Dome Creek", "BC-8"),
	Esquimalt("Esquimalt", "BC-40"),
	EstevanPoint("Estevan Point", "BC-15"),
	FortNelson("Fort Nelson", "BC-83"),
	FortStJohn("Fort St. John", "BC-78"),
	Gibsons("Gibsons", "BC-1"),
	Golden("Golden", "BC-34"),
	GonzalesPoint("Gonzales Point", "BC-32"),
	GoodHopeLake("Good Hope Lake", "BC-9"),
	GrandForks("Grand Forks", "BC-39"),
	GulfIslands_Southern("Gulf Islands (Southern)", "BC-93"),
	Hope("Hope", "BC-36"),
	HopeSlide("Hope Slide", "BC-31"),
	Invermere("Invermere", "BC-72"),
	Kamloops("Kamloops", "BC-45"),
	Kelowna("Kelowna", "BC-48"),
	Kitimat("Kitimat", "BC-30"),
	Kootenay_NationalPark("Kootenay (National Park)", "BC-11"),
	LiardRiver("Liard River", "BC-87"),
	Lillooet("Lillooet", "BC-28"),
	Lytton("Lytton", "BC-33"),
	Mackenzie("Mackenzie", "BC-90"),
	Malahat("Malahat", "BC-29"),
	Masset("Masset", "BC-56"),
	McBride("McBride", "BC-47"),
	Merritt("Merritt", "BC-49"),
	MunchoLake("Muncho Lake", "BC-63"),
	Nakusp("Nakusp", "BC-38"),
	Nanaimo("Nanaimo", "BC-20"),
	Nelson("Nelson", "BC-37"),
	Osoyoos("Osoyoos", "BC-69"),
	Pemberton("Pemberton", "BC-16"),
	Penticton("Penticton", "BC-84"),
	PittMeadows("Pitt Meadows", "BC-35"),
	PortAlberni("Port Alberni", "BC-46"),
	PortHardy("Port Hardy", "BC-89"),
	PowellRiver("Powell River", "BC-58"),
	PrinceGeorge("Prince George", "BC-79"),
	PrinceRupert("Prince Rupert", "BC-57"),
	Princeton("Princeton", "BC-41"),
	PuntziMountain("Puntzi Mountain", "BC-42"),
	Quesnel("Quesnel", "BC-64"),
	Revelstoke("Revelstoke", "BC-65"),
	RockCreek("Rock Creek", "BC-10"),
	SalmonArm("Salmon Arm", "BC-51"),
	Sandspit("Sandspit", "BC-88"),
	Sechelt("Sechelt", "BC-3"),
	Smithers("Smithers", "BC-82"),
	Sparwood("Sparwood", "BC-52"),
	Squamish("Squamish", "BC-50"),
	Stewart("Stewart", "BC-73"),
	Summerland("Summerland", "BC-54"),
	TatlayokoLake("Tatlayoko Lake", "BC-60"),
	Terrace("Terrace", "BC-80"),
	TetsaRiver_ProvincialPark("Tetsa River (Provincial Park)", "BC-53"),
	Tofino("Tofino", "BC-17"),
	Trail("Trail", "BC-71"),
	Ucluelet("Ucluelet", "BC-5"),
	Valemount("Valemount", "BC-13"),
	Vancouver("Vancouver", "BC-74"),
	Vanderhoof("Vanderhoof", "BC-44"),
	Vernon("Vernon", "BC-27"),
	Victoria("Victoria", "BC-85"),
	Victoria_Hartland("Victoria (Hartland)", "BC-59"),
	Victoria_Universityof("Victoria (University of)", "BC-66"),
	VictoriaHarbour("Victoria Harbour", "BC-75"),
	Whistler("Whistler", "BC-86"),
	WhiteRock("White Rock", "BC-62"),
	WilliamsLake("Williams Lake", "BC-76"),
	Yoho_NationalPark("Yoho (National Park)", "BC-68");
	
	private String link;
	private String name;
	
	BritishColumbiaCities(String n, String l) {
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
		BritishColumbiaCities[] array = BritishColumbiaCities.values();
		for (BritishColumbiaCities city : array)
			values.add(city.name);
		return values;
	}
}
