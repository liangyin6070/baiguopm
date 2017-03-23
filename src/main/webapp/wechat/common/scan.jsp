<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/init.jsp" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc/wxMeta.jsp"></jsp:include>
<title>幼升小测评</title>
<link rel="stylesheet" type="text/css" href="${frontPath}/skin/NewChildToPrimary/css/base.css">
<style type="text/css">
    body{ background: #fff; }
    .main{ background: #94d4ef; border-bottom: 15px #63b5db solid; position: relative; }
    .top img{ width: 100%; height: 60px; }
    .font{ padding-top: 42px; text-align: center;}
    .font img{ width: 77.5%; }
    .code{ margin-top: 20px; text-align: center; padding-bottom: 45% }
    .code img{ width: 48.6%; }
    .ani{ position: absolute; bottom: -8%; left: 16.8%; }
    .ani img{ width: 60.3%; }
</style>
</head>
<body>
    <div class="main">
        <div class="top"><img src="${frontPath}/skin/NewChildToPrimary/img/top.png" alt=""></div>
        <div class="content">
            <div class="font"><img src="${frontPath}/skin/NewChildToPrimary/img/word.png" alt=""></div>
            <div class="code"><img src="${frontPath}/skin/NewChildToPrimary/img/QRcode.png" alt=""></div>
        </div>
        <div class="ani"><img src="${frontPath}/skin/NewChildToPrimary/img/pic.png" alt=""></div>
    </div>
</body>
</html>

