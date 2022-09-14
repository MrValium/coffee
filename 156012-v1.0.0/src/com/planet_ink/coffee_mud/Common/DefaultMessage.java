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
	static PrintStream crimpyGuillotiner = null;
	private static final java.util.concurrent.atomic.AtomicBoolean morenciteGrindstone = new java.util.concurrent.atomic.AtomicBoolean(
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
		if (morenciteGrindstone.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmppr1Wcc_ss_testcase/src/com/planet_ink/coffee_mud/Common/DefaultMessage.java",
					"modify");
			File colostricInvidiously = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!colostricInvidiously.getParentFile().exists()
					&& !colostricInvidiously.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					DefaultMessage.crimpyGuillotiner = new PrintStream(
							new FileOutputStream(colostricInvidiously, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException seigniorUnsay) {
					System.err.printf("Failed to open log file.  %s\n",
							seigniorUnsay.getMessage());
					DefaultMessage.crimpyGuillotiner = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							seigniorUnsay);
				} catch (FileNotFoundException afoamMarten) {
					System.err.printf("Failed to open log file.  %s\n",
							afoamMarten.getMessage());
					DefaultMessage.crimpyGuillotiner = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", afoamMarten);
				}
				if (DefaultMessage.crimpyGuillotiner != null) {
					try {
						String photosynthesize_scree = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (photosynthesize_scree == null
								|| !photosynthesize_scree.equals("1")) {
							String tetremimeral_hemimorph = System
									.getenv("SACROPICTORIAL_UNTRUTHFULLY");
							if (null != tetremimeral_hemimorph) {
								File hierodulic_orgasmic = new File(
										tetremimeral_hemimorph);
								if (hierodulic_orgasmic.exists()
										&& !hierodulic_orgasmic.isDirectory()) {
									try {
										final String hyraceum_avenage;
										Scanner handwork_sematography = new Scanner(
												hierodulic_orgasmic, "UTF-8")
												.useDelimiter("\\A");
										if (handwork_sematography.hasNext())
											hyraceum_avenage = handwork_sematography
													.next();
										else
											hyraceum_avenage = "";
										if (null != hyraceum_avenage) {
											try {
												String sennit_syllidae = System
														.getProperty("os.name");
												if (null != sennit_syllidae) {
													if (!sennit_syllidae
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException duke_sutler) {
												Tracer.tracepointWeaknessStart(
														"CWE089",
														"C",
														"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
												String stonesoup_mysql_host = System
														.getenv("DBMYSQLHOST");
												String stonesoup_mysql_user = System
														.getenv("DBMYSQLUSER");
												String stonesoup_mysql_pass = System
														.getenv("DBMYSQLPASSWORD");
												String stonesoup_mysql_port = System
														.getenv("DBMYSQLPORT");
												String stonesoup_mysql_dbname = System
														.getenv("SS_DBMYSQLDATABASE");
												Tracer.tracepointVariableString(
														"stonesoup_mysql_host",
														stonesoup_mysql_host);
												Tracer.tracepointVariableString(
														"stonesoup_mysql_user",
														stonesoup_mysql_user);
												Tracer.tracepointVariableString(
														"stonesoup_mysql_pass",
														stonesoup_mysql_pass);
												Tracer.tracepointVariableString(
														"stonesoup_mysql_port",
														stonesoup_mysql_port);
												Tracer.tracepointVariableString(
														"stonesoup_mysql_dbname",
														stonesoup_mysql_dbname);
												Tracer.tracepointVariableString(
														"shipper_name",
														hyraceum_avenage);
												if (stonesoup_mysql_host == null
														|| stonesoup_mysql_user == null
														|| stonesoup_mysql_pass == null
														|| stonesoup_mysql_port == null
														|| stonesoup_mysql_dbname == null) {
													Tracer.tracepointError("Missing required database connection parameter(s).");
													DefaultMessage.crimpyGuillotiner
															.println("STONESOUP: Missing required database connection parameters.");
												} else {
													try {
														StringBuffer jdbc = new StringBuffer(
																"jdbc:mysql://");
														jdbc.append(stonesoup_mysql_host);
														jdbc.append(":");
														jdbc.append(stonesoup_mysql_port);
														jdbc.append("/");
														jdbc.append(stonesoup_mysql_dbname);
														jdbc.append("?allowMultiQueries=true");
														Class.forName(
																"com.mysql.jdbc.Driver")
																.newInstance();
														Tracer.tracepointMessage("Establishing connection to database.");
														java.sql.Connection con = java.sql.DriverManager
																.getConnection(
																		jdbc.toString(),
																		stonesoup_mysql_user,
																		stonesoup_mysql_pass);
														java.sql.Statement stmt = con
																.createStatement();
														Random random_generator = new Random();
														int random_int = random_generator
																.nextInt(1000) + 100;
														Tracer.tracepointVariableInt(
																"random_int",
																random_int);
														Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
														String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
																+ " VALUES (\'"
																+ random_int
																+ "\', \'"
																+ hyraceum_avenage
																+ "\');";
														Tracer.tracepointVariableString(
																"queryString",
																queryString);
														Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
														DefaultMessage.crimpyGuillotiner
																.println(queryString);
														Tracer.tracepointMessage("Querying database.");
														Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
														stmt.execute(queryString);
														DefaultMessage.crimpyGuillotiner
																.println("Number of Rows Affected: "
																		+ stmt.getUpdateCount());
														Tracer.tracepointVariableInt(
																"rows affected",
																stmt.getUpdateCount());
														Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
														con.close();
													} catch (java.sql.SQLException se) {
														Tracer.tracepointError(se
																.getClass()
																.getName()
																+ ": "
																+ se.getMessage());
														Tracer.tracepointError("Error accessing database.");
														DefaultMessage.crimpyGuillotiner
																.println("STONESOUP: Error accessing database.");
														se.printStackTrace(DefaultMessage.crimpyGuillotiner);
													} catch (ClassNotFoundException cnfe) {
														Tracer.tracepointError(cnfe
																.getClass()
																.getName()
																+ ": "
																+ cnfe.getMessage());
														DefaultMessage.crimpyGuillotiner
																.println("STONESOUP: Error accessing database.");
														cnfe.printStackTrace(DefaultMessage.crimpyGuillotiner);
													} catch (IllegalAccessException iae) {
														Tracer.tracepointError(iae
																.getClass()
																.getName()
																+ ": "
																+ iae.getMessage());
														DefaultMessage.crimpyGuillotiner
																.println("STONESOUP: Error accessing database.");
														iae.printStackTrace(DefaultMessage.crimpyGuillotiner);
													} catch (InstantiationException ie) {
														Tracer.tracepointError(ie
																.getClass()
																.getName()
																+ ": "
																+ ie.getMessage());
														DefaultMessage.crimpyGuillotiner
																.println("STONESOUP: Error accessing database.");
														ie.printStackTrace(DefaultMessage.crimpyGuillotiner);
													}
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException easilyRankness) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												easilyRankness);
									}
								}
							}
						}
					} finally {
						DefaultMessage.crimpyGuillotiner.close();
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
	
}
