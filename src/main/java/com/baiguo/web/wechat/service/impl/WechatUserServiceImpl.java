package com.baiguo.web.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiguo.web.wechat.dao.WechatUserMapper;
import com.baiguo.web.wechat.model.WechatUser;
import com.baiguo.web.wechat.service.WechatUserServiceI;
/**
 * 
 * @description 
 * @author ldw
 * @datetime 2017年3月23日 上午11:50:56
 */
@Service("wxUserService")
public class WechatUserServiceImpl implements WechatUserServiceI {

	@Resource
	private WechatUserMapper wxUserMapper;
	
	@Override
	public WechatUser selectUserByOpenId(String openid) {
		List<WechatUser> list = wxUserMapper.selectListByOpenid(openid);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public void updateUser(WechatUser user) {
		wxUserMapper.updateByPrimaryKey(user);
	}

	@Override
	public void insertUser(WechatUser user) {
		wxUserMapper.insert(user);
	}

}
