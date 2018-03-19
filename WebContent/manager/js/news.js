$(function(){
	//决定要展示tab及title内容，由父窗口的js提供值
	$("#posName1").text(parent.posName1);
	$("#posName2").text(parent.posName2);
	$("#" + parent.tabId).show();
	
	$("#uploadImg").ajaxStart(function(){
		$(this).attr({src:parent.domain_resource + "images/loading.gif",style:"width:30px;"});
	});
	$("#uploadImg").ajaxStop(function(){
		$(this).attr({src:parent.domain_resource + "images/upload.png",style:"width:65px;"});
	});
});
function updateimgInfo(obj){
	logining(obj, "保存中");
	var imgtitle = strtrim($("#imgtitle").val());
	var imgdesc = strtrim($("#imgdesc").val());
	var imghref = strtrim($("#imghref").val());
	var imgtypename = $("#imgtypename").val();
	var imgableflag = $("#imgableflag").val();
	var up_datetime = $("#up_datetime").val();
	var imgurl = $("#imgurl").val();
	var msg = "";
	if (imgtitle == "") {
		msg = "请输入图片标题";
	} else if (imgdesc == "") {
		msg = "请输入图片描述";
	} else if (up_datetime == "" || imgurl == "") {
		msg = "请先选择图片并上传";
	} 
	if(msg != "") {
		alert(msg);
		canlogin(obj, "保存");
	} else {
		$.ajax({
			url : parent.domain_manager + "img/insertimg.htm",
			type : "post",
			dataType : "json",
			data : {
				title : imgtitle,
				imgdesc : imgdesc,
				imghref : imghref,
				typename : imgtypename,
				ableflag : imgableflag,
				imgurl : imgurl,
				publishtime : up_datetime
			},
			async : false,
			global : false,
			success : function(res){
				if(res.flag == "1") {
					alert("保存成功");
					logining(obj, "保存成功");
				} else {
					alert("保存失败");
					canlogin(obj, "保存");
				}
			}
			
			
		});
	}
}

//展示图片本地路径
function showFilename(obj) {
	$(obj).prev().val(obj.value);
}
//图片上传按钮
function upload(obj) {
	$.ajaxFileUpload({
		url : parent.domain_manager + "img/imgupload.htm",
		secureuri : false,
		type : "post",  
		fileElementId : "slideimgFile",
		dataType : "json",
		data : {
			typename :  $("#imgtypename").val(),
		},
		success : function(res,status) {
			if (res.flag == "1") {
				$(obj).prev().val(res.up_datetime);
				$(obj).prev().prev().val(res.imgurl);
				alert(res.msg);
			} else {
				alert(res.msg);
			}
		}
	});
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
//删除二边空格
var strtrim = function (str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
};