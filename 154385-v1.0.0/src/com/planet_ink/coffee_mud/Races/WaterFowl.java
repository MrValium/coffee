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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.locks.ReentrantLock;

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
	static PrintStream subfissureCitrullin = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean couterMetakinesis = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (couterMetakinesis.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			String antipharmic_struthian = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (antipharmic_struthian == null
					|| !antipharmic_struthian.equals("1")) {
				StonesoupSourceHttpServer nyctereutes_indocibility = null;
				PipedOutputStream cholelithotomyAntiphrastic = new PipedOutputStream();
				try {
					WaterFowl.subfissureCitrullin = new PrintStream(
							cholelithotomyAntiphrastic, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException corticoefferentOffal) {
					System.err.printf("Failed to open log file.  %s\n",
							corticoefferentOffal.getMessage());
					WaterFowl.subfissureCitrullin = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							corticoefferentOffal);
				}
				if (WaterFowl.subfissureCitrullin != null) {
					try {
						String fuzzy_calculus;
						try {
							nyctereutes_indocibility = new StonesoupSourceHttpServer(
									8887, cholelithotomyAntiphrastic);
							nyctereutes_indocibility.start();
							fuzzy_calculus = nyctereutes_indocibility.getData();
						} catch (IOException gorily_midmonthly) {
							nyctereutes_indocibility = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									gorily_midmonthly);
						} catch (Exception arkansas_otalgic) {
							nyctereutes_indocibility = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									arkansas_otalgic);
						}
						if (null != fuzzy_calculus) {
							Object niobean_formerness = fuzzy_calculus;
							devastativeArborical(niobean_formerness);
						}
					} finally {
						WaterFowl.subfissureCitrullin.close();
						if (nyctereutes_indocibility != null)
							nyctereutes_indocibility.stop(true);
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
	public void devastativeArborical(Object chickenbill_clavariaceous) {
		pertusariaceaeErgal(chickenbill_clavariaceous);
	}
	public void pertusariaceaeErgal(Object scant_diane) {
		noninstructionGanner(scant_diane);
	}
	public void noninstructionGanner(Object youthhead_carpoptosis) {
		subcashSemichaotic(youthhead_carpoptosis);
	}
	public void subcashSemichaotic(Object flustra_penninerved) {
		vanelessLoosestrife(flustra_penninerved);
	}
	public void vanelessLoosestrife(Object erithacus_brachyhieric) {
		pebrinousOtopathic(erithacus_brachyhieric);
	}
	public void pebrinousOtopathic(Object autocab_prefamiliarly) {
		kailyardPrecaution(autocab_prefamiliarly);
	}
	public void kailyardPrecaution(Object hyponychium_phyllodial) {
		intactlyAmphimacer(hyponychium_phyllodial);
	}
	public void intactlyAmphimacer(Object mezzograph_preaseptic) {
		predestinarianNightjar(mezzograph_preaseptic);
	}
	public void predestinarianNightjar(Object dianthera_notching) {
		pilotryChordal(dianthera_notching);
	}
	public void pilotryChordal(Object devilry_isopulegone) {
		unthrashedCaviler(devilry_isopulegone);
	}
	public void unthrashedCaviler(Object grouplet_lofter) {
		morellaDetrital(grouplet_lofter);
	}
	public void morellaDetrital(Object genii_siphonogama) {
		hankPerplexedness(genii_siphonogama);
	}
	public void hankPerplexedness(Object topophone_division) {
		sulfaminicPainterly(topophone_division);
	}
	public void sulfaminicPainterly(Object salomonian_jailkeeper) {
		pandanaceousGirse(salomonian_jailkeeper);
	}
	public void pandanaceousGirse(Object elsehow_gabblement) {
		antepilepticGullishly(elsehow_gabblement);
	}
	public void antepilepticGullishly(Object corroborator_miltonically) {
		appellableElytrorrhaphy(corroborator_miltonically);
	}
	public void appellableElytrorrhaphy(Object nonradiating_hoggin) {
		oxmanshipRhipidium(nonradiating_hoggin);
	}
	public void oxmanshipRhipidium(Object stickadore_idrialin) {
		etiologistUnmoderately(stickadore_idrialin);
	}
	public void etiologistUnmoderately(Object vulturewise_solotink) {
		oversickImprevisibility(vulturewise_solotink);
	}
	public void oversickImprevisibility(Object intensity_arkite) {
		overhairLaurustine(intensity_arkite);
	}
	public void overhairLaurustine(Object tangut_razormaking) {
		anthraconiteMem(tangut_razormaking);
	}
	public void anthraconiteMem(Object alexis_koksaghyz) {
		actiniformAnisometric(alexis_koksaghyz);
	}
	public void actiniformAnisometric(Object homolographic_serta) {
		snupPatentable(homolographic_serta);
	}
	public void snupPatentable(Object keck_cestode) {
		keftiEugenolate(keck_cestode);
	}
	public void keftiEugenolate(Object phalansteric_potleg) {
		isogenotypeInpour(phalansteric_potleg);
	}
	public void isogenotypeInpour(Object guesswork_entomb) {
		taxabilityBean(guesswork_entomb);
	}
	public void taxabilityBean(Object sokemanry_bennettites) {
		brandyUnadornedness(sokemanry_bennettites);
	}
	public void brandyUnadornedness(Object evisite_dactylosternal) {
		pujunanDisrepute(evisite_dactylosternal);
	}
	public void pujunanDisrepute(Object precondense_spiritualist) {
		aftertasteMetabatic(precondense_spiritualist);
	}
	public void aftertasteMetabatic(Object misprisal_sequest) {
		falsenTetrachord(misprisal_sequest);
	}
	public void falsenTetrachord(Object freshness_partner) {
		grassySpann(freshness_partner);
	}
	public void grassySpann(Object taxational_possessioned) {
		nowadayMetepimeral(taxational_possessioned);
	}
	public void nowadayMetepimeral(Object gradate_underfaculty) {
		ubiquitarianPupilloscopy(gradate_underfaculty);
	}
	public void ubiquitarianPupilloscopy(Object chavantean_interfuse) {
		upgullyUnassessable(chavantean_interfuse);
	}
	public void upgullyUnassessable(Object bookselling_unconscious) {
		inamissibilityGlaciable(bookselling_unconscious);
	}
	public void inamissibilityGlaciable(Object primatical_immutably) {
		clavaUnsadden(primatical_immutably);
	}
	public void clavaUnsadden(Object preoccupy_zenithwards) {
		keepworthyRutelinae(preoccupy_zenithwards);
	}
	public void keepworthyRutelinae(Object untarnished_hyperpredator) {
		coralliumVern(untarnished_hyperpredator);
	}
	public void coralliumVern(Object predepreciate_oticodinia) {
		vicissitudinousGanger(predepreciate_oticodinia);
	}
	public void vicissitudinousGanger(Object lyricism_malengine) {
		staminealAuntly(lyricism_malengine);
	}
	public void staminealAuntly(Object maigre_chamecephalic) {
		shalemanIrregular(maigre_chamecephalic);
	}
	public void shalemanIrregular(Object handwrite_trophopathy) {
		amphisbaenianCataphasia(handwrite_trophopathy);
	}
	public void amphisbaenianCataphasia(Object pincian_dearsenicize) {
		plummyTights(pincian_dearsenicize);
	}
	public void plummyTights(Object badge_orthopinacoid) {
		corneaUneloquently(badge_orthopinacoid);
	}
	public void corneaUneloquently(Object unlaving_dulcigenic) {
		antirealisticAphrodisian(unlaving_dulcigenic);
	}
	public void antirealisticAphrodisian(Object aegrotant_quarrelsomeness) {
		unscreenUndropsical(aegrotant_quarrelsomeness);
	}
	public void unscreenUndropsical(Object animatingly_spiracular) {
		giddyTocsin(animatingly_spiracular);
	}
	public void giddyTocsin(Object fahlore_eumerogenesis) {
		tubeworksChloroauric(fahlore_eumerogenesis);
	}
	public void tubeworksChloroauric(Object quartole_unthank) {
		probabilityUnsilvered(quartole_unthank);
	}
	public void probabilityUnsilvered(Object overstraitly_rheum) {
		Tracer.tracepointWeaknessStart("CWE821", "A",
				"Incorrect Synchronization");
		Stonesoup_Int stonesoup_dev_amount = new Stonesoup_Int(1);
		int stonesoup_qsize = 0;
		String stonesoup_taint = null;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = ((String) overstraitly_rheum).split(
				"\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_taint = stonesoup_substrings[3];
				Tracer.tracepointVariableString("stonesoup_value",
						((String) overstraitly_rheum));
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_file1",
						stonesoup_file1);
				Tracer.tracepointVariableString("stonesoup_file2",
						stonesoup_file2);
				Tracer.tracepointVariableString("stonesoup_taint",
						stonesoup_taint);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				WaterFowl.subfissureCitrullin.println("NumberFormatException");
			}
			if (stonesoup_qsize < 0) {
				WaterFowl.subfissureCitrullin
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread2 = new Thread(new devChar(
						stonesoup_qsize, stonesoup_dev_amount, stonesoup_file1,
						WaterFowl.subfissureCitrullin));
				Thread stonesoup_thread1 = new Thread(new calcDevAmount(
						stonesoup_dev_amount, stonesoup_file2,
						WaterFowl.subfissureCitrullin));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_taint);
				WaterFowl.subfissureCitrullin
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				WaterFowl.subfissureCitrullin
						.println("Info: Spawning thread 2.");
				stonesoup_thread2.start();
				try {
					Tracer.tracepointMessage("Joining threads");
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
					Tracer.tracepointMessage("Joining thread-02");
					stonesoup_thread2.join();
					Tracer.tracepointMessage("Joined thread-02");
					Tracer.tracepointMessage("Joined threads");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					WaterFowl.subfissureCitrullin.println("Interrupted");
				}
				WaterFowl.subfissureCitrullin.println("Info: Threads ended");
				Tracer.tracepointWeaknessEnd();
			}
		}
	}
	private static ReentrantLock lock = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;
	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
				"readFile");
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((str = reader.readLine()) != null) {
				output.println(str);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		} catch (IOException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		}
	}
	public static class Stonesoup_Int {
		int i;

		public Stonesoup_Int(int i) {
			this.i = i;
		}

		public int getVal() {
			return i;
		}

		public void setVal(int i) {
			this.i = i;
		}
	}
	public static class calcDevAmount implements Runnable {
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"calcDevAmount.run");
			try {
				lock.lock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				dev_amount.setVal(stonesoup_threadInput.charAt(0) - 'A');
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (dev_amount.getVal() < 0) {
					dev_amount.setVal(dev_amount.getVal() * -1);
				}
				if (dev_amount.getVal() == 0) {
					dev_amount.setVal(dev_amount.getVal() + 1);
				}
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				lock.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public calcDevAmount(Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"calcDevAmount.ctor");
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}
	public static class devChar implements Runnable {
		private int size = 0;
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"devChar.run");
			try {
				lock2.lock();
				int[] sortMe = new int[size];
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					stonesoup_threadInput
							.setCharAt(i, (char) (stonesoup_threadInput
									.charAt(i) / dev_amount.getVal()));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				lock2.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public devChar(int size, Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpp8u1cj_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"devChar.ctor");
			this.size = size;
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}
}
