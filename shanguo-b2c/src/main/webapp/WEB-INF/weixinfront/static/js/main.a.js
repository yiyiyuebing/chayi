/**
 * Created by Administrator on 2016/11/9.
 */

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
        msgDIV.push('<span data-type="message">'+this.message+'</span>');
        msgDIV.push('</div>');
        msgEntity = $(msgDIV.join('')).appendTo(this.context);
        //设置消息样式

        var bottom = this.bottom == null ? '15%' : this.bottom;
        msgEntity.css({position:'fixed',bottom:bottom,'z-index':'99',left:"50%", 'background-color':'rgba(0,0,0,0.6)',color:'white','font-size':'0.8125rem',padding:'0.8rem 1rem','border-radius':'3px'});
        msgEntity.hide();
    },
    setMessage:function(message){
        this.message = message;
        msgEntity.find("[data-type='message']").text(message);
        return this;
    },
    //显示动画
    show :function(param){

        msgEntity.stop(true);
        msgEntity.hide();
        var width  = msgEntity.outerWidth(false); //不包含margin
        console.log(width);
        msgEntity.css({'margin-left':"-" + width /2 + "px"});
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
function set_html(id,content) {
    var _id ="#" + id;
    $(_id).html(content);
}



var Util = Util || {};
Util.common = {
//   baseUrl: 'http://dev.youchalian.com/shanguoyinyi/',
   baseUrl: '${server.address}/',
//   baseUrl: 'http://demo.youchalian.com/shanguoyinyi/',
//	baseUrl: 'http://192.168.104.242:9070/',
    //baseUrl: 'http://192.168.0.186:8080/shanguoyinyi/',

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
            console.log("success\nurl:" + url + "\nparam:" + JSON.stringify(param) + "\nresult:" + JSON.stringify(result));
            callbackFun(result);
        }, 'json');
    },
    //ajax回调获取get方式，不建议使用，统一使用post
    executeGetJsonAjaxCallback: function (url, param, callbackFun) {
        $.getJSON(url, param, function (result) {
            console.log("success\nurl:" + url + "\nparam:" + JSON.stringify(param) + "\nresult:" + JSON.stringify(result));
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
    initAdvertiseSlide:function(){
        $("#slider-content").swiper({
            slidesPerView: 1,
            autoplay: 2500,
            loop: true,
            effect: 'fade',
            fade: {
                crossFade: true
            },
            speed: 1000,
            autoplayDisableOnInteraction: false,
            pagination: '.swiper-pagination',
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
};


