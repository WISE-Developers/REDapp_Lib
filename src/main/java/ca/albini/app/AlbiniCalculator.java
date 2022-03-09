/***********************************************************************
 * REDapp - AlbiniCalculator.java
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

package ca.albini.app;

import ca.albini.Sem.SpotAlgorithm.SpotSource;
import ca.albini.Sem.SpotAlgorithm.SpotSpecies;
import ca.albini.Sem.SpotBurningPile;
import ca.albini.Sem.SpotSurfaceFire;
import ca.albini.Sem.SpotTorchingTrees;

public class AlbiniCalculator {
	private double windSpeed = 0.0;
	private double coverHeight = 0.0;
	private SpotSpecies species;
	private double dbh = 12.5;
	private double height = 3.0; //tree height
	private int torchingTrees = 0;
	private FireType fireType = FireType.BurningPile;
	private double flameHeight = 0.0;
	private double flameLength = 0.0;
	private double ridgeDistance = 0.0;
	private double ridgeElevation = 0.0;
	private TerrainType terrainType = TerrainType.FlatTerrain;
	private SpotSource spotSource = SpotSource.SpotSourceMidslopeLeeward;
	
	private double out_flameHeight;
	private double out_coverHeight;
	private double out_firebrandHeight;
	private double out_spotfireDistance;
	private double out_flameDuration;
	
	public void setWindSpeed(double speed) { windSpeed = speed; }
	public void set10MWindSpeed(double speed) { windSpeed = speed / 1.15; }
	public double getWindSpeed() { return windSpeed; }
	public void setDownwindCoverHeight(double hieght) { coverHeight = hieght; }
	public double getDownwindCoverHeight() { return coverHeight; }
	public void setSpotSpecies(SpotSpecies species) { this.species = species; }
	public SpotSpecies getSpotSpecier() { return species; }
	public void setDBH(double dbh) { this.dbh = dbh; }
	public double getDBH() { return dbh; }
	public void setTreeHeight(double height) { this.height = height; }
	public double getTreeHeight() { return height; }
	public void setNumberOfTorchingTrees(int count) { torchingTrees = count; }
	public int getNumberOfTorchingTrees() { return torchingTrees; }
	public void setFireType(FireType type) { fireType = type; }
	public FireType getFireType() { return fireType; }
	public void setFlameHeight(double height) { flameHeight = height; }
	public double getFlameHeight() { return flameHeight; }
	public void setFlameLength(double length) { flameLength = length; }
	public double getFlameLength() { return flameLength; }
	public void setRidgetopToValleyDistance(double distance) { ridgeDistance = distance; }
	public double getRidgetopToValleyDistance() { return ridgeDistance; }
	public void setRidgetopToValleyElevationChange(double elevation) { ridgeElevation = elevation; }
	public double getRidgetopToValleyElevationChange() { return ridgeElevation; }
	public void setTerrainType(TerrainType type) { terrainType = type; }
	public TerrainType getTerrainType() { return terrainType; }
	public void setSpotSource(SpotSource source) { spotSource = source; }
	public SpotSource getSpotSource() { return spotSource; }
	
	public double getOutputFlameHeight() { return out_flameHeight; }
	public double getOutputCriticalCoverHeight() { return out_coverHeight; }
	public double getOutputFireBrandHeight() { return out_firebrandHeight; }
	public double getOutputSpotfireDistance() { return out_spotfireDistance; }
	public double getOutputFlameDuration() { return out_flameDuration; }
	
	public double minWindSpeed() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.windSpeedAt20FtMin;
		case SurfaceFire:
			return SpotSurfaceFire.windSpeedAt20FtMin;
		default:
			return SpotTorchingTrees.windSpeedAt20FtMin;
		}
	}
	
	public double maxWindSpeed() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.windSpeedAt20FtMax;
		case SurfaceFire:
			return SpotSurfaceFire.windSpeedAt20FtMax;
		default:
			return SpotTorchingTrees.windSpeedAt20FtMax;
		}
	}
	
	public double minCoverHeight() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.coverHeightMin;
		case SurfaceFire:
			return SpotSurfaceFire.coverHeightMin;
		default:
			return SpotTorchingTrees.coverHeightMin;
		}
	}
	
	public double maxCoverHeight() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.coverHeightMax;
		case SurfaceFire:
			return SpotSurfaceFire.coverHeightMax;
		default:
			return SpotTorchingTrees.coverHeightMax;
		}
	}
	
	public double minDBH() {
		return SpotTorchingTrees.treeDbhMin;
	}
	
	public double maxDBH() {
		return SpotTorchingTrees.treeDbhMax;
	}
	
	public double minTreeHeight() {
		return SpotTorchingTrees.treeHeightMin;
	}
	
	public double maxTreeHeight() {
		return SpotTorchingTrees.treeHeightMax;
	}
	
	public int minNumberOfTorchingTrees() {
		return SpotTorchingTrees.torchingTreesMin;
	}
	
	public int maxNumberOfTorchingTrees() {
		return SpotTorchingTrees.torchingTreesMax;
	}
	
	public double minFlameHeight() {
		return SpotBurningPile.flameHeightMin;
	}
	
	public double maxFlameHeight() {
		return SpotBurningPile.flameHeightMax;
	}
	
	public double minFlameLength() {
		return SpotSurfaceFire.flameLengthMin;
	}
	
	public double maxFlameLength() {
		return SpotSurfaceFire.flameLengthMax;
	}
	
	public double minRidgetopToValleyDistance() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.ridgetopToValleyDistanceMin;
		case SurfaceFire:
			return SpotSurfaceFire.ridgetopToValleyDistanceMin;
		default:
			return SpotTorchingTrees.ridgetopToValleyDistanceMin;
		}
	}
	
	public double maxRidgetopToValleyDistance() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.ridgetopToValleyDistanceMax;
		case SurfaceFire:
			return SpotSurfaceFire.ridgetopToValleyDistanceMax;
		default:
			return SpotTorchingTrees.ridgetopToValleyDistanceMax;
		}
	}
	
	public double minRidgetopToValleyElevationChange() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.ridgetopToValleyElevationMin;
		case SurfaceFire:
			return SpotSurfaceFire.ridgetopToValleyElevationMin;
		default:
			return SpotTorchingTrees.ridgetopToValleyElevationMin;
		}
	}
	
	public double maxRidgetopToValleyElevationChange() {
		switch (fireType) {
		case BurningPile:
			return SpotBurningPile.ridgetopToValleyElevationMax;
		case SurfaceFire:
			return SpotSurfaceFire.ridgetopToValleyElevationMax;
		default:
			return SpotTorchingTrees.ridgetopToValleyElevationMax;
		}
	}
	
	public AlbiniCalculator() {
	}
	
	public void calculate() {
		switch (fireType) {
		case BurningPile:
			calculateBurningPile();
			break;
		case SurfaceFire:
			calculateSurfaceFire();
			break;
		default:
			calculateTorchingTrees();
			break;
		}
	}
	
	private void calculateBurningPile() {
		SpotBurningPile bp = new SpotBurningPile();
		bp.setCoverHeight(coverHeight);
		bp.setFlameHeight(flameHeight);
		bp.setRidgetopToValleyDistance(ridgeDistance);
		bp.setRidgetopToValleyElevation(ridgeElevation);
		bp.setSource(spotSource);
		bp.setWindSpeedAt20Ft(windSpeed);
		
		out_flameHeight = bp.flameHeight();
		out_coverHeight = bp.coverHeight();
		out_firebrandHeight = bp.firebrandHeight();
		out_spotfireDistance = terrainType == TerrainType.FlatTerrain ? bp.flatTerrainSpottingDistance() : bp.mountainTerrainSpottingDistance();
	}
	
	private void calculateSurfaceFire() {
		SpotSurfaceFire sf = new SpotSurfaceFire();
		sf.setCoverHeight(coverHeight);
		sf.setFlameLength(flameLength);
		sf.setRidgetopToValleyDistance(ridgeDistance);
		sf.setRidgetopToValleyElevation(ridgeElevation);
		sf.setSource(spotSource);
		sf.setWindSpeedAt20Ft(windSpeed);
		
		out_flameHeight = sf.flameLength();
		out_coverHeight = sf.coverHeight();
		out_firebrandHeight = sf.firebrandHeight();
		out_spotfireDistance = terrainType == TerrainType.FlatTerrain ? sf.flatTerrainSpottingDistance() : sf.mountainTerrainSpottingDistance();
	}
	
	private void calculateTorchingTrees() {
		SpotTorchingTrees tt = new SpotTorchingTrees();
		tt.setCoverHeight(coverHeight);
		tt.setRidgetopToValleyDistance(ridgeDistance);
		tt.setRidgetopToValleyElevation(ridgeElevation);
		tt.setSource(spotSource);
		tt.setSpecies(species);
		tt.setTorchingTrees(torchingTrees);
		tt.setTreeDbh(dbh);
		tt.setTreeHeight(height);
		tt.setWindSpeedAt20Ft(windSpeed);
		
		out_flameHeight = tt.flameHeight();
		out_coverHeight = tt.coverHeight();
		out_firebrandHeight = tt.firebrandHeight();
		out_flameDuration = tt.flameDuration();
		out_spotfireDistance = terrainType == TerrainType.FlatTerrain ? tt.flatTerrainSpottingDistance() : tt.mountainTerrainSpottingDistance();
	}
	
	public static enum FireType {
		BurningPile,
		SurfaceFire,
		TorchingTrees;
		
		@Override
		public String toString() {
			switch (this) {
			case SurfaceFire:
				return "Surface Fire";
			case TorchingTrees:
				return "Torching Trees";
			default:
				return "Burning Pile";
			}
		}
	}
	
	public static enum TerrainType {
		FlatTerrain,
		MountainousTerrain
	}
}
