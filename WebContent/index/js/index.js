$(function(){
	
	$("#othercity").hover(function(){
		$(this).addClass("imgrorate");
		$(".uptime").fadeIn("normal", "linear");
	},function(){
		$(this).removeClass("imgrorate");
		$(".uptime").fadeOut("normal", "linear");
	});
	
	//指数信息切换
	$(".zsname .array").on("click", function(){
		if(!$(this).hasClass("arrayup")){
			$(".zsname .array").removeClass("arrayup");
			$(this).addClass("arrayup");
			$(".zsdesc").slideUp();
			$(this).parent(".zsname").next().slideDown();
		}
	}).hover(function(){
		$(this).parent(".zsname").addClass("tabhover");
	},function(){
		$(this).parent(".zsname").removeClass("tabhover");
	});
	
	//图表title切换
	$(".charttitle li").on("click", function(){
		if(!$(this).hasClass("tabselected")) {
			$(".charttitle li").removeClass("tabselected tabhover");
			$(this).addClass("tabselected");
			if ($(this).index() < 3) {
				if($(".chart").parent().css("display") == "none") {
					$(".chartbody").hide();
					$(".chart").parent().show();
				}
				var tabId = $(this).attr("rel");
				$(".detsvg").hide();
				$(".yCoorUnit:lt(3)").hide();
				$("#detsvg_" + tabId).show();
				$("#yunit_" + tabId).show();
			} else {
				$(".chartbody").hide();
				$(".aqidetail").parent().show();
			}
		}
	}).hover(function(){
		if(!$(this).hasClass("tabselected")){
			$(this).addClass("tabhover");
		}
	},function(){
		$(this).removeClass("tabhover");
	});
	
	showAqiInfo();
	show24Info();
	
});



/**
 * 展示24小时温湿度的方法
 */
function show24Info(){
	$.ajax({
		url : domain + "index/get24Info.htm",
		type : "get",
		dataType : "json",
		data : {},
		success : function(res){
			if(res != null && res != "") {
				draw24Info(res);
			} else {
				alert("查询温湿度信息失败！");
			}
		},
		error : function (){
			alert("查询温湿度信息失败！");
		}
	});
}
function showAqiInfo(){
	$.ajax({
		url : domain + "index/getAqiInfo.htm",
		type : "get",
		dataType : "json",
		data : {},
		success : function(res){
			if(res.flag == "1") {
				var aqiInfo = '污染指数：'+ (res.aqi != null ? res.aqi : "N/A") +'&nbsp;|&nbsp;'+ (res.quality != null ? res.quality : "N/A");
				$(".currdetail span #aqi").html(aqiInfo);
				drawAqiInfo(res);
			} else {
				alert("查询空气质量数据失败！");
			}
		},
		error : function (){
			alert("查询空气质量数据失败！");
		}
	});
}
/**
 * 初始化svg背景线
 */
function initSvgBg(){
	var BGLINEDISTANCE = 28;//背景线间距
	var strsvgbg = "";//背景线字符串
	for(var i = 0; i < 10; i++){
		var y_coor = BGLINEDISTANCE * (i - (-1));
		strsvgbg += '<line x1="0" y1="' + y_coor + '" x2="523" y2 ="' + y_coor + '" stroke="#C8DDE8" stroke-width="1" stroke-dasharray="1,1"/>';
	}
	$("#svgbg_init").html(parseSVG(strsvgbg));
}
/**
 * 画24小时温度信息
 * @param res
 */
function draw24Info(res) {
	initSvgBg();
	var X_COOR_DIS = 21;//x轴间距
	var strx_coor_dot = "";//x轴单位点字符串
	var str_xunit = "";//
	var strtemp_dot = "";//温度坐标点字符串
	var strtemp_line = "";//温度连接线字符串
	var strtemp_yunit = "";//温度y坐标
	var strhumidity_yunit = "";
	var strhumidity_dot = "";
	var strhumidity_line = "";
	var straqi_dot = "";
	var straqi_line = "";
	var straqi_yunit = "";
	for(var i = 0, num = res.length; i < num; i++){
		var x_coor = X_COOR_DIS * (i - (-1));
		var x_coor_unit = (X_COOR_DIS - 7) + (X_COOR_DIS -15) * i;
		var y_tem_coor = eval((40 - res[i].temp) * (280/50));
		var y_hum_coor = eval((110 - res[i].shidu) * (280/100));
		var y_aqi_coor = eval((500 - res[i].aqi) * (280/500));
		strx_coor_dot += '<circle cx="' + x_coor + '" cy="280" r="2" stroke="#fff" fill="#fff"/>';
		str_xunit += '<span style="left:' + x_coor_unit + 'px;">' + (res[i].up_datestr.split("-"))[1] + '</span>';
		strtemp_dot += '<circle value="' + res[i].temp + '" cx="' + x_coor + '" cy="' + y_tem_coor + '" r="4"  stroke="#fff" fill="#fff"/>';
		strhumidity_dot += '<circle value="' + res[i].shidu + '" cx="' + x_coor + '" cy="' + y_hum_coor + '" r="4"  stroke="#fff" fill="#fff"/>';
		straqi_dot += '<circle value="' + res[i].aqi + '" cx="' + x_coor + '" cy="' + y_aqi_coor + '" r="4"  stroke="#fff" fill="#fff"/>';
		if(i < num - 1){
			var x_coor_n = X_COOR_DIS * (i - (-2));
			var y_tem_coor_n = eval((40 - res[i+1].temp) * (280/50));
			var y_hum_coor_n = eval((110 - res[i+1].shidu) * (280/100));
			var y_aqi_coor_n = eval((500 - res[i+1].aqi) * (280/500));
			strtemp_line += '<line x1="' + x_coor + '" y1="' + y_tem_coor + '" x2= "' + x_coor_n + '" y2="' + y_tem_coor_n + '" stroke="#fff" stroke-width="1"/>';
			strhumidity_line += '<line x1="' + x_coor + '" y1="' + y_hum_coor + '" x2= "' + x_coor_n + '" y2="' + y_hum_coor_n + '" stroke="#fff" stroke-width="1"/>';
			straqi_line += '<line x1="' + x_coor + '" y1="' + y_aqi_coor + '" x2= "' + x_coor_n + '" y2="' + y_aqi_coor_n + '" stroke="#fff" stroke-width="1"/>';
		} else {
			str_xunit += '<span style="left:' + (x_coor_unit - (-10)) + 'px;">时</span>';
		}
	}
	
	for (var i = 0; i < 9; i++){
		var BGLINEDISTANCE = 28;//背景线间距
		var y_coor = (BGLINEDISTANCE - 10) - (-BGLINEDISTANCE * i);//y轴单位坐标
		var y_tem_unit = 40 - 5 * (i - (-1));
		var y_hum_unit = 110 - 10 * (i - (-1));
		var y_aqi_unit = 500 - 50 * (i - (-1));
		strtemp_yunit += '<span style="top:' + y_coor + 'px;">' + y_tem_unit + '</span>';
		strhumidity_yunit += '<span style="top:' + y_coor + 'px;">' + y_hum_unit + '</span>';
		straqi_yunit += '<span style="top:' + y_coor + 'px;">' + y_aqi_unit + '</span>';
	}
	strtemp_yunit += '<span style="top:-10px;">°C</span>';
	strhumidity_yunit += '<span style="top:-10px;">%</span>';
	$("#svgbg_init").append(parseSVG(strx_coor_dot));//增加X轴坐标点
	$(".xCoorUnit").html(str_xunit);//增加X轴单位
	$("#detsvg_temp").append(parseSVG(strtemp_line)).append(parseSVG(strtemp_dot));//温度折线
	$("#detsvg_shidu").append(parseSVG(strhumidity_line)).append(parseSVG(strhumidity_dot));//湿度折线
	$("#detsvg_aqi").append(parseSVG(straqi_line)).append(parseSVG(straqi_dot));//aqi折线
	$("#yunit_temp").html(strtemp_yunit);//温度Y轴单位
	$("#yunit_shidu").html(strhumidity_yunit);
	$("#yunit_aqi").html(straqi_yunit);
	
	$(".detsvg circle").hover(function(){
		this_r = $(this).attr("r");
		this_fill = $(this).attr("fill");
		var thisx_coor = $(this).attr("cx");
		var thisy_coor = $(this).attr("cy");
		var this_value = $(this).attr("value");
		$(this).attr({"r" : "8","fill" : "#fc0"});
		var str_over = "";
		str_over += '<line x1="' + thisx_coor + '" y1="0" x2="' + thisx_coor + '" y2="280" stroke="#FC0" stroke-width="2" stroke-dasharray="1,2"/>';
		str_over += '<line x1="0" y1="' + thisy_coor + '" x2="525" y2="' + thisy_coor + '" stroke="#FC0" stroke-width="2" stroke-dasharray="1,1"/>';
//		str_over += '<circle cx="' + thisx_coor + '" cy="' + thisy_coor + '" r="7" stroke="#fff" fill="#fc0"/>';
		$("#svgani").html(parseSVG(str_over));
		var parentId = $(this).parent().attr("id");
		var right_yunit = "";
		if(parentId == "detsvg_temp"){
			right_yunit = "°C";
		} else if(parentId == "detsvg_shidu"){
			right_yunit = "%";
		} 
		$("#right_yunit").html('<span style="top:' + (thisy_coor-12) + 'px;background-color:#fff;color:#095E9C;font-size:16px;">' + this_value + right_yunit + '</span>');
	},function(){
		$(this).attr({"r" : this_r,"fill" : this_fill});
		$("#svgani").html(parseSVG(""));
		$("#right_yunit").html("");
	});
}

function drawAqiInfo(res) {
	var aqiInfo = [
	        {"name":"PM2.5","value":res.pm2_5,"desc":"细颗粒物"},
	        {"name":"PM10","value":res.pm10,"desc":"可吸入颗粒物"},
			{"name":"NO2","value":res.no2,"desc":"二氧化氮"},
			{"name":"03","value":res.o3,"desc":"臭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;氧"},
			{"name":"SO2","value":res.so2,"desc":"二氧化硫"},
			{"name":"CO","value":res.co,"desc":"一氧化碳"}
	];
	var primary_pollutant = res.primary_pollutant;
	var time_point = res.time_point;
	var aqistr = "";
	aqistr += '<div class="title"><span>数据更新时间：'+ time_point.substring(0,10) + ' ' + time_point.substring(11,19) +'</span>';
	aqistr += '<span style="float:right;">数值单位：μg/m3(CO为mg/m3)</span></div>';
	aqistr += '<div class="title" style="top:5px;"><span>首要污染物：'+ primary_pollutant +'</span></div>';
	for(var i = 0, num = aqiInfo.length; i < num; i++) {
		aqistr += '<div class="eachbody"><p>'+ aqiInfo[i].value +'</p><p>'+ aqiInfo[i].name +'</p><input type="hidden" value="' + aqiInfo[i].desc + '"/></div>';
	}
//	aqistr += '<div class="aqidesc" style="left:-8px;top:-10px;"><p>' + aqiInfo[0].desc + '</p></div>';
	aqistr += '<div class="aqidesc" style="display:none;"></div>';
	$(".aqidetail").html(aqistr);
	$(".eachbody").live("click",function(){
		var offset = $(this).position();
		var desc = $(this).children("input").val();
		$(".aqidesc").css({
			left:offset.left-16 + "px",
			top:"-10px",
		}).html("<p>" + desc + "</p>");
	});
	$(".eachbody").hover(function(){
		var offset = $(this).position();
		var desc = $(this).children("input").val();
		$(".aqidesc").css({
			left:offset.left-16 + "px",
			top:"-10px",
		}).html("<p>" + desc + "</p>").fadeIn();
	},function(){
		$(".aqidesc").hide();
	});
}
/**
 * 此方法用来创建svg dom对象
 * 普通创建dom对象的方法不适用于svg dom
 * @param s
 * @returns
 */
function parseSVG(s) {
    var div= document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
    div.innerHTML= '<svg xmlns="http://www.w3.org/2000/svg">'+s+'</svg>';
    var frag= document.createDocumentFragment();
    while (div.firstChild.firstChild)
        frag.appendChild(div.firstChild.firstChild);
    return frag;
}


