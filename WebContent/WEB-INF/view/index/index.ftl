<?xml version="1.0" encoding="UTF-8"?>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小环境气象监测发布系统</title>
<link rel="Shortcut Icon" href="${domain_resource}favicon.ico"  type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="${domain_resource}css/base.css" />
<link rel="stylesheet" type="text/css" href="${domain_resource}index/css/index.css" />
<script type="text/javascript" src="${domain_resource}js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div class="wrapper">
[#include "WEB-INF/view/frame/header.ftl"]
<div id="content" class="inner">
	[#-- 左侧天气预报 --]
	<div class="weather">
		<div class="splitv"></div>
		<div class="cityinfo">
			<span>
				<h2>南信大</h2>
				<p>${today.date_y!"----年--月--日"}&nbsp;${sk.time!"--:--"}</p>
			</span>
			<!--div class="othercity">
				<img id="othercity" src="${domain_resource}images/othercity.png" alt="选择其他城市" />
				<div class="uptime" style="display:none">其他城市</div>
			</div-->
		</div>
		<div class="todayinfo">
			[#if weaico == "1" || weaico == "2"]
				[#if nowHour gt 6 && nowHour lt 18]
					<div class="currweatherico wead${weaico}"></div>
				[#else]
					<div class="currweatherico wean${weaico}"></div>
				[/#if]
			[#else]
				<div class="currweatherico wea${weaico}"></div>
			[/#if]
			<div class="currdetail">
				<span>
					<h1>${sk.temp!"N/A"}°C<em>&nbsp;&nbsp;${sk.humidity!"N/A"}</em></h1>
					<p>${today.weather!"N/A"}&nbsp;|&nbsp;${sk.wind_direction!"N/A"}&nbsp;|&nbsp;${sk.wind_strength!"N/A"}</p>
					<p id="aqi">污染指数：N/A&nbsp;|&nbsp;N/A</p>
				</span>
			</div>
		</div>
		<hr/>
		<div class="zhishu borderbot">
			<div class="title"><p>指数信息</p></div>
			<ul>
				[#list zhishu as zs]
				<li>
					<div class="zsname">
						<div class="array[#if zs_index == 0] arrayup[#else][/#if]"><p>${zs.i2}<span>${zs.i4}</span></p></div>
					</div>
					<div class="zsdesc" style="display:[#if zs_index == 0]block[#else]none[/#if];">
						<div class="backgr"></div>
						<p>${zs.i5}</p>
					</div>
				</li>
				[/#list]
			</ul>
		</div>
	</div>
	[#-- 左下信大资讯 
	<div class="news">
		<div class="newstitle">
			<p>信大资讯</p><span><a href="#">更多>></a></span>
		</div>
		<div class="newslist">
			<ul>
			[#list newsList as news]
				<li>◆&nbsp;<a href="#">[#if news.title?length gt 20]${news.title?substring(0,20)}...[#else]${news.title}[/#if]</a></li>
			[/#list]
			</ul>
		</div>
	</div>--]
	<div class="news">
		<div class="newstitle"><p>校园实景</p></div>
	</div>
	<script>
			//图片轮播插件
			$(".news").slide({
				theme:"report",
				width:300,
				height:195,
				animateStyle:'fade',
				data:[
					[#list imgList as img]
				    	{url:domain_img + "${img.imgurl}",
				    	 href:"${img.imghref}",
				    	 title:"${img.title}",
				    	 desc:"${img.imgdesc}",
				    	 imgWidth:"300",
				    	 imgHeight:"195"},
				    [/#list]]
			});
		</script>
	[#-- 右侧图表区开始 --]
	<div class="rightContent">
		<div class="charttab">
			<div class="charttitle">
				<ul>
					<li rel="temp" class="tabselected">24小时温度</li>
					<li rel="shidu">24小时湿度</li>
					<li rel="aqi">24小时AQI</li>
					<li rel="aqidetail">空气污染物</li>
				</ul>
			</div>
			<div class="chartbody">
				<div class="chart">
					<svg>
						<g id="svgbg_init"></g>
  						<g class="detsvg" id="detsvg_temp"></g>
  						<g class="detsvg" id="detsvg_shidu" style="display:none;"></g>
  						<g class="detsvg" id="detsvg_aqi" style="display:none;"></g>
  						<g id="svgani"></g>
					</svg>
				</div>
				<div class="xCoorUnit"></div>
				<div id="yunit_temp" class="yCoorUnit"></div>
				<div id="yunit_shidu" class="yCoorUnit" style="display:none;"></div>
				<div id="yunit_aqi" class="yCoorUnit" style="display:none;"></div>
				<div id="right_yunit" class="yCoorUnit right"></div>
			</div>
			<div class="chartbody"  style="display:none;">
				<div class="aqidetail"></div>
			</div>
		</div>
	</div>
	[#-- 右下天气预报信息 --]
	<div class="forecast">
		<div class="fctitle"><p>天气预报</p></div>
		<div class="fcbody">
			[#list future as ft]
			[#if ft_index gt 0]
			<div class="eachday">
				[#if ft_index = 1]
				<div class="week"><p>明天</p></div>
				[#elseif ft_index = 2]
				<div class="week"><p>后天</p></div>
				[#else]
				<div class="week"><p>${ft.week}</p></div>
				[/#if]
				[#if ft.weather?length gt 6]
				<div class="week"><p><marquee scrollamount="3" direction="up" style="text-align:center;">${ft.weather}</marquee></p></div>
				[#else]
				<div class="week"><p>${ft.weather}</p></div>
				[/#if]
				<div class="week"><p>${ft.temperature}</p></div>
				<div class="week"><p>${ft.wind}</p></div>
			</div>
			[/#if]
			[/#list]
		</div>
	</div>
</div>
[#include "WEB-INF/view/frame/footer.ftl"]
</div>
</body>
<script type="text/javascript" src="${domain_resource}index/js/index.js"></script>
</html>
