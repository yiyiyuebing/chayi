/**
 * Created by Administrator on 2016/11/9.
 */

window.canGoBack=function () {
    // history.back(-1);
    alert('调用此方法');
}

//toast弹窗
var Toast = function(config){
    this.context = config.context==null?$('body'):config.context;//上下文
    this.message = config.message;//显示内容
    this.time = config.time==null?3000:config.time;//持续时间
    this.left = config.left;//距容器左边的距离
    this.bottom = config.bottom//距容器上方的距离
    this.init();
}
var msgEntity;
Toast.prototype = {
    //初始化显示的位置内容等
    init : function(){
        $("#toastMessage").remove();
        //设置消息体
        var msgDIV = new Array();
        msgDIV.push('<div id="toastMessage">');
        msgDIV.push('<span>'+this.message+'</span>');
        msgDIV.push('</div>');
        msgEntity = $(msgDIV.join('')).appendTo(this.context);
        //设置消息样式
        var left = this.left == null ? this.context.width()/2-msgEntity.find('span').width()/2 : this.left;
        var bottom = this.bottom == null ? '45%' : this.bottom;
        msgEntity.css({position:'fixed',bottom:bottom,'z-index':'99',left:left,'background-color':'rgba(0,0,0,0.6)',color:'white','font-size':'0.8125rem',padding:'0.8rem 1rem',margin:'0','border-radius':'3px'});
        msgEntity.hide();
    },
    //显示动画
    show :function(param){
        msgEntity.fadeIn(this.time/2);
        msgEntity.fadeOut(this.time/2);
        if(!param) {
            param={
                func:function () {
                    
                }
            }
        }else {
            setTimeout(function () {
                param.func();
            },this.time)
        }

    }

}

var ToastImg = function(config){
    this.context = config.context==null?$('body'):config.context;//上下文
    this.message = config.message;//显示内容
    this.time = config.time==null?3000:config.time;//持续时间
    this.left = config.left;//距容器左边的距离
    this.bottom = config.bottom//距容器上方的距离
    this.init();
}

var msgEntity2;
ToastImg.prototype = {
    //初始化显示的位置内容等
    init : function(){
        $("#toastImg").remove();
        //设置消息体
        var msgDIV = new Array();
        msgDIV.push('<div id="toastImg">');
        msgDIV.push('<div>'+this.message+'</div>');
        msgDIV.push('</div>');
        msgEntity2 = $(msgDIV.join('')).appendTo(this.context);
        //设置消息样式
        var left = this.left == null ? this.context.width()/2-msgEntity2.find('span').width()/2 : this.left;
        var bottom = this.bottom == null ? '45%' : this.bottom;
        msgEntity2.css({'z-index':'999',position:'fixed','top':'50%','left':'50%','width':'50%','margin-left':'-25%',color:'white','font-size':'0.8125rem',padding:'0','margin-top':'-25%','border-radius':'3px'});
        msgEntity2.hide();
    },
    //显示动画
    show :function(param){

        if(!param) {
            param={
                func:function () {

                }
            }
        }else {
                param.func();
        }
        msgEntity2.fadeIn(this.time/3);
        msgEntity2.fadeOut(this.time/3);

    }

}


//confirm弹窗
function my_confirm(param) {
    if (!param) {
        param = {
            title:'提示',
            tips:"没有任何提示信息！",
            btnOk:'确定',
            btnNo:'取消',
            funcOk:function () {
            },
            funcNo:function () {
            }
        }
     }else {

    }

        var tpl='<div id="confirm" class="dialog-con">'
                    +'<div class="dialog">'
                        +'<div class="dialog-content">'
                            +'<h1>'+param.title+'</h1>'
                            +'<p class="border-b1">'
                                +param.tips
                            +'</p>'
                            +'<div class="clearfix">'
                                +'<div id="noBtn" class="fl w50 color-main border-l1">'+param.btnNo+'</div>'
                                +'<div id="okBtn" class="fl w50">'+param.btnOk+'</div>'
                            +'</div>'
                        +'</div>'
                    +'</div>'
                +'</div>';


        $("body").append(tpl);

    var okBtn=$("#okBtn");
    var noBtn=$("#noBtn");
    
    okBtn.click(function () {
        param.funcOk();
        $("#confirm").remove();
    })
    noBtn.click(function () {
        param.funcNo();
        $("#confirm").remove();
    })
}


//字数限制
function strLimit(str,len) {
    var h='';
    var j=str.replace(/(\n)/g, "").replace(/(\t)/g, "").replace(/(\r)/g, "").replace(/<\/?[^>]*>/g, "").replace(/\s*/g, "");
    h+=j.substr(0, len);
    if(j.length>len){
        h+='...';
    }
    return h;
}

//页面跳转
function jumpTo(name) {
    var url= name +'.html';
    window.location.href=url;
}


var Util = Util || {};
Util.common = {
    //测试地址
    // baseUrl: 'http://test.youchalian.com/shanguoyinyi/',
    // b2cUrlApi: 'http://123.206.213.98:9300/',

    //拟真地址
    // baseUrl: 'http://118.89.99.228:19005/shanguoyinyi/',
    // b2cUrlApi: 'http://118.89.99.228:19015/',

    // 正式地址
   baseUrl: '${server.address}/',
   b2cUrlApi: '${server.b2c}/',

    //配置全局版本号
    versionCode: 'v2.0',
    //获取html页面直接跳转参数值
    getParameter: function (name) {
        var re = new RegExp("[\?|\&]" + name + "=([^\&]*)", "i");
        var a = re.exec(document.location.search);
        if (a == null){
            return null;
        }
        return decodeURIComponent(a[1]);

    },
    //ajax回调获取post方式
    executeAjaxCallback: function (url, param, callbackFun) {
        
        $.post(Util.common.baseUrl+url, param, function (result) {
            // console.log("success\nurl:" + url + "\nparam:" + JSON.stringify(param) + "\nresult:" + JSON.stringify(result));
            callbackFun(result);
        }, 'json');
    },
    // b2c回调post方式
    b2cPostCallback: function(url, param, callbackFun) {
        $.post(Util.common.b2cUrlApi+url, param, function (result) {
            // console.log("success\nurl:" + url + "\nparam:" + JSON.stringify(param) + "\nresult:" + JSON.stringify(result));
            callbackFun(result);
        }, 'json');
    },
    //ajax回调获取get方式，不建议使用，统一使用post
    executeGetJsonAjaxCallback: function (url, param, callbackFun) {
        $.getJSON(url, param, function (result) {
            // console.log("success\nurl:" + url + "\nparam:" + JSON.stringify(param) + "\nresult:" + JSON.stringify(result));
            callbackFun(result);
        });
    },
    // setWxTitle:function(title){
    //     setTimeout(function(){
    //         $('head title').html(title);
    //     },300);
    //     //需要jQuery
    //     var $body = $('body');
    //     document.title = title;
    //     // hack在微信等webview中无法修改document.title的情况
    //     var $iframe = $('<iframe src="/favicon.ico"></iframe>');
    //     $iframe.on('load',function() {
    //         setTimeout(function() {
    //             $iframe.off('load').remove();
    //         }, 0);
    //     }).appendTo($body);
    // }
    initAdvertiseSlide:function(data){
        var isLoop = true;
        var isPagination = '.swiper-pagination';
        if (data.datas.length<2) {
            isLoop = false;
            isPagination = '';
        }
        $("#slider-content").swiper({
            slidesPerView: 1,
            autoplay: 2500,
            loop: isLoop,
            effect: 'fade',
            fade: {
                crossFade: true
            },
            speed: 1000,
            autoplayDisableOnInteraction: false,
            pagination: isPagination,
            spaceBetween: 0
        });
        $('.advertise').click(function(){
            console.info($(this).attr('type'))
            if($(this).attr('type')=='0'){
                var typeId = $(this).attr('typeId');
                var url = Util.common.baseUrl+"/weixin/common/getImgRichText.do?id="+typeId;
                Util.common.executeAjaxCallback(url, {}, function (data) {
                    $('#adtitle').html(data.title);
                    $('#adcontents').html(data.content);
                    $('#adDetailText').show().popup("open");
                });

            }
        });
    },
    loadTemplate:function(render ,templateId ,data ){
        // $(render).loadTemplate(templateId, data);
        $(render).html($(templateId).tmpl(data));
    },
    addloadingAnimate:function () {
        var tpl='<div id="spinner" class="spinner-bg">'
            +'<div class="spinner">'
            +'<div class="spinner-container container1">'
            +'<div class="circle1"></div>'
            +'<div class="circle2"></div>'
            +'<div class="circle3"></div>'
            +'<div class="circle4"></div>'
            +'</div>'
            +'<div class="spinner-container container2">'
            +'<div class="circle1"></div>'
            +'<div class="circle2"></div>'
            +'<div class="circle3"></div>'
            +'<div class="circle4"></div>'
            +'</div>'
            +'<div class="spinner-container container3">'
            +'<div class="circle1"></div>'
            +'<div class="circle2"></div>'
            +'<div class="circle3"></div>'
            +'<div class="circle4"></div>'
            +'</div>'
            +'</div>'
            +'<p class="spinner-text ta-c color-main fs-14">加载中</p>'
            +'</div>';
        $("body").append(tpl);
    },
    removeloadingAnimate:function (id) {
        $("#"+id).removeClass('op0');
        $("#spinner").remove();

    }
};
//处理列表页到详情页返回记录预览位置
Util.scrollMar = {
    setPos: function(pageId , pos , level, style) {
       var scrollObj = JSON.parse(window.sessionStorage.getItem("scrollObj") || '{}');
    //    if (!scrollObj[pageId]) {
    //         scrollObj[pageId] = {
    //             pos: pos,
    //             level: level,
    //             style: true
    //         };
    //    } else {
           scrollObj[pageId] = {
                pos: pos,
                level: level, 
                style: style
            };
    //    }
        window.sessionStorage.setItem("scrollObj",JSON.stringify(scrollObj));
    },
    init: function(pageId, callback) {
        var scrollObj = JSON.parse(window.sessionStorage.getItem("scrollObj"));
        var page = scrollObj[pageId] || {style: true};
        var pl = page.level;
        callback(page);
        for(x in scrollObj) {
            if (scrollObj[x].level > pl) {
                scrollObj[x].pos = 0;
            }
        }
        window.sessionStorage.setItem("scrollObj", JSON.stringify(scrollObj));
    },
}

/**
 * 获取请求参数列表
 * @returns {{}}
 */
function getRequestParameters(){
    var arr = (location.search || "").replace(/^\?/,'').split("&");
    var params = {};
    for(var i=0; i<arr.length; i++){
        var data = arr[i].split("=");
        if(data.length == 2){
            params[data[0]] = data[1];
        }
    }
    return params;
}
function getRequestParameter(key){
    var params = getRequestParameters();
    return params[key];
}

