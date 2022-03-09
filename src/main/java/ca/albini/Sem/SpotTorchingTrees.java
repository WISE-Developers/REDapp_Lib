/***********************************************************************
 * REDapp - SpotTorchingTrees.java
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

public class SpotTorchingTrees extends Signal {
	private static final int spotSurfaceFireVersion = 1;
	public static final double coverHeightMin = 0.0;
	public static final double coverHeightMax = 90.0;
	public static final double ridgetopToValleyDistanceMin = 0.0;
	public static final double ridgetopToValleyDistanceMax = 6.5;
	public static final double ridgetopToValleyElevationMin = 0.0;
	public static final double ridgetopToValleyElevationMax = 6400.0;
	public static final int torchingTreesMin = 0;
	public static final int torchingTreesMax = 30;
	public static final double treeDbhMin = 12.5;
	public static final double treeDbhMax = 100.0;
	public static final double treeHeightMin = 3.0;
	public static final double treeHeightMax = 91.5;
	public static final double windSpeedAt20FtMin = 0.0;
	public static final double windSpeedAt20FtMax = 160.0;
	protected double m_criticalCover;
	
	protected double m_coverHeight;
	protected double m_distance;
	protected double m_elevation;
	protected SpotAlgorithm.SpotSource m_source;
	protected SpotAlgorithm.SpotSpecies m_species;
	protected double m_torchingTrees;
	protected double m_treeDbh;
	protected double m_treeHeight;
	protected double m_windSpeedAt20Ft;

	protected double m_firebrandHeight;
	protected double m_flameHeight;
	protected double m_flameDuration;
    protected double m_flatSpottingDistance;
    protected double m_mtnSpottingDistance;
	
	public SpotTorchingTrees() {
		m_coverHeight = 0;
		m_distance = 0;
		m_elevation = 0;
		m_source = SpotAlgorithm.SpotSource.SpotSourceValleyBottom;
		m_species = SpotAlgorithm.SpotSpecies.SpotSpeciesLodgepolePine;
		m_torchingTrees = 0.0;
		m_treeDbh = treeDbhMin;
		m_treeHeight = treeHeightMin;
		m_windSpeedAt20Ft = 0;
		m_firebrandHeight = 0;
		m_flameHeight = 0;
		m_flameDuration = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
		init();
		m_classVersion = spotSurfaceFireVersion;
		setDirty();
	}
	
	public SpotTorchingTrees(SpotAlgorithm.SpotSource source, double ridgetopToValleyDistance, double ridgetopToValleyElevation, double coverHeight, double windSpeedAt20Ft, double torchingTrees, double treeDbh, double treeHeight, SpotAlgorithm.SpotSpecies treeSpecies) {
		m_coverHeight = coverHeight;
		m_distance = ridgetopToValleyDistance;
		m_elevation = ridgetopToValleyElevation;
		m_source = source;
		m_species = treeSpecies;
		m_torchingTrees = torchingTrees;
		m_treeDbh = treeDbh;
		m_treeHeight = treeHeight;
		m_windSpeedAt20Ft = windSpeedAt20Ft;
		m_firebrandHeight = 0;
		m_flameHeight = 0;
		m_flameDuration = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
		
		assert m_coverHeight >= coverHeightMin && m_coverHeight <= coverHeightMax;
		assert m_distance >= ridgetopToValleyDistanceMin && m_distance <= ridgetopToValleyDistanceMax;
		assert m_elevation >= ridgetopToValleyElevationMin && m_elevation <= ridgetopToValleyElevationMax;
		assert m_torchingTrees >= torchingTreesMin && m_torchingTrees <= torchingTreesMax;
		assert m_treeDbh >= treeDbhMin && m_treeDbh <= treeDbhMax;
		assert m_treeHeight >= treeHeightMin && m_treeHeight <= treeHeightMax;
		assert m_windSpeedAt20Ft >= windSpeedAt20FtMin && m_windSpeedAt20Ft <= windSpeedAt20FtMax;
		
		init();
		m_classVersion = spotSurfaceFireVersion;
		setDirty();
	}
	
	public SpotTorchingTrees(SpotTorchingTrees right) {
	    init();
	    m_classVersion    = right.m_classVersion;
	    m_coverHeight     = right.m_coverHeight;
	    m_distance        = right.m_distance;
	    m_elevation       = right.m_elevation;
	    m_source          = right.m_source;
	    m_species         = right.m_species;
	    m_torchingTrees   = right.m_torchingTrees;
	    m_treeDbh         = right.m_treeDbh;
	    m_treeHeight      = right.m_treeHeight;
	    m_windSpeedAt20Ft = right.m_windSpeedAt20Ft;
	    m_firebrandHeight = right.m_firebrandHeight;
	    m_flameHeight     = right.m_flameHeight;
	    m_flameDuration   = right.m_flameDuration;
	    m_flatSpottingDistance = right.m_flatSpottingDistance;
	    m_mtnSpottingDistance = right.m_mtnSpottingDistance;
	    setDirty();
	}

	public String className() {
		return "Sem::SpotTorchingTrees";
	}

	public int classVersion() {
		return spotSurfaceFireVersion;
	}

	public double coverHeight() {
		return m_coverHeight;
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
	
	public SpotAlgorithm.SpotSpecies species() {
		return m_species;
	}
	
	public double torchingTrees() {
		return m_torchingTrees;
	}
	
	public double treeDbh() {
		return m_treeDbh;
	}
	
	public double treeHeight() {
		return m_treeHeight;
	}

	public double windSpeedAt20Ft() {
		return m_windSpeedAt20Ft;
	}

	public double firebrandHeight() {
		checkUpdate();
		return m_firebrandHeight;
	}
	
	public double flameHeight() {
		checkUpdate();
		return m_flameHeight;
	}
	
	public double flameDuration() {
		checkUpdate();
		return m_flameDuration;
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
	
	public void setSpecies(SpotAlgorithm.SpotSpecies species) {
	    if (species != m_species)
	    {
	        m_species = species;
	        setDirty();
	    }
	}
	
	public void setTorchingTrees(double torchingTrees) {
	    assert torchingTrees >= torchingTreesMin && torchingTrees <= torchingTreesMax;
	       if (torchingTrees != m_torchingTrees)
	       {
	           m_torchingTrees = torchingTrees;
	           setDirty();
	       }
	}
	
	public void setTreeDbh(double treeDbh) {
	    assert treeDbh >= treeDbhMin && treeDbh <= treeDbhMax;
	       if (treeDbh != m_treeDbh)
	       {
	           m_treeDbh = treeDbh;
	           setDirty();
	       }
	}
	
	public void setTreeHeight(double treeHeight) {
	    assert treeHeight >= treeHeightMin && treeHeight <= treeHeightMax;
	       if (treeHeight != m_treeHeight)
	       {
	           m_treeHeight = treeHeight;
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
		m_flameHeight = 0;
		m_flameDuration = 0;
		m_flatSpottingDistance = 0;
		m_mtnSpottingDistance = 0;
	}
	
	@Override
	protected void update() {
		init();
		
		m_flameHeight = SpotAlgorithm.steadyFlameHeightFromTorchingTrees(m_species, m_treeDbh, m_torchingTrees);
		m_flameDuration = SpotAlgorithm.steadyFlameDurationFromTorchingTrees(m_species, m_treeDbh, m_torchingTrees);
		m_firebrandHeight = SpotAlgorithm.firebrandHeightFromTorchingTrees(m_treeHeight, m_flameHeight, m_flameDuration);
		m_criticalCover = SpotAlgorithm.criticalCoverHeight(m_firebrandHeight, m_coverHeight);
		m_flatSpottingDistance = SpotAlgorithm.flatTerrainSpotDistanceFromTorchingTrees(m_firebrandHeight, m_windSpeedAt20Ft, m_coverHeight);
		m_mtnSpottingDistance = SpotAlgorithm.mountainTerrainSpotDistance(m_flatSpottingDistance, m_source, m_distance, m_elevation);
	}
	
	public static boolean equal(SpotTorchingTrees lhs, SpotTorchingTrees rhs) {
		return( abs( lhs.coverHeight() - rhs.coverHeight() ) < Smidgen
			       && abs( lhs.ridgetopToValleyDistance() - rhs.ridgetopToValleyDistance() ) < Smidgen
			       && abs( lhs.ridgetopToValleyElevation() - rhs.ridgetopToValleyElevation() ) < Smidgen
			       && lhs.source() == rhs.source()
			       && lhs.species() == rhs.species()
			       && abs( lhs.torchingTrees() - rhs.torchingTrees() ) < Smidgen
			       && abs( lhs.treeDbh() - rhs.treeDbh() ) < Smidgen
			       && abs( lhs.treeHeight() - rhs.treeHeight() ) < Smidgen
			       && abs( lhs.windSpeedAt20Ft() - rhs.windSpeedAt20Ft() ) < Smidgen );
	}
	
	public static boolean notEqual(SpotTorchingTrees lhs, SpotTorchingTrees rhs) {
		return (!(equal(lhs, rhs)));
	}
}
