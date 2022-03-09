/***********************************************************************
 * REDapp - XMLFile.java
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ca.weather.acheron.XMLFileType;

/**
 * Parses an XML weather file from Environment Canada.
 * 
 * @author Travis
 */
public class XMLFile extends Thread {
	private String path;
	private Location loc;
	private String beginTime;
	private String endTime;
	private ForecastElement element;
	private String creationDate;
	private List<Model> models;
	private List<Forecast> forecasts;
	private boolean initialized = false;
	private boolean valid = false;
	private XMLFileType type;
	
	public XMLFile(String path, XMLFileType type) {
		this.path = path;
		this.type = type;
		models = new ArrayList<Model>();
		forecasts = new ArrayList<Forecast>();
		
		this.start();
	}
	
	@Override
	public void run() {
		File fl = new File(path);
		if (!fl.exists())
			return;
		if (parseXmlFile()) {
			valid = true;
			initialized = true;
		}
	}
	
	private boolean parseXmlFile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document dom;
		try {
			db = dbf.newDocumentBuilder();
			dom = db.parse(path);
		} catch (ParserConfigurationException e) {
			return false;
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return parseDocument(dom);
	}
	
	private boolean parseDocument(Document dom) {
		try {
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("header");
			if (nl == null || nl.getLength() == 0)
				return false;
			Element header = (Element)nl.item(0);
			Node n = header.getFirstChild();
			while (n != null) {
				Element el;
				try {
					el = (Element)n;
				}
				catch (Exception e) {
					n = n.getNextSibling();
					continue;
				}
				if (el.getTagName().equals("location"))
					parseLocation(el);
				else if (el.getTagName().equals("valid-begin-time"))
					beginTime = getValue(el);
				else if (el.getTagName().equals("valid-end-time"))
					endTime = getValue(el);
				else if (el.getTagName().equals("forecast_element"))
					parseForecastElement(el);
				else if (el.getTagName().equals("creation_date"))
					creationDate = getValue(el);
				else if (el.getTagName().equals("model_description"))
					parseModelDescription(el);
				
				n = n.getNextSibling();
			}
			nl = docEle.getElementsByTagName("forecast");
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = null;
				try {
					el = (Element)nl.item(i);
				}
				catch (Exception e) {
				}
				if (el != null) {
					parseForecast(el);
				}
			}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private void parseLocation(Element el) {
		String description = el.getAttribute("description");
		String country = el.getAttribute("pays_country");
		String province = el.getAttribute("province_state");
		loc = new Location(description, country, province);
	}
	
	private void parseForecastElement(Element el) {
		String code = el.getAttribute("code");
		String title_fr = el.getAttribute("titre_francais");
		String unit_fr = el.getAttribute("unite_francaise");
		String title_en = el.getAttribute("title_english");
		String unit_en = el.getAttribute("unit_english");
		element = new ForecastElement(code, title_fr, unit_fr, title_en, unit_en);
	}
	
	private void parseModelDescription(Element el) {
		Node n = el.getFirstChild();
		while (n != null) {
			Element ele = null;
			if (n instanceof Element) {
				try {
					ele = (Element)n;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				if (ele != null) {
					if (ele.getTagName().equals("model")) {
						String id = ele.getAttribute("id");
						String model = ele.getAttribute("model");
						String member = ele.getAttribute("member");
						String centre = ele.getAttribute("center");
						String domain = ele.getAttribute("domain");
						String data_type = ele.getAttribute("data_type");
						String member_type = ele.getAttribute("member_type");
						int idi = Integer.parseInt(id);
						Model m = new Model(idi, model, member, centre, domain, data_type, member_type);
						models.add(m);
					}
				}
			}
			n = n.getNextSibling();
		}
	}
	
	private void parseForecast(Element el) {
		String forecast_hour = el.getAttribute("forecast_hour");
		String valid_time = el.getAttribute("valid_time");
		Forecast f = new Forecast(Integer.parseInt(forecast_hour), valid_time);
		Node n = el.getFirstChild();
		while (n != null) {
			Element ele = null;
			try {
				ele = (Element)n;
			}
			catch (Exception e) {
			}
			if (ele != null) {
				String modelId = ele.getAttribute("id");
				int modelIdi = Integer.parseInt(modelId);
				String value = getValue(ele);
				if (value == null)
					continue;
				double d = Double.parseDouble(value);
				f.put(modelIdi, d);
			}
			n = n.getNextSibling();
		}
		forecasts.add(f);
	}
	
	private String getValue(Element el) {
		String retval = null;
		if (el.hasChildNodes()) {
			Node nn = el.getFirstChild();
			while (nn != null) {
				if ((retval = nn.getNodeValue()) != null)
					break;
				nn = nn.getNextSibling();
			}
		}
		else
			retval = el.getNodeValue();
		return retval;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getCreationDate() {
		if (!initialized)
			return null;
		return creationDate;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public Location getLocation() {
		if (!initialized)
			return null;
		return loc;
	}
	
	public String getBeginTime() {
		if (!initialized)
			return null;
		return beginTime;
	}
	
	public String getEndTime() {
		if (!initialized)
			return null;
		return endTime;
	}
	
	public ForecastElement getElement() {
		if (!initialized)
			return null;
		return element;
	}
	
	public XMLFileType getFileType() {
		return type;
	}
	
	public int getForecastHourCount() {
		if (forecasts == null)
			return 0;
		return forecasts.size();
	}
	
	public Model getModelAt(int i) throws IndexOutOfBoundsException {
		if (!initialized)
			return null;
		if (i < 0 || i >= models.size())
			throw new IndexOutOfBoundsException();
		return models.get(i);
	}
	
	public Iterator<Model> getModelIterator() {
		return models.iterator();
	}
	
	public Forecast getForecastAt(int i) throws IndexOutOfBoundsException {
		if (!initialized)
			return null;
		if (i < 0 || i >= forecasts.size())
			throw new IndexOutOfBoundsException();
		return forecasts.get(i);
	}
	
	public Iterator<Forecast> getForecastIterator() {
		return forecasts.iterator();
	}
}
