package com.baiguo.web.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baiguo.web.admin.dao.SystemRoleMapper;
import com.baiguo.web.admin.model.SystemRole;
import com.baiguo.web.admin.service.SystemRoleServiceI;


/**
 * 
 * @description 角色表业务层
 * @author ldw
 * @datetime 2016年12月23日 下午3:18:31
 */
@Service("roleService")
public class SystemRoleService implements SystemRoleServiceI{

	@Autowired
	private SystemRoleMapper roleMapper;
	/**
	 * 根据用户账号获取角色值
	 * @param userName
	 * @return
	 */
	@Override
	public Set<String> findRolesByUserName(String userName) {
		List<String> roles = roleMapper.selectByUserName(userName);
		Set<String> result = new HashSet<String>();
		for (String role : roles) {
			if(null!= role && !result.contains(role)) {
				result.add(role);
			}
		}
		return result;
	}
	/**
	 * 分页获取角色列表
	 */
	@Override
	public List<SystemRole> findByPage(Map<String, Object> params) {
		return roleMapper.findByPage(params);
	}
	/**
	 * 删除角色
	 */
	@Override
	public void deleteById(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 新增角色
	 */
	@Override
	public int saveRole(SystemRole role) {
		return roleMapper.insertSelective(role);
	}
	/**
	 * 更新角色数据
	 */
	@Override
	public void updateRole(SystemRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}
	/**
	 * 获取角色数量
	 */
	@Override
	public long count(Map<String, Object> params) {
		return roleMapper.count(params);
	}
	
}
