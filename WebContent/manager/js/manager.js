var tabId,posName1,posName2;//全局变量，共子窗口调用
$(function(){
	//左侧菜单栏切换
	$(".modelTab li").hover(function(){
		$(this).addClass("hover");
	},function(){
		$(this).removeClass("hover");
	}).on("click",function(){
		if(!$(this).hasClass("selected")){
			var iframeDom = window.frames["mainIframe"].document;
			$(".modelTab li").removeClass("selected");
			$(this).addClass("selected");
			var liId = this.id;
			var idArray = liId.split("_");
			var pageCode = idArray[0];
			tabId = idArray[1];
			posName1 = $(this).parent().prev().children("p").text();
			posName2 = $(this).text();
			var iframeSrc = $("iframe").attr("src");
			var NiframeSrc = domain_manager + "main.htm?pageCode=" + pageCode;
			if(iframeSrc != NiframeSrc){
				$("iframe").attr({"src" : NiframeSrc});
			} else {
				$(iframeDom).find("#posName1").text(posName1);
				$(iframeDom).find("#posName2").text(posName2);
				$(iframeDom).find(".editor").hide();
				$(iframeDom).find("#" + tabId).show();
//				if(parent.tabId == "passwd") {
//					$(iframeDom).find(".console .passtips").html("<p>注：	密码是6-18位字母数字或下划线的组合，区分大小写</p>");
//				} else if (parent.tabId == "userinfo") {
//					$(iframeDom).find(".console .passtips").html("<p>注：	昵称是6-18位汉字字母数字或下划线的组合，区分大小写</p>");
//				}
			}
		}
	});
	
	
});
