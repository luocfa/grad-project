<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>南京信息工程大学-小气候环境监测系统发布平台</title>
<link rel="stylesheet" type="text/css" href="${domain_resource}manager/css/main.css" />
<script type="text/javascript" src="${domain_resource}js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${domain_resource}js/ajaxfileupload.js"></script>
</head>
<body>
	<div class="content">
		<div class="title">
			<div class="figure"></div>
			<div class="position">
				<span id="posName1"></span>>><span id="posName2"></span>
			</div>
		</div>
		<div id="editor" class="editor">
			<div class="imgmanager">
				<!--div class="closebtn" onclick="javascript:closefindpwd();"></div-->
				<div class="eachTip">
					<label for="imgtitle">图片标题</label><em>:</em>
					<input id="imgtitle" type="text" placeholder="请输入新闻标题"/>
				</div>
				<div class="eachTip top45">
					<label for="imgdesc">图片描述</label><em>:</em>
					<input id="imgdesc" type="text" placeholder="请输入发布人"/>
				</div>
				<div class="eachTip top90">
					<label for="imghref">图片链接</label><em>:</em>
					<input id="imghref" placeholder="请输入新闻内容"/>
				</div>
				<div class="eachTip top135">
					<label for="imgtypename">分类名称</label><em>:</em>
					<select id="imgtypename" style="width:200px;border:0;">
						<option value="campusimg">首页左下校园实景(300*195)</option>
						<option value="slideimg">登录页焦点大图(550*300)</option>
					</select>
					<label for="imgableflag" style="left:320px;">启用标记</label><em style="left:425px;">:</em>
					<select id="imgableflag" style="width:105px;border:0;left:431px;">
						<option value="Y">启用</option>
						<option value="N">停用</option>
					</select>
				</div>
				<div class="eachTip top180">
					<label for="slideimgFile">图&nbsp;&nbsp;&nbsp;片</label><em>:</em>
					<input style="width:420px;" type="text" placeholder="请选择本地图片"/>
					<input style="width:420px;" class="file" id="slideimgFile" type="file" name="slideimgFile" accept="image/*" onchange="javascript:showFilename(this);"/>
					<input type="hidden" id="imgurl" value="">
					<input type="hidden" id="up_datetime" value="">
					<a href="#" onclick="javascript:upload(this);"><img id="uploadImg" src="${domain_resource}images/upload.png"/></a>
				</div>
				<div class="submitbtn">
					<button id="updateimgInfo" onclick="javascript:updateimgInfo(this);">保存</button>
				</div>	
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${domain_resource}manager/js/news.js"></script>
</html>