package com.baiguo.web.common.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baiguo.framework.bankunion.demo.DemoBase;
import com.baiguo.framework.bankunion.sdk.AcpService;
import com.baiguo.framework.bankunion.sdk.LogUtil;
import com.baiguo.framework.bankunion.sdk.SDKConfig;
import com.baiguo.framework.bankunion.sdk.SDKConstants;
import com.baiguo.framework.base.BaseRestController;
/**
 * 银联支付接口
 * @author Administrator
 *
 */
@Controller
public class BankUnionPayController extends BaseRestController {
	private static Logger log = LoggerFactory.getLogger(BankUnionPayController.class);

	/**
	 * 重要：联调测试时请仔细阅读注释！
	 * 
	 * 产品：跳转网关支付产品<br>
	 * 交易：消费：前台跳转，有前台通知应答和后台通知应答<br>
	 * 日期： 2015-09<br>
	 * 版本： 1.0.0
	 * 版权： 中国银联<br>
	 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码性能规范性等方面的保障<br>
	 * 提示：该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《网关支付产品接口规范》，<br>
	 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表)<br>
	 *              《全渠道平台接入接口规范 第3部分 文件接口》（对账文件格式说明）<br>
	 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
	 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
	 *                             测试过程中产生的6位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
	 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
	 * 交易说明:1）以后台通知或交易状态查询交易确定交易成功,前台通知不能作为判断成功的标准.
	 *       2）交易状态查询交易（Form_6_5_Query）建议调用机制：前台类交易建议间隔（5分、10分、30分、60分、120分）发起交易查询，如果查询到结果成功，则不用再查询。（失败，处理中，查询不到订单均可能为中间状态）。也可以建议商户使用payTimeout（支付超时时间），过了这个时间点查询，得到的结果为最终结果。
	 */
	@RequestMapping("/bankUnion/frontConsume")
	public void frontConsume(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@RequestParam(value="txnAmt", required=true) String txnAmt,
			@RequestParam(value="orderId", required=true) String orderId) {
		//前台页面传过来的
		//String merId = request.getParameter("merId");
		//String txnAmt = request.getParameter("txnAmt");
		
		Map<String, String> requestData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		requestData.put("version", DemoBase.version);   			  //版本号，全渠道默认值
		requestData.put("encoding", DemoBase.encoding_UTF8); 			  //字符集编码，可以使用UTF-8,GBK两种方式
		requestData.put("signMethod", "01");            			  //签名方法，只支持 01：RSA方式证书加密
		requestData.put("txnType", "01");               			  //交易类型 ，01：消费
		requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
		requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
		requestData.put("channelType", "07");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
		
		/***商户接入参数***/
		requestData.put("merId", SDKConfig.getConfig().getMerId());    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		
		requestData.put("orderId", orderId);             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", DemoBase.getCurrentTime());        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
		requestData.put("txnAmt", txnAmt);             			      //交易金额，单位分，不要带小数点
		//requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
		
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		requestData.put("frontUrl", SDKConfig.getConfig().getFrontUrl());
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		requestData.put("backUrl", SDKConfig.getConfig().getBackUrl());
		
		//////////////////////////////////////////////////
		//
		//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
		//
		//////////////////////////////////////////////////
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> submitFromData = AcpService.sign(requestData,DemoBase.encoding_UTF8);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, DemoBase.encoding_UTF8);   //生成自动跳转的Html表单
		
		log.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
		//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
		try {
			response.getWriter().write(html);
		} catch (IOException e) {
			log.error("网络IO异常", e);
		}
	}
	/**
	 * 重要：联调测试时请仔细阅读注释！
	 * 
	 * 产品：跳转网关支付产品<br>
	 * 功能：前台通知接收处理示例 <br>
	 * 日期： 2015-09<br>
	 * 版本： 1.0.0 
	 * 版权： 中国银联<br>
	 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
	 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《网关支付产品接口规范》，<br>
	 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表），
	 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
	 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
	 *                             测试过程中产生的6位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
	 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
	 * 交易说明：支付成功点击“返回商户”按钮的时候出现的处理页面示例
	 */
	@RequestMapping("/bankUnion/frontRcvResponse")
	public String frontRcvResponse(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		String encoding = request.getParameter(SDKConstants.param_encoding);
		//String pageResult = "";
//		if (DemoBase.encoding_UTF8.equalsIgnoreCase(encoding)) {
//			pageResult = "/unionpaydemo/utf8_result.jsp";
//		} else {
//			pageResult = "/unionpaydemo/gbk_result.jsp";
//		}
		Map<String, String> respParam = getAllRequestParam(request);

		// 打印请求报文
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes(encoding), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!AcpService.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			LogUtil.writeLog("验证签名结果[失败].");
			model.addAttribute("status", 0);
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			LogUtil.writeLog("验证签名结果[成功].");
			String orderId = valideData.get("orderId"); //其他字段也可用类似方式获取
			model.addAttribute("orderNum", orderId.substring(0, 18));
			model.addAttribute("status", 1);
		}
//		request.setAttribute("result", page.toString());
//		try {
//			request.getRequestDispatcher(pageResult).forward(request, response);
//		} catch (ServletException e) {
//			log.error("网络IO异常", e);
//		} catch (IOException e) {
//			log.error("网络IO异常", e);
//		}
		return "/mobile/commodityNew/payResultWechatUnion";
		//LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
	}
	/**
	 * 重要：联调测试时请仔细阅读注释！
	 * 
	 * 产品：跳转网关支付产品<br>
	 * 功能：后台通知接收处理示例 <br>
	 * 日期： 2015-09<br>
	 * 版本： 1.0.0 
	 * 版权： 中国银联<br>
	 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
	 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《网关支付产品接口规范》，<br>
	 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表），
	 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
	 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
	 *                             测试过程中产生的6位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
	 *                           2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
	 * 交易说明：成功的交易才会发送后台通知，建议此交易与交易状态查询交易结合使用确定交易是否成功
	 */
	@RequestMapping("/bankUnion/backRcvResponse")
	public void backRcvResponse(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
		
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes(encoding), encoding);
				} catch (UnsupportedEncodingException e1) {
					log.error("不支持字符格式编辑", e1);
				}
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			
			String orderId = valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			String respCode = valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
			String txnAmt = valideData.get("txnAmt");//金额
//			if(respCode.equals("00")) {
				
//		    }
			
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		try {
			response.getWriter().print("ok");
		} catch (IOException e) {
			log.error("网络IO异常", e);
		}
	}
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
