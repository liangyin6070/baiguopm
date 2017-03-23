package com.baiguo.web.common.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baiguo.framework.base.BaseRestController;
import com.baiguo.framework.utils.JedisUtils;
import com.baiguo.framework.utils.ResponseUtils;
import com.baiguo.framework.utils.SmsXuanWuUtils;
import com.baiguo.framework.utils.StatusUtils;
import com.baiguo.web.wechat.model.WechatUser;
import com.baiguo.web.wechat.service.WechatUserServiceI;


/**
 * 短信平台MVC
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("")
public class SmsController extends BaseRestController {

	private static Logger log = LoggerFactory.getLogger(SmsController.class);

	@Resource
	private WechatUserServiceI wxUserService;

	/**
	 * 发送短信
	 * 
	 * @param reqeust
	 * @param response
	 * @param model
	 * @param userId
	 */
	@RequestMapping("/sms/sendSms")
	public void sendSms(HttpServletRequest reqeust, HttpServletResponse response,
			ModelMap model, Integer userId, String phone) {
		JSONObject res = new JSONObject();
		if(StringUtils.isBlank(phone)) {
			res.element("success", false);
			res.element("msg", "手机号不能为空");
			ResponseUtils.renderJson(response, res.toString());
		} else {
			Date now = Calendar.getInstance().getTime();

			String num = String.valueOf(ramSix());
			JedisUtils.setValueForExpire(userId+"_phone", num, StatusUtils._CHECK_TIME_OUT);
			
			StringBuilder builder = new StringBuilder();
			builder.append("请输入验证码").append(num)
					.append("完成手机验证(10分钟内有效)。如非本人操作请忽略。");
			
			String code = SmsXuanWuUtils.send(phone, builder.toString());//调用玄武短信平台发送短信
			
			try {
				if ("0".equals(code)) {
					res.element("success", true);
				} else {
					res.element("success", false);
					res.element("msg", "发送失败，请稍后重试");
				}	
			} catch(Exception e) {
				res.element("success", false);
				res.element("msg", "发送失败，请稍后重试");
				log.error("", e);
			} finally {
				ResponseUtils.renderJson(response, res.toString());
			}
		}
	}
	/**
	 * 生成6位验证码
	 * @return
	 */
	public int ramSix() {
		return (int) ((Math.random() * 9 + 1) * 100000);
	}
}
