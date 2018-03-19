package com.nuist.common.utils;

import java.security.MessageDigest;

/**
 * MD5加密
 *    
 * 项目名称：grad-project   
 * 类名称：MD5   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月24日 上午9:58:51 
 * @version  
 * 修改人：luocf     修改时间：2015年3月24日 上午9:58:51   
 * @version
 * 修改备注：   
 *
 */
public class MD5 {

	/**
	 *  MD5公共加密方法
	  
	 * encode  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月24日 上午9:59:18
	 */
	public final static String encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * MD5加密的解密方法
	  
	 * encode  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年3月24日 上午9:59:39
	 */
	public final static String encode(String s, String encoding) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes(encoding);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}