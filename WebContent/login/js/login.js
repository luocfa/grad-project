$(function(){
	
	//回车登录
	$("body").on("keydown",function(){
		if(event.keyCode == 13) {
			if($("#login").css("display") == "block"){
				$("#submitbtn").click();
			}
		}
	});
	
	
});
var pwWrongNum = 0;
//登录
function submitbtn(obj){
	logining(obj,"登录中...");
	var loginname = $("#loginname").val();
	var loginpass = $("#passwd").val();
	var isReady = "";
	if(strtrim(loginname) == ""){
		isReady = "请输入用户名";
	} else if(!checkname(loginname)){
		isReady = "用户名格式不正确"; 
	} else if(strtrim(loginpass) == ""){
		isReady = "请输入密码";
	} else if(!checkpwd(loginpass)){
		isReady = "密码格式不正确";
	} else if($("#verify1").css("display") != "none") {
		var isCapReady = verifyCaptcha($("#verification").val());
		if(!isCapReady) {
			isReady = "验证码不正确";
		}
	}
	if(isReady){
		canlogin(obj,"登录");
		alert(isReady);
	} else {
		$.ajax({
			url : domain_login + "doLogin.htm",
			type : "post",
			dataType : "json",
			data : {
				loginname : strtrim(loginname),
				loginpass : hex_md5(loginpass)
			},
			async : false,
			success : function(res) {
				if(res.flag == "1") {
					location.href = domain_manager;
				} else {
					canlogin(obj,"登录");
					alert(res.msg);
					if(res.failCode == "L002") {
						pwWrongNum++;
						if(pwWrongNum == 3) {
							$("#forregist").hide();
							$("#verify1 img").attr({"src" : domain_login + "getCaptcha.htm?time=" + new Date()});
							$("#verify1").show();
							$("#submitbtn").parent().css({"top" : "215px"});
						}
					}
				}
			}
		});
	}
}

//重置密码
function resetPw(obj){
	logining(obj,"正在重置...");
	var username = $("#username").val();
	var phone = $("#phone").val();
	var isReady = "";
	if(strtrim(username) == ""){
		isReady = "请输入用户名";
	} else if(!checkname(username)){
		isReady = "用户名格式不正确"; 
	} else if(strtrim(phone) == ""){
		isReady = "请输入手机号";
	} else if(!isMobile(phone)){
		isReady = "手机号码格式不正确";
	} else if(!verifyCaptcha($("#verify").val())) {
		isReady = "验证码不正确";
	}
	if(isReady){
		canlogin(obj, "重置密码");
		alert(isReady);
	} else {
		$.ajax({
			url : domain_login + "resetpasswd.htm",
			type : "post",
			dataType : "json",
			data : {
				username : strtrim(username),
				phone : strtrim(phone)
			},
			async : false,
			success : function(res) {
				if(res.flag == "1") {
					logining(obj,"密码已重置");
					alert("密码已重置为" + res.passwd + "，请登录后修改密码！");
				} else {
					canlogin(obj);
					alert(res.msg);
				}
			}
		});
	}
	
}
//点击验证码换一张
function getCaptcha(obj){
	$(obj).children("img").attr({"src" : domain_login + "getCaptcha.htm?time=" + new Date()});
}
//检查验证码是否正确
function verifyCaptcha(captchaInput) {
	var captureFlag = false;
	if (captchaInput == "") {
		captureFlag = false;
	} else {
		$.ajax({
			url : parent.domain_login + "verifyCaptcha.htm",
			type : "get",
			dataType : "json",
			async : false,
			data : {
				captchaInput : captchaInput,
			},
			success : function(res) {
				captureFlag = res.captureFlag;
			},
			error : function() {
				captureFlag =  false;
			}
		});
	}
	return captureFlag;
	
}
//登录中效果
function logining(obj,msg) {
	$(obj).addClass("logining");
	$(obj).attr({"onclick" : "javascript:;"});
	$(obj).text(msg);
}
//可登陆效果
function canlogin(obj,msg) {
	$(obj).removeClass("logining");
	var methodName = obj.id;
	$(obj).attr({"onclick" : "javascript:" + methodName + "(this);"});
	$(obj).text(msg);
}
//点击重置或者联系管理员的方法
function showothers(obj) {
	var showId = $(obj).prop("rel");
	$(".loginbody").hide();
	if(showId == "findpw"){
		$("#verify2 img").attr({"src" : domain_login + "getCaptcha.htm?time=" + new Date()});
	}
	$("#" + showId).show();
}
//重置密码或者联系管理员关闭按钮
function closefindpwd(){
	$(".loginbody").hide();
	$("#login").show();
}
//校验字符串：字母开头，6-18个字母、数字、下划线
var checkname = function (str) {
	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^[a-zA-Z][a-zA-Z0-9_]{5,17}$/;
	return patrn.test(str);
};
//密码校验 6-18个字母、数字、下划线
var checkpwd = function (str) {
	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^[a-zA-Z0-9_]{6,18}$/;
	return patrn.test(str);
};
//校验手机号码
var isMobile = function (str) {
	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^0?1[3,4,5,7,8][0-9]{9}$/;
	return patrn.test(str);
};
//删除二边空格
var strtrim = function (str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
};


