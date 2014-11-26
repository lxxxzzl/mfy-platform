package com.hcb.script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ClearSpot {

	public final static String TO_CLEAR_SPOT_CODE_STR = "51529,36488,5602,35852,81,51590,50044,6662,6847,8944,6445,1791780,6608,822,7885,22549,787438,67,39224,26703,47282,1800612,4604,6633,5478,7489,23080,6310,6471,47245,51521,94,7296,1800613,39261,39238,7733,28313,6753,7902,7518,7256,3192,5623,1791906,6938,28260,1158,6723,6828,1801019,15505,7997,6624,47557,51005,37978,39250,6766,787696,7110,4327,1792148,2037,32748,7528,6461,39223,23234,5295,5504,1800838,1160,1800839,39228,5570,22563,6812,47458,47420,,6418,5031,6467,4251,3355,7613,7493,39217,39216,6987,6796,1799759,47519,38135,32732,1791781,7368,6783,22712,39533,1791777,25099,22694,6417,6613,7289,6815,6818,6824,12238,1791791,5296,5024,1791797,7661,1994,1171316,3195,28017,1791785,5467,7245,10838,7303,6965,39241,50380,3196,2592,1799752,7756,1791786,463,1800292,7757,90,786212,37891,884,7716,7510,22921,36096,9791,6456,5824,1800183,20915,51531,7254,1800905,28,6775,6990,1791810,1791776,6971,6405,47412,6391,1800180,7621,37433,35770,5544,35679,38037,35953,10828,1697,3344,10879,50074,6340,47617,7356,1047,7152,6611,7611,7946,5398,10812,1791804,51006,6592,6939,22,35881,6651,1791806,36738,6402,1800478,26736,33171,1049,6629,23213,1791775,36750,5875,7634,47443,6394,732,39144,2136,6748,34871,1171910,65,3352,5898,1171899,,7484,26719,9824,8063,1791793,224,38706,1357,,37,39346,39240,27152,38776,787426,,,39229,39540,7987,50442,,,,33902,1800904,33930,33487,,4800,6628,39218,39207,1042,1800548,7132,6649,5713,7492,7535,26626,37430,47415,31,1041,,7496,6827,26944,39210,10878,33965,6963,6768,5779,1171284,7011,23273,1799624,32610,6585,10876,786406,6715,38994,5742,47390,22597,6652,6984,6823,1791783,39239,6730,35906,6653,1801030,1557,50149,36751,32254,592,27052,5406,27570,32359,10853,36494,7992,38125,8103,47283,38661,51530,6580,7330,39349,50010,28292,1168502,6874,1791787,36031,1791802,1800285,1045,3957,36753,10819,1799892,24096,6396,6593,8972,6725,1048,4997,6390,22609,6945,7575,27577,23310,4426,4293,39236,26908,39225,33717,36489,1172155,39298,31413,39214,6749,23079,22710,6755,6650,33481,47583,1156,47520,7116,6788,7495,6805,1791788,6985,36472,1791821,26714,22701,50403,47349,1169664,10045,10048,10047,10050,17,6865,8125,495,,,25995,6940,,7591,7486,1791801,39343,466,6978,1799753,1799895,5487,6419,5887,1791808,7324,740,6964,35736,5403,9290,38764,467,6409,731,36482,47410,5129,6421,39152,6773,6616,7844,6959,,18849,1791782,22808,5624,36399,8050,1487,89,5635,4923,4378,,36643,36548,16,6654,6465,6384,6606,1791790,34414,39534,8025,1791792,7044,39220,39215,728,7622,1791814,21426,7294,590,5712,6643,22800,826,47444,4377,32363,22783,35811,6414,7713,6821,,6437,13319,47531,36370";
	
	public final static String SYSTEN_CONFIG_FILE_PATH = "config/system-config.properties";
			
    /**
     * 本地端口
     */
    static int lport = 0;
    
    /**
     * 远程MySQL服务器
     */
    static String rhost = null;
    
    /**
     * 远程MySQL服务端口
     */
    static int rport = 0;
    
	public final static String mysqlDriverClassName = "com.mysql.jdbc.Driver"; 
	
	public static String productDBPasswrod = null; 
	public static String productDBUserName = null;
	public static String productDBProxyUrl = null;
	
    /**
     * SSH连接用户名
     */
    String user = null;
    
    /**
     * SSH连接密码
     */
    String password = null;
    
    /**
     * SSH服务器
     */
    String host = null;
    
	/**
	 * 查询景点code不为0产品模板列表
	 */
	public final static String queryProBaseSql = "select id, spot_code, spot_name from product_base where spot_code!=0"; 
	
	/**
	 * 查询景点code不为0产品模板行程目的地列表
	 */
	public final static String queryJourneyDestSql = "select j.base_id,d.spot_code, d.spot_name from product_journey  j join product_journey_destination d on d.journey_id=j.id and d.del_flag=0 and d.spot_code!=0 where j.del_flag=0 order by j.base_id"; 
	
	private Connection productDBConn ;
    
	private List<String> clearSpotCodeList = new ArrayList<String>(); 
	
	private Session session = null;
	
	public ClearSpot() throws NumberFormatException, Exception {
		//加载配置文件
		this.loadConfig();
		
		String[] clearSpotCodeArray = TO_CLEAR_SPOT_CODE_STR.split(",");
		clearSpotCodeList = Arrays.asList(clearSpotCodeArray);
		this.go();
		Class.forName(mysqlDriverClassName);
		productDBConn = DriverManager.getConnection(productDBProxyUrl, productDBUserName,
                 productDBPasswrod); 
	}
	
	/**
	 * 加载配置文件
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void loadConfig() throws NumberFormatException, Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream(SYSTEN_CONFIG_FILE_PATH));
		
		//本地端口
	    lport = Integer.valueOf(getPropertyValue(properties, "lport"));
	    //远程MySQL服务器
	    rhost = getPropertyValue(properties, "rhost");
	    //远程MySQL服务端口
	    rport = Integer.valueOf(getPropertyValue(properties, "rport"));
	    
		productDBPasswrod = getPropertyValue(properties, "productDBPasswrod");
		productDBUserName = getPropertyValue(properties, "productDBUserName");  
		productDBProxyUrl = getPropertyValue(properties, "productDBProxyUrl");
		
		//SSH连接用户名
	    user = getPropertyValue(properties, "user");
	    //SSH连接密码
	    password = getPropertyValue(properties, "password");
	    //SSH服务器
	    host = getPropertyValue(properties, "host");
	}
	
	/**
	 * 读取配置项
	 * @param properties
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private String getPropertyValue(Properties properties, String key) throws Exception {
		String value = null;
		if (properties.containsKey(key)) {
			value = properties.getProperty(key);
		} else {
			throw new Exception("config properties file error, can't find key: " + key);
		} 
		return value;
	}
	
    public void go() {
        int port = 22;//SSH访问端口
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 获取景点code不为空的产品模板列表
	 * @return
	 * @throws SQLException
	 */
	public List<ProBase> queryProBaselist() throws SQLException {
		List<ProBase> proBaselist = new ArrayList<ProBase>();
		PreparedStatement queryProBaseStatement = null;
		ResultSet queryProductResultSet = null;
		try {
			queryProBaseStatement = productDBConn.prepareStatement(queryProBaseSql);
			queryProductResultSet = queryProBaseStatement.executeQuery(); 
			while (queryProductResultSet.next()) {
				ProBase proBase = new ProBase();
				proBase.setId(queryProductResultSet.getInt(1));
				proBase.setSpotCode(queryProductResultSet.getString(2));
				proBase.setSpotName(queryProductResultSet.getString(3));
				proBaselist.add(proBase); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != queryProductResultSet) {
				queryProductResultSet.close();
			}
			
			if (null != queryProBaseStatement) {
				queryProBaseStatement.close();
			}
		}
		return proBaselist;
	}
	
	/**
	 * 获取景点code不为空的行程目的地列表
	 * @return
	 * @throws SQLException
	 */
	public List<ProBase> queryJourneyDestlist() throws SQLException {
		List<ProBase> journeyDestlist = new ArrayList<ProBase>(); 
		PreparedStatement queryStatement = null;
		ResultSet queryResultSet = null; 
		try {
			queryStatement = productDBConn.prepareStatement(queryJourneyDestSql);
			queryResultSet = queryStatement.executeQuery(); 
			while (queryResultSet.next()) {
				ProBase proBase = new ProBase();
				proBase.setId(queryResultSet.getInt(1));
				proBase.setSpotCode(queryResultSet.getString(2));
				proBase.setSpotName(queryResultSet.getString(3));
				journeyDestlist.add(proBase); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != queryResultSet) {
				queryResultSet.close();
			}
			
			if (null != queryStatement) {
				queryStatement.close();
			}
		}
		return journeyDestlist;
	}
	
	/**
	 * 断开连接
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (null != productDBConn) {
			productDBConn.close(); 
		}
		
		if (null != session) {
			session.disconnect();
		}
	}
	
	/**
	 * 创建文本输出流对象
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter createFileWriter(String fileName) throws IOException {
		BufferedWriter resultWriter = null; 
    	StringBuilder sb = new StringBuilder();
    	sb.append(fileName);
    	sb.append(".txt");
        resultWriter = new BufferedWriter(new FileWriter(new File(sb.toString())));
        return resultWriter;
	}
	
	/**
	 * 过滤出来自于产品模板中，需要清理景点名称的产品模板id集合
	 * @return
	 * @throws SQLException
	 */
	public List<ProBase> filterClearSpotProBaseIdSetFromProBase() throws SQLException {  
		System.out.println("start execute Spot filter from proBase");
		List<ProBase> toClearSpotBaseIds = new ArrayList<ProBase>();
		List<ProBase> proBaselist = this.queryProBaselist();
		if (null != proBaselist) {
			for (ProBase proBase : proBaselist) {
				if (clearSpotCodeList.contains(proBase.getSpotCode())) {
					toClearSpotBaseIds.add(proBase);
					//System.out.println(proBase);
				}
			}
		}
		System.out.println("finish execute Spot filter from proBase, toClearSpotBaseIds.size: " + toClearSpotBaseIds.size());
		return toClearSpotBaseIds;
	}

	/**
	 * 过滤出来自于产品模板行程资源目的地中，需要清理景点名称的产品模板id集合
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 */
	public Set<Integer> filterClearSpotProBaseIdSetFromJourneyDestination() throws SQLException, IOException { 
		BufferedWriter resultWriter = createFileWriter("filterClearSpotProBaseIdSetFromJourneyDestination");
		long currentMills = System.currentTimeMillis();
		System.out.println("start execute Spot filter from journey destination");
		Set<Integer> toClearSpotBaseIds = new HashSet<Integer>();
		List<ProBase> journeyDestlist = this.queryJourneyDestlist();
		if (null != journeyDestlist) {
			for (ProBase proBase : journeyDestlist) {
				if (clearSpotCodeList.contains(proBase.getSpotCode())) {
					toClearSpotBaseIds.add(proBase.getId());
					//System.out.println(proBase);
					resultWriter.append(proBase.toString());
					resultWriter.newLine();
				}
			}
		}
		System.out.println("finish execute Spot filter from journey destination, toClearSpotBaseIds.size: " + toClearSpotBaseIds.size() 
				+ " takes:" + (System.currentTimeMillis()-currentMills) +"ms");
		if (null != resultWriter) {
			resultWriter.close();
		}
		return toClearSpotBaseIds;
	}
	
	/**
	 * 产品模板VO
	 */
	class ProBase {
		/**
		 * 模板id
		 */
		private int id;
		
		/**
		 * 景点名称 (可以是产品模板的景点，也可以是行程资源的景点)
		 */
		private String spotName;
		
		/**
		 * 景点code  (可以是产品模板的景点，也可以是行程资源的景点)
		 */
		private String spotCode;
		
		public String getSpotName() {
			return spotName;
		}

		public void setSpotName(String spotName) {
			this.spotName = spotName;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSpotCode() {
			return spotCode;
		}

		public void setSpotCode(String spotCode) {
			this.spotCode = spotCode;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
//			sb.append(this.getId());
//			sb.append(",");
//			sb.append(this.getSpotCode());
//			sb.append(",");
			sb.append(this.getSpotName());
			return sb.toString();
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		ClearSpot clearSpot = new ClearSpot();
		
		//过滤出来自于产品模板中，需要清理景点名称的产品模板id集合
		List<ProBase> toClearSpotBaseIdsFromProBase = clearSpot.filterClearSpotProBaseIdSetFromProBase();
		
        //过滤出来自于产品模板行程资源目的地中，需要清理景点名称的产品模板id集合
		Set<Integer> toClearSpotBaseIdsFromJourneyDestination = clearSpot.filterClearSpotProBaseIdSetFromJourneyDestination();
		
		//过滤交集
		System.out.println("start 过滤交集");
	    List<Integer> mergeList = new ArrayList<Integer>();
		Iterator<ProBase> it = toClearSpotBaseIdsFromProBase.iterator();
		while (it.hasNext()) {
			ProBase proBase = it.next();
			if (toClearSpotBaseIdsFromJourneyDestination.contains(proBase.getId())) {
				mergeList.add(proBase.getId());
				System.out.println(proBase);
			}
		}
		System.out.println("end 过滤交集: " + mergeList.size());
		
		//断开DB连接
		clearSpot.close(); 
	}

}
