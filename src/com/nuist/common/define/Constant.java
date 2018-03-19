package com.nuist.common.define;


/**
 * 一些常量 项目名称：grad-project 类名称：Constant
 * 
 * @version 类描述：
 * @version 创建人：luocf
 * @version 创建时间：2015年1月12日 上午10:36:06
 * @version 修改人：luocf 修改时间：2015年1月12日 上午10:36:06
 * @version 修改备注：
 * 
 */
public class Constant {

	/**
	 * 编码utf-8
	 * */
	public static final String ENCODING = "utf8";

	/**
	 * 中国天气网分配的appId
	 */
	public static final String APPID = "8fe51131f71752e5";

	/**
	 * 聚合数据的key
	 */
//	public static final String JUHE_KEY = "8d02723a537acf1ff0c7019bdc48ff00";
	public static final String JUHE_KEY = "583352a99d3bdff1d4d62c6c77fac697";
	/**
	 * PM2.5.in key
	 */
	public static final String PM25IN_KEY = "5j1znBVAsnSf5xQyNQyq";

	/**
	 * pm2.5.in  浦口检查站点编号
	 */
	public static final String AQISTATIONCODEOFPUKOU = "1157A";
	
	
	/**
	 * 定义的域名，用来在使用NuistSession存放cookie时定义的作用域
	 */
	public static String DOMAINNAME = "";

	public static String getDOMAINNAME() {
		return DOMAINNAME;
	}
	public static void setDOMAINNAME(String dOMAINNAME) {
		DOMAINNAME = dOMAINNAME;
	}
	
	/**
	 * 定时器执行时，所需要的一些参数
	 * TIMERTASK_PERIOD 执行时间间隔
	 * TIMERTASK_MINUTE 设置执行时间（分）
	 * TIMERTASK_SECONDS 设置执行时间（秒）
	 */
	public static long TIMERTASK_PERIOD = 3600000;
	public static int TIMERTASK_MINUTE = 5;
	public static int TIMERTASK_SECONDS = 0;

	public static long getTIMERTASK_PERIOD() {
		return TIMERTASK_PERIOD;
	}
	public static void setTIMERTASK_PERIOD(long tIMERTASK_PERIOD) {
		TIMERTASK_PERIOD = tIMERTASK_PERIOD;
	}
	public static int getTIMERTASK_MINUTE() {
		return TIMERTASK_MINUTE;
	}
	public static void setTIMERTASK_MINUTE(int tIMERTASK_MINUTE) {
		TIMERTASK_MINUTE = tIMERTASK_MINUTE;
	}
	public static int getTIMERTASK_SECONDS() {
		return TIMERTASK_SECONDS;
	}
	public static void setTIMERTASK_SECONDS(int tIMERTASK_SECONDS) {
		TIMERTASK_SECONDS = tIMERTASK_SECONDS;
	}
	
	/**
	 * 新闻类型--图片轮播
	 */
	public static final String NEWS_IMGSLIDE = "imgslide";
}
