package com.baiguo.web.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baiguo.framework.base.BaseRestController;
import com.baiguo.web.admin.model.SystemUser;
import com.baiguo.web.admin.service.SystemUserServiceI;

/**
 * 
 * @description 用户管理MVC
 * @author ldw
 * @datetime 2016年12月23日 下午3:59:41
 */
@Controller
public class UserController extends BaseRestController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private SystemUserServiceI userService;
	/**
	 * 跳转到用户列表页
	 * @return
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("user:toList")//权限限制
	@RequestMapping(value="/manage/admin/user/toList", method=RequestMethod.GET)
	public String toList() {
		return "/admin/system/user";
	}
	/**
	 * 异步获取用户列表信息
	 * @param request
	 * @param response
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("user:ajaxList")//权限限制
	@RequestMapping(value="/manage/admin/user/ajaxList", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject ajaxList(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="page", required=true, defaultValue="1") Integer pageNo, 
			@RequestParam(value="rows", required=true, defaultValue="20") Integer pageSize) {
		JSONObject result = new JSONObject();
		SystemUser user = new SystemUser();
		user.setPageNo(pageNo);
		user.setPageSize(pageSize);
		
		List<SystemUser> users = userService.selectPage(user);
		result.put("total", user.getTotal());
		result.put("rows", (JSONArray)JSONArray.toJSON(users));
		return result;
	}
	/**
	 * 新增用户
	 * @param request
	 * @param response
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("user:add")//权限限制
	@RequestMapping(value="/manage/admin/user/add", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addUser(HttpServletRequest request, HttpServletResponse response, SystemUser user) {
		JSONObject result = new JSONObject();
		try {
			userService.insertUser(user);
			result.put("success",true);
			result.put("msg", "用户新增成功");
		} catch (Exception e) {
			logger.error("新增用户失败", e);
		}
		return result;
	}
	/**
	 * 删除用户
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("user:delete")//权限限制
	@RequestMapping(value="/manage/admin/user/delete", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteUser(HttpServletRequest request, HttpServletResponse response, Integer id) {
		JSONObject result = new JSONObject();
		try {
			userService.deleteById(id);
			result.put("success",true);
			result.put("msg", "用户新增成功");
		} catch (Exception e) {
			logger.error("新增用户失败", e);
		}
		return result;
	}
	/**
	 * 编辑用户
	 */
	@RequiresRoles("admin")//超级管理员角色
	@RequiresPermissions("user:edit")//权限限制
	@RequestMapping(value="/manage/admin/user/edit", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject editUser(HttpServletRequest request, HttpServletResponse response, SystemUser user) {
		JSONObject result = new JSONObject();
		try {
			userService.editUser(user);
			result.put("success",true);
			result.put("msg", "用户新增成功");
		} catch (Exception e) {
			logger.error("新增用户失败", e);
		}
		return result;
	}
}
