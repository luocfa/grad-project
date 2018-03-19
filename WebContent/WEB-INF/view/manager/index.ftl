<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小气候环境监测系统发布平台</title>
<link rel="Shortcut Icon" href="${domain_resource}favicon.ico"  type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="${domain_resource}css/base.css" />
<link rel="stylesheet" type="text/css" href="${domain_resource}manager/css/manager.css" />
<script type="text/javascript" src="${domain_resource}js/md5.js"></script>
</head>
<body>
<div class="wrapper">
[#include "WEB-INF/view/frame/header.ftl"]
<div id="content" class="inner">
<!--div class="headerTip">
	
	</div-->
	<div class="menu">
		<div class="modelTab">
			<div class="title">
				<p>气象信息</p>
			</div>
			<ul>
				<li id="weather_hourinfo">24H气象资料</li>
				<!--li id="weather_aqiinfo">实时空气质量数据</li-->
			</ul>
		</div>
		<div class="modelTab">
			<div class="title">
				<p>图片管理</p>
			</div>
			<ul>
				<!--li id="news_list">查看图片</li-->
				<li id="news_editor">添加图片</li>
			</ul>
		</div>
		<div class="modelTab">
			<div class="title">
				<p>个人信息</p>
			</div>
			<ul>
				<li id="userinfo_passwd">密码修改</li>
				<!--li id="userinfo_auth">管理权限分配</li-->
				<li id="userinfo_userinfo">个人资料修改</li>
			</ul>
		</div>
	</div>
	
	<iframe name="mainIframe" src="${domain_manager}main.htm?pageCode=welcome"></iframe>
</div>
[#include "WEB-INF/view/frame/footer.ftl"]
</div>
</body>
<script type="text/javascript" src="${domain_resource}manager/js/manager.js"></script>
</html>
