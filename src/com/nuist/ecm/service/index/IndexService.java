package com.nuist.ecm.service.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.nuist.common.define.Constant;
import com.nuist.common.utils.FetchInterface;
import com.nuist.ecm.dao.index.IndexDao;

/**
 * 首页各种展示数据
 *    
 * 项目名称：grad-project   
 * 类名称：IndexService   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月6日 下午1:30:31 
 * @version  
 * 修改人：luocf     修改时间：2015年3月6日 下午1:30:31   
 * @version
 * 修改备注：   
 *
 */
@Service
@SuppressWarnings("unchecked")
public class IndexService {
	
	@Resource(name = "interfaceConfig")
	private Map<String, String> interfaceConfig;
	
	@Autowired
	private FetchInterface fetchInterface;
	
	@Autowired
	private IndexDao indexdao;
	
	@Autowired
	@Qualifier("weatherMap")
	private Properties properties;
	
	/**
	 * 根据城市Id获取城市详细信息_中国天气网
	  
	 * getInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月6日 下午1:29:28
	 */
	public Map<String, Object> getCityInfo(String areaid) {
		Map<String, Object> info = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("areaid", areaid);
		params.put("type", "forecast_v");
		String cityInfo = fetchInterface.getDataByZGTQW(params);
		info = (Map<String, Object>) ((Map<String, Object>) JSON.parse(cityInfo));
		return info;
	}
	
	/**
	 * 中国天气网，指数数据接口
	  
	 * getzhishu  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午4:08:57
	 */
	public List<Map<String, Object>> getzhishu(String areaid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("areaid", areaid);
		params.put("type", "index_v");
		String cityInfo = "";
		try {
			cityInfo = fetchInterface.getDataByZGTQW(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, Object>> info = new ArrayList<Map<String, Object>>();
		if(!"".equals(cityInfo) && cityInfo != null) {
			 info = (List<Map<String, Object>>) ((Map<String, Object>) JSON.parse(cityInfo)).get("i");
		}
		
		return info;
	}
	
	/**
	 * 聚合数据，获取详细的天气信息，包括实时数据
	  
	 * getRetDatafromJUHE  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午4:13:16
	 */
	public Map<String, Object> getRetDatafromJUHE(String cityname){
		Map<String, String> params = new HashMap<String, String>();
		params.put("cityname", cityname);
		params.put("key", Constant.JUHE_KEY);
		params.put("dtype", "json");
		params.put("format", "2");
		String rtWeaInfo = "";
//		Map<String, Object> no = new HashMap<String, Object>();
		Map<String, Object> retData = null;
		try {
			rtWeaInfo = fetchInterface.getDataByGET("juhe_forecast", "聚合数据接口实时天气信息", params);
			if(rtWeaInfo != null && !"".equals(rtWeaInfo)){
				retData = (Map<String, Object>) ((Map<String, Object>) JSON.parse(rtWeaInfo)).get("result");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(retData != null && !retData.isEmpty()){//当返回出现异常时，防止页面报错
			String weaname = ((Map<String, Object>) retData.get("today")).get("weather").toString();
			weaname = weaname.indexOf("转") > -1 ? (weaname.split("转"))[0] : weaname;
			String weaico = properties.getProperty(weaname, "1");
			retData.put("weaico", weaico);
		} else {
			retData = new HashMap<String, Object>();
			retData.put("sk", new HashMap<String, Object>());
			retData.put("today", new HashMap<String, Object>());
			retData.put("future", new HashMap<String, Object>());
		}
		return retData;
	}
	
	
	/**
	 * 获取空气质量——PM25.in
	  
	 * getAqiFromPM25IN  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月6日 下午2:01:39
	 */
	public Map<String, Object> getAqiFromPM25IN(String stationCode) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> aqi = new HashMap<String, Object>();
		params.put("token", Constant.PM25IN_KEY);
//		params.put("city", quhao);
//		params.put("stations", "no");
		params.put("station_code", stationCode);
		String aqiInfo = fetchInterface.getDataByGET("aqiByStation_PM25in", "PM25.in空气质量指数", params);
		if("".equals(aqiInfo) || aqiInfo.indexOf("error") > -1) {
			aqi.put("flag", "0");
		} else {
			List<Map<String, Object>> aqiList = (List<Map<String, Object>>) JSON.parse(aqiInfo);
			if(aqiList != null && aqiList.size() > 0) {
				aqi = aqiList.get(0);
				aqi.put("flag", "1");
			} else {
				aqi.put("flag", "0");
			}
		}
		return aqi;
		
	}
	
	/**
	 * 从数据库获取首页新闻列表
	  
	 * getNewsList  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午7:51:48
	 */
	public List<Map<String, Object>> getNewsList(){
		return indexdao.getNewsList();
	}
	
	/**
	 * 从数据库获取首页24小时温湿度信息
	  
	 * get24Info  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午7:52:36
	 */
	public List<Map<String, Object>> get24Info(int rowNum, int page) {
		int stIndex = (page - 1) * rowNum;
		return indexdao.get24Info(stIndex, rowNum);
	}

}
