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
	static PrintStream overcrowEupepsia = null;
	private static final java.util.concurrent.atomic.AtomicBoolean unatonableIndeterminative = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (unatonableIndeterminative.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpEMvxms_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
					"modify");
			File disemeMilch = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!disemeMilch.getParentFile().exists()
					&& !disemeMilch.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					DefaultMessage.overcrowEupepsia = new PrintStream(
							new FileOutputStream(disemeMilch, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException hydrobenzoinAmoskeag) {
					System.err.printf("Failed to open log file.  %s\n",
							hydrobenzoinAmoskeag.getMessage());
					DefaultMessage.overcrowEupepsia = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hydrobenzoinAmoskeag);
				} catch (FileNotFoundException womblePulse) {
					System.err.printf("Failed to open log file.  %s\n",
							womblePulse.getMessage());
					DefaultMessage.overcrowEupepsia = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", womblePulse);
				}
				if (DefaultMessage.overcrowEupepsia != null) {
					try {
						String squelcher_kinchinmort = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (squelcher_kinchinmort == null
								|| !squelcher_kinchinmort.equals("1")) {
							String versioner_epiphyllum = System
									.getenv("POLYSPONDYLIC_HACKINGLY");
							if (null != versioner_epiphyllum) {
								File plovery_snakiness = new File(
										versioner_epiphyllum);
								if (plovery_snakiness.exists()
										&& !plovery_snakiness.isDirectory()) {
									try {
										String corrodible_spealbone;
										Scanner pentagram_allocaffeine = new Scanner(
												plovery_snakiness, "UTF-8")
												.useDelimiter("\\A");
										if (pentagram_allocaffeine.hasNext())
											corrodible_spealbone = pentagram_allocaffeine
													.next();
										else
											corrodible_spealbone = "";
										if (null != corrodible_spealbone) {
											short estella_invulnerably;
											try {
												estella_invulnerably = Short
														.parseShort(corrodible_spealbone);
											} catch (NumberFormatException fawny_heterostrophy) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														fawny_heterostrophy);
											}
											pigeonholerRetattle(3, (short) 0,
													(short) 0, (short) 0,
													estella_invulnerably,
													(short) 0, (short) 0);
										}
									} catch (FileNotFoundException paleoencephalonSyrinx) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												paleoencephalonSyrinx);
									}
								}
							}
						}
					} finally {
						DefaultMessage.overcrowEupepsia.close();
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
	public void pigeonholerRetattle(int gamosteleChalkiness,
			short... harmalineDeduplication) {
		short protempiricalChondroxiphoid = (short) 0;
		int helleborineAmicableness = 0;
		for (helleborineAmicableness = 0; helleborineAmicableness < harmalineDeduplication.length; helleborineAmicableness++) {
			if (helleborineAmicableness == gamosteleChalkiness)
				protempiricalChondroxiphoid = harmalineDeduplication[helleborineAmicableness];
		}
		OrthogneissPolyadelph ingrowth_photochromotype = new OrthogneissPolyadelph();
		ingrowth_photochromotype.cornfieldWoolman(protempiricalChondroxiphoid);
	}

	public static class OrthogneissPolyadelph {
		public void cornfieldWoolman(short mesothoracic_abnormalist) {
			CheechaCistvaen restringent_cnidian = new CheechaCistvaen();
			restringent_cnidian.hindcastDoing(mesothoracic_abnormalist);
		}
	}

	public static class CheechaCistvaen {
		public void hindcastDoing(short ungual_torpedoer) {
			Tracer.tracepointWeaknessStart("CWE194", "A",
					"Unexpected Sign Extension");
			short stonesoup_array_size = ungual_torpedoer;
			Tracer.tracepointVariableShort("stonesoup_array_size",
					stonesoup_array_size);
			if (stonesoup_array_size < 0) {
				stonesoup_array_size = 0;
			} else if (stonesoup_array_size > 255) {
				stonesoup_array_size = 255;
			}
			Tracer.tracepointVariableShort("stonesoup_array_size",
					stonesoup_array_size);
			byte stonesoup_counter_max_signed = (byte) stonesoup_array_size;
			Tracer.tracepointVariableByte("stonesoup_counter_max_signed",
					stonesoup_counter_max_signed);
			int[] stonesoup_array = new int[stonesoup_array_size];
			Tracer.tracepointBufferInfo("stonesoup_array",
					stonesoup_array.length, "Length of stonesoup_array");
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			char stonesoup_counter_max = (char) stonesoup_counter_max_signed;
			Tracer.tracepointVariableChar("stonesoup_counter_max",
					stonesoup_counter_max);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				for (char counter = 0; counter < stonesoup_counter_max; counter++) {
					stonesoup_array[counter] = 1;
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (java.lang.RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(DefaultMessage.overcrowEupepsia);
				throw e;
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
	
}
