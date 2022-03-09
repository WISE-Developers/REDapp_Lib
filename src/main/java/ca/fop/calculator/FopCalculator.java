/***********************************************************************
 * REDapp - FopCalculator.java
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

package ca.fop.calculator;


public class FopCalculator {
	private static final double[] Wt = { 0.458964673950, 0.417000830772, 0.113373382074,
	   	   1.03991974531E-2, 2.61017202815E-4, 8.98547906430E-7 };

	private static final double[] Xi = { 0.222846604179, 1.188932101673, 2.992736326059,
			5.775143569105, 9.837467418383, 15.982873980602 };

	private static final double[] AC1 = {  0.97, -0.59,   1.2,  0.13,  -5.6,  -7.1,   0.79,  0.42 };
	private static final double[] AC2 = { -0.19, -0.15, -0.12, -0.05,   0.0,   0.0, -0.081, -0.12 };
	private static final double[] AC3 = {   0.0,   0.0,   0.0,   0.0,  0.68,   1.4,    0.0,   0.0 };
	private static final double[] AC4 = {   0.0,   0.0,   0.0,   0.0,   0.0,   0.0,   -8.5,   0.0 };
	private static final double[] B = {   0.012, 0.005, 0.002, 0.005, 0.007, 0.006,  0.011, 0.005 };
	
	private FopCalculator() {
	}
	
	/**
	 * @brief IgnProb calculates the ignition probabilities of wildland fine fuels by
	 * lightning flash based upon
	 *
	 * Latham, D.J; Schlieter, J.A.  1989.  Ignition probabilities of wildland fuels
	 * based upon simulated lightning discharges.  USDA For. Serv. Res. Pap.
	 * INT-411, Intermt. Res. Stn., Ogden, UT.  16 pp.22
	 * @param iFuel The fuel type.
	 * @param FM The percent moisture content, valid between 0% and 40%,
	 *    represented in this program as a fraction (1.00 = 100%)
	 * @param Depth The fuel depth in cm, valid between 0 and 10 cm.
	 * @param iStroke The polarity of the flash > 0 for positive, <= 0 for negative.
	 * @return The ignition probability of a given wildland fine fuel.
	 */
	public static double ignProb(FuelType iFuel, double FM, double Depth, int iStroke) {
		int i;
		double S, Sum, T, Y, FX;
		double EtaM, Coef, Prob;

		double CCPerPos = 1.;
		double CCPerNeg = 1.;  /* used to be 0.2 but I'll handle P(LCC) elsewhere */

		double RHOB = 0.127;

		double EtaNeg = 1.6;
		double EtaPos = 2.3;
		double SNeg = 207.8;
		double SPos = 69.3;
		
		if (iFuel == FuelType.NULL)
			return 0;

		if (iFuel != FuelType.Automatic_Ignition) {
			if (iStroke > 0) {
				EtaM = 1./EtaPos;
				S = SPos;
				Coef = CCPerPos;
			}
			else {
				EtaM = 1./EtaNeg;
				S = SNeg;
				Coef = CCPerNeg;
			}

			Sum = 0.;
			for (i = 0; i < 6; i++) {
				T = S * Math.pow(Xi[i], EtaM);
				Y = AC1[iFuel.toInt() - 1]
					+ 100.0 * AC2[iFuel.toInt() - 1] * FM
					+ AC3[iFuel.toInt() - 1] * Depth
					+ AC4[iFuel.toInt() - 1] * RHOB
					+ B[iFuel.toInt() - 1] * T;
				FX = 1.0/(1.0 + Math.exp(-Y));
				Sum = Sum + Wt[i] * FX;
			}
			Prob = Sum * Coef;
		}
		else
			Prob = 1.0;
		return Prob;
	}
	
	/**
	 * @brief Calculates the moisture content from the Duff Moisture Code (DMC).
	 * 
	 * Moisture content is based on:
	 * 
	 * Lawson, B.D.; Dalrymple, G.N.; Hawkes, B.C.  1997.  Predicting forest floor moisture
     * contents from Duff Moisture Code values.  Nat. Resour. Can., Can. For. Serv., Pac.
     * For. Cent., Victoria, British Columbia.  Tech. Transfer Note 6.
	 * @param DMC The Duff Moisture Code (DMC).
	 * @param eqn Fuel equation to use.
	 * @return The moisture content as a fraction.
	 */
	public static double dmc2mc(double DMC, MoistureContentEquation eqn) {
		double mc = 0;
		
		if (eqn == MoistureContentEquation.DMC_National_Standard)
			mc = Math.exp((DMC - 244.7) / (-43.4)) + 20;   /* DMC National Standard and Coastal B.C. CWH */
		
		if (eqn == MoistureContentEquation.Southern_Interior_BC)
			mc = Math.exp((DMC - 223.9) / (-41.7)) + 20;   /* Southern Interior B.C. (2-4 cm) */
		
		if (eqn == MoistureContentEquation.Southern_Yukon_Undifferentiated_Duff)
			mc = Math.exp((DMC - 157.3) / (-24.6)) + 20;   /* Southern Yukon - Pine/White Spruce */
		                                          /* Feather moss, Sphagnum and
		                                             Undifferentiated duff (2-4 cm) */
		if (eqn == MoistureContentEquation.Southern_Yukon_Reindeer_Lichen)
			mc = Math.exp((DMC - 106.7) / (-14.9)) + 20;   /* Southern Yukon - Pine/White Spruce */
		                                          /* Reindeer lichen (2-4 cm) */
		if (eqn == MoistureContentEquation.Southern_Yukon_Other)
			mc = Math.exp((DMC - 149.6) / (-20.9));		/* Southern Yukon - White Spruce */
													/* White spruce/feather moss (2-4 cm) */
		mc = mc / 100.0;
		
		return mc;
	}

	/**
	 * @brief DC2MC calculates the moisture content in the forest floor.
	 * 
	 * The moisture content is calculated following the equations outlined in:
	 * 
	 * Lawson, B.D.; Frandsen, W.H.; Hawkes, B.C.; Dalrymple, G.N.  1997.  Probability of sustained smoldering
     * ignition for some Boreal forest duff types.  Nat. Resour. Can., Can. For. Serv., Pac. For. Cent.,
     * Victoria, British Columbia.  For/ Manage. Note 63.
	 * @param DC
	 * @param eqn The equation to use.
	 * @return The moisture content as a fraction.
	 */
	public static double dc2mc(double DC, ForrestFloorEquation eqn) {
		double mc = 0;
		
		if (eqn == ForrestFloorEquation.DC_National_Standard)
			mc = 800.0 / Math.exp(DC / 400.0);				/* DC National Standard */
		else
			mc = 488.4 / Math.exp(DC / 267.9);				/* White spruce duff (6-10 cm) */
		
		mc = mc / 100.0;
		
		return(mc);
	}

	/**
	 * @brief Calculates the probability of burning for a smoldering fire.
	 * 
	 * Calculations are based on:
	 * 
	 * Hartford, R.A.  1989.  Smoldering combustion limits in peat as influenced
	 *       by moisture, mineral content, and organic bulk density.  Pages 282-286
	 *       in 10th Conf. on Fire and Forest Meteorology, April 17-21, 1989,
	 *       Ottawa, Ont.  AES, Downsview.
	 *
	 *    Frandsen, W.H.  1991.  Burning rate of smoldering peat.  Northwest Sci.
	 *       65(4):166-172
	 *
	 *    Lawson, B.D.; Frandsen, W.H.; Hawkes, B.C.; Darymple, G.N. 1997. Probability
	 *    	of sustained smoldering ignitions for some boreal forest duff types.
	 * 	 	Nat. Resour. Can., Can. For. Serv., North. For. Cent., Edmonton, Alberta.
	 * 	 	For. Manage. Note 63.
	 *
	 * FBP default values compiled from lengthy literature search
	 * (most inorganic ratios are currently a guess)
	 * @param mc
	 * @param Ri
	 * @param rhoB
	 * @param eqn The equation to use.
	 * @return
	 */
	public static double smolderingLimit(double mc, double Ri, double rhoB, SmolderingLimitEquation eqn) {
		double P, rhoO, Rm;
		final double[] Ash = { 17.2, 19.1, 26.1, 35.9, 12.4, 56.5 };
		final double[] Rho = { 46.4, 38.9, 56.3, 122.0, 21.8, 119.0 };
		final double[] B0 = { 13.9873, 13.2628, 8.0359, 332.5604, -8.8306, 327.3347 };
		final double[] B1 = { -0.3296, -0.1167, -0.0393, -1.2220, -0.0608, -3.7655 };
		final double[] B2 = { 0.4904, 0.3308, -0.0591, -2.1024, 0.8095, -8.7849 };
		final double[] B3 = { 0.0568, -0.2604, -0.0340, -1.2619, 0.2735, 2.6684 };
		
		/* Default values if zero or negative */
		if ((Ri < 0.0) || (rhoB <= 0.0)) {
			P = 0.0;
		}
		else {
			/* Hartford's calculation is used as a default */
			if (eqn == SmolderingLimitEquation.NULL || eqn == SmolderingLimitEquation.Peat_Moss) {
				rhoO = rhoB / (Ri + 1);                                    /* RAH-3 */
				Rm = (1 + Ri) * (mc);
				
				/* Calculate Hartford's probability, with a correction to the last term */
				P = 1 / (1 + Math.exp(-19.329 + 17.047 * Rm + 1.7170 * Ri + 23.059 * rhoO * Ri)); /* RAH-4 */
			}
			else {
				P = 1 / (1 + Math.exp(-(B0[eqn.toInt() - 2] + B1[eqn.toInt() - 2] * mc * 100.0 + B2[eqn.toInt() - 2] * Ash[eqn.toInt() - 2] + B3[eqn.toInt() - 2] * Rho[eqn.toInt() - 2])));
			}
		}
		return P;
	}
	
	/**
	 * Equations and their regions:
	 * 
	 * C1	1A		Cladonia						NF ( 101-5), MB (501-6)
	 * C1	1B		Pine-Cladonia, Spruce-Cladonia	AB-Whitecourt (702-2,702-8)
	 * C1	1C		Cladonia						SK (601-6)
	 * 
	 * C2	9C		Spruce							NWT (901-3)
	 * C2	9A		Spruce-Fir						NF (101-3)
	 * C2	9B		Spruce	NF (101-4)
	 * C2	9D		Pine-Spruce,Spruce,Spruce-Pine	MB (501-1),SK (601-4), AB-Kananaskis (701-9)
	 * C2	9E		Spruce, Spruce					AB-Whitecourt (702-6, 702-7)
	 * C2	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * 
	 * C3	6A		Closed Jack Pine/
	 * 			Lodgepole Pine, Pine-Spruce,
	 * 			Balsam Fir						NF (101-1), SK (601-7, 601-8),MB (501-2, 501-5, 501-9),AB-Kananaskis (701-5, 701-6), AB-Whitecourt (702-3), NWT (901-2)
	 * C3	6-5012	Jack Pine (JY2)					MB (501-2)
	 * C3	6-6017	Pine							SK (601-7)
	 * C3	6B		Pine, Jack Pine					AB-Whitecourt (702-1), NWT (901-1)
	 * C3	BC		Dry Pine
	 * 			Lodgepole Pine ( Dry )			BC-Prince George
	 * C3	BC		Moist Pine
	 * 			Lodgepole Pine ( Moist )		BC-Prince George
	 * 
	 * C4	6-5012	Jack Pine (JY2)					MB (501-2)
	 * C4	6A		See C3 6A above
	 * C4	6-7015	Lodgepole Pine (L4)				AB-Kananaskis (701-5)
	 * C4	6B		Pine, Jack Pine					AB-Whitecourt (702-1), NWT (901-1)
	 * C4	BC		Dry Pine
	 * 			Lodgepole Pine ( Dry )			BC-Prince George
	 * C4	BC		Moist Pine
	 * 			Lodgepole Pine ( Moist )		BC-Prince George
	 * 
	 * C5	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * C5	6A		See C3 6A above
	 * C5	9A		Spruce-Fir						NF (101-3)
	 * C5	9E		Spruce, Spruce					AB-Whitecourt (702-6, 702-7)
	 * 
	 * C6	BC		Dry Pine
	 * 			Lodgepole Pine (Dry)			BC-Prince George
	 * C6	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * C6	6A		See C3 6A above
	 * C6	6-5012	Jack Pine (JY2)					MB (501-2)
	 * C6	9C		Spruce	NWT (901-3)
	 * C6	9D		Pine-Spruce,Spruce,Spruce-Pine	MB (501-1),SK (601-4),  AB-Kananaskis (701-9)
	 * 
	 * C7	4BC		Interior Douglas Fir (
	 * 			open w/grass)					BC
	 * 
	 * D1	8C		Poplar-Birch, Poplar, Aspen		MB (501-4,501-8), NWT (901-6)
	 * D1	8A		Pine-Poplar, Aspen				AB-Whitecourt (702-4, 702-5)
	 * D1	8B		Aspen	SK (601-1)
	 * 
	 * D2	8		See Note
	 * 
	 * M1	7A		Spruce-Aspen-Pine				NWT (901-5)
	 * M1	7B		Poplar-Spruce-Pine				NWT (901-4)
	 * 
	 * M2	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * M2	9A		Spruce-Fir						NF (101-3)
	 * M2	9B		Spruce							NF (101-4)
	 * M2	9C		Spruce							NWT (901-3)
	 * M2	9D		Pine-Spruce,Spruce,Spruce-Pine	MB (501-1),SK (601-4),    AB-Kananaskis (701-9)
	 * M2	9E		Spruce, Spruce					AB-Whitecourt (702-6, 702-7)
	 * 
	 * M3	9A		Spruce-Fir						NF (101-3)
	 * M3	9B		Spruce							NF (101-4)
	 * M3	9C		Spruce							NWT (901-3)
	 * M3	9D		Pine-Spruce,Spruce,Spruce-Pine	MB (501-1),SK (601-4),    AB-Kananaskis (701-9)
	 * M3	9E		Spruce, Spruce					AB-Whitecourt (702-6, 702-7)
	 * M3	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * 
	 * M4	9BC		White Spruce-Subalpine Fir		BC-Prince George
	 * M4	9A		Spruce-Fir						NF (101-3)
	 * M4	9B		Spruce							NF (101-4)
	 * M4	9C		Spruce							NWT (901-3)
	 * M4	9D		Pine-Spruce,Spruce,Spruce-Pine	MB (501-1),SK (601-4),    AB-Kananaskis (701-9)
	 * M4	9E		Spruce, Spruce					AB-Whitecourt (702-6, 702-7)
	 * 
	 * S1	2A		Cutover-Bracken, Fir regen-open	BC-L Cowichan (802-2, 802-3)
	 * S2	2A		Cutover-Bracken, Fir regen-open	BC-L Cowichan (802-2, 802-3)
	 * S3	2A		Cutover-Bracken, Fir regen-open	BC-L Cowichan (802-2, 802-3)
	 * 
	 * O1a	SaA		Grass,							Fir-grass-open	BC-100 Mile ( 801-3,  801-8)
	 * O1a	SbA		Grass							AB-Whitecourt ( 702-10)
	 * 
	 * O1b	SaA		Grass,							Fir-grass-open	BC-100 Mile ( 801-3,  801-8)
	 * O1b	SbA		Grass							AB-Whitecourt ( 702-10)
	 * @param WippEqn
	 * @param fbp
	 * @param prov
	 * @param FFMC
	 * @param DMC
	 * @param DC
	 * @param ISI
	 * @param BUI
	 * @return
	 */
	public static double wippCalc(WIPPEquation WippEqn, FBPType fbp, Province prov, double FFMC, double DMC, double DC, double ISI, double BUI)
	{
		double P = 0;
		WIPPEquation eqn = WIPPEquation.NULL;

		if (WippEqn == WIPPEquation.NULL) {
			switch (fbp) {
			case C1:
				if (prov ==  Province.Newfoundland_and_Labrador ||
					prov == Province.Manitoba)
					eqn = WIPPEquation.wipp_1A;
				else if (prov == Province.Saskatchewan)
					eqn = WIPPEquation.wipp_1C;
				else
					eqn = WIPPEquation.wipp_1B;	/* Alberta is set as the default */
				break;
			case C2:
				if (prov == Province.Northwest_Territories || prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_9C;
				else if (prov == Province.Newfoundland_and_Labrador)
					eqn = WIPPEquation.wipp_9A;  /* or 9B */
				else if (prov == Province.Manitoba ||
						prov == Province.Saskatchewan ||
						prov == Province.Alberta)
					eqn = WIPPEquation.wipp_9D;
				else if (prov == Province.British_Columbia)
					eqn = WIPPEquation.wipp_9BC;
				else
					eqn = WIPPEquation.wipp_9D;
				break;
			case C3:
			case C4:
			case C6:
				if (prov == Province.Newfoundland_and_Labrador ||
					prov == Province.Saskatchewan ||
					prov == Province.Manitoba ||
					prov == Province.Alberta ||
					prov == Province.Northwest_Territories ||
					prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_6A;
				else if (prov == Province.British_Columbia)
					eqn = WIPPEquation.wipp_BC_Dry_Pine;
				else
					eqn = WIPPEquation.wipp_6A;
				break;
			case C5:
				if (prov == Province.Newfoundland_and_Labrador ||
					prov == Province.Saskatchewan ||
					prov == Province.Manitoba ||
					prov == Province.Alberta ||
					prov == Province.Northwest_Territories ||
					prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_6A;
				else if (prov == Province.British_Columbia)
					eqn = WIPPEquation.wipp_9BC;
				else
					eqn = WIPPEquation.wipp_6A;
				break;
			case C7:
				eqn = WIPPEquation.wipp_4BC;
				break;
			case D1:
				if (prov == Province.Manitoba ||
					prov == Province.Northwest_Territories ||
					prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_8C;
				else if(prov == Province.Alberta)
					eqn = WIPPEquation.wipp_8A;
				else if(prov == Province.Saskatchewan)
					eqn = WIPPEquation.wipp_8B;
				else
					eqn = WIPPEquation.wipp_8A;
				break;
			case D2:
				eqn = WIPPEquation.wipp_8;
				break;
			case M1:
				eqn = WIPPEquation.wipp_7A;
				break;
			case M2:
				if (prov == Province.British_Columbia)
					eqn = WIPPEquation.wipp_9BC;
				else if(prov == Province.Newfoundland_and_Labrador)
					eqn = WIPPEquation.wipp_9A;
				else if(prov == Province.Northwest_Territories ||
						prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_9C;
				else
					eqn = WIPPEquation.wipp_9D;
				break;
			case M3:
			case M4:
				if (prov == Province.Newfoundland_and_Labrador)
					eqn = WIPPEquation.wipp_9A;
				else if (prov == Province.Northwest_Territories ||
						prov == Province.Nunavut)
					eqn = WIPPEquation.wipp_9C;
				else if(prov == Province.British_Columbia)
					eqn = WIPPEquation.wipp_9BC;
				else
					eqn = WIPPEquation.wipp_9D;
				break;
			case S1:
			case S2:
			case S3:
				eqn = WIPPEquation.wipp_2A;
				break;
			case O1a:
			case O1b:
				eqn = WIPPEquation.wipp_SbA;
				break;
			default:
				break;
			}
		}

		/* The 26 equations */
		if (eqn == WIPPEquation.wipp_1A)
			P = 1 / (1 + Math.exp(5.061 - 0.086 * FFMC));
		else if (eqn == WIPPEquation.wipp_1B)
			P = 1 / (1 + Math.exp(1.965 - 0.704 * ISI));
		else if (eqn == WIPPEquation.wipp_1C)
			P = 1 / (1 + Math.exp(0.837 - 1.020 * ISI));
		else if (eqn == WIPPEquation.wipp_2A)
			P = 1 / (1 + Math.exp(7.219 - 0.107 * FFMC));
		else if (eqn == WIPPEquation.wipp_4BC)
			P = 1 / (1 + Math.exp(1.563 - 0.005 * BUI - 0.478 * ISI));
		else if (eqn == WIPPEquation.wipp_6__5012)
			P = 1 / (1 + Math.exp(3.731 - 0.079 * DMC - 0.185 * ISI));
		else if (eqn == WIPPEquation.wipp_6__6017)
			P = 1 / (1 + Math.exp(1.754 - 0.021 * DMC - 0.282 * ISI));
		else if (eqn == WIPPEquation.wipp_6__7015)
			P = 1 / (1 + Math.exp(2.199 - 0.022 * DMC - 0.119 * ISI));
		else if (eqn == WIPPEquation.wipp_6A)
			P = 1 / (1 + Math.exp(2.199 - 0.021 * DMC - 0.265 * ISI));
		else if (eqn == WIPPEquation.wipp_6B)
			P = 1 / (1 + Math.exp(14.424 - 0.171 * FFMC - 0.017 * DMC));
		else if (eqn == WIPPEquation.wipp_7A)
			P = 1 / (1 + Math.exp(25.540 - 0.264 * FFMC - 0.036 * DMC));
		else if (eqn == WIPPEquation.wipp_7B)
			P = 1 / (1 + Math.exp(45.827 - 0.491 * FFMC));
		else if (eqn == WIPPEquation.wipp_8)
			P = 1 / (1 + Math.exp(14.0 - 0.121 * FFMC - 0.010 * DMC));
		else if (eqn == WIPPEquation.wipp_8A)
			P = 1 / (1 + Math.exp(3.503 - 0.044 * DMC - 0.407 * ISI));
		else if (eqn == WIPPEquation.wipp_8B)
			P = 1 / (1 + Math.exp(5.026 - 0.233 * ISI));
		else if (eqn == WIPPEquation.wipp_8C)
			P = 1 / (1 + Math.exp(12.781 - 0.121 * FFMC - 0.032 * DMC));
		else if (eqn == WIPPEquation.wipp_9A)
			P = 1 / (1 + Math.exp(2.144 - 0.423 * ISI));
		else if (eqn == WIPPEquation.wipp_9B)
			P = 1 / (1 + Math.exp(10.675 - 0.112 * FFMC - 0.100 * DMC));
		else if (eqn == WIPPEquation.wipp_9BC)
			P = 1 / (1 + Math.exp(2.766 - 0.005 * DC - 0.396 * ISI));
		else if (eqn == WIPPEquation.wipp_9C)
			P = 1 / (1 + Math.exp(33.299 - 0.353 * FFMC - 0.057 * DMC));
		else if (eqn == WIPPEquation.wipp_9D)
			P = 1 / (1 + Math.exp(11.677 - 0.123 * FFMC - 0.027 * DMC));
		else if (eqn == WIPPEquation.wipp_9E)
			P = 1 / (1 + Math.exp(6.438 - 0.077 * DMC - 0.357 * ISI));
		else if (eqn == WIPPEquation.wipp_BC_Dry_Pine)
			P = 1 / (1 + Math.exp(2.107 - 0.727 * ISI));
		else if (eqn == WIPPEquation.wipp_BC_Moist_Pine)
			P = 1 / (1 + Math.exp(2.146 - 0.009 * BUI -0.349 * ISI));
		else if (eqn == WIPPEquation.wipp_SaA)
			P = 1 / (1 + Math.exp(0.161 - 0.016 * DMC -0.240 * ISI));
		else if (eqn == WIPPEquation.wipp_SbA)
			P = 1 / (1 + Math.exp(46.942 - 0.508 * FFMC -0.063 * DMC));
		
		return P;
	}
	
	/**
	 * 
	 * @author Travis Redpath
	 */
	public enum FuelType {
		NULL,
		Automatic_Ignition,
		Ponderosa_Pine_Litter,
		Rotten_Chunky_Punky_Wood,
		Punky_Wood_Powder_Deep,
		Punky_Wood_Powder_Shallow,
		Lodgepole_Pine_Duff,
		Douglas_Fir_Duff,
		High_Altitude_Mixed_Mainly_Englemann_Spruce,
		Peat_Moss;

		@Override
		public String toString() {
			String toReturn = "";
			switch (this) {
			case Automatic_Ignition:
			case Ponderosa_Pine_Litter:
			case Lodgepole_Pine_Duff:
			case Douglas_Fir_Duff:
				toReturn = this.name();
				toReturn = toReturn.replace('_', ' ');
				break;
			case Rotten_Chunky_Punky_Wood:
				toReturn = "Rotten, chunky, punky wood";
				break;
			case Punky_Wood_Powder_Deep:
				toReturn = "Punky wood powder, deep (4.8cm)";
				break;
			case Punky_Wood_Powder_Shallow:
				toReturn = "Punky wood powder, shallow (2.4cm)";
				break;
			case High_Altitude_Mixed_Mainly_Englemann_Spruce:
				toReturn = "High altitude, mixed, mainly englemann spruce";
				break;
			case Peat_Moss:
				toReturn = "Peat moss (commercial)";
				break;
			case NULL:
				break;
			}
			return toReturn;
		}

		public int toInt() {
			switch (this) {
			case Automatic_Ignition:
				return 0;
			case Ponderosa_Pine_Litter:
				return 1;
			case Rotten_Chunky_Punky_Wood:
				return 2;
			case Punky_Wood_Powder_Deep:
				return 3;
			case Punky_Wood_Powder_Shallow:
				return 4;
			case Lodgepole_Pine_Duff:
				return 5;
			case Douglas_Fir_Duff:
				return 6;
			case High_Altitude_Mixed_Mainly_Englemann_Spruce:
				return 7;
			case Peat_Moss:
				return 8;
			case NULL:
				break;
			}
			return -1;
		}
		
		public static FuelType fromInt(int i) {
			switch (i) {
			case 0:
				return Automatic_Ignition;
			case 1:
				return Ponderosa_Pine_Litter;
			case 2:
				return Rotten_Chunky_Punky_Wood;
			case 3:
				return Punky_Wood_Powder_Deep;
			case 4:
				return Punky_Wood_Powder_Shallow;
			case 5:
				return Lodgepole_Pine_Duff;
			case 6:
				return Douglas_Fir_Duff;
			case 7:
				return High_Altitude_Mixed_Mainly_Englemann_Spruce;
			case 8:
				return Peat_Moss;
			}
			return NULL;
		}
	}

	/**
	 * 
	 * @author Travis Redpath
	 */
	public enum MoistureContentEquation {
		NULL,
		DMC_National_Standard,
		Southern_Interior_BC,
		Southern_Yukon_Undifferentiated_Duff,
		Southern_Yukon_Reindeer_Lichen,
		Southern_Yukon_Other;

		@Override
		public String toString() {
			switch (this) {
			case DMC_National_Standard:
				return "DMC National Standard and Coastal B.C. CWH";
			case Southern_Interior_BC:
				return "Southern Interior B.C. (2-4 cm)";
			case Southern_Yukon_Undifferentiated_Duff:
				return "Southern Yukon - Pine/White Spruce, Feather moss, Sphagnum and, Undifferentiated duff (2-4 cm)";
			case Southern_Yukon_Reindeer_Lichen:
				return "Southern Yukon - Pine/White Spruce, Reindeer lichen (2-4 cm)";
			case Southern_Yukon_Other:
				return "Southern Yukon - White Spruce, White spruce/feather moss (2-4 cm)";
			case NULL:
				break;
			}
			return "";
		}
		
		public int toInt() {
			switch (this) {
			case DMC_National_Standard:
				return 1;
			case Southern_Interior_BC:
				return 2;
			case Southern_Yukon_Undifferentiated_Duff:
				return 3;
			case Southern_Yukon_Reindeer_Lichen:
				return 4;
			case Southern_Yukon_Other:
				return 5;
			case NULL:
				break;
			}
			return -1;
		}
		
		public static MoistureContentEquation fromInt(int i) {
			switch (i) {
			case 1:
				return DMC_National_Standard;
			case 2:
				return Southern_Interior_BC;
			case 3:
				return Southern_Yukon_Undifferentiated_Duff;
			case 4:
				return Southern_Yukon_Reindeer_Lichen;
			case 5:
				return Southern_Yukon_Other;
			}
			return NULL;
		}
	}

	/**
	 * 
	 * @author Travis Redpath
	 */
	public enum ForrestFloorEquation {
		NULL,
		DC_National_Standard,
		White_Spruce_Duff;

		@Override
		public String toString() {
			switch (this) {
			case DC_National_Standard:
				return "DC National Standard";
			case White_Spruce_Duff:
				return "White spruce duff (6-10 cm)";
			case NULL:
				break;
			}
			return "";
		}
		
		public int toInt() {
			switch (this) {
			case DC_National_Standard:
				return 5;
			case White_Spruce_Duff:
				return 6;
			case NULL:
				break;
			}
			return -1;
		}
		
		public static ForrestFloorEquation fromInt(int i) {
			switch (i) {
			case 5:
				return DC_National_Standard;
			case 6:
				return White_Spruce_Duff;
			}
			return NULL;
		}
	}

	/**
	 * 
	 * @author Travis Redpath
	 */
	public enum SmolderingLimitEquation {
		NULL,
		Peat_Moss,
		Upper_feather_moss,
		Lower_feather_moss,
		Reindeer_lichen,
		White_Spruce_Duff,
		Upper_Sphagnum,
		Lower_Sphagnum;

		@Override
		public String toString() {
			switch (this) {
			case Peat_Moss:
				return "Peat moss (Hartford, 1989)";
			case Upper_feather_moss:
				return "Upper feather moss";
			case Lower_feather_moss:
				return "Lower feather moss";
			case Reindeer_lichen:
				return "Reindeer lichen/feather moss";
			case White_Spruce_Duff:
				return "White spruce duff";
			case Upper_Sphagnum:
				return "Upper sphagnum";
			case Lower_Sphagnum:
				return "Lower sphagnum";
			case NULL:
				break;
			}
			return "";
		}
		
		public int toInt() {
			switch (this) {
			case Peat_Moss:
				return 1;
			case Upper_feather_moss:
				return 2;
			case Lower_feather_moss:
				return 3;
			case Reindeer_lichen:
				return 4;
			case White_Spruce_Duff:
				return 5;
			case Upper_Sphagnum:
				return 6;
			case Lower_Sphagnum:
				return 7;
			case NULL:
				break;
			}
			return -1;
		}
		
		public static SmolderingLimitEquation fromInt(int i) {
			switch (i) {
			case 1:
				return Peat_Moss;
			case 2:
				return Upper_feather_moss;
			case 3:
				return Lower_feather_moss;
			case 4:
				return Reindeer_lichen;
			case 5:
				return White_Spruce_Duff;
			case 6:
				return Upper_Sphagnum;
			case 7:
				return Lower_Sphagnum;
			}
			return NULL;
		}
	}
	
	/**
	 * 
	 * @author Travis Redpath
	 */
	public enum Province {
		NULL,
		Alberta,
		British_Columbia,
		Manitoba,
		New_Brunswich,
		Newfoundland_and_Labrador,
		Northwest_Territories,
		Nova_Scotia,
		Nunavut,
		Ontario,
		Prince_Edward_Island,
		Quebec,
		Saskatchewan,
		Yukon;

		@Override
		public String toString() {
			return this.name().replace('_', ' ');
		}
		
		public String toAbbreviation() {
			switch (this) {
			case Alberta:
				return "AB";
			case British_Columbia:
				return "BC";
			case Manitoba:
				return "MB";
			case New_Brunswich:
				return "NB";
			case Newfoundland_and_Labrador:
				return "NL";
			case Northwest_Territories:
				return "NT";
			case Nova_Scotia:
				return "NS";
			case Nunavut:
				return "NU";
			case Ontario:
				return "ON";
			case Prince_Edward_Island:
				return "PE";
			case Quebec:
				return "QC";
			case Saskatchewan:
				return "SK";
			case Yukon:
				return "YT";
			case NULL:
				break;
			}
			return "";
		}
	}
	
	public enum FBPType {
		NULL,
		C1,
		C2,
		C3,
		C4,
		C5,
		C6,
		C7,
		D1,
		D2,
		M1,
		M2,
		M3,
		M4,
		S1,
		S2,
		S3,
		O1,
		O1a,
		O1b;

		@Override
		public String toString() {
			return this.name();
		}
		
		public static FBPType fromString(String val) {
			FBPType[] values = FBPType.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].toString().equalsIgnoreCase(val))
					return values[i];
			}
			return NULL;
		}
	}
	
	public enum WIPPEquation {
		NULL,
		wipp_1A,
		wipp_1B,
		wipp_1C,
		wipp_2A,
		wipp_4BC,
		wipp_6__5012,
		wipp_6__6017,
		wipp_6__7015,
		wipp_6A,
		wipp_6B,
		wipp_7A,
		wipp_7B,
		wipp_8,
		wipp_8A,
		wipp_8B,
		wipp_8C,
		wipp_9A,
		wipp_9B,
		wipp_9BC,
		wipp_9C,
		wipp_9D,
		wipp_9E,
		wipp_BC_Dry_Pine,
		wipp_BC_Moist_Pine,
		wipp_SaA,
		wipp_SbA;
		
		@Override
		public String toString() {
			return this.name().replace("wipp_", "").replace("__", "-").replace('_', ' ');
		}
	}
}
