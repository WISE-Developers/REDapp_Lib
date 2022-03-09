/***********************************************************************
 * REDapp - CitiesHelper.java
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

import ca.weather.forecast.Province;

public class CitiesHelper {
	private CitiesHelper() { }
	
	public static Cities[] getCities(Province prov) {
		switch (prov) {
		case ALBERTA:
			return AlbertaCities.values();
		case BRITISH_COLUMBIA:
			return BritishColumbiaCities.values();
		case MANITOBA:
			return ManitobaCities.values();
		case NEW_BRUNSWICK:
			return NewBrunswickCities.values();
		case NEWFOUNDLAND_AND_LABRADOR:
			return NewfoundLandLabradorCities.values();
		case NORTHWEST_TERRITORIES:
			return NorthwestTerritoriesCities.values();
		case NOVA_SCOTIA:
			return NovaScotiaCities.values();
		case NUNAVUT:
			return NunavutCities.values();
		case ONTARIO:
			return OntarioCities.values();
		case PRINCE_EDWARD_ISLAND:
			return PrinceEdwardIslandCities.values();
		case QUEBEC:
			return QuebecCities.values();
		case SASKATCHEWAN:
			return SaskatchewanCities.values();
		case YUKON:
			return YukonTerritoryCities.values();
		}
		return null;
	}
	
	public static Province getProvince(Cities[] list) {
		if (list == null || list.length == 0)
			return null;
		Cities first = list[0];
		Class<?> cls = first.getClass();
		if (cls.equals(AlbertaCities.Airdrie.getClass()))
			return Province.ALBERTA;
		else if (cls.equals(BritishColumbiaCities.Abbotsford.getClass()))
			return Province.BRITISH_COLUMBIA;
		else if (cls.equals(ManitobaCities.Altona.getClass()))
			return Province.MANITOBA;
		else if (cls.equals(NewBrunswickCities.BasCaraquet.getClass()))
			return Province.NEW_BRUNSWICK;
		else if (cls.equals(NewfoundLandLabradorCities.Badger.getClass()))
			return Province.NEWFOUNDLAND_AND_LABRADOR;
		else if (cls.equals(NorthwestTerritoriesCities.Aklavik.getClass()))
			return Province.NORTHWEST_TERRITORIES;
		else if (cls.equals(NovaScotiaCities.Amherst.getClass()))
			return Province.NOVA_SCOTIA;
		else if (cls.equals(NunavutCities.Alert.getClass()))
			return Province.NUNAVUT;
		else if (cls.equals(OntarioCities.Alexandria.getClass()))
			return Province.ONTARIO;
		else if (cls.equals(PrinceEdwardIslandCities.Charlottetown.getClass()))
			return Province.PRINCE_EDWARD_ISLAND;
		else if (cls.equals(QuebecCities.Akulivik.getClass()))
			return Province.QUEBEC;
		else if (cls.equals(SaskatchewanCities.Assiniboia.getClass()))
			return Province.SASKATCHEWAN;
		else if (cls.equals(YukonTerritoryCities.BeaverCreek.getClass()))
			return Province.YUKON;
		return null;
	}
}
