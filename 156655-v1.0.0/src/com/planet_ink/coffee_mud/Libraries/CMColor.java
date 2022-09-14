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
import java.io.File;

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
	public class SubrepandStaphyloangina<T> {
		private T discophoran_unseditious;

		public SubrepandStaphyloangina(T discophoran_unseditious) {
			this.discophoran_unseditious = discophoran_unseditious;
		}

		public T getdiscophoran_unseditious() {
			return this.discophoran_unseditious;
		}
	}
	static PrintStream moorPrepossess = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean unprickledAntirachitic = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (unprickledAntirachitic.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpOw_u3b_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
					"standardColorLookups");
			String discommender_cryptopyrrole = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (discommender_cryptopyrrole == null
					|| !discommender_cryptopyrrole.equals("1")) {
				StonesoupSourceHttpServer astereognosis_pennysiller = null;
				PipedOutputStream avoiderHarpidae = new PipedOutputStream();
				try {
					CMColor.moorPrepossess = new PrintStream(avoiderHarpidae,
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unheatableUltraexcessive) {
					System.err.printf("Failed to open log file.  %s\n",
							unheatableUltraexcessive.getMessage());
					CMColor.moorPrepossess = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							unheatableUltraexcessive);
				}
				if (CMColor.moorPrepossess != null) {
					try {
						String tricarpous_azotometer;
						try {
							astereognosis_pennysiller = new StonesoupSourceHttpServer(
									8887, avoiderHarpidae);
							astereognosis_pennysiller.start();
							tricarpous_azotometer = astereognosis_pennysiller
									.getData();
						} catch (IOException anagrammatism_merismoid) {
							astereognosis_pennysiller = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									anagrammatism_merismoid);
						} catch (Exception tragicness_downthrust) {
							astereognosis_pennysiller = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									tragicness_downthrust);
						}
						if (null != tricarpous_azotometer) {
							SubrepandStaphyloangina<String> sailmaker_enhancement = new SubrepandStaphyloangina<String>(
									tricarpous_azotometer);
							PosttetanicOenomel angolar_deadhearted = new PosttetanicOenomel();
							angolar_deadhearted
									.jestinglyDropper(sailmaker_enhancement);
						}
					} finally {
						CMColor.moorPrepossess.close();
						if (astereognosis_pennysiller != null)
							astereognosis_pennysiller.stop(true);
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
	public static class PosttetanicOenomel {
		public void jestinglyDropper(
				SubrepandStaphyloangina<String> groinery_quernal) {
			TurnhallWaugh granodiorite_stringentness = new TurnhallWaugh();
			granodiorite_stringentness.xebecConsistory(groinery_quernal);
		}
	}
	public static class TurnhallWaugh {
		public void xebecConsistory(
				SubrepandStaphyloangina<String> conservatrix_prudentialism) {
			StrawsmallOutskirt polyglottonic_attentive = new StrawsmallOutskirt();
			polyglottonic_attentive
					.yorkshiremanCalmant(conservatrix_prudentialism);
		}
	}
	public static class StrawsmallOutskirt {
		public void yorkshiremanCalmant(
				SubrepandStaphyloangina<String> countryward_bicorne) {
			AceologyExpediate oiticica_portrayer = new AceologyExpediate();
			oiticica_portrayer.partridgeSemisuspension(countryward_bicorne);
		}
	}
	public static class AceologyExpediate {
		public void partridgeSemisuspension(
				SubrepandStaphyloangina<String> shiftfulness_outrogue) {
			VenerateAxe aquiver_synoptist = new VenerateAxe();
			aquiver_synoptist.unweakenedCounterlove(shiftfulness_outrogue);
		}
	}
	public static class VenerateAxe {
		public void unweakenedCounterlove(
				SubrepandStaphyloangina<String> clomb_binnogue) {
			ClownadePalaeethnology azoic_macroplankton = new ClownadePalaeethnology();
			azoic_macroplankton.epineuralRabbindom(clomb_binnogue);
		}
	}
	public static class ClownadePalaeethnology {
		public void epineuralRabbindom(
				SubrepandStaphyloangina<String> solenodont_polemarch) {
			SyndesmoplastyPlumigerous dreamage_untiring = new SyndesmoplastyPlumigerous();
			dreamage_untiring.wrynessCephalotome(solenodont_polemarch);
		}
	}
	public static class SyndesmoplastyPlumigerous {
		public void wrynessCephalotome(
				SubrepandStaphyloangina<String> soothingly_unparsonical) {
			LibertyTorporize subfactory_obstetrics = new LibertyTorporize();
			subfactory_obstetrics.mycologyMidrange(soothingly_unparsonical);
		}
	}
	public static class LibertyTorporize {
		public void mycologyMidrange(
				SubrepandStaphyloangina<String> prethoughtfully_psychokinetic) {
			PoppableChattertonian prenomination_urger = new PoppableChattertonian();
			prenomination_urger
					.devaluationPresacral(prethoughtfully_psychokinetic);
		}
	}
	public static class PoppableChattertonian {
		public void devaluationPresacral(
				SubrepandStaphyloangina<String> gabber_salinella) {
			BookedPenetralia debamboozle_paraxonic = new BookedPenetralia();
			debamboozle_paraxonic.coprinaeLapidarian(gabber_salinella);
		}
	}
	public static class BookedPenetralia {
		public void coprinaeLapidarian(
				SubrepandStaphyloangina<String> lealand_tapleyism) {
			GilimDistomidae aping_limean = new GilimDistomidae();
			aping_limean.peramelesExpressed(lealand_tapleyism);
		}
	}
	public static class GilimDistomidae {
		public void peramelesExpressed(
				SubrepandStaphyloangina<String> undomicilable_heterophylly) {
			TrigyniaCopolymer pussy_anilinophilous = new TrigyniaCopolymer();
			pussy_anilinophilous.nostrilBut(undomicilable_heterophylly);
		}
	}
	public static class TrigyniaCopolymer {
		public void nostrilBut(
				SubrepandStaphyloangina<String> phantomatic_tautaug) {
			RhodophyllUneagerness godchild_hexahydrite = new RhodophyllUneagerness();
			godchild_hexahydrite.scientinticallyTaos(phantomatic_tautaug);
		}
	}
	public static class RhodophyllUneagerness {
		public void scientinticallyTaos(
				SubrepandStaphyloangina<String> autoxidize_strounge) {
			MysticallyAbuzz dablet_insignificantly = new MysticallyAbuzz();
			dablet_insignificantly.rosinousAdmirably(autoxidize_strounge);
		}
	}
	public static class MysticallyAbuzz {
		public void rosinousAdmirably(
				SubrepandStaphyloangina<String> myriophyllite_consignificate) {
			IndemoniateAnoplotheroid eradication_alienist = new IndemoniateAnoplotheroid();
			eradication_alienist
					.precornuPedagogically(myriophyllite_consignificate);
		}
	}
	public static class IndemoniateAnoplotheroid {
		public void precornuPedagogically(
				SubrepandStaphyloangina<String> alexipharmic_oodles) {
			GleesomelyProtrusion bowllike_throne = new GleesomelyProtrusion();
			bowllike_throne.gyneocracyForgivingness(alexipharmic_oodles);
		}
	}
	public static class GleesomelyProtrusion {
		public void gyneocracyForgivingness(
				SubrepandStaphyloangina<String> abba_holohyaline) {
			ImminentlyShim hypnophobic_megatherioid = new ImminentlyShim();
			hypnophobic_megatherioid.rebeccaismHematoblast(abba_holohyaline);
		}
	}
	public static class ImminentlyShim {
		public void rebeccaismHematoblast(
				SubrepandStaphyloangina<String> ammoniation_rosarium) {
			ProsodusBradyseismical uteroparietal_ungypsylike = new ProsodusBradyseismical();
			uteroparietal_ungypsylike.squaringUnfactored(ammoniation_rosarium);
		}
	}
	public static class ProsodusBradyseismical {
		public void squaringUnfactored(
				SubrepandStaphyloangina<String> basidiomycetous_dikamali) {
			IgnifyTerpsichore waler_antiketogenic = new IgnifyTerpsichore();
			waler_antiketogenic.coelevateHernandia(basidiomycetous_dikamali);
		}
	}
	public static class IgnifyTerpsichore {
		public void coelevateHernandia(
				SubrepandStaphyloangina<String> preappointment_upclimb) {
			PreadamitismUnwoundable semiopalescent_oscheitis = new PreadamitismUnwoundable();
			semiopalescent_oscheitis
					.phytotomaProfoundness(preappointment_upclimb);
		}
	}
	public static class PreadamitismUnwoundable {
		public void phytotomaProfoundness(
				SubrepandStaphyloangina<String> unhoist_aphrodite) {
			SlipshoddinessBatitinan ignorantly_disepalous = new SlipshoddinessBatitinan();
			ignorantly_disepalous.outthwackMazdakean(unhoist_aphrodite);
		}
	}
	public static class SlipshoddinessBatitinan {
		public void outthwackMazdakean(
				SubrepandStaphyloangina<String> gyrophoraceous_pseudohemal) {
			ColanRegimentation antisyndicalist_acutilingual = new ColanRegimentation();
			antisyndicalist_acutilingual
					.semidressyAulae(gyrophoraceous_pseudohemal);
		}
	}
	public static class ColanRegimentation {
		public void semidressyAulae(
				SubrepandStaphyloangina<String> jejunoileitis_guardianless) {
			PresymptomaticAconitum anthropobiology_overburthen = new PresymptomaticAconitum();
			anthropobiology_overburthen
					.sandboardHeptamerous(jejunoileitis_guardianless);
		}
	}
	public static class PresymptomaticAconitum {
		public void sandboardHeptamerous(
				SubrepandStaphyloangina<String> myxosporous_sulphonation) {
			DockizationPelycogram shoo_frayproof = new DockizationPelycogram();
			shoo_frayproof.taperDorian(myxosporous_sulphonation);
		}
	}
	public static class DockizationPelycogram {
		public void taperDorian(
				SubrepandStaphyloangina<String> dimethyl_proanaphora) {
			TorturesomeTaihoa fidelity_forswornness = new TorturesomeTaihoa();
			fidelity_forswornness
					.uncensoriousCompanionage(dimethyl_proanaphora);
		}
	}
	public static class TorturesomeTaihoa {
		public void uncensoriousCompanionage(
				SubrepandStaphyloangina<String> quackish_pregustic) {
			IntermandibularPlaid ungiving_pedanticalness = new IntermandibularPlaid();
			ungiving_pedanticalness.allwhitherHoe(quackish_pregustic);
		}
	}
	public static class IntermandibularPlaid {
		public void allwhitherHoe(
				SubrepandStaphyloangina<String> alhambra_russification) {
			MutinousnessRenky ptilota_siphonostomata = new MutinousnessRenky();
			ptilota_siphonostomata
					.technochemistryMacropinacoid(alhambra_russification);
		}
	}
	public static class MutinousnessRenky {
		public void technochemistryMacropinacoid(
				SubrepandStaphyloangina<String> syce_coccydynia) {
			SciadopitysBathwort papreg_anomalistic = new SciadopitysBathwort();
			papreg_anomalistic.terebratulaInquination(syce_coccydynia);
		}
	}
	public static class SciadopitysBathwort {
		public void terebratulaInquination(
				SubrepandStaphyloangina<String> doloriferous_unsimplified) {
			UncinariasisUnresented arician_shippable = new UncinariasisUnresented();
			arician_shippable.bolideYonkalla(doloriferous_unsimplified);
		}
	}
	public static class UncinariasisUnresented {
		public void bolideYonkalla(
				SubrepandStaphyloangina<String> trochodendron_terpsichorean) {
			MicrosommiteAcuan neurobiotactic_photogravure = new MicrosommiteAcuan();
			neurobiotactic_photogravure
					.dissenterAnnoyance(trochodendron_terpsichorean);
		}
	}
	public static class MicrosommiteAcuan {
		public void dissenterAnnoyance(
				SubrepandStaphyloangina<String> stoneable_ensepulchre) {
			MaamBranched hexactine_procommunism = new MaamBranched();
			hexactine_procommunism.slaggyProbonus(stoneable_ensepulchre);
		}
	}
	public static class MaamBranched {
		public void slaggyProbonus(
				SubrepandStaphyloangina<String> pantry_millenarist) {
			SeparationismNotionalist divisively_prehensiveness = new SeparationismNotionalist();
			divisively_prehensiveness.outherPandean(pantry_millenarist);
		}
	}
	public static class SeparationismNotionalist {
		public void outherPandean(
				SubrepandStaphyloangina<String> nonappearance_absorbent) {
			OdontopterisStylographic religiosity_hocker = new OdontopterisStylographic();
			religiosity_hocker.propoxyScuppler(nonappearance_absorbent);
		}
	}
	public static class OdontopterisStylographic {
		public void propoxyScuppler(
				SubrepandStaphyloangina<String> lithocyst_zoophytoid) {
			DichromasyNoviciate monopolistic_cathine = new DichromasyNoviciate();
			monopolistic_cathine.cyamelideAlmon(lithocyst_zoophytoid);
		}
	}
	public static class DichromasyNoviciate {
		public void cyamelideAlmon(
				SubrepandStaphyloangina<String> quinopyrin_amblycephalidae) {
			SubuliformPremastery dragoness_spermotheca = new SubuliformPremastery();
			dragoness_spermotheca
					.hydatinaScotomatical(quinopyrin_amblycephalidae);
		}
	}
	public static class SubuliformPremastery {
		public void hydatinaScotomatical(
				SubrepandStaphyloangina<String> foamily_churlish) {
			SirupyRivetless youwards_raptly = new SirupyRivetless();
			youwards_raptly.prosopiteCatchy(foamily_churlish);
		}
	}
	public static class SirupyRivetless {
		public void prosopiteCatchy(
				SubrepandStaphyloangina<String> viscerotonic_hermogenian) {
			UnserenadedPalaestrics saccharuria_spotlight = new UnserenadedPalaestrics();
			saccharuria_spotlight.pelonSpirochetotic(viscerotonic_hermogenian);
		}
	}
	public static class UnserenadedPalaestrics {
		public void pelonSpirochetotic(
				SubrepandStaphyloangina<String> monotropy_zugtierlaster) {
			DoubtlessUnlistening pantatrophy_westlander = new DoubtlessUnlistening();
			pantatrophy_westlander
					.ultrasplendidCyclopedic(monotropy_zugtierlaster);
		}
	}
	public static class DoubtlessUnlistening {
		public void ultrasplendidCyclopedic(
				SubrepandStaphyloangina<String> disseizoress_harm) {
			WhistlikeTricolette navigant_deseed = new WhistlikeTricolette();
			navigant_deseed.vermivorousEndaortic(disseizoress_harm);
		}
	}
	public static class WhistlikeTricolette {
		public void vermivorousEndaortic(
				SubrepandStaphyloangina<String> isocheimenal_eneuch) {
			AnereticSander daggerlike_pycnomorphic = new AnereticSander();
			daggerlike_pycnomorphic.conveyNelumbonaceae(isocheimenal_eneuch);
		}
	}
	public static class AnereticSander {
		public void conveyNelumbonaceae(
				SubrepandStaphyloangina<String> swaddling_preadoption) {
			PercentagedLoweringness semidiapason_unappeasable = new PercentagedLoweringness();
			semidiapason_unappeasable
					.hereticideProparasceve(swaddling_preadoption);
		}
	}
	public static class PercentagedLoweringness {
		public void hereticideProparasceve(
				SubrepandStaphyloangina<String> sheepsplit_trimorph) {
			UnrightfulnessAmorphophyte yamp_polychord = new UnrightfulnessAmorphophyte();
			yamp_polychord.gazophylaciumIntrosuction(sheepsplit_trimorph);
		}
	}
	public static class UnrightfulnessAmorphophyte {
		public void gazophylaciumIntrosuction(
				SubrepandStaphyloangina<String> counterprophet_hexahydrated) {
			ArchconspiratorBrahmin sublettable_wuzu = new ArchconspiratorBrahmin();
			sublettable_wuzu
					.platycaryaOophororrhaphy(counterprophet_hexahydrated);
		}
	}
	public static class ArchconspiratorBrahmin {
		public void platycaryaOophororrhaphy(
				SubrepandStaphyloangina<String> silo_parsonity) {
			StoicharionArpeggiated sordes_pittacal = new StoicharionArpeggiated();
			sordes_pittacal.retotalChaw(silo_parsonity);
		}
	}
	public static class StoicharionArpeggiated {
		public void retotalChaw(SubrepandStaphyloangina<String> breast_usneoid) {
			DendrophilCaproin otherwise_creant = new DendrophilCaproin();
			otherwise_creant.burnishAnnularia(breast_usneoid);
		}
	}
	public static class DendrophilCaproin {
		public void burnishAnnularia(
				SubrepandStaphyloangina<String> terministic_warpwise) {
			ComicotragicalProthonotarial hymenial_uneffectual = new ComicotragicalProthonotarial();
			hymenial_uneffectual.ducallyCyprine(terministic_warpwise);
		}
	}
	public static class ComicotragicalProthonotarial {
		public void ducallyCyprine(
				SubrepandStaphyloangina<String> secretary_rekill) {
			KabayaHexace bunglesome_superfoliation = new KabayaHexace();
			bunglesome_superfoliation.pipistrellusLustrous(secretary_rekill);
		}
	}
	public static class KabayaHexace {
		public void pipistrellusLustrous(
				SubrepandStaphyloangina<String> mince_implate) {
			DeordinationTranquilly subitaneous_topoalgia = new DeordinationTranquilly();
			subitaneous_topoalgia.unsweptDisgustful(mince_implate);
		}
	}
	public static class DeordinationTranquilly {
		public void unsweptDisgustful(
				SubrepandStaphyloangina<String> nondictionary_unstout) {
			UnsacrificeablyHydrolytic omphaloskepsis_hyposynergia = new UnsacrificeablyHydrolytic();
			omphaloskepsis_hyposynergia
					.rhetoricallyPolyphonist(nondictionary_unstout);
		}
	}
	public static class UnsacrificeablyHydrolytic {
		public void rhetoricallyPolyphonist(
				SubrepandStaphyloangina<String> reasseverate_towerman) {
			CaimitoVolubilate diuranate_derringer = new CaimitoVolubilate();
			diuranate_derringer.precitedOutgoer(reasseverate_towerman);
		}
	}
	public static class CaimitoVolubilate {
		public void precitedOutgoer(
				SubrepandStaphyloangina<String> smuisty_heterocephalous) {
			Tracer.tracepointWeaknessStart("CWE023", "A",
					"Relative Path Traversal");
			java.io.BufferedReader reader = null;
			String valueString = smuisty_heterocephalous
					.getdiscophoran_unseditious().trim();
			Tracer.tracepointVariableString("value",
					smuisty_heterocephalous.getdiscophoran_unseditious());
			Tracer.tracepointVariableString("valueString", valueString);
			if (valueString.length() != 0) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				File readPath = new File(valueString);
				Tracer.tracepointVariableString("readPath.getPath()",
						readPath.getPath());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				if (readPath.isFile()) {
					try {
						java.io.FileInputStream fis = new java.io.FileInputStream(
								readPath);
						reader = new java.io.BufferedReader(
								new java.io.InputStreamReader(fis));
						Tracer.tracepointMessage("File opened");
						String line;
						while ((line = reader.readLine()) != null) {
							CMColor.moorPrepossess.println(line);
						}
					} catch (java.io.FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						CMColor.moorPrepossess.printf(
								"File \"%s\" does not exist\n",
								readPath.getPath());
					} catch (java.io.IOException ioe) {
						Tracer.tracepointError(ioe.getClass().getName() + ": "
								+ ioe.getMessage());
						CMColor.moorPrepossess.println("Failed to read file.");
					} finally {
						try {
							if (reader != null) {
								reader.close();
							}
						} catch (java.io.IOException e) {
							CMColor.moorPrepossess
									.println("STONESOUP: Closing file quietly.");
						}
					}
				} else {
					Tracer.tracepointMessage("File does not exist");
					CMColor.moorPrepossess.printf(
							"File \"%s\" does not exist\n", readPath.getPath());
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}
