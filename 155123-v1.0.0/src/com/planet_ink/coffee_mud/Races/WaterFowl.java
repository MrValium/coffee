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
	public class InobservantHaplodon {
		private short cered_ridgeboard;

		public InobservantHaplodon(short cered_ridgeboard) {
			this.cered_ridgeboard = cered_ridgeboard;
		}

		public short getcered_ridgeboard() {
			return this.cered_ridgeboard;
		}
	}
	static PrintStream boilinglyTalofibular = null;
	private static final java.util.concurrent.atomic.AtomicBoolean sacMiscut = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (sacMiscut.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpM7IQAn_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File bigginPhagolytic = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!bigginPhagolytic.getParentFile().exists()
					&& !bigginPhagolytic.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.boilinglyTalofibular = new PrintStream(
							new FileOutputStream(bigginPhagolytic, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unlaunchedDiscontentment) {
					System.err.printf("Failed to open log file.  %s\n",
							unlaunchedDiscontentment.getMessage());
					WaterFowl.boilinglyTalofibular = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unlaunchedDiscontentment);
				} catch (FileNotFoundException suiogothicPantagruel) {
					System.err.printf("Failed to open log file.  %s\n",
							suiogothicPantagruel.getMessage());
					WaterFowl.boilinglyTalofibular = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							suiogothicPantagruel);
				}
				if (WaterFowl.boilinglyTalofibular != null) {
					try {
						String propulsor_underpitch = System
								.getenv("MULTILIGHTED_INTERPOSE");
						if (null != propulsor_underpitch) {
							short dawdler_piebaldly;
							try {
								dawdler_piebaldly = Short
										.parseShort(propulsor_underpitch);
							} catch (NumberFormatException mae_overlargely) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										mae_overlargely);
							}
							InobservantHaplodon bracciale_dollarfish = new InobservantHaplodon(
									dawdler_piebaldly);
							int mashie_unlikeable = 0;
							while (true) {
								mashie_unlikeable++;
								if (mashie_unlikeable >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart("CWE191", "A",
									"Integer Underflow (Wrap or Wraparound)");
							short stonesoup_checked_value = bracciale_dollarfish
									.getcered_ridgeboard();
							Tracer.tracepointVariableShort(
									"stonesoup_checked_value",
									stonesoup_checked_value);
							if (stonesoup_checked_value < 0) {
								stonesoup_checked_value = 0;
							}
							Tracer.tracepointVariableShort(
									"stonesoup_checked_value",
									stonesoup_checked_value);
							Short[] stonesoup_some_values = new Short[] { 0, 1,
									2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
									15, 16, 17, 18, 19, 20 };
							short stonesoup_counter = -20;
							short stonesoup_offset = 40;
							Tracer.tracepointBufferInfo(
									"stonesoup_some_values",
									stonesoup_some_values.length,
									"Length of stonesoup_some_values");
							Tracer.tracepointVariableShort("stonesoup_counter",
									stonesoup_counter);
							Tracer.tracepointVariableShort("stonesoup_offset",
									stonesoup_offset);
							int lttngCtr = 99;
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
							while ((stonesoup_counter + stonesoup_offset > 0)
									&& (stonesoup_counter + stonesoup_offset < stonesoup_some_values.length)) {
								WaterFowl.boilinglyTalofibular.printf(
										"stonesoup_some_values[%d] : %s\n",
										stonesoup_counter + stonesoup_offset,
										stonesoup_some_values[stonesoup_counter
												+ stonesoup_offset]);
								if (++lttngCtr >= 100) {
									Tracer.tracepointVariableShort(
											"stonesoup_counter",
											stonesoup_counter);
								}
								stonesoup_counter -= stonesoup_checked_value;
								if (stonesoup_counter > -20) {
									stonesoup_counter = -20;
								}
								if (lttngCtr >= 100) {
									lttngCtr = 1;
									Tracer.tracepointVariableShort(
											"stonesoup_counter",
											stonesoup_counter);
								}
							}
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointBufferInfo(
									"stonesoup_some_values",
									stonesoup_some_values.length,
									"Length of stonesoup_some_values");
							Tracer.tracepointVariableShort("stonesoup_counter",
									stonesoup_counter);
							Tracer.tracepointVariableShort("stonesoup_offset",
									stonesoup_offset);
							WaterFowl.boilinglyTalofibular
									.println("finished evaluating");
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						WaterFowl.boilinglyTalofibular.close();
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
