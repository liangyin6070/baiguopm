package com.baiguo.framework.wechat.utils;
/**
 * 
 * @description 微信工具类
 * @author ldw
 * @datetime 2017年3月23日 下午2:27:10
 */
public class WechatUtils {

	/**
	 * 
	 * title: 组成xml字符串
	 * date:2016-5-4
	 * author:ldw
	 */
	public static String respXML(String toUserName, String fromUserName, Long createTime, String msgType, String content) {
		StringBuilder respXML = new StringBuilder();
        respXML.append("<xml>")
        		.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>")
        		.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>")
        		.append("<CreateTime>").append(createTime).append("</CreateTime>")
        		.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>")
        		.append("<Content><![CDATA[").append(content).append("]]></Content>")
        		.append("</xml>");
        return respXML.toString();
	}
}
