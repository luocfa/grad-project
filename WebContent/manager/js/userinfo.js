$(function(){
	//决定要展示tab及title内容，由父窗口的js提供值
	$("#posName1").text(parent.posName1);
	$("#posName2").text(parent.posName2);
	$("#" + parent.tabId).show();
	
	$("#phone1").attr({placeholder:parent.phonenum});
	$("#nickname").attr({placeholder:parent.nickname});
	
	showconsoletip();
	$(window.parent.document).find(".modelTab li").on("click",function(){
		showconsoletip();
	});
});
//下方提示切换
function showconsoletip() {
	if(parent.tabId == "passwd") {
		$(".console .passtips").html("<p>注：	密码是6-18位字母数字或下划线的组合，区分大小写</p>");
	} else if (parent.tabId == "userinfo") {
		$(".console .passtips").html("<p>注：	昵称是3-18位汉字字母数字或下划线的组合，区分大小写</p>");
	}
}
//重置密码
function changePw(obj){
	logining(obj,"正在修改...");
	var phone = $("#phone").val();
	var passwd0 = $("#passwd0").val();
	var passwd1 = $("#passwd1").val();
	var passwd2 = $("#passwd2").val();
	var verify = $("#verify").val();
	var isReady = "";
	if(strtrim(phone) == ""){
		isReady = "请输入手机号";
	} else if(!isMobile(phone)){
		isReady = "手机号码格式不正确";
	} else if(strtrim(passwd0) == ""){
		isReady = "请输入旧密码";
	} else if(!checkpwd(passwd0)){
		isReady = "密码格式不正确";
	} else if(strtrim(passwd1) == ""){
		isReady = "请输入新密码";
	} else if(!checkpwd(passwd1)){
		isReady = "密码格式不正确";
	} else if(passwd2 != passwd1){
		isReady = "两次密码格式不相同";
	} else if(!verifyCaptcha(verify)) {
		isReady = "验证码不正确";
	}
	if(isReady){
		alert(isReady);
		canlogin(obj, "修改密码");
	} else {
		$.ajax({
			url : parent.domain_manager + "userinfo/changepass.htm",
			type : "post",
			dataType : "json",
			data : {
				phone : strtrim(phone),
				oldpass : parent.hex_md5(passwd0),
				pass : parent.hex_md5(passwd1)
			},
			async : false,
			success : function(res) {
				if(res.flag == "1") {
					logining(obj,"密码已修改");
					alert("密码修改成功，请重新登录");
					setTimeout(function(){
						parent.location.href = parent.domain_login;
					},500);
				} else {
					canlogin(obj);
					alert(res.msg);
				}
			}
		});
	}
	
}
function changeInfo(obj) {
	logining(obj,"正在修改...");
	var phone = $("#phone1").val();
	var nickname = $("#nickname").val();
	var verify = $("#verify1").val();
	var isReady = "";
	if(strtrim(phone) == ""){
		isReady = "请输入手机号";
	} else if(!isMobile(phone)){
		isReady = "手机号码格式不正确";
	} else if(nickname == ""){
		isReady = "请输入昵称";
	} else if(!checkNickname(nickname)) {
		isReady = "昵称格式不正确";
	}else if(!verifyCaptcha(verify)) {
		isReady = "验证码不正确";
	}
	if(isReady){
		alert(isReady);
		canlogin(obj, "修改信息");
	} else {
		$.ajax({
			url : parent.domain_manager + "userinfo/updateInfo.htm",
			type : "post",
			dataType : "json",
			data : {
				phone : strtrim(phone),
				nickname : nickname
			},
			async : false,
			success : function(res) {
				if(res.flag == "1") {
					logining(obj,"信息已修改");
					alert("信息修改修改成功");
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
	$(obj).children("img").attr({"src" : parent.domain_login + "getCaptcha.htm?time=" + new Date()});
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
//校验字符串：字母开头，6-18个字母、数字、下划线
var checkname = function (str) {
	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^[a-zA-Z][a-zA-Z0-9_]{5,17}$/;
	return patrn.test(str);
};
//密码校验 6-18个字母、数字、下划线
var checkpwd = function (str) {
//	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^[a-zA-Z0-9_]{6,18}$/;
	return patrn.test(str);
};
var checkNickname = function (str) {
	str = str.replace(/(^\s*)|(\s*$)/g, "");// 删除二边空格
	var patrn = /^[\u4e00-\u9fa5a-zA-Z0-9_]{3,18}$/;;
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