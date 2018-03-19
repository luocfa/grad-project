package com.nuist.common.utils;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nuist.common.define.Constant;


/**
 * SpringMVC返回结果封装类
 *    
 * 项目名称：grad-project   
 * 类名称：SpringMVCResult   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年4月10日 上午11:02:51 
 * @version  
 * 修改人：luocf     修改时间：2015年4月10日 上午11:02:51   
 * @version
 * 修改备注：   
 *
 */
public class SpringMVCResult {
	
	/**
	 * 字符串类型
	 * 
	 * @param result
	 * @return
	 */
	public static ResponseEntity<String> returnResponseEntity(String result){
		return returnResponseEntity(result, "text", "html");
	}
	
	/**
	 * 字符串类型
	 * 
	 * @param result 返回内容
	 * @param type 如：text
	 * @param subtype 如：html
	 * @return
	 */
	public static ResponseEntity<String> returnResponseEntity(String result,String type, String subtype){
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = new MediaType(type, subtype, Charset.forName(Constant.ENCODING));
		headers.setContentType(mt);
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	}
}
