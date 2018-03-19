package com.nuist.ecm.action.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import session.TTSSession;

import com.alibaba.fastjson.JSON;
import com.nuist.common.define.Constant;
import com.nuist.common.utils.SpringMVCResult;
import com.nuist.ecm.service.login.LoginService;

/**
 * 登录
 * 项目名称：grad-project   
 * 类名称：LoginController   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年1月20日 下午7:28:20 
 * @version  
 * 修改人：luocf     修改时间：2015年1月20日 下午7:28:20   
 * @version
 * 修改备注：   
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录首页跳转
	  
	 * toLoginIndex  
	 * @param    
	 * @return  
	 * @throws Exception 
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午10:59:52
	 */
	@RequestMapping("index.htm")
	public ModelAndView toLoginIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		if(!"".equals(userid)) {
			String domain = request.getContextPath();
			return new ModelAndView(new RedirectView(domain + "/manager"));
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("imgList", loginService.getSlideImgList("slideimg"));
			return new ModelAndView("WEB-INF/view/login/login.ftl", model);
		}
	}
	
	/**
	 * 登录并保存userid到cookie
	  
	 * dologin  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 上午11:23:37
	 */
	@RequestMapping("doLogin.htm")
	public ResponseEntity<String> dologin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "loginname") String loginname,
			@RequestParam(value = "loginpass") String loginpass) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		result = loginService.doLogin(loginname, loginpass);
		if ("1".equals(result.get("flag"))){
			TTSSession session = new TTSSession(request, response);
			String userid = (result.get("userid")).toString();
			session.setAttribute("userid", userid, Constant.DOMAINNAME);
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 退出登录
	  
	 * logOut  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 上午9:35:35
	 */
	@RequestMapping("logOut.htm")
	public ResponseEntity<String> logOut(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "flag", required = false) String flag){
		TTSSession session = new TTSSession(request, response);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			session.delAttribute("userid", Constant.DOMAINNAME);
			result.put("flag", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("flag", "0");
			e.printStackTrace();
		}//清除cookie
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	/**
	 * 获取图片验证码
	 * captchaRegister  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2014年12月9日 下午2:13:53
	 */
	@RequestMapping("getCaptcha.htm")
	public void captchaRegister(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int width = 65;
		int height = 30;	
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Arial", Font.BOLD, 22));
		//随机获取字母
		Random random = new Random();
		String captchaTouchCreate = "";
		char[] s = new char[4];
		for(int i=0; i<4; i++){
			int j  = random.nextInt(62);
			if(j < 10){
				s[i] = (char) ('0' + j);
			}else if(j < 36){
				s[i] = (char) (j - 10 + 'a');
			}else{
				s[i] = (char) (j - 36 + 'A');
			}
			captchaTouchCreate += s[i];
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(s[i]+"", 7 + 12 * i, 22);
		}
		TTSSession session = new TTSSession(request, response);
		// 将验证码存入SESSION
		session.setAttribute("captcha", captchaTouchCreate, Constant.DOMAINNAME);
		//生成图像
		g.dispose();
		response.setContentType("image/jpeg");
		response.setHeader("Cache-Control", "no-cache");
		ServletOutputStream output = response.getOutputStream();
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		ImageIO.write(image, "JPEG", imageOut);
		imageOut.close();
	}
	
	/**
	 * 验证验证码是否正确
	 * getJudgeCaptchaTrueFlag  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2014年12月9日 下午2:48:59
	 */
	@RequestMapping("verifyCaptcha.htm")
	public ResponseEntity<String> getJudgeCaptchaTrueFlag(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "captchaInput") String captchaInput){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			TTSSession session = new TTSSession(request, response);
			String sessionCaptcha = null == session.getAttribute("captcha") ? "" : session.getAttribute("captcha") + "";
//			boolean captureFlag = CommonUtil.getCaptureVerify(sessionCaptcha, captchaInput);
			boolean captureFlag = false;
			sessionCaptcha=sessionCaptcha.toLowerCase();
			captchaInput=captchaInput.toLowerCase();
			if(sessionCaptcha.equals(captchaInput))
				captureFlag=true;
			result.put("captureFlag", captureFlag);
		}catch(Exception e){
			result.put("captureFlag", false);
			e.printStackTrace();
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 登录验证
	  
	 * verifyLogin  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月23日 下午11:00:15
	 */
	@RequestMapping("verifyLogin.htm")
	public ResponseEntity<String> verifyLogin(HttpServletRequest request,HttpServletResponse response) {
		TTSSession session = new TTSSession(request, response);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", "0");
		try {
			String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
			if (!"".equals(userid)) {
				Map<String, Object> userInfo = loginService.getUserInfo(userid);
				if (userInfo != null) {
					String nickname = userInfo.get("nickname").toString();
					if("".equals(nickname) || nickname == null) {
						nickname = userInfo.get("loginname").toString();
					}
					if(!"".equals(nickname) && nickname != null) {
						result.put("flag", "1");
						result.put("nickname", nickname);
						result.put("phonenum", userInfo.get("phone"));
					}
				}
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 重置密码
	  
	 * resetpasswd  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 上午9:35:17
	 */
	@RequestMapping("resetpasswd.htm")
	public ResponseEntity<String> resetpasswd(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "phone") String phone) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		result = loginService.resetpasswd(username, phone);
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
}
