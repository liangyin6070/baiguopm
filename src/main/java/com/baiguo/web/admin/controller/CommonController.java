package com.baiguo.web.admin.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;
import com.alibaba.fastjson.JSONObject;
import com.baiguo.framework.base.BaseRestController;
import com.baiguo.framework.utils.ResponseUtils;
import com.baiguo.framework.utils.WebParamUtils;
/**
 * 
 * @description 
 * @author ldw
 * @datetime 2016年12月27日 上午9:49:56
 */
@Controller
public class CommonController extends BaseRestController {
	
	private static Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@RequestMapping(value="/common/blank",method=RequestMethod.GET)
	public String toBlank() {
		return "/admin/blank";
	}
	/**
	 * 获取随机字符串
	 * @return
	 */
	@RequestMapping(value = "/common/token")
	@ResponseBody
	public String getToken(HttpServletRequest request) {
		String token = WebParamUtils.generateVerifyCode(12);
		WebUtils.setSessionAttribute(request, "token", token);
		return token;
	}
	
	/**
	 * 统一上传文件入口
	 * @param request
	 * @param response
	 * @param file
	 * @param token 随机字符串，保存在session中
	 * @param timestamp 时间戳，精确到毫秒
	 * @param signature 签名
	 */
	@RequestMapping(value="/common/upload",method=RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "signature", required = true) String signature) {
		
		
		
    	JSONObject result = new JSONObject();
		String path = request.getSession().getServletContext().getRealPath("upload");  
		String fileName = file.getOriginalFilename();  
		File targetFile = new File(path, fileName);  
		if(!targetFile.exists()){  
			targetFile.mkdirs();  
		}  
		//保存  
		try {  
			file.transferTo(targetFile); 
			result.put(SUCCESS, true);
			result.put(MSG, "文件上传成功");
			result.put("filePath", "/upload/"+fileName);
		} catch (Exception e) {  
			log.error("上传文件失败", e);
			result.put(SUCCESS, false);
			result.put(MSG, "上传文件失败");
		}  
		ResponseUtils.renderJson(response, result.toJSONString());
	}
}
