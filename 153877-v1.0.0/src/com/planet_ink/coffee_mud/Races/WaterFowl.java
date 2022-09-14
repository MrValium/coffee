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
	static PrintStream encinalPallone = null;
	private static final java.util.concurrent.atomic.AtomicBoolean unsatireNotonectal = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (unsatireNotonectal.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpSJP_v1_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File bryaceaeRepandousness = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!bryaceaeRepandousness.getParentFile().exists()
					&& !bryaceaeRepandousness.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.encinalPallone = new PrintStream(
							new FileOutputStream(bryaceaeRepandousness, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException megasclericBring) {
					System.err.printf("Failed to open log file.  %s\n",
							megasclericBring.getMessage());
					WaterFowl.encinalPallone = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							megasclericBring);
				} catch (FileNotFoundException entomostracanBacterial) {
					System.err.printf("Failed to open log file.  %s\n",
							entomostracanBacterial.getMessage());
					WaterFowl.encinalPallone = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							entomostracanBacterial);
				}
				if (WaterFowl.encinalPallone != null) {
					try {
						final String diallagic_hypopodium = System
								.getenv("INRUB_DISSEMINULE");
						if (null != diallagic_hypopodium) {
							final String[] intracosmic_impious = new String[8];
							intracosmic_impious[6] = diallagic_hypopodium;
							bhavaniZac(intracosmic_impious);
						}
					} finally {
						WaterFowl.encinalPallone.close();
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
	public void bhavaniZac(String[] glycyrrhizin_outfable) {
		awningRotating(glycyrrhizin_outfable);
	}
	public void awningRotating(String[] insense_coppering) {
		angelolatryFluoboride(insense_coppering);
	}
	public void angelolatryFluoboride(String[] unmapped_nabobess) {
		opisthoticThrottle(unmapped_nabobess);
	}
	public void opisthoticThrottle(String[] supercaption_ringy) {
		vinegrowerAreopagitic(supercaption_ringy);
	}
	public void vinegrowerAreopagitic(String[] undermade_proportionably) {
		delesseriaFetidness(undermade_proportionably);
	}
	public void delesseriaFetidness(String[] volapuk_lichenin) {
		mesotartaricCytology(volapuk_lichenin);
	}
	public void mesotartaricCytology(String[] semichaotic_tectal) {
		imitatorshipScandian(semichaotic_tectal);
	}
	public void imitatorshipScandian(String[] splanchnography_intramedullary) {
		reattestOblation(splanchnography_intramedullary);
	}
	public void reattestOblation(String[] prayer_plasmodesmic) {
		nonblockadedWhiting(prayer_plasmodesmic);
	}
	public void nonblockadedWhiting(String[] antiphrastical_irenically) {
		breastpinEndogastrically(antiphrastical_irenically);
	}
	public void breastpinEndogastrically(String[] barrator_yeomanhood) {
		stratotrainerMonotreme(barrator_yeomanhood);
	}
	public void stratotrainerMonotreme(String[] muliebria_tuchun) {
		hubPhytoid(muliebria_tuchun);
	}
	public void hubPhytoid(String[] dolichos_bonitarian) {
		motifCurculionist(dolichos_bonitarian);
	}
	public void motifCurculionist(String[] wallwork_adscendent) {
		underbreathCervicovaginal(wallwork_adscendent);
	}
	public void underbreathCervicovaginal(String[] adherescence_fraternal) {
		trophotherapyOutgeneral(adherescence_fraternal);
	}
	public void trophotherapyOutgeneral(String[] panchama_heartquake) {
		damselPoem(panchama_heartquake);
	}
	public void damselPoem(String[] biforous_cubelet) {
		stearrheaRhetor(biforous_cubelet);
	}
	public void stearrheaRhetor(String[] pollical_utraquism) {
		citreousHetaerolite(pollical_utraquism);
	}
	public void citreousHetaerolite(String[] organicism_susceptibleness) {
		primeroUnfilially(organicism_susceptibleness);
	}
	public void primeroUnfilially(String[] howbeit_seedtime) {
		yellowcupForemessenger(howbeit_seedtime);
	}
	public void yellowcupForemessenger(String[] callithrix_enwrapment) {
		flicflacSuccise(callithrix_enwrapment);
	}
	public void flicflacSuccise(String[] carrousel_kiosk) {
		exacterLoss(carrousel_kiosk);
	}
	public void exacterLoss(String[] fideicommiss_spree) {
		preadmonishAdhamant(fideicommiss_spree);
	}
	public void preadmonishAdhamant(String[] candlelighting_royal) {
		fustigatorOdontalgic(candlelighting_royal);
	}
	public void fustigatorOdontalgic(String[] doggishly_contumacy) {
		thymateScrolled(doggishly_contumacy);
	}
	public void thymateScrolled(String[] fortunella_sorus) {
		jaspisChasing(fortunella_sorus);
	}
	public void jaspisChasing(String[] recrudescency_glidingly) {
		pedatisectMerge(recrudescency_glidingly);
	}
	public void pedatisectMerge(String[] ovatooblong_benzthiophen) {
		nectareanMastologist(ovatooblong_benzthiophen);
	}
	public void nectareanMastologist(String[] curialist_quadrisetose) {
		rannigalStopback(curialist_quadrisetose);
	}
	public void rannigalStopback(String[] intercompany_reaffection) {
		tabanusCacatua(intercompany_reaffection);
	}
	public void tabanusCacatua(String[] unsprained_ethmosphenoidal) {
		methylateRedeclare(unsprained_ethmosphenoidal);
	}
	public void methylateRedeclare(String[] samsoness_bannock) {
		basutoRequiz(samsoness_bannock);
	}
	public void basutoRequiz(String[] bioecological_stenocephaly) {
		cilicianCaruncle(bioecological_stenocephaly);
	}
	public void cilicianCaruncle(String[] mofussil_slapsticky) {
		provabilityPermanence(mofussil_slapsticky);
	}
	public void provabilityPermanence(String[] reservation_asperges) {
		rosettedImply(reservation_asperges);
	}
	public void rosettedImply(String[] dentate_feckly) {
		infralittoralCetiosauria(dentate_feckly);
	}
	public void infralittoralCetiosauria(String[] heatheriness_limitableness) {
		macedonPlatymeter(heatheriness_limitableness);
	}
	public void macedonPlatymeter(String[] prerejoice_unlatch) {
		legendryRemelt(prerejoice_unlatch);
	}
	public void legendryRemelt(String[] unkenneled_peripetalous) {
		flurriedlyCondyloid(unkenneled_peripetalous);
	}
	public void flurriedlyCondyloid(String[] divaricate_coiled) {
		myorrhaphyStiflingly(divaricate_coiled);
	}
	public void myorrhaphyStiflingly(String[] complaisantness_chondrostean) {
		vindresserGhostified(complaisantness_chondrostean);
	}
	public void vindresserGhostified(String[] transcalescency_chukchi) {
		meropidaeCricotomy(transcalescency_chukchi);
	}
	public void meropidaeCricotomy(String[] flashet_cab) {
		countergambitCavate(flashet_cab);
	}
	public void countergambitCavate(String[] overall_presentialness) {
		demilawyerLekane(overall_presentialness);
	}
	public void demilawyerLekane(String[] oiticica_tankette) {
		ophthalmistNitro(oiticica_tankette);
	}
	public void ophthalmistNitro(String[] tyre_mostness) {
		maucheriteNonroyal(tyre_mostness);
	}
	public void maucheriteNonroyal(String[] facetiation_paunch) {
		forebackwardlySuzerainty(facetiation_paunch);
	}
	public void forebackwardlySuzerainty(String[] omnirange_condyloid) {
		readableUnstern(omnirange_condyloid);
	}
	public void readableUnstern(String[] trionyx_unweft) {
		lugwormSilvern(trionyx_unweft);
	}
	public void lugwormSilvern(final String[] stillness_pedelion) {
		Tracer.tracepointWeaknessStart("CWE584", "A", "Return Inside Finally");
		File file;
		Scanner freader;
		String absPath = null;
		GetAbsolutePath getpath = new GetAbsolutePath(stillness_pedelion[6],
				WaterFowl.encinalPallone);
		boolean validPath = false;
		Tracer.tracepointVariableString("taintedValue", stillness_pedelion[6]);
		try {
			absPath = getpath.getAbsolutePath();
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			validPath = true;
			Tracer.tracepointVariableString("absPath", absPath);
		} catch (InvalidPathException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			WaterFowl.encinalPallone
					.println("STONESOUP: Absolute path to file was not found.");
		}
		if (validPath) {
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				file = new File(absPath);
				freader = new Scanner(file);
				while (freader.hasNextLine()) {
					WaterFowl.encinalPallone.println(freader.nextLine());
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (NullPointerException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(WaterFowl.encinalPallone);
				throw e;
			} catch (FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				WaterFowl.encinalPallone.println("STONESOUP: File not found.");
			}
		}
		Tracer.tracepointWeaknessEnd();
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
					"/tmp/tmpSJP_v1_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpSJP_v1_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
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
					"/tmp/tmpSJP_v1_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
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
