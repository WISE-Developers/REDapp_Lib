/***********************************************************************
 * REDapp - BZipExtractor.java
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

package ca.weather.acheron;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class BZipExtractor {
	/**
	 * Extract a file compressed using BZip2. The extracted file will be deleted when the virtual machine shuts down.
	 *
	 * @param bzipFile The location of the BZip2 file.
	 * @return The path to the extracted file. This file will be deleted on application close.
	 * @throws FileNotFoundException If the input file does not exist.
	 * @throws IOException If the input file is not a proper BZip2 file.
	 */
	public static String extract(String bzipFile) throws FileNotFoundException, IOException {
		String outputFilename = bzipFile.substring(0, bzipFile.length() - 4);
		File fl = new File(bzipFile);
		if (!fl.exists())
			throw new FileNotFoundException();

		File outputFile = new File(outputFilename);
		outputFile.deleteOnExit();
		FileInputStream in = new FileInputStream(bzipFile);
		try (FileOutputStream out = new FileOutputStream(outputFile)) {
			try (BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in)) {
				final byte[] buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = bzIn.read(buffer))) {
					out.write(buffer, 0, n);
				}
			}
		}

		return outputFilename;
	}

	private BZipExtractor() { }
}
