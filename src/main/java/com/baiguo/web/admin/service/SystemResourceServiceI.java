package com.baiguo.web.admin.service;

import java.util.List;
import java.util.Set;

import com.baiguo.web.admin.model.SystemResource;

public interface SystemResourceServiceI {
	public Set<String> findPermissionsByUserName(String userName);
	
	public List<SystemResource> findListByUserId(Integer userId);
}
