<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小气候环境监测系统发布平台</title>
<link rel="stylesheet" type="text/css" href="${domain_resource}manager/css/main.css" />
<script type="text/javascript" src="${domain_resource}js/jquery-1.7.2.min.js"></script>
</head>
<body>
	<div class="content">
		<div class="title">
			<div class="figure"></div>
			<div class="position">
				<span id="posName1"></span>>><span id="posName2"></span>
			</div>
		</div>
		<div id="passwd" class="editor" style="height:357px;">
			<div class="passwdbody">
				<div class="eachTip top0">
					<label for="phone">手&nbsp;机&nbsp;号</label><em>:</em>
					<input id="phone" type="text" placeholder="请输入手机号"/>
				</div>
				<div class="eachTip top45">
					<label for="passwd0">旧&nbsp;密&nbsp;码</label><em>:</em>
					<input id="passwd0" type="password" placeholder="请输入旧密码"/>
				</div>
				<div class="eachTip top90">
					<label for="passwd1">新&nbsp;密&nbsp;码</label><em>:</em>
					<input id="passwd1" type="password" placeholder="请输入新密码"/>
				</div>
				<div class="eachTip top135">
					<label for="passwd2">确认密码</label><em>:</em>
					<input id="passwd2" type="password" placeholder="请再次输入密码"/>
				</div>
				<div class="eachTip top180">
					<label for="verify">验&nbsp;证&nbsp;码</label><em>:</em>
					<input id="verify" style="width:68px;font-size:8px;" type="text" placeholder="输入验证码"/>
					<a href="javascript:;" onclick="javascript:getCaptcha(this);"><img src="${domain_resource}images/clickme.png"/>[#-- 不要写 src="#"，这样页面进来是src找不到，就会多执行一次controller --]</a>
				</div>
				<div class="submitbtn">
					<button id="changePw" onclick="javascript:changePw(this);">修改密码</button>
				</div>	
			</div>
		</div>
		<div id="userinfo" class="editor" style="height:357px;">
			<div class="passwdbody">
				<div class="eachTip top45">
					<label for="phone1">手&nbsp;机&nbsp;号</label><em>:</em>
					<input id="phone1" type="text" placeholder="请输入手机号"/>
				</div>
				<div class="eachTip top90">
					<label for="nickname">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</label><em>:</em>
					<input id="nickname" type="text" placeholder="请输入昵称"/>
				</div>
				<div class="eachTip top135">
					<label for="verify1">验&nbsp;证&nbsp;码</label><em>:</em>
					<input id="verify1" style="width:68px;font-size:8px;" type="text" placeholder="输入验证码"/>
					<a href="javascript:;" onclick="javascript:getCaptcha(this);"><img src="${domain_resource}images/clickme.png"/>[#-- 不要写 src="#"，这样页面进来是src找不到，就会多执行一次controller --]</a>
				</div>
				<div class="submitbtn">
					<button id="changeInfo" onclick="javascript:changeInfo(this);">修改信息</button>
				</div>	
			</div>
		</div>
		<div class="console" style="height:90px;top:435px;">
			<div class="passtips">
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${domain_resource}manager/js/userinfo.js"></script>
</html>