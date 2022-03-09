/***********************************************************************
 * REDapp - Forecast.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Stores model information for a forecast.
 * @author Travis
 *
 */
public class Forecast {
	private HashMap<Integer, Double> map;
	private int forecast_hour;
	private String valid_time;
	
	public Forecast(int hour, String time) {
		map = new HashMap<Integer, Double>();
		forecast_hour = hour;
		valid_time = time;
	}
	
	public int getForecastHour() {
		return forecast_hour;
	}
	
	public String getValidTime() {
		return valid_time;
	}
	
	public void put(int modelId, double value) {
		map.put(Integer.valueOf(modelId), Double.valueOf(value));
	}
	
	public double get(int modelId) throws ModelDoesNotExistException {
		Integer i = Integer.valueOf(modelId);
		if (map.containsKey(i))
			return map.get(i).doubleValue();
		throw new ModelDoesNotExistException(modelId);
	}
	
	public boolean modelExists(int modelId) {
		return map.containsKey(Integer.valueOf(modelId));
	}
	
	public List<Double> getValuesForMembers(List<Integer> memberIds) {
		List<Double> retval = new ArrayList<Double>();
		for (int i : memberIds) {
			if (modelExists(i)) {
				retval.add(map.get(i));
			}
		}
		return retval;
	}
	
	public int getSize() {
		return map.size();
	}
	
	public static class ModelDoesNotExistException extends Exception {
		private static final long serialVersionUID = 1L;
		public int modelId;
		
		public ModelDoesNotExistException(int modelId) {
			this.modelId = modelId;
		}
	}
}
