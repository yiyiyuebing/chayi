/**
 * Created by Administrator on 2017/5/11.
 */
var  ProductList = {
    init:function(){
        ProductList.navFn($(".nav-product"));
        ProductList.swicthFn($(".nav-product-aside-group"),$(".nav-product-content-group"),"nav-product-aside-active");
        ProductList.swiperZoom();
        ProductList.zoomFunction();
        ProductList.clickShowFn($(".favorable-content-title"),$(".favorable-datalist"),'up');
        ProductList.chooseDestination();
        ProductList.amountFn();
        ProductList.rightSwiperFn();
        ProductList.textLimit();
        ProductList.shareSwiper();
        ProductList.shareImgFn();
        ProductList.shareToFn();
        ProductList.popupFn();
    },
    /*所有商品分类展开*/
    navFn:function(obj){
        obj.find(".nav-product-wrap").hide();
        obj.hover(function(){
            obj.find(".nav-product-wrap").show();
        },function(){
            obj.find(".nav-product-wrap").hide();
        });
    },
    /*商品类别切换*/
    swicthFn:function(obj,target,className){
        target.hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
            });
        },function(){
            target.hide();
            $(this).removeClass(className);
        })
    },
    /*放大镜效果*/
     zoomFunction:function(){
        $('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens:true,
            preloadImages: true,
            alwaysOn:false,
            zoomWidth:500,
            zoomHeight:500
        });
     },
    /*swiper*/
    swiperZoom:function(){
        var mySwiper = new Swiper('.product-swiper .swiper-container',{
            autoPlay:3000,
            slidesPerView : 5
        });
        $('.swiper-btn-prev').on('click', function(e){
            e.preventDefault();
            mySwiper.swipePrev();
        });
        $('.swiper-btn-next').on('click', function(e){
            e.preventDefault();
            mySwiper.swipeNext();
        });
    },
    clickShowFn:function(obj,target,classname){
        target.hide();
        obj.click(function(){
            if($(this).find("i").hasClass(classname)){
                target.hide();
                $(this).find("i").removeClass(classname);
            }else{
                target.show();
                $(this).find("i").addClass(classname);
            }
        });
    },
    /*选择地址*/
    chooseDestination:function(){
        /*初始化状态*/
        $(".express-choose-datalist").hide();
        $(".province-title").show().siblings().hide();
        $(".location-content").find(".province").show().siblings().hide();
        /*地址选择按钮*/
        $("#locationChoose").click(function(){
            if($(this).hasClass('location-choose-active')){
                $(".express-choose-datalist").hide();
                $(this).removeClass("location-choose-active");
            }else{
                $(".express-choose-datalist").show();
                $(this).addClass("location-choose-active");
            }
        });
        /*关闭按钮*/
        $("#expressClose").click(function(){
            $(".express-choose-datalist").hide();
            $("#locationChoose").removeClass("active");
        });
        /*省  市  区  选择点击时间*/
        var province;
        var city;
        var area;
        ProductList.siblingsHide($("#provinceTitle"));
        ProductList.siblingsHide($("#cityTitle"));
        /*省列表点击*/
        for(var i=0;i<$("#province").find("li").length;i++){
            $("#province").find("li").eq(i).find('span').click(function(){
                /*获取省份信息*/
                province = $(this).html();
                $(".province-title").html( province + '<i></i>' );
                $("#cityTitle").html('请选择' + '<i></i>');
                $(".city-title").show();
                $(".city-title").addClass("active").siblings().removeClass("active");
                $("#city").show();
                console.log(province);
                $("#province").hide();
                $("#city").show().siblings().hide();
                for(var i = 0 ;i<$("#city").find("ul").length;i++){
                    if($("#city").find("ul").eq(i).attr("data-category-province") == province){
                        $("#city").find("ul").eq(i).show();
                    }else {
                        $("#city").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*市列表点击*/
        for(var i=0;i<$("#city").find("li").length;i++){
            $("#city").find("li").eq(i).find('span').click(function(){
                /*获取城市信息*/
                city = $(this).html();
                $(".city-title").html(city + '<i></i>');
                $(".area-title").show();
                $(".area-title").addClass("active").siblings().removeClass("active");
                $("#area").show();
                console.log(city);
                $("#city").hide();
                $("#area").show().siblings().hide();
                for(var i = 0 ;i<$("#area").find("ul").length;i++){
                    if($("#area").find("ul").eq(i).attr("data-category-city") == city){
                        $("#area").find("ul").eq(i).show();
                    }else {
                        $("#area").find("ul").eq(i).hide();
                    }
                }
            });
        };
        /*区域列表点击*/
        for(var i=0;i<$("#area").find("li").length;i++){
            $("#area").find("li").eq(i).find('span').click(function(){
                /*获取区域信息*/
                area = $(this).html();
                $(".location-choose").html(province + city +area + '<i></i>');
                $(".express-choose-datalist").hide();
            });
        };
        /*头部点击*/
        $("#provinceTitle").click(function(){
            $("#province").show().siblings().hide();
            $(this).html("请选择<i></i>");
            $(this).addClass("active").siblings().removeClass("active");
        });
        $("#cityTitle").click(function(){
            $("#city").show().siblings().hide();
            $(this).html("请选择<i></i>");
            $(this).addClass("active").siblings().removeClass("active");
        });
    },
    /*点击  后面的兄弟标签消失*/
    siblingsHide:function(obj){
        obj.click(function(){
            $(this).nextAll().hide();
        });
    },
    /*数量加减*/
    amountFn:function(){
        $(".subtraction").click(function(){
            var count = parseInt( $(this).siblings('input').val() );
            if(count >0){
                count--;
            }else{
                count=0;
                $(this).addClass("disabled");
            }
            $(this).siblings('input').val(count);
        });
        $(".plus").click(function(){
            var count = parseInt( $(this).siblings('input').val() );
            count++;
            $(this).siblings('input').val(count);
            $(this).siblings(".subtraction").removeClass("disabled");
        });
    },
    /*顶部右边轮播*/
    rightSwiperFn:function(){
        var mySwiper = new Swiper('.look-swiper',{
            autoPlay:3000,
            slidesPerView : 3,
            mode : 'vertical'
        });
        $('.look-prev').on('click', function(e){
            e.preventDefault();
            $('.look-next').removeClass('disabled');
            if (mySwiper.activeIndex == 1) {
                $(this).addClass('disabled');
            }
            mySwiper.swipePrev();
        });
        $('.look-next').on('click', function(e){
            e.preventDefault();
            $('.look-prev').removeClass('disabled');
            if (mySwiper.activeIndex == mySwiper.slides.length - 4) {
                $(this).addClass('disabled');
            }
            mySwiper.swipeNext();
        });
    },
    /*分享框字数限制*/
    textLimit:function(){
        /*获取最大输入值*/
        var maxLength = parseInt( $(".number-limit").html() );
        $("#shareTxt").bind("propertychange input",function(){
            var len = $(this).val().length;
            var txt = $(this).val();
            if(maxLength >= len) {
                $(".number-limit").html(maxLength - len);
            }else{
                $(".number-limit").html(0);
                $(this).val(txt.substring(0,maxLength));
            }
        });
    },
    /*分享swiper*/
    shareSwiper:function(){
        var mySwiper = new Swiper('.share-swiper',{
            autoPlay:3000,
            slidesPerView : 4
        });
        $('.share-img-prev').on('click', function(e){
            e.preventDefault();
            mySwiper.swipePrev();
        });
        $('.share-img-next').on('click', function(e){
            e.preventDefault();
            mySwiper.swipeNext();
        });
    },
    /*选择分享图片*/
    shareImgFn:function(){
        /*初始化*/
        $("#shareImg").find(".swiper-slide").eq(0).find("input[type=radio]").attr("checked","checked");
        $("#shareImg").find(".swiper-slide").eq(0).find(".share-img-box").addClass("share-img-active");
        $("#shareImg").find("label").click(function(){
            if($(this).find("input[type=radio]").attr("checked")){
                for(var i=0;i<$(".share-img-box").length;i++){
                    $(".share-img-box").eq(i).removeClass("share-img-active");
                }
                $(this).find(".share-img-box").addClass("share-img-active");
            }else{
                $(this).find(".share-img-box").removeClass("share-img-active");
            }
        });
    },
    /*分享到*/
    shareToFn:function(){
        $("#shareTo").find("label").click(function(){
            if($(this).find("input[type=checkbox]").attr("checked")){
                $(this).find(".share-input-mask").addClass("share-to-active");
            }else{
                $(this).find(".share-input-mask").removeClass("share-to-active");
            }
        });
    },
    /*分享弹窗*/
    popupFn:function(){
        $("#popup").hide();
        $("#shareBtn").click(function(){
            $("#popup").show();
            $("body,html").css("overflow",'hidden');
        });
    }
};

$(function(){
    ProductList.init();
});