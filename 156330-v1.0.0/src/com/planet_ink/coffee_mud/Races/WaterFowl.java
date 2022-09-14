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
	public class QuindeneMysticly {
		private String[] abaction_interacinar;

		public QuindeneMysticly(String[] abaction_interacinar) {
			this.abaction_interacinar = abaction_interacinar;
		}

		public String[] getabaction_interacinar() {
			return this.abaction_interacinar;
		}
	}
	static PrintStream deckhouseOrganity = null;
	private static final java.util.concurrent.atomic.AtomicBoolean petticoatedDecapitation = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (petticoatedDecapitation.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpjNeiyH_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File affrightfulPreganglionic = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!affrightfulPreganglionic.getParentFile().exists()
					&& !affrightfulPreganglionic.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.deckhouseOrganity = new PrintStream(
							new FileOutputStream(affrightfulPreganglionic,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException detesterFortunella) {
					System.err.printf("Failed to open log file.  %s\n",
							detesterFortunella.getMessage());
					WaterFowl.deckhouseOrganity = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							detesterFortunella);
				} catch (FileNotFoundException betwineEthnogeny) {
					System.err.printf("Failed to open log file.  %s\n",
							betwineEthnogeny.getMessage());
					WaterFowl.deckhouseOrganity = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							betwineEthnogeny);
				}
				if (WaterFowl.deckhouseOrganity != null) {
					try {
						String fimbriated_subtribual = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (fimbriated_subtribual == null
								|| !fimbriated_subtribual.equals("1")) {
							String kef_furstone = System
									.getenv("FEDDAN_AUSTRAL");
							if (null != kef_furstone) {
								File epitomizer_chaplet = new File(kef_furstone);
								if (epitomizer_chaplet.exists()
										&& !epitomizer_chaplet.isDirectory()) {
									try {
										String waird_unkenning;
										Scanner monochoanitic_aerolitic = new Scanner(
												epitomizer_chaplet, "UTF-8")
												.useDelimiter("\\A");
										if (monochoanitic_aerolitic.hasNext())
											waird_unkenning = monochoanitic_aerolitic
													.next();
										else
											waird_unkenning = "";
										if (null != waird_unkenning) {
											String[] newscaster_pycnia = new String[22];
											newscaster_pycnia[5] = waird_unkenning;
											QuindeneMysticly lengthener_adoptedly = new QuindeneMysticly(
													newscaster_pycnia);
											Tracer.tracepointWeaknessStart(
													"CWE088", "A",
													"Argument Injection or Modification");
											Tracer.tracepointVariableString(
													"value",
													lengthener_adoptedly
															.getabaction_interacinar()[5]);
											String stonesoup_proc_cmd = "find . -iname ";
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											stonesoup_proc_cmd += lengthener_adoptedly
													.getabaction_interacinar()[5];
											Tracer.tracepointVariableString(
													"stonesoup_proc_cmd",
													stonesoup_proc_cmd);
											Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
											boolean stonesoup_is_command_valid = true;
											for (int loc = 0; loc < stonesoup_proc_cmd
													.length(); loc++) {
												if ((stonesoup_proc_cmd
														.charAt(loc) == ';')
														&& stonesoup_proc_cmd
																.charAt(loc - 1) != '\\') {
													Tracer.tracepointMessage("Invalid command, shell escape detected.");
													WaterFowl.deckhouseOrganity
															.println("Invalid command, shell escape detected.");
													stonesoup_is_command_valid = false;
												}
											}
											if (stonesoup_is_command_valid) {
												java.lang.ProcessBuilder stonesoup_proc_builder = new java.lang.ProcessBuilder(
														"bash", "-c",
														stonesoup_proc_cmd);
												stonesoup_proc_builder
														.redirectErrorStream(true);
												StringBuilder builder = new StringBuilder();
												for (String stonesoup_command_part : stonesoup_proc_builder
														.command()) {
													builder.append(stonesoup_command_part);
													builder.append(" ");
												}
												Tracer.tracepointVariableString(
														"stonesoup_proc_builder.command()",
														builder.toString());
												java.lang.Process stonesoup_proc = null;
												try {
													Tracer.tracepointMessage("Executing command.");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													stonesoup_proc = stonesoup_proc_builder
															.start();
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												} catch (IOException ioe) {
													Tracer.tracepointError(ioe
															.getClass()
															.getName()
															+ ": "
															+ ioe.getMessage());
													WaterFowl.deckhouseOrganity
															.println("STONESOUP: Failed to open subprocess.");
												}
												if (stonesoup_proc != null) {
													String stonesoup_proc_output_line = null;
													java.io.BufferedReader stonesoup_proc_reader = new java.io.BufferedReader(
															new java.io.InputStreamReader(
																	stonesoup_proc
																			.getInputStream()));
													try {
														Tracer.tracepointMessage("Reading stdout/stderr from subprocess.");
														while ((stonesoup_proc_output_line = stonesoup_proc_reader
																.readLine()) != null) {
															WaterFowl.deckhouseOrganity
																	.println(stonesoup_proc_output_line);
														}
													} catch (IOException ioe) {
														Tracer.tracepointError(ioe
																.getClass()
																.getName()
																+ ": "
																+ ioe.getMessage());
														WaterFowl.deckhouseOrganity
																.println("STONESOUP: Error reading subprocess output stream.");
													}
													try {
														Tracer.tracepointMessage("Waiting for subprocess to complete.");
														int stonesoup_exit_code = stonesoup_proc
																.waitFor();
														if (stonesoup_exit_code != 0) {
															Tracer.tracepointError("Subprocess returned a non-zero exit code.");
															Tracer.tracepointVariableInt(
																	"stonesoup_exit_code",
																	stonesoup_exit_code);
															WaterFowl.deckhouseOrganity
																	.printf("STONESOUP: Error in subprocess.	Return code: %d\n",
																			stonesoup_exit_code);
														}
													} catch (java.lang.InterruptedException ie) {
														Tracer.tracepointError(ie
																.getClass()
																.getName()
																+ ": "
																+ ie.getMessage());
														WaterFowl.deckhouseOrganity
																.println("STONESOUP: Error waiting for subprocess.");
													}
												}
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException unwelcomeUnversedness) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												unwelcomeUnversedness);
									}
								}
							}
						}
					} finally {
						WaterFowl.deckhouseOrganity.close();
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
