<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 微信支付统一入口 -->
<!-- <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> -->

<script type="text/javascript">
var orderId = "<%=request.getParameter("orderId")%>";
var configData;
$(function(){
	var openid = "<%=request.getParameter("openid")%>";
	var body = "<%=request.getParameter("body")%>";
	var outTradeNo = "<%=request.getParameter("out_trade_no")%>";
	var totalFee = "<%=request.getParameter("total_fee")%>";
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/wxPay/unifiedOrde.htm",
		dataType: "json",
        data:{openid:openid, body:body, out_trade_no:outTradeNo, total_fee:totalFee},
		success:function(resdata) {
			if(resdata.success) {
				configData = {
						"appId":resdata.appId,     //公众号名称，由商户传入     
				        "timeStamp":resdata.timeStamp,         //时间戳，自1970年以来的秒数     
				        "nonceStr":resdata.nonceStr, //随机串     
				        "package": resdata.package,     
				        "signType":"MD5",         //微信签名方式：     
				        "paySign": resdata.paySign //微信签名 	
					};
			} else {
				alert("生成交易订单失败");
			}
		},
		error: function(resData) {
			alert("无法访问");
		}
	});
});
/*
 * {
     "appId" ： "wx2421b1c4370ec43b",     //公众号名称，由商户传入     
     "timeStamp"：" 1395712654",         //时间戳，自1970年以来的秒数     
     "nonceStr" ： "e61463f8efa94090b1f366cccfbbb444", //随机串     
     "package" ： "prepay_id=u802345jgfjsdfgsdg888",     
     "signType" ： "MD5",         //微信签名方式：     
     "paySign" ： "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
 }
 */

function onBridgeReady(){
   WeixinJSBridge.invoke('getBrandWCPayRequest', configData,
       function(res){     
	   //alert(res.err_code+res.err_desc+res.err_msg);
           if(res.err_msg == "get_brand_wcpay_request:ok") {
            	// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            	window.location.href = "${pageContext.request.contextPath}/commodityPay/toPayResultWechat.htm?status=1&orderId="+orderId;
           } else if(res.err_msg == "get_brand_wcpay_request:cancel" || res.err_msg == "get_brand_wcpay_request:fail") {
        	   	//alert(res.err_code+res.err_desc+res.err_msg);
        	   	window.location.href = "${pageContext.request.contextPath}/commodityPay/toPayResultWechat.htm?status=0&orderId="+orderId;
           }     
       }
   ); 
}
/*支付方法*/
function callPay() {
	if(typeof(WeixinJSBridge) == "undefined"){
	   if(document.addEventListener){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if(document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	} else{
	   onBridgeReady();
	} 
}

</script>

