package com.baiguo.web.wechat.dao;

import java.util.List;

import com.baiguo.web.wechat.model.WechatUser;
/**
 * 
 * @description 
 * @author ldw
 * @datetime 2017年3月23日 下午2:06:43
 */
public interface WechatUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);

	List<WechatUser> selectListByOpenid(String openid);
}