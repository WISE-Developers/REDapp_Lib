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

package ca.weather.internal;

public class Model {
	private int id;
	private String model;
	private String member;
	private String centre;
	private String domain;
	private String data_type;
	private String member_type;
	
	public Model(int id, String model, String member, String centre, String domain, String data_type, String member_type) {
		this.id = id;
		this.model = model;
		this.member = member;
		this.centre = centre;
		this.domain = domain;
		this.data_type = data_type;
		this.member_type = member_type;
	}
	
	public int getId() {
		return id;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getMember() {
		return member;
	}
	
	public String getCentre() {
		return centre;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getDataType() {
		return data_type;
	}
	
	public String getMemberType() {
		return member_type;
	}
}
