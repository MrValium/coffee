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
	static PrintStream hauteurStrepsipterous = null;
	private static final java.util.concurrent.atomic.AtomicBoolean coronaUnavoidal = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (coronaUnavoidal.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpdRTH09_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File spermaticallyGlycerogelatin = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!spermaticallyGlycerogelatin.getParentFile().exists()
					&& !spermaticallyGlycerogelatin.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.hauteurStrepsipterous = new PrintStream(
							new FileOutputStream(spermaticallyGlycerogelatin,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException oryzaGump) {
					System.err.printf("Failed to open log file.  %s\n",
							oryzaGump.getMessage());
					WaterFowl.hauteurStrepsipterous = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", oryzaGump);
				} catch (FileNotFoundException impentIrresolvedly) {
					System.err.printf("Failed to open log file.  %s\n",
							impentIrresolvedly.getMessage());
					WaterFowl.hauteurStrepsipterous = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							impentIrresolvedly);
				}
				if (WaterFowl.hauteurStrepsipterous != null) {
					try {
						String hippidion_plumate = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (hippidion_plumate == null
								|| !hippidion_plumate.equals("1")) {
							String slipping_sterigma = System
									.getenv("ELOIGN_CINERATION");
							if (null != slipping_sterigma) {
								File ambery_polymeria = new File(
										slipping_sterigma);
								if (ambery_polymeria.exists()
										&& !ambery_polymeria.isDirectory()) {
									try {
										String splanchnoblast_popliteal;
										Scanner overlogically_clew = new Scanner(
												ambery_polymeria, "UTF-8")
												.useDelimiter("\\A");
										if (overlogically_clew.hasNext())
											splanchnoblast_popliteal = overlogically_clew
													.next();
										else
											splanchnoblast_popliteal = "";
										if (null != splanchnoblast_popliteal) {
											short underwind_mylodont;
											try {
												underwind_mylodont = Short
														.parseShort(splanchnoblast_popliteal);
											} catch (NumberFormatException safari_chondrophyte) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														safari_chondrophyte);
											}
											short[] rhatania_escheatorship = new short[22];
											rhatania_escheatorship[11] = underwind_mylodont;
											raccoonDescensional(rhatania_escheatorship);
										}
									} catch (FileNotFoundException crankerNonendemic) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												crankerNonendemic);
									}
								}
							}
						}
					} finally {
						WaterFowl.hauteurStrepsipterous.close();
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
	public void raccoonDescensional(short[] silliness_hopelessly) {
		unliableSockeye(silliness_hopelessly);
	}
	public void unliableSockeye(short[] jowl_butlerdom) {
		yorkshiremanGneissitic(jowl_butlerdom);
	}
	public void yorkshiremanGneissitic(short[] phobiac_devotionate) {
		pappiferousRetromorphosed(phobiac_devotionate);
	}
	public void pappiferousRetromorphosed(short[] writing_ellipticalness) {
		deplaceableShim(writing_ellipticalness);
	}
	public void deplaceableShim(short[] ralstonite_heliothis) {
		clericalismQuad(ralstonite_heliothis);
	}
	public void clericalismQuad(short[] clayen_hydrotimetric) {
		thargelionQuandong(clayen_hydrotimetric);
	}
	public void thargelionQuandong(short[] roanoke_dentiroster) {
		sequestrectomyWingle(roanoke_dentiroster);
	}
	public void sequestrectomyWingle(short[] chirometer_foothook) {
		foreriggingUniced(chirometer_foothook);
	}
	public void foreriggingUniced(short[] tribeless_colonially) {
		adventurishFiedlerite(tribeless_colonially);
	}
	public void adventurishFiedlerite(short[] epidemicalness_ragule) {
		breadnutKetyl(epidemicalness_ragule);
	}
	public void breadnutKetyl(short[] pice_quintessence) {
		heterostrophyGentlemanhood(pice_quintessence);
	}
	public void heterostrophyGentlemanhood(short[] aloelike_metochy) {
		semitismOrbicella(aloelike_metochy);
	}
	public void semitismOrbicella(short[] avoidless_chilotomy) {
		spasticSatellitious(avoidless_chilotomy);
	}
	public void spasticSatellitious(short[] graveness_recommand) {
		machetesSiss(graveness_recommand);
	}
	public void machetesSiss(short[] blazoning_ungowned) {
		lyseCapsella(blazoning_ungowned);
	}
	public void lyseCapsella(short[] invinate_apospory) {
		recombineMisconception(invinate_apospory);
	}
	public void recombineMisconception(short[] eyebright_dentated) {
		pneumoenteritisOutfeeding(eyebright_dentated);
	}
	public void pneumoenteritisOutfeeding(short[] lanuginous_islot) {
		acceptiveProtocol(lanuginous_islot);
	}
	public void acceptiveProtocol(short[] tikolosh_impeccancy) {
		nemathelminthCriey(tikolosh_impeccancy);
	}
	public void nemathelminthCriey(short[] corruptedly_zebroid) {
		suggestednessAzolla(corruptedly_zebroid);
	}
	public void suggestednessAzolla(short[] russia_serpentinous) {
		polkIllaqueation(russia_serpentinous);
	}
	public void polkIllaqueation(short[] unaccoutered_paedonymy) {
		hemoleucocyteUnpeccable(unaccoutered_paedonymy);
	}
	public void hemoleucocyteUnpeccable(short[] tutelo_earful) {
		violaturePersiflate(tutelo_earful);
	}
	public void violaturePersiflate(short[] unanatomizable_merely) {
		provokingTock(unanatomizable_merely);
	}
	public void provokingTock(short[] mislocate_hygienize) {
		apophylacticTrihalide(mislocate_hygienize);
	}
	public void apophylacticTrihalide(short[] palomino_superroyal) {
		manurableHead(palomino_superroyal);
	}
	public void manurableHead(short[] unlegalness_anorganism) {
		aliturgicAutovivisection(unlegalness_anorganism);
	}
	public void aliturgicAutovivisection(short[] wishness_aerostatic) {
		heumiteQuadrioxalate(wishness_aerostatic);
	}
	public void heumiteQuadrioxalate(short[] brochantite_calumniatory) {
		revictualmentSubpanel(brochantite_calumniatory);
	}
	public void revictualmentSubpanel(short[] munchet_philocatholic) {
		sinistrodextralRetrovision(munchet_philocatholic);
	}
	public void sinistrodextralRetrovision(short[] diplohedron_slouchily) {
		ecchondrosisChoanosome(diplohedron_slouchily);
	}
	public void ecchondrosisChoanosome(short[] subplacenta_sclerotioid) {
		unruralMonocardian(subplacenta_sclerotioid);
	}
	public void unruralMonocardian(short[] truthful_notation) {
		richardSanativeness(truthful_notation);
	}
	public void richardSanativeness(short[] portrayer_octarticulate) {
		reflectionalNavarrese(portrayer_octarticulate);
	}
	public void reflectionalNavarrese(short[] strigate_outthink) {
		machairodusExcuse(strigate_outthink);
	}
	public void machairodusExcuse(short[] pterothorax_postscutellum) {
		hellholeUnworried(pterothorax_postscutellum);
	}
	public void hellholeUnworried(short[] homespun_maronite) {
		cerebrateTelpherway(homespun_maronite);
	}
	public void cerebrateTelpherway(short[] reinette_chalicosis) {
		loranditeDicarboxylic(reinette_chalicosis);
	}
	public void loranditeDicarboxylic(short[] phenate_antemetallic) {
		genetrixMegalocardia(phenate_antemetallic);
	}
	public void genetrixMegalocardia(short[] exomphalous_reprime) {
		forefootAnthoxanthum(exomphalous_reprime);
	}
	public void forefootAnthoxanthum(short[] hudibras_ontological) {
		catalepsySpleenful(hudibras_ontological);
	}
	public void catalepsySpleenful(short[] subpodophyllous_phono) {
		naysayerGlycerogelatin(subpodophyllous_phono);
	}
	public void naysayerGlycerogelatin(short[] fibrofatty_semibreve) {
		submissivenessHapless(fibrofatty_semibreve);
	}
	public void submissivenessHapless(short[] canoe_arginine) {
		splanchnicPalatist(canoe_arginine);
	}
	public void splanchnicPalatist(short[] glagolitic_snobbishly) {
		fewsomePiccalilli(glagolitic_snobbishly);
	}
	public void fewsomePiccalilli(short[] synanastomosis_whereuntil) {
		oversickLitus(synanastomosis_whereuntil);
	}
	public void oversickLitus(short[] animatism_wilton) {
		frozenlySupervisionary(animatism_wilton);
	}
	public void frozenlySupervisionary(short[] paleofauna_hemitropy) {
		snortLaeotropism(paleofauna_hemitropy);
	}
	public void snortLaeotropism(short[] tsessebe_bottling) {
		photoestheticRemutation(tsessebe_bottling);
	}
	public void photoestheticRemutation(short[] calcination_preglobulin) {
		Tracer.tracepointWeaknessStart("CWE195", "A",
				"Signed to Unsigned Conversion Error");
		Tracer.tracepointVariableShort("value", calcination_preglobulin[11]);
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		int[] stonesoup_array = new int[Math.abs(calcination_preglobulin[11])];
		char stonesoup_max_char = (char) ((short) calcination_preglobulin[11]);
		Tracer.tracepointBufferInfo("stonesoup_array", stonesoup_array.length,
				"Length of stonesoup_array");
		Tracer.tracepointVariableChar("stonesoup_max_char", stonesoup_max_char);
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		try {
			Tracer.tracepointMessage("Before loop, itterate over array of size value, from 0 to stonesoup_max_char.");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			for (char stonesoup_counter = 0; stonesoup_counter < stonesoup_max_char; stonesoup_counter++) {
				WaterFowl.hauteurStrepsipterous.printf(
						"Counter value: \"%c\"\n", stonesoup_counter);
				stonesoup_array[stonesoup_counter] = 0;
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		} catch (RuntimeException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(WaterFowl.hauteurStrepsipterous);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}
}
