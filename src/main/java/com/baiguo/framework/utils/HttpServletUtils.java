package com.baiguo.framework.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装常用的http方法
 * @author 刘德伟
 *
 */
public class HttpServletUtils {
	private static Logger log = LoggerFactory.getLogger(HttpServletUtils.class);
	
	public static final String _USER_TEST = "user_test";
	
	/*
	 * token值
	 */
	public static final String TOEKN = "token";
	/**
	 * 保存微信用户的session键名
	 */
	public static final String SESSION_USER = "web_user";
	/**
	 * 保存微信授权oauth2的键名
	 */
	public static final String OAUTH2 = "oauth2";
	/**
	 * 返回数据的格式类型json
	 */
	public static final String TYPE_JSON = "application/json";
	/**
	 * 返回数据的格式类型html
	 */
	public static final String TYPE_HTML = "text/html";
	/**
	 * 返回数据的格式类型xml
	 */
	public static final String TYPE_XML = "text/xml";
	/**
	 * 返回数据的格式类型plain
	 */
	public static final String TYPE_PLAIN = "text/plain";
	/**
	 * 字符编码UTF8
	 */
	public static final String ENCODING_UTF8 = "UTF-8";
	/**
	 * 字符编码GBK
	 */
	public static final String ENCODING_GBK = "GBK";
	
	/*
	 * 获取提交的参数
	 */
	public static Object getObject(HttpServletRequest request, String keyName) {
		if(request.getAttribute(keyName) != null) {
			return request.getAttribute(keyName);
		} else if(request.getParameter(keyName) != null) {
			return request.getParameter(keyName);
		} else if(getSession(request).getAttribute(keyName) != null) {
			return getSession(request).getAttribute(keyName);
		}
		return null;
	}
	
	/*
	 * 获取session
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}
	/**
	 * 设置session的key-value缓存对象
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void setSessionObject(HttpServletRequest request, String key, Object obj) {
		getSession(request).setAttribute(key, obj);
	}
	/**
	 * 获取session的key-value缓存对象
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSessionObject(HttpServletRequest request, String key) {
		return getSession(request).getAttribute(key);
	}
	/**
	 * 
	 * title: 移除session的key-value对象
	 * date:2016-4-22
	 * author:ldw
	 */
	public static void removeSessionObject(HttpServletRequest request, String key) {
		getSession(request).removeAttribute(key);
	}
	
	/**
	 * 返回数据基本方法
	 * @param resp
	 * @param encoding 编码格式
	 * @param contentType 返回数据类型
	 * @param data 返回数据
	 * @throws IOException
	 */
	public static void renfer(HttpServletResponse resp, String encoding, String contentType, String data) throws IOException {
		resp.setCharacterEncoding(encoding);
		resp.setContentType(contentType);
		PrintWriter out = resp.getWriter();
		out.write(data);
		out.flush();
		out.close();
	}
	/**
	 * 返回json格式数据
	 * @param resp
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void renferJson(HttpServletResponse resp, String data, String encoding) throws IOException {
		if(StringUtils.isNotBlank(encoding)) {
			renfer(resp, encoding ,TYPE_JSON, data);
		} else {
			renfer(resp, ENCODING_UTF8 ,TYPE_JSON, data);
		}
	}
	/**
	 * 默认使用UTF-8编码，返回json格式数据
	 * @param resp
	 * @param data
	 */
	public static void referJsonForUTF8(HttpServletResponse resp, String data) {
		try {
			renferJson(resp, data, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回html格式数据
	 * @param resp
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void referHtml(HttpServletResponse resp, String data, String encoding) throws IOException {
		if(StringUtils.isNotBlank(encoding)) {
			renfer(resp, encoding ,TYPE_HTML, data);
		} else {
			renfer(resp, ENCODING_UTF8 ,TYPE_HTML, data);
		}
	}
	/**
	 * 默认以UTF8格式返回html
	 * @param resp
	 * @param data
	 */
	public static void referHtmlForUTF8(HttpServletResponse resp, String data) {
		try {
			referHtml(resp, data, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void referPlain(HttpServletResponse resp, String data, String encoding) throws IOException {
		if(StringUtils.isNotBlank(encoding)) {
			renfer(resp, encoding ,TYPE_PLAIN, data);
		} else {
			renfer(resp, ENCODING_UTF8 ,TYPE_PLAIN, data);
		}
	}
	/**
	 * 默认UTF8返回plain格式数据
	 * @param resp
	 * @param data
	 */
	public static void referPlainForUTF8(HttpServletResponse resp, String data) {
		try {
			referPlain(resp, data, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户访问url以及参数
	 * @param request
	 * @return
	 */
	public static String getLocalUrl(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		if(StringUtils.isNotBlank(request.getQueryString())) {
			url.append(request.getRequestURI()).append("?").append(request.getQueryString());
		} else {
			url.append(request.getRequestURI());
		}
		return url.toString();
	}
	
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	/**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public  Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }   
    }
      
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    public static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    /**
     * 删除cookie
     * @param name
     * @param request
     * @param resposne
     */
    public static void delCookie(String name, HttpServletRequest request, HttpServletResponse response) {
    	Cookie[] cookies = request.getCookies();
        if (null==cookies) {
        	log.info("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    log.info("被删除的cookie名字为:"+cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
    /**
     * 删除所有cookie
     * @param request
     * @param resposne
     */
    public static void delAllCookies(HttpServletRequest request, HttpServletResponse response) {
    	Cookie[] cookies = request.getCookies();
        if (null==cookies) {
        	log.info("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");        
                response.addCookie(cookie);
                break;
            }
        }
    }
    
    /**
     * 编辑cookie
     * @param request
     * @param response
     * @param name
     * @param value
     */
    public void editCookie(HttpServletRequest request,HttpServletResponse response,String name,String value){
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            log.info("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                	log.info("原值为:"+cookie.getValue());
                    cookie.setValue(value);
                    cookie.setPath("/");
                    cookie.setMaxAge(30 * 60);// 设置为30min
                    log.info("被修改的cookie名字为:"+cookie.getName()+",新值为:"+cookie.getValue());
                    response.addCookie(cookie);
                    break;
                }
            }
        } 
    }
    /**
     * 添加cookie
     * @param response
     * @param name
     * @param value
     * @param second 生存秒数
     */
    public void addCookie(HttpServletResponse response, String name, String value, int second){
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setMaxAge(second);// 设置存在秒数
        cookie.setPath("/");
        log.info("已添加===============");
        response.addCookie(cookie);
    }
}
