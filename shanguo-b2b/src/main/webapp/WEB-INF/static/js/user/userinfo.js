$(function() {
    if (!$.checkLogin()) { //验证登录
        return;
    }
    UserInfo.init();
});

var UserInfo = {
    thumbnailWidth: 100,
    thumbnailHeight: 100,
    init : function() {
        $.initCitySelect({
            selectId: $("#detail"),
            clickId: $("#locationChoose"),
            selectArea:UserInfo.userInfo,
            selectAreaCode: UserInfo.selectAreaCode
        });
        console.log(userInfo);
        document.getElementById("locationChoose").value=userInfo.provinceName+""+userInfo.cityName+""+userInfo.countryName;
        UserInfo.initCredenImg();
        UserInfo.tabFun();
        UserInfo.editInfo();
    },


    initCredenImg: function() {

        var url=userInfo.url;
        var picList=url.split(",");
        var idCard =userInfo.idCardIndex;
        var idCardBack =userInfo.idCardBack;
        var businessLicence=userInfo.businessLicence;
        var taxPhoto=userInfo.taxPhoto;
        var headImgUrl =userInfo.headImgUrl;

        var picUrlList = [];
        $.each(picList, function(i, pic) {
            if (pic.length > 0) {
                var data = {
                    url: pic
                }
                picUrlList.push(data);
            }
        });

        var idCard1 = [];
        if (idCard) {
            var imgObj = {
                id: 1,
                url: idCard,
                name: ''
            };
            idCard1.push(imgObj);
        }
        $.createWebUploader({
            viewListId: "#view-idcard-1",
            pickBtn: "#add-idcard-1",
            fileNumLimit: 1,
            imageList:idCard1
        });

        var idCard2 = [];
        if (idCardBack) {
            var imgObj = {
                id: 1,
                url: idCardBack,
                name: ''
            };
            idCard2.push(imgObj);
        }
        $.createWebUploader({
            viewListId: "#view-idcard-2",
            pickBtn: "#add-idcard-2",
            fileNumLimit: 1,
            imageList:idCard2
        });

        var businessLicences = [];
        if (businessLicence) {
            var imgObj = {
                id: 1,
                url: businessLicence,
                name: ''
            };
            businessLicences.push(imgObj);
        }

        var businessUploader = $.createWebUploader({
            viewListId: "#view-business-license",
            pickBtn: "#pick-business-license",
            fileNumLimit: 1,
            imageList:businessLicences
        });

       /* console.log("输出"+$.getUploadImageList("#view-business-license")); //获取图片*/
        var taxPhotos = [];
        if (taxPhoto) {
            var imgObj = {
                id: 1,
                url: taxPhoto,
                name: ''
            };
            taxPhotos.push(imgObj);
        }
        $.createWebUploader({
            viewListId: "#view-tax",
            pickBtn: "#pick-tax",
            fileNumLimit: 1,
            imageList:taxPhotos //加载图片

        });

        $.createWebUploader({
            viewListId: "#store-photo-list",
            pickBtn: "#pick-store-photo",
            replaceModel: false,
            fileNumLimit: 6,
            imageList:picUrlList,
            addImgSuccess: function() { //选择图片成功事件
                var picNum = $("#store-photo-list").children().length;
                $(".bottom-line").find(".total-num").html(picNum);
                $(".bottom-line").find(".last-num").html( 6 - Number(picNum));
            },
            formData:{
                imgName:"idCardIndex"
            }
        });

        var headImgUrls = [];
        if (headImgUrl) {
            var imgObj = {
                id: 1,
                url: headImgUrl,
                name: ''
            };
            headImgUrls.push(imgObj);
        }
        $.createWebUploader({
            viewListId: "#head-list-view",
            pickBtn: "#pick-head-btn",
            replaceModel: false,
            thumbnailWidth:460,
            thumbnailHeight:230,
            width:460,
            height:230,
            fileNumLimit: 1,
            imageList:headImgUrls, //加载图片
            isShow: true,
            uploadSuccess: function(result) {//图片上传成功后事件
                $(".big-img").find("img").attr("src", result.data.url);
                $(".small-img").find("img").attr("src", result.data.url);
                verify(1);
            },
            onDelete: function() {
                verify(1);
                $(".big-img").find("img").attr("src", "");
                $(".small-img").find("img").attr("src", "");
                //alert("aa");
            }
        });
    },





    userInfo: function (province, city, area) {
        var provinceName =$("#provinceName").val(province);
        var cityName = $("#cityName").val(city);
        var areaName = $("#areaName").val(area);

        $("#provinceCode").val($.getCodeByName($.trim(province)));
        $("#cityCode").val($.getCodeByName($.trim(city)));
        $("#areaCode").val($.getCodeByName($.trim(area)));
    },

    selectAreaCode: function (province, city, area) {
        $("#provinceCode").val($.trim(province));
        $("#cityCode").val($.trim(city));
        $("#areaCode").val($.trim(area));
    },

    tabFun : function() {
        var liTagt = $("#user-nav li");
        liTagt.click(function () {
            var tagt = $(this),
                index = tagt.index();
            liTagt.removeClass("active");
            tagt.addClass("active");

            var listCont = $("#user-tab-box .user-info-wrap");
            listCont.addClass("webuploaderHide");
            listCont.eq(index).removeClass("webuploaderHide");
        });
    },
    editInfo : function() {
       $(".option-btn").click(function() {
           $(this).siblings(".option-span").addClass("hide");
           $(this).siblings(".option-ipt").removeClass("hide");
       });
    }
}

function saveHeadUrl() {
    var id=$("#id").val();
    var headImgUrl1=$.getUploadImageList("#head-list-view");
    for (var i in headImgUrl1){
        var headImgUrl=headImgUrl1[i]['url'];
    }
    var data = {
        id:id,
        headImgUrl:headImgUrl
    };
    $.ajax({
        type:"POST",
        url:"/user/updateShop",
        data:{"modelJsonStr":JSON.stringify(data)},
        success:function(result){
            if (result.success){
                layer.msg("修改成功！");
            }else {
                layer.msg(result.msg);
            }
        }
    });
}

function verify(type){
    var id=$("#id").val();
    var name=$("#name").val();
    var weixin = $("input[name=weixin]").val();
    var userName=$("#userName").val();
    var provinceName=$("#provinceName").val();
    var countryName=$("#areaName").val();
    var cityName=$("#cityName").val();
    var provinceCode =$("#provinceCode").val();
    var cityCode = $("#cityCode").val();
    var areaCode = $("#areaCode").val();
    var address=$("#address").val();
    var phone =$("#phone").html();
    var description = $("#description").val();
    var idCard1= $.getUploadImageList("#view-idcard-1");
    if (idCard1==null || idCard1=="" ){
        layer.msg("身份证正面不能为空！");
        return;
    }
    for (var i in idCard1){
       console.log(idCard1[i]['url']);
        var idCard = idCard1[i]['url'];
    }
    var idCardBack1= $.getUploadImageList("#view-idcard-2");
    if (idCardBack1==null || idCardBack1=="" ){
        layer.msg("身份证反面不能为空！");
        return;
    }
    for (var i in idCardBack1){
        var idCardBack=idCardBack1[i]['url'];
    }
    var businessLicence1= $.getUploadImageList("#view-business-license");
    //if (businessLicence1==null || businessLicence1=="" ){
    //    layer.msg("营业执照不能为空！");
    //    return;
    //}
    for (var i in businessLicence1){
        var businessLicence=businessLicence1[i]['url'];
    }
    var taxPhoto1= $.getUploadImageList("#view-tax");
    //if (taxPhoto1==null || taxPhoto1=="" ){
    //    layer.msg("税务登记不能为空！");
    //    return;
    //}
    for (var i in taxPhoto1){
        var taxPhoto=taxPhoto1[i]['url'];
    }
    var headImgUrl1=$.getUploadImageList("#head-list-view");
    for (var i in headImgUrl1){
        var headImgUrl=headImgUrl1[i]['url'];
    }
    var picList1=$.getUploadImageList("#store-photo-list");
    var addPicList = [];
    $.each(picList1,function(i,pic){
        addPicList.push(pic.url);
        console.log(addPicList);
    });
    var data = {
        id:id,
        addPicList:addPicList,
        idCardIndex:idCard,
        weixin:weixin,
        idCardBack:idCardBack,
        businessLicence:businessLicence,
        taxPhoto:taxPhoto,
        headImgUrl:headImgUrl,
        name:name,
        userName:userName,
        province:provinceCode,
        city:cityCode,
        country:areaCode,
        provinceName:provinceName,
        countryName:countryName,
        cityName:cityName,
        description:description,
        phone:phone,
        address:address
    };
    $.ajax({
        type:"POST",
        url:"/user/updateShop",
        data:{"modelJsonStr":JSON.stringify(data)},
        success:function(result){
            if (result.success){
                layer.msg("修改成功！");
                if (!type) {
                    location.reload();
                }
            }else {
                layer.msg(result.msg);
            }
        }
    });
}