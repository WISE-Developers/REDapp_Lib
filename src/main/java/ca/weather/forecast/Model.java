/***********************************************************************
 * REDapp - Model.java
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

/**
 * The different forecast models available from Environment Canada.
 * 
 * @author Travis Redpath
 */
public enum Model {
	NCEP,
	GEM_DETER,
	GEM,
	BOTH,
	CUSTOM;

	public static Model fromString(String text) {
		for (Model m : Model.values())
			if (m.toString().equals(text))
				return m;
		return CUSTOM;
	}
}
