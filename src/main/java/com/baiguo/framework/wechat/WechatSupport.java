package com.baiguo.framework.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.baiguo.framework.base.DateConvertEditor;
import com.baiguo.framework.wechat.common.SHA1;
import com.baiguo.framework.wechat.common.WechatConfig;
import com.baiguo.framework.wechat.jsapi.JsApiConfig;
import com.baiguo.framework.wechat.oauth2.SnsapiBaseManage;
import com.baiguo.framework.wechat.user.UserModel;
import com.baiguo.framework.wechat.utils.AttributeUils;
import com.baiguo.framework.wechat.utils.ResponseUtils;
/**
 * 
 * @class com.liudw.wxapi.WechatSupport
 * @date 2016-12-5 下午4:10:34
 * @author Administrator
 * @description 微信业务入口类——总控入口
 */
public abstract class WechatSupport {
	
	private static Logger log = LoggerFactory.getLogger(WechatSupport.class);
	public static String SUCCESS = "success";
	public static String MSG = "msg";
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
	
	/**
	 * 验证微信平台是否接入成功
	 * @param request
	 * @param response
	 */
	public void checkURL(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature"); 
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String[] str = { WechatConfig.getInstance().getToken(), timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        if(digest.equals(signature)) {
        	ResponseUtils.renderText(response, echostr);
        } 
	}
	/**
	 * 微信菜单入口URL,
	 * ##http://testwx.smart2run.cn/tour/transfer/toHtml.htm?refer=Index
	 * 根据refer判断并从redirect.properties中读取相应的URL重定向到具体业务URL
	 * @param request
	 * @param response
	 */
	public void redirectToWechat(HttpServletRequest request, HttpServletResponse response) {
		//组装重定向到微信服务器的参数
		StringBuilder urlParams = new StringBuilder();
		urlParams.append(request.getHeader("Host")).append(WechatConfig.getInstance().getCommonRedirectURI());
		Enumeration enu = request.getParameterNames();
		int i = 0;
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement();  
			String value = request.getParameter(paraName);
			if(i != 0) {
				urlParams.append("&");
			} else {
				urlParams.append("?");
			}
			urlParams.append(paraName).append("=").append(value);
			i++;
		}
		try {
			String redirect = SnsapiBaseManage.snsapiBase(urlParams.toString(), "transfer2016");
			response.sendRedirect(redirect);
		} catch (UnsupportedEncodingException e) {
			log.error("urlencode失败", e);
		} catch (IOException e) {
			log.error("重定向到页面", e);
		}
	}
	/**
	 * 接受微信重定向回来的数据，并判断用户关注与否，跳转到其他业务页面
	 * @param request
	 * @param response
	 * @param refer
	 */
	public void redirectToLocalUrl(HttpServletRequest request, HttpServletResponse response, String refer) {
		String code = request.getParameter("code");
		UserModel user = SnsapiBaseManage.getOpenIdForSnsapiBase(code);
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redirect.properties"));
			String url = null;	
			if(user.getSubscribe() == 0) {
				url= prop.getProperty("scanURL").trim();
				response.sendRedirect(url);
			} else {
				request.getSession(false).setAttribute(AttributeUils.WECHAT_USER, user);
				url = prop.getProperty(refer).trim();
				response.sendRedirect(editUrlParams(url, request));
			}
		} catch (IOException e) {
			log.error("读取配置文件失败", e);
		}
	}
	/**
	 * 创建重定向本地服务器的url
	 * @param url
	 * @param request
	 * @return
	 */
	private String editUrlParams(String url, HttpServletRequest request) {
		StringBuilder urlParams = new StringBuilder();
		urlParams.append(url);
		Enumeration enu = request.getParameterNames();
		int i = 0;
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement();  
			String value = request.getParameter(paraName);
			if(!paraName.equals("refer") && !paraName.equals("state") && !paraName.equals("code")) {
				if(i != 0) {
					urlParams.append("&");
				} else {
					urlParams.append("?");
				}
				urlParams.append(paraName).append("=").append(value);
				i++;
			}
		}
		return urlParams.toString();
	}
	
	/**
	 * 将JSSDK签名写入jsp
	 * @param now
	 * @param url
	 * @param model
	 */
	public void addJsApiParams(String url, ModelMap model) {
		JsApiConfig config = JsApiConfig.getInstance(false, url);
		model.addAttribute("signature", config.getSignature());
		model.addAttribute("timestamp", config.getTimestamp());
		model.addAttribute("nonceStr", config.getNonceStr());
		model.addAttribute("appid", config.getAppid());
	}
}
