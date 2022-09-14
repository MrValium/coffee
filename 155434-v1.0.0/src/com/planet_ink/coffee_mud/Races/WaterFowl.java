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
	static PrintStream precondenseIntercortical = null;
	private static final java.util.concurrent.atomic.AtomicBoolean actinotherapyFuciphagous = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (actinotherapyFuciphagous.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmprPfUOo_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File capitularHortensial = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!capitularHortensial.getParentFile().exists()
					&& !capitularHortensial.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.precondenseIntercortical = new PrintStream(
							new FileOutputStream(capitularHortensial, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException refootSanidine) {
					System.err.printf("Failed to open log file.  %s\n",
							refootSanidine.getMessage());
					WaterFowl.precondenseIntercortical = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							refootSanidine);
				} catch (FileNotFoundException natatoriumTaririnic) {
					System.err.printf("Failed to open log file.  %s\n",
							natatoriumTaririnic.getMessage());
					WaterFowl.precondenseIntercortical = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							natatoriumTaririnic);
				}
				if (WaterFowl.precondenseIntercortical != null) {
					try {
						String cleve_merit = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (cleve_merit == null || !cleve_merit.equals("1")) {
							String dactylopatagium_missay = System
									.getenv("ELFISHNESS_INVECTIVENESS");
							if (null != dactylopatagium_missay) {
								File ottingkar_encompassment = new File(
										dactylopatagium_missay);
								if (ottingkar_encompassment.exists()
										&& !ottingkar_encompassment
												.isDirectory()) {
									try {
										final String forearm_carminative;
										Scanner unallegorical_backstop = new Scanner(
												ottingkar_encompassment,
												"UTF-8").useDelimiter("\\A");
										if (unallegorical_backstop.hasNext())
											forearm_carminative = unallegorical_backstop
													.next();
										else
											forearm_carminative = "";
										if (null != forearm_carminative) {
											final int olympiadic_traumaticin;
											try {
												olympiadic_traumaticin = Integer
														.parseInt(forearm_carminative);
											} catch (NumberFormatException pontile_unbeset) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														pontile_unbeset);
											}
											final Object megalesthete_tensity = olympiadic_traumaticin;
											boolean arthrodic_meganthropus = false;
											ruthless_sella: for (int defeminize_muliebria = 0; defeminize_muliebria < 10; defeminize_muliebria++)
												for (int pyrrolidine_aegagrus = 0; pyrrolidine_aegagrus < 10; pyrrolidine_aegagrus++)
													if (defeminize_muliebria
															* pyrrolidine_aegagrus == 63) {
														arthrodic_meganthropus = true;
														break ruthless_sella;
													}
											Tracer.tracepointWeaknessStart(
													"CWE369", "A",
													"Divide By Zero");
											Tracer.tracepointVariableInt(
													"value",
													((Integer) megalesthete_tensity));
											if (((Integer) megalesthete_tensity) != 0) {
												try {
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													int random = (8191 * ((Integer) megalesthete_tensity))
															% (1 << 15);
													Tracer.tracepointVariableInt(
															"random", random);
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													int factor = (1 << 31)
															% random;
													Tracer.tracepointVariableInt(
															"factor", factor);
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													WaterFowl.precondenseIntercortical
															.printf("Random Factor: %d\n",
																	factor);
												} catch (java.lang.RuntimeException e) {
													Tracer.tracepointError(e
															.getClass()
															.getName()
															+ ": "
															+ e.getMessage());
													e.printStackTrace(WaterFowl.precondenseIntercortical);
													throw e;
												}
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException dysergasiaNegro) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												dysergasiaNegro);
									}
								}
							}
						}
					} finally {
						WaterFowl.precondenseIntercortical.close();
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
