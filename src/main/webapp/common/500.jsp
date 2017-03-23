<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/init.jsp" %> 

<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/inc/wxMeta.jsp" />
<title>幼升小测评</title>
<link rel="stylesheet" type="text/css" href="${frontPath}/skin/NewChildToPrimary/css/base.css">
<style type="text/css">
    body{ background: #fff }
    div.img{ margin-top:45%; text-align: center; }
    div.img img{ width: 226px }
</style>
<script type="text/javascript">
var str=window.document.location.href;
if(str.indexOf("wechats/wxConsult/toOrder.htm")>=0){
	window.location.href="/wechats/wechat/toOrder.htm";
} else {
	window.location.href="/wechats/personal/toHtml.htm";
}
</script>
</head>
<body>
    <div class="main">
        <div class="img"><img src='${frontPath}/skin/NewChildToPrimary/img/wrong.png' alt=""></div>
    </div>
</body>
</html>
