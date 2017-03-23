package com.baiguo.web.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.baiguo.web.wechat.dao.WechatTemplateMapper;
import com.baiguo.web.wechat.service.WechatTemplateServiceI;

@Service("wxTemplateService")
public class WechatTemplateServiceImpl implements WechatTemplateServiceI {

	private WechatTemplateMapper wxTemplateMapper;
}
