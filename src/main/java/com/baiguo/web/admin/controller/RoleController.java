package com.baiguo.web.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.baiguo.framework.base.BaseRestController;
import com.baiguo.web.admin.service.SystemRoleServiceI;
/**
 * 
 * @description 角色管理MVC
 * @author ldw
 * @datetime 2017年1月23日 下午3:33:56
 */
@Controller
public class RoleController extends BaseRestController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private SystemRoleServiceI roleService;
	/**
	 * 跳转到角色列表页
	 * @return
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("role:toList")//权限限制
	@RequestMapping(value="/manage/admin/role/toList", method=RequestMethod.GET)
	public String tiList(HttpServletRequest request, HttpServletResponse response) {
		return "/admin/system/role";
	}
	
	
	
	public JSONObject add() {
		return null;
	}
	
}
