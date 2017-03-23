package com.baiguo.web.wechat.service;

import com.baiguo.web.wechat.model.WechatUser;

public interface WechatUserServiceI {

	WechatUser selectUserByOpenId(String openid);

	void updateUser(WechatUser user);

	void insertUser(WechatUser user);

}
