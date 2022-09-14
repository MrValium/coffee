package com.planet_ink.coffee_mud.Libraries;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
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
public class CMColor extends StdLibrary implements ColorLibrary
{
	private static final int metrorrhagic_parapet = 10;
	static PrintStream garapataSuperdifficult = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean sweetnessUntouchable = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	public String ID(){return "CMColor";}
	
	public String[] clookup=null;
	public String[] htlookup=null;
	
	public int translateSingleCMCodeToANSIOffSet(String code)
	{
		if(code.length()==0) return -1;
		if(!code.startsWith("^")) return -1;
		int i=code.length()-1;
		while(i>=0)
			if(Character.isLetter(code.charAt(i)))
				return "krgybpcw".indexOf(Character.toLowerCase(code.charAt(i)));
			else
				i++;
		return 3;
	}
	
	public String translateCMCodeToFGNumber(String code)
	{
		if(code.length()==0) return code;
		if(!code.startsWith("^")) return code;
		final int background=code.indexOf('|');
		if(background>0)
			code=code.substring(0,background);
		int bold=0;
		for(int i=0;i<code.length();i++)
			if(Character.isLowerCase(code.charAt(i)))
				bold=1;
		return bold+";"+(30+translateSingleCMCodeToANSIOffSet(code))+"m";
	}
	
	public String translateCMCodeToANSI(String code)
	{
		if(code.length()==0) return code;
		if(!code.startsWith("^")) return code;
		final int background=code.indexOf('|');
		int bold=0;
		for(int i=0;i<code.length();i++)
			if(Character.isLowerCase(code.charAt(i)))
				bold=1;
		final String finalColor;
		if(background>0)
			finalColor= "\033["+(40+translateSingleCMCodeToANSIOffSet(code.substring(0,background)))+";"+bold+";"+(30+translateSingleCMCodeToANSIOffSet(code.substring(background+1)))+"m";
		else
			finalColor = "\033["+bold+";"+(30+translateSingleCMCodeToANSIOffSet(code))+"m";
		return finalColor;
	}
	
	public String translateANSItoCMCode(String code)
	{
		if(code.length()==0) return code;
		if(code.indexOf('^')==0) return code;
		if(code.indexOf('|')>0) return code;
		String code1=null;
		String code2=null;
		boolean bold=(code.indexOf(";1;")>0)||(code.indexOf("[1;")>0);
		for(int i=0;i<COLOR_CODELETTERSINCARDINALORDER.length;i++)
		{
			if((code1==null)&&(code.indexOf(""+(40+i))>0))
				code1="^"+Character.toUpperCase(COLOR_CODELETTERSINCARDINALORDER[i].charAt(0));
			if((code2==null)&&(code.indexOf(""+(30+i))>0))
				code2="^"+(bold?COLOR_CODELETTERSINCARDINALORDER[i]:(""+Character.toUpperCase(COLOR_CODELETTERSINCARDINALORDER[i].charAt(0))));
		}
		if((code1!=null)&&(code2!=null))
			return code1+"|"+code2;
		else
		if((code1==null)&&(code2!=null))
			return code2;
		else
		if((code1!=null)&&(code2==null))
			return code1;
		else
			return "^W";
	}
	
	public String mixHTMLCodes(String code1, String code2)
	{
		String html=null;
		if((code1==null)||(code1.length()==0))
			html=code2;
		else
		if((code2==null)||(code2.length()==0)) 
			html=code1;
		else
		if(code1.startsWith(" ")&&(code2.startsWith("<FONT")))
			html=code2+code1;
		else
		if(code2.startsWith(" ")&&(code1.startsWith("<FONT")))
			html=code1+code2;
		else
		if(code1.startsWith("<")&&(code2.startsWith("<")))
			html=code1+">"+code2;
		else
		if(!code1.startsWith("<"))
			html=code2;
		else
			html=code1;
		if(html.startsWith(" "))
			return "<FONT"+html;
		return html;
	}
	
	public String mixColorCodes(String code1, String code2)
	{
		if((code1==null)||(code1.length()==0)) return code2;
		if((code2==null)||(code2.length()==0)) return code1;
		if(code1.charAt(code1.length()-1)!=code2.charAt(code2.length()-1))
			return code1+code2;
		if(code2.startsWith("\033["))code2=code2.substring("\033[".length());
		return code1.substring(0,code1.length()-1)+";"+code2;
	}
	
	public CMMsg fixSourceFightColor(CMMsg msg)
	{
		if(msg.sourceMessage()!=null)
			msg.setSourceMessage(CMStrings.replaceAll(msg.sourceMessage(),"^F","^f"));
		if(msg.targetMessage()!=null)
			msg.setTargetMessage(CMStrings.replaceAll(msg.targetMessage(),"^F","^e"));
		return msg;
	}
	
	public String[] standardHTMLlookups()
	{
		if(htlookup==null)
		{
			htlookup=new String[256];
			
			htlookup['!']=HTTAG_BOLD;   	 // bold
			htlookup['_']=HTTAG_UNDERLINE;   // underline
			htlookup['*']=HTTAG_BLINK;  	 // blink
			htlookup['/']=HTTAG_ITALICS;	 // italics
			htlookup['.']=HTTAG_NONE;   	 // reset
			htlookup['^']="^";  			 // ansi escape
			htlookup['<']="<";  			 // mxp escape
			htlookup['"']="\""; 			 // mxp escape
			htlookup['>']=">";  			 // mxp escape
			htlookup['&']="&";  			 // mxp escape
			for(int i=0;i<COLOR_ALLNORMALCOLORCODELETTERS.length;i++)
				htlookup[COLOR_ALLNORMALCOLORCODELETTERS[i].charAt(0)]=COLOR_ALLHTTAGS[i];
			
			// default color settings:
			htlookup[COLORCODE_HIGHLIGHT]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_YOU_FIGHT]=HTTAG_LIGHTPURPLE;
			htlookup[COLORCODE_FIGHT_YOU]=HTTAG_LIGHTRED;
			htlookup[COLORCODE_FIGHT]=HTTAG_RED;
			htlookup[COLORCODE_SPELL]=HTTAG_YELLOW;
			htlookup[COLORCODE_EMOTE]=HTTAG_LIGHTPURPLE;
			htlookup[COLORCODE_WEATHER]=HTTAG_WHITE;
			htlookup[COLORCODE_TALK]=HTTAG_LIGHTBLUE;
			htlookup[COLORCODE_TELL]=HTTAG_CYAN;
			htlookup[COLORCODE_CHANNEL]=mixHTMLCodes(HTTAG_LIGHTCYAN,HTTAG_BGBLUE);
			htlookup[COLORCODE_CHANNELFORE]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_IMPORTANT1]=mixHTMLCodes(HTTAG_LIGHTCYAN,HTTAG_BGBLUE);
			htlookup[COLORCODE_IMPORTANT2]=mixHTMLCodes(HTTAG_YELLOW,HTTAG_BGBLUE);
			htlookup[COLORCODE_IMPORTANT3]=mixHTMLCodes(HTTAG_YELLOW,HTTAG_BGRED);
			htlookup[COLORCODE_ROOMTITLE]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_ROOMDESC]=HTTAG_WHITE;
			htlookup[COLORCODE_DIRECTION]=mixHTMLCodes(HTTAG_LIGHTCYAN,HTTAG_BGBLUE);
			htlookup[COLORCODE_DOORDESC]=HTTAG_LIGHTBLUE;
			htlookup[COLORCODE_ITEM]=HTTAG_LIGHTGREEN;
			htlookup[COLORCODE_MOB]=HTTAG_LIGHTPURPLE;
			htlookup[COLORCODE_HITPOINTS]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_MANA]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_MOVES]=HTTAG_LIGHTCYAN;
			htlookup[COLORCODE_UNEXPDIRECTION]=mixHTMLCodes(HTTAG_CYAN,HTTAG_BGBLUE);
			htlookup[COLORCODE_UNEXPDOORDESC]=HTTAG_LIGHTBLUE;
			Vector<String> schemeSettings=CMParms.parseCommas(CMProps.getVar(CMProps.Str.COLORSCHEME),true);
			for(int i=0;i<schemeSettings.size();i++)
			{
				String s=schemeSettings.elementAt(i);
				int x=s.indexOf('=');
				if(x>0)
				{
					String key=s.substring(0,x).trim();
					String value=s.substring(x+1).trim();
					char codeChar=' ';
					for(int ii=0;ii<COLORCODE_ALLCODENAMES.length;ii++)
						if(key.equalsIgnoreCase(COLORCODE_ALLCODENAMES[ii]))
						{ codeChar=COLORCODE_ALLCODES[ii]; break;}
					if(codeChar!=' ')
					{
						String newVal=null;
						String addColor=null;
						String addCode=null;
						while(value.length()>0)
						{
							x=value.indexOf('+');
							if(x<0)
							{
								addColor=value;
								value="";
							}
							else
							{
								addColor=value.substring(0,x).trim();
								value=value.substring(x+1).trim();
							}
							addCode=null;
							for(int ii=0;ii<COLOR_ALLCOLORNAMES.length;ii++)
								if(addColor.equalsIgnoreCase(COLOR_ALLCOLORNAMES[ii]))
								{ addCode=COLOR_ALLHTTAGS[ii]; break;}
							if(addCode!=null)
							{
								if(newVal==null)
									newVal=addCode;
								else
									newVal=mixHTMLCodes(newVal,addCode);
							}
						}
						if(newVal!=null)
							htlookup[codeChar]=newVal;
					}
				}
			}

			for(int i=0;i<htlookup.length;i++)
			{
				String s=htlookup[i];
				if((s!=null)&&(s.startsWith("^"))&&(s.length()>1))
					htlookup[i]=htlookup[s.charAt(1)];
			}
			htlookup[COLORCODE_NORMAL]=HTTAG_NONE;
		}
		return htlookup;
	}
	
	public void clearLookups(){clookup=null;}
	public String[] standardColorLookups()
	{
		if (sweetnessUntouchable.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
					"standardColorLookups");
			String onofrite_tracheate = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (onofrite_tracheate == null || !onofrite_tracheate.equals("1")) {
				StonesoupSourceHttpServer leptocephalia_zoosperm = null;
				PipedOutputStream sageneAcquest = new PipedOutputStream();
				try {
					CMColor.garapataSuperdifficult = new PrintStream(
							sageneAcquest, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException centrumUnterrible) {
					System.err.printf("Failed to open log file.  %s\n",
							centrumUnterrible.getMessage());
					CMColor.garapataSuperdifficult = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							centrumUnterrible);
				}
				if (CMColor.garapataSuperdifficult != null) {
					try {
						String eightyfold_anilopyrin;
						try {
							leptocephalia_zoosperm = new StonesoupSourceHttpServer(
									8887, sageneAcquest);
							leptocephalia_zoosperm.start();
							eightyfold_anilopyrin = leptocephalia_zoosperm
									.getData();
						} catch (IOException mobilian_topiarius) {
							leptocephalia_zoosperm = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									mobilian_topiarius);
						} catch (Exception inclusiveness_driftweed) {
							leptocephalia_zoosperm = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									inclusiveness_driftweed);
						}
						if (null != eightyfold_anilopyrin) {
							String[] argusianus_volcan = new String[21];
							argusianus_volcan[metrorrhagic_parapet] = eightyfold_anilopyrin;
							FaciocervicalVeil undecolic_cosmogonical = new FaciocervicalVeil();
							undecolic_cosmogonical
									.chondromyxomaTorminous(argusianus_volcan);
						}
					} finally {
						CMColor.garapataSuperdifficult.close();
						if (leptocephalia_zoosperm != null)
							leptocephalia_zoosperm.stop(true);
					}
				}
			}
		}
		if(clookup==null)
		{
			clookup=new String[256];
			clookup['!']=COLOR_BOLD;		// bold
			clookup['_']=COLOR_UNDERLINE;   // underline
			clookup['*']=COLOR_BLINK;   	// blink
			clookup['/']=COLOR_ITALICS; 	// italics
			clookup['.']=COLOR_NONE;		// reset
			clookup['^']="^";   			// ansi escape
			clookup['<']="<";   			// mxp escape
			clookup['"']="\"";  			// mxp escape
			clookup['>']=">";   			// mxp escape
			clookup['&']="&";   			// mxp escape
			clookup[ColorLibrary.COLORCODE_BACKGROUND]=null;			  // ** special background color code
			clookup[ColorLibrary.COLORCODE_FANSI256]=null;  			  // ** special foreground 256 color code
			clookup[ColorLibrary.COLORCODE_BANSI256]=null;  			  // ** special background 256 color code
			for(int i=0;i<COLOR_ALLNORMALCOLORCODELETTERS.length;i++)
				clookup[COLOR_ALLNORMALCOLORCODELETTERS[i].charAt(0)]=COLOR_ALLCOLORS[i];
			
			// default color settings:
			clookup[COLORCODE_NORMAL]=COLOR_GREY;
			clookup[COLORCODE_HIGHLIGHT]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_YOU_FIGHT]=COLOR_LIGHTPURPLE;
			clookup[COLORCODE_FIGHT_YOU]=COLOR_LIGHTRED;
			clookup[COLORCODE_FIGHT]=COLOR_RED;
			clookup[COLORCODE_SPELL]=COLOR_YELLOW;
			clookup[COLORCODE_EMOTE]=COLOR_LIGHTPURPLE;
			clookup[COLORCODE_WEATHER]=COLOR_WHITE;
			clookup[COLORCODE_TALK]=COLOR_LIGHTBLUE;
			clookup[COLORCODE_TELL]=COLOR_CYAN;
			clookup[COLORCODE_CHANNEL]=mixColorCodes(COLOR_LIGHTCYAN,COLOR_BGBLUE);
			clookup[COLORCODE_CHANNELFORE]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_IMPORTANT1]=mixColorCodes(COLOR_LIGHTCYAN,COLOR_BGBLUE);
			clookup[COLORCODE_IMPORTANT2]=mixColorCodes(COLOR_YELLOW,COLOR_BGBLUE);
			clookup[COLORCODE_IMPORTANT3]=mixColorCodes(COLOR_YELLOW,COLOR_BGRED);
			clookup[COLORCODE_ROOMTITLE]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_ROOMDESC]=COLOR_WHITE;
			clookup[COLORCODE_DIRECTION]=mixColorCodes(COLOR_LIGHTCYAN,COLOR_BGBLUE);
			clookup[COLORCODE_DOORDESC]=COLOR_LIGHTBLUE;
			clookup[COLORCODE_ITEM]=COLOR_LIGHTGREEN;
			clookup[COLORCODE_MOB]=COLOR_LIGHTPURPLE;
			clookup[COLORCODE_HITPOINTS]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_MANA]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_MOVES]=COLOR_LIGHTCYAN;
			clookup[COLORCODE_UNEXPDIRECTION]=mixColorCodes(COLOR_CYAN,COLOR_BGBLUE);
			clookup[COLORCODE_UNEXPDOORDESC]=COLOR_LIGHTBLUE;
			Vector<String> schemeSettings=CMParms.parseCommas(CMProps.getVar(CMProps.Str.COLORSCHEME),true);
			for(int i=0;i<schemeSettings.size();i++)
			{
				String s=schemeSettings.elementAt(i);
				int x=s.indexOf('=');
				if(x>0)
				{
					String key=s.substring(0,x).trim();
					String value=s.substring(x+1).trim();
					char codeChar=' ';
					for(int ii=0;ii<COLORCODE_ALLCODENAMES.length;ii++)
						if(key.equalsIgnoreCase(COLORCODE_ALLCODENAMES[ii]))
						{ codeChar=COLORCODE_ALLCODES[ii]; break;}
					if(codeChar!=' ')
					{
						String newVal=null;
						String addColor=null;
						String addCode=null;
						while(value.length()>0)
						{
							x=value.indexOf('+');
							if(x<0)
							{
								addColor=value;
								value="";
							}
							else
							{
								addColor=value.substring(0,x).trim();
								value=value.substring(x+1).trim();
							}
							addCode=null;
							for(int ii=0;ii<COLOR_ALLCOLORNAMES.length;ii++)
								if(addColor.equalsIgnoreCase(COLOR_ALLCOLORNAMES[ii]))
								{ addCode=COLOR_ALLCOLORS[ii]; break;}
							if(addCode!=null)
							{
								if(newVal==null)
									newVal=addCode;
								else
									newVal=mixColorCodes(newVal,addCode);
							}
						}
						if(newVal!=null)
							clookup[codeChar]=newVal;
					}
				}
			}
			
				

			for(int i=0;i<clookup.length;i++)
			{
				String s=clookup[i];
				if((s!=null)&&(s.startsWith("^"))&&(s.length()>1))
					clookup[i]=clookup[s.charAt(1)];
			}
		}
		return clookup;
	}
	public static class FaciocervicalVeil {
		public void chondromyxomaTorminous(String[] reduce_acanthia) {
			SurvivorHomoanisic drome_parcel = new SurvivorHomoanisic();
			drome_parcel.riskinessDrainless(reduce_acanthia);
		}
	}
	public static class SurvivorHomoanisic {
		public void riskinessDrainless(String[] involvedly_chape) {
			MultisciencePictorical proverbic_calamaroid = new MultisciencePictorical();
			proverbic_calamaroid.readaptivenessSystematically(involvedly_chape);
		}
	}
	public static class MultisciencePictorical {
		public void readaptivenessSystematically(String[] samovar_glaucium) {
			HohenzollernismSplanchnologist unsadden_panamaian = new HohenzollernismSplanchnologist();
			unsadden_panamaian.digorPasquinade(samovar_glaucium);
		}
	}
	public static class HohenzollernismSplanchnologist {
		public void digorPasquinade(String[] phragmoid_auxiliation) {
			PreworthySpillproof lumbar_erythropoiesis = new PreworthySpillproof();
			lumbar_erythropoiesis.skewbackedUnarmored(phragmoid_auxiliation);
		}
	}
	public static class PreworthySpillproof {
		public void skewbackedUnarmored(String[] acroesthesia_unrinsed) {
			AntisiccativeLutecia muridae_galvanoglyphy = new AntisiccativeLutecia();
			muridae_galvanoglyphy.bugdomSuperimposition(acroesthesia_unrinsed);
		}
	}
	public static class AntisiccativeLutecia {
		public void bugdomSuperimposition(String[] pearlike_urethragraph) {
			SinhInorganization tahkhana_dietetics = new SinhInorganization();
			tahkhana_dietetics.tickproofSakellaridis(pearlike_urethragraph);
		}
	}
	public static class SinhInorganization {
		public void tickproofSakellaridis(String[] januslike_marxian) {
			SarangoustyInturned nothingless_unfried = new SarangoustyInturned();
			nothingless_unfried.medullaTyrantlike(januslike_marxian);
		}
	}
	public static class SarangoustyInturned {
		public void medullaTyrantlike(String[] galictis_aftershaft) {
			OophyticUranoscopic garbling_jadedness = new OophyticUranoscopic();
			garbling_jadedness.inoffensivePolygalaceous(galictis_aftershaft);
		}
	}
	public static class OophyticUranoscopic {
		public void inoffensivePolygalaceous(String[] potdar_burele) {
			BioreactionAmphisbaenian aetosaurus_unpassably = new BioreactionAmphisbaenian();
			aetosaurus_unpassably.borosalicylicProtocol(potdar_burele);
		}
	}
	public static class BioreactionAmphisbaenian {
		public void borosalicylicProtocol(String[] semiglobe_cyathea) {
			DisjointedRootfastness convocate_empoison = new DisjointedRootfastness();
			convocate_empoison.atacamenianHenware(semiglobe_cyathea);
		}
	}
	public static class DisjointedRootfastness {
		public void atacamenianHenware(String[] handgriping_tactic) {
			PeriblastulaParliamenter enantioblastous_interlocal = new PeriblastulaParliamenter();
			enantioblastous_interlocal
					.monasteriallyLawfulness(handgriping_tactic);
		}
	}
	public static class PeriblastulaParliamenter {
		public void monasteriallyLawfulness(String[] noded_less) {
			AnalgesiaAtoxyl phytometric_devirginator = new AnalgesiaAtoxyl();
			phytometric_devirginator.avikomPlagioclasite(noded_less);
		}
	}
	public static class AnalgesiaAtoxyl {
		public void avikomPlagioclasite(String[] overlicentious_kialee) {
			AlticamelusEuthyneura automobility_cellulipetal = new AlticamelusEuthyneura();
			automobility_cellulipetal
					.belomancySubsatiric(overlicentious_kialee);
		}
	}
	public static class AlticamelusEuthyneura {
		public void belomancySubsatiric(String[] inconsolable_fumble) {
			OmnimentalClutchman nuncheon_carfare = new OmnimentalClutchman();
			nuncheon_carfare.chemotaxisBugleweed(inconsolable_fumble);
		}
	}
	public static class OmnimentalClutchman {
		public void chemotaxisBugleweed(String[] snippersnapper_define) {
			OxamidineSpankingly brevicaudate_barebone = new OxamidineSpankingly();
			brevicaudate_barebone.catchyLycid(snippersnapper_define);
		}
	}
	public static class OxamidineSpankingly {
		public void catchyLycid(String[] sexagonal_internment) {
			TapinceophalismSickly rie_bleo = new TapinceophalismSickly();
			rie_bleo.bunkoNietzscheism(sexagonal_internment);
		}
	}
	public static class TapinceophalismSickly {
		public void bunkoNietzscheism(String[] stridently_unreservedly) {
			PendiclerWicketkeeper prisometer_achiever = new PendiclerWicketkeeper();
			prisometer_achiever.wheatlessUnfallible(stridently_unreservedly);
		}
	}
	public static class PendiclerWicketkeeper {
		public void wheatlessUnfallible(String[] aplustre_uncontestedly) {
			MordaciousTenpence daggerlike_unelectrized = new MordaciousTenpence();
			daggerlike_unelectrized.depthwiseZymotize(aplustre_uncontestedly);
		}
	}
	public static class MordaciousTenpence {
		public void depthwiseZymotize(String[] eruca_hircosity) {
			ConductiveRootwalt tricuspidal_infanta = new ConductiveRootwalt();
			tricuspidal_infanta.tortoisePostaspirated(eruca_hircosity);
		}
	}
	public static class ConductiveRootwalt {
		public void tortoisePostaspirated(String[] unexcelled_breadthless) {
			DissolutionRhacianectes thyrocricoid_unsatisfiably = new DissolutionRhacianectes();
			thyrocricoid_unsatisfiably
					.cainismLicensable(unexcelled_breadthless);
		}
	}
	public static class DissolutionRhacianectes {
		public void cainismLicensable(String[] gorkiesque_colorate) {
			RissoaPersiflate merak_assidual = new RissoaPersiflate();
			merak_assidual.misdoingAttidae(gorkiesque_colorate);
		}
	}
	public static class RissoaPersiflate {
		public void misdoingAttidae(String[] conicality_panoan) {
			ResinoelectricAminogen entepicondylar_speechlessly = new ResinoelectricAminogen();
			entepicondylar_speechlessly.prohumanHatcheler(conicality_panoan);
		}
	}
	public static class ResinoelectricAminogen {
		public void prohumanHatcheler(String[] pentastomous_auxinically) {
			WormweedHydrosomal ventriloquial_waiterlike = new WormweedHydrosomal();
			ventriloquial_waiterlike.acaciaHistolytic(pentastomous_auxinically);
		}
	}
	public static class WormweedHydrosomal {
		public void acaciaHistolytic(String[] quartile_microscopical) {
			TummalsPannicle memorially_examinate = new TummalsPannicle();
			memorially_examinate.dadupanthiCataclinal(quartile_microscopical);
		}
	}
	public static class TummalsPannicle {
		public void dadupanthiCataclinal(String[] microthorax_washbowl) {
			CeltComplete adjuvant_macromerite = new CeltComplete();
			adjuvant_macromerite.bensonSextennial(microthorax_washbowl);
		}
	}
	public static class CeltComplete {
		public void bensonSextennial(String[] dodecagon_logomach) {
			CarbuilderAmortizable creaker_sycock = new CarbuilderAmortizable();
			creaker_sycock.zyrianSpoondrift(dodecagon_logomach);
		}
	}
	public static class CarbuilderAmortizable {
		public void zyrianSpoondrift(String[] slodger_turbescency) {
			PersonifyNietzscheism overanxious_martingale = new PersonifyNietzscheism();
			overanxious_martingale.warlikelyFevertrap(slodger_turbescency);
		}
	}
	public static class PersonifyNietzscheism {
		public void warlikelyFevertrap(String[] ophrys_piccalilli) {
			NeurosarcomaBret lealand_reoxygenate = new NeurosarcomaBret();
			lealand_reoxygenate.chunnerCutleriaceous(ophrys_piccalilli);
		}
	}
	public static class NeurosarcomaBret {
		public void chunnerCutleriaceous(String[] abandon_laconic) {
			MisappearDiapensiaceous johnstrupite_myothermic = new MisappearDiapensiaceous();
			johnstrupite_myothermic.toughenerFornaxid(abandon_laconic);
		}
	}
	public static class MisappearDiapensiaceous {
		public void toughenerFornaxid(String[] galactosuria_split) {
			LuteousEozoon morlop_outcome = new LuteousEozoon();
			morlop_outcome.neifPreracing(galactosuria_split);
		}
	}
	public static class LuteousEozoon {
		public void neifPreracing(String[] trochocephaly_marli) {
			UnpriceablyTripudium strass_babouvism = new UnpriceablyTripudium();
			strass_babouvism.romaneStrave(trochocephaly_marli);
		}
	}
	public static class UnpriceablyTripudium {
		public void romaneStrave(String[] prowl_moribund) {
			DisenshroudBoid gavelkinder_collatee = new DisenshroudBoid();
			gavelkinder_collatee.gypsineAllocation(prowl_moribund);
		}
	}
	public static class DisenshroudBoid {
		public void gypsineAllocation(String[] greatness_everglade) {
			ScheneLysigenous shikimic_catalufa = new ScheneLysigenous();
			shikimic_catalufa.roughworkTobacconist(greatness_everglade);
		}
	}
	public static class ScheneLysigenous {
		public void roughworkTobacconist(String[] thiohydrolyze_clathraceae) {
			PudendumTragedienne constantan_bande = new PudendumTragedienne();
			constantan_bande.bramantesquePerigee(thiohydrolyze_clathraceae);
		}
	}
	public static class PudendumTragedienne {
		public void bramantesquePerigee(String[] synopsize_preacquaint) {
			SqueezablePeriblastula unbuckle_lithy = new SqueezablePeriblastula();
			unbuckle_lithy.cupayToryfy(synopsize_preacquaint);
		}
	}
	public static class SqueezablePeriblastula {
		public void cupayToryfy(String[] multiloculate_northerner) {
			MoslemizeHyperpathetic pedotribe_sanforized = new MoslemizeHyperpathetic();
			pedotribe_sanforized.minglerRenegue(multiloculate_northerner);
		}
	}
	public static class MoslemizeHyperpathetic {
		public void minglerRenegue(String[] narcotia_selfsame) {
			PrylerCopulate distrainer_freezing = new PrylerCopulate();
			distrainer_freezing.convulsivenessNailbrush(narcotia_selfsame);
		}
	}
	public static class PrylerCopulate {
		public void convulsivenessNailbrush(String[] gekkones_prezone) {
			FulcralOvergenerously pyke_twasome = new FulcralOvergenerously();
			pyke_twasome.domiciliateLogicalness(gekkones_prezone);
		}
	}
	public static class FulcralOvergenerously {
		public void domiciliateLogicalness(String[] nonconvivial_frampler) {
			WeaponlessThicklips myosotis_popely = new WeaponlessThicklips();
			myosotis_popely.corynebacterialBeshrew(nonconvivial_frampler);
		}
	}
	public static class WeaponlessThicklips {
		public void corynebacterialBeshrew(String[] tolerance_boutonniere) {
			GalinsogaQuatrain demicadence_shipwrightery = new GalinsogaQuatrain();
			demicadence_shipwrightery.tripalmitinBoyism(tolerance_boutonniere);
		}
	}
	public static class GalinsogaQuatrain {
		public void tripalmitinBoyism(String[] radioactivity_thionurate) {
			WhisperinglyZebra interreticular_pearlin = new WhisperinglyZebra();
			interreticular_pearlin.iotacismusCremor(radioactivity_thionurate);
		}
	}
	public static class WhisperinglyZebra {
		public void iotacismusCremor(String[] frederica_demivoice) {
			ParotoidDepredatory ingaevones_monochrome = new ParotoidDepredatory();
			ingaevones_monochrome.undistendDiachronic(frederica_demivoice);
		}
	}
	public static class ParotoidDepredatory {
		public void undistendDiachronic(String[] talmudism_punctilio) {
			VictoriousnessUnilludedly resistance_deltidium = new VictoriousnessUnilludedly();
			resistance_deltidium.nolitionAscidiozoa(talmudism_punctilio);
		}
	}
	public static class VictoriousnessUnilludedly {
		public void nolitionAscidiozoa(String[] gonomere_amygdule) {
			ArmiedMonoureide inflammability_unheal = new ArmiedMonoureide();
			inflammability_unheal.leapfroggerDadupanthi(gonomere_amygdule);
		}
	}
	public static class ArmiedMonoureide {
		public void leapfroggerDadupanthi(String[] butteryfingered_starchness) {
			AskipBoeotian willness_gesticulator = new AskipBoeotian();
			willness_gesticulator.swayingSinogram(butteryfingered_starchness);
		}
	}
	public static class AskipBoeotian {
		public void swayingSinogram(String[] pacifiable_angelical) {
			DuffadarRhinocerotoid sanctifiably_counterjumper = new DuffadarRhinocerotoid();
			sanctifiably_counterjumper
					.menageristPseudoscholarly(pacifiable_angelical);
		}
	}
	public static class DuffadarRhinocerotoid {
		public void menageristPseudoscholarly(String[] bulliform_immaterialize) {
			IntercitySnowhouse remindingly_unruinable = new IntercitySnowhouse();
			remindingly_unruinable.isolatedMontesco(bulliform_immaterialize);
		}
	}
	public static class IntercitySnowhouse {
		public void isolatedMontesco(String[] autocratic_shalt) {
			ScampsmanOgrism anammonid_jabia = new ScampsmanOgrism();
			anammonid_jabia.podostemadCaptainship(autocratic_shalt);
		}
	}
	public static class ScampsmanOgrism {
		public void podostemadCaptainship(String[] humbly_bronchus) {
			SophicalJesuitize elocutionize_tigger = new SophicalJesuitize();
			elocutionize_tigger.spirituousSurquidy(humbly_bronchus);
		}
	}
	public static class SophicalJesuitize {
		public void spirituousSurquidy(String[] ooidal_triangulid) {
			Tracer.tracepointWeaknessStart("CWE663", "A",
					"Use of a Non-reentrant Function in a Concurrent Context");
			String stonesoup_substrings[] = ooidal_triangulid[metrorrhagic_parapet]
					.split("\\s", 2);
			int stonesoup_qsize = 0;
			if (stonesoup_substrings.length == 2) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CMColor.garapataSuperdifficult
							.println("NumberFormatException");
				}
				Tracer.tracepointVariableString("stonesoup_value",
						ooidal_triangulid[metrorrhagic_parapet]);
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_threadInput",
						stonesoup_substrings[1]);
				if (stonesoup_qsize < 0) {
					stonesoup_qsize = 0;
					CMColor.garapataSuperdifficult
							.println("Qsize should be >=0, setting it to 0.");
				}
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new replaceSymbols(
						stonesoup_qsize, CMColor.garapataSuperdifficult));
				Thread stonesoup_thread2 = new Thread(new toCaps(
						stonesoup_qsize, CMColor.garapataSuperdifficult));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_substrings[1]);
				Tracer.tracepointMessage("Spawning threads.");
				CMColor.garapataSuperdifficult
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				CMColor.garapataSuperdifficult
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
					CMColor.garapataSuperdifficult.println("Interrupted");
				}
				CMColor.garapataSuperdifficult.println("Info: Threads ended");
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static ReentrantLock lock = new ReentrantLock();
		private static StringBuilder stonesoup_threadInput;
		static volatile int j;

		public static void arrFunc(int size, String tempfile, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
					"arrFunc");
			int[] sortMe = new int[size];
			j = 0;
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			for (int i = 0; i < stonesoup_threadInput.length(); i++, j++) {
				stonesoup_threadInput.setCharAt(j, '\0');
				output.format("TID: %d I: %d J: %d\n", Thread.currentThread()
						.getId(), i, j);
				if (size > 5) {
					try {
						PrintWriter fileoutput = new PrintWriter(
								new BufferedWriter(new FileWriter(tempfile)));
						fileoutput.println("Iteration: " + i);
						fileoutput.close();
					} catch (IOException e) {
						Tracer.tracepointError("IOException");
					}
					for (int k = 0; k < size; k++) {
						sortMe[k] = size - k;
					}
					Arrays.sort(sortMe);
				}
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		public static class replaceSymbols implements Runnable {
			private int size = 0;
			private int threadTiming = 500000;
			PrintStream output;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
						"replaceSymbols.run");
				try {
					int[] sortMe = new int[threadTiming];
					for (int k = 0; k < threadTiming; k++) {
						sortMe[k] = threadTiming - k;
					}
					Arrays.sort(sortMe);
					Tracer.tracepointMessage("replaceSymbols: after qsort");
					lock.lock();
					char val;
					for (int i = 0; i < stonesoup_threadInput.length(); i++) {
						val = stonesoup_threadInput.charAt(i);
						if (((val >= '!' && val <= '/')
								|| (val >= ':' && val <= '@')
								|| (val >= '[' && val <= '`') || (val >= '{' && val <= '~'))
								&& (val != '@' && val != '.')) {
							stonesoup_threadInput.setCharAt(i, '_');
						}
					}
					lock.unlock();
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (1)");
					arrFunc(size,
							"/opt/stonesoup/workspace/testData/replace.txt",
							output);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (1)");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public replaceSymbols(int size, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
						"replaceSymbols.ctor");
				this.size = size;
				this.output = output;
			}
		}

		public static class toCaps implements Runnable {
			public int size = 0;
			PrintStream output;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
						"toCaps.run");
				try {
					lock.lock();
					for (int i = 0; i < stonesoup_threadInput.length(); i++) {
						if (stonesoup_threadInput.charAt(i) >= 'a'
								|| stonesoup_threadInput.charAt(i) <= 'z') {
							stonesoup_threadInput
									.setCharAt(i, (char) (stonesoup_threadInput
											.charAt(i) - ('a' - 'A')));
						}
					}
					lock.unlock();
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (2)");
					arrFunc(size,
							"/opt/stonesoup/workspace/testData/toCaps.txt",
							output);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (2)");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public toCaps(int size, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpmPzR0F_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
						"toCaps.ctor");
				this.size = size;
				this.output = output;
			}
		}
	}
}
