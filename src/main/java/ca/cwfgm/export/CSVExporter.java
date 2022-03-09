/***********************************************************************
 * REDapp - CSVExporter.java
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
import java.io.FileWriter;
import java.io.IOException;

/**
 * Exports data in comma separated value format.
 * @author "Travis Redpath"
 *
 * @param <T> The type of data that will be written to the file.
 */
public class CSVExporter extends DataExporter {
	private String delimiter = ",";
	private String newLine = System.getProperty("line.separator");
	private FileWriter output = null;
	
	public void setDelimiter(String delim) {
		delimiter = delim;
	}
	
	public String getDelimiter() {
		return delimiter;
	}
	
	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}
	
	public String getNewLine() {
		return newLine;
	}
	
	public CSVExporter(File fl) throws IOException {
		super();
		output = new FileWriter(fl);
	}

	@Override
	public void writeHeader(String... data) throws IOException {
		writeRow((Object[])data);
	}
	
	@Override
	public void writeRow(Object... data) throws IOException {
		for (int i = 0; i < data.length - 1; i++) {
			output.write(data[i].toString());
			output.write(delimiter);
		}
		output.write(data[data.length - 1].toString());
		output.write(newLine);
	}
	
	@Override
	public void close() throws IOException {
		output.close();
	}

	@Override
	public String[] invalidChars() {
		return new String[] { delimiter };
	}

	@Override
	public void setElementName(String name) {
	}
}
