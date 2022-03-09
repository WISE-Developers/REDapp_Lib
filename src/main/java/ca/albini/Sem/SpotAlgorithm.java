/***********************************************************************
 * REDapp - SpotAlgorithm.java
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

public class SpotAlgorithm {
	public static final int SpotSpeciesCount = 14;

	private static double METERS_TO_FEET(double value) {
		return value * 3.28084;
	}

	private static double FEET_TO_METERS(double value) {
		return value * 0.3048;
	}

	private static double MILES_TO_KILOMETERS(double value) {
		return value * 1.60934;
	}

	private static double KILOMETERS_TO_MILES(double value) {
		return value * 0.621371;
	}

	@SuppressWarnings("unused")
	private static double INCHES_TO_CENTIMETERS(double value) {
		return value * 2.54;
	}

	private static double CENTIMETERS_TO_INCHES(double value) {
		return value * 0.393701;
	}

	/**
	 *
	 * @param firebrandHeight The firebrand height in metres.
	 * @param coverHeight the cover height in metres.
	 * @return The critical cover height in metres.
	 */
	public static double criticalCoverHeight(double firebrandHeight, double coverHeight) {
		double fbh = METERS_TO_FEET(firebrandHeight);
		double ch = METERS_TO_FEET(coverHeight);
		double hitCrit = 2.2 * Math.pow(fbh, 0.337) - 4.0;
		double htUsed = (ch > hitCrit) ? (ch) : (hitCrit);
		return FEET_TO_METERS(htUsed);
	}

	/**
	 *
	 * @param flameHeight The flame height in metres.
	 * @return The firebrand height in metres.
	 */
	public static double firebrandHeightFromBurningPile(double flameHeight) {
		return FEET_TO_METERS(12.2) * flameHeight;
	}

	/**
	 *
	 * @param flameLength The flame length in metres.
	 * @param windSpeedAt20Ft The wind speed at 20ft (about 6m) in km/h.
	 * @return The firebrand height in metres.
	 */
	public static double firebrandHeightFromSurfaceFire(double flameLength, double windSpeedAt20Ft) {
	    double z = 0.0;
	    double fl = METERS_TO_FEET(flameLength);
	    double ws = KILOMETERS_TO_MILES(windSpeedAt20Ft);
	    if (flameLength > Smidgen)
	    {
	        // f is function relating thermal energy to windspeed.
	        double f = 322.0 * Math.pow( ( 0.474 * ws ), -1.01 );

	        // Byram's fireline intensity is derived back from flame length.
	        double byrams = Math.pow( ( fl / 0.45 ), ( 1.0 / 0.46 ) );

	        // Strength of thermal updraft (Btu/ft)
	        double energy = f * byrams;

	        // Initial firebrand height (ft).
	        z = ( energy < Smidgen ) ? ( 0.0 ) : ( 1.055 * Math.sqrt( energy ) );
	    }
	    return FEET_TO_METERS(z);
	}

	/**
	 *
	 * @param treeHeight The tree height in metres.
	 * @param flameHeight The flame height in metres.
	 * @param flameDuration The flame duration.
	 * @return The firebrand height in metres.
	 */
	public static double firebrandHeightFromTorchingTrees(double treeHeight, double flameHeight, double flameDuration) {
		double th = METERS_TO_FEET(treeHeight);
		double fh = METERS_TO_FEET(flameHeight);
	    final double[][] FirebrandFlameHeightRatio = {
	        {4.24, 0.332},
	        {3.64, 0.391},
	        {2.78, 0.418},
	        {4.70, 0.000}
	    };

	    // Constrain flameHeight
	    double z = 0.0;
	    if (flameHeight > Smidgen && flameDuration > Smidgen)
	    {
	        // Determine which Figure 7 curve to apply
	        int j;
	        double y;
	        if ( ( y = th / fh ) >= 1. )
	        {
	            j = 0;  // y >= 1.0
	        }
	        else if ( y >= 0.5 )
	        {
	            j = 1;  // y >= 0.5
	        }
	        else if ( flameDuration < 3.5 )
	        {
	            j = 2;  // y < 0.5 and flameDuration < 3.5
	        }
	        else
	        {
	            j = 3;  // y < 0.5 and flameDuration >= 3.5
	        }
	        // Figure 7 lofted firebrand height / steady flame height
	        z = FirebrandFlameHeightRatio[j][0]
	            * Math.pow( flameDuration, FirebrandFlameHeightRatio[j][1] )
	            // multiply by steady flame height
	            * fh
	            // add 1/2 tree height
	            + th / 2.;
	    }
	    return FEET_TO_METERS(z);
	}

	/**
	 *
	 * @param fuel
	 * @param firebrandHeight The firebrand height in metres.
	 * @param windSpeedAt20Ft The wind speed at 20ft (about 6m) in km/h.
	 * @param coverHeight The cover height in metres.
	 * @return The flat terrain spotting distance in kilometres.
	 */
	public static double flatTerrainSpotDistanceFromFBPType(String fuel, double firebrandHeight, double windSpeedAt20Ft, double coverHeight) {
		if (fuel.startsWith("C") || fuel.startsWith("M") || fuel.startsWith("D"))
			return flatTerrainSpotDistanceFromTorchingTrees(firebrandHeight, windSpeedAt20Ft, coverHeight);
		else
			return flatTerrainSpotDistanceFromSurfaceFire(firebrandHeight, windSpeedAt20Ft, coverHeight);
	}

	/**
	 *
	 * @param firebrandHeight The firebrand height in metres.
	 * @param windSpeedAt20Ft The wind speed at 20ft (about 6m) in km/h.
	 * @param coverHeight The cover height in metres.
	 * @return The flat terrain spotting distance in kilometres.
	 */
	public static double flatTerrainSpotDistanceFromBurningPile(double firebrandHeight, double windSpeedAt20Ft, double coverHeight) {
	    return flatTerrainSpotDistanceFromTorchingTrees(firebrandHeight, windSpeedAt20Ft, coverHeight);
	}

	/**
	 *
	 * @param firebrandHeight The firebrand height in metres
	 * @param windSpeedAt20Ft The wind speed at 20ft (about 6m) in  km/h.
	 * @param coverHeight The cover height in metres.
	 * @return The flat terrain spotting distance in kilometres.
	 */
	public static double flatTerrainSpotDistanceFromSurfaceFire(double firebrandHeight, double windSpeedAt20Ft, double coverHeight) {
	    double flatDist = flatTerrainSpotDistanceFromTorchingTrees(firebrandHeight, windSpeedAt20Ft, coverHeight );
	        if ( flatDist > Smidgen )
	        {
	            // Downward drift during lofting (mi)
	            double drift = MILES_TO_KILOMETERS(0.000278 * KILOMETERS_TO_MILES(windSpeedAt20Ft) * Math.pow(METERS_TO_FEET(firebrandHeight), 0.643));
	            flatDist += drift;
	        }
	        return flatDist;
	}

	/**
	 *
	 * @param firebrandHeight The firebrand height in metres.
	 * @param windSpeedAt20Ft The wind speed at 20ft (about 6m) in km/h.
	 * @param coverHeight The cover height in meters.
	 * @return The flat terrain spotting distance in kilometres.
	 */
	public static double flatTerrainSpotDistanceFromTorchingTrees(double firebrandHeight, double windSpeedAt20Ft, double coverHeight) {
	    double flatDist = 0.0;
	    double fbh = METERS_TO_FEET(firebrandHeight);
	    double ws = KILOMETERS_TO_MILES(windSpeedAt20Ft);
	    if (firebrandHeight > Smidgen)
	    {
	        // Cover height used
	        double htUsed = METERS_TO_FEET(criticalCoverHeight(firebrandHeight, coverHeight));
	        if (htUsed > Smidgen )
	        {
	            flatDist = 0.000718 * ws * Math.sqrt( htUsed )
	                     * ( 0.362 + Math.sqrt(fbh / htUsed) / 2.0
	                     * Math.log( fbh / htUsed ) );
	        }
	    }
	    return MILES_TO_KILOMETERS(flatDist);
	}

	/**
	 *
	 * @param flatSpotDistance The flat terrain spotting distance in kilometres.
	 * @param source
	 * @param ridgetopToValleyDistance The ridge top to valley distance in kilometres.
	 * @param ridgetopToValleyElevation The ridge top to valley elevation in metres.
	 * @return The mountain terrain spotting distance in kilometres.
	 */
	public static double mountainTerrainSpotDistance(double flatSpotDistance, SpotSource source, double ridgetopToValleyDistance, double ridgetopToValleyElevation) {
	    double mtnSpotDistance = flatSpotDistance;
	    double rtvd = KILOMETERS_TO_MILES(ridgetopToValleyDistance);
	    double rtve = METERS_TO_FEET(ridgetopToValleyElevation);
	    if (rtve > Smidgen && rtvd > Smidgen)
	    {
	    	double fsd = KILOMETERS_TO_MILES(flatSpotDistance);
	        double a1 = fsd / rtvd;
	        double b1 = rtve / ( 10. * Math.PI ) / 1000.;
	        double x = a1;
	        for ( int i=0; i<6; i++ )
	        {
	            x = a1 - b1 * ( Math.cos( Math.PI * x - source.toInt() * Math.PI / 2.0 )
	              - Math.cos( source.toInt() * Math.PI / 2.0 ) );
	        }
	        mtnSpotDistance = x * rtvd;
	    }
	    return MILES_TO_KILOMETERS(mtnSpotDistance);
	}

	/**
	 *
	 * @param treeSpecies
	 * @param treeDbh The trees diameter at breast height in centimetres.
	 * @param torchingTrees The number of torching trees (0-30).
	 * @return
	 */
	public static double steadyFlameDurationFromTorchingTrees(SpotSpecies treeSpecies, double treeDbh, double torchingTrees) {
		double td = CENTIMETERS_TO_INCHES(treeDbh);
	    final double[][] Duration =
	        {
	        //   ht_a  ht_b dur_a   dur_b
	            {12.6, -.256},  //  0 Engelmann spruce
	            {10.7, -.278},  //  1 Douglas-fir
	            {10.7, -.278},  //  2 subalpine fir
	            { 6.3, -.249},  //  3 western hemlock
	            {12.6, -.256},  //  4 ponderosa pine
	            {12.6, -.256},  //  5 lodgepole pine
	            {10.7, -.278},  //  6 western white pine
	            {10.7, -.278},  //  7 grand fir
	            {10.7, -.278},  //  8 balsam fir
	            {11.9, -.389},  //  9 slash pine
	            {11.9, -.389},  // 10 longleaf pine
	            {7.91, -.344},  // 11 pond pine
	            {7.91, -.344},  // 12 shortleaf pine
	            {13.5, -.544}   // 13 loblolly pine
	        } ;
	        double duration = 0.0;
	        if ( torchingTrees > Smidgen && treeDbh > Smidgen )
	        {
	            duration =
	                // Figure 3b, 4b, or 5b duration
	                Duration[treeSpecies.toInt()][0] * Math.pow( td, Duration[treeSpecies.toInt()][1] )
	                // Figure 6 flame duration multiplier
	                * Math.pow( torchingTrees, -0.2 );
	        }
	        return duration;
	}

	/**
	 *
	 * @param treeSpecies
	 * @param treeDbh The trees diameter at breast height in centimetres.
	 * @param torchingTrees The number of torching trees (0-30).
	 * @return The flame height in metres.
	 */
	public static double steadyFlameHeightFromTorchingTrees(SpotSpecies treeSpecies, double treeDbh, double torchingTrees) {
		double td = CENTIMETERS_TO_INCHES(treeDbh);
	    final double[][] FlameHeight =
	        {
	            {15.7, .451},  //  0 Engelmann spruce
	            {15.7, .451},  //  1 Douglas-fir
	            {15.7, .451},  //  2 subalpine fir
	            {15.7, .451},  //  3 western hemlock
	            {12.9, .453},  //  4 ponderosa pine
	            {12.9, .453},  //  5 lodgepole pine
	            {12.9, .453},  //  6 western white pine
	            {16.5, .515},  //  7 grand fir
	            {16.5, .515},  //  8 balsam fir
	            {2.71, 1.00},  //  9 slash pine
	            {2.71, 1.00},  // 10 longleaf pine
	            {2.71, 1.00},  // 11 pond pine
	    		{2.71, 1.00},  // 12 shortleaf pine
		    	{2.71, 1.00}   // 13 loblolly pine
	        };
	        double flameHeight = 0.0;
	        if ( torchingTrees > Smidgen && treeDbh > Smidgen )
	        {
	            flameHeight =
	                // Figure 3a, 4a, or 5a flame height
	                FlameHeight[treeSpecies.toInt()][0] * Math.pow( td, FlameHeight[treeSpecies.toInt()][1] )
	                // Figure 6 flame height multiplier
	                * Math.pow( torchingTrees, 0.4 );
	        }
	        return FEET_TO_METERS(flameHeight);
	}

	/**
	 *
	 * @param fuel The FBP fuel type.
	 * @param HFI The head fire intensity (kW/m).
	 * @param ws The 10 metre wind speed (km/h).
	 * @param h The tree height.
	 * @return The maximum spot fire distance.
	 */
	public static double spotCalc(String fuel, double HFI, double WS, double H) {
		double X = 0;
		double z;
		double z_o; //initial particle height
		double z_F; //height of the flame tip
		double U;
		double U_H;
		double Uh;
		double A = 0;
		double B = 0;
		double h;
		double fU;
		double E;
		double h_c;
		double F;
		final double g = 9.8;
		double z_10 = 10.0;

		U = WS / 3.6; //convert to m/s.

		//Spot fires from burning trees
		if (fuel.startsWith("C") || fuel.startsWith("M") || fuel.startsWith("D")) {
			if (H <= 0.0)
				H = 10.0;

			z_F = Math.sqrt(HFI / 300.0);
			z = 12.2 * z_F;

			z_o = 0.1313 * H;
			U_H = U / 0.493 / Math.log(z_10 / z_o);

			X = 21.9 * U_H * Math.sqrt(H / g) * (0.362 + Math.sqrt(z / H) * (0.5) * Math.log(z / H) );
		}
		else {
			if (fuel.startsWith("S"))
			{
				A = 69.6;
				B = -0.818;
				h = 1.0;
			}
			else if (fuel.startsWith("O1A"))
			{
				A = 162.0;
				B = -1.465;
				h = 1.;
			}
			else if (fuel.startsWith("O"))
			{
				A = 129.0;
				B = -1.238;
				h = 0.5;
			}

			if (A > 0.)
			{
				fU = A * Math.pow(U, B);
				E = HFI * fU;
				H = 0.173 * Math.sqrt(E);
				h_c = Math.pow(H, 0.337) - 1.22;

				if (h_c < 6.0)
					h = 6.0;
				else
					h = h_c;

				Uh = U * Math.pow(h / 10.0, 1.0 / 7);
				h = h_c;
				F = 1.3 * 3.6 * Uh * Math.sqrt(h) * (0.362 + Math.sqrt(H / h) * (0.5) * Math.log(H / h));

				if (h < 10.0) {
					U_H = U * Math.pow(H / 10.0, 1.0 / 7);
					X = 2.78 * U_H * Math.sqrt(H);
				}
				else
					X = Uh * Math.sqrt(H) * (2.13 + Math.log (H / h));

				X = X + F;
			}
		}
		return X;
	}

	public enum SpotSource {
		SpotSourceMidslopeWindward("ui.label.spotting.terrain.midslope"),
		SpotSourceValleyBottom("ui.label.spotting.terrain.valley"),
		SpotSourceMidslopeLeeward("ui.label.spotting.terrain.leeward"),
		SpotSourceRidgettop("ui.label.spotting.terrain.ridge");

		String m_resid;

		SpotSource(String resid) {
			m_resid = resid;
		}

		public int toInt() {
			switch (this) {
			case SpotSourceMidslopeWindward:
				return 0;
			case SpotSourceValleyBottom:
				return 1;
			case SpotSourceMidslopeLeeward:
				return 2;
			default:
				return 3;
			}
		}

		public static SpotSource fromInt(int v) {
			switch (v) {
			case 0:
				return SpotSourceMidslopeWindward;
			case 1:
				return SpotSourceValleyBottom;
			case 2:
				return SpotSourceMidslopeLeeward;
			default:
				return SpotSourceRidgettop;
			}
		}

		@Override
		public String toString() {
			switch (this) {
			case SpotSourceMidslopeWindward:
				return "Midslope Windward";
			case SpotSourceValleyBottom:
				return "Valley Bottom";
			case SpotSourceMidslopeLeeward:
				return "Midslope Leeward";
			default:
				return "Ridgetop";
			}
		}

		public String getResourceId() {
			return m_resid;
		}
	}

	public enum SpotSpecies
	{
	    SpotSpeciesEngelmannSpruce(0, "Engelmann Spruce", "ui.label.spotting.spec.spruce"),
	    SpotSpeciesDouglasFir(1, "Douglas Fir", "ui.label.spotting.spec.douglas"),
	    SpotSpeciesSubalpineFir(2, "Subalpine Fir", "ui.label.spotting.spec.subalpine"),
	    SpotSpeciesWesternHemlock(3, "Western Hemlock", "ui.label.spotting.spec.hemlock"),
	    SpotSpeciesPonderosaPine(4, "Ponderosa Pine", "ui.label.spotting.spec.ponderosa"),
	    SpotSpeciesLodgepolePine(5, "Lodgepole Pine", "ui.label.spotting.spec.lodgepole"),
	    SpotSpeciesWesternWhitePine(6, "Western White Pine", "ui.label.spotting.spec.white"),
	    SpotSpeciesGrandFir(7, "Grand Fir", "ui.label.spotting.spec.grand"),
	    SpotSpeciesBalsamFir(8, "Balsam Fir", "ui.label.spotting.spec.balsam"),
	    SpotSpeciesSlashPine(9, "Slash Pine", "ui.label.spotting.spec.slash"),
	    SpotSpeciesLongleafPine(10, "Long Leaf Pine", "ui.label.spotting.spec.longleaf"),
	    SpotSpeciedsPondPine(11, "Pond Pine", "ui.label.spotting.spec.pond"),
	    SpotSpeciesShortleafPine(12, "Short Leaf Pine", "ui.label.spotting.spec.shortleaf"),
	    SpotSpeciesLoblollyPine(13, "Loblolly Pine", "ui.label.spotting.spec.loblolly");

	    private int m_id;
	    private String m_name;
	    private String m_resid;

	    private SpotSpecies(int id, String name, String resid) {
	    	m_id = id;
	    	m_name = name;
	    	m_resid = resid;
	    }

	    @Override
	    public String toString() {
	    	return m_name;
	    }

	    public String getResourceId() {
	    	return m_resid;
	    }

	    public int toInt() {
	    	return m_id;
	    }

	    public static SpotSpecies fromInt(int v) {
	    	return SpotSpecies.values()[v];
	    }
	};
}
