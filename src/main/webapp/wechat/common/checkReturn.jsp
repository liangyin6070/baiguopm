<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 安卓webview 后退强制刷新解决方案 START -->
<jsp:useBean id="now" class="java.util.Date" />
<input type="hidden" id="SERVER_TIME" value="${now.getTime()}"/>
<script>
//每次webview重新打开H5首页，就把server time记录本地存储
var SERVER_TIME = document.getElementById("SERVER_TIME"); 
var REMOTE_VER = SERVER_TIME && SERVER_TIME.value;
var returnUriType = <%=request.getParameter("returnUriType")%>;
var testId = <%=request.getParameter("testId")%>;
if(REMOTE_VER){
    var LOCAL_VER = sessionStorage && sessionStorage.PAGEVERSION;
    if(LOCAL_VER && parseInt(LOCAL_VER) >= parseInt(REMOTE_VER)){
        //说明html是从本地缓存中读取的
        //location.reload(true);
        if(returnUriType == 1000) {
        	window.location.href = "${pageContext.request.contextPath}/wechat/toTestStart.htm?testId="+testId; 
        } else if(returnUriType == 1001) {
        	//商品列表
        	window.location.href = "${pageContext.request.contextPath}/transfer/toHtml.htm?refer=CommodityOrderList"; 
        } else if(returnUriType == 1002) {
        	//新任务返回
        	window.location.href = "${pageContext.request.contextPath}/newTask/main.htm?childId="+"<%=request.getParameter("childId")%>";
        } else {
        	location.reload(true);
        }
        
    }else{
        //说明html是从server端重新生成的，更新LOCAL_VER
        sessionStorage.PAGEVERSION = REMOTE_VER;
    }
}
</script>
<!-- 安卓webview 后退强制刷新解决方案 END -->

