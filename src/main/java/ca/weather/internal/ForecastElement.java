/***********************************************************************
 * REDapp - ForecastElement.java
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

package ca.weather.internal;

import java.util.Locale;

public class ForecastElement {
	private String code;
	private String title_fr;
	private String unit_fr;
	private String title_en;
	private String unit_en;
	
	public ForecastElement(String code, String title_fr, String unit_fr, String title_en, String unit_en) {
		this.code = code;
		this.title_fr = title_fr;
		this.unit_fr = unit_fr;
		this.title_en = title_en;
		this.unit_en = unit_en;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getTitleFr() {
		return title_fr;
	}
	
	public String getTitleEn() {
		return title_en;
	}
	
	public String getTitle() {
		if (Locale.getDefault().getLanguage().equals(Locale.FRENCH.getLanguage()) || Locale.getDefault().getLanguage().equals(Locale.CANADA_FRENCH.getLanguage()))
			return title_fr;
		return title_en;
	}
	
	public String getUnitFr() {
		return unit_fr;
	}
	
	public String getUnitEn() {
		return unit_en;
	}
	
	public String getUnit() {
		if (Locale.getDefault().getLanguage().equals(Locale.FRENCH.getLanguage()) || Locale.getDefault().getLanguage().equals(Locale.CANADA_FRENCH.getLanguage()))
			return unit_fr;
		return unit_en;
	}
}
