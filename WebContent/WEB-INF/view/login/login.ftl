<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小气候环境监测系统发布平台</title>
<link rel="Shortcut Icon" href="${domain_resource}favicon.ico"  type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="${domain_resource}css/base.css" />
<link rel="stylesheet" type="text/css" href="${domain_resource}login/css/login.css" />
<script type="text/javascript" src="${domain_resource}js/md5.js"></script>
</head>
<body>
<div class="wrapper">
[#include "WEB-INF/view/frame/header.ftl"]
<div id="content" class="inner">
	<div class="main">
		<div class="slideimg"></div>
		<script>
			//图片轮播插件
			$(".slideimg").slide({
				theme:"report",
				width:550,
				height:300,
				animateStyle:'fade',
				data:[
					[#list imgList as img]
				    	{url:domain_img + "${img.imgurl}",
				    	 href:"${img.imghref}",
				    	 title:"${img.title}",
				    	 desc:"${img.imgdesc}",
				    	 imgWidth:"550",
				    	 imgHeight:"300"},
				    [/#list]]
			});
		</script>
	</div>
	<div class="loginbox">
		<div class="loginboxs">
			<div class="logintab">
				<ul>
					<li><p class="currtab">后台管理登录</p></li>
				</ul>
			</div>
			<div id="login" class="loginbody" style="display:block;">
				<div class="username">
					<label for="loginname">用&nbsp;户&nbsp;名</label><em>:</em>
					<input id="loginname" type="text" placeholder="请输入用户名"/>
				</div>
				<div class="passwd">
					<label for="passwd">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label><em>:</em>
					<input id="passwd" type="password" placeholder="请输入密码"/>
				</div>
				<div id="verify1" class="verification" style="display:none;">
					<label for="verification">验&nbsp;证&nbsp;码</label><em>:</em>
					<input id="verification" style="width:68px;font-size:8px;" type="text" placeholder="输入验证码"/>
					<a href="javascript:;" onclick="javascript:getCaptcha(this);"><img/></a>
				</div>
				<div id="forregist" class="verification" >
					<span><a href="javascript:;" onclick="javascript:showothers(this);" rel="findpw">重置密码</a>&nbsp;|&nbsp;<a href="javascript:;" onclick="javascript:showothers(this);" rel="apadmin">申请管理权限</a></span>
				</div>
				<div class="submitbtn">
					<button id="submitbtn" onclick="javascript:submitbtn(this);">登录</button>
				</div>
			</div>
			<div id="findpw" class="loginbody" style="display:none;">
				<div class="closebtn" onclick="javascript:closefindpwd();"></div>
				<div class="username">
					<label for="username">用&nbsp;户&nbsp;名</label><em>:</em>
					<input id="username" type="text" placeholder="请输入用户名"/>
				</div>
				<div class="passwd">
					<label for="phone">手&nbsp;机&nbsp;号</label><em>:</em>
					<input id="phone" type="text" placeholder="请输入手机号"/>
				</div>
				<div id="verify2" class="verification">
					<label for="verify">验&nbsp;证&nbsp;码</label><em>:</em>
					<input id="verify" style="width:68px;font-size:8px;" type="text" placeholder="输入验证码"/>
					<a href="javascript:;" onclick="javascript:getCaptcha(this);"><img/>[#-- 不要写 src="#"，这样页面进来是src找不到，就会多执行一次controller --]</a>
				</div>
				<div class="submitbtn" style="top:215px;">
					<button id="resetPw" onclick="javascript:resetPw(this);">重置密码</button>
				</div>
			</div>
			<div id="apadmin" class="loginbody" style="display:none;">
				<div class="closebtn" onclick="javascript:closefindpwd();"></div>
				<ul>
					<li>请联系管理员获取管理权限</li>
					<li>联系电话：18512526245</li>
					<li>邮箱：luocfa@qq.com</li>
				</ul>
				<div class="submitbtn">
					<button onclick="javascript:location.href='${domain}';">返回首页</button>
				</div>
			</div>
		</div>
	</div>
	<div class="loginTips">
		<div>温馨提示</div>
		<ul>
			<li>1.	用户名是6-18位字母数字或下划线的组合，但是必须以字母开头，并区分大小写</li>
			<li>2.	密码是6-18位字母数字或下划线的组合，区分大小写</li>
			<li>3.	如忘记密码，请点击“忘记密码”，密码将被重置为六位随机数字，请使用新密码<br/>&nbsp;&nbsp;&nbsp;&nbsp;登录后修改密码</li>
			<li>4.	如需要管理权限，请联系网站管理员，联系电话：18512526245</li>
		</ul>
	</div>
</div>
[#include "WEB-INF/view/frame/footer.ftl"]
</div>
<div class=""></div>
</body>
<script type="text/javascript" src="${domain_resource}login/js/login.js"></script>
</html>
