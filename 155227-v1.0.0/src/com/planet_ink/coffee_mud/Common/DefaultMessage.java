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
	static PrintStream hlithskjalfFurthest = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean analcimiteWoefulness = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (analcimiteWoefulness.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp3ty9VB_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
					"modify");
			String staidness_eggfish = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (staidness_eggfish == null || !staidness_eggfish.equals("1")) {
				StonesoupSourceHttpServer nemathelminth_titleship = null;
				PipedOutputStream pseudogasterCryptoproselyte = new PipedOutputStream();
				try {
					DefaultMessage.hlithskjalfFurthest = new PrintStream(
							pseudogasterCryptoproselyte, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException errhinePhysically) {
					System.err.printf("Failed to open log file.  %s\n",
							errhinePhysically.getMessage());
					DefaultMessage.hlithskjalfFurthest = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							errhinePhysically);
				}
				if (DefaultMessage.hlithskjalfFurthest != null) {
					try {
						final String legalness_parlay;
						try {
							nemathelminth_titleship = new StonesoupSourceHttpServer(
									8887, pseudogasterCryptoproselyte);
							nemathelminth_titleship.start();
							legalness_parlay = nemathelminth_titleship
									.getData();
						} catch (IOException semicoke_unreefed) {
							nemathelminth_titleship = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									semicoke_unreefed);
						} catch (Exception disburden_thunor) {
							nemathelminth_titleship = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									disburden_thunor);
						}
						if (null != legalness_parlay) {
							final short reallege_uncivilize;
							try {
								reallege_uncivilize = Short
										.parseShort(legalness_parlay);
							} catch (NumberFormatException recompensable_wavemeter) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										recompensable_wavemeter);
							}
							final short[] inconsumable_bathyesthesia = new short[13];
							inconsumable_bathyesthesia[4] = reallege_uncivilize;
							EgomismMicrographic designlessness_shrimpi = new EgomismMicrographic();
							designlessness_shrimpi
									.becalmParoemiography(inconsumable_bathyesthesia);
						}
					} finally {
						DefaultMessage.hlithskjalfFurthest.close();
						if (nemathelminth_titleship != null)
							nemathelminth_titleship.stop(true);
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

	public static class EgomismMicrographic {
		public void becalmParoemiography(short[] chronical_antirachitic) {
			RheumWindlasser outbetter_furze = new RheumWindlasser();
			outbetter_furze.popeyeArcifinious(chronical_antirachitic);
		}
	}

	public static class RheumWindlasser {
		public void popeyeArcifinious(short[] creep_formularist) {
			VirtualCanions nonfinancial_thereckly = new VirtualCanions();
			nonfinancial_thereckly.mischristenLoxodromical(creep_formularist);
		}
	}

	public static class VirtualCanions {
		public void mischristenLoxodromical(short[] unold_carbonide) {
			AlcaicCasuistess odoriferosity_esponton = new AlcaicCasuistess();
			odoriferosity_esponton.multiverseEximious(unold_carbonide);
		}
	}

	public static class AlcaicCasuistess {
		public void multiverseEximious(short[] nattily_hemoglobinuria) {
			TroncRenascence impacability_dialectics = new TroncRenascence();
			impacability_dialectics
					.snakeberryIschiopubic(nattily_hemoglobinuria);
		}
	}

	public static class TroncRenascence {
		public void snakeberryIschiopubic(short[] underaction_streptococcus) {
			CotyledonalRecalcitrant deutoxide_retrochoir = new CotyledonalRecalcitrant();
			deutoxide_retrochoir
					.proofreadingSuperexistent(underaction_streptococcus);
		}
	}

	public static class CotyledonalRecalcitrant {
		public void proofreadingSuperexistent(short[] hako_concordancer) {
			UnimpressedOlethreutidae enchantress_jecoral = new UnimpressedOlethreutidae();
			enchantress_jecoral.ondameterSubopaque(hako_concordancer);
		}
	}

	public static class UnimpressedOlethreutidae {
		public void ondameterSubopaque(short[] zebrula_unsufficient) {
			OversilenceZorgite quinault_unscabbarded = new OversilenceZorgite();
			quinault_unscabbarded.abroniaTriannulate(zebrula_unsufficient);
		}
	}

	public static class OversilenceZorgite {
		public void abroniaTriannulate(short[] unsucculent_voluminosity) {
			BothererFerngrower substantiveness_bradford = new BothererFerngrower();
			substantiveness_bradford.ravinVoidless(unsucculent_voluminosity);
		}
	}

	public static class BothererFerngrower {
		public void ravinVoidless(short[] nonantigenic_adhafera) {
			ScolopendraOsmina parricided_web = new ScolopendraOsmina();
			parricided_web.pitsideChadacryst(nonantigenic_adhafera);
		}
	}

	public static class ScolopendraOsmina {
		public void pitsideChadacryst(short[] ural_yamassee) {
			DorcatheriumGoodwilly postglacial_metaluminate = new DorcatheriumGoodwilly();
			postglacial_metaluminate.tinterFlaccidly(ural_yamassee);
		}
	}

	public static class DorcatheriumGoodwilly {
		public void tinterFlaccidly(short[] toyfulness_snortle) {
			CancellousPotichomania subcubical_cloudless = new CancellousPotichomania();
			subcubical_cloudless.toillessnessWoorali(toyfulness_snortle);
		}
	}

	public static class CancellousPotichomania {
		public void toillessnessWoorali(short[] crossbred_transthalamic) {
			SnowscapeSerfship prolocutor_druidic = new SnowscapeSerfship();
			prolocutor_druidic.supervisorySloganeer(crossbred_transthalamic);
		}
	}

	public static class SnowscapeSerfship {
		public void supervisorySloganeer(short[] preacquaint_unsettlement) {
			TransportalBreakfaster infrastigmatal_costal = new TransportalBreakfaster();
			infrastigmatal_costal
					.mashallahGammerstang(preacquaint_unsettlement);
		}
	}

	public static class TransportalBreakfaster {
		public void mashallahGammerstang(short[] bluestem_camphane) {
			ReconcileIchneumonidae eosinophile_astonied = new ReconcileIchneumonidae();
			eosinophile_astonied.toryfyPeeling(bluestem_camphane);
		}
	}

	public static class ReconcileIchneumonidae {
		public void toryfyPeeling(short[] voider_bribegiver) {
			RareripePhyllorhinine epeirogenesis_unhurriedness = new RareripePhyllorhinine();
			epeirogenesis_unhurriedness.nascencyCrazyweed(voider_bribegiver);
		}
	}

	public static class RareripePhyllorhinine {
		public void nascencyCrazyweed(short[] reproachful_isotropism) {
			NonlixiviatedUltradiscipline inport_subterrene = new NonlixiviatedUltradiscipline();
			inport_subterrene.puzzleationTreator(reproachful_isotropism);
		}
	}

	public static class NonlixiviatedUltradiscipline {
		public void puzzleationTreator(short[] coroplasta_passamaquoddy) {
			StrongyloidosisInsectivore molluscoid_valse = new StrongyloidosisInsectivore();
			molluscoid_valse.nostologyOvercoy(coroplasta_passamaquoddy);
		}
	}

	public static class StrongyloidosisInsectivore {
		public void nostologyOvercoy(short[] bromcresol_radiographic) {
			EthylidyneAposepalous tripod_kallitype = new EthylidyneAposepalous();
			tripod_kallitype.kennebunkerJervine(bromcresol_radiographic);
		}
	}

	public static class EthylidyneAposepalous {
		public void kennebunkerJervine(short[] saki_honeymoonshine) {
			LupiformOphiuran amphistyly_subterfluous = new LupiformOphiuran();
			amphistyly_subterfluous.bolagMotograph(saki_honeymoonshine);
		}
	}

	public static class LupiformOphiuran {
		public void bolagMotograph(short[] guaraunan_kerry) {
			SociologizerSoutheastwardly grease_pothead = new SociologizerSoutheastwardly();
			grease_pothead.libateSplenitis(guaraunan_kerry);
		}
	}

	public static class SociologizerSoutheastwardly {
		public void libateSplenitis(short[] anointment_azafrin) {
			MagnitudinousExcentral besetment_organize = new MagnitudinousExcentral();
			besetment_organize.insurgentPhotocompose(anointment_azafrin);
		}
	}

	public static class MagnitudinousExcentral {
		public void insurgentPhotocompose(short[] nonconformance_emetically) {
			HyperphoricGastrula sifted_hydrophobia = new HyperphoricGastrula();
			sifted_hydrophobia.distichlisShover(nonconformance_emetically);
		}
	}

	public static class HyperphoricGastrula {
		public void distichlisShover(short[] gadbush_uncontrite) {
			BedesmanUndershrubby electralize_bedcord = new BedesmanUndershrubby();
			electralize_bedcord.scarlatinalVelvetry(gadbush_uncontrite);
		}
	}

	public static class BedesmanUndershrubby {
		public void scarlatinalVelvetry(short[] nudish_cheki) {
			ThreatproofCognoscibility nyctalopic_pyrenean = new ThreatproofCognoscibility();
			nyctalopic_pyrenean.curvesomenessGell(nudish_cheki);
		}
	}

	public static class ThreatproofCognoscibility {
		public void curvesomenessGell(short[] confabulation_echoize) {
			BushcraftCeliocyesis inorganization_incessancy = new BushcraftCeliocyesis();
			inorganization_incessancy.midrangeEmulsin(confabulation_echoize);
		}
	}

	public static class BushcraftCeliocyesis {
		public void midrangeEmulsin(short[] rangework_pillbox) {
			NonupholsteredGossiping gradienter_doggerelizer = new NonupholsteredGossiping();
			gradienter_doggerelizer.enhungerMozambique(rangework_pillbox);
		}
	}

	public static class NonupholsteredGossiping {
		public void enhungerMozambique(short[] nasolabial_sawbones) {
			DuchessWaiterdom splaymouthed_kummel = new DuchessWaiterdom();
			splaymouthed_kummel.lunuletUndersteward(nasolabial_sawbones);
		}
	}

	public static class DuchessWaiterdom {
		public void lunuletUndersteward(short[] swiveleyed_unprofusely) {
			OphiophobeResketch madeline_darwinize = new OphiophobeResketch();
			madeline_darwinize.hibbinBerenice(swiveleyed_unprofusely);
		}
	}

	public static class OphiophobeResketch {
		public void hibbinBerenice(short[] galenist_succedanea) {
			UnwettableCasklike figuratively_baft = new UnwettableCasklike();
			figuratively_baft.aconeSyllidae(galenist_succedanea);
		}
	}

	public static class UnwettableCasklike {
		public void aconeSyllidae(short[] gandergoose_permanently) {
			MorphologistEffusive platurous_trichophytosis = new MorphologistEffusive();
			platurous_trichophytosis
					.unsneckSatellitoid(gandergoose_permanently);
		}
	}

	public static class MorphologistEffusive {
		public void unsneckSatellitoid(short[] untutoredly_overdaintily) {
			AllabutaUnmindful heavenize_underexposure = new AllabutaUnmindful();
			heavenize_underexposure.avidConfessable(untutoredly_overdaintily);
		}
	}

	public static class AllabutaUnmindful {
		public void avidConfessable(short[] rhodophyta_hyperemic) {
			AleakHighbred swartness_khutuktu = new AleakHighbred();
			swartness_khutuktu.sellarEmery(rhodophyta_hyperemic);
		}
	}

	public static class AleakHighbred {
		public void sellarEmery(short[] algorab_motomagnetic) {
			EmpaperOverlaid dismark_pachyotia = new EmpaperOverlaid();
			dismark_pachyotia.electromericRopelayer(algorab_motomagnetic);
		}
	}

	public static class EmpaperOverlaid {
		public void electromericRopelayer(short[] staw_archpresbytery) {
			ValuedUndashed incompliance_spermatolytic = new ValuedUndashed();
			incompliance_spermatolytic
					.sheepherdingEgocentric(staw_archpresbytery);
		}
	}

	public static class ValuedUndashed {
		public void sheepherdingEgocentric(short[] mangue_flophouse) {
			OenomausZalophus inurbanely_retial = new OenomausZalophus();
			inurbanely_retial.anisocotylyHydatogenesis(mangue_flophouse);
		}
	}

	public static class OenomausZalophus {
		public void anisocotylyHydatogenesis(short[] counterpotent_nonprojection) {
			IntuitionalMamilius noncapillary_nidge = new IntuitionalMamilius();
			noncapillary_nidge
					.overeasyLycanthropist(counterpotent_nonprojection);
		}
	}

	public static class IntuitionalMamilius {
		public void overeasyLycanthropist(short[] regulate_furbelow) {
			ForekingPseudoheroic slidingly_kreutzer = new ForekingPseudoheroic();
			slidingly_kreutzer.blennotorrheaOverdearness(regulate_furbelow);
		}
	}

	public static class ForekingPseudoheroic {
		public void blennotorrheaOverdearness(short[] phytophysiology_expense) {
			SqueamousPyoctanin bruce_karrusel = new SqueamousPyoctanin();
			bruce_karrusel.renovaterSubcommended(phytophysiology_expense);
		}
	}

	public static class SqueamousPyoctanin {
		public void renovaterSubcommended(short[] voucheress_gnostic) {
			BlindfolderJarldom unutilizable_raffler = new BlindfolderJarldom();
			unutilizable_raffler.rigoristJangler(voucheress_gnostic);
		}
	}

	public static class BlindfolderJarldom {
		public void rigoristJangler(short[] toozoo_dioscuri) {
			CheererTalaing quarenden_polyarteritis = new CheererTalaing();
			quarenden_polyarteritis.synaxarionGyrogonite(toozoo_dioscuri);
		}
	}

	public static class CheererTalaing {
		public void synaxarionGyrogonite(short[] malaccident_moter) {
			PindyNerium rickettsial_reparative = new PindyNerium();
			rickettsial_reparative.sulfuranTruncage(malaccident_moter);
		}
	}

	public static class PindyNerium {
		public void sulfuranTruncage(short[] unflossy_foochow) {
			DanielicUnfeignedness enchondroma_bespice = new DanielicUnfeignedness();
			enchondroma_bespice.oxytylotateUnbrained(unflossy_foochow);
		}
	}

	public static class DanielicUnfeignedness {
		public void oxytylotateUnbrained(short[] babyolatry_roger) {
			ArakePalaearctic bacilligenic_lopper = new ArakePalaearctic();
			bacilligenic_lopper.cleanishSnout(babyolatry_roger);
		}
	}

	public static class ArakePalaearctic {
		public void cleanishSnout(short[] butting_advocatory) {
			AttunementTamelessness lustihead_repace = new AttunementTamelessness();
			lustihead_repace.requisitorialTeaboard(butting_advocatory);
		}
	}

	public static class AttunementTamelessness {
		public void requisitorialTeaboard(short[] unchecked_overkindness) {
			IhiNonextensional heteromorphosis_phyllous = new IhiNonextensional();
			heteromorphosis_phyllous
					.emolumentSpinoperipheral(unchecked_overkindness);
		}
	}

	public static class IhiNonextensional {
		public void emolumentSpinoperipheral(short[] cisrhenane_moonsick) {
			BeglamourBurled ebionite_narcotherapy = new BeglamourBurled();
			ebionite_narcotherapy.unmimickedNutlet(cisrhenane_moonsick);
		}
	}

	public static class BeglamourBurled {
		public void unmimickedNutlet(short[] heiress_racialism) {
			ResupposeImmaterialize gasterosteidae_undereat = new ResupposeImmaterialize();
			gasterosteidae_undereat.aleyrodidReduceableness(heiress_racialism);
		}
	}

	public static class ResupposeImmaterialize {
		public void aleyrodidReduceableness(short[] subfief_untremendous) {
			DrinkproofAntemedial skuse_pudder = new DrinkproofAntemedial();
			skuse_pudder.pookaunDynamitard(subfief_untremendous);
		}
	}

	public static class DrinkproofAntemedial {
		public void pookaunDynamitard(short[] cuttyhunk_hour) {
			EmbryophagousRopewalker undoubtedness_excoecaria = new EmbryophagousRopewalker();
			undoubtedness_excoecaria.paragenicExomis(cuttyhunk_hour);
		}
	}

	public static class EmbryophagousRopewalker {
		public void paragenicExomis(final short[] irrenderable_odontic) {
			Tracer.tracepointWeaknessStart("CWE190", "A",
					"Integer Overflow or Wraparound");
			short stonesoup_checked_value = irrenderable_odontic[4];
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			if (stonesoup_checked_value < 0) {
				stonesoup_checked_value = 0;
			}
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			short stonesoup_value = (short) (((short) (Short.MAX_VALUE) - (short) 500) + stonesoup_checked_value);
			Tracer.tracepointVariableShort("stonesoup_value", stonesoup_value);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			stonesoup_value++;
			String[] stonesoup_array = null;
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointMessage("Trying to allocate a string with size stonesoup_value.");
				stonesoup_array = new String[stonesoup_value];
				Tracer.tracepointBufferInfo("stonesoup_array",
						stonesoup_array.length,
						"Length of newly allocated stonesoup_array");
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				for (short index = 0; index < stonesoup_value; index++) {
					stonesoup_array[index] = Character.toString((char) index);
				}
				Tracer.tracepointMessage("Copy data into stonesoup_array.");
			} catch (java.lang.RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(DefaultMessage.hlithskjalfFurthest);
				throw e;
			}
			for (int counter = 0; counter < stonesoup_array.length; counter++) {
				DefaultMessage.hlithskjalfFurthest.printf("array[%d]=%s\n",
						counter, stonesoup_array[counter]);
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
	
}
