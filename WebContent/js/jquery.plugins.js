(function($){
	/*
	 jquery公共插件
	 依赖文件:jquery.js
	*/
	$.fn.extend({
		/**************
		
		name:jquery.slide.js
		description:轮播图插件（可自定义动画方式、主题样式、轮播图尺寸、支持全屏幻灯片）
		author:gzd
		date：2014.4.4
		update:2014.6.4-增加全屏模式下窗口自适应宽度
		
		参数设定：1、data(必须)-图片的url(地址)、href(链接)、title(描述)、target(链接打开方式),desc(图片描述，必须设置参数desc:true时才有效),格式如下：
			  data:[{url:"images/1.jpg",href:"www.trip8080.com",title:"畅途网",target:"_blank",desc:"畅途网-长途汽车票预订_长途汽车时刻表_汽车票查询"}]
			  2、delay(可选)-动画间隔时间，默认为4000毫秒
			  3、duration(可选)-执行一次动画所需的时间，默认为600毫秒
			  4、iconEvent(可选)-按钮切换触发时间，默认为鼠标点击
			  5、width(可选)-轮播图宽度，根据实际轮播图的宽度设定，默认为960
			  6、height(可选)-轮播图的高度，根据实际轮播图的高度设定，默认为300
			  7、theme(可选)-样式主题，用于控制轮播图的样式，默认为default
			  8、shiftBtn(可选)-是否显示左右切换按钮，默认显示
			  9、desc(可选)-是否显示文字描述，默认为不显示
			  10、maskBg(可选)-是否显示半透明背景，默认不显示
			  11、iconBtn(可选)-是否显示小图标按钮，默认显示
			  12、animateStyle(可选)-动画方式，fade:淡入淡出(默认), slide-x:横向滚动 ，slide-y:纵向滚动，slide-x-seamless:横向无缝滚动 ，slide-y-seamless:纵向无缝滚动，none：无动画 
			  13、fullScreen(可选)-是否支持全屏幻灯片，默认不启用，如果启用全屏幻灯片那么参数width可以不用设置了
	
		*************/
		slide:function(options){
			var defaults={
				delay:4000,   //动画间隔时间
				duration:600,  //执行一次动画的时间
				iconEvent:'mouseover',  //按钮切换触发事件
				width:960,
				height:300,
				theme:"default",
				shiftBtn:false,//是否显示左右切换按钮
				desc:false,//是否显示文字描述
				maskBg:false, //是否显示背景
				iconBtn:true,//是否显示小图标按钮
				animateStyle:'fade', //动画效果：fade:淡入淡出, slide-x:横向滚动 ，slide-y:纵向滚动，slide-x-seamless:横向无缝滚动 ，slide-y-seamless:纵向无缝滚动，none：无动画
				fullScreen:false   //是否支持全屏
			};
			var settings=$.extend({},defaults,options);
			var $this=this;
			var timer=null;
			var imgSize=1920;
			var index=0,iNow=0;
			var itemLen=settings.data.length; //多少条数据
			var sWidth=document.documentElement.clientWidth||document.body.clientWidth; //屏幕可视区宽度
			var oAstyle=settings.animateStyle;
			var oWidth=settings.fullScreen?sWidth:settings.width; //如果启用全屏模式，则宽度为屏幕可视区宽度
			//窗口大小发生变化时，调整宽度
			$(window).resize(function(){
				sWidth=document.documentElement.clientWidth||document.body.clientWidth; //屏幕可视区宽度
				oWidth=settings.fullScreen?sWidth:settings.width;
			})
			var oHeight=settings.height;
	
			if(!options.data) return false;  //如果没有图片数据则返回
			var con=$("<div></div>").addClass("tripSlide_"+settings.theme).width(oWidth).height(oHeight).appendTo($this);
			var container=$("<div class='slide-container'></div>").width(oWidth).height(oHeight).appendTo(con);
			//窗口大小发生变化时，调整宽度
			$(window).resize(function(){
				con.width(oWidth);
				container.width(oWidth);
			})
			var wrap=$("<div class='slide-wrap'></div>").appendTo(container);
			if(settings.shiftBtn&&itemLen>1){ //大于一条数据才生成切换按钮
				var btn=$("<div class='slide-nav'><a href='javascript:;' class='prev'><i class='ico i-prev'></i></a><a href='javascript:;' class='next'><i class='ico i-next'></i></a></div>").appendTo(con);
			}
			if(settings.maskBg){
				var maskBg=$("<div class='slide-maskBg'></div>").appendTo(con);
			}
			if(settings.desc){
				var desc=$("<div class='slide-desc'></div>").appendTo(con);
			}
			if(settings.iconBtn&&itemLen>1){ //大于一条数据生成icon包裹
				if(settings.fullScreen){
					var iconWrap=$("<div class='slide-icon-wrap'></div>").appendTo(con);
				}else{
					var iconWrap=con;	
				}
				var icon=$("<div class='slide-icon'></div>").appendTo(iconWrap);
			}
			var items=null;
			var aIcon=null;
			$.each(settings.data,function(i,n){
				var oHref=n.href?n.href:"#";  //图片链接
				var oTitle=n.title?n.title:""; //图片title，alt信息
				var imgWidth = n.imgWidth?n.imgWidth:"";
				var imgHeight = n.imgHeight?n.imgHeight:"";
				var chtts = n.chtts?n.chtts:"";
				var imgtts = n.tts?n.tts:"";
				var oTarget=n.target?n.target:"_blank"; //链接打开方式
				var item=$("<div class='slide-item'></div>").width(oWidth).height(oHeight).appendTo(wrap);
				if (oHref == "#"){
					var itemInner=$("<a  rel='nofollow' href='javascript:void(0);' title='"+oTitle+"' class='banner'></a>").width(oWidth).height(oHeight).appendTo(item);
				}else{
					var itemInner=$("<a  rel='nofollow' target='"+oTarget+"' href='"+oHref+"' title='"+oTitle+"' class='banner'></a>").width(oWidth).height(oHeight).appendTo(item);
				}
				if(imgWidth&&imgHeight&&imgtts){
					var itemImg=$("<img src='"+n.url+"' tts='"+imgtts+"' width='"+imgWidth+"' height='"+imgHeight+"'  alt='"+oTitle+"' />").appendTo(itemInner);	
				}else{
					var itemImg=$("<img src='"+n.url+"' alt='"+oTitle+"' />").appendTo(itemInner);
				}
				
				if(settings.fullScreen){
					itemImg.css({"position":"relative","left":-(imgSize-oWidth)/2});
					//窗口大小发生变化时，调整宽度
					$(window).resize(function(){
						item.width(oWidth);
						itemInner.width(oWidth);
						itemImg.css({"left":-(imgSize-oWidth)/2});
					})
				}
				if(settings.iconBtn&&itemLen>1){ //大于一条数据才生成切换icon
					if(chtts){
						$("<a tts='"+ chtts +"' href='javascript:;'>"+(i+1)+"</a>").appendTo(icon);
					}else{
						$("<a href='javascript:;'>"+(i+1)+"</a>").appendTo(icon);
					}
				}
				if(settings.desc){
					var oDesc=n.desc?n.desc:"";
					if (oHref == "#"){
						$("<a rel='nofollow' href='javascript:void(0)' title='"+oTitle+"'>"+oDesc+"</a>").appendTo(desc);
					}else{
						$("<a rel='nofollow' href='"+oHref+"' title='"+oTitle+"' target='"+oTarget+"'>"+oDesc+"</a>").appendTo(desc);
					}
				}
			});
			var oSlideNav=con.find('.slide-nav');
			var preBtn=con.find('.slide-nav .prev');
			var nextBtn=con.find('.slide-nav .next');
			var items=con.find('.slide-item');
			var aIcon=con.find('.slide-icon a');
			var aDesc=con.find('.slide-desc a');
			var len=items.length;
			
			//不同动画方式
			if(oAstyle=="slide-x"||oAstyle=="slide-x-seamless"){
				wrap.width(oWidth*len).height(oHeight);
				//窗口大小发生变化时，调整宽度
				$(window).resize(function(){
					wrap.width(oWidth*len);
				})
				items.css({"float":"left"});
			}else if(oAstyle=="slide-y"||oAstyle=="slide-y-seamless"){
				wrap.width(oWidth).height(oHeight*len);
				//窗口大小发生变化时，调整宽度
				$(window).resize(function(){
					wrap.width(oWidth);
				})
				items.css({"float":"left"});
			}else{
				wrap.width(oWidth).height(oHeight);
				//窗口大小发生变化时，调整宽度
				$(window).resize(function(){
					wrap.width(oWidth);
				})
				items.css({"position":"absolute","top":0,"left":0});
				items.hide().eq(0).show();
			}
			aIcon.removeClass('current').eq(0).addClass('current');
			aDesc.hide().eq(0).show();
	
			//轮播动画函数
			function imageChange(){
				iNow=index;
				if(index==len){
					index=0;
					if(oAstyle=="slide-x-seamless"){
						items.eq(0).css({"position":"relative","left":oWidth*len});
					}else if(oAstyle=="slide-y-seamless"){
						items.eq(0).css({"position":"relative","top":oHeight*len});
					}
				}else if(index==-1){
					index=len-1;
					if(oAstyle=="slide-x-seamless"){
						items.eq(len-1).css({"position":"relative","left":-oWidth*len});
					}else if(oAstyle=="slide-y-seamless"){
						items.eq(len-1).css({"position":"relative","top":-oHeight*len});
					}
				}
				//判断不同动画方式
				if(oAstyle=="none"){
					items.hide().eq(index).show();
				}else if(oAstyle=="slide-x"){
					wrap.stop().animate({"left":-oWidth*index},settings.duration);
				}else if(oAstyle=="slide-y"){
					wrap.stop().animate({"top":-oHeight*index},settings.duration);
				}else if(oAstyle=="slide-x-seamless"){
					wrap.stop().animate({"left":-oWidth*iNow},settings.duration,function(){
						if(iNow==len){
							iNow=0;
							items.eq(0).css({"position":"static","left":0});
							wrap.css({"left":0});
						}else if(iNow==-1){
							iNow=len-1;
							items.eq(len-1).css({"position":"static","left":0});
							wrap.css({"left":-oWidth*(len-1)});
						}
					});
				}else if(oAstyle=="slide-y-seamless"){
					wrap.stop().animate({"top":-oHeight*iNow},settings.duration,function(){
						if(iNow==len){
							iNow=0;
							items.eq(0).css({"position":"static","top":0});
							wrap.css({"top":0});
						}else if(iNow==-1){
							iNow=len-1;
							items.eq(len-1).css({"position":"static","top":0});
							wrap.css({"top":-oHeight*(len-1)});
						}
					});
				}else{
					items.fadeOut(settings.duration).eq(index).fadeIn(settings.duration);	
				}
				aIcon.removeClass('current').eq(index).addClass('current');
				aDesc.hide().eq(index).show();
			}
			//icon小按钮切换轮播图
			aIcon.bind(settings.iconEvent,function(){
				var curIndex=aIcon.index(this);
				if(curIndex!=index){
					index=curIndex;
					imageChange();	
				}
			})
	
			//切换至前一张幻灯片
			preBtn.bind('click',function(){
				index--;
				imageChange();	
			})
			//切换至后一张幻灯片
			nextBtn.bind('click',function(){
				index++;
				imageChange();
			})
			//鼠标移入轮播图暂停，移出轮播图开始轮播
			if(itemLen>1){
				con.hover(function(){
					oSlideNav.show();
					clearInterval(timer);
				},function(){
					oSlideNav.hide();
					timer=setInterval(function(){
						index++;	
						imageChange();
					},settings.delay)
				}).trigger('mouseleave');//触发一次移出效果，轮播图自动播放	
			}
		},
	
	
		/**************
	
		name:jquery.autoRoll.js
		description:-滚动公告插件（可自定义滚动的方向、每一次滚动的数目）
		version:1.0
		author:gzd
		date:2014.4.10
		
		用法：在用于包裹所有项目的标签中添加属性type="rollCon",在每个项目中添加属性rel="item",示例如下：
		<ul type="rollCon">
			<li rel="item">1111111111111111</li>
			<li rel="item">2222222222222222</li>
			<li rel="item">3333333333333333</li>
			<li rel="item">4444444444444444</li>
			<li rel="item">5555555555555555</li>
			<li rel="item">6666666666666666</li>
		</ul>
		
		
	**************/
		autoRoll:function(option){
			var $this=this;
			var timer=null;
			var defaults={
				delay:2000,			//每一次动画的时间间隔
				duration:500,       //完成一次动画所需时间
				direction:"bottom", //滚动方向：top-向上滚动,bottom-向下滚动,left-向左滚动,right-向右滚动
				rollNum:1,          //每一次滚动的数目
				fade:false          //是否淡入,只有当direction:"bottom"或者"right" 时才生效
			};
			var opt=$.extend({},defaults,option);
			var obj=$this.find("[type='rollCon']");
			var items=obj.find("[rel='item']");
			var iNum=items.length;
			var lineH=items.outerHeight(true);  //每行的高度
			var lineW=items.outerWidth(true);  //每行的宽度
			var _direct=opt.direction;
			var _rollNum=opt.rollNum;
			var _fade=opt.fade;
			if(_direct=="left"||_direct=="right"){
				items.css({"float":"left"});
				obj.width(iNum*lineW);
			}
			function roll(){
				//根据不同方向分类
				if(_direct=="bottom"){
					for(var i=0;i<_rollNum;i++){
						obj.find("li:eq("+(iNum-1)+")").prependTo(obj);
					}
					obj.css("margin-top",-_rollNum*lineH);
					//支持淡入
					if(_fade){
						for(var i=0;i<_rollNum;i++){
							obj.find("li:eq("+i+")").css({"opacity":0});
						}
					}
					obj.animate({"margin-top":0},opt.duration,function(){
						if(_fade){
							for(var i=0;i<_rollNum;i++){
								obj.find("li:eq("+i+")").animate({"opacity":1});
							}
						}
					});
				}else if(_direct=="left"){
					obj.animate({"margin-left":-_rollNum*lineW},opt.duration,function(){
						for(var i=0;i<_rollNum;i++){
							obj.find("li:eq(0)").appendTo(obj);
						}
						obj.css("margin-left",0);
					});
				}else if(_direct=="right"){
					for(var i=0;i<_rollNum;i++){
						obj.find("li:eq("+(iNum-1)+")").prependTo(obj);
					}
					obj.css("margin-left",-_rollNum*lineW);
					//支持淡入
					if(_fade){
						for(var i=0;i<_rollNum;i++){
							obj.find("li:eq("+i+")").css({"opacity":0});
						}
					}
					obj.animate({"margin-left":0},opt.duration,function(){
						if(_fade){
							for(var i=0;i<_rollNum;i++){
								obj.find("li:eq("+i+")").animate({"opacity":1});
							}
						}
					});
				}else{
					obj.animate({"margin-top":-_rollNum*lineH},opt.duration,function(){
						for(var i=0;i<_rollNum;i++){
							obj.find("li:eq(0)").appendTo(obj);
						}
						obj.css("margin-top",0);
					});
				}
			}

			$this.hover(function(){
				clearInterval(timer);
			},function(){
				timer=setInterval(roll,opt.delay);
			}).trigger("mouseleave");
		},
		
		
		/**************
	
		name:jquery.listRoll.js
		description:-列表滚动插件（可修改每一次滚动的数目，可见数目以及滚动的动画风格）
		version:1.0
		author:gzd
		date:2014.6.3
			
		用法：在用于包裹所有项目的标签中添加属性data-hook="listWrap",向前翻的标签添加data-hook="btnPrev",向后翻的标签添加data-hook="btnNext"，示例如下：
		<a href="javascript:;" class="btn_prev" data-hook="btnPrev"></a>
    	<div class="list_main">
        	<ul class="list_wrap clearfix" data-hook="listWrap">
            	<li>1</li>
                <li>2</li>
                <li>3</li>
                <li>4</li>
                <li>5</li>
                <li>6</li>
                <li>7</li>
                <li>8</li>
            </ul>
        </div>
        <a href="javascript:;" class="btn_next" data-hook="btnNext"></a>
		
	    **************/
		listRoll:function(option){
			//默认参数
			var defaults={
				rollNum:1, //每一次滚动的数目
				visibleNum:4, //可见的数目
				autoPlay:true, //是否自动播放
				animateStyle:"normal",  //normal默认滚动，cover-覆盖第一个滚动，compress-压缩滚动
				duration:700,     //执行一次滚动所需时间
				delay:3000,     //每一次滚动之间的时间间隔
				easing:"swing"   //动画方式
			};
			var opts=$.extend({},defaults,option);
			var container=$(this),
			btnPrev=container.find("[data-hook='btnPrev']"),
			btnNext=container.find("[data-hook='btnNext']"),
			listWrap=container.find("[data-hook='listWrap']"),
			items=listWrap.children(),      //每一个项目
			len=items.length,
			itemWidth=items.eq(0).outerWidth(),
			itemOuterWidth=items.eq(0).outerWidth(true),
			itemMarginLeft=parseInt(items.eq(0).css("margin-left")),     //每一项的margin值，用于计算listWrap的位置和其父元素的宽度
			itemMarginRight=parseInt(items.eq(0).css("margin-right")),   
			timer=null;
			var _rollNum=opts.rollNum;
			var _visibleNum=opts.visibleNum;
			listWrap.width(itemOuterWidth*len).css("margin-left",-itemMarginLeft);    //设定wrap的宽度
			listWrap.parent().width(itemOuterWidth*_visibleNum-itemMarginLeft-itemMarginRight);  //设定wrap父元素的宽度
			//向前滚动方法
			function prevRoll(){
				for(var i=0;i<_rollNum;i++){
					listWrap.children(":eq("+(len-1)+")").prependTo(listWrap);
				}
				listWrap.css("margin-left",-_rollNum*itemOuterWidth-itemMarginLeft);
				listWrap.stop().animate({"margin-left":0-itemMarginLeft},opts.duration,opts.easing);
			}
			//向后滚动方法
			function nextRoll(){
				if(opts.animateStyle=="cover"){
					listWrap.children(":eq(1)").stop().animate({"margin-left":-itemWidth-itemMarginRight},opts.duration,opts.easing,function(){
						listWrap.children(":eq(0)").appendTo(listWrap);
						listWrap.children(":eq(0)").css("margin-left",itemMarginLeft);
					});
				}else if(opts.animateStyle=="compress"){
					listWrap.children(":eq(0)").stop().animate({"width":0,"margin-right":0},opts.duration,opts.easing,function(){
						listWrap.children(":eq(0)").css({"width":itemWidth,"margin-right":itemMarginRight}).appendTo(listWrap);
					});
				}else{
					listWrap.stop().animate({"margin-left":-_rollNum*itemOuterWidth-itemMarginLeft},opts.duration,opts.easing,function(){
						for(var i=0;i<_rollNum;i++){
							listWrap.children(":eq(0)").appendTo(listWrap);
							listWrap.css("margin-left",0-itemMarginLeft);
						}
					});
				}
			}
			//鼠标移入移出容器操作
			if(opts.autoPlay){
				container.hover(function(){
					clearInterval(timer);
				},function(){
					timer=setInterval(function(){
						nextRoll();
					},opts.delay);
				}).trigger("mouseout");
			}
			
			//上翻下翻
			btnPrev.click(function(){
				prevRoll();
			});
			
			btnNext.click(function(){
				nextRoll();
			});
		},
		
		/**************
	
		name:jquery.marquee.js
		description:-无缝滚动插件（可修改每一次滚动速度）
		version:1.0
		author:gzd
		date:2014.6.5
			
		**************/
		marquee:function(option){
			var opts=$.extend({},{speed:1},option)
			var $this=$(this);
			if(!$this.length) return;
			var $wrap=$this.find("[data-hook='marqueeWrap']"),
			$item=$wrap.children(),
			len=$item.length,
			conWidth=$this.width(),
			timer=null;
			var itemWidth=0;
			$.each($item,function(i,n){
				itemWidth+=$(n).outerWidth(true);
			})
			if(itemWidth<=conWidth) return;
			var delta=conWidth/(itemWidth);
			var cloneTime=Math.ceil(delta);    //计算克隆次数
			for(var i=0;i<cloneTime;i++){
				$item.clone().appendTo($wrap);
			}
			$wrap.width(itemWidth*len*(cloneTime+1));
			function move(){
				if($wrap[0].offsetLeft<-itemWidth){
					$wrap[0].style.left=0;
				}
				$wrap[0].style.left=$wrap[0].offsetLeft-opts.speed+"px";
			}
			$this.hover(function(){
				clearInterval(timer);
			},function(){
				timer=setInterval(move,80);
			}).trigger("mouseleave");
		},
		
		/**************
	
		name:jquery.tabMenu.js
		description:-tab切换插件
		version:1.0
		author:gzd
		date:2014.6.5
			
		**************/
		tabMenu:function(option){
			var defaults={
				cntSelect:".content_wrap",
				tabEvent:"mouseover",
				tabStyle:"normal",
				direction:"top",
				aniMethod:"swing",
				aniSpeed:"fast",
				onStyle:"current",
				menuChildSel:"*",
				cntChildSel:"*"
			}
			
			var opts=$.extend({},defaults,option );
	
			return this.each(function(i){
				var _this=$(this);
				var $menus=_this.children( opts.menuChildSel );
				var $container=$( opts.cntSelect ).eq(i);
				
				if( !$container) return;
				
				if( opts.tabStyle=="move"||opts.tabStyle=="move-fade"||opts.tabStyle=="move-animate" ){
					var step=0;
					if( opts.direction=="left"){
						step=$container.children().children( opts.cntChildSel ).outerWidth(true);
					}else{
						step=$container.children().children( opts.cntChildSel ).outerHeight(true);
					}
				}
				
				if( opts.tabStyle=="move-animate" ){ var animateArgu=new Object();	}
					
				$menus[opts.tabEvent](function(){
					var index=$menus.index($(this));
					$(this).addClass(opts.onStyle).siblings().removeClass( opts.onStyle );
					switch(opts.tabStyle){
						case "fade":
							if(!($container.children( opts.cntChildSel ).eq( index ).is(":animated"))){
								$container.children( opts.cntChildSel ).eq( index ).siblings().css( "display", "none").end().stop( true, true ).fadeIn( opts.aniSpeed );
							}
							break;
						case "move-animate":
							animateArgu[opts.direction]=-step*index+"px";
							$container.children(opts.cntChildSel).stop(true).animate( animateArgu,opts.aniSpeed,opts.aniMethod );
							break;
						default:
							$container.children(opts.cntChildSel).eq( index ).css("display","block").siblings().css("display","none");
					}
				});
				$menus.eq(0)[opts.tabEvent]();
			});
		},
		
		//返回顶部
		toTop: function(opt) {
			var $this = $(this);
			var defaults = {
				toTopContainer:this,  //默认点击容器为自身，也可用class或ID指定回到顶部的点击容器
				animateStyle:"fade", 
				duration:700
			};
			var setting = $.extend({}, defaults, opt);
			$(window).scroll(function() {
				var sHeight = $(document).scrollTop();
				var wHeight = window.screen.availHeight;
				switch(setting.animateStyle){
					case "fade":
						if (sHeight >= wHeight/2) {
							$this.fadeIn();
						} else {
							$this.fadeOut();
						}
						break;
					case "slide":
						if (sHeight >= wHeight/2) {
							$this.slideDown();
						} else {
							$this.slideUp();
						}
						break;
					default:
						if (sHeight >= wHeight/2) {
							$this.show();
						} else {
							$this.hide();
						}
				}
			})
			if (setting.toTopContainer) {
				$(setting.toTopContainer).click(function() {
					$("html,body").animate({scrollTop: 0}, setting.duration);
				})
			}
		}

	
	})
})(jQuery);

(function($) {

    /* Changes for 3.0
     - return removed (it was depreciated)
     - passing arguments changed (id,s,e)
     - refresh of code speed and accuracy
     - items now object instead of id's
     - multiple tabs can now point to same id
     - removed autoloading jQuery
     - added item classes support
     - toggle visibility
     - update or remove idTabs
     - grouped areas
     - extend idTabs
    */

    /* Options (in any order):

     start (number|string)
        Index number of default tab. ex: $(...).idTabs(0)
        String of id of default tab. ex: $(...).idTabs("#tab1")
        default: class "selected" or index 0
        Passing -1 or null will force it to not select a default tab

     change (boolean)
        True - Url will change. ex: $(...).idTabs(true)
        False - Url will not change. ex: $(...).idTabs(false)
        default: false

     click (function)
        Function will be called when a tab is clicked. ex: $(...).idTabs(function(id,s){...})
        If the function returns false, idTabs will not take any action,
        otherwise idTabs will show/hide the content (as usual).
        The function is passed three variables:
          The id of the element to be shown
          The settings object which has the following additional items:
            area     - the original area $(area).idTabs();
            tabarea  - the current tab area used to find tabs
            tabs     - a jQuery list of tabs
            items    - a jQuery list of the elements the tabs point to
            tab(id)  - a helper function to find a tab with id
            item(id) - a helper function to find an item with id
          The event object that triggered idTabs

     selected (string)
        Class to use for selected. ex: $(...).idTabs(".current")
        default: ".selected"

     event (string)
        Event to trigger idTabs on. ex: $(...).idTabs("!mouseover")
        default: "!click"
        To bind multiple events, call idTabs multiple times
          ex: $(...).idTabs("!click").idTabs("!focus")

     toggle (boolean)
        True - Toggle visibility of tab content. ex: $(...).idTabs("!true")
        False - Ignore clicks on tabs already selected. ex: $(...).idTabs("!false")
        default: false

     grouped (boolean)
        True - Groups all tabs in area together. ex: $(...).idTabs(":grouped")
        False - jQuery selector is seperated into tab areas. ex: $(...).idTabs(":!grouped")
        default: false

     update (boolean)
        True - Rebinds idTabs ex: $(...).idTabs(":update");
        False - Cancels update ex: $(...).idTabs(":!update");

     remove (boolean)
        True - Removes idTabs ex: $(...).idTabs(":remove");
        False - Cancels removal ex: $(...).idTabs(":!remove");

    */

    // Helper functions
    var idTabs; //shortcut
    var undefined; //speed up
    var href = function(e) {
        return $(e).attr("rel");
    }
    var type = function(o) { //reliable
        return o === null && "Null" || o === undefined && "Undefined" || ({}).toString.call(o).slice(8, -1);
    };

    $.fn.idTabs = function() {
        var s = idTabs.args.apply(this, arguments);
        var action = s.update && "update" || s.remove && "remove" || "bind";
        s.area = this; //save context
        idTabs[action](s);
        return this; //chainable
    };

    idTabs = $.idTabs = function(tabarea, options, data) {
        // Settings
        var e, tabs, items, test = $(), meta = $.metadata ? $(tabarea).metadata() : {}; //metadata
        var s = { tab: idTabs.tab, item: idTabs.item }; //helpers
        s = $.extend(s, idTabs.settings, meta, options || {}); //settings
        s.tabarea = $(tabarea); //save context
        s.data = data || "idTabs" + +new Date; //save expando

        // Play nice
        $.each({ selected: '.', event: '!', start: '#' }, function(n, c) {
            if (type(s[n]) == "String" && s[n].indexOf(c) == 0)
                s[n] = s[n].substr(1);
        }); //removes type characters
        if (s.start === null) s.start = -1; //no tab selected

        // Find tabs
        items = []; //save elements
        //s.tabs = tabs = $("a[href^=#]", tabarea); //save tabs
        s.tabs = tabs = $("li[rel]", tabarea); //save tabs
        tabs.each(function() { //add items
            test = s.item(href(this));
            if (test.length) items = items.concat(test.get());
        });
        s.items = $(items).hide(); //hide items

        // Save Settings
        e = "idTabs." + s.event;
        data = s.tabarea.data("idTabs") || {};
        data[e] = s;
        s.tabarea.data("idTabs", data);

        // Bind idTabs
        tabs.trigger(e).data(s.data, s).bind(e, { s: s }, function() { //wrapper function due to jQuery bug
            return idTabs.unbind.apply(this, arguments);
        }).bind(s.event, { s: s }, idTabs.find);

        // Select default tab
        type(s.start) == "Number" && (s.start < 0 || (test = tabs.eq(s.start)).length)
          || type(s.start) == "String" && (test = tabs.filter("a[href=#" + s.start + "]")).length
          || (test = tabs.filter('.' + s.selected).removeClass(s.selected)).length
          || (s.start === undefined && (test = tabs.eq(0)).length);
        if (test.length) test.trigger(s.event);

        return s; //return current settings (be creative)
    };

    // Parse arguments into settings
    idTabs.args = function() {
        var a, i = 0, s = {}, args = arguments;
        // Handle string flags .!:
        var str = function(_, a) {
            if (a.indexOf('.') == 0) s.selected = a;
            else if (a.indexOf('!') == 0)
                if (/^!(true|false)$/i.test(a)) s.toggle = /^!true$/i.test(a);
            else s.event = a;
            else if (a.indexOf(':') == 0) {
                a = a.substr(1).toLowerCase();
                if (a.indexOf('!') == 0) s[a.substr(1)] = false;
                else s[a] = true;
            } else if (a) s.start = a;
        };
        // Loop through arguments matching options
        while (i < args.length) {
            a = args[i++];
            switch (type(a)) {
                case "Object": $.extend(s, a); break;
                case "Boolean": s.change = a; break;
                case "Number": s.start = a; break;
                case "Function": s.click = a; break;
                case "Null": s.start = a; break;
                case "String": $.each(a.split(/\s+/g), str);
                default: break;
            }
        }
        return s; //settings object
    };

    // Bind idTabs
    idTabs.bind = function(s) {
        if (!s) return;
        var data = "idTabs" + +new Date; //instance expando
        if (s.grouped) $.idTabs(s.area, s, data);
        else s.area.each(function() { $.idTabs(this, s, data); });
    };

    // Rebind idTabs
    idTabs.update = function(s) {
        if (!s) return;
        s.update = false;
        var self, data, n, e = s.event;
        e = (e + "").indexOf('!') == 0 && e.substr(1) || e;
        e = e ? "idTabs." + e : "";
        return s.area.each(function() {
            self = $(this);
            data = self.data("idTabs");
            if (!data) return;
            if (e) {
                n = $.extend({}, data[e], s);
                idTabs.remove(data[e])
                idTabs(n.tabarea, n, n.data);
            } else for (e in data) {
                if (!Object.hasOwnProperty.call(data, e)) continue;
                n = $.extend({}, data[e], s);
                idTabs.remove(data[e]);
                idTabs(n.tabarea, n, n.data);
            }
        });
    };

    // Unbind idTabs
    idTabs.remove = function(s) {
        if (!s) return;
        var data, tabs, e = s.event;
        e = (e + "").indexOf('!') == 0 && e.substr(1) || e;
        e = "idTabs" + (e ? "." + e : "");
        return s.area.each(function() {
            data = $(this).data("idTabs");
            delete data["idTabs." + s.event];
            $(this).data("idTabs", data);
            //tabs = s.tabs || $("a[href^=#]", this); //save tabs
            tabs = s.tabs || $("li[rel]", this);
            if (!tabs.length && $(this).is("li[rel]")) tabs = $(this);
            tabs.trigger(e);
        });
    };

    // Find tabs
    idTabs.find = function(e) {
        // Save self since clicked tab may not be the first tab in the tabarea
        var self = this, ret = false, s = e.data.s;
        // Find first tab within each tabset
        $("li[rel=" + href(this) + "]:first", s.area).each(function() {
            var t = $(this).data(s.data); //tab's settings
            if (t) ret = idTabs.showtab.call(t.tabarea == s.tabarea ? self : this, t, e) || ret;
        });
        return ret;
    };

    // Show tab
    idTabs.showtab = function(s, e) {
        if (!s || !s.toggle && $(this).is('.' + s.selected))
            return s && s.change; //return if already selected
        var id = href(this); //find id
        if (s.click && s.click.call(this, id, s, e) == false) return s.change; //call custom func
        if (s.toggle && $(this).is('.' + s.selected)) id = null; //hide items
        return idTabs.show.call(this, id, s, e); //call default func
    };

    // Show item
    idTabs.show = function(id,s){
      s.tabs.removeClass(s.selected); //clear tabs
      s.tab(id).addClass(s.selected); //select tab(s)
      if(s.fade){
          s.items.hide(); //hide all items
          s.item(id).fadeIn(); //show item(s)
      }else{
          s.items.hide(); //hide all items
          s.item(id).show(); //show item(s)        
      }
      return s.change; //option for changing url
    };

    // Unbind idTabs
    idTabs.unbind = function(e) {
        var s = e.data.s;
        $(this).removeData(s.data).unbind("idTabs." + s.event);
        return false;
    };

    // Extend idTabs
    idTabs.extend = function() {
        var args = arguments;
        return function() {
            [ ].push.apply(args, arguments);
            this.idTabs.apply(this, args);
        };
    };

    // Matching tabs
    idTabs.tab = function(id) {
        if (!id) return $([]);
        return $("li[rel=" + id + "]", this.tabarea);
    };

    // Matching items
    idTabs.item = function(id) {
        if (!id) return $([]);
        var item = $("#" + id);
        return item.length ? item : $('.' + id.substr(1));
    };

    // Defaults
    idTabs.settings = {
        start: undefined,
        change: false,
        click: null,
        selected: ".selected",    //当前选中tab样式class,默认为：selected
        event: "!click",         //鼠标出发事件，可选值：1、!click-鼠标点击触发(默认)， 2、!mouseover-鼠标移入触发 
        grouped: false,
        fade:false             //是否淡入淡出，默认为false
    };

    // Version
    idTabs.version = "3.0";

})(jQuery);