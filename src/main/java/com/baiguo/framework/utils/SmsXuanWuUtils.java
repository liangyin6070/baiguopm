package com.baiguo.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 短信平台工具类
 * @author Administrator
 *
 */
public class SmsXuanWuUtils {
	private static Logger log = LoggerFactory.getLogger(SmsXuanWuUtils.class);
	/**
	 * 短信发送
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static String send(String mobile, String content) {
		log.info("短信发送---手机："+mobile + "&&发送内容：" + content);
		String result = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://211.147.239.62:9050/cgi-bin/sendsms?username=gzrz@gzrz&password=O02gznyYTcpexvX9&to=");
			builder.append(mobile);
			builder.append("&text=");
			builder.append(URLEncoder.encode(content, "GB2312"));
			builder.append("&subid=&msgtype=1");
			String get = HttpClientUtils.sendForGET(builder.toString());
			
			result = URLDecoder.decode(get, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(send("18520147639", "测试数据"));
	}
}
