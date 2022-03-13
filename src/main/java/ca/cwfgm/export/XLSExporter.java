/***********************************************************************
 * REDapp - XLSExporter.java
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Exports data in either xls or xlsx format.
 * 
 * @author "Travis Redpath"
 */
public class XLSExporter extends DataExporter {
	private Workbook workbook;
	private Sheet sheet;
	private Row data;
	private int col, row;
	private File file;
	private CellStyle headerstyle;
	
	public XLSExporter(File file) {
		if(file.getAbsolutePath().endsWith("xlsx"))
			workbook = new XSSFWorkbook();
		else
			workbook = new HSSFWorkbook();
		this.file = file;
		sheet = workbook.createSheet("Weather Data");
		data = sheet.createRow(0);
		headerstyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		
		font.setBold(true);
		
		headerstyle.setFont(font);
		
		headerstyle.setAlignment(HorizontalAlignment.CENTER);
		
		row = 1;
	}

	@Override
	public void writeHeader(String... rowData) throws IOException {
		for (Object o : rowData) {
			Cell cell = data.createCell(col);
			cell.setCellStyle(headerstyle);
			col++;
			cell.setCellValue(o.toString());
		}
		data = sheet.createRow(row++);
		col = 0;
	}

	@Override
	public void writeRow(Object... rowData) throws IOException {
		for (Object o : rowData) {
			Cell cell = data.createCell(col);
			col++;
			Class<? extends Object> cls = o.getClass();
			if (cls == Double.class)
				cell.setCellValue((Double)o);
			else if (cls == Date.class)
				cell.setCellValue((Date)o);
			else if (cls == Calendar.class)
				cell.setCellValue((Calendar)o);
			else if (cls == Boolean.class)
				cell.setCellValue((Boolean)o);
			else if (cls == Integer.class)
				cell.setCellValue((Integer)o);
			else
				cell.setCellValue(o.toString());
		}
		data = sheet.createRow(row++);
		col = 0;
	}

	@Override
	public void close() throws IOException {
		try(OutputStream os = new FileOutputStream(file)) {
			workbook.write(os);
			os.close();
		}
		catch (FileNotFoundException ex) {
			throw new IOException(ex.getMessage());
		}
		catch (SecurityException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	@Override
	public String[] invalidChars() {
		return new String[] { };
	}

	@Override
	public void setElementName(String name) {
	}
}
