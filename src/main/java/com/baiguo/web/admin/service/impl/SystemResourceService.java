package com.baiguo.web.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baiguo.web.admin.dao.SystemResourceMapper;
import com.baiguo.web.admin.model.SystemResource;
import com.baiguo.web.admin.service.SystemResourceServiceI;


/**
 * 
 * @description 资源表业务层
 * @author ldw
 * @datetime 2016年12月23日 下午3:20:35
 */
@Service("resourceService")
public class SystemResourceService implements SystemResourceServiceI {

	@Autowired
	private SystemResourceMapper resourceMapper;
	/**
	 * 根据用户账号查询权限值
	 * @param userName
	 * @return
	 */
	@Override
	public Set<String> findPermissionsByUserName(String userName) {
		Set<String> result = new HashSet<String>();
		List<String> permissions = resourceMapper.findPermissionsByUserName(userName);
		for (String permission : permissions) {
			if(null != permission && !result.contains(permission)) {
				result.add(permission);
			}
		}
		return result;
	}
	/**
	 * 根据用户ID查询权限列表
	 */
	@Override
	public List<SystemResource> findListByUserId(Integer userId) {
		return resourceMapper.findListByUserId(userId);
	}
}
