package com.baiguo.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Title:HttpClientUtils Description: http工具类 Company:jiahe
 * 
 * @author ldw
 * @date 2016-4-18
 */
public class HttpClientUtils {
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void downloadFileToDir(String url, String fileName, String dirName) {
		 // 使用get方法连接服务器  
        HttpGet httpGet = new HttpGet(url);  
        HttpClient client = new DefaultHttpClient();  
        InputStream inputStream = null;
        FileOutputStream fos = null;  
        try {  
            // 客户端开始向指定的网址发送请求  
            HttpResponse response = client.execute(httpGet);  
            inputStream = response.getEntity().getContent();  
            File file = new File(dirName);  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            fos = new FileOutputStream(dirName+File.separator+fileName);  
            IOUtils.copy(inputStream, fos);       
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	IOUtils.closeQuietly(fos);
        	IOUtils.closeQuietly(inputStream);
        }
	}
	
	/**
	 * get提交
	 * 
	 * @param url
	 * @return
	 */
	public static String sendForGET(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		String result = null;
		HttpEntity entity = null;
		try {
			CloseableHttpResponse resp = client.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity,
						HttpServletUtils.ENCODING_UTF8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}
	/**
	 * post提交string字符串
	 * @param url
	 * @param data
	 * @return
	 */
	public static String postString(String url, String data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String result = null;
		HttpEntity entity = null;
		try {
			if (data != null) {
				StringEntity se = new StringEntity(data, HttpServletUtils.ENCODING_UTF8);
				se.setContentEncoding(HttpServletUtils.ENCODING_UTF8);
				//se.setContentType(HttpServletUtils.TYPE_JSON);
				post.setEntity(se);
			}
			CloseableHttpResponse resp = client.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity, HttpServletUtils.ENCODING_UTF8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}
	
	/**
	 * post提交
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String sendForPOST(String url, JSONObject data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String result = null;
		HttpEntity entity = null;
		try {
			if (data != null) {
				StringEntity se = new StringEntity(data.toString(),
						HttpServletUtils.ENCODING_UTF8);
				se.setContentEncoding(HttpServletUtils.ENCODING_UTF8);
				se.setContentType(HttpServletUtils.TYPE_JSON);
				post.setEntity(se);
			}
			CloseableHttpResponse resp = client.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity,
						HttpServletUtils.ENCODING_UTF8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}

	/**
	 * 
	 * title: 模拟表单提交 date:2016-5-6 author:ldw
	 */
	public static String sendForPostByForm(String url,
			Map<String, String> params) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String result = null;
		HttpEntity entity = null;
		List<NameValuePair> nvps = null;
		try {
			if (params != null) {
				nvps = new ArrayList<NameValuePair>();
				Iterator<String> itr = params.keySet().iterator();
				while (itr.hasNext()) {
					String key = itr.next();
					nvps.add(new BasicNameValuePair(key, params.get(key)));// 添加表单数据
				}
				post.setEntity(new UrlEncodedFormEntity(nvps,
						HttpServletUtils.ENCODING_UTF8));
			}
			CloseableHttpResponse resp = client.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity,
						HttpServletUtils.ENCODING_UTF8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}

	/**
	 * 
	 * title: GET提交，返回json对象 date:2016-4-18 author:ldw
	 */
	public static JSONObject jsonGET(String url) {
		String result = sendForGET(url);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return null;
	}

	/**
	 * 
	 * title: POST提交，返回json对象 date:2016-4-18 author:ldw
	 */
	public static JSONObject jsonPOST(String url, JSONObject data) {
		String result = sendForPOST(url, data);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return null;
	}

	public static JSONObject jsonPostByForm(String url,
			Map<String, String> params) {
		String result = sendForPostByForm(url, params);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return null;
	}

	public static String appPost(String url, Map<String, String> parameters) {
		URL uri = null;
		HttpURLConnection conn = null;
		String result = null;
		try {
			uri = new URL(url);
			conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						"UTF-8"));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}
			byte[] b = params.toString().getBytes();
			conn.getOutputStream().write(b, 0, b.length);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			result = IOUtils.toString(conn.getInputStream(), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();
				conn = null;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		String s = "{\"button\": [{\"name\": \"学能测评\", \"sub_button\": [{\"type\": \"view\", \"name\": \"幼升小测评\", \"url\": \"http://liudw2.6655.la/wechats/wechat/toHtml.htm\", \"sub_button\": [ ]},{\"type\": \"view\", \"name\": \"找回结果\", \"url\": \"http://liudw2.6655.la/wechats/wechat/toResult.htm\", \"sub_button\": [ ]},{\"type\": \"view\", \"name\": \"免费解读\", \"url\": \"http://liudw2.6655.la/wechats/wechat/toOrder.htm\", \"sub_button\": [ ]}]}]}";
		System.out.println(jsonPOST(
				"http://liudw2.6655.la/wechats/wx/addMenu.htm",
				JSONObject.parseObject(s)).toJSONString());
	}
}
