package com.nuist.ecm.action.index;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.nuist.common.define.Constant;
import com.nuist.common.utils.SpringMVCResult;
import com.nuist.ecm.service.index.IndexService;
import com.nuist.ecm.service.login.LoginService;

/**
 * 
 *    
 * 项目名称：grad-project   
 * 类名称：IndexController   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年4月9日 上午9:18:15 
 * @version  
 * 修改人：luocf     修改时间：2015年4月9日 上午9:18:15   
 * @version
 * 修改备注：   
 *
 */
@Controller
public class IndexController {

	@Autowired
	private IndexService indexService;
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("index.htm")
	public ModelAndView index(
			@RequestParam(value = "cityCode", required = false) String cityCode){
		
		if(cityCode == null || "".equals(cityCode)){
			cityCode = "101190107";
		}
		
		Map<String, Object> model = indexService.getRetDatafromJUHE("浦口");
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("sk", new HashMap<String, Object>());
//		model.put("today", new HashMap<String, Object>());
//		model.put("future", new HashMap<String, Object>());
		
		model.put("zhishu", indexService.getzhishu(cityCode));		
		model.put("imgList", loginService.getSlideImgList("campusimg"));
		SimpleDateFormat df = new SimpleDateFormat("HH");
		int hour = Integer.parseInt(df.format(new Date()));
		model.put("nowHour", hour);
		return new ModelAndView("WEB-INF/view/index/index.ftl", model);
	}
	
	@RequestMapping("index/get24Info.htm")
	public ResponseEntity<String> getTemp24Info(
			@RequestParam(value = "type", required = false) String type) {
		List<Map<String, Object>> temp24info = indexService.get24Info(24,1);
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(temp24info));
	}
	
	@RequestMapping("index/getAqiInfo.htm")
	public ResponseEntity<String> getAqiDet(
			@RequestParam(value = "quhao", required = false) String quhao) {
		Map<String, Object> temp24info = indexService.getAqiFromPM25IN(Constant.AQISTATIONCODEOFPUKOU);
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(temp24info));
	}
	
	@RequestMapping("forecastInfo.htm")
	public ResponseEntity<String> forecastInfo(
			@RequestParam(value = "cityId", required = false) String cityId,
			@RequestParam(value = "callback", required = false) String callback) throws Exception {
		cityId = URLDecoder.decode(cityId, Constant.ENCODING);
		Map<String, Object> model = indexService.getRetDatafromJUHE(cityId);
		return SpringMVCResult.returnResponseEntity(callback != null && !"".equals(callback) ? callback + "(" + JSON.toJSONString(model) + ")" : JSON.toJSONString(model));
	}
}
