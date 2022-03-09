/***********************************************************************
 * REDapp - QuebecCities.java
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

public enum QuebecCities implements Cities {
	Akulivik("Akulivik", "QC-36"),
	Alma("Alma", "QC-144"),
	Amos("Amos", "QC-168"),
	Amqui("Amqui", "QC-146"),
	Asbestos("Asbestos", "QC-38"),
	Aupaluk("Aupaluk", "QC-117"),
	Bagotville("Bagotville", "QC-161"),
	BaieComeau("Baie-Comeau", "QC-160"),
	BaieJames("Baie-James", "QC-69"),
	BaieSaintPaul("Baie-Saint-Paul", "QC-165"),
	Beauceville("Beauceville", "QC-48"),
	Beauharnois("Beauharnois", "QC-49"),
	Becancour("Bécancour", "QC-50"),
	Bernieres("Bernières", "QC-51"),
	Berthierville("Berthierville", "QC-25"),
	Blainville("Blainville", "QC-52"),
	BlancSablon("Blanc-Sablon", "QC-163"),
	Boisbriand("Boisbriand", "QC-53"),
	Bonaventure("Bonaventure", "QC-54"),
	Candiac("Candiac", "QC-55"),
	CapChat("Cap Chat", "QC-120"),
	Carignan("Carignan", "QC-56"),
	CarletonsurMer("Carleton-sur-Mer", "QC-57"),
	Chambly("Chambly", "QC-58"),
	Chandler("Chandler", "QC-D4"),
	Charlevoix("Charlevoix", "QC-119"),
	Chelsea("Chelsea", "QC-59"),
	Chevery("Chevery", "QC-152"),
	Chibougamau("Chibougamau", "QC-121"),
	Coaticook("Coaticook", "QC-60"),
	Contrecoeur("Contrecoeur", "QC-46"),
	Cowansville("Cowansville", "QC-61"),
	Delson("Delson", "QC-62"),
	DeuxMontagnes("Deux-Montagnes", "QC-63"),
	DolbeauMistassini("Dolbeau-Mistassini", "QC-64"),
	Donnacona("Donnacona", "QC-65"),
	Drummondville("Drummondville", "QC-2"),
	Escoumins("Escoumins", "QC-26"),
	Farnham("Farnham", "QC-140"),
	Fermont("Fermont", "QC-29"),
	Forestville("Forestville", "QC-27"),
	Forillon("Forillon", "QC-67"),
	Forillon_NationalPark("Forillon (National Park)", "QC-95"),
	Gaspe("Gaspé", "QC-101"),
	Gaspesie_Parcnational("Gaspésie (Parc national)", "QC-94"),
	Gatineau("Gatineau", "QC-126"),
	Gouin_Reservoir("Gouin (Réservoir)", "QC-A1"),
	Granby("Granby", "QC-5"),
	GrandeVallee("Grande-Vallée", "QC-169"),
	HavreStPierre("Havre St-Pierre", "QC-104"),
	Huntingdon("Huntingdon", "QC-68"),
	IlesdelaMadeleine("Îles-de-la-Madeleine", "QC-103"),
	Inukjuak("Inukjuak", "QC-131"),
	Ivujivik("Ivujivik", "QC-112"),
	Joliette("Joliette", "QC-137"),
	Kamouraska("Kamouraska", "QC-70"),
	Kangiqsualujjuaq("Kangiqsualujjuaq", "QC-118"),
	Kangiqsujuaq("Kangiqsujuaq", "QC-155"),
	Kangirsuk("Kangirsuk", "QC-159"),
	Kuujjuaq("Kuujjuaq", "QC-150"),
	Kuujjuarapik("Kuujjuarapik", "QC-105"),
	LAssomption("L'Assomption", "QC-156"),
	LIslet("L'Islet", "QC-77"),
	LaGrandeRiviere("La Grande Rivière", "QC-158"),
	LaGrandeQuatre("La Grande-Quatre", "QC-100"),
	LaMalbaie("La Malbaie", "QC-11"),
	LaPrairie("La Prairie", "QC-71"),
	LaSarre("La Sarre", "QC-45"),
	LaTuque("La Tuque", "QC-154"),
	LaVerendrye_Reservefaunique("La Vérendrye (Réserve faunique)", "QC-30"),
	LacRaglan("Lac Raglan", "QC-99"),
	LacSaintJean("Lac Saint-Jean", "QC-73"),
	LacMegantic("Lac-Mégantic", "QC-72"),
	Lachute("Lachute", "QC-14"),
	Lanaudiere("Lanaudière", "QC-74"),
	Laurentides_Reservefaunique("Laurentides (Réserve faunique)", "QC-113"),
	Laval("Laval", "QC-76"),
	LeGardeur("Le Gardeur", "QC-D3"),
	Levis("Lévis", "QC-78"),
	Longueuil("Longueuil", "QC-109"),
	Lorraine("Lorraine", "QC-81"),
	Louiseville("Louiseville", "QC-D2"),
	Magog("Magog", "QC-83"),
	Manicouagan("Manicouagan", "QC-153"),
	Maniwaki("Maniwaki", "QC-102"),
	Marieville("Marieville", "QC-84"),
	Mascouche("Mascouche", "QC-85"),
	Matagami("Matagami", "QC-129"),
	Matane("Matane", "QC-15"),
	Matapedia("Matapedia", "QC-151"),
	Mauricie("Mauricie", "QC-89"),
	Mingan("Mingan", "QC-90"),
	Mirabel("Mirabel", "QC-123"),
	MontSaintHilaire("Mont Saint-Hilaire", "QC-92"),
	MontJoli("Mont-Joli", "QC-127"),
	MontLaurier("Mont-Laurier", "QC-47"),
	MontTremblant("Mont-Tremblant", "QC-167"),
	Montmagny("Montmagny", "QC-124"),
	Montreal("Montréal", "QC-147"),
	Murdochville("Murdochville", "QC-16"),
	Natashquan("Natashquan", "QC-125"),
	NewCarlisle("New Carlisle", "QC-111"),
	Nicolet("Nicolet", "QC-110"),
	OtterburnPark("Otterburn Park", "QC-93"),
	Papineau("Papineau", "QC-17"),
	Parent("Parent", "QC-114"),
	Perce("Percé", "QC-18"),
	Pincourt("Pincourt", "QC-96"),
	PointealaCroix("Pointe-à-la-Croix", "QC-20"),
	Pontiac("Pontiac", "QC-19"),
	PortCartier("Port-Cartier", "QC-97"),
	PortMenier("Port-Menier", "QC-142"),
	Prevost("Prévost", "QC-98"),
	Puvirnituq("Puvirnituq", "QC-40"),
	Quaqtaq("Quaqtaq", "QC-106"),
	Quebec("Québec", "QC-133"),
	Repentigny("Repentigny", "QC-A0"),
	Richelieu("Richelieu", "QC-21"),
	Rigaud("Rigaud", "QC-A2"),
	Rimouski("Rimouski", "QC-138"),
	RiviereduLoup("Rivière-du-Loup", "QC-108"),
	Roberval("Roberval", "QC-134"),
	Rosemere("Rosemère", "QC-A3"),
	RouynNoranda("Rouyn-Noranda", "QC-148"),
	Saguenay("Saguenay", "QC-166"),
	SaintAmable("Saint-Amable", "QC-A5"),
	SaintBasileLeGrand("Saint-Basile Le Grand", "QC-A6"),
	SaintConstant("Saint-Constant", "QC-A7"),
	SaintEustache("Saint-Eustache", "QC-B4"),
	SaintFelicien("Saint-Félicien", "QC-B5"),
	SaintGeorges("Saint-Georges", "QC-B6"),
	SaintHyacinthe("Saint-Hyacinthe", "QC-22"),
	SaintJeansurRichelieu("Saint-Jean-sur-Richelieu", "QC-28"),
	SaintJerome("Saint-Jérôme", "QC-13"),
	SaintLazare("Saint-Lazare", "QC-B7"),
	SaintLinLaurentides("Saint-Lin-Laurentides", "QC-B8"),
	SaintLuc("Saint-Luc", "QC-B9"),
	SaintMicheldesSaints("Saint-Michel-des-Saints", "QC-C0"),
	SaintNicephore("Saint-Nicéphore", "QC-C1"),
	SaintRemi("Saint-Rémi", "QC-C2"),
	SaintSauveur("Saint-Sauveur", "QC-C3"),
	SaintTimothee("Saint-Timothée", "QC-C4"),
	SainteAgathe("Sainte-Agathe", "QC-33"),
	SainteAnneDesMonts("Sainte-Anne-Des-Monts", "QC-A8"),
	SainteAnneDesPlaines("Sainte-Anne-Des-Plaines", "QC-A9"),
	SainteCatherine("Sainte-Catherine", "QC-B0"),
	SainteJulie("Sainte-Julie", "QC-B1"),
	SainteSophie("Sainte-Sophie", "QC-B2"),
	SainteTherese("Sainte-Thérèse", "QC-B3"),
	SalaberrydeValleyfield("Salaberry-de-Valleyfield", "QC-143"),
	Salluit("Salluit", "QC-128"),
	Schefferville("Schefferville", "QC-115"),
	SeptIles("Sept-Îles", "QC-141"),
	Shawinigan("Shawinigan", "QC-132"),
	Sherbrooke("Sherbrooke", "QC-136"),
	SorelTracy("Sorel-Tracy", "QC-C6"),
	Sutton("Sutton", "QC-135"),
	Tadoussac("Tadoussac", "QC-107"),
	Tasiujaq("Tasiujaq", "QC-145"),
	Temiscamingue("Témiscamingue", "QC-139"),
	Temiscouata("Temiscouata", "QC-35"),
	Terrebonne("Terrebonne", "QC-C7"),
	ThetfordMines("Thetford Mines", "QC-C8"),
	Tracy("Tracy", "QC-C9"),
	TroisPistoles("Trois-Pistoles", "QC-24"),
	TroisRivieres("Trois-Rivières", "QC-130"),
	Umiujaq("Umiujaq", "QC-122"),
	ValdOr("Val-d'Or", "QC-149"),
	ValdesMonts("Val-des-Monts", "QC-42"),
	ValleedelaMatapedia("Vallée de la Matapédia", "QC-D0"),
	Vanier("Vanier", "QC-41"),
	Varennes("Varennes", "QC-162"),
	VaudreuilDorion("Vaudreuil-Dorion", "QC-66"),
	Victoriaville("Victoriaville", "QC-157"),
	Waskaganish("Waskaganish", "QC-116");
	
	private String link;
	private String name;
	
	QuebecCities(String n, String l) {
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
		QuebecCities[] array = QuebecCities.values();
		for (QuebecCities city : array)
			values.add(city.name);
		return values;
	}
	
}
