package com.nuist.ecm.service.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import session.TTSSession;

import com.nuist.common.define.Constant;
import com.nuist.common.utils.MD5;
import com.nuist.ecm.dao.login.LoginDao;


@Service
public class LoginService {
	
	@Autowired
	private LoginDao logindao;

	/**
	 * 登录验证
	  
	 * doLogin  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:58:03
	 */
	public Map<String, Object> doLogin(String loginname, String loginpass) {
		boolean isNameExists = logindao.checkloginname(loginname);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", "0");
		result.put("msg", "");
		if(!isNameExists) {
			result.put("msg", "用户名不存在！");
			result.put("failCode", "L001");
		} else {
			String userid = logindao.dologin(loginname, loginpass);
			if(!"".equals(userid) && userid != null) {
				result.put("flag", "1");
				result.put("userid", userid);
			} else {
				result.put("msg", "密码不正确！");
				result.put("failCode", "L002");
			}
		}
		
		return result;
	}
	
	/**
	 * 根据userid获取用户信息
	  
	 * getNiceName  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:58:28
	 */
	public Map<String, Object> getUserInfo(String userid) {
		return logindao.getUserInfo(userid);
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
	public List<Map<String, Object>> getSlideImgList(String typename){
		return logindao.getSlideImgList(typename);
	}
	
	/**
	 * 重置密码
	  
	 * resetpasswd  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:58:14
	 */
	public Map<String, Object> resetpasswd(String loginname, String phone) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", "0");
		result.put("msg", "");
		boolean isNameExists = logindao.checkloginname(loginname);
		if(!isNameExists) {
			result.put("msg", "用户名不存在！");
			result.put("failCode", "L001");
		} else {
			String userid = logindao.checkNameAndPhone(loginname, phone);
			if(!"".equals(userid) && userid != null) {
				Random random = new Random();
			    int randompw = random.nextInt(999999-100000+1) + 100000;
				boolean resetFlag = logindao.resetPasswdByUserid(userid, MD5.encode(randompw + ""));
				if(resetFlag) {
					result.put("flag", "1");
					result.put("passwd", randompw);
				} else {
					result.put("msg", "重置密码失败！");
					result.put("failCode", "L003");
				}
			} else {
				result.put("msg", "手机号码不正确！");
				result.put("failCode", "L002");
			}
		}
		return result;
	}
}
