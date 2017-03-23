package com.baiguo.framework.wechat.event;
/**
 * 微信消息推送类型
 * @author site
 *
 */
public enum MsgType {
	
	Text("text"),  //文本消息
    Image("image"),  //图片消息
    Music("music"),  //媒体消息
    Video("video"),  //视频
    Voice("voice"),  //音频
    Location("location"),  //定位消息
    Link("link"),  //链接
	Event("event"); //事件消息
    private String msgType = "";  
  
    MsgType(String msgType) {  
        this.msgType = msgType;  
    }  
  
    /** 
     * @return the msgType 
     */  
    @Override  
    public String toString() {  
        return msgType;  
    }  
}
