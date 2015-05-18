package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.csrc.stock.client.StockConfig;
import com.csrc.stock.model.Authinfo;
import com.csrc.stock.model.FundBean;
import com.csrc.stock.model.PositionBean;
import com.csrc.stock.model.PriceBean;
import com.csrc.stock.model.StockBean;

@SuppressWarnings({ "unused", "unchecked" })
public class StockTest {
	public static String year = "2015";
	public static String month = "";
	public static String topline = "10";
	Map<String, FundBean> fundMap = new HashMap<String, FundBean>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
	String now_str = sdf.format(new Date());

	@Test
	public void tReg() throws Exception {
		String str = "<ab>1</ab>cdcr<ab>2</ab>fvf<ab>3</ab>";
		String reg = "<ab>.*?</ab>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			System.out.println(matcher.group());
		}

		String path = "e:/11.obj";
		// ObjectOutputStream oos = new ObjectOutputStream(new
		// FileOutputStream(path));
		// List<PositionBean> pl = new ArrayList<PositionBean>();
		// PositionBean positionBean = new PositionBean();
		// positionBean.setRank(1);
		// pl.add(positionBean);
		// positionBean = new PositionBean();
		// positionBean.setRank(2);
		// pl.add(positionBean);
		// oos.writeObject(pl);
		// oos.close();

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		List<PositionBean> pl2 = (List<PositionBean>) ois.readObject();
		ois.close();
		System.out.println(pl2.get(1).getRank());

	}

	@Test
	public void t01() throws Exception {
		String year = "2015";
		// String year =
		// String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String topline = "10";
		String month = "";
		// String stockNo = "660001";
		String stockNo = "710002";
		// String resp = getRawPosition(stockNo, topline, year, month);
		// System.out.println("resp: ------\n" + resp);
		//
		// List<PositionBean> positionBeans = parsePosition(resp);
		// System.out.println(positionBeans);
		// Map<String, List<PositionBean>> map = new HashMap<String,
		// List<PositionBean>>();
		// map.put(stockNo, positionBeans);

		String fundNo = "660001";
		String fundName = "农银成长";
		FundBean fundBean = new FundBean(fundNo, fundName);
		fundMap.put(fundNo, fundBean);
		fundNo = "710002";
		fundName = "富安达策略";
		fundBean = new FundBean(fundNo, fundName);
		fundMap.put(fundNo, fundBean);
		// hotFundStatics(fundMap);
		Authinfo authinfo = getAuthinfo("18010151140", "east-3698");

		String raw_favor = getRawFavor(authinfo);
		fundMap = parseRawFavor(raw_favor);
		hotFundStatics(fundMap);
	}

	private Map<String, FundBean> parseRawFavor(String raw_favor) {
		System.out.println("###############parseRawFavor【开始】#############");
		if (raw_favor == null || raw_favor.trim().equals("")) {
			System.out.println("[parseRawFavor]参数为空！");
			return null;
		}
		Map<String, FundBean> fundMap = new HashMap<String, FundBean>();
		String reg = "\"(\\d{6})\",\\s*\"([^,]+)\",";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(raw_favor);
		String reg2;
		Pattern pattern2;
		Matcher matcher2;
		while (matcher.find()) {
			String fundNo = matcher.group(1);
			String fundName = matcher.group(2);
			FundBean fundBean = new FundBean(fundNo, fundName);
			System.out.println(fundBean);
			fundMap.put(fundNo, fundBean);
		}

		System.out.println("###############parseRawFavor【结束】#############");
		return fundMap;
	}

	public String getRawPriceInfo(String gpdmCode) throws IOException {
		System.out.println("###############getRawPriceInfo【开始】#############");
		if (gpdmCode == null || gpdmCode.trim().equals("")) {
			System.out.println("[getRawPriceInfo]参数为空！");
			return null;
		}

		String reg = "(\\d{7},)+";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(gpdmCode);
		if (!matcher.matches()) {
			System.out.println("[getRawPriceInfo]参数格式不正确！");
			return null;
		}
		// gpdmCode =
		// "0016962,0008292,0026572,0023392,6007701,3000282,6013771,6001801,0024182,6003911,";
		String strAction = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd="
				+ gpdmCode
				+ "&sty=E1OQCPZT&st=z&sr=&p=&ps=&cb=&js=var%20js_fav={favif:[%28x%29]}&token=8a36403b92724d5d1dd36dc40534aec5&rt="
				+ Math.random();
		URL myURL = new URL(strAction);
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);

		// 消息头
		httpConn.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		httpConn.addRequestProperty("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpConn.addRequestProperty("Cache-Control", "max-age=0");
		httpConn.addRequestProperty("Connection", "Keep-Alive");
		// httpConn.addRequestProperty("Cookie","");
		httpConn.addRequestProperty("Host", "nufm.dfcfw.com");
		httpConn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		printRequestHeaders(httpConn);

		httpConn.connect();

		InputStreamReader insr = new InputStreamReader(
				httpConn.getInputStream(), "utf-8");
		BufferedReader br = new BufferedReader(insr);
		String line, str = "";
		while ((line = br.readLine()) != null) {
			str += line + "\n";
		}

		httpConn.disconnect();
		System.out.println("###############getRawPriceInfo【结束】#############");

		return str;
	}

	public void hotFundStatics(Map<String, FundBean> fundMap) throws Exception {
		System.out.println("###############hotFundStatics【开始】#############");
		if (fundMap == null || fundMap.size() == 0) {
			System.out.println("[hotFundStatics]参数为空！");
			return;
		}
		Map<String, List<PositionBean>> positionMap = new HashMap<String, List<PositionBean>>();
		for (String key : fundMap.keySet()) {
			FundBean fundBean = fundMap.get(key);
			String resp;
			try {
				resp = getRawPosition(fundBean.getFundNo(), topline, year,
						month);
				System.out.println("resp: ------\n" + resp);
				List<PositionBean> positionBeans = parsePosition(
						fundBean.getFundNo(), resp);
				System.out.println(positionBeans);
				fundBean.setPositionBeans(positionBeans);
				positionMap.put(fundBean.getFundNo(), positionBeans);
			} catch (IOException e) {
				System.err.println(fundBean.getFundNo() + ": 接收异常！！");
				e.printStackTrace();
			}

		}

		persistFundsPosition(fundMap);

		Map<String, StockBean> stockMap = statics2stockmap(fundMap, positionMap);
		persistStockMap(stockMap);

		System.out.println("###############hotFundStatics【结束】#############");
	}

	private void persistStockMap(Map<String, StockBean> stockMap)
			throws Exception {
		System.out.println("###############persistStockMap【开始】#############");
		if (stockMap == null || stockMap.size() == 0) {
			System.out.println("[persistStockMap]参数为空！");
			return;
		}

		// 通过ArrayList构造函数把map.entrySet()转换成list
		ArrayList<Entry<String, StockBean>> mappingList = new ArrayList<Map.Entry<String, StockBean>>(
				stockMap.entrySet());
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, StockBean>>() {
					public int compare(Map.Entry<String, StockBean> mapping1,
							Map.Entry<String, StockBean> mapping2) {
						if (mapping2.getValue().getTotalCost() > mapping1
								.getValue().getTotalCost())
							return 1;
						return -1;
					}
				});

		String dirpath = "e:/stock";
		File dirFile = new File(dirpath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		String histpath = dirpath + "/stock_hist.txt";
		File histFile = new File(histpath);
		if (!histFile.exists() || !histFile.isFile()) {
			histFile.createNewFile();
		}

		Map<String, StockBean> tmp_stockMap;
		ObjectOutputStream out;
		ObjectInputStream in;
		StockBean stockBean, tmp_stockBean;

		String objpath = dirpath + "/stock_last.obj";
		File objFile = new File(objpath);
		String str = "-----------------------------------" + now_str
				+ "-----------------------------------\n";
		String lastpath = dirpath + "/stock_last.txt";
		File lastFile = new File(lastpath);
		if (!lastFile.exists() || !lastFile.isFile()) {
			lastFile.createNewFile();
			for (Map.Entry<String, StockBean> mapping : mappingList) {
				System.out.println(mapping.getKey() + ":" + mapping.getValue());
				str += mapping.getValue().format(StockConfig.SPLIT) + StockConfig.SPLIT + "0" + StockConfig.SPLIT + "0.0\n";
			}
		} else {
			if (!objFile.exists() || !objFile.isFile()) {
				System.out.println("[persistStockMap]objFile不存在！");
				return;
			}
			in = new ObjectInputStream(new FileInputStream(objpath));
			tmp_stockMap = (Map<String, StockBean>) in.readObject();
			in.close();
			if (tmp_stockMap == null || tmp_stockMap.size() == 0) {
				System.out
						.println("[persistStockMap]tmp_stockMap为空！tmp_stockMap="
								+ tmp_stockMap);
				return;
			}
			for (Map.Entry<String, StockBean> mapping : mappingList) {
		//	for (String stockNo : stockMap.keySet()) {
				//stockBean = stockMap.get(stockNo);
				stockBean = mapping.getValue();
				//tmp_stockBean = tmp_stockMap.get(stockNo);
				String stockNo = mapping.getKey();
				tmp_stockBean = tmp_stockMap.get(stockNo);
				if (tmp_stockBean == null) {
					str += stockBean.format(StockConfig.SPLIT) + StockConfig.SPLIT + "0" + StockConfig.SPLIT + "0.0" + StockConfig.SPLIT + "新进\n";
					continue;
				}
				long holdnum1 = tmp_stockBean.getTotalHoldNum();
				long holdnum2 = stockBean.getTotalHoldNum();
				long hold_diff = holdnum2 - holdnum1;
				float hold_diffpercent = (float) ((double) hold_diff / holdnum1);
				str += stockBean.format(StockConfig.SPLIT) + StockConfig.SPLIT + hold_diff + StockConfig.SPLIT
						+ hold_diffpercent + "\n";
			}
		}
		str += "\n";
		FileWriter writer = new FileWriter(histFile, true);
		writer.write(str);
		writer.flush();
		writer.close();

		out = new ObjectOutputStream(new FileOutputStream(objpath));
		out.writeObject(stockMap);
		out.flush();
		out.close();

		System.out.println("###############persistStockMap【结束】#############");
	}

	private void persistFundsPosition(Map<String, FundBean> fundMap)
			throws Exception {
		System.out
				.println("###############persistFundsPosition【开始】#############");
		if (fundMap == null || fundMap.size() == 0) {
			System.out.println("[statics_core]参数为空！");
			return;
		}

		FundBean fundBean;
		for (String fundNo : fundMap.keySet()) {
			fundBean = fundMap.get(fundNo);
			persistFundPosition(fundBean);
		}

		System.out
				.println("###############persistFundsPosition【结束】#############");
	}

	private void persistFundPosition(FundBean fundBean) throws Exception {
		System.out
				.println("###############persistFundPosition【开始】#############");
		if (fundBean == null || fundBean.isEmpty()) {
			System.out.println("[persistFundPosition]参数为空！");
			return;
		}

		String fundstr = "[" + fundBean.getFundNo() + "]"
				+ fundBean.getFundName();
		String dirpath = "e:/stock/" + fundstr;
		File dirFile = new File(dirpath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		String histpath = dirpath + "/" + fundstr + "_hist.txt";
		File histFile = new File(histpath);
		if (!histFile.exists() || !histFile.isFile()) {
			histFile.createNewFile();
		}

		
		ObjectInputStream in;
		ObjectOutputStream out;
		
		String stockNo;
		PositionBean positionBean, tmp_positionBean;
		List<PositionBean> tmp_PositionBeans;
		StockBean tmp_stockBean;
		String objpath = dirpath + "/" + fundstr + "_last.obj";
		File objFile = new File(objpath);
		
		String str = "-----------------------------------" + now_str
				+ "-----------------------------------\n";
		String lastpath = dirpath + "/" + fundstr + "_last.txt";
		File lastFile = new File(lastpath);
		if (!lastFile.exists() || !lastFile.isFile()) {
			lastFile.createNewFile();
			for (int i = 0; i < fundBean.getPositionBeans().size(); i++) {
				positionBean = fundBean.getPositionBeans().get(i);
				str += positionBean.format(StockConfig.SPLIT) + StockConfig.SPLIT + "0" + StockConfig.SPLIT + "0.0\n";
			}
		} else {
			if (!objFile.exists() || !objFile.isFile()) {
				System.out.println("[persistFundPosition]objFile不存在！");
				return;
			}
			
			Map<String, PositionBean> tmp_positionMap = new HashMap<String, PositionBean>();
			in = new ObjectInputStream(new FileInputStream(objpath));
			tmp_PositionBeans = (List<PositionBean>) in.readObject();
			in.close();
			for (int i = 0; i < tmp_PositionBeans.size(); i++) {
				tmp_positionBean = tmp_PositionBeans.get(i);
				stockNo = tmp_positionBean.getStockBean().getStockNo();
				tmp_positionMap.put(stockNo, tmp_positionBean);
			}
			for (int i = 0; i < fundBean.getPositionBeans().size(); i++) {
				positionBean = fundBean.getPositionBeans().get(i);
				stockNo = positionBean.getStockBean().getStockNo();
				tmp_positionBean = tmp_positionMap.get(stockNo);
				if (tmp_positionBean == null) {
					str += positionBean.format(StockConfig.SPLIT) + StockConfig.SPLIT + "0" + StockConfig.SPLIT + "0.0" + StockConfig.SPLIT + "新进\n";
					continue;
				}
				long holdnum1 = tmp_positionBean.getStockBean().getTotalHoldNum();
				long holdnum2 = positionBean.getStockBean()
						.getTotalHoldNum();
				long hold_diff = holdnum2 - holdnum1;
				float hold_diffpercent = (float) ((double) hold_diff / holdnum1);
				str += positionBean.format(StockConfig.SPLIT) + StockConfig.SPLIT + hold_diff + StockConfig.SPLIT
						+ hold_diffpercent + "\n";
			}
		}
		str += "\n";
		FileWriter writer = new FileWriter(histFile, true);
		writer.write(str);
		writer.flush();
		writer.close();

		out = new ObjectOutputStream(new FileOutputStream(objpath));
		out.writeObject(fundBean.getPositionBeans());
		out.flush();
		out.close();

		System.out
				.println("###############persistFundPosition【结束】#############");

	}

	private void getEntryinfo() throws IOException {
		System.out.println("###############getEntryinfo【开始】#############");
		String strAction = "http://passport.eastmoney.com/ajax/lg.aspx";
		URL myURL = new URL(strAction);
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);

		// 消息头
		httpConn.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		httpConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		httpConn.addRequestProperty("Cache-Control", "no-cache");
		httpConn.addRequestProperty("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpConn.addRequestProperty("Cache-Control", "max-age=0");
		httpConn.addRequestProperty("Connection", "Keep-Alive");
		httpConn.addRequestProperty(
				"Cookie",
				"pgv_pvi=630118134; emstat_bc_emcount=31875638851444062267; emstat_ss_emcount=7_1431708748_3073576512; pu=18010151140");

		httpConn.addRequestProperty(
				"Referer",
				"http://passport.eastmoney.com/loglogin.EmUser?http://fund.eastmoney.com/favor/");
		// refer length
		httpConn.addRequestProperty("Host", "passport.eastmoney.com");
		httpConn.addRequestProperty("Pragma", "no-cache");
		httpConn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		printRequestHeaders(httpConn);

		httpConn.connect();

		printResponseHeaders(httpConn);
		String reg = "pi=([^;]+);";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher;
		Authinfo authinfo = new Authinfo();
		List<String> cookieList = httpConn.getHeaderFields().get("Set-Cookie");
		if (cookieList.size() >= 2) {
			// System.out.println(cookieList.get(0));
			matcher = pattern.matcher(cookieList.get(1));
			if (matcher.find()) {
				System.out.println("pi: " + matcher.group(1));
				authinfo.setPi(matcher.group(1).trim());
			}
		}
		authinfo.setPgv_info("");
		authinfo.setEmstat_bc_emcount("31875638851444062267");
		authinfo.setEmstat_ss_emcount("7_1431708748_3073576512");
		authinfo.setPu("18010151140");
		// httpConn.gets

		InputStreamReader insr = new InputStreamReader(
				httpConn.getInputStream(), "utf-8");
		BufferedReader br = new BufferedReader(insr);
		String line, str = "";
		while ((line = br.readLine()) != null) {
			str += line + "\n";
		}
		httpConn.disconnect();
		System.out.println("login_resp: " + str);
		if (!str.trim().equals("0")) {
			System.out.println("[getAuthinfo]登录失败！");
			return;
		}

		System.out.println("###############getAuthinfo【结束】#############");
	}

	private Authinfo getAuthinfo(String username, String password)
			throws IOException {
		System.out.println("###############getAuthinfo【开始】#############");
		if (username == null || username.trim().equals("") || password == null
				|| password.trim().equals("")) {
			System.out.println("[getAuthinfo]参数为空！");
			return null;
		}

		String strAction = "http://passport.eastmoney.com/ajax/lg.aspx";
		URL myURL = new URL(strAction);
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);

		// 消息头
		httpConn.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		httpConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		httpConn.addRequestProperty("Cache-Control", "no-cache");
		httpConn.addRequestProperty("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpConn.addRequestProperty("Cache-Control", "max-age=0");
		httpConn.addRequestProperty("Connection", "Keep-Alive");
		// httpConn.addRequestProperty("Cookie","pgv_pvi=630118134; emstat_bc_emcount=31875638851444062267; emstat_ss_emcount=7_1431708748_3073576512; pu=18010151140");

		httpConn.addRequestProperty(
				"Referer",
				"http://passport.eastmoney.com/loglogin.EmUser?http://fund.eastmoney.com/favor/");
		// refer length
		httpConn.addRequestProperty("Host", "passport.eastmoney.com");
		httpConn.addRequestProperty("Pragma", "no-cache");
		httpConn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		printRequestHeaders(httpConn);

		httpConn.connect();
		// 写正文
		String paramstr = "vcode=&u=" + username + "&p=" + password + "&r="
				+ Math.random();
		httpConn.getOutputStream().write(paramstr.getBytes());
		httpConn.getOutputStream().flush();
		httpConn.getOutputStream().close();

		printResponseHeaders(httpConn);
		String reg;
		Pattern pattern;
		Matcher matcher;
		Authinfo authinfo = new Authinfo();
		List<String> cookieList = httpConn.getHeaderFields().get("Set-Cookie");
		if (cookieList.size() >= 2) {
			// System.out.println(cookieList.get(0));
			reg = "pi=([^;]+);";
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(cookieList.get(1));
			if (matcher.find()) {
				System.out.println("pi: " + matcher.group(1));
				authinfo.setPi(matcher.group(1).trim());
			}
			reg = "pu=([^;]+);";
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(cookieList.get(0));
			if (matcher.find()) {
				System.out.println("pu: " + matcher.group(1));
				authinfo.setPu(matcher.group(1).trim());
			}
		}
		authinfo.setPgv_info("");
		authinfo.setEmstat_bc_emcount("31875638851444062267");
		authinfo.setEmstat_ss_emcount("7_1431708748_3073576512");
		authinfo.setPu("18010151140");
		// httpConn.gets

		InputStreamReader insr = new InputStreamReader(
				httpConn.getInputStream(), "utf-8");
		BufferedReader br = new BufferedReader(insr);
		String line, str = "";
		while ((line = br.readLine()) != null) {
			str += line + "\n";
		}
		httpConn.disconnect();
		System.out.println("login_resp: " + str);
		if (!str.trim().equals("0")) {
			System.out.println("[getAuthinfo]登录失败！");
			return null;
		}

		System.out.println("###############getAuthinfo【结束】#############");

		return authinfo;
	}

	private String getRawFavor(Authinfo authinfo) throws IOException {
		System.out.println("###############getRawFavor【开始】#############");
		if (authinfo == null) {
			System.out.println("[getRawFavor]参数为空！");
			return null;
		}

		String strAction = "http://fund.eastmoney.com/Data/FavorCenter_v3.aspx?o=r&rnd="
				+ new Date().getTime();
		URL myURL = new URL(strAction);
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);

		// 消息头
		httpConn.addRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		// httpConn.setRequestProperty("Content-Type",
		httpConn.addRequestProperty("Cache-Control", "no-cache");
		httpConn.addRequestProperty("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpConn.addRequestProperty("Cache-Control", "max-age=0");
		httpConn.addRequestProperty("Connection", "Keep-Alive");
		httpConn.addRequestProperty("Cookie", "pi=" + authinfo.getPi()
				+ "; pu=" + authinfo.getPu() + ";");

		// httpConn.addRequestProperty("Referer",
		// "http://fund.eastmoney.com/favor.html");
		// refer length
		httpConn.addRequestProperty("Host", "fund.eastmoney.com");
		// httpConn.addRequestProperty("Pragma", "no-cache");
		httpConn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		printRequestHeaders(httpConn);
		httpConn.connect();
		printResponseHeaders(httpConn);

		InputStreamReader insr = new InputStreamReader(
				httpConn.getInputStream(), "utf-8");
		BufferedReader br = new BufferedReader(insr);
		String line, str = "";
		while ((line = br.readLine()) != null) {
			str += line + "\n";
		}
		httpConn.disconnect();
		System.out.println("favor_resp: " + str);

		System.out.println("###############getRawFavor【结束】#############");

		return str;
	}

	private Map<String, StockBean> statics2stockmap(
			Map<String, FundBean> fundMap,
			Map<String, List<PositionBean>> positionMap) {
		System.out.println("###############statics_core【开始】#############");
		if (fundMap == null || fundMap.size() == 0 || positionMap == null
				|| positionMap.size() == 0) {
			System.out.println("[statics_core]参数为空！");
			return null;
		}

		Map<String, StockBean> stockMap = new HashMap<String, StockBean>();
		List<PositionBean> positionList;
		for (String fundNo : positionMap.keySet()) {
			positionList = positionMap.get(fundNo);
			if (positionList == null) {
				System.err.println(fundNo + ": positionList为空");
				continue;
			}
			for (int i = 0; i < positionList.size(); i++) {
				PositionBean positionBean = positionList.get(i);
				String stockNo = positionBean.getStockBean().getStockNo();
				StockBean stockBean = positionBean.getStockBean();
				StockBean tmp_stockBean = stockMap.get(stockNo);
				String holder;
				if (tmp_stockBean == null) {
					holder = "[" + fundNo + "]"
							+ fundMap.get(fundNo).getFundName() + "#";
					stockBean.setHolder(holder);
					stockMap.put(stockNo, stockBean);
				} else {
					// tmp_stockBean.setPriceBean(stockBean.getPriceBean());
					holder = tmp_stockBean.getHolder() + "[" + fundNo + "]"
							+ fundMap.get(fundNo).getFundName() + "#";
					tmp_stockBean.setHolder(holder);
					tmp_stockBean.setTotalHoldNum(tmp_stockBean
							.getTotalHoldNum() + stockBean.getTotalHoldNum());
					tmp_stockBean.setTotalCost(tmp_stockBean.getTotalCost()
							+ stockBean.getTotalCost());
				}
			}

		}

		for (String key : stockMap.keySet()) {
			System.out.println("[" + key + "]: " + stockMap.get(key));
		}

		System.out.println("###############statics_core【结束】#############");
		return stockMap;
	}

	private List<PositionBean> parsePosition(String fundNo, String resp)
			throws IOException {
		System.out.println("###############parsePosition【开始】#############");
		if (resp == null || resp.trim().equals("")) {
			System.out.println("[parsePosition]参数为空！");
			return null;
		}

		List<PositionBean> list = new ArrayList<PositionBean>();

		String table_str;
		String tbody_str;
		String tr_str;
		String td_str;

		String reg = "<table\\s+.*?>.*?</table>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(resp);
		if (!matcher.find()) {
			System.out.println("[parsePosition]table未找到！");
			return null;
		}
		table_str = matcher.group();

		reg = "<div[^>]*gpdmList'\\s*>(.*?)</div>";
		pattern = Pattern.compile(reg);
		matcher = pattern.matcher(resp);
		Map<String, PriceBean> priceMap = new HashMap<String, PriceBean>();
		if (matcher.find()) {
			System.out.println("gpdmList: " + matcher.group(1).trim());
			String rpi_resp = getRawPriceInfo(matcher.group(1).trim());
			reg = "\"(.*?)\"";
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(rpi_resp);
			String str, price_str, diff_str;
			float price, diff;
			String reg2;
			Pattern pattern2;
			Matcher matcher2;
			while (matcher.find()) {
				str = matcher.group(1).trim();
				String[] strs = str.split(",");
				String stockNo = strs[1].trim();
				String stockName = strs[2].trim();
				price_str = strs[3].trim();
				reg2 = "\\d[\\d\\.]*";
				pattern2 = Pattern.compile(reg2);
				matcher2 = pattern2.matcher(price_str);
				if (matcher2.matches()) {
					price = Float.parseFloat(price_str);
				} else {
					price = -1f;
				}
				diff_str = strs[4].trim().substring(0,
						strs[4].trim().length() - 1);
				reg2 = "\\-?\\d[\\d\\.]*";
				pattern2 = Pattern.compile(reg2);
				matcher2 = pattern2.matcher(diff_str);
				if (matcher2.matches()) {
					diff = Float.parseFloat(diff_str) / 100;
				} else {
					diff = 0f;
				}
				PriceBean priceBean = new PriceBean(price, diff);
				priceMap.put(stockNo, priceBean);
			}
		}

		reg = "<tbody>.*?</tbody>";
		pattern = Pattern.compile(reg);
		matcher = pattern.matcher(table_str);
		if (!matcher.find()) {
			System.out.println("[parsePosition]tbody未找到！");
			return null;
		}
		tbody_str = matcher.group();
		reg = "<tr>.*?</tr>";
		pattern = Pattern.compile(reg);
		matcher = pattern.matcher(tbody_str);
		while (matcher.find()) {
			tr_str = matcher.group();
			String reg2 = "<td.*?>(.*?)</td>";
			Pattern pattern2 = Pattern.compile(reg2);
			Matcher matcher2 = pattern2.matcher(tr_str);
			StockBean stockBean = new StockBean();
			PositionBean positionBean = new PositionBean();
			int cnt = 1;
			String stockNo;
			String stockName;
			int rank;
			float lastprice;
			float diff;
			float percent;
			long stockNum;
			long cost;
			String reg3;
			Pattern pattern3;
			Matcher matcher3;
			while (matcher2.find()) {
				td_str = matcher2.group();
				// System.out.println("td_str: " + td_str);
				String td_inner = matcher2.group(1).trim();
				switch (cnt++) {
				case 1:
					rank = Integer.parseInt(td_inner);
					positionBean.setRank(rank);
					break;
				case 2:
					reg3 = "<a.*?>(.*?)</a>";
					pattern3 = Pattern.compile(reg3);
					matcher3 = pattern3.matcher(td_inner);
					if (matcher3.find()) {
						stockNo = matcher3.group(1).trim();
						stockBean.setStockNo(stockNo);
						PriceBean priceBean = priceMap.get(stockNo);
						if (priceBean != null) {
							stockBean.setPriceBean(priceBean);
						}
					}
					break;
				case 3:
					reg3 = "<a.*?>(.*?)</a>";
					pattern3 = Pattern.compile(reg3);
					matcher3 = pattern3.matcher(td_inner);
					if (matcher3.find()) {
						stockName = matcher3.group(1).trim();
						stockBean.setStockName(stockName);
					}
					break;
				case 7:
					String tmp_str = td_inner.substring(0,
							td_inner.length() - 1);
					percent = Float.parseFloat(tmp_str) / 100;
					positionBean.setPercent(percent);
					break;
				case 8:
					stockNum = str2Long(td_inner.replaceAll(",", ""));
					if (stockNum != -1l) {
						stockBean.setTotalHoldNum(stockNum);
					}
				case 9:
					cost = str2Long(td_inner.replaceAll(",", ""));
					if (cost != -1l) {
						stockBean.setTotalCost(cost);
					}
					break;
				default:
					break;
				}
			}
			String holder = "[" + fundNo + "]"
					+ fundMap.get(fundNo).getFundName() + "#";
			stockBean.setHolder(holder);
			positionBean.setStockBean(stockBean);
			list.add(positionBean);
		}

		System.out.println("###############parsePosition【结束】#############");

		return list;
	}

	private long str2Long(String str) {
		if (str == null || str.trim().equals("")) {
			System.out.println("[parseStr2Long]参数为空");
			return -1l;
		}
		str = str.replaceAll(",", "");
		String reg = "[\\d\\.]+";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			System.out.println("[parseStr2Long]参数格式不正确：str=" + str);
			return -1l;
		}
		int idx = str.indexOf('.');
		if (idx == -1) {
			str = str + "0000";
			return Long.parseLong(str);
		}
		int len = str.length();
		String str1 = str.substring(0, idx);
		if (idx == len - 2) {
			str = str.replaceAll("\\.", "") + "000";
			return Long.parseLong(str);
		}
		if (idx == len - 3) {
			str = str1 + str.substring(idx + 1, idx + 3) + "00";
			return Long.parseLong(str);
		}
		if (idx == len - 4) {
			str = str1 + str.substring(idx + 1, idx + 4) + "0";
			return Long.parseLong(str);
		}
		if (idx <= len - 5) {
			str = str1 + str.substring(idx + 1, idx + 5);
			return Long.parseLong(str);
		}

		return -1l;
	}

	public String getRawPosition(String fundNo, String topline, String year,
			String month) throws IOException {
		System.out.println("###############getRawPosition【开始】" + fundNo
				+ "#############");
		if (fundNo == null || fundNo.trim().equals("")) {
			System.out.println("[getRawPosition]参数为空！");
			return null;
		}

		String reg = "\\d{6}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(fundNo);
		if (!matcher.matches()) {
			System.out.println("[getRawPosition]参数格式不正确！");
			return null;
		}

		if (topline == null || topline.trim().equals("")) {
			topline = "10";
		}
		if (year == null || year.trim().equals("")) {
			// year = "";
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		}
		if (month == null || month.trim().equals("")) {
			month = "";
		}

		String strAction = "http://fund.eastmoney.com/f10/FundArchivesDatas.aspx?type=jjcc&code="
				+ fundNo
				+ "&topline="
				+ topline
				+ "&year="
				+ year
				+ "&month="
				+ month + "&rt=" + Math.random();
		URL myURL = new URL(strAction);
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);

		// 消息头
		httpConn.addRequestProperty("Accept", "*/*");
		httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		httpConn.addRequestProperty("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpConn.addRequestProperty("Connection", "Keep-Alive");
		// httpConn.addRequestProperty("Cookie","");
		httpConn.addRequestProperty("Host", "fund.eastmoney.com");
		httpConn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		printRequestHeaders(httpConn);

		httpConn.connect();

		InputStreamReader insr = new InputStreamReader(
				httpConn.getInputStream(), "gb2312");
		BufferedReader br = new BufferedReader(insr);
		String line, str = "";
		while ((line = br.readLine()) != null) {
			str += line + "\n";
		}

		httpConn.disconnect();
		System.out.println("###############getRawPosition【结束】#############");
		return str;
	}

	public void printRequestHeaders(HttpURLConnection httpConn) {
		System.out.println("-----------请求头----------");
		Map<String, List<String>> reqheader_map = httpConn
				.getRequestProperties();
		for (String key : reqheader_map.keySet()) {
			System.out.print(key + ": ");
			List<String> hlist = reqheader_map.get(key);
			for (int i = 0; i < hlist.size(); i++) {
				System.out.println(hlist.get(i));
			}
		}
	}

	public void printResponseHeaders(HttpURLConnection httpConn) {
		System.out.println("-----------响应头----------");
		Map<String, List<String>> hmaps = httpConn.getHeaderFields();
		for (String key : hmaps.keySet()) {
			System.out.print(key + ": ");
			List<String> hlist = hmaps.get(key);
			for (int i = 0; i < hlist.size(); i++) {
				System.out.println(hlist.get(i));
			}
		}
	}

	public String getCompositeStr(StockBean stockBean) {
		if (stockBean == null)
			return null;
		return "[" + stockBean.getStockNo() + "]" + stockBean.getStockName();
	}

	public String getCompositeStr(FundBean fundBean) {
		if (fundBean == null)
			return null;
		return "[" + fundBean.getFundNo() + "]" + fundBean.getFundName();
	}
}
