package com.nuist.ecm.action.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import session.TTSSession;

import com.alibaba.fastjson.JSON;
import com.nuist.common.define.Constant;
import com.nuist.common.utils.SpringMVCResult;
import com.nuist.ecm.service.index.IndexService;
import com.nuist.ecm.service.manager.ManagerService;

/**
 * 后台管理
 *    
 * 项目名称：grad-project   
 * 类名称：ManagerController   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月24日 下午1:28:09 
 * @version  
 * 修改人：luocf     修改时间：2015年3月24日 下午1:28:09   
 * @version
 * 修改备注：   
 *
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private ManagerService managerservice;
	
	@Autowired
	private IndexService indexService;
	
	/**
	 * 
	  
	 * managerIndex  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月24日 下午5:22:53
	 */
	@RequestMapping("index.htm")
	public ModelAndView managerIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		if("".equals(userid)) {
			String domain = request.getContextPath();
			return new ModelAndView(new RedirectView(domain + "/login/"));
		} else {
			return new ModelAndView("/WEB-INF/view/manager/index.ftl");
		}
	}
	
	/**
	 * 菜单栏的跳转
	  
	 * welcome  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 下午4:27:23
	 */
	@RequestMapping("main.htm")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageCode") String pageCode) throws Exception {
		String viewStr = "/WEB-INF/view/manager/main/" + pageCode + ".ftl";
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		if("".equals(userid)) {
			String domain = request.getContextPath();
			return new ModelAndView(new RedirectView(domain + "/login/index.htm"));
		} else {
			return new ModelAndView(viewStr);
		}
	}
	
	/**
	 * 修改密码
	  
	 * changepass  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月25日 下午4:27:42
	 */
	@RequestMapping("userinfo/changepass.htm")
	public ResponseEntity<String> changepass(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "oldpass", required = false) String oldpass,
			@RequestParam(value = "pass", required = false) String pass) throws Exception {
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		Map<String, Object> result = managerservice.changePasswd(userid, phone, oldpass, pass);
		if("1".equals(result.get("flag"))) {
			session.delAttribute("userid", Constant.DOMAINNAME);
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 更新用户信息
	  
	 * changeInfo  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月3日 下午2:03:48
	 */
	@RequestMapping("userinfo/updateInfo.htm")
	public ResponseEntity<String> changeInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "nickname", required = false) String nickname) throws Exception {
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		Map<String, Object> result = new HashMap<String, Object>();
		if ("".equals(userid)) {
			result.put("flag", "0");
			result.put("msg", "你的登陆已过期，请先登陆");
		} else if (managerservice.updateuserInfo(userid, phone, nickname)) {
			result.put("flag", "1");
		} else {
			result.put("flag", "0");
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 上传图片到服务器
	  
	 * imgupload  
	 * @param    
	 * @return  
	 * @throws Exception 
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月27日 下午3:09:39
	 */
	@RequestMapping("img/imgupload.htm")
	public ResponseEntity<String> imgupload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "slideimgFile", required = false) MultipartFile  newsimg,
			@RequestParam(value = "typename", required = false) String  typename) throws Exception {
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		Map<String, Object> result = new HashMap<String, Object>();
		if ("".equals(userid)) {
			result.put("flag", "0");
			result.put("msg", "你的登陆已过期，请先登陆");
		} else {
			//定义可上传图片格式
			List<String> imgTypeName = new ArrayList<String>();
			imgTypeName.add("jpg");
			imgTypeName.add("gif");
			imgTypeName.add("png");
			imgTypeName.add("bmp");
			String realPath = request.getSession().getServletContext().getRealPath("/");
			if (realPath.indexOf("ROOT") > 0) {
				realPath = realPath.replace("ROOT", "res-images/" + typename);
			} else {
				realPath = realPath.replace("grad-project", "res-images/" + typename);
			}
			if (newsimg != null && !newsimg.isEmpty()) {
				String fileName = newsimg.getOriginalFilename();
				String expName = (fileName.split("\\."))[1];
				if (imgTypeName.contains(expName.toLowerCase())) {
					fileName = typename  + "_" + Long.toString(System.currentTimeMillis()) + "." +expName.toLowerCase();
					try {
						FileUtils.copyInputStreamToFile(newsimg.getInputStream(), new File(realPath, fileName));
						Map<String, Object> insertres = managerservice.insertNews(typename,typename + "/" + fileName);
						if ("1".equals(insertres.get("flag"))) {
							result.put("flag", "1");
							result.put("msg", "文件上传成功");
							result.put("up_datetime", insertres.get("up_datetime"));
							result.put("imgurl", typename + "/" + fileName);
						} else {
							File file = new File(realPath + "/" + fileName);
							file.delete();
							result.put("flag", "1");
							result.put("msg", "文件上传失败");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						result.put("flag", "0");
						result.put("msg", "文件上传失败");
						e.printStackTrace();
					}
				} else {
					result.put("flag", "0");
					result.put("msg", "文件格式不符合要求");
				}
			} else {
				result.put("flag", "0");
				result.put("msg", "未选择文件");
			}
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
	/**
	 * 更新图片信息
	  
	 * insertNews  
	 * @param    
	 * @return  
	 * @throws Exception 
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月10日 上午10:06:05
	 */
	@RequestMapping("img/insertimg.htm")
	public ResponseEntity<String> insertNews(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "title", required = false) String  title,
			@RequestParam(value = "typename", required = false) String  typename,
			@RequestParam(value = "imghref", required = false) String  imghref,
			@RequestParam(value = "imgdesc", required = false) String  imgdesc,
			@RequestParam(value = "publishtime", required = false) String  publishtime,
			@RequestParam(value = "ableflag", required = false) String  ableflag,
			@RequestParam(value = "imgurl", required = false) String  imgurl) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		if ("".equals(userid)) {
			result.put("flag", "0");
			result.put("msg", "你的登陆已过期，请先登陆");
		} else if(managerservice.updateimg(title, imgdesc, imgurl, typename, imghref, ableflag, publishtime)) {
			result.put("flag", "1");
		} else {
			result.put("flag", "0");
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	/**
	 * 
	  
	 * get24Info  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月28日 上午11:25:44
	 */
	@RequestMapping("weather/get24Info.htm")
	public ResponseEntity<String> get24Info(
			@RequestParam(value = "page", required = false) String page) {
		page = page == null ? "1" : page;
		String reg = "^[0-9]+$";
		Pattern pt = Pattern.compile(reg);
		page = pt.matcher(page).find() ? page : "1";
		List<Map<String, Object>> result = indexService.get24Info(12, Integer.parseInt(page));
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	/**
	 * 
	  
	 * update24Info  
	 * @param    
	 * @return  
	 * @throws Exception 
	 * @throws 
	 * @author luocf  
	 * @date   2015年4月28日 上午11:25:49
	 */
	@RequestMapping("weather/update24Info.htm")
	public ResponseEntity<String> update24Info(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "temp", required = false) String temp,
			@RequestParam(value = "shidu", required = false) String shidu,
			@RequestParam(value = "aqi", required = false) String aqi,
			@RequestParam(value = "id", required = false) String id) throws Exception {
		TTSSession session = new TTSSession(request, response);
		String userid = null == session.getAttribute("userid") ? "" : session.getAttribute("userid") + "";
		Map<String, Object> model = new HashMap<String, Object>();
		if ("".equals(userid)) {
			model.put("flag", "0");
			model.put("msg", "你的登陆已过期，请先登陆");
		} else {
			boolean result = managerservice.updatehourInfo(temp, shidu, aqi, id);
			model.put("flag", result ? "1" : "0");
		}
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(model));
	}
}
