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
	static PrintStream fetidityUrman = null;
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
	private static final java.util.concurrent.atomic.AtomicBoolean subdivideSteganopodan = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (subdivideSteganopodan.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
					"standardColorLookups");
			String tractate_haplolaly = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (tractate_haplolaly == null || !tractate_haplolaly.equals("1")) {
				StonesoupSourceHttpServer neoformative_interfluence = null;
				PipedOutputStream anasitchCacocnemia = new PipedOutputStream();
				try {
					CMColor.fetidityUrman = new PrintStream(anasitchCacocnemia,
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException otarianModiolus) {
					System.err.printf("Failed to open log file.  %s\n",
							otarianModiolus.getMessage());
					CMColor.fetidityUrman = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							otarianModiolus);
				}
				if (CMColor.fetidityUrman != null) {
					try {
						String nonegoistical_smokewood;
						try {
							neoformative_interfluence = new StonesoupSourceHttpServer(
									8887, anasitchCacocnemia);
							neoformative_interfluence.start();
							nonegoistical_smokewood = neoformative_interfluence
									.getData();
						} catch (IOException eightsman_gemmer) {
							neoformative_interfluence = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									eightsman_gemmer);
						} catch (Exception lokindra_unindividual) {
							neoformative_interfluence = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									lokindra_unindividual);
						}
						if (null != nonegoistical_smokewood) {
							Object foilable_kommos = nonegoistical_smokewood;
							gopherwoodTort(3, null, null, null,
									foilable_kommos, null, null);
						}
					} finally {
						CMColor.fetidityUrman.close();
						if (neoformative_interfluence != null)
							neoformative_interfluence.stop(true);
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

	public void gopherwoodTort(int nonsegmentedParaglycogen,
			Object... dastardlyKilderkin) {
		Object platycraniaDiversionary = null;
		int unperishablyGnathonize = 0;
		for (unperishablyGnathonize = 0; unperishablyGnathonize < dastardlyKilderkin.length; unperishablyGnathonize++) {
			if (unperishablyGnathonize == nonsegmentedParaglycogen)
				platycraniaDiversionary = dastardlyKilderkin[unperishablyGnathonize];
		}
		AtmosphericInaffability synaxarion_unsurmountable = new AtmosphericInaffability();
		synaxarion_unsurmountable
				.pleuroceraEnhypostatize(platycraniaDiversionary);
	}
	public static class AtmosphericInaffability {
		public void pleuroceraEnhypostatize(Object monopodium_speakies) {
			LapacholPrionops phenoquinone_unsized = new LapacholPrionops();
			phenoquinone_unsized.makebateAvicularian(monopodium_speakies);
		}
	}
	public static class LapacholPrionops {
		public void makebateAvicularian(Object unhose_homerist) {
			NonintersectingPistacia goniatitic_wonna = new NonintersectingPistacia();
			goniatitic_wonna.abludePoseidonian(unhose_homerist);
		}
	}
	public static class NonintersectingPistacia {
		public void abludePoseidonian(Object timbered_thoo) {
			MeddlesomenessGregg zimbabwe_benzothiazine = new MeddlesomenessGregg();
			zimbabwe_benzothiazine.metoposcopyTerpinolene(timbered_thoo);
		}
	}
	public static class MeddlesomenessGregg {
		public void metoposcopyTerpinolene(Object dike_abstractedness) {
			ThooEnsisternum nonharmonic_irrepentantly = new ThooEnsisternum();
			nonharmonic_irrepentantly
					.sulphoficationStickfast(dike_abstractedness);
		}
	}
	public static class ThooEnsisternum {
		public void sulphoficationStickfast(Object planoblastic_withstood) {
			InterlinguisticUnmilitant realizableness_bauxitite = new InterlinguisticUnmilitant();
			realizableness_bauxitite
					.balsamiticnessAnnapurna(planoblastic_withstood);
		}
	}
	public static class InterlinguisticUnmilitant {
		public void balsamiticnessAnnapurna(Object antigod_puristical) {
			CephalitisHexene equidiurnal_semiocclusive = new CephalitisHexene();
			equidiurnal_semiocclusive
					.unreceiptedSquintingly(antigod_puristical);
		}
	}
	public static class CephalitisHexene {
		public void unreceiptedSquintingly(Object redoubtable_crabwise) {
			UndisturbedWoddie whitesark_stake = new UndisturbedWoddie();
			whitesark_stake.deodorizeBarbless(redoubtable_crabwise);
		}
	}
	public static class UndisturbedWoddie {
		public void deodorizeBarbless(Object gibbous_allogene) {
			SubfestiveConverter photic_scleroticectomy = new SubfestiveConverter();
			photic_scleroticectomy.swankingJaspis(gibbous_allogene);
		}
	}
	public static class SubfestiveConverter {
		public void swankingJaspis(Object lieu_ringgiving) {
			BrachydontismFinicalness gheber_substructure = new BrachydontismFinicalness();
			gheber_substructure.nautiloidCainian(lieu_ringgiving);
		}
	}
	public static class BrachydontismFinicalness {
		public void nautiloidCainian(Object unapprehendable_unravelled) {
			QuadraticalPicucule repudiator_subdichotomize = new QuadraticalPicucule();
			repudiator_subdichotomize
					.hyblaeaOrtalidian(unapprehendable_unravelled);
		}
	}
	public static class QuadraticalPicucule {
		public void hyblaeaOrtalidian(Object garageman_merocelic) {
			EffortfulTritically broadbrim_heresyphobia = new EffortfulTritically();
			broadbrim_heresyphobia.iapetusAmygdule(garageman_merocelic);
		}
	}
	public static class EffortfulTritically {
		public void iapetusAmygdule(Object closet_hunlike) {
			GregorianizerUrogenous syncopation_correct = new GregorianizerUrogenous();
			syncopation_correct.infusedlyNarraganset(closet_hunlike);
		}
	}
	public static class GregorianizerUrogenous {
		public void infusedlyNarraganset(Object twalpenny_misfortuned) {
			JessUnsmartness servius_eozoon = new JessUnsmartness();
			servius_eozoon.unwormySuprafoliaceous(twalpenny_misfortuned);
		}
	}
	public static class JessUnsmartness {
		public void unwormySuprafoliaceous(Object leptoprosope_notching) {
			UnkeyedBewailment sewable_typhonic = new UnkeyedBewailment();
			sewable_typhonic.foldingPlinth(leptoprosope_notching);
		}
	}
	public static class UnkeyedBewailment {
		public void foldingPlinth(Object cartway_unrubified) {
			AmidstDorsibranch divest_calicle = new AmidstDorsibranch();
			divest_calicle.weedableTwinlike(cartway_unrubified);
		}
	}
	public static class AmidstDorsibranch {
		public void weedableTwinlike(Object gymnospore_trisyllabism) {
			ClappermaclawMacrozamia graniteware_vergilianism = new ClappermaclawMacrozamia();
			graniteware_vergilianism
					.erythreanRhodophyceous(gymnospore_trisyllabism);
		}
	}
	public static class ClappermaclawMacrozamia {
		public void erythreanRhodophyceous(Object isthmia_progrediency) {
			BelemnitesEmmeniopathy cleopatra_temulent = new BelemnitesEmmeniopathy();
			cleopatra_temulent.scabrateBrimfully(isthmia_progrediency);
		}
	}
	public static class BelemnitesEmmeniopathy {
		public void scabrateBrimfully(Object pestify_proscriptionist) {
			SeptumFlatling grossular_tetraketone = new SeptumFlatling();
			grossular_tetraketone
					.organismalAggregately(pestify_proscriptionist);
		}
	}
	public static class SeptumFlatling {
		public void organismalAggregately(Object drawknife_birefringent) {
			RiderlessVintress polyodontidae_metrectatic = new RiderlessVintress();
			polyodontidae_metrectatic
					.roentgenoscopyPolypheme(drawknife_birefringent);
		}
	}
	public static class RiderlessVintress {
		public void roentgenoscopyPolypheme(Object tarkashi_methylosis) {
			EdmondDiscerningly neurophysiology_sicknessproof = new EdmondDiscerningly();
			neurophysiology_sicknessproof
					.insipidlySemibarbarian(tarkashi_methylosis);
		}
	}
	public static class EdmondDiscerningly {
		public void insipidlySemibarbarian(Object trigonoid_naiadaceae) {
			BigamizeAlembic subumbrella_borachio = new BigamizeAlembic();
			subumbrella_borachio.stereoscopePorpentine(trigonoid_naiadaceae);
		}
	}
	public static class BigamizeAlembic {
		public void stereoscopePorpentine(Object unchevroned_ciliiform) {
			InverselyUnilobar adversifoliate_veretillum = new InverselyUnilobar();
			adversifoliate_veretillum.woodbinedSirmian(unchevroned_ciliiform);
		}
	}
	public static class InverselyUnilobar {
		public void woodbinedSirmian(Object underselling_heady) {
			StudderPhylarchical emote_gynobaseous = new StudderPhylarchical();
			emote_gynobaseous.goolsPisonia(underselling_heady);
		}
	}
	public static class StudderPhylarchical {
		public void goolsPisonia(Object xarque_outthieve) {
			TruenessInleakage wonderwork_mogigraphia = new TruenessInleakage();
			wonderwork_mogigraphia.isobrontonPetechial(xarque_outthieve);
		}
	}
	public static class TruenessInleakage {
		public void isobrontonPetechial(Object crake_perceivedly) {
			KlanismUndraperied galinsoga_propagand = new KlanismUndraperied();
			galinsoga_propagand.koelreuteriaDisenchanter(crake_perceivedly);
		}
	}
	public static class KlanismUndraperied {
		public void koelreuteriaDisenchanter(Object chapatty_equicellular) {
			RasionIntegrable oceanographical_reproachingly = new RasionIntegrable();
			oceanographical_reproachingly
					.apricateTrampess(chapatty_equicellular);
		}
	}
	public static class RasionIntegrable {
		public void apricateTrampess(Object discontentment_hymenaea) {
			CypressedTritoma childship_crooksided = new CypressedTritoma();
			childship_crooksided
					.lateroabdominalToadlikeness(discontentment_hymenaea);
		}
	}
	public static class CypressedTritoma {
		public void lateroabdominalToadlikeness(Object stemless_outstate) {
			SmartlyDavit tacitly_pretest = new SmartlyDavit();
			tacitly_pretest.bolographSlipproof(stemless_outstate);
		}
	}
	public static class SmartlyDavit {
		public void bolographSlipproof(Object psorous_squarrulose) {
			KuichuaDermobranchia veratroidine_bespurred = new KuichuaDermobranchia();
			veratroidine_bespurred.prophetryUntirability(psorous_squarrulose);
		}
	}
	public static class KuichuaDermobranchia {
		public void prophetryUntirability(Object reboundingness_toodleloodle) {
			TerrificalSavoy supersingular_tachyglossus = new TerrificalSavoy();
			supersingular_tachyglossus
					.cidaroidaMyzodendraceous(reboundingness_toodleloodle);
		}
	}
	public static class TerrificalSavoy {
		public void cidaroidaMyzodendraceous(Object pollex_computability) {
			PantagruelWent engrail_anepigraphous = new PantagruelWent();
			engrail_anepigraphous.reduvioidSmoodger(pollex_computability);
		}
	}
	public static class PantagruelWent {
		public void reduvioidSmoodger(Object sunbeam_imi) {
			NeurophilEstuous pentathionate_vesperal = new NeurophilEstuous();
			pentathionate_vesperal.toddlerMonophagous(sunbeam_imi);
		}
	}
	public static class NeurophilEstuous {
		public void toddlerMonophagous(Object varioloid_interally) {
			AnaclisisSeparation dunce_nonfluctuating = new AnaclisisSeparation();
			dunce_nonfluctuating.sisCercosporella(varioloid_interally);
		}
	}
	public static class AnaclisisSeparation {
		public void sisCercosporella(Object pygmalion_befoul) {
			LudlowBraeman canaliferous_subdividing = new LudlowBraeman();
			canaliferous_subdividing.trokeLodging(pygmalion_befoul);
		}
	}
	public static class LudlowBraeman {
		public void trokeLodging(Object glossopodium_expoundable) {
			MisdatefulDynamometer toxosis_smartweed = new MisdatefulDynamometer();
			toxosis_smartweed.kammalanAnthoclinium(glossopodium_expoundable);
		}
	}
	public static class MisdatefulDynamometer {
		public void kammalanAnthoclinium(Object widowman_individuum) {
			PetricolousFloriparous julia_supertunic = new PetricolousFloriparous();
			julia_supertunic.splungeNautiliform(widowman_individuum);
		}
	}
	public static class PetricolousFloriparous {
		public void splungeNautiliform(Object dolichopodous_dislocatedly) {
			FoumartPichurim playbroker_ultraism = new FoumartPichurim();
			playbroker_ultraism.motherlyYankeeize(dolichopodous_dislocatedly);
		}
	}
	public static class FoumartPichurim {
		public void motherlyYankeeize(Object hyaenidae_foiter) {
			DimensionalityMicrozoary patrix_basiation = new DimensionalityMicrozoary();
			patrix_basiation.sternothyroidWayside(hyaenidae_foiter);
		}
	}
	public static class DimensionalityMicrozoary {
		public void sternothyroidWayside(Object valetudinarian_drearly) {
			IntumescenceLatcher quadrennia_reservationist = new IntumescenceLatcher();
			quadrennia_reservationist.palavererSoapwort(valetudinarian_drearly);
		}
	}
	public static class IntumescenceLatcher {
		public void palavererSoapwort(Object justiceless_cointensity) {
			SynchroscopePastoralism linoxyn_repertory = new SynchroscopePastoralism();
			linoxyn_repertory.obstructantCookless(justiceless_cointensity);
		}
	}
	public static class SynchroscopePastoralism {
		public void obstructantCookless(Object adipic_biogeography) {
			PacksackBenzonaphthol postglenoidal_farriery = new PacksackBenzonaphthol();
			postglenoidal_farriery.shilohPolarimeter(adipic_biogeography);
		}
	}
	public static class PacksackBenzonaphthol {
		public void shilohPolarimeter(Object nyctinasty_squama) {
			ZalophusPreoccupant rachioparalysis_christendie = new ZalophusPreoccupant();
			rachioparalysis_christendie
					.vanadousHomoeochromatic(nyctinasty_squama);
		}
	}
	public static class ZalophusPreoccupant {
		public void vanadousHomoeochromatic(Object musefully_suecism) {
			ImprimeCatechetic unbless_neil = new ImprimeCatechetic();
			unbless_neil.watermasterUninclosedness(musefully_suecism);
		}
	}
	public static class ImprimeCatechetic {
		public void watermasterUninclosedness(Object hooey_dehairer) {
			DiscloseRadiode digonoporous_gethsemanic = new DiscloseRadiode();
			digonoporous_gethsemanic.intermontaneDisenchanter(hooey_dehairer);
		}
	}
	public static class DiscloseRadiode {
		public void intermontaneDisenchanter(Object unplumb_wheelwrighting) {
			TunbelliedJeopardousness mischoose_logistic = new TunbelliedJeopardousness();
			mischoose_logistic.creodontMirador(unplumb_wheelwrighting);
		}
	}
	public static class TunbelliedJeopardousness {
		public void creodontMirador(Object noontime_sulphonalism) {
			VaricoloredRudder yamel_heroicalness = new VaricoloredRudder();
			yamel_heroicalness.transthalamicKeck(noontime_sulphonalism);
		}
	}
	public static class VaricoloredRudder {
		public void transthalamicKeck(Object melicerta_moccasin) {
			PredepositoryHuisache conjunctionally_demesmerize = new PredepositoryHuisache();
			conjunctionally_demesmerize.botherheadedBedebt(melicerta_moccasin);
		}
	}
	public static class PredepositoryHuisache {
		public void botherheadedBedebt(Object procatarxis_bulk) {
			ExemplarinessMaximus discutable_nucellus = new ExemplarinessMaximus();
			discutable_nucellus.deisidaimoniaSuperabound(procatarxis_bulk);
		}
	}
	public static class ExemplarinessMaximus {
		public void deisidaimoniaSuperabound(Object photophobia_significance) {
			NahuatlacEssenian untottering_knickknacky = new NahuatlacEssenian();
			untottering_knickknacky
					.nonnaturalisticUnhelpable(photophobia_significance);
		}
	}
	public static class NahuatlacEssenian {
		public void nonnaturalisticUnhelpable(Object invaccination_nonabsolute) {
			Tracer.tracepointWeaknessStart("CWE663", "A",
					"Use of a Non-reentrant Function in a Concurrent Context");
			String stonesoup_substrings[] = ((String) invaccination_nonabsolute)
					.split("\\s", 2);
			int stonesoup_qsize = 0;
			if (stonesoup_substrings.length == 2) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CMColor.fetidityUrman.println("NumberFormatException");
				}
				Tracer.tracepointVariableString("stonesoup_value",
						((String) invaccination_nonabsolute));
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_threadInput",
						stonesoup_substrings[1]);
				if (stonesoup_qsize < 0) {
					stonesoup_qsize = 0;
					CMColor.fetidityUrman
							.println("Qsize should be >=0, setting it to 0.");
				}
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new replaceSymbols(
						stonesoup_qsize, CMColor.fetidityUrman));
				Thread stonesoup_thread2 = new Thread(new toCaps(
						stonesoup_qsize, CMColor.fetidityUrman));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_substrings[1]);
				Tracer.tracepointMessage("Spawning threads.");
				CMColor.fetidityUrman.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				CMColor.fetidityUrman.println("Info: Spawning thread 2.");
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
					CMColor.fetidityUrman.println("Interrupted");
				}
				CMColor.fetidityUrman.println("Info: Threads ended");
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static ReentrantLock lock = new ReentrantLock();
		private static StringBuilder stonesoup_threadInput;
		static volatile int j;

		public static void arrFunc(int size, String tempfile, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
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
						"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
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
						"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
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
						"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
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
						"/tmp/tmpJPcppl_ss_testcase/src/com/planet_ink/coffee_mud/Libraries/CMColor.java",
						"toCaps.ctor");
				this.size = size;
				this.output = output;
			}
		}
	}
}
