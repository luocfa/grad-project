$(function(){
	$.ajax({
		url : domain_login + "verifyLogin.htm",
		type : "get",
		dataType : "json",
		success : function(res){
			if(res.flag == "1") {
				nickname = res.nickname;
				phonenum = res.phonenum;
				$(".lang").html("<a href='" + domain_manager + "'>"+ res.nickname + "</a>，欢迎您  | <a href='javascript:loginOut();'>退出</a>");
			}
		}
	});
});

function loginOut(){
	$.ajax({
		url : domain_login + "logOut.htm",
		type : "get",
		dataType : "json",
		success : function(res){
			if(res.flag == "1") {
				location.href = domain;
			} else {
				alert("退出失败！");
			}
		}
	});
}