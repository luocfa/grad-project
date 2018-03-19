$(function(){
	//决定要展示tab及title内容，由父窗口的js提供值
	$("#posName1").text(parent.posName1);
	$("#posName2").text(parent.posName2);
	$("#" + parent.tabId).show();
	
	get24Info("1");
	
});
var page = "1";
function get24Info(page) {
	$.ajax({
		url : parent.domain_manager + "weather/get24Info.htm",
		type : "get",
		dataType : "json",
		data : {
			page : page
		},
		success : function (res) {
			var str = "";
			if (res.length == 0) {
				alert("查询信息失败！");
			} else {
				str += '<tr class="success"><th>日期</th><th>小时</th><th>温度(°C)</th><th>湿度(%)</th><th>AQI</th><th>操作</th></tr>';
				for (var i = res.length-1; i >= 0; i--) {
					if (i % 2 == 1) {
						str += '<tr class="warning">';
					} else {
						str += '<tr class="info">';
					}
					str += '<td>'+ res[i].up_day +'</td><td>'+res[i].up_datestr.substring(9,11)+'</td>';
					str += '<td class="bechange">'+res[i].temp+'</td>';
					str += '<td class="bechange">'+res[i].shidu+'</td>';
					str += '<td class="bechange">'+res[i].aqi+'</td>';
					str += '<td><div onclick="javascript:changevalue(this);"><span class="glyphicon glyphicon-floppy-save"></span>';
					str += '<span>修改</span></div><input type="hidden" value="' + res[i].id + '"></td></tr>';
				}
				$(".hourinfobody table tbody").html(str);
			}
		}
		
	});
}
//变为可修改状态
function changevalue(obj) {
	var $thistr = $(obj).parents("tr").find(".bechange");
	for (var i = 0; i < $thistr.length; i++) {
		var value = $($thistr[i]).text();
		$($thistr[i]).html('<input class="form-control" type="text" value="' + value + '">').css({"padding":"1px"});
	}
	$(obj).attr({"onclick" : "javascript:savechange(this);"}).html('<span class="glyphicon glyphicon-floppy-save"></span><span>保存</span>');
}
//保存修改
function savechange(obj) {
	var $thistr = $(obj).parents("tr").find(".form-control");
	var temp = $thistr[0].value;
	var shidu = $thistr[1].value;
	var aqi = $thistr[2].value;
	var id = $(obj).next("input").val();
	if ($thistr.length == 0) {
		return;
	} else {
		$.ajax({
			url : parent.domain_manager + "weather/update24Info.htm",
			type : "get",
			dataType : "json",
			data : {
				temp : temp,
				shidu : shidu,
				aqi : aqi,
				id : id
			},
			success : function (res) {
				if (res.flag == "1") {
					reverttable(obj, temp, shidu, aqi, id);
					alert("修改成功！");
				} else {
					alert("修改失败！");
				}
			}
		});
	}
}
//回复问初始状态
function reverttable(obj, temp, shidu, aqi, id) {
	var $thistr = $(obj).parents("tr").find(".bechange");
	$($thistr[0]).html(temp);
	$($thistr[1]).html(shidu);
	$($thistr[2]).html(aqi);
	$($thistr).css({"padding":"5px"});
	$(obj).next("input").val(id);
	$(obj).attr({"onclick" : "javascript:changevalue(this);"}).html('<span class="glyphicon glyphicon-floppy-save"></span><span>修改</span>');
}

function showPage(obj) {
	var clickpage = $(obj).text();
	if (page == clickpage) return;
	page = clickpage;
	$(obj).addClass("active").siblings().removeClass("active");
	$("#Previous, #Next").removeClass("disabled");
	if (page == "1") {
		$("#Previous").addClass("disabled");
	} else if (page == "6") {
		$("#Next").addClass("disabled");
	} 
	get24Info(page);
}
function pageJia() {
	if (page == "6") {
		return;
	} else {
		page = page - (-1);
		$("#page" + page).addClass("active").siblings().removeClass("active");
		$("#Previous, #Next").removeClass("disabled");
		if (page == "6") $("#Next").addClass("disabled");
		get24Info(page);
	}
}
function pageJian() {
	if (page == "1") {
		return;
	} else {
		page = page - 1;
		$("#page" + page).addClass("active").siblings().removeClass("active");
		$("#Previous, #Next").removeClass("disabled");
		if (page == "1") $("#Previous").addClass("disabled");
		get24Info(page);
	}
}
