/***********************************************************************
 * REDapp - SpotBurningPile.java
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

import static ca.albini.Sem.Config.Smidgen;
import static java.lang.Math.*;

public class SpotBurningPile extends Signal {
	private static final int spotBurningPileVersion = 1;
	public static final double coverHeightMin = 0.0;
	public static final double coverHeightMax = 90;
	public static final double flameHeightMin = 0.0;
	public static final double flameHeightMax = 30.5;
	public static final double ridgetopToValleyDistanceMin = 0.0;
	public static final double ridgetopToValleyDistanceMax = 6.5;
	public static final double ridgetopToValleyElevationMin = 0.0;
	public static final double ridgetopToValleyElevationMax = 6400.0;
	public static final double windSpeedAt20FtMin = 0.0;
	public static final double windSpeedAt20FtMax = 160.0;
	
	protected double m_coverHeight;
	protected double m_distance;
	protected double m_elevation;
	protected double m_flameHeight;
	protected SpotAlgorithm.SpotSource m_source;
	protected double m_windSpeedAt20Ft;
	protected double m_firebrandHeight;
	protected double m_flatSpottingDistance;
	protected double m_mtnSpottingDistance;
	protected double m_criticalCover;
	
	public SpotBurningPile() {
		m_coverHeight = 0;
		m_distance = 0;
		m_elevation = 0;
		m_flameHeight = 0;
		m_source = SpotAlgorithm.SpotSource.SpotSourceValleyBottom;
		m_windSpeedAt20Ft = 0;
		m_firebrandHeight = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
		init();
		m_classVersion = spotBurningPileVersion;
		setDirty();
	}
	
	public SpotBurningPile(SpotAlgorithm.SpotSource source, double ridgetopToValleyDistance, double ridgetopToValleyElevation, double coverHeight, double windSpeedAt20Ft, double flameHeight) {
		m_coverHeight = coverHeight;
		m_distance = ridgetopToValleyDistance;
		m_elevation = ridgetopToValleyElevation;
		m_flameHeight = flameHeight;
		m_source = source;
		m_windSpeedAt20Ft = windSpeedAt20Ft;
		m_firebrandHeight = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
		
		assert m_coverHeight >= coverHeightMin && m_coverHeight <= coverHeightMax;
		assert m_distance >= ridgetopToValleyDistanceMin && m_distance <= ridgetopToValleyDistanceMax;
		assert m_elevation >= ridgetopToValleyElevationMin && m_elevation <= ridgetopToValleyElevationMax;
		assert m_flameHeight >= flameHeightMin && m_flameHeight <= flameHeightMax;
		assert m_windSpeedAt20Ft >= windSpeedAt20FtMin && m_windSpeedAt20Ft <= windSpeedAt20FtMax;
		
		init();
		m_classVersion = spotBurningPileVersion;
		setDirty();
	}
	
	public SpotBurningPile(SpotBurningPile right) {
	    init();
	    m_classVersion    = right.m_classVersion;
	    m_coverHeight     = right.m_coverHeight;
	    m_distance        = right.m_distance;
	    m_elevation       = right.m_elevation;
	    m_flameHeight     = right.m_flameHeight;
	    m_source          = right.m_source;
	    m_windSpeedAt20Ft = right.m_windSpeedAt20Ft;
	    m_firebrandHeight = right.m_firebrandHeight;
	    m_flatSpottingDistance = right.m_flatSpottingDistance;
	    m_mtnSpottingDistance  = right.m_mtnSpottingDistance;
	    setDirty();
	}
	
	public String className() {
		return "Sem::SpotBurningPile";
	}
	
	public int classVersion() {
		return spotBurningPileVersion;
	}
	
	public double coverHeight() {
		return m_coverHeight;
	}
	
	public double flameHeight() {
		return m_flameHeight;
	}
	
	public double ridgetopToValleyDistance() {
		return m_distance;
	}
	
	public double ridgetopToValleyElevation() {
		return m_elevation;
	}
	
	public SpotAlgorithm.SpotSource source() {
		return m_source;
	}
	
	public double windSpeedAt20Ft() {
		return m_windSpeedAt20Ft;
	}
	
	public double firebrandHeight() {
		checkUpdate();
		return m_firebrandHeight;
	}
	
	public double flatTerrainSpottingDistance() {
		checkUpdate();
		return m_flatSpottingDistance;
	}
	
	public double mountainTerrainSpottingDistance() {
		checkUpdate();
		return m_mtnSpottingDistance;
	}
	
	public double criticalCoverHeight() {
		checkUpdate();
		return m_criticalCover;
	}
	
	public void setCoverHeight(double coverHeight) {
		assert coverHeight >= coverHeightMin && coverHeight <= coverHeightMax;
		if (coverHeight != m_coverHeight)
		{
			m_coverHeight = coverHeight;
			setDirty();
		}
	}
	
	public void setFlameHeight(double flameHeight) {
		assert flameHeight >= flameHeightMin && flameHeight <= flameHeightMax;
		if (flameHeight != m_flameHeight)
		{
			m_flameHeight = flameHeight;
			setDirty();
		}
	}
	
	public void setRidgetopToValleyDistance(double distance) {
		assert distance >= ridgetopToValleyDistanceMin && distance <= ridgetopToValleyDistanceMax;
		if (distance != m_distance)
		{
			m_distance = distance;
			setDirty();
		}
	}
	
	public void setRidgetopToValleyElevation(double elevation) {
		assert elevation >= ridgetopToValleyElevationMin && elevation <= ridgetopToValleyElevationMax;
		if (elevation != m_elevation)
		{
			m_elevation = elevation;
			setDirty();
		}
	}
	
	public void setSource(SpotAlgorithm.SpotSource source) {
		if (source != m_source)
		{
			m_source = source;
			setDirty();
		}
	}
	
	public void setWindSpeedAt20Ft(double windSpeed) {
		assert windSpeed >= windSpeedAt20FtMin && windSpeed <= windSpeedAt20FtMax;
		if (windSpeed != m_windSpeedAt20Ft)
		{
			m_windSpeedAt20Ft = windSpeed;
			setDirty();
		}
	}
	
	protected void init() {
		m_firebrandHeight = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
	}
	
	@Override
	protected void update() {
		init();
		
		m_firebrandHeight = SpotAlgorithm.firebrandHeightFromBurningPile(m_flameHeight);
		m_criticalCover = SpotAlgorithm.criticalCoverHeight(m_firebrandHeight, m_coverHeight);
		m_flatSpottingDistance = SpotAlgorithm.flatTerrainSpotDistanceFromBurningPile(m_firebrandHeight, m_windSpeedAt20Ft, m_coverHeight);
		m_mtnSpottingDistance = SpotAlgorithm.mountainTerrainSpotDistance(m_flatSpottingDistance, m_source, m_distance, m_elevation);
	}
	
	public static boolean equal(SpotBurningPile lhs, SpotBurningPile rhs) {
		return( abs( lhs.coverHeight() - rhs.coverHeight() ) < Smidgen
			       && abs( lhs.ridgetopToValleyDistance() - rhs.ridgetopToValleyDistance() ) < Smidgen
			       && abs( lhs.ridgetopToValleyElevation() - rhs.ridgetopToValleyElevation() ) < Smidgen
			       && abs( lhs.flameHeight() - rhs.flameHeight() ) < Smidgen
			       && lhs.source() == rhs.source()
			       && abs( lhs.windSpeedAt20Ft() - rhs.windSpeedAt20Ft() ) < Smidgen );
	}
	
	public static boolean notEqual(SpotBurningPile p1, SpotBurningPile p2) {
		return !(equal(p1, p2));
	}
}
