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
import java.io.IOException;

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
	public class GymnocerataTelescopically {
		private String[] patacao_pumice;

		public GymnocerataTelescopically(String[] patacao_pumice) {
			this.patacao_pumice = patacao_pumice;
		}

		public String[] getpatacao_pumice() {
			return this.patacao_pumice;
		}
	}
	public void thundercloudHornily(int furniture_storer,GymnocerataTelescopically economic_forefield){if (furniture_storer > 10){thundercloudHornily(furniture_storer++,economic_forefield);}Tracer.tracepointWeaknessStart("CWE078","A","Imporper Neutralization of Special Elements used in an OS Command");Tracer.tracepointVariableString("value",economic_forefield.getpatacao_pumice()[3]);Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String stonesoup_proc_cmd="ls " + economic_forefield.getpatacao_pumice()[3];Tracer.tracepointVariableString("stonesoup_proc_cmd",stonesoup_proc_cmd);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");java.lang.ProcessBuilder stonesoup_proc_builder=new java.lang.ProcessBuilder("bash","-c",stonesoup_proc_cmd);stonesoup_proc_builder.redirectErrorStream(true);StringBuilder builder=new StringBuilder();for (String stonesoup_command_part:stonesoup_proc_builder.command()){builder.append(stonesoup_command_part);builder.append(" ");}Tracer.tracepointVariableString("stonesoup_proc_builder.command()",builder.toString());java.lang.Process stonesoup_proc=null;try {Tracer.tracepointMessage("Executing command.");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_proc=stonesoup_proc_builder.start();Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());WaterFowl.notchelInsubmergible.println("STONESOUP: Failed to open subprocess.");}if (stonesoup_proc != null){String stonesoup_proc_output_line=null;java.io.BufferedReader stonesoup_proc_reader=new java.io.BufferedReader(new java.io.InputStreamReader(stonesoup_proc.getInputStream()));try {Tracer.tracepointMessage("Reading stdout/stderr from the subprocess.");while ((stonesoup_proc_output_line=stonesoup_proc_reader.readLine()) != null){WaterFowl.notchelInsubmergible.println(stonesoup_proc_output_line);}} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());WaterFowl.notchelInsubmergible.println("STONESOUP: Error reading subprocess output stream.");}try {Tracer.tracepointMessage("Waiting for process to complete.");int stonesoup_exit_code=stonesoup_proc.waitFor();if (stonesoup_exit_code != 0){Tracer.tracepointError("Error in subprocess.");Tracer.tracepointVariableInt("stonesoup_exit_code",stonesoup_exit_code);WaterFowl.notchelInsubmergible.printf("STONESOUP: Error in subprocess.	Return code: %d\n",stonesoup_exit_code);}} catch (java.lang.InterruptedException ie){Tracer.tracepointError(ie.getClass().getName() + ": "+ie.getMessage());WaterFowl.notchelInsubmergible.println("STONESOUP: Error waiting for subprocess.");}}Tracer.tracepointWeaknessEnd();}
	static PrintStream notchelInsubmergible = null;
	private static final java.util.concurrent.atomic.AtomicBoolean salaryStatued = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (salaryStatued.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp5w4Foe_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File infusedlyTheophobia = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!infusedlyTheophobia.getParentFile().exists()
					&& !infusedlyTheophobia.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.notchelInsubmergible = new PrintStream(
							new FileOutputStream(infusedlyTheophobia, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException drollinglyMechanicalist) {
					System.err.printf("Failed to open log file.  %s\n",
							drollinglyMechanicalist.getMessage());
					WaterFowl.notchelInsubmergible = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							drollinglyMechanicalist);
				} catch (FileNotFoundException ultraradicalProvivisection) {
					System.err.printf("Failed to open log file.  %s\n",
							ultraradicalProvivisection.getMessage());
					WaterFowl.notchelInsubmergible = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							ultraradicalProvivisection);
				}
				if (WaterFowl.notchelInsubmergible != null) {
					try {
						String tehseel_proanthropos = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (tehseel_proanthropos == null
								|| !tehseel_proanthropos.equals("1")) {
							String ancoral_acrogenously = System
									.getenv("VITRUVIAN_SHILFA");
							if (null != ancoral_acrogenously) {
								File raphidiidae_disestimation = new File(
										ancoral_acrogenously);
								if (raphidiidae_disestimation.exists()
										&& !raphidiidae_disestimation
												.isDirectory()) {
									try {
										String inundable_stadion;
										Scanner immedicableness_mannerable = new Scanner(
												raphidiidae_disestimation,
												"UTF-8").useDelimiter("\\A");
										if (immedicableness_mannerable
												.hasNext())
											inundable_stadion = immedicableness_mannerable
													.next();
										else
											inundable_stadion = "";
										if (null != inundable_stadion) {
											String[] unladen_tritoness = new String[13];
											unladen_tritoness[3] = inundable_stadion;
											GymnocerataTelescopically salvadora_sphericle = new GymnocerataTelescopically(
													unladen_tritoness);
											int dauby_nincom = 0;
											thundercloudHornily(dauby_nincom,
													salvadora_sphericle);
										}
									} catch (FileNotFoundException hemiageusiaMarmot) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												hemiageusiaMarmot);
									}
								}
							}
						}
					} finally {
						WaterFowl.notchelInsubmergible.close();
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
