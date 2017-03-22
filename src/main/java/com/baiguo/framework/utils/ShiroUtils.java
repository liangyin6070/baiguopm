package com.baiguo.framework.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @class com.framwork.utils.ShiroUtils
 * @date 2017-3-9 下午3:28:59
 * @author Administrator
 * @description shiro工具类
 */
public class ShiroUtils {
	private static Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
	/**
	 * 获取session
	 * @return
	 */
    public static Session getSession() {
    	Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession(); 
            logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            return session;
        }  
        return null;
    }
	
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    public static void setSession(Object key, Object value){  
        Session session = getSession(); 
        if(null != session){  
            session.setAttribute(key, value);  
        }   
    }
    /**
     * 根据key获取对应值
     * @param key
     * @return
     */
    public static Object getAttribute(String key) {
    	Session session = getSession(); 
        if(null != session){  
            return session.getAttribute(key);  
        } 
        return null;
    }
}
