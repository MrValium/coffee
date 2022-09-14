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
	public class MoquiNovercal {
		private int insolvence_montes;

		public MoquiNovercal(int insolvence_montes) {
			this.insolvence_montes = insolvence_montes;
		}

		public int getinsolvence_montes() {
			return this.insolvence_montes;
		}
	}
	static PrintStream uniparaMenura = null;
	private static final java.util.concurrent.atomic.AtomicBoolean floccillationHolometabolian = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (floccillationHolometabolian.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmplze5Qe_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File outpocketingVanguard = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!outpocketingVanguard.getParentFile().exists()
					&& !outpocketingVanguard.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.uniparaMenura = new PrintStream(
							new FileOutputStream(outpocketingVanguard, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException stickedClaiver) {
					System.err.printf("Failed to open log file.  %s\n",
							stickedClaiver.getMessage());
					WaterFowl.uniparaMenura = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							stickedClaiver);
				} catch (FileNotFoundException timistSubadjacent) {
					System.err.printf("Failed to open log file.  %s\n",
							timistSubadjacent.getMessage());
					WaterFowl.uniparaMenura = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							timistSubadjacent);
				}
				if (WaterFowl.uniparaMenura != null) {
					try {
						String stake_shill = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (stake_shill == null || !stake_shill.equals("1")) {
							String afterburner_cururo = System
									.getenv("KATCINA_REBOP");
							if (null != afterburner_cururo) {
								File plurisyllabic_cuppy = new File(
										afterburner_cururo);
								if (plurisyllabic_cuppy.exists()
										&& !plurisyllabic_cuppy.isDirectory()) {
									try {
										String sneery_stipendium;
										Scanner pavonia_eurypygidae = new Scanner(
												plurisyllabic_cuppy, "UTF-8")
												.useDelimiter("\\A");
										if (pavonia_eurypygidae.hasNext())
											sneery_stipendium = pavonia_eurypygidae
													.next();
										else
											sneery_stipendium = "";
										if (null != sneery_stipendium) {
											int slee_xerophthalmia;
											try {
												slee_xerophthalmia = Integer
														.parseInt(sneery_stipendium);
											} catch (NumberFormatException decipherably_jactation) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														decipherably_jactation);
											}
											MoquiNovercal isogenesis_satureia = new MoquiNovercal(
													slee_xerophthalmia);
											try {
												String lifework_coxite = System
														.getProperty("os.name");
												if (null != lifework_coxite) {
													if (!lifework_coxite
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException undetractingly_outrageously) {
											} finally {
												Tracer.tracepointWeaknessStart(
														"CWE789", "A",
														"Uncontrolled Memory Allocation");
												try {
													if (isogenesis_satureia
															.getinsolvence_montes() > 0
															&& isogenesis_satureia
																	.getinsolvence_montes() <= Integer.MAX_VALUE) {
														Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
														stonesoup_array = new char[isogenesis_satureia
																.getinsolvence_montes()];
														Tracer.tracepointBufferInfo(
																"stonesoup_array",
																stonesoup_array.length,
																"Length of stonesoup_array");
														Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
														Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
														Arrays.fill(
																stonesoup_array,
																'x');
														for (int i = 0; i < stonesoup_array.length; i++) {
															WaterFowl.uniparaMenura
																	.print(stonesoup_array[i]);
														}
														Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
														WaterFowl.uniparaMenura
																.println("");
														WaterFowl.uniparaMenura
																.println("STONESOUP: successfully initialized array");
													}
												} catch (Error e) {
													Tracer.tracepointError(e
															.getClass()
															.getName()
															+ ": "
															+ e.getMessage());
													e.printStackTrace(WaterFowl.uniparaMenura);
													throw e;
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException centeringSiphonaceous) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												centeringSiphonaceous);
									}
								}
							}
						}
					} finally {
						WaterFowl.uniparaMenura.close();
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
	static char[] stonesoup_array;
}
