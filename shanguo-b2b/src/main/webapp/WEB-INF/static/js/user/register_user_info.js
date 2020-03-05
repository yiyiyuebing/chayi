/**
 * Created by Administrator on 2017/5/23.
 */
var userInfoComplete = {
    init:function(){
        userInfoComplete.limitCount($(".shop-name"),$(".length-limit10"), 10);
        userInfoComplete.limitCount($(".shop-input-desc"),$(".length-limit120"), 120);
        userInfoComplete.updateWebImage();
        userInfoComplete.validateInput();
        userInfoComplete.saveUserInfo();
        $.initCitySelect({
            selectId: $("#register-user-location-select"),
            clickId: $("#register-user-location-click"),
            selectArea: function(province, city, area) {
                $("input[name=provinceName]").val(province);
                $("input[name=cityName]").val(city);
                $("input[name=countryName]").val(area);

                $("input[name=province]").val($.getCodeByName($.trim(province)));
                $("input[name=city]").val($.getCodeByName($.trim(city)));
                $("input[name=country]").val($.getCodeByName($.trim(area)));
                $(".express-wrap").closest(".input-group").siblings(".tips").addClass("hide");
            }
        });
    },

    saveUserInfo: function() {

        $(".input-submit").find("a").click(function() {
            if (!$.validateNull($("input[name=name]").val())) {
                $("input[name=name]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }
            if (!$.validateNull($("input[name=userName]").val())) {
                $("input[name=userName]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }
            if (!$.validateNull($("input[name=phone]").val())) {
                $("input[name=phone]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }
            if (!$.validateNull($("input[name=address]").val())) {
                $("input[name=address]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }

            if (!$.validateNull($("input[name=cityName]").val())) {
                $("input[name=cityName]").closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            }


            var view_idcard_z = $.getUploadImageList("#view-idcard-z").length > 0 ? $.getUploadImageList("#view-idcard-z")[0].url : "";
            var view_idcard_f = $.getUploadImageList("#view-idcard-f").length > 0 ? $.getUploadImageList("#view-idcard-f")[0].url : "";
            var view_business = $.getUploadImageList("#view-business").length > 0 ? $.getUploadImageList("#view-business")[0].url : "";
            var view_tax = $.getUploadImageList("#view-tax").length > 0 ? $.getUploadImageList("#view-tax")[0].url : "";
            var head_list_view = $.getUploadImageList("#head-list-view").length > 0 ? $.getUploadImageList("#head-list-view")[0].url : "";

            if (!$.validateNull(view_idcard_z)) {
                layer.msg("请维护个人身份证正面");
                return;
            }
            if (!$.validateNull(view_idcard_f)) {
                layer.msg("请维护个人身份证反面");
                return;
            }
            //if (!$.validateNull(view_business)) {
            //    layer.msg("请维护营业执照");
            //    return;
            //}
            //if (!$.validateNull(view_tax)) {
            //    layer.msg("请维护税务登记");
            //    return;
            //}
            if (!$.validateNull(head_list_view)) {
                layer.msg("请维护门店照片");
                return;
            }
            //idCardIndex:idCard,idCardBack:idCardBack,businessLicence:businessLicence,taxPhoto:taxPhoto
            var formDataArray = $("#user-form").serializeArray();
            var data = {};
            $.each(formDataArray, function(i, obj) {
                if (data[obj.name] && data[obj.name].length > 0) {
                    data[obj.name] = data[obj.name] + "," + obj.value;
                } else {
                    data[obj.name] = obj.value;
                }
            });

            data.idCardIndex = view_idcard_z;
            data.idCardBack = view_idcard_f;
            data.businessLicence = view_business;
            data.taxPhoto = view_tax;
            data.headImgUrl = head_list_view;
            //$.each(head_list_view, function(i, pic){
            //    data.addPicList.push(pic.url);
            //});
            var updateFlag = false;
            if ($("input[name=action]").val() == 'update') {
                data.id = $("input[name=id]").val();
                updateFlag = true;
            } else {
                data.mobile = $("input[name=moblie]").val();
            }
            console.log(data);
            if (!updateFlag) {
                $.ajax({
                    type:"POST",
                    url:"/passport/doRegisterUserInfo",
                    data:{"subJson":JSON.stringify(data), moblie: $("input[name=moblie]").val()},
                    success:function(result){
                        if (result.success) {
                            //if ($("input[name=action]").val() && $("input[name=action]").val() == 'update') {
                            //    window.location.href = "/passport/registerInfoBandcard/" + $("input[name=id]").val();
                            //} else {
                                window.location.href = "/passport/registerAudit";
                            //}
                        } else {
                            layer.msg(result.msg);
                        }
                    }
                });

            } else {
                $.ajax({
                     type:"POST",
                     url:"/user/updateShop",
                     data:{"modelJsonStr":JSON.stringify(data)},
                     success:function(result){
                         window.location.href = "/passport/registerInfoBandcard/" + $("input[name=id]").val();
                     }
                 });
            }

        });





        /*$.ajax({
            type:"POST",
            url:"/user/updateShop",
            data:{"modelJsonStr":JSON.stringify(data)},
            success:function(result){
                if (result.success){
                    layer.msg("修改成功！");
                    location.reload();
                }else {
                    layer.msg("修改失败！");
                }
            }
        });*/
    },

    updateWebImage: function() {
        $.createWebUploader({
            viewListId: "#view-idcard-z",
            pickBtn: "#pick-idcard-z",
            fileNumLimit: 1
        });
        $.createWebUploader({
            viewListId: "#view-idcard-f",
            pickBtn: "#pick-idcard-f",
            fileNumLimit: 1
        });
        $.createWebUploader({
            viewListId: "#view-business",
            pickBtn: "#pick-business",
            fileNumLimit: 1
        });
        $.createWebUploader({
            viewListId: "#view-tax",
            pickBtn: "#pick-tax",
            fileNumLimit: 1
        });

        $.createWebUploader({
            viewListId: "#head-list-view",
            pickBtn: "#pick-head",
            replaceModel: false,
            fileNumLimit: 1
        });
    },

    validateInput: function() {

        $("input[name=name]").on("keyup", function() {
            if (!$.validateNull($(this).val())) {
                $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            } else {
                $(this).closest(".input-group").siblings(".tips").addClass("hide");
            }
        });
        $("input[name=userName]").on("keyup", function() {
            if (!$.validateNull($(this).val())) {
                $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            } else {
                $(this).closest(".input-group").siblings(".tips").addClass("hide");
            }
        });
        $("input[name=phone]").on("keyup", function() {
            if (!$.validateNull($(this).val())) {
                $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            } else {
                $(this).closest(".input-group").siblings(".tips").addClass("hide");
            }
        });
        $("input[name=address]").on("keyup", function() {
            if (!$.validateNull($(this).val())) {
                $(this).closest(".input-group").siblings(".tips").removeClass("hide");
                return;
            } else {
                $(this).closest(".input-group").siblings(".tips").addClass("hide");
            }
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
    }
    /*上传图片*//*
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
    },*/
    /*照相机图标  上传图标*//*
    cameraUploadImg:function(){
        *//**//*
        *//*可以上传图片的总数*//*
        var maxlength = 6;
        *//*已上传的图片数量*//*
        var uploadedLength = $("#imgList").find('li').length - 1;
        *//*初始化图片数量*//*
        $(".camera-img-uploadable").html(maxlength - uploadedLength);
        $(".camera-img-uploaded").html(uploadedLength);
        *//*还能上传图片的数量*//*
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
    }*/
};
$(function(){
    userInfoComplete.init();
});