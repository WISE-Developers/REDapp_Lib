/***********************************************************************
 * REDapp - DataExporter.java
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for data exporters.
 * @author "Travis Redpath"
 *
 * @param <T> The type of data that will be written to the file.
 */
public abstract class DataExporter {
	/**
	 * Write a row of data to the file.
	 * @param data
	 */
	public abstract void writeRow(Object... data) throws IOException;

	/**
	 * Set the element name
	 * @param name
	 */
	public abstract void setElementName(String name);
	
	/**
	 * Write a row of data to the file.
	 * @param data
	 */
	public abstract void writeHeader(String... data) throws IOException;
	
	/**
	 * Save the data to a file.
	 * @param filename The file to save to.
	 * @throws IOException
	 */
	public abstract void close() throws IOException;
	
	/**
	 * Retrieve an array of characters that are not valid for the exporters file type.
	 * @return
	 */
	public abstract String[] invalidChars();
	
	/**
	 * An exception that gets thrown when an attempt to add a second row with the same name is made.
	 * @author "Travis Redpath"
	 */
	public static class ColumnExistsException extends Exception {
		private static final long serialVersionUID = 1L;

		public ColumnExistsException() {
			super();
		}
		
		public ColumnExistsException(String message) {
			super(message);
		}
	}
	
	/**
	 * A helper class for building arrays of data to write as a row.
	 * Elements will be returned ready to be used in the {@link DataExporter}
	 * in the same order as they were added.
	 * 
	 * @author "Travis Redpath"
	 */
	public static class RowBuilder {
		private List<Object> m_data = new ArrayList<Object>();
		
		/**
		 * Add an object to the row.
		 * @param o
		 */
		public void addData(Object o) {
			m_data.add(o);
		}
		
		/**
		 * Add a set of objects to the row.
		 * @param o
		 */
		public void addAllData(Object... o) {
			m_data.addAll(Arrays.asList(o));
		}
		
		/**
		 * Return an object array ready to use in the {@link DataExporter}.
		 * @return
		 */
		public Object[] toRow() {
			Object[] o = m_data.toArray();
			m_data.clear();
			return o;
		}

		/**
		 * Return an string array ready to use as a header in the {@link DataExporter}.
		 * @return
		 */
		public String[] toHeader() {
			String[] list = new String[m_data.size()];
			int i = 0;
			for (Object o : m_data) {
				list[i++] = o.toString();
			}
			m_data.clear();
			return list;
		}
	}
}
