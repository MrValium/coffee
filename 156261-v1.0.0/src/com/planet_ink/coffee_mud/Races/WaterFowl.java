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
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;

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
	static PrintStream intracloacalDisproportion = null;
	private static class StonesoupSourceHttpServer extends NanoHTTPD {
		private String data = null;
		private CyclicBarrier receivedBarrier = new CyclicBarrier(2);
		private PipedInputStream responseStream = null;
		private PipedOutputStream responseWriter = null;

		public StonesoupSourceHttpServer(int port, PipedOutputStream writer)
				throws IOException {
			super(port);
			this.responseWriter = writer;
		}

		private Response handleGetRequest(IHTTPSession session, boolean sendBody) {
			String body = null;
			if (sendBody) {
				body = String
						.format("Request Approved!\n\n"
								+ "Thank you for you interest in \"%s\".\n\n"
								+ "We appreciate your inquiry.  Please visit us again!",
								session.getUri());
			}
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT,
					body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleOptionsRequest(IHTTPSession session) {
			NanoHTTPD.Response response = new NanoHTTPD.Response(null);
			response.setStatus(NanoHTTPD.Response.Status.OK);
			response.setMimeType(NanoHTTPD.MIME_PLAINTEXT);
			response.addHeader("Allow", "GET, PUT, POST, HEAD, OPTIONS");
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleUnallowedRequest(IHTTPSession session) {
			String body = String.format("Method Not Allowed!\n\n"
					+ "Thank you for your request, but we are unable "
					+ "to process that method.  Please try back later.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handlePostRequest(IHTTPSession session) {
			String body = String
					.format("Request Data Processed!\n\n"
							+ "Thank you for your contribution.  Please keep up the support.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.CREATED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}		private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
				NanoHTTPD.Response.Status status, String message) {
			String body = String.format(
					"There was an issue processing your request!\n\n"
							+ "Reported Error Message:\n\n%s.", message);
			NanoHTTPD.Response response = new NanoHTTPD.Response(status,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private void setResponseOptions(IHTTPSession session,
				NanoHTTPD.Response response) {
			response.setRequestMethod(session.getMethod());
		}

		@Override
		public Response serve(IHTTPSession session) {
			Method method = session.getMethod();
			switch (method) {
			case GET:
				return handleGetRequest(session, true);
			case HEAD:
				return handleGetRequest(session, false);
			case DELETE:
				return handleUnallowedRequest(session);
			case OPTIONS:
				return handleOptionsRequest(session);
			case POST:
			case PUT:
				String matchCheckHeader = session.getHeaders().get("if-match");
				if (matchCheckHeader == null
						|| !matchCheckHeader
								.equalsIgnoreCase("weak_taint_source_value")) {
					return handlePostRequest(session);
				} else {
					return handleTaintRequest(session);
				}
			default:
				return writeErrorResponse(session, Response.Status.BAD_REQUEST,
						"Unsupported request method.");
			}
		}

		public String getData() throws IOException {
			try {
				this.receivedBarrier.await();
			} catch (InterruptedException e) {
				throw new IOException(
						"HTTP Taint Source: Interruped while waiting for data.",
						e);
			} catch (BrokenBarrierException e) {
				throw new IOException(
						"HTTP Taint Source: Wait barrier broken.", e);
			}
			return this.data;
		}
	}
	private static final java.util.concurrent.atomic.AtomicBoolean worldproofCardiopathic = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (worldproofCardiopathic.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpRuq7SJ_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			String nonhabitable_japanize = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (nonhabitable_japanize == null
					|| !nonhabitable_japanize.equals("1")) {
				StonesoupSourceHttpServer transocular_dromiacea = null;
				PipedOutputStream singillatimCystignathidae = new PipedOutputStream();
				try {
					WaterFowl.intracloacalDisproportion = new PrintStream(
							singillatimCystignathidae, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException salicylanilideBenzonaphthol) {
					System.err.printf("Failed to open log file.  %s\n",
							salicylanilideBenzonaphthol.getMessage());
					WaterFowl.intracloacalDisproportion = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							salicylanilideBenzonaphthol);
				}
				if (WaterFowl.intracloacalDisproportion != null) {
					try {
						final String consentfully_hemidysesthesia;
						try {
							transocular_dromiacea = new StonesoupSourceHttpServer(
									8887, singillatimCystignathidae);
							transocular_dromiacea.start();
							consentfully_hemidysesthesia = transocular_dromiacea
									.getData();
						} catch (IOException echelon_wastepaper) {
							transocular_dromiacea = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									echelon_wastepaper);
						} catch (Exception unrepudiable_oreodontine) {
							transocular_dromiacea = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									unrepudiable_oreodontine);
						}
						if (null != consentfully_hemidysesthesia) {
							ChristianiadealCriminogenesis internasal_unexperience = new ChristianiadealCriminogenesis();
							internasal_unexperience
									.pronounalPainkiller(consentfully_hemidysesthesia);
						}
					} finally {
						WaterFowl.intracloacalDisproportion.close();
						if (transocular_dromiacea != null)
							transocular_dromiacea.stop(true);
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
	public static class ChristianiadealCriminogenesis {
		public void pronounalPainkiller(String sabulous_for) {
			BrainsMansuetude lithifaction_separationism = new BrainsMansuetude();
			lithifaction_separationism.depatriateInterlaboratory(sabulous_for);
		}
	}
	public static class BrainsMansuetude {
		public void depatriateInterlaboratory(String revegetate_unfurthersome) {
			MilitarismMoro brewst_engrandizement = new MilitarismMoro();
			brewst_engrandizement.papismKui(revegetate_unfurthersome);
		}
	}
	public static class MilitarismMoro {
		public void papismKui(String slaughteryard_drugstore) {
			FriendedAftergrave angelet_transferee = new FriendedAftergrave();
			angelet_transferee.roamDibs(slaughteryard_drugstore);
		}
	}
	public static class FriendedAftergrave {
		public void roamDibs(String justinian_opisthocomine) {
			DislocatedBellicoseness protohomo_pharaonic = new DislocatedBellicoseness();
			protohomo_pharaonic.uniphonousReobligate(justinian_opisthocomine);
		}
	}
	public static class DislocatedBellicoseness {
		public void uniphonousReobligate(String begeck_pretervection) {
			FrivolityWoodprint arachnoidea_apprense = new FrivolityWoodprint();
			arachnoidea_apprense.troftSuburbia(begeck_pretervection);
		}
	}
	public static class FrivolityWoodprint {
		public void troftSuburbia(String bussu_subsimilation) {
			TangalungTryp amygdalaceous_sexly = new TangalungTryp();
			amygdalaceous_sexly.tmemaHypermetropic(bussu_subsimilation);
		}
	}
	public static class TangalungTryp {
		public void tmemaHypermetropic(String bicycler_uptide) {
			NailingHarnesser filch_saccharophylly = new NailingHarnesser();
			filch_saccharophylly.superconformistSinapine(bicycler_uptide);
		}
	}
	public static class NailingHarnesser {
		public void superconformistSinapine(String cornic_maxwell) {
			HarvestmanMonoptote sarcina_unfatted = new HarvestmanMonoptote();
			sarcina_unfatted.maulawiyahEndwise(cornic_maxwell);
		}
	}
	public static class HarvestmanMonoptote {
		public void maulawiyahEndwise(String spirochetosis_cromfordite) {
			ExegetistUnviolable overseed_seth = new ExegetistUnviolable();
			overseed_seth.trailsideCrisping(spirochetosis_cromfordite);
		}
	}
	public static class ExegetistUnviolable {
		public void trailsideCrisping(String subsyndicate_tscherkess) {
			FirestoneHemiascales hyporadial_onerative = new FirestoneHemiascales();
			hyporadial_onerative.exchequerSmackee(subsyndicate_tscherkess);
		}
	}
	public static class FirestoneHemiascales {
		public void exchequerSmackee(String guilelessness_tessaraconter) {
			OtioseKrama ashkenazic_cottagers = new OtioseKrama();
			ashkenazic_cottagers
					.stephaniteNonremonstrance(guilelessness_tessaraconter);
		}
	}
	public static class OtioseKrama {
		public void stephaniteNonremonstrance(String lavishingly_squamiferous) {
			EnforceableBaccated cladophoraceae_upher = new EnforceableBaccated();
			cladophoraceae_upher.jarldomFathometer(lavishingly_squamiferous);
		}
	}
	public static class EnforceableBaccated {
		public void jarldomFathometer(String autophyte_monocularity) {
			BollardFeedbox balmarcodes_pluries = new BollardFeedbox();
			balmarcodes_pluries.wraitlyChalicosis(autophyte_monocularity);
		}
	}
	public static class BollardFeedbox {
		public void wraitlyChalicosis(String korntonde_ketonize) {
			CoelenteronBeng rekindlement_sporadosiderite = new CoelenteronBeng();
			rekindlement_sporadosiderite
					.cockbrainConsistorial(korntonde_ketonize);
		}
	}
	public static class CoelenteronBeng {
		public void cockbrainConsistorial(String serinette_adonidin) {
			PrehensivenessMatralia unbasedness_undisposed = new PrehensivenessMatralia();
			unbasedness_undisposed.unmetricalCamelopardus(serinette_adonidin);
		}
	}
	public static class PrehensivenessMatralia {
		public void unmetricalCamelopardus(String stranger_perniciousness) {
			CapacitateZunyite dissolvableness_probator = new CapacitateZunyite();
			dissolvableness_probator.cohobateClootie(stranger_perniciousness);
		}
	}
	public static class CapacitateZunyite {
		public void cohobateClootie(String correlated_vaporization) {
			RubiatePeroxidizement quadrauricular_asperge = new RubiatePeroxidizement();
			quadrauricular_asperge
					.ectoplacentaProbationerhood(correlated_vaporization);
		}
	}
	public static class RubiatePeroxidizement {
		public void ectoplacentaProbationerhood(String polyemic_truish) {
			OutporchPromulger unmigrating_nooking = new OutporchPromulger();
			unmigrating_nooking.unpriceablySpiloma(polyemic_truish);
		}
	}
	public static class OutporchPromulger {
		public void unpriceablySpiloma(String marginality_untransgressed) {
			ElephantousChirivita semiexecutive_foremeant = new ElephantousChirivita();
			semiexecutive_foremeant
					.importunelyUnpurposed(marginality_untransgressed);
		}
	}
	public static class ElephantousChirivita {
		public void importunelyUnpurposed(String brutishly_goddesshood) {
			TheocracyZoocyst pinguin_pinguidity = new TheocracyZoocyst();
			pinguin_pinguidity.snatchyFinal(brutishly_goddesshood);
		}
	}
	public static class TheocracyZoocyst {
		public void snatchyFinal(String curse_wots) {
			SpidgerHexadiyne acana_manganous = new SpidgerHexadiyne();
			acana_manganous.hexagynousSecretly(curse_wots);
		}
	}
	public static class SpidgerHexadiyne {
		public void hexagynousSecretly(String banding_romantic) {
			TriglidLadleful unarrested_prostomial = new TriglidLadleful();
			unarrested_prostomial.arthrogryposisSynemmenon(banding_romantic);
		}
	}
	public static class TriglidLadleful {
		public void arthrogryposisSynemmenon(String lumberyard_foodlessness) {
			AlkahesticalBordroom spinstership_engraved = new AlkahesticalBordroom();
			spinstership_engraved
					.undernurseHeterosyllabic(lumberyard_foodlessness);
		}
	}
	public static class AlkahesticalBordroom {
		public void undernurseHeterosyllabic(String proprovost_nonsyllogistic) {
			SirmianSabbatary hagiolatry_alban = new SirmianSabbatary();
			hagiolatry_alban.portolanoMyeloma(proprovost_nonsyllogistic);
		}
	}
	public static class SirmianSabbatary {
		public void portolanoMyeloma(String futtock_curn) {
			RegroupmentBeret cryophorus_snakeleaf = new RegroupmentBeret();
			cryophorus_snakeleaf.handkerchieffulBellonian(futtock_curn);
		}
	}
	public static class RegroupmentBeret {
		public void handkerchieffulBellonian(String scintillator_unplain) {
			TransfereeGorillian satyric_reprobative = new TransfereeGorillian();
			satyric_reprobative.baskonizeGripy(scintillator_unplain);
		}
	}
	public static class TransfereeGorillian {
		public void baskonizeGripy(String connectable_dyewood) {
			InexpectationCoachsmith agrological_antitax = new InexpectationCoachsmith();
			agrological_antitax.lycopodeBursera(connectable_dyewood);
		}
	}
	public static class InexpectationCoachsmith {
		public void lycopodeBursera(String samarskite_unleisurely) {
			ScripturalitySanicle journey_pathematology = new ScripturalitySanicle();
			journey_pathematology.lasqueHitoshi(samarskite_unleisurely);
		}
	}
	public static class ScripturalitySanicle {
		public void lasqueHitoshi(String uncontentable_binder) {
			AcclimateJudication chloragogen_greedygut = new AcclimateJudication();
			chloragogen_greedygut
					.scrimshawIntervisitation(uncontentable_binder);
		}
	}
	public static class AcclimateJudication {
		public void scrimshawIntervisitation(String naegate_anconeus) {
			MbaloloSyzygy courbash_camerist = new MbaloloSyzygy();
			courbash_camerist.bountylessNecessitously(naegate_anconeus);
		}
	}
	public static class MbaloloSyzygy {
		public void bountylessNecessitously(String affricated_phlebemphraxis) {
			QuadridigitateVirginian vota_unversedness = new QuadridigitateVirginian();
			vota_unversedness.anemonellaUnstern(affricated_phlebemphraxis);
		}
	}
	public static class QuadridigitateVirginian {
		public void anemonellaUnstern(String reflection_kompeni) {
			ScythicNanpie acanthocereus_bonelessly = new ScythicNanpie();
			acanthocereus_bonelessly
					.coelacanthidaeDemurrage(reflection_kompeni);
		}
	}
	public static class ScythicNanpie {
		public void coelacanthidaeDemurrage(String imprudentness_oquassa) {
			LitigiousHandleable misprovoke_rhyotaxitic = new LitigiousHandleable();
			misprovoke_rhyotaxitic.misappearWaterquake(imprudentness_oquassa);
		}
	}
	public static class LitigiousHandleable {
		public void misappearWaterquake(String liberticidal_decane) {
			SyphilogenesisRhigosis greatness_inspiratrix = new SyphilogenesisRhigosis();
			greatness_inspiratrix
					.fetalizationEmblematicize(liberticidal_decane);
		}
	}
	public static class SyphilogenesisRhigosis {
		public void fetalizationEmblematicize(String clairsentience_multipresent) {
			GlobefishUnteachable sulcular_imperialine = new GlobefishUnteachable();
			sulcular_imperialine
					.aortorrhaphyLorandite(clairsentience_multipresent);
		}
	}
	public static class GlobefishUnteachable {
		public void aortorrhaphyLorandite(String vacationist_empaper) {
			MisassayFlanker shopocrat_pythagorizer = new MisassayFlanker();
			shopocrat_pythagorizer.frolicPostwoman(vacationist_empaper);
		}
	}
	public static class MisassayFlanker {
		public void frolicPostwoman(String emotion_matrilinearism) {
			ShaksheerPiperales appellable_polyhistoric = new ShaksheerPiperales();
			appellable_polyhistoric.subpimpVagrantlike(emotion_matrilinearism);
		}
	}
	public static class ShaksheerPiperales {
		public void subpimpVagrantlike(String idioelectrical_evolve) {
			AntikamniaMystify dustiness_picot = new AntikamniaMystify();
			dustiness_picot.negativerFibster(idioelectrical_evolve);
		}
	}
	public static class AntikamniaMystify {
		public void negativerFibster(String anthraquinol_repetitory) {
			UltimobranchialEcholalic looping_renovate = new UltimobranchialEcholalic();
			looping_renovate.orogenesisIncorporator(anthraquinol_repetitory);
		}
	}
	public static class UltimobranchialEcholalic {
		public void orogenesisIncorporator(String atropamine_spousally) {
			StropInterlineary columbia_theobromine = new StropInterlineary();
			columbia_theobromine.dukeEthereal(atropamine_spousally);
		}
	}
	public static class StropInterlineary {
		public void dukeEthereal(String psychologically_kauravas) {
			BalaamiticalVigilance appendotome_prefulgency = new BalaamiticalVigilance();
			appendotome_prefulgency.amyrinVolumescope(psychologically_kauravas);
		}
	}
	public static class BalaamiticalVigilance {
		public void amyrinVolumescope(String uromelus_boulangerite) {
			PastramiNotkerian peribronchial_arduousness = new PastramiNotkerian();
			peribronchial_arduousness
					.recurefulPictorialize(uromelus_boulangerite);
		}
	}
	public static class PastramiNotkerian {
		public void recurefulPictorialize(String argentation_aconitin) {
			GreensickHemidysergia polewards_meccawee = new GreensickHemidysergia();
			polewards_meccawee.bleedingTubeworks(argentation_aconitin);
		}
	}
	public static class GreensickHemidysergia {
		public void bleedingTubeworks(String unrule_amorousness) {
			SulphonamidoSmokeless rekindlement_tricksical = new SulphonamidoSmokeless();
			rekindlement_tricksical.uncalculablyLegging(unrule_amorousness);
		}
	}
	public static class SulphonamidoSmokeless {
		public void uncalculablyLegging(String skoinolon_trachodontid) {
			SpirketingAeolian airtightly_spectaclemaking = new SpirketingAeolian();
			airtightly_spectaclemaking
					.sawingScenopinidae(skoinolon_trachodontid);
		}
	}
	public static class SpirketingAeolian {
		public void sawingScenopinidae(String alpujarra_nassology) {
			AsperserPromagisterial subtriquetrous_plural = new AsperserPromagisterial();
			subtriquetrous_plural.koreishBoil(alpujarra_nassology);
		}
	}
	public static class AsperserPromagisterial {
		public void koreishBoil(String nonglucose_plexiform) {
			AlectoriaBeseemliness lunar_petrosiliceous = new AlectoriaBeseemliness();
			lunar_petrosiliceous.phagolysisArteriovenous(nonglucose_plexiform);
		}
	}
	public static class AlectoriaBeseemliness {
		public void phagolysisArteriovenous(String untraded_paraselenic) {
			UnwrappedEximiously terp_asphyxial = new UnwrappedEximiously();
			terp_asphyxial.manganousSextuplet(untraded_paraselenic);
		}
	}
	public static class UnwrappedEximiously {
		public void manganousSextuplet(String prissiness_clarist) {
			VirginiaVesicoabdominal unmalleable_endocrinopathy = new VirginiaVesicoabdominal();
			unmalleable_endocrinopathy
					.dispauperizeQuantifiably(prissiness_clarist);
		}
	}
	public static class VirginiaVesicoabdominal {
		public void dispauperizeQuantifiably(final String kincob_overhigh){Tracer.tracepointWeaknessStart("CWE564","B","SQL Injection: Hybernate");String psql_host=System.getenv("DBPGHOST");String psql_user=System.getenv("DBPGUSER");String psql_pass=System.getenv("DBPGPASSWORD");String psql_port=System.getenv("DBPGPORT");String psql_dbname=System.getenv("SS_DBPGDATABASE");Tracer.tracepointVariableString("psql_host",psql_host);Tracer.tracepointVariableString("psql_user",psql_user);Tracer.tracepointVariableString("psql_pass",psql_pass);Tracer.tracepointVariableString("psql_port",psql_port);Tracer.tracepointVariableString("psql_dbname",psql_dbname);Tracer.tracepointVariableString("valueString",kincob_overhigh);if (kincob_overhigh != null && psql_host != null && psql_user != null && psql_pass != null && psql_port != null && psql_dbname != null){try {Tracer.tracepointMessage("Setting up hibernate connection.");org.hibernate.cfg.Configuration cfg=new org.hibernate.cfg.Configuration();cfg.setProperty("hibernate.connection.url","jdbc:postgresql://" + psql_host+":"+psql_port+"/"+psql_dbname);cfg.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");cfg.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");cfg.setProperty("hibernate.connection.username",psql_user);cfg.setProperty("hibernate.connection.password",psql_pass);cfg.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");cfg.setProperty("hibernate.current_session_context_class","thread");cfg.setProperty("org.hibernate.flushMode","COMMIT");cfg.setProperty("hibernate.hbm2ddl.auto","validate");cfg.setProperty("hibernate.connection.pool_size","1");cfg.addClass(SS_CWE_564_POSTGRES.Categories.class);cfg.addClass(SS_CWE_564_POSTGRES.Customercustomerdemo.class);cfg.addClass(SS_CWE_564_POSTGRES.Customerdemographics.class);cfg.addClass(SS_CWE_564_POSTGRES.Customers.class);cfg.addClass(SS_CWE_564_POSTGRES.Employees.class);cfg.addClass(SS_CWE_564_POSTGRES.Employeeterritories.class);cfg.addClass(SS_CWE_564_POSTGRES.OrderDetails.class);cfg.addClass(SS_CWE_564_POSTGRES.Orders.class);cfg.addClass(SS_CWE_564_POSTGRES.Products.class);cfg.addClass(SS_CWE_564_POSTGRES.Region.class);cfg.addClass(SS_CWE_564_POSTGRES.Shippers.class);cfg.addClass(SS_CWE_564_POSTGRES.ShippersTmp.class);cfg.addClass(SS_CWE_564_POSTGRES.Suppliers.class);cfg.addClass(SS_CWE_564_POSTGRES.Territories.class);cfg.addClass(SS_CWE_564_POSTGRES.Usstates.class);org.hibernate.SessionFactory factory=cfg.buildSessionFactory();org.hibernate.Session session=factory.openSession();Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String hql="from SS_CWE_564_POSTGRES.Customers where country = '" + kincob_overhigh+"'";Tracer.tracepointVariableString("hql",hql);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");org.hibernate.Query query=session.createQuery(hql);Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");@SuppressWarnings("rawtypes") java.util.Iterator iter=query.iterate();while (iter.hasNext()){SS_CWE_564_POSTGRES.Customers c=(SS_CWE_564_POSTGRES.Customers)iter.next();WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getCustomerId()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getCompanyName()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getContactName()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getContactTitle()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getAddress()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getCity()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getRegion()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getPostalCode()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getCountry()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getPhone()));WaterFowl.intracloacalDisproportion.print(String.format("%10s | ",c.getFax()));WaterFowl.intracloacalDisproportion.println();}Tracer.tracepointMessage("TRIGGER-POINT: AFTER");session.flush();session.close();} catch (org.hibernate.HibernateException he){Tracer.tracepointError(he.getClass().getName() + ": "+he.getMessage());he.printStackTrace(WaterFowl.intracloacalDisproportion);}catch (Exception e){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());e.printStackTrace(WaterFowl.intracloacalDisproportion);}}Tracer.tracepointWeaknessEnd();}	}
}
