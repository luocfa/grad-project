package com.nuist.ecm.service.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuist.common.define.Constant;
import com.nuist.ecm.dao.login.LoginDao;
import com.nuist.ecm.dao.manager.ManagerDao;
import com.nuist.ecm.service.index.IndexService;

/**
 * 
 *    
 * 项目名称：grad-project   
 * 类名称：ManagerService   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月25日 下午4:19:34 
 * @version  
 * 修改人：luocf     修改时间：2015年3月25日 下午4:19:34   
 * @version
 * 修改备注：   
 *
 */
@Service
public class ManagerService {
	
	@Autowired
	private ManagerDao managerdao;
	
	@Autowired
	private LoginDao logindao;
	
	@Autowired
	private IndexService indexService;
	private Logger log = Logger.getLogger(ManagerService.class);
	/**
	 * 修改密码
	  
	 * changePasswd  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 下午4:21:07
	 */
	public Map<String, Object> changePasswd(String userid, String phone, String oldpass, String pass) {
		Map<String, Object> userInfo = logindao.getUserInfo(userid);
		String rPhone = userInfo.get("phone").toString();
		String rPass = userInfo.get("loginpass").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if ("".equals(userid)) {
			result.put("flag", "0");
			result.put("msg", "你的登陆已过期，请先登陆");
		} else if ("".equals(rPhone)) {
			result.put("flag", "0");
			result.put("msg", "手机号码验证失败");
		} else if (!phone.equals(rPhone)){
			result.put("flag", "0");
			result.put("msg", "手机号码错误");
		} else if(!oldpass.equals(rPass)) {
			result.put("flag", "0");
			result.put("msg", "旧密码不正确");
		} else {
			if (managerdao.resetPasswdByUserid(userid, pass)) {
				result.put("flag", "1");
			} else {
				result.put("flag", "0");
				result.put("msg", "修改密码失败，请重试");
			}
		}
		return result;
	}
	
	/**
	 * 更新用户信息
	  
	 * updateuserInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 下午1:46:10
	 */
	public boolean updateuserInfo(String userid, String phone, String nickname) {
		return managerdao.updateuserinfo(userid, phone, nickname);
	}
	
	/**
	 * 首先查一下数据库内在本小时是否已经有数据
	  
	 * checkIsInsertedHourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月26日 上午10:48:37
	 */
	public boolean checkIsInsertedHourInfo() {
		Map<String, Object> hasInsert = managerdao.checkIsInsertedHourInfo();
		String retryFlag = hasInsert.get("retryFlag").toString();
		while("1".equals(retryFlag)) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hasInsert = managerdao.checkIsInsertedHourInfo();
			retryFlag = hasInsert.get("retryFlag").toString();
		}
		String count = hasInsert.get("COUNT(*)").toString();
		if (!"0".equals(count)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 插入一次数据的方法
	 * 如果没有数据就不执行插入，有的话再获取值
	  
	 * autoInsertHourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月26日 上午10:03:52
	 */
	@SuppressWarnings("unchecked")
	public boolean autoInsertHourInfo() {
		String temp = "", shidu = "", aqi = "";
		Map<String, Object> weatherInfo = indexService.getRetDatafromJUHE("浦口");
		if(weatherInfo != null && !weatherInfo.isEmpty()) {
			Map<String, Object> retInfo = (Map<String, Object>) weatherInfo.get("sk");
			if (retInfo != null && !retInfo.isEmpty()) {
				temp = retInfo.get("temp").toString();
				shidu = retInfo.get("humidity").toString();
				shidu = shidu.replace("%", "");
			} else {
				log.info("天气接口返回失败");
			}
		} else {
			log.info("天气接口返回失败");
		}
		Map<String, Object> aqiInfo = indexService.getAqiFromPM25IN(Constant.AQISTATIONCODEOFPUKOU);
		if (aqiInfo != null && !aqiInfo.isEmpty()) {
			aqi = aqiInfo.get("aqi").toString();
		} else {
			log.info("aqi接口返回失败");
		}
		if (!"".equals(temp) && !"".equals(shidu) && !"".equals(aqi)) {
			if(managerdao.autoInserthourInfo(temp, shidu, aqi)){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 更新24小时气象信息
	  
	 * updatehourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月28日 上午11:04:29
	 */
	public boolean updatehourInfo(String temp, String shidu, String aqi, String id) {
		return managerdao.updatehourInfo(temp, shidu, aqi, id);
	}
	
	/**
	 * 插入图片到数据库
	  
	 * insertNews  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 上午10:03:29
	 */
	public Map<String, Object> insertNews(String typename,String imgurl) {
		return managerdao.insertnews(typename,imgurl);
	}
	
	/**
	 * 更新图片信息
	  
	 * updateimg  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 上午10:03:45
	 */
	public boolean updateimg(String title, String imgdesc, String imgurl, String typename, String imghref, String ableflag, String publishtime) {
		if ("".equals(imghref)) {
			imghref = "javascript:;";
		}
		imgurl = imgurl.replaceAll("(^[a-zA-Z]+\\/[a-zA-Z]+)", typename + "/" + typename);
		return managerdao.updateImg(title, imgdesc, imgurl, typename, imghref, ableflag, publishtime);
	}
	
	public static void main(String[] args) {
		String imgurl = "campusimg/slideimdfsfsag_1427965430546.png";
		imgurl = imgurl.replaceAll("(^[a-zA-Z]+\\/[a-zA-Z]+)", "campus/camffpus");
		System.out.print(imgurl);
	}
}
