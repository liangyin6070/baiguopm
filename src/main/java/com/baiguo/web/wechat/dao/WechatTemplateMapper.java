package com.baiguo.web.wechat.dao;

import com.baiguo.web.wechat.model.WechatTemplate;

public interface WechatTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatTemplate record);

    WechatTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(WechatTemplate record);
}