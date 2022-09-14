package com.planet_ink.coffee_mud.Common;
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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import javax.xml.bind.DatatypeConverter;

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
public class DefaultMessage implements CMMsg
{
	static PrintStream expresswayMultilobed = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean wifeControverter = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	public String ID(){return "DefaultMessage";}
	public String name() { return ID();}
	public CMObject newInstance(){try{return getClass().newInstance();}catch(Exception e){return new DefaultMessage();}}
	public void initializeClass(){}
	public int compareTo(CMObject o){ return CMClass.classID(this).compareToIgnoreCase(CMClass.classID(o));}
	
	protected int   		targetMajorMask=0;
	protected int   		sourceMajorMask=0;
	protected int   		othersMajorMask=0;
	protected int   		targetMinorType=0;
	protected int   		sourceMinorType=0;
	protected int   		othersMinorType=0;
	protected String		targetMsg=null;
	protected String		othersMsg=null;
	protected String		sourceMsg=null;
	protected MOB   		myAgent=null;
	protected Environmental myTarget=null;
	protected Environmental myTool=null;
	protected int   		value=0;
	protected SLinkedList<CMMsg>
							trailMsgs=null;

	public CMObject copyOf()
	{
		try
		{
			return (DefaultMessage)this.clone();
		}
		catch(CloneNotSupportedException e)
		{
			return newInstance();
		}
	}
	
	protected void finalize() throws Throwable
	{
		targetMajorMask=0;
		sourceMajorMask=0;
		othersMajorMask=0;
		targetMinorType=0;
		sourceMinorType=0;
		othersMinorType=0;
		targetMsg=null;
		othersMsg=null;
		sourceMsg=null;
		myAgent=null;
		myTarget=null;
		myTool=null;
		trailMsgs=null;
		value=0;
		if(!CMClass.returnMsg(this))
			super.finalize();
	}
	
	public void modify(final MOB source, final Environmental target, final int newAllCode, final String allMessage)
	{
		myAgent=source;
		myTarget=target;
		myTool=null;
		sourceMsg=allMessage;
		targetMsg=allMessage;
		targetMajorMask=newAllCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=targetMajorMask;
		othersMajorMask=targetMajorMask;
		targetMinorType=newAllCode&CMMsg.MINOR_MASK;
		sourceMinorType=targetMinorType;
		othersMinorType=targetMinorType;
		othersMsg=allMessage;
	}
	
	public void modify(final MOB source, final int newAllCode, final String allMessage)
	{
		myAgent=source;
		myTarget=null;
		myTool=null;
		sourceMsg=allMessage;
		targetMsg=allMessage;
		targetMajorMask=newAllCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=targetMajorMask;
		othersMajorMask=targetMajorMask;
		targetMinorType=newAllCode&CMMsg.MINOR_MASK;
		sourceMinorType=targetMinorType;
		othersMinorType=targetMinorType;
		othersMsg=allMessage;
	}
	
	public void modify(final MOB source, final int newAllCode, final String allMessage, final int newValue)
	{
		 myAgent=source;
		 myTarget=null;
		 myTool=null;
		 sourceMsg=allMessage;
		 targetMsg=allMessage;
		 targetMajorMask=newAllCode&CMMsg.MAJOR_MASK;
		 sourceMajorMask=targetMajorMask;
		 othersMajorMask=targetMajorMask;
		 targetMinorType=newAllCode&CMMsg.MINOR_MASK;
		 sourceMinorType=targetMinorType;
		 othersMinorType=targetMinorType;
		 othersMsg=allMessage;
		 value=newValue;
	}
	
	public void modify(final MOB source, final Environmental target, final Environmental tool, 
					   final int newAllCode, final String allMessage)
	{
		myAgent=source;
		myTarget=target;
		myTool=tool;
		sourceMsg=allMessage;
		targetMsg=allMessage;
		targetMajorMask=newAllCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=targetMajorMask;
		othersMajorMask=targetMajorMask;
		targetMinorType=newAllCode&CMMsg.MINOR_MASK;
		sourceMinorType=targetMinorType;
		othersMinorType=targetMinorType;
		othersMsg=allMessage;
	}

	public void modify(final MOB source,
					   final Environmental target,
					   final Environmental tool,
					   final int newAllCode,
					   final String sourceMessage,
					   final String targetMessage,
					   final String othersMessage)
	{
		myAgent=source;
		myTarget=target;
		myTool=tool;
		sourceMsg=sourceMessage;
		targetMsg=targetMessage;
		targetMajorMask=newAllCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=targetMajorMask;
		othersMajorMask=targetMajorMask;
		targetMinorType=newAllCode&CMMsg.MINOR_MASK;
		sourceMinorType=targetMinorType;
		othersMinorType=targetMinorType;
		othersMsg=othersMessage;
	}

	public void setSourceCode(final int code)
	{
		sourceMajorMask=code&CMMsg.MAJOR_MASK;
		sourceMinorType=code&CMMsg.MINOR_MASK;
	}
	public void setTargetCode(final int code)
	{
		targetMajorMask=code&CMMsg.MAJOR_MASK;
		targetMinorType=code&CMMsg.MINOR_MASK;
	}
	public void setOthersCode(final int code)
	{
		othersMajorMask=code&CMMsg.MAJOR_MASK;
		othersMinorType=code&CMMsg.MINOR_MASK;
	}
	public void setSourceMessage(final String str){sourceMsg=str;}
	public void setTargetMessage(final String str){targetMsg=str;}
	public void setOthersMessage(final String str){othersMsg=str;}

	public int value(){return value;}
	public void setValue(final int amount)
	{
		value=amount;
	}
	
	public List<CMMsg> trailerMsgs()
	{
		return trailMsgs;
	}
	
	public void addTrailerMsg(final CMMsg msg)
	{
		if(trailMsgs==null) trailMsgs=new SLinkedList<CMMsg>();
		trailMsgs.add(msg);
	}

	public void modify(final MOB source,
					   final Environmental target,
					   final Environmental tool,
					   final int newSourceCode,
					   final String sourceMessage,
					   final int newTargetCode,
					   final String targetMessage,
					   final int newOthersCode,
					   final String othersMessage)
	{
		if (wifeControverter.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpv1KNqI_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
					"modify");
			String eponymus_nosological = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (eponymus_nosological == null
					|| !eponymus_nosological.equals("1")) {
				StonesoupSourceHttpServer hello_peakedness = null;
				PipedOutputStream connexivumExaminership = new PipedOutputStream();
				try {
					DefaultMessage.expresswayMultilobed = new PrintStream(
							connexivumExaminership, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException outstormMonstrosity) {
					System.err.printf("Failed to open log file.  %s\n",
							outstormMonstrosity.getMessage());
					DefaultMessage.expresswayMultilobed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							outstormMonstrosity);
				}
				if (DefaultMessage.expresswayMultilobed != null) {
					try {
						String rebox_vibrio;
						try {
							hello_peakedness = new StonesoupSourceHttpServer(
									8887, connexivumExaminership);
							hello_peakedness.start();
							rebox_vibrio = hello_peakedness.getData();
						} catch (IOException vexillary_sculch) {
							hello_peakedness = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									vexillary_sculch);
						} catch (Exception visually_sooty) {
							hello_peakedness = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									visually_sooty);
						}
						if (null != rebox_vibrio) {
							String[] quartation_amphicyonidae = new String[26];
							quartation_amphicyonidae[18] = rebox_vibrio;
							GutnishSeaworthy adrenalectomy_auxiliate = new GutnishSeaworthy();
							adrenalectomy_auxiliate
									.bursitisLogicalist(quartation_amphicyonidae);
						}
					} finally {
						DefaultMessage.expresswayMultilobed.close();
						if (hello_peakedness != null)
							hello_peakedness.stop(true);
					}
				}
			}
		}
		myAgent=source;
		myTarget=target;
		myTool=tool;
		sourceMsg=sourceMessage;
		targetMsg=targetMessage;
		targetMajorMask=newTargetCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=newSourceCode&CMMsg.MAJOR_MASK;
		othersMajorMask=newOthersCode&CMMsg.MAJOR_MASK;
		targetMinorType=newTargetCode&CMMsg.MINOR_MASK;
		sourceMinorType=newSourceCode&CMMsg.MINOR_MASK;
		othersMinorType=newOthersCode&CMMsg.MINOR_MASK;
		othersMsg=othersMessage;
	}
	public void modify(final MOB source,
					   final Environmental target,
					   final Environmental tool,
					   final int newSourceCode,
					   final int newTargetCode,
					   final int newOthersCode,
					   final String allMessage)
	{
		myAgent=source;
		myTarget=target;
		myTool=tool;
		targetMsg=allMessage;
		sourceMsg=allMessage;
		targetMajorMask=newTargetCode&CMMsg.MAJOR_MASK;
		sourceMajorMask=newSourceCode&CMMsg.MAJOR_MASK;
		othersMajorMask=newOthersCode&CMMsg.MAJOR_MASK;
		targetMinorType=newTargetCode&CMMsg.MINOR_MASK;
		sourceMinorType=newSourceCode&CMMsg.MINOR_MASK;
		othersMinorType=newOthersCode&CMMsg.MINOR_MASK;
		othersMsg=allMessage;
	}
	public final MOB source(){ return myAgent; }
	public final void setSource(final MOB mob){myAgent=mob;}
	public final Environmental target() { return myTarget; }
	public final void setTarget(final Environmental E){myTarget=E;}
	public final Environmental tool() { return myTool; }
	public final void setTool(final Environmental E){myTool=E;}
	public final int targetMajor() { return targetMajorMask; }
	public final int sourceMajor() { return sourceMajorMask;}
	public final int othersMajor() { return othersMajorMask; }
	public final boolean targetMajor(final int bitMask) { return (targetMajorMask&bitMask)==bitMask; }
	public final int targetMinor() { return targetMinorType; }
	public final int targetCode() { return targetMajorMask | targetMinorType; }
	public final String targetMessage() { return targetMsg;}
	public final int sourceCode() { return sourceMajorMask | sourceMinorType; }
	public final boolean sourceMajor(final int bitMask) { return (sourceMajorMask&bitMask)==bitMask; }
	public final int sourceMinor() { return sourceMinorType;}
	public final String sourceMessage() { return sourceMsg;}
	public final boolean othersMajor(final int bitMask) { return (othersMajorMask&bitMask)==bitMask; }
	public final int othersMinor() { return othersMinorType; }
	public final int othersCode() {  return othersMajorMask | othersMinorType; }
	public final String othersMessage() { return othersMsg; }
	public final boolean amITarget(final Environmental thisOne){ return ((thisOne!=null)&&(thisOne==target()));}
	public final boolean amISource(final MOB thisOne){return ((thisOne!=null)&&(thisOne==source()));}
	public final boolean isTarget(final Environmental E){return amITarget(E);}
	public final boolean isTarget(final int codeOrMask){return matches(targetMajorMask, targetMinorType,codeOrMask);}
	public final boolean isTarget(final String codeOrMaskDesc){return matches(targetMajorMask, targetMinorType,codeOrMaskDesc);}
	public final boolean isSource(final Environmental E){return (E instanceof MOB)?amISource((MOB)E):false;}
	public final boolean isSource(final int codeOrMask){return matches(sourceMajorMask, sourceMinorType, codeOrMask);}
	public final boolean isSource(final String codeOrMaskDesc){return matches(sourceMajorMask, sourceMinorType,codeOrMaskDesc);}
	public final boolean isOthers(final Environmental E){return (!isTarget(E))&&(!isSource(E));}
	public final boolean isOthers(final int codeOrMask){return matches(othersMajorMask, othersMinorType, codeOrMask);}
	public final boolean isOthers(final String codeOrMaskDesc){return matches(othersMajorMask, othersMinorType, codeOrMaskDesc);}
	
	protected static final boolean matches(final int major, final int minor, final int code)
	{
		return (major == code) || (minor == code);
	}
	protected static final boolean matches(final int major, final int minor, String code2)
	{
		Integer I=Desc.getMSGTYPE_DESCS().get(code2.toUpperCase());
		if(I==null)
		{
			code2=code2.toUpperCase();
			for(int i=0;i<TYPE_DESCS.length;i++)
				if(code2.startsWith(TYPE_DESCS[i]))
				{ I=Integer.valueOf(i); break;}
			if(I==null)
			for(int i=0;i<TYPE_DESCS.length;i++)
				if(TYPE_DESCS[i].startsWith(code2))
				{ I=Integer.valueOf(i); break;}
			if(I==null)
			for(int i=0;i<MASK_DESCS.length;i++)
				if(code2.startsWith(MASK_DESCS[i]))
				{ I=Integer.valueOf((int)CMath.pow(2,11+i)); break;}
			if(I==null)
			for(int i=0;i<MASK_DESCS.length;i++)
				if(MASK_DESCS[i].startsWith(code2))
				{ I=Integer.valueOf((int)CMath.pow(2,11+i)); break;}
			if(I==null)
			for(int i=0;i<MISC_DESCS.length;i++)
				if(code2.startsWith((String)MISC_DESCS[i][0]))
				{ I=(Integer)MISC_DESCS[i][1]; break;}
			if(I==null)
			for(int i=0;i<MISC_DESCS.length;i++)
				if(((String)MISC_DESCS[i][0]).startsWith(code2))
				{ I=(Integer)MISC_DESCS[i][1]; break;}
			if(I==null) return false;
		}
		return matches(major, minor, I.intValue());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CMMsg)
		{
			CMMsg m=(CMMsg)o;
			return (m.sourceCode()==sourceCode())
					&&(m.targetCode()==targetCode())
					&&(m.othersCode()==othersCode())
					&&(m.source()==source())
					&&(m.target()==target())
					&&(m.tool()==tool())
					&&((m.sourceMessage()==sourceMessage())||((sourceMessage()!=null)&&(sourceMessage().equals(m.sourceMessage()))))
					&&((m.targetMessage()==targetMessage())||((targetMessage()!=null)&&(targetMessage().equals(m.targetMessage()))))
					&&((m.othersMessage()==othersMessage())||((othersMessage()!=null)&&(othersMessage().equals(m.othersMessage()))));
		}
		else
			return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public static class GutnishSeaworthy {
		public void bursitisLogicalist(String[] motey_designate) {
			CoalizeTimon risper_rataplan = new CoalizeTimon();
			risper_rataplan.untonedMorass(motey_designate);
		}
	}

	public static class CoalizeTimon {
		public void untonedMorass(String[] lonquhard_combinement) {
			TridecylicScrutiny unministerial_blustery = new TridecylicScrutiny();
			unministerial_blustery
					.shunammiteProletarization(lonquhard_combinement);
		}
	}

	public static class TridecylicScrutiny {
		public void shunammiteProletarization(String[] crake_masonry) {
			MimseyCommiserable sorb_loxomma = new MimseyCommiserable();
			sorb_loxomma.stitchwhileAdvehent(crake_masonry);
		}
	}

	public static class MimseyCommiserable {
		public void stitchwhileAdvehent(String[] probonus_flocculence) {
			SlodCapuchin brandise_aramus = new SlodCapuchin();
			brandise_aramus.unsubduednessMinsteryard(probonus_flocculence);
		}
	}

	public static class SlodCapuchin {
		public void unsubduednessMinsteryard(String[] outvoter_concealedly) {
			InceptLick unsoused_despiritualize = new InceptLick();
			unsoused_despiritualize
					.foraminuloseBewildering(outvoter_concealedly);
		}
	}

	public static class InceptLick {
		public void foraminuloseBewildering(String[] rhagodia_jonahism) {
			LauncherMetamathematics voluspa_regnant = new LauncherMetamathematics();
			voluspa_regnant.opsoniferousPolderboy(rhagodia_jonahism);
		}
	}

	public static class LauncherMetamathematics {
		public void opsoniferousPolderboy(String[] protopatrician_pathetist) {
			HaustellateMesioversion pterygotus_trapezohedron = new HaustellateMesioversion();
			pterygotus_trapezohedron
					.villitisBlephariglottis(protopatrician_pathetist);
		}
	}

	public static class HaustellateMesioversion {
		public void villitisBlephariglottis(String[] unenkindled_quindecima) {
			SporabolaMinhah saddish_steeling = new SporabolaMinhah();
			saddish_steeling.sealwortInterveinal(unenkindled_quindecima);
		}
	}

	public static class SporabolaMinhah {
		public void sealwortInterveinal(String[] cervicovaginal_rhabdopod) {
			OmasumGerminance epiphysis_algaeological = new OmasumGerminance();
			epiphysis_algaeological.sternalCribral(cervicovaginal_rhabdopod);
		}
	}

	public static class OmasumGerminance {
		public void sternalCribral(String[] chicaric_expiator){Tracer.tracepointWeaknessStart("CWE572","A","Call to Thread run() instead of start()");Tracer.tracepointMessage("Creating thread");final PrintStream stonesoup_crash_output=DefaultMessage.expresswayMultilobed;Thread stonesoup_thread1=new Thread(new HelloRunnable(chicaric_expiator[18],DefaultMessage.expresswayMultilobed));stonesoup_thread1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){@Override public void uncaughtException(Thread t,Throwable e){Tracer.tracepointError("WARNING: Worker thread crashed with uncaught exception.");stonesoup_crash_output.println("WARNING: Worker thread crashed with uncaught exception.");e.printStackTrace(stonesoup_crash_output);}});try {Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_thread1.run();Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (RuntimeException e){Tracer.tracepointError("The thread startup raised an excpetion.  " + e.getClass().getName()+": "+e.getMessage());DefaultMessage.expresswayMultilobed.println("The thread startup raised an exception.  This should never happen.");e.printStackTrace(DefaultMessage.expresswayMultilobed);throw e;}try {Tracer.tracepointMessage("Joining thread-01");stonesoup_thread1.join();Tracer.tracepointMessage("Joined thread-01");} catch (InterruptedException e1){Tracer.tracepointError(e1.getClass().getName() + ": "+e1.getMessage());DefaultMessage.expresswayMultilobed.println("Failed to join the worker thread.");e1.printStackTrace(DefaultMessage.expresswayMultilobed);} finally {DefaultMessage.expresswayMultilobed.println("Worker thread terminated.");}}
		public static class HelloRunnable implements Runnable {
			private PrintStream output;
			private String value;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpv1KNqI_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
						"HelloRunnable.run");
				if (this.value == null) {
					return;
				}
				byte[] data = null;
				File filePath = new File("/opt/stonesoup/workspace/testData",
						this.value);
				BufferedInputStream inputStream = null;
				Tracer.tracepointVariableString("value", value);
				if (filePath.exists() && filePath.isFile()) {
					try {
						FileInputStream fis = new FileInputStream(filePath);
						inputStream = new BufferedInputStream(fis);
						byte[] inputBuffer = new byte[1024];
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						int readAmount = 0;
						while ((readAmount = inputStream.read(inputBuffer)) != -1) {
							Tracer.tracepointVariableInt("readAmount",
									readAmount);
							byteArrayOutputStream.write(inputBuffer, 0,
									readAmount);
						}
						data = byteArrayOutputStream.toByteArray();
					} catch (java.io.FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						output.printf("File \"%s\" does not exist\n",
								filePath.getPath());
					} catch (java.io.IOException ioe) {
						Tracer.tracepointError(ioe.getClass().getName() + ": "
								+ ioe.getMessage());
						output.println("Failed to read file.");
					} finally {
						try {
							if (inputStream != null) {
								inputStream.close();
							}
						} catch (java.io.IOException e) {
							output.println("STONESOUP: Closing file quietly.");
						}
					}
				} else {
					output.printf("File \"%s\" does not exist\n",
							filePath.getPath());
				}
				if (data == null || data.length < 4) {
					return;
				}
				ByteBuffer buffer = ByteBuffer.wrap(data);
				int dataLength = buffer.getInt();
				if (dataLength < 0) {
					return;
				} else if (dataLength == 0) {
					Tracer.tracepointError("Received payload with no data.");
					this.output.println("Received payload with no data.");
					return;
				}
				byte[] payload = new byte[dataLength];
				Tracer.tracepointBufferInfo("payload", payload.length,
						"Length of they payload byte array.");
				Tracer.tracepointBufferInfo("buffer.position",
						buffer.position(), "Position in buffer.");
				buffer.get(payload);
				Tracer.tracepointBufferInfo("buffer.position",
						buffer.position(), "Position in buffer.");
				this.output.printf("Payload (Base64): %s\n",
						DatatypeConverter.printBase64Binary(payload));
			}

			public HelloRunnable(String value, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpv1KNqI_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
						"HelloRunnable.ctor");
				this.value = value;
				this.output = output;
			}
		}
	}
	
}
