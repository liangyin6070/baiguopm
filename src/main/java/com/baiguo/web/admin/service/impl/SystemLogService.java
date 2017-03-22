package com.baiguo.web.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baiguo.web.admin.dao.SystemLogMapper;
import com.baiguo.web.admin.model.SystemLog;
import com.baiguo.web.admin.service.SystemLogServiceI;



/**
 * 
 * @description 
 * @author ldw
 * @datetime 2016年12月23日 下午4:08:13
 */
@Service("logService")
public class SystemLogService implements SystemLogServiceI{

	@Autowired
	private SystemLogMapper logMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int insertLog(SystemLog log) {
		return logMapper.insertSelective(log);
	}
}
