package com.baiguo.framework.base;

import java.util.Date;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 基础controller抽象类
 * 
 * @author 刘德伟
 * 
 */
public abstract class BaseRestController {
	
	public static String SUCCESS = "success";
	public static String MSG = "msg";
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
}
