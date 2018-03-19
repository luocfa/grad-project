<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小气候环境监测系统发布平台</title>
<link rel="stylesheet" href="${domain_resource}angularJs/css/bootstrap.min.css" />
<link rel="stylesheet" href="${domain_resource}manager/css/main.css" />
<script src="${domain_resource}js/jquery-1.11.2.js"></script>
</head>
<body>
	<div class="content">
		<div class="title">
			<div class="figure"></div>
			<div class="position">
				<span id="posName1"></span>>><span id="posName2"></span>
			</div>
		</div>
		<div id="hourinfo" class="editor">
			<div class="hourinfobody">
				<table class="table table-bordered table-condensed">
					<tr class="success"><th>日期</th><th>小时</th><th>温度(°C)</th><th>湿度(%)</th><th>AQI</th><th>操作</th></tr>
				</table>
				  <ul class="pagination">
				    <li id="Previous" class="disabled">
				      <a href="javascript:pageJian();" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <li id="page1" onclick="javascript:showPage(this);" class="active"><a href="javascript:;">1</a></li>
				    <li id="page2" onclick="javascript:showPage(this);"><a href="javascript:;">2</a></li>
				    <li id="page3" onclick="javascript:showPage(this);"><a href="javascript:;">3</a></li>
				    <li id="page4" onclick="javascript:showPage(this);"><a href="javascript:;">4</a></li>
				    <li id="page5" onclick="javascript:showPage(this);"><a href="javascript:;">5</a></li>
				    <li id="page6" onclick="javascript:showPage(this);"><a href="javascript:;">6</a></li>
				    <li id="Next">
				      <a href="javascript:pageJia();" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				  </ul>
				  <div class="tips">
				  	<p class="bg-success">仅支持72小时以内的气象数据修改</p>
				  </div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${domain_resource}manager/js/weather.js"></script>
</html>