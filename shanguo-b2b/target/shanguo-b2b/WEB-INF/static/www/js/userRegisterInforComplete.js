/**
 * Created by Administrator on 2017/5/23.
 */
var userInfoComplete = {
    init:function(){
        userInfoComplete.chooseDestination();
        userInfoComplete.limitCount($(".shop-name"),$(".length-limit10"),10);
        userInfoComplete.limitCount($(".shop-input-desc"),$(".length-limit120"),120);
        userInfoComplete.uploadImg();
        userInfoComplete.cameraUploadImg();
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
        userInfoComplete.siblingsHide($("#provinceTitle"));
        userInfoComplete.siblingsHide($("#cityTitle"));
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
                $("#locationChoose").removeClass("location-choose-active");
                $("#addressSimple").val(province + city +area);
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
    /*字数显示*/
    limitCount:function(obj,target,sum){
        obj.bind("propertychange input",function(){
            var value = $(this).val();
            var len = $(this).val().length;
            target.html(len + "/" +sum);
            if(len>sum-1){
                obj.val(value.substring(0,sum));
                target.html(sum + "/" +sum);
            }
        });
    },
    /*上传图片*/
    uploadImg:function(){
        $(".img-operation-upload").find("input[type=file]").change(function(e){
            var e = e || event;
            var file = e.target.files[0];
            var reader = new FileReader();
            var imgbox = $(this).closest(".img-operation-box").siblings(".img-show-box").find("img");
            reader.onload = function(e){
                imgbox.prop('src',e.target.result);
            };
            reader.readAsDataURL(file);
        });
        $(".img-operation-change").find("input[type=file]").change(function(e){
            var e = e || event;
            var file = e.target.files[0];
            var reader = new FileReader();
            var imgbox = $(this).closest(".img-operation-box").siblings(".img-show-box").find("img");
            reader.onload = function(e){
                imgbox.prop('src',e.target.result);
            };
            reader.readAsDataURL(file);
        });
    },
    /*照相机图标  上传图标*/
    cameraUploadImg:function(){
        /**/
        /*可以上传图片的总数*/
        var maxlength = 6;
        /*已上传的图片数量*/
        var uploadedLength = $("#imgList").find('li').length - 1;
        /*初始化图片数量*/
        $(".camera-img-uploadable").html(maxlength - uploadedLength);
        $(".camera-img-uploaded").html(uploadedLength);
        /*还能上传图片的数量*/
        var uploadableLength = maxlength - uploadedLength;
            $("#cameraBtn").change(function(e){
                if(uploadableLength>0) {
                    var e = e || event;
                    var file = e.target.files[0];
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#imgList").prepend('<li class="pull-left"><img src="' + e.target.result + '"></li>');
                        uploadableLength--;
                        uploadedLength++;
                        $(".camera-img-uploadable").html(uploadableLength);
                        $(".camera-img-uploaded").html(uploadedLength);
                    };
                    reader.readAsDataURL(file);
                }else{
                    return false;
                }
            });
    }
};
$(function(){
    userInfoComplete.init();
});