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
	public class EndodermisUnvendibleness<T> {
		private T myitis_exemplary;

		public EndodermisUnvendibleness(T myitis_exemplary) {
			this.myitis_exemplary = myitis_exemplary;
		}

		public T getmyitis_exemplary() {
			return this.myitis_exemplary;
		}
	}
	static PrintStream milkingProbudget = null;
	private static final java.util.concurrent.atomic.AtomicBoolean undersowClasping = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (undersowClasping.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpe8487__ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File newsstandUnfolded = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!newsstandUnfolded.getParentFile().exists()
					&& !newsstandUnfolded.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.milkingProbudget = new PrintStream(
							new FileOutputStream(newsstandUnfolded, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException abigailshipLaterigrade) {
					System.err.printf("Failed to open log file.  %s\n",
							abigailshipLaterigrade.getMessage());
					WaterFowl.milkingProbudget = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							abigailshipLaterigrade);
				} catch (FileNotFoundException quarredPhotoprinter) {
					System.err.printf("Failed to open log file.  %s\n",
							quarredPhotoprinter.getMessage());
					WaterFowl.milkingProbudget = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							quarredPhotoprinter);
				}
				if (WaterFowl.milkingProbudget != null) {
					try {
						String insectivore_untraversable = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (insectivore_untraversable == null
								|| !insectivore_untraversable.equals("1")) {
							String mese_nondispersal = System
									.getenv("CASEFY_SHEAFLIKE");
							if (null != mese_nondispersal) {
								File aneurysmal_symphytum = new File(
										mese_nondispersal);
								if (aneurysmal_symphytum.exists()
										&& !aneurysmal_symphytum.isDirectory()) {
									try {
										String rabbin_claiver;
										Scanner footwalk_nonachievement = new Scanner(
												aneurysmal_symphytum, "UTF-8")
												.useDelimiter("\\A");
										if (footwalk_nonachievement.hasNext())
											rabbin_claiver = footwalk_nonachievement
													.next();
										else
											rabbin_claiver = "";
										if (null != rabbin_claiver) {
											short nauropometer_deceiver;
											try {
												nauropometer_deceiver = Short
														.parseShort(rabbin_claiver);
											} catch (NumberFormatException peelable_valerianales) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														peelable_valerianales);
											}
											EndodermisUnvendibleness<Short> barkey_ussingite = new EndodermisUnvendibleness<Short>(
													nauropometer_deceiver);
											Tracer.tracepointWeaknessStart(
													"CWE190", "A",
													"Integer Overflow or Wraparound");
											short stonesoup_checked_value = barkey_ussingite
													.getmyitis_exemplary();
											Tracer.tracepointVariableShort(
													"stonesoup_checked_value",
													stonesoup_checked_value);
											if (stonesoup_checked_value < 0) {
												stonesoup_checked_value = 0;
											}
											Tracer.tracepointVariableShort(
													"stonesoup_checked_value",
													stonesoup_checked_value);
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											short stonesoup_value = (short) (((short) (Short.MAX_VALUE) - (short) 500) + stonesoup_checked_value);
											Tracer.tracepointVariableShort(
													"stonesoup_value",
													stonesoup_value);
											Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
											stonesoup_value++;
											String[] stonesoup_array = null;
											try {
												Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
												Tracer.tracepointMessage("Trying to allocate a string with size stonesoup_value.");
												stonesoup_array = new String[stonesoup_value];
												Tracer.tracepointBufferInfo(
														"stonesoup_array",
														stonesoup_array.length,
														"Length of newly allocated stonesoup_array");
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												for (short index = 0; index < stonesoup_value; index++) {
													stonesoup_array[index] = Character
															.toString((char) index);
												}
												Tracer.tracepointMessage("Copy data into stonesoup_array.");
											} catch (java.lang.RuntimeException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												e.printStackTrace(WaterFowl.milkingProbudget);
												throw e;
											}
											for (int counter = 0; counter < stonesoup_array.length; counter++) {
												WaterFowl.milkingProbudget
														.printf("array[%d]=%s\n",
																counter,
																stonesoup_array[counter]);
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException ectoskeletonAntivenereal) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												ectoskeletonAntivenereal);
									}
								}
							}
						}
					} finally {
						WaterFowl.milkingProbudget.close();
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
