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
import java.math.BigInteger;

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
	static PrintStream ossianPhonogram = null;
	private static final java.util.concurrent.atomic.AtomicBoolean sleazyContrarational = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (sleazyContrarational.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp58_E1Q_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File unappetizingRepeatable = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!unappetizingRepeatable.getParentFile().exists()
					&& !unappetizingRepeatable.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.ossianPhonogram = new PrintStream(
							new FileOutputStream(unappetizingRepeatable, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException capilliformConcertstuck) {
					System.err.printf("Failed to open log file.  %s\n",
							capilliformConcertstuck.getMessage());
					WaterFowl.ossianPhonogram = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							capilliformConcertstuck);
				} catch (FileNotFoundException helpworthyPelew) {
					System.err.printf("Failed to open log file.  %s\n",
							helpworthyPelew.getMessage());
					WaterFowl.ossianPhonogram = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							helpworthyPelew);
				}
				if (WaterFowl.ossianPhonogram != null) {
					try {
						String preformationist_semelincident = System
								.getenv("PEDICELLIFORM_THICKLY");
						if (null != preformationist_semelincident) {
							unatonedFavonian(3, null, null, null,
									preformationist_semelincident, null, null);
						}
					} finally {
						WaterFowl.ossianPhonogram.close();
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
	public void unatonedFavonian(int unplannedUnderkeel,
			String... stomatorrhagiaBluejoint) {
		String dihydrocupreineAnguillidae = null;
		int quadricellularUnconsecrate = 0;
		for (quadricellularUnconsecrate = 0; quadricellularUnconsecrate < stomatorrhagiaBluejoint.length; quadricellularUnconsecrate++) {
			if (quadricellularUnconsecrate == unplannedUnderkeel)
				dihydrocupreineAnguillidae = stomatorrhagiaBluejoint[quadricellularUnconsecrate];
		}
		NongraduationNaggle beneficially_tzontle = new NongraduationNaggle();
		beneficially_tzontle.syeGanner(dihydrocupreineAnguillidae);
	}
	public static class NongraduationNaggle {
		public void syeGanner(String holla_heartbrokenly) {
			Tracer.tracepointWeaknessStart("CWE834", "A", "Excessive Iteration");
			BigInteger stonesoup_checkVal;
			BigInteger stonesoup_intValue;
			BigInteger stonesoup_intValueMinusOne;
			boolean stonesoup_prime = true;
			Tracer.tracepointVariableString("stonesoup_taintedValue",
					holla_heartbrokenly);
			try {
				stonesoup_checkVal = new BigInteger("2");
				stonesoup_intValue = new BigInteger(holla_heartbrokenly);
				stonesoup_intValueMinusOne = stonesoup_intValue
						.subtract(BigInteger.ONE);
				if (stonesoup_intValue.compareTo(BigInteger.ZERO) > 0) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (; stonesoup_checkVal
							.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
							.add(BigInteger.ONE)) {
						if (stonesoup_intValue.mod(stonesoup_checkVal)
								.compareTo(BigInteger.ZERO) == 0) {
							stonesoup_prime = false;
							WaterFowl.ossianPhonogram.println("Not Prime");
							break;
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				WaterFowl.ossianPhonogram
						.println("STONESOUP: Input string is not an integer");
			}
			WaterFowl.ossianPhonogram.println("finished evaluating");
			Tracer.tracepointWeaknessEnd();
		}
	}
}
