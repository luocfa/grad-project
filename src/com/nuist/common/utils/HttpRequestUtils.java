package com.nuist.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.nuist.common.define.Constant;

/**
 * 
 *    
 * 项目名称：grad-project   
 * 类名称：HttpRequestUtils   
 * @version
 * 类描述：
 * @version   
 * 创建人：luocf   
 * @version
 * 创建时间：2015年3月23日 下午10:20:13 
 * @version  
 * 修改人：luocf     修改时间：2015年3月23日 下午10:20:13   
 * @version
 * 修改备注：   
 *
 */
public class HttpRequestUtils {

	public static final String REQUEST_TYPE_GET = "get";
	public static final String REQUEST_TYPE_POST = "post";

	@Resource(name = "interfaceConfig")
	private Map<String, String> interfaceConfig;

	/**
	 * 基础方法
	 * request  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年1月9日 下午4:50:58
	 */
	public static String request(String url, Map<String, String> params,
			String method, String encoding) throws ClientProtocolException,
			IOException {
		String result = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;

		// GET方式请求
		if (HttpRequestUtils.REQUEST_TYPE_GET.equals(method)) {
			// 加入请求参数
			if (params != null) {
				if (url.indexOf("?") != -1) {
					url += "&";
				} else {
					url += "?";
				}
				for (String key : params.keySet()) {
					if (params.get(key) != null) {
						url += key
								+ "="
								+ URLEncoder.encode(params.get(key),
										Constant.ENCODING) + "&";
					}
				}
			}
			HttpGet httpGet = new HttpGet(url);
			httpResponse = httpClient.execute(httpGet);

			// POST方式请求
		} else if (HttpRequestUtils.REQUEST_TYPE_POST.equals(method)) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; text/html; charset="
							+ encoding);
			httpPost.addHeader("User-Agent", "Mozilla/4.0");
			// 加入请求参数
			if (params != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					if (key != null) {
						paramList.add(new BasicNameValuePair(key, params
								.get(key)));
					}
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						paramList, encoding);
				httpPost.setEntity(entity);
			}
			httpResponse = httpClient.execute(httpPost);
		}

		// 获取返回内容
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			/*InputStream is = entity.getContent();
			int l ;
			byte[] buff = new byte[1024];
			while( (l = is.read(buff)) != -1){
				result += new String(buff, 0, l, encoding);
			}
			if(is != null){
				is.close();
			}*/
			BufferedReader br = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			String s = "";
			while ((s = br.readLine()) != null) {
				result += s;
			}
			if (br != null) {
				br.close();
			}

		}
		return result;
	}

	/**
	 * get  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年1月9日 下午4:51:54
	 */
	public static String get(String url, String encoding) {
		String result = "";
		try {
			result = HttpRequestUtils.request(url, null,
					HttpRequestUtils.REQUEST_TYPE_GET, encoding);
		} catch (Exception e) {
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * post  
	 * @param    
	 * @return  
	 * @throws 
	 * @author luocf  
	 * @date   2015年1月9日 下午4:52:04
	 */
	public static String post(String url, Map<String, String> params,
			String encoding) {
		String result = "";
		try {
			result = HttpRequestUtils.request(url, params,
					HttpRequestUtils.REQUEST_TYPE_POST, encoding);
		} catch (Exception e) {
			result = e.getMessage();
		}
		return result;
	}
}