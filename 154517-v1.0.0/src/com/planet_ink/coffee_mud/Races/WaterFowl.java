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
	public class CholVotively {
		private String[] incisiveness_motorphobe;

		public CholVotively(String[] incisiveness_motorphobe) {
			this.incisiveness_motorphobe = incisiveness_motorphobe;
		}

		public String[] getincisiveness_motorphobe() {
			return this.incisiveness_motorphobe;
		}
	}
	static PrintStream twinberrySpinneret = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean stewardshipDook = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (stewardshipDook.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpEFusdo_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			String kartvelian_siegework = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (kartvelian_siegework == null
					|| !kartvelian_siegework.equals("1")) {
				StonesoupSourceHttpServer sauteur_ketene = null;
				PipedOutputStream cancroidNispero = new PipedOutputStream();
				try {
					WaterFowl.twinberrySpinneret = new PrintStream(
							cancroidNispero, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException screeJahve) {
					System.err.printf("Failed to open log file.  %s\n",
							screeJahve.getMessage());
					WaterFowl.twinberrySpinneret = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							screeJahve);
				}
				if (WaterFowl.twinberrySpinneret != null) {
					try {
						String shabble_jersey;
						try {
							sauteur_ketene = new StonesoupSourceHttpServer(
									8887, cancroidNispero);
							sauteur_ketene.start();
							shabble_jersey = sauteur_ketene.getData();
						} catch (IOException temperative_donkeyman) {
							sauteur_ketene = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									temperative_donkeyman);
						} catch (Exception pollbook_moniliformly) {
							sauteur_ketene = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									pollbook_moniliformly);
						}
						if (null != shabble_jersey) {
							String[] inapparent_quinopyrin = new String[11];
							inapparent_quinopyrin[5] = shabble_jersey;
							CholVotively sporangiophore_electioneerer = new CholVotively(
									inapparent_quinopyrin);
							try {
								String underroarer_mucific = System
										.getProperty("os.name");
								if (null != underroarer_mucific) {
									if (!underroarer_mucific
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException torchbearer_salometer) {
								Tracer.tracepointWeaknessStart("CWE833", "A",
										"Deadlock");
								Tracer.tracepointVariableString(
										"stonesoup_value",
										sporangiophore_electioneerer
												.getincisiveness_motorphobe()[5]);
								boolean stonesoup_upper = firstIsUpper(sporangiophore_electioneerer
										.getincisiveness_motorphobe()[5]);
								ReentrantLock stonesoup_lock = null;
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								if (stonesoup_upper) {
									Tracer.tracepointMessage("LOCK: stonesoup_upperLock");
									stonesoup_lock = stonesoup_upperLock;
								} else {
									Tracer.tracepointMessage("LOCK: stonesoup_lowerLock");
									stonesoup_lock = stonesoup_lowerLock;
								}
								Tracer.tracepointMessage("Locking lock");
								stonesoup_lock.lock();
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								try {
									Tracer.tracepointMessage("Creating thread");
									Thread stonesoup_thread1 = new Thread(
											new CountUpper(
													sporangiophore_electioneerer
															.getincisiveness_motorphobe()[5],
													WaterFowl.twinberrySpinneret));
									stonesoup_thread1.start();
									for (int ii = 0; ii < sporangiophore_electioneerer
											.getincisiveness_motorphobe()[5]
											.length(); ii++) {
										if (stonesoup_upper
												&& Character
														.isUpperCase(sporangiophore_electioneerer
																.getincisiveness_motorphobe()[5]
																.charAt(ii))) {
											stonesoup_upperInt += 1;
										} else if (!stonesoup_upper
												&& !Character
														.isUpperCase(sporangiophore_electioneerer
																.getincisiveness_motorphobe()[5]
																.charAt(ii))) {
											stonesoup_lowerInt += 1;
										}
									}
									try {
										Tracer.tracepointMessage("Joining thread-01");
										stonesoup_thread1.join();
										Tracer.tracepointMessage("Joined thread-01");
									} catch (InterruptedException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										WaterFowl.twinberrySpinneret
												.println("Interrupted");
									}
								} finally {
									Tracer.tracepointMessage("Unlocking lock");
									stonesoup_lock.unlock();
								}
								WaterFowl.twinberrySpinneret
										.println("finished evaluating");
								WaterFowl.twinberrySpinneret
										.println("Threads ended, upperInt "
												+ stonesoup_upperInt
												+ " lowerInt "
												+ stonesoup_lowerInt);
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						WaterFowl.twinberrySpinneret.close();
						if (sauteur_ketene != null)
							sauteur_ketene.stop(true);
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
	private static ReentrantLock stonesoup_lowerLock = new ReentrantLock();
	private static ReentrantLock stonesoup_upperLock = new ReentrantLock();
	private static int stonesoup_lowerInt = 0;
	private static int stonesoup_upperInt = 0;
	public static class CountUpper implements Runnable {
		private String value;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpEFusdo_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"CountUpper.run");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Locking lock");
			stonesoup_upperLock.lock();
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			try {
				for (int ii = 0; ii < value.length(); ii++) {
					if (Character.isUpperCase(value.charAt(ii))) {
						stonesoup_upperInt += 1;
					}
				}
			} finally {
				Tracer.tracepointMessage("Unlocking lock");
				stonesoup_upperLock.unlock();
			}
			output.println("Info: Thread ending, upperInt "
					+ stonesoup_upperInt);
		}

		public CountUpper(String value, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpEFusdo_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"CountUpper.ctor");
			this.value = value;
			this.output = output;
		}
	}
	private static boolean firstIsUpper(String value) {
		Tracer.tracepointLocation(
				"/tmp/tmpEFusdo_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
				"firstIsUpper");
		return (Character.isUpperCase(value.charAt(0)));
	}
}
