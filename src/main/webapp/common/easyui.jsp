<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/easyui/themes/default/easyui.css" />
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/skin/easyui/themes/metro-blue/easyui.css" /> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/easyui/themes/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
	
	.panel-header, .panel-body{
		border:none;
	}
	.panel-header{
		background:#f1f1f1;
		padding: 12px 5px;
	}
	.panel-header .panel-title{
		color:#b5b5b5;
	}
	
	








	.tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs{
		background: #fff;
		border:none;
	}
	.tabs-header{
		padding: 7px 15px 0;
	}

	.tabs li a.tabs-inner, .tabs li a.tabs-inner:hover{
		background: #f2f2f2;
		border:none;
		color: #b5b5b5;
		padding:0 15px;
	}
	.tabs li.tabs-selected a.tabs-inner{ 
		background: #eaf3fc;
		border:none;
		color: #168acc;
	}
	.datagrid-toolbar{
		background:#fff;
	}
	



	#tb{
		padding: 15px 18px !important;
	}

	.datagrid .datagrid-pager{
		padding:3px 15px;
	}
	.datagrid-header{
		background: transparent;
		border:none;
	}
	.datagrid-toolbar{
		border:none;
	}
	.datagrid-htable, .datagrid-btable, .datagrid-ftable{

	}
	
	.datagrid-header-inner{
		background: #fff;
	}
	 .datagrid-header-row{
		background: #244086;
	}
	.datagrid-header td{
		color: #fff;
	}
	.datagrid-header td:hover{
		background: transparent;
		color: #fff;
	}
	.datagrid-cell{
		padding:12px 10px;
	}
	.datagrid-header-check, .datagrid-cell-check{
		padding: 12px 10px;
	}
	table,tr,td{
		margin:0;
		padding:0;
	}
	.datagrid-row-selected{
		background: #f9f9f9;
	}
	.datagrid-row:hover{
		background: #f9f9f9;
	}
</style>
    