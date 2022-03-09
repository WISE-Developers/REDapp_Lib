/***********************************************************************
 * REDapp - XMLExporter.java
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

package ca.cwfgm.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Exports data in xml format.
 * 
 * @author "Travis Redpath"
 */
public class XMLExporter extends DataExporter {
	private Document doc;
	private Element root;
	private File file;
	private List<String> headers = new ArrayList<String>();
	private String elementName = "hour";
	
	public void setElementName(String name) {
		elementName = name;
	}
	
	public String getElementName() {
		return elementName;
	}

	public XMLExporter(File file) throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
		root = doc.createElement("WeatherData");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		root.setAttribute("exportTime", dateFormat.format(date));
		this.file = file;
	}
	
	@Override
	public void writeRow(Object... data) throws IOException {
		if (data.length != headers.size())
			throw new IOException("Incorrect data size");
		Element base = doc.createElement(elementName);
		for (int i = 0; i < data.length; i++) {
			Element elem = doc.createElement(headers.get(i));
			elem.appendChild(doc.createTextNode(data[i].toString()));
			base.appendChild(elem);
		}
		root.appendChild(base);
	}

	@Override
	public void writeHeader(String... data) throws IOException {
		headers.clear();
		headers.addAll(Arrays.asList(data));
	}

	@Override
	public void close() throws IOException {
		OutputStream os = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(root);
			os = new FileOutputStream(file);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new IOException();
		}
		finally {
			if (os != null) {
				os.close();
			}
		}
	}

	@Override
	public String[] invalidChars() {
		return new String[] { };
	}
}
