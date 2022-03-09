/***********************************************************************
 * REDapp - OntarioCities.java
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

public enum OntarioCities implements Cities {
	Alexandria("Alexandria", "ON-122"),
	AlgonquinPark_Brent("Algonquin Park (Brent)", "ON-1"),
	AlgonquinPark_LakeofTwoRivers("Algonquin Park (Lake of Two Rivers)", "ON-29"),
	Alliston("Alliston", "ON-114"),
	Apsley("Apsley", "ON-30"),
	Armstrong("Armstrong", "ON-111"),
	Atikokan("Atikokan", "ON-148"),
	Attawapiskat("Attawapiskat", "ON-164"),
	Bancroft("Bancroft", "ON-102"),
	Barrie("Barrie", "ON-151"),
	BarrysBay("Barry's Bay", "ON-133"),
	Belleville("Belleville", "ON-3"),
	BigTroutLake("Big Trout Lake", "ON-124"),
	BlindRiver("Blind River", "ON-21"),
	Bracebridge("Bracebridge", "ON-9"),
	Brampton("Brampton", "ON-4"),
	Brantford("Brantford", "ON-86"),
	Brockville("Brockville", "ON-8"),
	BurksFalls("Burk's Falls", "ON-31"),
	Burlington("Burlington", "ON-95"),
	Caledon("Caledon", "ON-32"),
	Cambridge("Cambridge", "ON-81"),
	Chapleau("Chapleau", "ON-91"),
	ChathamKent("Chatham-Kent", "ON-11"),
	Cobourg("Cobourg", "ON-75"),
	Cochrane("Cochrane", "ON-33"),
	Collingwood("Collingwood", "ON-150"),
	Cornwall("Cornwall", "ON-152"),
	DeepRiver("Deep River", "ON-83"),
	Dorion("Dorion", "ON-34"),
	Dryden("Dryden", "ON-72"),
	Dunchurch("Dunchurch", "ON-35"),
	Dundalk("Dundalk", "ON-36"),
	EarFalls("Ear Falls", "ON-167"),
	Earlton("Earlton", "ON-136"),
	ElliotLake("Elliot Lake", "ON-170"),
	FortAlbany("Fort Albany", "ON-173"),
	FortErie("Fort Erie", "ON-12"),
	FortFrances("Fort Frances", "ON-159"),
	FortSevern("Fort Severn", "ON-171"),
	Gananoque("Gananoque", "ON-90"),
	Goderich("Goderich", "ON-160"),
	Gogama("Gogama", "ON-37"),
	GoreBay("Gore Bay", "ON-144"),
	Gravenhurst("Gravenhurst", "ON-38"),
	GreaterNapanee("Greater Napanee", "ON-39"),
	GreaterSudbury("Greater Sudbury", "ON-40"),
	Greenstone_Beardmore("Greenstone (Beardmore)", "ON-153"),
	Greenstone_Geraldton("Greenstone (Geraldton)", "ON-70"),
	Greenstone_Nakina("Greenstone (Nakina)", "ON-97"),
	Guelph("Guelph", "ON-5"),
	GullBay("Gull Bay", "ON-41"),
	HaldimandCounty("Haldimand County", "ON-42"),
	Haliburton("Haliburton", "ON-165"),
	HaltonHills("Halton Hills", "ON-68"),
	Hamilton("Hamilton", "ON-77"),
	Hawkesbury("Hawkesbury", "ON-71"),
	Hearst("Hearst", "ON-73"),
	Hornepayne("Hornepayne", "ON-78"),
	Huntsville("Huntsville", "ON-88"),
	Ignace("Ignace", "ON-156"),
	KakabekaFalls("Kakabeka Falls", "ON-145"),
	Kaladar("Kaladar", "ON-43"),
	Kapuskasing("Kapuskasing", "ON-142"),
	KawarthaLakes_FenelonFalls("Kawartha Lakes (Fenelon Falls)", "ON-44"),
	KawarthaLakes_Lindsay("Kawartha Lakes (Lindsay)", "ON-168"),
	Kemptville("Kemptville", "ON-74"),
	Kenora("Kenora", "ON-96"),
	Killarney("Killarney", "ON-146"),
	Kincardine("Kincardine", "ON-28"),
	Kingston("Kingston", "ON-69"),
	KirklandLake("Kirkland Lake", "ON-76"),
	KitchenerWaterloo("Kitchener-Waterloo", "ON-82"),
	LakeSuperior_ProvincialPark("Lake Superior (Provincial Park)", "ON-45"),
	LambtonShores("Lambton Shores", "ON-46"),
	LansdowneHouse("Lansdowne House", "ON-87"),
	Leamington("Leamington", "ON-23"),
	Lincoln("Lincoln", "ON-47"),
	London("London", "ON-137"),
	Marathon("Marathon", "ON-108"),
	Markham("Markham", "ON-85"),
	Midland("Midland", "ON-169"),
	MineCentre("Mine Centre", "ON-48"),
	Mississauga("Mississauga", "ON-24"),
	MontrealRiverHarbour("Montreal River Harbour", "ON-163"),
	Moosonee("Moosonee", "ON-113"),
	Morrisburg("Morrisburg", "ON-92"),
	MountForest("Mount Forest", "ON-89"),
	Muskoka("Muskoka", "ON-93"),
	NewTecumseth("New Tecumseth", "ON-49"),
	Newmarket("Newmarket", "ON-25"),
	NiagaraFalls("Niagara Falls", "ON-125"),
	Nipigon("Nipigon", "ON-26"),
	Norfolk("Norfolk", "ON-50"),
	NorthBay("North Bay", "ON-139"),
	NorthPerth("North Perth", "ON-51"),
	Oakville("Oakville", "ON-79"),
	Ogoki("Ogoki", "ON-101"),
	Orangeville("Orangeville", "ON-140"),
	Orillia("Orillia", "ON-13"),
	Oshawa("Oshawa", "ON-117"),
	Ottawa_KanataOrleans("Ottawa (Kanata - Orl�ans)", "ON-118"),
	Ottawa_RichmondMetcalfe("Ottawa (Richmond - Metcalfe)", "ON-52"),
	OwenSound("Owen Sound", "ON-7"),
	OxtongueLake("Oxtongue Lake", "ON-53"),
	ParrySound("Parry Sound", "ON-103"),
	Peawanuck("Peawanuck", "ON-99"),
	Pembroke("Pembroke", "ON-131"),
	Petawawa("Petawawa", "ON-112"),
	Peterborough("Peterborough", "ON-121"),
	Pickering("Pickering", "ON-54"),
	PickleLake("Pickle Lake", "ON-120"),
	Pikangikum("Pikangikum", "ON-55"),
	PortCarling("Port Carling", "ON-56"),
	PortColborne("Port Colborne", "ON-80"),
	PortElgin("Port Elgin", "ON-19"),
	PortPerry("Port Perry", "ON-149"),
	PrinceEdward_Picton("Prince Edward (Picton)", "ON-27"),
	QuinteWest("Quinte West", "ON-57"),
	RedLake("Red Lake", "ON-104"),
	Renfrew("Renfrew", "ON-58"),
	RichmondHill("Richmond Hill", "ON-59"),
	Rodney("Rodney", "ON-172"),
	Rondeau_ProvincialPark("Rondeau (Provincial Park)", "ON-141"),
	SachigoLake("Sachigo Lake", "ON-105"),
	SandyLake("Sandy Lake", "ON-129"),
	Sarnia("Sarnia", "ON-147"),
	SaugeenShores("Saugeen Shores", "ON-60"),
	SaultSteMarie("Sault Ste. Marie", "ON-162"),
	SavantLake("Savant Lake", "ON-134"),
	SharbotLake("Sharbot Lake", "ON-61"),
	Shelburne("Shelburne", "ON-84"),
	Simcoe("Simcoe", "ON-161"),
	SiouxLookout("Sioux Lookout", "ON-135"),
	SiouxNarrows("Sioux Narrows", "ON-166"),
	SmithsFalls("Smiths Falls", "ON-106"),
	SouthBrucePeninsula("South Bruce Peninsula", "ON-62"),
	StCatharines("St. Catharines", "ON-107"),
	StThomas("St. Thomas", "ON-98"),
	Stirling("Stirling", "ON-115"),
	Stratford("Stratford", "ON-116"),
	Strathroy("Strathroy", "ON-18"),
	Sudbury_Greater("Sudbury (Greater)", "ON-174"),
	Sydenham("Sydenham", "ON-63"),
	TemiskamingShores("Temiskaming Shores", "ON-22"),
	TerraceBay("Terrace Bay", "ON-123"),
	ThunderBay("Thunder Bay", "ON-100"),
	Tillsonburg("Tillsonburg", "ON-17"),
	Timmins("Timmins", "ON-127"),
	Tobermory("Tobermory", "ON-157"),
	Toronto("Toronto", "ON-143"),
	TorontoIsland("Toronto Island", "ON-128"),
	Trenton("Trenton", "ON-126"),
	Upsala("Upsala", "ON-154"),
	Vaughan("Vaughan", "ON-64"),
	Vineland("Vineland", "ON-109"),
	Walkerton("Walkerton", "ON-16"),
	Wawa("Wawa", "ON-138"),
	Webequie("Webequie", "ON-132"),
	Welland("Welland", "ON-14"),
	WestNipissing("West Nipissing", "ON-65"),
	Westport("Westport", "ON-66"),
	Whitby("Whitby", "ON-119"),
	WhiteRiver("White River", "ON-67"),
	Wiarton("Wiarton", "ON-130"),
	Winchester("Winchester", "ON-155"),
	Windsor("Windsor", "ON-94"),
	Wingham("Wingham", "ON-110"),
	Woodstock("Woodstock", "ON-15"),
	WunnumminLake("Wunnummin Lake", "ON-158");
	
	private String link;
	private String name;
	
	OntarioCities(String n, String l) {
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
		OntarioCities[] array = OntarioCities.values();
		for (OntarioCities city : array)
			values.add(city.name);
		return values;
	}
	
}
