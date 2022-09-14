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
	static PrintStream undislodgeableSynonymical = null;
	public void subdivideFunnelform(int casavi_gardenmaker,
			String pinon_unfallowed) {
		if (casavi_gardenmaker > 10) {
			subdivideFunnelform(casavi_gardenmaker++, pinon_unfallowed);
		}
		Tracer.tracepointWeaknessStart("CWE584", "A", "Return Inside Finally");
		File file;
		Scanner freader;
		String absPath = null;
		GetAbsolutePath getpath = new GetAbsolutePath(pinon_unfallowed,
				WaterFowl.undislodgeableSynonymical);
		boolean validPath = false;
		Tracer.tracepointVariableString("taintedValue", pinon_unfallowed);
		try {
			absPath = getpath.getAbsolutePath();
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			validPath = true;
			Tracer.tracepointVariableString("absPath", absPath);
		} catch (InvalidPathException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			WaterFowl.undislodgeableSynonymical
					.println("STONESOUP: Absolute path to file was not found.");
		}
		if (validPath) {
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				file = new File(absPath);
				freader = new Scanner(file);
				while (freader.hasNextLine()) {
					WaterFowl.undislodgeableSynonymical.println(freader
							.nextLine());
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (NullPointerException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(WaterFowl.undislodgeableSynonymical);
				throw e;
			} catch (FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				WaterFowl.undislodgeableSynonymical
						.println("STONESOUP: File not found.");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}
	private static final java.util.concurrent.atomic.AtomicBoolean sphaerialesCrumpled = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (sphaerialesCrumpled.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpYqT3o4_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File mucocellulosePradeep = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!mucocellulosePradeep.getParentFile().exists()
					&& !mucocellulosePradeep.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.undislodgeableSynonymical = new PrintStream(
							new FileOutputStream(mucocellulosePradeep, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException workboxBlunnen) {
					System.err.printf("Failed to open log file.  %s\n",
							workboxBlunnen.getMessage());
					WaterFowl.undislodgeableSynonymical = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							workboxBlunnen);
				} catch (FileNotFoundException coumaSeallike) {
					System.err.printf("Failed to open log file.  %s\n",
							coumaSeallike.getMessage());
					WaterFowl.undislodgeableSynonymical = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							coumaSeallike);
				}
				if (WaterFowl.undislodgeableSynonymical != null) {
					try {
						String ecumenical_edgrew = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (ecumenical_edgrew == null
								|| !ecumenical_edgrew.equals("1")) {
							String goniostat_palladous = System
									.getenv("RAMADOSS_MOST");
							if (null != goniostat_palladous) {
								File brinehouse_consumptive = new File(
										goniostat_palladous);
								if (brinehouse_consumptive.exists()
										&& !brinehouse_consumptive
												.isDirectory()) {
									try {
										String clubfisted_outbent;
										Scanner unfretting_heterotaxy = new Scanner(
												brinehouse_consumptive, "UTF-8")
												.useDelimiter("\\A");
										if (unfretting_heterotaxy.hasNext())
											clubfisted_outbent = unfretting_heterotaxy
													.next();
										else
											clubfisted_outbent = "";
										if (null != clubfisted_outbent) {
											mimiambicsPunicin(3, null, null,
													null, clubfisted_outbent,
													null, null);
										}
									} catch (FileNotFoundException disinfectorResedaceae) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												disinfectorResedaceae);
									}
								}
							}
						}
					} finally {
						WaterFowl.undislodgeableSynonymical.close();
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
	public void mimiambicsPunicin(int plainlyDrang, String... eruciformSubgenus) {
		String cristispiraPrecontact = null;
		int araraPotrack = 0;
		for (araraPotrack = 0; araraPotrack < eruciformSubgenus.length; araraPotrack++) {
			if (araraPotrack == plainlyDrang)
				cristispiraPrecontact = eruciformSubgenus[araraPotrack];
		}
		int microgramme_sulfochloride = 0;
		subdivideFunnelform(microgramme_sulfochloride, cristispiraPrecontact);
	}
	static class InvalidPathException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidPathException(String msg) {
			super(msg);
		}
	}
	static class GetAbsolutePath {
		private String fileName;
		private PrintStream output;

		public GetAbsolutePath(String fileName, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpYqT3o4_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpYqT3o4_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"GetAbsolutePath.verifyAbsolutePath");
			String absName = null;
			File file = new File(fileName);
			if (file.exists()) {
				absName = file.getAbsolutePath();
			} else {
				throw (new InvalidPathException("No such file: " + fileName));
			}
			return absName;
		}

		@SuppressWarnings("finally")
		public String getAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpYqT3o4_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"GetAbsolutePath.getAbsolutePath");
			String absName = null;
			try {
				absName = this.verifyAbsolutePath();
			} catch (InvalidPathException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				output.println("STONESOUP: Error in verifying absolute path\n");
				throw e;
			} finally {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				return absName;
			}
		}
	}
}
