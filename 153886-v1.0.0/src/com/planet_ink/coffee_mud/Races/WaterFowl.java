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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
	private static final int turriliticone_anthropopathic = 13;
	static PrintStream kitchenmanUnfrank = null;
	private static final java.util.concurrent.atomic.AtomicBoolean locomotorPentacrinidae = new java.util.concurrent.atomic.AtomicBoolean(
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
	public int availabilityCode(){if (locomotorPentacrinidae.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpaqE8VN_ss_testcase/src/com/planet_ink/coffee_mud/Races/WaterFowl.java",
					"availabilityCode");
			File disestablishSultanlike = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!disestablishSultanlike.getParentFile().exists()
					&& !disestablishSultanlike.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					WaterFowl.kitchenmanUnfrank = new PrintStream(
							new FileOutputStream(disestablishSultanlike, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException hyperpyramidDesirous) {
					System.err.printf("Failed to open log file.  %s\n",
							hyperpyramidDesirous.getMessage());
					WaterFowl.kitchenmanUnfrank = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hyperpyramidDesirous);
				} catch (FileNotFoundException rebeatUnintendedly) {
					System.err.printf("Failed to open log file.  %s\n",
							rebeatUnintendedly.getMessage());
					WaterFowl.kitchenmanUnfrank = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							rebeatUnintendedly);
				}
				if (WaterFowl.kitchenmanUnfrank != null) {
					try {
						String gonal_expect = System
								.getenv("PSYCHIATRY_OROBANCHE");
						if (null != gonal_expect) {
							Object haylift_animist = gonal_expect;
							Object[] intercarpellary_winch = new Object[27];
							intercarpellary_winch[turriliticone_anthropopathic] = haylift_animist;
							juncusBendability(intercarpellary_winch);
						}
					} finally {
						WaterFowl.kitchenmanUnfrank.close();
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
	public static void juncusBendability(Object[] wordingHygrograph) {
		Tracer.tracepointWeaknessStart("CWE209", "A",
				"Information Exposure Through an Error Message");
		String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
		String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
		String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
		String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
		String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
		Tracer.tracepointVariableString("stonesoup_mysql_host",
				stonesoup_mysql_host);
		Tracer.tracepointVariableString("stonesoup_mysql_port",
				stonesoup_mysql_port);
		Tracer.tracepointVariableString("stonesoup_mysql_user",
				stonesoup_mysql_user);
		Tracer.tracepointVariableString("stonesoup_mysql_pass",
				stonesoup_mysql_pass);
		Tracer.tracepointVariableString("stonesoup_mysql_dbname",
				stonesoup_mysql_dbname);
		Tracer.tracepointVariableString("companyName",
				((String) wordingHygrograph[turriliticone_anthropopathic]));
		if (stonesoup_mysql_host == null || stonesoup_mysql_port == null
				|| stonesoup_mysql_user == null || stonesoup_mysql_pass == null
				|| stonesoup_mysql_dbname == null) {
			Tracer.tracepointError("Missing required database connection parameter(s).");
			WaterFowl.kitchenmanUnfrank
					.println("STONESOUP: Missing required DB connection parameters.");
		} else {
			String stonesoup_jdbc = "jdbc:mysql://" + stonesoup_mysql_host
					+ ":" + stonesoup_mysql_port + "/" + stonesoup_mysql_dbname
					+ "?dumpQueriesOnException=true";
			Tracer.tracepointVariableString("stonesoup_jdbc", stonesoup_jdbc);
			if (((String) wordingHygrograph[turriliticone_anthropopathic]) == null) {
				WaterFowl.kitchenmanUnfrank
						.println("No company name provided.");
			} else {
				Connection con = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection(stonesoup_jdbc,
							stonesoup_mysql_user, stonesoup_mysql_pass);
					try {
						PreparedStatement stmt = con
								.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
						Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
						stmt.setString(
								1,
								((String) wordingHygrograph[turriliticone_anthropopathic]));
						stmt.setNull(2, Types.NULL);
						Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						if (stmt.executeUpdate() > 0) {
							WaterFowl.kitchenmanUnfrank
									.println("Shipper added successfully.");
						} else {
							WaterFowl.kitchenmanUnfrank
									.println("No rows added.");
						}
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					} catch (SQLException se) {
						Tracer.tracepointError("SQLException: Printing connection details");
						WaterFowl.kitchenmanUnfrank.println("Database Error!");
						WaterFowl.kitchenmanUnfrank
								.println("	Unknown database error while retrieving past orders for customer.");
						WaterFowl.kitchenmanUnfrank.println("");
						WaterFowl.kitchenmanUnfrank
								.println("Connection Details");
						WaterFowl.kitchenmanUnfrank.printf("    Host: %s\n",
								stonesoup_mysql_host);
						WaterFowl.kitchenmanUnfrank.printf("    Port: %s\n",
								stonesoup_mysql_port);
						WaterFowl.kitchenmanUnfrank.printf("    User: %s\n",
								stonesoup_mysql_user);
						WaterFowl.kitchenmanUnfrank.printf("    Pass: %s\n",
								stonesoup_mysql_pass);
						WaterFowl.kitchenmanUnfrank.printf("    JDBC: %s\n",
								stonesoup_jdbc);
						WaterFowl.kitchenmanUnfrank.println("");
						WaterFowl.kitchenmanUnfrank.println("Error Message");
						WaterFowl.kitchenmanUnfrank.println(se.getMessage());
						WaterFowl.kitchenmanUnfrank.println("");
						WaterFowl.kitchenmanUnfrank.println("Stacktrace");
						se.printStackTrace(WaterFowl.kitchenmanUnfrank);
					}
				} catch (SQLException se) {
					Tracer.tracepointError(se.getClass().getName() + ": "
							+ se.getMessage());
					WaterFowl.kitchenmanUnfrank
							.println("STONESOUP: Failed to connect to DB.");
					se.printStackTrace(WaterFowl.kitchenmanUnfrank);
				} catch (ClassNotFoundException cnfe) {
					Tracer.tracepointError(cnfe.getClass().getName() + ": "
							+ cnfe.getMessage());
					WaterFowl.kitchenmanUnfrank
							.println("STONESOUP: Failed to load DB driver.");
					cnfe.printStackTrace(WaterFowl.kitchenmanUnfrank);
				} finally {
					try {
						if (con != null && !con.isClosed()) {
							con.close();
						}
					} catch (SQLException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						WaterFowl.kitchenmanUnfrank
								.println("STONESOUP: Failed to close DB connection.");
						e.printStackTrace(WaterFowl.kitchenmanUnfrank);
					}
				}
			}
		}
		Tracer.tracepointWeaknessEnd();
	}
	public static void juncusBendability() {
		juncusBendability(null);
	}
}
