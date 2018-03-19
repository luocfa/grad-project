package com.nuist.ecm.dao.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 后台管理
 *    
 * 项目名称：grad-project   
 * 类名称：ManagerDao   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月25日 下午4:00:02 
 * @version  
 * 修改人：luocf     修改时间：2015年3月25日 下午4:00:02   
 * @version
 * 修改备注：   
 *
 */
@Repository("managerdao")
public class ManagerDao {

	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(ManagerDao.class);
	
	
	/**
	 * 修改密码
	  
	 * resetPasswdByUserid  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 下午4:07:35
	 */
	public boolean resetPasswdByUserid(String userid, String passwd) {
		String sql = "UPDATE ecm_users SET loginpass = ? WHERE userid = ?";
		int num = 0;
		try {
			num =  jdbcTemplate.update(sql, passwd, Integer.parseInt(userid));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("重置密码失败！");
		}
		return num > 0 ? true : false;
	}
	
	/**
	 * 更改用户信息
	  
	 * updateuserinfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 下午1:40:19
	 */
	public boolean updateuserinfo(String userid, String phone, String nickname) {
		String sql = "UPDATE ecm_users SET nickname = ?, phone = ? WHERE userid = ?";
		int num = 0;
		try {
			num =  jdbcTemplate.update(sql, nickname, phone, Integer.parseInt(userid));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("更新用户信息失败！");
		}
		return num > 0;
	}
	
	/**
	 * 24小时信息--检查现在这个小时有没有插入值
	  
	 * checkIsInsertedHourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月27日 下午4:21:22
	 */
	public Map<String, Object> checkIsInsertedHourInfo() {
		String sql = "SELECT COUNT(*) FROM ecm_24info WHERE up_datestr = DATE_FORMAT(NOW(),'%Y%m%d-%H')";
		Map<String, Object> count = new HashMap<String,Object>();
		try {
			count = jdbcTemplate.queryForMap(sql);
			count.put("retryFlag", "0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			count.put("retryFlag", "1");
		}
		return count;
	}
	
	/**
	 * 24小时信息更新
	 * autoInserthourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月26日 上午8:42:41
	 */
	public boolean autoInserthourInfo(String temp, String shidu, String aqi) {
		String sql = "INSERT INTO ecm_24info (up_datetime,up_day,up_datestr,temp,shidu,aqi) VALUES (NOW(),DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(NOW(),'%Y%m%d-%H'),?,?,?)";
		int info = 0;
		try {
			info = jdbcTemplate.update(sql,temp,shidu,aqi);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("24小时信息插入：" + (info > 0 ? "数据存库成功" : "数据存库失败"));
		return info > 0 ? true : false;
	}
	
	/**
	 * 24小时信息更新
	 * autoInserthourInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月26日 上午8:42:41
	 */
	public boolean updatehourInfo(String temp, String shidu, String aqi, String id) {
		String sql = "UPDATE ecm_24info SET temp = ?, shidu = ?, aqi = ? WHERE id = ?";
		int info = 0;
		try {
			info = jdbcTemplate.update(sql,temp,shidu,aqi,id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("24小时信息更新：" + (info > 0 ? "数据存库成功" : "数据存库失败"));
		return info > 0 ? true : false;
	}
	
	/**
	 * 插入新闻或者图片
	  
	 * insertnews  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月27日 下午4:32:36
	 */
	public Map<String, Object> insertnews(String typename,String imgurl) {
		String sql = "INSERT INTO ecm_news (publishtime,imgurl,typename,ableflag) VALUES (DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'),?,?,'N')";
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		int info = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			info = jdbcTemplate.update(sql,date,imgurl,typename);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("图片插入：" + (info > 0 ? "数据存库成功" : "数据存库失败"));
		if (info > 0) {
			result.put("flag", "1");
			result.put("up_datetime", date);
		} else {
			result.put("flag", "0");
		}
		return result;
	}
	
	/**
	 * 图片内容更新
	  
	 * updateImg  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 上午9:09:39
	 */
	public boolean updateImg(String title, String imgdesc, String imgurl, String typename, String imghref, String ableflag, String publishtime) {
		String sql = "UPDATE ecm_news SET title = ?, imgdesc = ?, imgurl = ?, typename = ?, imghref = ?,ableflag = ? WHERE publishtime = DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s')";
//		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//		String date = df.format(new Date());
		int info = 0;
		try {
			info = jdbcTemplate.update(sql,title,imgdesc,imgurl,typename,imghref,ableflag,publishtime);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("图片更新插入：" + (info > 0 ? "数据存库成功" : "数据存库失败"));
		return info > 0 ? true : false;
	}
}
