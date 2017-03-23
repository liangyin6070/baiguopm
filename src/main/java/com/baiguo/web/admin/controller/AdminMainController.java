package com.baiguo.web.admin.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.WebUtils;

import com.baiguo.framework.base.BaseRestController;
import com.baiguo.framework.utils.HttpServletUtils;
import com.baiguo.framework.utils.WebParamUtils;
import com.baiguo.web.admin.model.SystemLog;
import com.baiguo.web.admin.model.SystemResource;
import com.baiguo.web.admin.model.SystemUser;
import com.baiguo.web.admin.service.SystemLogServiceI;
import com.baiguo.web.admin.service.SystemResourceServiceI;
import com.baiguo.web.admin.service.SystemUserServiceI;
import com.baiguo.web.admin.vo.SystemResourceVo;
/**
 * 
 * @description 后台管理
 * @author ldw
 * @datetime 2016年12月21日 下午4:31:12
 */
@Controller
public class AdminMainController extends BaseRestController {
	private static Logger logger = LoggerFactory.getLogger(AdminMainController.class);
	@Autowired
	private SystemUserServiceI userService;
	@Autowired
	private SystemLogServiceI logService;
	@Autowired
	private SystemResourceServiceI resourceService;
	/**
	 * 跳转到登录页
	 * @return
	 */
	@RequestMapping(value="/manage/toLogin", method=RequestMethod.GET)
	public String toLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String refer = request.getHeader("Referer");
		if(StringUtils.isNotBlank(refer)) {
			model.addAttribute("refer", refer);
		}
		return "/admin/login";
	}
	/**
	 * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/manage/getVerifyCodeImage", method=RequestMethod.GET)
	public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
        //生成随机字串 
        String verifyCode = WebParamUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        try {
			WebParamUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			logger.error("验证码图片生成异常", e);
		} 
	}
	/**
	 * 登录验证
	 * @return
	 */
	@RequestMapping(value="/manage/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request,@RequestParam(value="username", required=true) String username, 
			@RequestParam(value="password", required=true)String password, 
			//@RequestParam(value="verifiCode", required=false)String verifiCode,
			@RequestParam(value="isRememberMe", defaultValue="false") Boolean isRememberMe,
			ModelMap model) {
		Date now = Calendar.getInstance().getTime();
		//String sessionCode = (String) WebUtils.getSessionAttribute(request, "verCode");
		SystemLog log = new SystemLog();
		log.setOperateTime(now);
		log.setRemoteAddr(HttpServletUtils.getIpAddr(request));
		log.setOperateType("LOGIN");
		
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			model.addAttribute("message_login", "账号/密码不能为空");
			
			logService.insertLog(log);
			return "/admin/login";
		} /*else if(StringUtils.isBlank(verifiCode) || StringUtils.equals(verifiCode, sessionCode)) {
			model.addAttribute("message_login", "验证码不正确");
			return "/admin/login";
		}  */
		
		//验证用户密码是否匹配
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(isRememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch(UnknownAccountException uae){  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过,未知账户");  
            model.addAttribute("message_login", "未知账户");  
        }catch(IncorrectCredentialsException ice){  
        	logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");  
            model.addAttribute("message_login", "用户名或密码不正确");  
        }catch(LockedAccountException lae){  
        	logger.error("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");  
            model.addAttribute("message_login", "账户已锁定");  
        }catch(ExcessiveAttemptsException eae){  
        	logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");  
            model.addAttribute("message_login", "用户名或密码错误次数过多");  
        }catch(AuthenticationException ae){  
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
        	logger.error("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下", ae);   
            model.addAttribute("message_login", "用户名或密码不正确");  
        } 
		
		if(subject.isAuthenticated()) {
			//如果验证通过
			String refer = request.getHeader("Refer");
			if(StringUtils.isNotBlank(refer)) {
				return InternalResourceViewResolver.REDIRECT_URL_PREFIX + refer;
			}
			return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/manage/index";
		} else {
			return "/admin/login";
		}
	}
	/**
	 * 推出登陆
	 * @return
	 */
	@RequestMapping(value="/manage/logout", method=RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/manage/toLogin";
	}
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/manage/toRegister", method=RequestMethod.GET)
	public String toRegister() {
		return "/admin/register";
	}
	/**
	 * 注册用户
	 */
	@RequestMapping(value="/manage/register", method=RequestMethod.POST)
	public void register(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
	}
	
	/**
	 * 跳转到后台首页
	 * @return
	 */
	@RequestMapping(value="/manage/index", method=RequestMethod.GET)
	public String toIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		 
		SystemUser user = (SystemUser) session.getAttribute("admin_user");
		List<SystemResource> resources = resourceService.findListByUserId(user.getId());
		
		List<SystemResourceVo> vo = createMenuTree(resources);
		
		model.addAttribute("menus", vo);
		return "/admin/NewIndex";
	}
	/**
	 * tab首页，显示系统信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/manage/blank", method=RequestMethod.GET)
	public String toBlank(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "/admin/blank";
	}
	/**
	 * 生成树形模型
	 * @param resources
	 * @return
	 */
	public List<SystemResourceVo> createMenuTree(List<SystemResource> resources) {
		List<SystemResourceVo> res = new ArrayList<SystemResourceVo>();
		for(SystemResource resource : resources) {
			if(resource.getPid() == 0) {
				SystemResourceVo vo = new SystemResourceVo();
				try {
					BeanUtils.copyProperties(vo, resource);
					recursive(resources, vo);
				} catch (IllegalAccessException e) {
					logger.error("属性不符合", e);
				} catch (InvocationTargetException e) {
					logger.error("属性不符合", e);
				}
				
				res.add(vo);
			}
		}
		return res;
	}

	public void recursive(List<SystemResource> resources, SystemResourceVo vo) throws IllegalAccessException, InvocationTargetException {
		List<SystemResourceVo> childs = new ArrayList<SystemResourceVo>();
		
		for(SystemResource resource : resources) {
			if(resource.getPid() == vo.getId()) {
				SystemResourceVo child = new SystemResourceVo();
				BeanUtils.copyProperties(child, resource);
				childs.add(child);
			}
		}
		vo.setChilds(childs);
	}
}
