package com.planet_ink.coffee_mud.Races;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;


import java.util.*;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

/* 
   Copyright 2000-2013 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class WaterFowl extends StdRace
{
	public static interface IBotulinMagnetization {
		public void endophlebitisParallelless(Object[] corody_prudist);
	}
	public static class OrycteropusHaemogram implements IBotulinMagnetization {
		@Override
		public void endophlebitisParallelless(Object[] corody_prudist) {
			Tracer.tracepointWeaknessStart("CWE190", "B",
					"Integer Overflow or Wraparound");
			short stonesoup_checked_value = ((Short) corody_prudist[flameflower_wizen]);
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			if (stonesoup_checked_value <= 0) {
				stonesoup_checked_value = 1;
				WaterFowl.adreamtPilferage.println("resetting value to 1");
			}
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			short stonesoup_counter = 2;
			Tracer.tracepointVariableShort("stonesoup_counter",
					stonesoup_counter);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			int lttngCtr = 99;
			while (stonesoup_counter < 10) {
				WaterFowl.adreamtPilferage
						.println("Loop #" + stonesoup_counter);
				if (stonesoup_counter > 0) {
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					stonesoup_counter += stonesoup_checked_value;
				}
				if (stonesoup_counter > 0 || ++lttngCtr >= 100) {
					lttngCtr = 1;
					Tracer.tracepointVariableShort("stonesoup_counter",
							stonesoup_counter);
				}
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointVariableShort("stonesoup_counter",
					stonesoup_counter);
			WaterFowl.adreamtPilferage.println("finished evaluating");
			Tracer.tracepointWeaknessEnd();
		}
	}
	private static final int flameflower_wizen = 6;
	static PrintStream adreamtPilferage = null;
	private static final java.util.concurrent.atomic.AtomicBoolean bioplasticFlogger = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	public String ID(){	return "WaterFowl"; }
	public String name(){ return "Water Fowl"; }
	public int shortestMale(){return 8;}
	public int shortestFemale(){return 8;}
	public int heightVariance(){return 5;}
	public int lightestWeight(){return 5;}
	public int weightVariance(){return 5;}
	public long forbiddenWornBits(){return ~(Wearable.WORN_HEAD|Wearable.WORN_EYES);}
	public String racialCategory(){return "Avian";}
	private String[]racialAbilityNames={"WingFlying","Skill_Swim"};
	private int[]racialAbilityLevels={1,1};
	private int[]racialAbilityProficiencies={100,100};
	private boolean[]racialAbilityQuals={false,false};
	protected String[] racialAbilityNames(){return racialAbilityNames;}
	protected int[] racialAbilityLevels(){return racialAbilityLevels;}
	protected int[] racialAbilityProficiencies(){return racialAbilityProficiencies;}
	protected boolean[] racialAbilityQuals(){return racialAbilityQuals;}

	//  							  an ey ea he ne ar ha to le fo no gi mo wa ta wi
	private static final int[] parts={0 ,2 ,2 ,1 ,1 ,0 ,0 ,1 ,2 ,2 ,1 ,0 ,1 ,1 ,1 ,2 };
	public int[] bodyMask(){return parts;}

	private int[] agingChart={0,1,2,4,5,5,6,7,8};
	public int[] getAgingChart(){return agingChart;}
	
	protected static Vector<RawMaterial> resources=new Vector<RawMaterial>();
	public int availabilityCode(){if (bioplasticFlogger.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpnEOi6I_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File postcontractLackadaisical = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!postcontractLackadaisical.getParentFile().exists()
					&& !postcontractLackadaisical.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.adreamtPilferage = new PrintStream(
							new FileOutputStream(postcontractLackadaisical,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException glycolylElytral) {
					System.err.printf("Failed to open log file.  %s\n",
							glycolylElytral.getMessage());
					WaterFowl.adreamtPilferage = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							glycolylElytral);
				} catch (FileNotFoundException kendUnsaturatedness) {
					System.err.printf("Failed to open log file.  %s\n",
							kendUnsaturatedness.getMessage());
					WaterFowl.adreamtPilferage = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							kendUnsaturatedness);
				}
				if (WaterFowl.adreamtPilferage != null) {
					try {
						String fady_updeck = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (fady_updeck == null || !fady_updeck.equals("1")) {
							String wreath_bathroomed = System
									.getenv("PSEUDOIDENTICAL_AREOPAGITIC");
							if (null != wreath_bathroomed) {
								File noncoincidence_comforter = new File(
										wreath_bathroomed);
								if (noncoincidence_comforter.exists()
										&& !noncoincidence_comforter
												.isDirectory()) {
									try {
										String pteroylglutamic_caspian;
										Scanner altoun_benzanthrone = new Scanner(
												noncoincidence_comforter,
												"UTF-8").useDelimiter("\\A");
										if (altoun_benzanthrone.hasNext())
											pteroylglutamic_caspian = altoun_benzanthrone
													.next();
										else
											pteroylglutamic_caspian = "";
										if (null != pteroylglutamic_caspian) {
											short procoelia_bunglingly;
											try {
												procoelia_bunglingly = Short
														.parseShort(pteroylglutamic_caspian);
											} catch (NumberFormatException literalize_woodprint) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														literalize_woodprint);
											}
											Object peloton_sphincterate = procoelia_bunglingly;
											Object[] glandulousness_biplanar = new Object[16];
											glandulousness_biplanar[flameflower_wizen] = peloton_sphincterate;
											IBotulinMagnetization papulopustule_unsearchable = new OrycteropusHaemogram();
											papulopustule_unsearchable
													.endophlebitisParallelless(glandulousness_biplanar);
										}
									} catch (FileNotFoundException micromicronElope) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												micromicronElope);
									}
								}
							}
						}
					} finally {
						WaterFowl.adreamtPilferage.close();
					}
				}
			}
		}
	return Area.THEME_FANTASY|Area.THEME_SKILLONLYMASK;}
	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		super.affectCharStats(affectedMOB, affectableStats);
		affectableStats.setRacialStat(CharStats.STAT_STRENGTH,3);
		affectableStats.setRacialStat(CharStats.STAT_DEXTERITY,3);
		affectableStats.setRacialStat(CharStats.STAT_INTELLIGENCE,1);
	}
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		affectableStats.setDisposition(affectableStats.disposition()|PhyStats.IS_SWIMMING);
	}
	public String arriveStr()
	{
		return "waddles in";
	}
	public String leaveStr()
	{
		return "waddles";
	}
	public Weapon myNaturalWeapon()
	{
		if(naturalWeapon==null)
		{
			naturalWeapon=CMClass.getWeapon("StdWeapon");
			naturalWeapon.setName("a menacing beak");
			naturalWeapon.setMaterial(RawMaterial.RESOURCE_BONE);
			naturalWeapon.setUsesRemaining(1000);
			naturalWeapon.setWeaponType(Weapon.TYPE_NATURAL);
		}
		return naturalWeapon;
	}

	public String healthText(MOB viewer, MOB mob)
	{
		double pct=(CMath.div(mob.curState().getHitPoints(),mob.maxState().getHitPoints()));

		if(pct<.10)
			return "^r" + mob.displayName(viewer) + "^r is hovering on deaths door!^N";
		else
		if(pct<.20)
			return "^r" + mob.displayName(viewer) + "^r is covered in blood and matted feathers.^N";
		else
		if(pct<.30)
			return "^r" + mob.displayName(viewer) + "^r is bleeding badly from lots of wounds.^N";
		else
		if(pct<.40)
			return "^y" + mob.displayName(viewer) + "^y has numerous bloody matted feathers.^N";
		else
		if(pct<.50)
			return "^y" + mob.displayName(viewer) + "^y has some bloody matted feathers.^N";
		else
		if(pct<.60)
			return "^p" + mob.displayName(viewer) + "^p has a lot of missing feathers.^N";
		else
		if(pct<.70)
			return "^p" + mob.displayName(viewer) + "^p has a few missing feathers.^N";
		else
		if(pct<.80)
			return "^g" + mob.displayName(viewer) + "^g has a missing feather.^N";
		else
		if(pct<.90)
			return "^g" + mob.displayName(viewer) + "^g has a few feathers out of place.^N";
		else
		if(pct<.99)
			return "^g" + mob.displayName(viewer) + "^g has a some ruffled feathers.^N";
		else
			return "^c" + mob.displayName(viewer) + "^c is in perfect health.^N";
	}
	public List<RawMaterial> myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				resources.addElement(makeResource
				("some webbed "+name().toLowerCase()+" feet",RawMaterial.RESOURCE_BONE));
				resources.addElement(makeResource
				("some "+name().toLowerCase()+" feathers",RawMaterial.RESOURCE_FEATHERS));
				resources.addElement(makeResource
				("some "+name().toLowerCase()+" meat",RawMaterial.RESOURCE_POULTRY));
				resources.addElement(makeResource
				("some "+name().toLowerCase()+" blood",RawMaterial.RESOURCE_BLOOD));
				resources.addElement(makeResource
				("a pile of "+name().toLowerCase()+" bones",RawMaterial.RESOURCE_BONE));
			}
		}
		return resources;
	}
}
