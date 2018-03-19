/**   
* 文件名：LoginDao.java  
* 版本信息：   
* 日期：2015年3月22日   
* Copyright TTS Corporation 2015    
* 版权所有   
*   
*/
package com.nuist.ecm.dao.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**   
 *    
 * 项目名称：grad-project   
 * 类名称：LoginDao   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月22日 下午11:06:37 
 * @version  
 * 修改人：luocf     修改时间：2015年3月22日 下午11:06:37   
 * @version
 * 修改备注：   
 *
 */
@Repository
public class LoginDao {

	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(LoginDao.class);
	
	/**
	 * 检查用户名是否存在
	  
	 * checkloginname  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:41:33
	 */
	public boolean checkloginname(String loginname) {
		String sql = "SELECT COUNT(*) FROM ecm_users WHERE loginname = ?";
		Map<String, Object> count = null;
		boolean isExist = false;
		try {
			count = jdbcTemplate.queryForMap(sql, loginname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count != null){
			Object num = count.get("count(*)");
			isExist = Integer.parseInt(num.toString()) > 0 ? true : false;
		}
		log.info("查询用户名是否存在：" + isExist);
		return isExist;
	}
	
	/**
	 * 登录，返回userid
	  
	 * dologin  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:41:54
	 */
	public String dologin(String loginname, String loginpass) {
		String sql = "SELECT userid FROM ecm_users WHERE loginname = ? AND loginpass = ?";
		Map<String, Object> userMap = null;
		String userid = "";
		String nickname = "";
		try {
			userMap =  jdbcTemplate.queryForMap(sql, loginname, loginpass);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.info("登录失败！");
			return null;
		}
		if(userMap != null) {
			userid = userMap.get("userid").toString();
			log.info("登录成功！userid=" + userid);
		} else {
			log.info("登录失败！");
		}
		return userid;
	}
	
	/**
	 * 通过userid获取用户信息
	  
	 * getNickName  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:42:22
	 */
	public Map<String, Object> getUserInfo(String userid) {
		String sql = "SELECT * FROM ecm_users WHERE userid = ?";
		Map<String, Object> userMap = null;
		try {
			userMap =  jdbcTemplate.queryForMap(sql, Integer.parseInt(userid));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.info("获取用户信息失败！");
			return null;
		}
		if(userMap != null) {
			log.info("获取用户信息成功！userMap=" + userMap);
		} else {
			log.info("获取用户信息失败！");
		}
		return userMap;
	}
	
	/**
	 * 检查用户名和手机号是否匹配,并返回userid
	  
	 * checkNameAndPhone  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:43:16
	 */
	public String checkNameAndPhone(String loginname, String phone) {
		String sql = "SELECT * FROM ecm_users WHERE loginname = ? AND phone = ?";
		Map<String, Object> userMap = null;
		String userid = "";
		try {
			userMap =  jdbcTemplate.queryForMap(sql, loginname, phone);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.info("检验手机号码失败！");
			return null;
		}
		if(userMap != null) {
			userid = userMap.get("userid").toString();
			log.info("检验手机号码成功！userid=" + userid);
		} else {
			log.info("检验手机号码失败！");
		}
		return userid;
	}
	
	/**
	 * 根据userid把用户密码重置为生成的随机的六位数字
	  
	 * resetPasswdByUserid  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:54:18
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
	 * 获取轮播图片列表
	 * getNewsList  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月18日 下午3:58:03
	 */
	public List<Map<String, Object>> getSlideImgList(String typename) {
		String sql = "SELECT title,imgurl,imghref,imgdesc FROM ecm_news WHERE typename = ? AND ableflag = 'Y' ORDER BY publishtime DESC LIMIT 5";
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		try {
			newsList = jdbcTemplate.queryForList(sql,typename);
			log.info("获取轮播图片列表成功" + newsList);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			log.info("获取轮播图片列表失败");
		}
		return newsList;
	}
	
}
