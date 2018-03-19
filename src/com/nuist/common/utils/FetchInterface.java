package com.nuist.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuist.common.define.Constant;


/**
 * 调用中国天气网接口公共方法
 * 项目名称：grad-project   
 * 类名称：FetchInterface   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年1月12日 上午10:51:52 
 * @version  
 * 修改人：luocf     修改时间：2015年1月12日 上午10:51:52   
 * @version
 * 修改备注：   
 *
 */
@Component
public class FetchInterface {
	
	@Resource(name = "interfaceConfig")
	private Map<String, String> interfaceConfig;
	private Logger log = Logger.getLogger(FetchInterface.class);
	@Autowired
	private EncryTheKey encryTheKey;
	
	/**
	 * 通过get方式请求接口--中国天气网专用接口appid
	 * getDataByGET  
	 * @param url:按接口要求的url;Name:接口名称;params:参数
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年1月13日 上午10:51:21
	 */
	public String getDataByZGTQW(Map<String, String> params) {
		String encoding = Constant.ENCODING;//接口编码
		String url = interfaceConfig.get("safe_forecast");
		String result = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		params.put("date", date);
		url = url + "?areaid=" + params.get("areaid") + "&type=" + params.get("type") + "&date=" + date + "&appid=" + Constant.APPID;
		String key = encryTheKey.standardURLEncoder(url);
		url =  url.replace(Constant.APPID, Constant.APPID.substring(0, 6)) + "&key=" + key;
		log.info("中国天气网" + url);
		try {
			result = HttpRequestUtils.request(url, null, HttpRequestUtils.REQUEST_TYPE_GET, encoding);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		log.info(result);
		return result;
	}
	
	/**
	 * 通过get方式请求接口数据
	 * getDataByGET  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月5日 上午9:45:55
	 */
	public String getDataByGET(String urlKey, String interfaceDesc, Map<String, String> params) {
		String encoding = Constant.ENCODING;//接口编码
		String result = "";
		String url = interfaceConfig.get(urlKey);
		log.info(interfaceDesc + params);
		try {
			result = HttpRequestUtils.request(url, params, HttpRequestUtils.REQUEST_TYPE_GET, encoding);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		log.info(result);
		return result;
	}
	
	/**
	 * 通过post方式请求接口
	 * getDataByPOST  
	 *  @param url:按接口要求的url;Name:接口名称;params:参数
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年1月13日 上午10:54:37
	 */
	public String getDataByPOST(String urlKey, String interfaceDesc, Map<String, String> params) {
		String encoding = Constant.ENCODING;//接口编码
		String result = "";
		String url = interfaceConfig.get(urlKey);
		log.info(interfaceDesc + params);
		try {
			result = HttpRequestUtils.request(url, params, HttpRequestUtils.REQUEST_TYPE_POST, encoding);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		log.info(result);
		return result;
	}
	
}
