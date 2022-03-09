/***********************************************************************
 * REDapp - MetricPrefix.java
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

public enum MetricPrefix {
	yocto(-24, 0, "yocto", "y"),
	zepto(-21, 1, "zepto", "z"),
	atto(-18, 2, "atto", "a"),
	femto(-15, 3, "femto", "f"),
	pico(-12, 4, "pico", "p"),
	nano(-9, 5, "nano", "n"),
	mirco(-6, 6, "micro", "µ"),
	milli(-3, 7, "milli", "m"),
	centi(-2, 8, "centi", "c"),
	deci(-1, 9, "deci", "d"),
	NONE(0, 10, "", ""),
	deka(1, 11, "deka", "da"),
	hecto(2, 12, "hecto", "h"),
	kilo(3, 13, "kilo", "k"),
	mega(6, 14, "mega", "M"),
	giga(9, 15, "giga", "G"),
	tera(12, 16, "tera", "T"),
	peta(15, 17, "peta", "P"),
	exa(18, 18, "exa", "E"),
	zetta(21, 19, "zetta", "Z"),
	yotta(24, 20, "yotta", "Y"),
	DISABLE(0, -1, "", "");
	
	private int power;
	private double fraction;
	private int pos;
	private String name;
	private String abbv;
	@SuppressWarnings("unused")
	private boolean isError = false;
	
	public int getPower() { return power; }
	public double getFraction() { return fraction; }
	public String getName() { return name; }
	public String getName(MetricUnits unit) {
		return name + unit.getName();
	}
	public String getAbbreviation() { return abbv; }
	public String getAbbreviation(MetricUnits unit) {
		return abbv + unit.getAbbreviation();
	}
	public int getPositionInList() {
		return pos;
	}
	public static MetricPrefix getAtPosition(int pos) {
		for (MetricPrefix m : MetricPrefix.values()) {
			if (m.pos == pos)
				return m;
		}
		MetricPrefix retval = MetricPrefix.NONE;
		retval.isError = true;
		return retval;
	}
	
	public static double convertTo(double value, MetricPrefix from, MetricPrefix to) {
		return value * (from.fraction / to.fraction);
	}
	
	public static MetricUnitValue convertTo(MetricUnitValue value, MetricPrefix to) {
		return new MetricUnitValue(to, value.getUnit(), convertTo(value.getValue(), value.getPrefix(), to));
	}
	
	MetricPrefix(int power, int position, String name, String abbv) {
		this.power = power;
		this.name = name;
		this.abbv = abbv;
		pos = position;
		fraction = Math.pow(10, power);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String toString(MetricUnits unit) {
		return name + unit.getName();
	}
}
