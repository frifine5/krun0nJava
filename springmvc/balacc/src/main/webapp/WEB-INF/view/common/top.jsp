<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache">
<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="0">
<title>IBC应用系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/platform/css/platform.css" type="text/css" media="screen">
<script src="${pageContext.request.contextPath }/js/allpps.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/platform/plugins/platform.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/pps.css" type="text/css" media="screen">
<style type="text/css">
body {
	background: rgb(250, 250, 250) none repeat scroll 0% 0%;
}
#modulemenu .nav > li>a{
	padding:1px 5px 1px;
}
</style>
</head>
<script type="text/javascript">
	$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
	});
</script>
<body class="m-my-index" style="overflow-y:auto; ">
	<header id="header">
	<div id="topbar">
		<h5 id="companyname">IBC应用系统</h5>
	</div>
	<nav id="mainmenu">
		<ul class="nav">
<!-- 			<li class="active" data-id="my"> -->
<!-- 			<a href="javascript:;" style="padding:7px 15px;" -->
<%-- 				data-href="${pageContext.request.contextPath }/page/index.jsp" --%>
<!-- 				class="active"> -->
<!-- 				<i class="icon-home"></i><span> 我的地盘</span> -->
<!-- 				 </a> -->
<!-- 				</li> -->
		</ul>
	</nav> 
	<nav id="modulemenu">
		<ul class="nav">
			<li data-id="account"><span id="myname">
				<i class="icon-user"></i> 主页&nbsp;<i class="icon-angle-right"></i>&nbsp;</span>
			</li>
			<li id="mainPage" class="${param.name eq 1 ?'active':''}"  >
			    <a  style="background: #e5e5e5;"
				href="${pageContext.request.contextPath }/login/loginIndex">标识信息</a></li>
			<li class="${param.name eq 3 ?'active':''}"><a  style="background: #e5e5e5;"
				href="${pageContext.request.contextPath }/login/sign/listCheck">公开参数信息</a></li>
			<li class="${param.name eq 4 ?'active':''}"><a  style="background: #e5e5e5;"
				href="${pageContext.request.contextPath }/login/sign/listIRL">IRL下载</a></li>
			<%-- <li class="${param.name eq 2 ?'active':''}" style="background: #e5e5e5;">
			    <a style="background: #e5e5e5;"
				href="${pageContext.request.contextPath }/login/sign/listAdd">pps服务信息</a></li> --%>
		</ul>
	</nav> 
	</header>
