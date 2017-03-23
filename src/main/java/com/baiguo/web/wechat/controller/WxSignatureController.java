package com.baiguo.web.wechat.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.baiguo.framework.utils.ResponseUtils;
import com.baiguo.framework.utils.StatusUtils;
import com.baiguo.framework.wechat.WechatSupport;
import com.baiguo.framework.wechat.event.InputMessage;
import com.baiguo.framework.wechat.event.MsgType;
import com.baiguo.framework.wechat.user.UserManage;
import com.baiguo.framework.wechat.user.UserModel;
import com.baiguo.framework.wechat.utils.HttpChartsetUtils;
import com.baiguo.framework.wechat.utils.WechatUtils;
import com.baiguo.web.wechat.model.WechatUser;
import com.baiguo.web.wechat.service.WechatTemplateServiceI;
import com.baiguo.web.wechat.service.WechatUserServiceI;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
public class WxSignatureController extends WechatSupport {

	private static Logger logger = LoggerFactory.getLogger(WxSignatureController.class);
	@Resource
	private WechatUserServiceI wxUserService;
	@Resource
	private WechatTemplateServiceI wxTemplateService;
	/**
	 * 验证返回的signature是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/wx/signature", method = { RequestMethod.GET, RequestMethod.POST })
	public void signature(HttpServletRequest request, HttpServletResponse response) {
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if(isGet) {
			//进入验证
	        checkURL(request, response);
		} else {
			//进入消息推送
			acceptMessage(request, response);
		}
	}
	
	/**
	 * 
	 * title:处理接收消息
	 * date:2016-4-21
	 * author:ldw
	 */
	public void acceptMessage(HttpServletRequest request, HttpServletResponse response) {
		String requestXML = null;
		try {
			ServletInputStream sis = request.getInputStream();
			requestXML = IOUtils.toString(sis, HttpChartsetUtils.CHARSET_UTF8);
		} catch (IOException e) {
			logger.error("处理微信事件信息异常", e);
		}
		Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
		Date now = Calendar.getInstance().getTime();
		//将POST流转换为XStream对象  
        XStream xs = new XStream(new DomDriver());  
        //将指定节点下的xml节点数据映射为对象  
        xs.alias("xml", InputMessage.class);  
        //将xml内容转换为InputMessage对象  
        InputMessage inputMsg = (InputMessage) xs.fromXML(requestXML);  
        // 取得消息类型  
        String msgType = inputMsg.getMsgType();  
       
        //根据消息类型获取对应的消息内容  
        if (msgType.equals(MsgType.Event.toString())) { 
        	//事件
        	follow(inputMsg, now, returnTime, response);//处理关注事件
        } else if(msgType.equals(MsgType.Text.toString())) {
        	//文本消息
        	
        } else if(msgType.equals(MsgType.Image.toString())) {
        	//图片
        	
        } else if(msgType.equals(MsgType.Music.toString())) {
        	
        } else if(msgType.equals(MsgType.Video.toString())) {
        	
        } else if(msgType.equals(MsgType.Voice.toString())) {
        	
        } 
	}
	
	/**
	 * 
	 * title: 处理关注事件
	 * date:2016-5-4
	 * author:ldw
	 */
	public void follow(InputMessage inputMsg, Date now, Long returnTime, HttpServletResponse response) {
		WechatUser user = wxUserService.selectUserByOpenId(inputMsg.getFromUserName());
    	if(null != user && inputMsg.getEvent().equals(StatusUtils._WX_EVENT_UNSUBSCRIBE)) {
    		//取消关注公众号
    		user.setSubscribe(0);
    		wxUserService.updateUser(user);
    	} else {
    		//关注公众号 
    		UserModel userItem = UserManage.getUserInfo(inputMsg.getFromUserName());
    		if(user == null) {//如果本地数据库未存在，则加入到数据库中
            	user = new WechatUser();    
				try {
					BeanUtils.copyProperties(user, userItem);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
            	wxUserService.insertUser(user);
            } else if(user.getSubscribe() == 0){//如果本地数据库已存在，则更新数据
				user.setSubscribe(1);//1-已关注
				wxUserService.updateUser(user);
            } 
    		String msgResult = WechatUtils.respXML(inputMsg.getFromUserName(), inputMsg.getToUserName(), returnTime, MsgType.Text.toString(), "欢迎关注百果生态园");
    		ResponseUtils.renderText(response, msgResult);
    	}
	}
}
