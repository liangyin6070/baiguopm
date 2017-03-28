package com.baiguo.web.wechat.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.baiguo.framework.utils.HttpServletUtils;
import com.baiguo.framework.utils.StatusUtils;
import com.baiguo.framework.wechat.WechatSupport;
import com.baiguo.framework.wechat.unstoppedable.common.Configure;
import com.baiguo.framework.wechat.unstoppedable.common.Signature;
import com.baiguo.framework.wechat.unstoppedable.notify.PayNotifyData;
import com.baiguo.framework.wechat.unstoppedable.notify.PayNotifyTemplate;
import com.baiguo.framework.wechat.unstoppedable.notify.PaySuccessCallBack;
import com.baiguo.framework.wechat.unstoppedable.protocol.UnifiedOrderReqData;
import com.baiguo.framework.wechat.unstoppedable.service.WxPayApi;
import com.baiguo.framework.wechat.utils.GenerateQrCodeUtils;
import com.baiguo.framework.wechat.utils.ResponseUtils;

/**
 * 微信支付后台统一处理类
 * @author Administrator
 *
 */
@Controller
public class WxApiPayController extends WechatSupport {
	private static Logger log = LoggerFactory.getLogger(WxApiPayController.class);

	/**
	 * 统一下单入口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/wechat/wxPay/unifiedOrde", method={RequestMethod.POST})
	public void unifiedOrde(HttpServletRequest request, HttpServletResponse response, String openid,
			String body, String out_trade_no, Integer total_fee) {
		log.info("调用统一下单接口："+openid);
		UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(Configure.getAppid(), Configure.getMchid(),
				body, out_trade_no, total_fee, 
                HttpServletUtils.getIpAddr(request), Configure.getNotifyUrl(), "JSAPI")
                .setOpenid(openid).build();
		try {
			Map<String, Object> resultMap = WxPayApi.UnifiedOrder(reqData);
			JSONObject resdata = new JSONObject();
			log.info("预支付返回参数："+resultMap.toString());
			if(resultMap.get("return_code").equals(StatusUtils._RETURN_CODE) && resultMap.get("result_code").equals(StatusUtils._RESULT_CODE)) {
				String localTime = Long.toString(Calendar.getInstance().getTime().getTime()/1000);
				resdata.put("timeStamp", localTime);
				resdata.put("nonceStr", resultMap.get("nonce_str"));
				resdata.put("paySign", Signature.getSign(toMap(Configure.getAppid(), localTime, (String)resultMap.get("nonce_str"), "prepay_id="+resultMap.get("prepay_id"))));
				resdata.put("appId", Configure.getAppid());
				resdata.put("package", "prepay_id="+resultMap.get("prepay_id"));
				resdata.put("success", true);
			}
			log.info("传到前台："+resdata.toString());
			ResponseUtils.renderJson(response, resdata.toString());
		} catch (IOException | SAXException | ParserConfigurationException e) {
			log.error("格式化XML失败", e);
		}
	}
	/*
	 * 组串
	 */
	private Map<String, Object> toMap(String appId, String timeStamp, String nonceStr, String package1) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signType", "MD5");
		map.put("appId", appId);
		map.put("timeStamp", timeStamp);
		map.put("nonceStr", nonceStr);
		map.put("package", package1);
		return map;
	}
	
	/**
	 * 支付成功回掉接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechat/wxPay/success")
	public void paySuccess(HttpServletRequest request, HttpServletResponse response) {
		try {
			ServletInputStream sis = request.getInputStream();
			//获取微信调用我们notify_url的返回信息
			String result = IOUtils.toString(sis, "UTF-8");
			String responseXml = new PayNotifyTemplate(result).execute(new PaySuccessCallBack() {
			    @Override
			    public void onSuccess(PayNotifyData payNotifyData) {
			    	//进行业务处理
			    	String returnCode = payNotifyData.getReturn_code();
			    	String resultCode = payNotifyData.getResult_code();
			    	log.info("支付成功调用："+payNotifyData.toString());
			    	if(returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
			    		
			 
			    	} else {
			    		log.info("本次交易失败："+payNotifyData.toString());
			    	}
			    	//如果处理失败，抛出异常
			    }
			});
			ResponseUtils.renderXml(response, responseXml);
		} catch (IOException e) {
			log.error("获取微信回掉数据失败", e);
		}
	}
	
	/**
	   * 生成二维码图片并直接以流的形式输出到页面
	   * @param code_url
	   * @param response
	   */
	  @RequestMapping("/wechat/wxPay/qr_code")
	  public void getQRCode(HttpServletRequest request, HttpServletResponse response, String code_url){
		  GenerateQrCodeUtils.encodeQrcode(code_url, response);
	  }
}
