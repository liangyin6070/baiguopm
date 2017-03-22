package com.baiguo.web.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baiguo.web.admin.model.SystemRole;
/**
 * 
 * @description 角色业务层接口
 * @author ldw
 * @datetime 2017年1月23日 下午4:07:01
 */
public interface SystemRoleServiceI {
	
	void deleteById(Integer id);
	
	int saveRole(SystemRole role);
	
	void updateRole(SystemRole role);
	
	/**
	 * 根据用户账号获取角色值
	 * @param userName
	 * @return
	 */
	Set<String> findRolesByUserName(String userName);
	/**
	 * 分页获取角色列表
	 * @param params
	 * @return
	 */
	List<SystemRole> findByPage(Map<String, Object> params);
	
	long count(Map<String, Object> params);
}
