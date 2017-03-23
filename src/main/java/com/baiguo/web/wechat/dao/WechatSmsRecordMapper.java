package com.baiguo.web.wechat.dao;

import com.baiguo.web.wechat.model.WechatSmsRecord;

public interface WechatSmsRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatSmsRecord record);

    int insertSelective(WechatSmsRecord record);

    WechatSmsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatSmsRecord record);

    int updateByPrimaryKey(WechatSmsRecord record);
}