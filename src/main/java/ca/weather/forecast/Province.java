/***********************************************************************
 * REDapp - Province.java
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

package ca.weather.forecast;

import java.util.ArrayList;

import ca.hss.text.TranslationCallback;

/**
 * The Canadian provinces.
 * 
 * @author Travis
 *
 */
public enum Province {
	ALBERTA("AB", "ui.province.ab", "Alberta"),
	BRITISH_COLUMBIA("BC", "ui.province.bc", "British Columbia"),
	MANITOBA("MB", "ui.province.mb", "Manitoba"),
	NEW_BRUNSWICK("NB", "ui.province.nb", "New Brunswick"),
	NEWFOUNDLAND_AND_LABRADOR("NL", "ui.province.nl", "Newfoundland and Labrador"),
	NORTHWEST_TERRITORIES("NT", "ui.province.nwt", "Northwest Territories"),
	NOVA_SCOTIA("NS", "ui.province.ns", "Nova Scotia"),
	NUNAVUT("NU", "ui.province.nv", "Nunavut"),
	ONTARIO("ON", "ui.province.on", "Ontario"),
	PRINCE_EDWARD_ISLAND("PE", "ui.province.pei", "Prince Edward Island"),
	QUEBEC("QC", "ui.province.qc", "Quebec"),
	SASKATCHEWAN("SK", "ui.province.sk", "Saskatchewan"),
	YUKON("YT", "ui.province.yk", "Yukon");

	private String abb;
	private String id;
	private String name;

	Province(String abbv, String id, String name) {
		this.abb = abbv;
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		if (TranslationCallback.instance == null)
			return name;
		return TranslationCallback.instance.translate(id);
	}

	public String abbreviation() {
		return abb;
	}

	public static Province parseString(String val) {
		for (Province p : Province.values()) {
			if (val.equals(p.abb) || val.equals(p.name))
				return p;
		}
		return valueOf(val);
	}

	public static ArrayList<String> valuesAsStrings(){
		ArrayList<String> values = new ArrayList<String>();
		Province[] array = Province.values();
		for (int i = 0; i < array.length; i++) values.add(array[i].toString());
		return values;
	}

}
