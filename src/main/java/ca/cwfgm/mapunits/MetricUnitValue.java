/***********************************************************************
 * REDapp - MetricUnitValue.java
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

package ca.cwfgm.mapunits;

public class MetricUnitValue {
	private MetricPrefix prefix;
	private MetricUnits unit;
	private double value;
	
	public MetricPrefix getPrefix() { return prefix; }
	public MetricUnits getUnit() { return unit; }
	public double getValue() { return value; }
	public void setValue(double value) { this.value = value; }
	
	public void convertUnits(MetricPrefix toUnits) {
		value = MetricPrefix.convertTo(value, prefix, toUnits);
		prefix = toUnits;
	}
	
	public MetricUnitValue(MetricPrefix prefix, MetricUnits units, double value) {
		this.prefix = prefix;
		this.unit = units;
		this.value = value;
	}
	
	public String getUnitString() {
		return prefix.getAbbreviation(unit);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value) + " " + prefix.getAbbreviation(unit); 
	}
}
