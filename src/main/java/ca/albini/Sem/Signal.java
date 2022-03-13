/***********************************************************************
 * REDapp - Signal.java
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

package ca.albini.Sem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Signal {
	protected int m_classVersion;
	protected boolean m_dirty;
	
	public Signal() {
		m_classVersion = 1;
		m_dirty = true;
	}
	
	public Signal(int classVersion) {
		m_classVersion = classVersion;
		m_dirty = true;
	}
	
	public Signal(Signal right) {
		m_classVersion = right.m_classVersion;
		setDirty();
	}
	
	public void checkUpdate() {
		if (m_dirty)
			updateObject();
	}
	
	public String className() {
		return "Sem::Signal";
	}
	
	public int classVersion() {
		return m_classVersion;
	}
	
	protected boolean readHeader(BufferedReader reader) throws IOException {
		boolean status = true;
		
		String line = reader.readLine();
		if (!line.equals(className())) {
			assert false;
			status = false;
		}
		
		line = reader.readLine();
		m_classVersion = Integer.parseInt(line);
		setDirty();
		
		return status;
	}
	
	protected void setDirty() {
		m_dirty = true;
	}
	
	protected void updateObject() {
		update();
		m_dirty = false;
	}
	
	protected void writeHeader(BufferedWriter writer) throws IOException {
		writer.write(className());
		writer.write(classVersion());
	}
	
	public static boolean equals(Signal s1, Signal s2) {
		return (s1.className().equals(s2.className())) && (s2.classVersion() == s2.classVersion());
	}
	
	protected abstract void update();
}
