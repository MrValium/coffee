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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	private static final int proclamator_kolobus = 2;
	static PrintStream tressureRubbly = null;
	private static final java.util.concurrent.atomic.AtomicBoolean crandallCoincidency = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (crandallCoincidency.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp0vZ1xt_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
					"modify");
			File polyoeciousPhytocidal = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!polyoeciousPhytocidal.getParentFile().exists()
					&& !polyoeciousPhytocidal.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					DefaultMessage.tressureRubbly = new PrintStream(
							new FileOutputStream(polyoeciousPhytocidal, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException retentivelyYoungly) {
					System.err.printf("Failed to open log file.  %s\n",
							retentivelyYoungly.getMessage());
					DefaultMessage.tressureRubbly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							retentivelyYoungly);
				} catch (FileNotFoundException tetratheiteEasefulness) {
					System.err.printf("Failed to open log file.  %s\n",
							tetratheiteEasefulness.getMessage());
					DefaultMessage.tressureRubbly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							tetratheiteEasefulness);
				}
				if (DefaultMessage.tressureRubbly != null) {
					try {
						String lithocarpus_unembattled = System
								.getenv("HALIEUTICS_CINEL");
						if (null != lithocarpus_unembattled) {
							Object uncementing_affectable = lithocarpus_unembattled;
							Object[] beveler_hematothorax = new Object[18];
							beveler_hematothorax[proclamator_kolobus] = uncementing_affectable;
							usucaptionUndeeded(beveler_hematothorax);
						}
					} finally {
						DefaultMessage.tressureRubbly.close();
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
	public void usucaptionUndeeded(Object[] uncharming_posteroexternal) {
		dianilidLegationary(uncharming_posteroexternal);
	}
	public void dianilidLegationary(Object[] aphotic_daredeviltry) {
		seraphicIritis(aphotic_daredeviltry);
	}
	public void seraphicIritis(Object[] electrophorus_parachroia) {
		regimentaledStarosta(electrophorus_parachroia);
	}
	public void regimentaledStarosta(Object[] gerated_invitress) {
		influentiallyWhop(gerated_invitress);
	}
	public void influentiallyWhop(Object[] motto_appendalgia) {
		ulcuscleNincompoophood(motto_appendalgia);
	}
	public void ulcuscleNincompoophood(Object[] suburbanize_chiroptera) {
		cheiropteraBittium(suburbanize_chiroptera);
	}
	public void cheiropteraBittium(Object[] backspread_hemicylindrical) {
		interinfluenceSapphic(backspread_hemicylindrical);
	}
	public void interinfluenceSapphic(Object[] semiopalescent_untenable) {
		shardedChoriocarcinoma(semiopalescent_untenable);
	}
	public void shardedChoriocarcinoma(Object[] suspendible_hyperpredator) {
		coadjutivePalatoalveolar(suspendible_hyperpredator);
	}
	public void coadjutivePalatoalveolar(Object[] odontogen_clicket) {
		solecisticallyDisoxygenate(odontogen_clicket);
	}
	public void solecisticallyDisoxygenate(Object[] parapraxia_prepenetration) {
		priapeanDoudle(parapraxia_prepenetration);
	}
	public void priapeanDoudle(Object[] tinkler_echinacea) {
		rockinglyStereoplasm(tinkler_echinacea);
	}
	public void rockinglyStereoplasm(Object[] diplomatical_uncertainty) {
		trophobiosisBuckskinned(diplomatical_uncertainty);
	}
	public void trophobiosisBuckskinned(Object[] tinwork_unwholesomely) {
		diagrammatizeMatricidal(tinwork_unwholesomely);
	}
	public void diagrammatizeMatricidal(Object[] decompress_undertakable) {
		panickyLench(decompress_undertakable);
	}
	public void panickyLench(Object[] forgie_mosshead) {
		autolyzateYouthsome(forgie_mosshead);
	}
	public void autolyzateYouthsome(Object[] emulsify_lover) {
		squallerDisengaged(emulsify_lover);
	}
	public void squallerDisengaged(Object[] lacunule_convocational) {
		wolfhoodFirmamental(lacunule_convocational);
	}
	public void wolfhoodFirmamental(Object[] submarine_calefactor) {
		vagrantnessPinwork(submarine_calefactor);
	}
	public void vagrantnessPinwork(Object[] topiarist_koine) {
		indulgentialEsquirearchy(topiarist_koine);
	}
	public void indulgentialEsquirearchy(Object[] firstly_siphonogamous) {
		pinnelMyelic(firstly_siphonogamous);
	}
	public void pinnelMyelic(Object[] unfussed_eunomian) {
		extracolumellaSprank(unfussed_eunomian);
	}
	public void extracolumellaSprank(Object[] rattlebag_manred) {
		ubiquitaryUngamelike(rattlebag_manred);
	}
	public void ubiquitaryUngamelike(Object[] opisthocomine_sparada) {
		unsanguineousHypericum(opisthocomine_sparada);
	}
	public void unsanguineousHypericum(Object[] beal_autochthon) {
		notoriouslyChrysophilite(beal_autochthon);
	}
	public void notoriouslyChrysophilite(Object[] untendered_hauberk) {
		phycomycetousGradienter(untendered_hauberk);
	}
	public void phycomycetousGradienter(Object[] resparkle_perispomenon) {
		hypertropiaIntercomplexity(resparkle_perispomenon);
	}
	public void hypertropiaIntercomplexity(Object[] benzosulphimide_tileseed) {
		chubbilyZeuzeridae(benzosulphimide_tileseed);
	}
	public void chubbilyZeuzeridae(Object[] coredeemer_anelectric) {
		fossilifyPachydactyly(coredeemer_anelectric);
	}
	public void fossilifyPachydactyly(Object[] slump_preimitative) {
		boundaryGapeworm(slump_preimitative);
	}
	public void boundaryGapeworm(Object[] perineovulvar_cricothyreoid) {
		gelandesprungWhether(perineovulvar_cricothyreoid);
	}
	public void gelandesprungWhether(Object[] discohexaster_anamnia) {
		stoodSyncline(discohexaster_anamnia);
	}
	public void stoodSyncline(Object[] spicy_stringing) {
		arduouslyQuintuplicate(spicy_stringing);
	}
	public void arduouslyQuintuplicate(Object[] rheophile_hocker) {
		synclinorianAlatern(rheophile_hocker);
	}
	public void synclinorianAlatern(Object[] outwaste_snuffliness) {
		zonarBedaze(outwaste_snuffliness);
	}
	public void zonarBedaze(Object[] outsay_dirempt) {
		psychiatryTolfraedic(outsay_dirempt);
	}
	public void psychiatryTolfraedic(Object[] african_moneybags) {
		glutitionUnawardable(african_moneybags);
	}
	public void glutitionUnawardable(Object[] clinospore_phrenopathic) {
		koulanSteelify(clinospore_phrenopathic);
	}
	public void koulanSteelify(Object[] bannister_piannet) {
		noseliteDaubreelite(bannister_piannet);
	}
	public void noseliteDaubreelite(Object[] tichorrhine_gastraeal) {
		acarotoxicUnassisted(tichorrhine_gastraeal);
	}
	public void acarotoxicUnassisted(Object[] listeria_sethian) {
		terebinthTinner(listeria_sethian);
	}
	public void terebinthTinner(Object[] dutiable_unhang) {
		cotyledonarPhototelephone(dutiable_unhang);
	}
	public void cotyledonarPhototelephone(Object[] repayable_shuhali) {
		lyonnaisImplosive(repayable_shuhali);
	}
	public void lyonnaisImplosive(Object[] squamulate_smectite) {
		pothuntTraditionalist(squamulate_smectite);
	}
	public void pothuntTraditionalist(Object[] crocoisite_zoosporangium) {
		donateToxic(crocoisite_zoosporangium);
	}
	public void donateToxic(Object[] undegenerating_endearing) {
		procompetitionChiropteran(undegenerating_endearing);
	}
	public void procompetitionChiropteran(Object[] zymosimeter_thackerayan) {
		carouserXanthoproteic(zymosimeter_thackerayan);
	}
	public void carouserXanthoproteic(Object[] propylon_unimpounded) {
		orthosilicateDysteleology(propylon_unimpounded);
	}
	public void orthosilicateDysteleology(Object[] pleiobar_shabrack) {
		stootHeptanaphthene(pleiobar_shabrack);
	}
	public void stootHeptanaphthene(Object[] supradural_ramaism){Tracer.tracepointWeaknessStart("CWE088","A","Argument Injection or Modification");Tracer.tracepointVariableString("value",((String)supradural_ramaism[proclamator_kolobus]));String stonesoup_proc_cmd="find . -iname ";Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");stonesoup_proc_cmd+=((String)supradural_ramaism[proclamator_kolobus]);Tracer.tracepointVariableString("stonesoup_proc_cmd",stonesoup_proc_cmd);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");boolean stonesoup_is_command_valid=true;for (int loc=0;loc < stonesoup_proc_cmd.length();loc++){if ((stonesoup_proc_cmd.charAt(loc) == ';') && stonesoup_proc_cmd.charAt(loc - 1) != '\\'){Tracer.tracepointMessage("Invalid command, shell escape detected.");DefaultMessage.tressureRubbly.println("Invalid command, shell escape detected.");stonesoup_is_command_valid=false;}}if (stonesoup_is_command_valid){java.lang.ProcessBuilder stonesoup_proc_builder=new java.lang.ProcessBuilder("bash","-c",stonesoup_proc_cmd);stonesoup_proc_builder.redirectErrorStream(true);StringBuilder builder=new StringBuilder();for (String stonesoup_command_part:stonesoup_proc_builder.command()){builder.append(stonesoup_command_part);builder.append(" ");}Tracer.tracepointVariableString("stonesoup_proc_builder.command()",builder.toString());java.lang.Process stonesoup_proc=null;try {Tracer.tracepointMessage("Executing command.");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_proc=stonesoup_proc_builder.start();Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());DefaultMessage.tressureRubbly.println("STONESOUP: Failed to open subprocess.");}if (stonesoup_proc != null){String stonesoup_proc_output_line=null;java.io.BufferedReader stonesoup_proc_reader=new java.io.BufferedReader(new java.io.InputStreamReader(stonesoup_proc.getInputStream()));try {Tracer.tracepointMessage("Reading stdout/stderr from subprocess.");while ((stonesoup_proc_output_line=stonesoup_proc_reader.readLine()) != null){DefaultMessage.tressureRubbly.println(stonesoup_proc_output_line);}} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());DefaultMessage.tressureRubbly.println("STONESOUP: Error reading subprocess output stream.");}try {Tracer.tracepointMessage("Waiting for subprocess to complete.");int stonesoup_exit_code=stonesoup_proc.waitFor();if (stonesoup_exit_code != 0){Tracer.tracepointError("Subprocess returned a non-zero exit code.");Tracer.tracepointVariableInt("stonesoup_exit_code",stonesoup_exit_code);DefaultMessage.tressureRubbly.printf("STONESOUP: Error in subprocess.	Return code: %d\n",stonesoup_exit_code);}} catch (java.lang.InterruptedException ie){Tracer.tracepointError(ie.getClass().getName() + ": "+ie.getMessage());DefaultMessage.tressureRubbly.println("STONESOUP: Error waiting for subprocess.");}}}Tracer.tracepointWeaknessEnd();}
	
}
