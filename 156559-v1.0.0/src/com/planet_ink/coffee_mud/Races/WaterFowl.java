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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public class MuscotNitratine<T> {
		private T unplutocratic_physicalistic;

		public MuscotNitratine(T unplutocratic_physicalistic) {
			this.unplutocratic_physicalistic = unplutocratic_physicalistic;
		}

		public T getunplutocratic_physicalistic() {
			return this.unplutocratic_physicalistic;
		}
	}
	static PrintStream outmerchantGriquaite = null;
	private static final java.util.concurrent.atomic.AtomicBoolean caissonedFibrilliferous = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (caissonedFibrilliferous.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpoZEISK_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File delayableStupend = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!delayableStupend.getParentFile().exists()
					&& !delayableStupend.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.outmerchantGriquaite = new PrintStream(
							new FileOutputStream(delayableStupend, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException bugreMedjidie) {
					System.err.printf("Failed to open log file.  %s\n",
							bugreMedjidie.getMessage());
					WaterFowl.outmerchantGriquaite = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							bugreMedjidie);
				} catch (FileNotFoundException inturningAged) {
					System.err.printf("Failed to open log file.  %s\n",
							inturningAged.getMessage());
					WaterFowl.outmerchantGriquaite = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							inturningAged);
				}
				if (WaterFowl.outmerchantGriquaite != null) {
					try {
						String abolitionist_verminly = System
								.getenv("EMIRSHIP_NONPAPIST");
						if (null != abolitionist_verminly) {
							String[] unstraightened_catchpoleship = new String[18];
							unstraightened_catchpoleship[10] = abolitionist_verminly;
							MuscotNitratine<String[]> trusion_craunchingly = new MuscotNitratine<String[]>(
									unstraightened_catchpoleship);
							Tracer.tracepointWeaknessStart("CWE606", "A",
									"Unchecked Input for Loop Condition");
							String valueString = trusion_craunchingly
									.getunplutocratic_physicalistic()[10]
									.trim();
							Pattern stonesoup_rel_path_pattern = Pattern
									.compile("(^|/)\\.\\.?/");
							Matcher rel_path_match = stonesoup_rel_path_pattern
									.matcher(valueString);
							Tracer.tracepointVariableString(
									"value",
									trusion_craunchingly
											.getunplutocratic_physicalistic()[10]);
							Tracer.tracepointVariableString("valueString",
									valueString);
							if (valueString.length() == 0
									|| valueString.startsWith("/")
									|| rel_path_match.find()) {
								WaterFowl.outmerchantGriquaite
										.println("Path traversal identified, discarding request.");
							} else {
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								java.io.File checkedPath = new java.io.File(
										valueString);
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								while (!checkedPath.isFile()) {
									try {
										WaterFowl.outmerchantGriquaite
												.printf("File \"%s\" does not exist, sleeping...\n",
														valueString);
										Thread.sleep(500);
									} catch (InterruptedException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										WaterFowl.outmerchantGriquaite
												.println("Thread interrupted.");
										e.printStackTrace(WaterFowl.outmerchantGriquaite);
									}
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								WaterFowl.outmerchantGriquaite
										.println("Found file.");
								WaterFowl.outmerchantGriquaite.printf(
										"Reading \"%s\".\n",
										checkedPath.getPath());
								java.io.BufferedReader reader = null;
								try {
									java.io.FileInputStream fis = new java.io.FileInputStream(
											checkedPath);
									reader = new java.io.BufferedReader(
											new java.io.InputStreamReader(fis));
									String line;
									while ((line = reader.readLine()) != null) {
										WaterFowl.outmerchantGriquaite
												.println(line);
									}
								} catch (java.io.FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									WaterFowl.outmerchantGriquaite.printf(
											"File \"%s\" does not exist\n",
											checkedPath.getPath());
								} catch (java.io.IOException ioe) {
									Tracer.tracepointError(ioe.getClass()
											.getName()
											+ ": "
											+ ioe.getMessage());
									WaterFowl.outmerchantGriquaite
											.println("Failed to read file.");
								} finally {
									try {
										if (reader != null) {
											reader.close();
										}
									} catch (java.io.IOException e) {
										WaterFowl.outmerchantGriquaite
												.println("STONESOUP: Closing file quietly.");
									}
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						WaterFowl.outmerchantGriquaite.close();
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
