<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 微信分享js -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '<%=request.getParameter("appid")%>', // 必填，公众号的唯一标识
    timestamp: <%=request.getParameter("timestamp")%>, // 必填，生成签名的时间戳
    nonceStr: '<%=request.getParameter("nonceStr")%>', // 必填，生成签名的随机串
    signature: '<%=request.getParameter("signature")%>',// 必填，签名，见附录1
    jsApiList: [
		'checkJsApi',
		'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'onMenuShareQQ',
		'onMenuShareWeibo'
    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.ready(function () {
 // 2. 分享接口
 // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
   wx.onMenuShareAppMessage({
     title: '<%=request.getParameter("title")%>',
     desc: '<%=request.getParameter("desc")%>',
     link: '<%=request.getParameter("link")%>',
     imgUrl: '<%=request.getParameter("imgUrl")%>',
     trigger: function (res) {
   	  //用户点击发送给朋友
     },
     success: function (res) {
       	//分享成功后调用
     },
     cancel: function (res) {
       //alert('已取消');
     },
     fail: function (res) {
       //alert(JSON.stringify(res));
     }
   });

 // 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
    wx.onMenuShareTimeline({
     title: '<%=request.getParameter("title")%>',
     link: '<%=request.getParameter("link")%>',
     imgUrl: '<%=request.getParameter("imgUrl")%>',
      trigger: function (res) {
        //alert('用户点击分享到朋友圈');
      },
      success: function (res) {
        //alert('已分享');
      },
      cancel: function (res) {
       // alert('已取消');
      },
      fail: function (res) {
       // alert(JSON.stringify(res));
      }
    }); 
});

wx.error(function (res) {
  //alert(JSON.stringify(res));
});

</script>
