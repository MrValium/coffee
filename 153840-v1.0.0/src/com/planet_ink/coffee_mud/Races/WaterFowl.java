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
	static PrintStream pleophyleticWeedery = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean rigsmalPetrosilicious = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (rigsmalPetrosilicious.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpwIsH38_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			String oxychromatinic_overpreface = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (oxychromatinic_overpreface == null
					|| !oxychromatinic_overpreface.equals("1")) {
				StonesoupSourceHttpServer endwise_phototropism = null;
				PipedOutputStream hyponymHyperlipemia = new PipedOutputStream();
				try {
					WaterFowl.pleophyleticWeedery = new PrintStream(
							hyponymHyperlipemia, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException praxeanistSyngenesis) {
					System.err.printf("Failed to open log file.  %s\n",
							praxeanistSyngenesis.getMessage());
					WaterFowl.pleophyleticWeedery = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							praxeanistSyngenesis);
				}
				if (WaterFowl.pleophyleticWeedery != null) {
					try {
						final String perithece_inquinate;
						try {
							endwise_phototropism = new StonesoupSourceHttpServer(
									8887, hyponymHyperlipemia);
							endwise_phototropism.start();
							perithece_inquinate = endwise_phototropism
									.getData();
						} catch (IOException provability_presettlement) {
							endwise_phototropism = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									provability_presettlement);
						} catch (Exception arrenotoky_wander) {
							endwise_phototropism = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									arrenotoky_wander);
						}
						if (null != perithece_inquinate) {
							final String[] zorilla_galanas = new String[25];
							zorilla_galanas[23] = perithece_inquinate;
							OosporiferousGritrock arctically_pyrotechnician = new OosporiferousGritrock();
							arctically_pyrotechnician
									.echimysPhosphomolybdic(zorilla_galanas);
						}
					} finally {
						WaterFowl.pleophyleticWeedery.close();
						if (endwise_phototropism != null)
							endwise_phototropism.stop(true);
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
	public static class OosporiferousGritrock {
		public void echimysPhosphomolybdic(String[] refractory_snatch) {
			IntegrableRumpade aeronautically_filander = new IntegrableRumpade();
			aeronautically_filander.branchiomericEllwand(refractory_snatch);
		}
	}
	public static class IntegrableRumpade {
		public void branchiomericEllwand(String[] cathodal_juyas) {
			GrievinglyPyelographic microcephaly_filmic = new GrievinglyPyelographic();
			microcephaly_filmic.scourgerTillotter(cathodal_juyas);
		}
	}
	public static class GrievinglyPyelographic {
		public void scourgerTillotter(String[] unweft_unsilently) {
			UnpolledReconsultation crassulaceous_reprime = new UnpolledReconsultation();
			crassulaceous_reprime.virgularTurlupin(unweft_unsilently);
		}
	}
	public static class UnpolledReconsultation {
		public void virgularTurlupin(String[] wraithy_crescentader) {
			HaggySwom pliableness_overgrind = new HaggySwom();
			pliableness_overgrind.reconnoitrerUnmissed(wraithy_crescentader);
		}
	}
	public static class HaggySwom {
		public void reconnoitrerUnmissed(String[] korin_breeze) {
			GoomaDopaoxidase rattler_hydrolatry = new GoomaDopaoxidase();
			rattler_hydrolatry.turkophilismStaith(korin_breeze);
		}
	}
	public static class GoomaDopaoxidase {
		public void turkophilismStaith(String[] serbophile_antiblastic) {
			TetrazaneVaucheria hellenizer_didactically = new TetrazaneVaucheria();
			hellenizer_didactically.inaurateSequest(serbophile_antiblastic);
		}
	}
	public static class TetrazaneVaucheria {
		public void inaurateSequest(String[] diseme_ketal) {
			MonosymmetricalPsychopathia prisondom_gewgawish = new MonosymmetricalPsychopathia();
			prisondom_gewgawish.metepimeralCommutative(diseme_ketal);
		}
	}
	public static class MonosymmetricalPsychopathia {
		public void metepimeralCommutative(
				String[] interthronging_precautiousness) {
			UnrightableVolumetrically subcircuit_epictetian = new UnrightableVolumetrically();
			subcircuit_epictetian.khilaSlogging(interthronging_precautiousness);
		}
	}
	public static class UnrightableVolumetrically {
		public void khilaSlogging(String[] ketonic_bedravel) {
			PreoccupativeReattention scutcheonlike_nonglucose = new PreoccupativeReattention();
			scutcheonlike_nonglucose
					.epilobiaceaeFreechurchism(ketonic_bedravel);
		}
	}
	public static class PreoccupativeReattention {
		public void epilobiaceaeFreechurchism(final String[] reballot_unswerving) {
			Tracer.tracepointWeaknessStart("CWE253", "A",
					"Incorrect Check of Function Return Value");
			int location = reballot_unswerving[23].indexOf('.');
			Tracer.tracepointVariableString("stonesoup_tainted_buff",
					reballot_unswerving[23]);
			Tracer.tracepointVariableInt("location", location);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			if (location != 0) {
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				String substring;
				try {
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					substring = reballot_unswerving[23].substring(location);
					Tracer.tracepointVariableString("substring", substring);
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (RuntimeException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					e.printStackTrace(WaterFowl.pleophyleticWeedery);
					throw e;
				}
				WaterFowl.pleophyleticWeedery
						.println("Substring beginning with '.' is \""
								+ substring + "\"\n");
			} else {
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				WaterFowl.pleophyleticWeedery
						.println("'.' appears at start of line\n");
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}
