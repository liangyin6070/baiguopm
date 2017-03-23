<%@ page language="java" pageEncoding="utf-8" %>
<!-- 加载公用标签库 -->
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag" %> 
<c:set value="${pageContext.request.contextPath}" var="frontPath" scope="request"/>
<c:set value="http://testwx.run2smart.cn" var="sharePath" scope="request"></c:set>
<c:set value="http://testwx.run2smart.cn" var="staticPath" scope="request"></c:set>
<c:set value="${frontPath}/skin/common_base" var="commonPath" scope="request"></c:set>