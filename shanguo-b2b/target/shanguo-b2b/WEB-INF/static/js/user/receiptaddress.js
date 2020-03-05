$(function() {
    if (!$.checkLogin()) { //验证登录
        return;
    }
    PersonCommon.delTipsFun();
    PersonCommon.defaultTipsFun();
    ReceiptAddress.init();
});

var ReceiptAddress = {
    init : function() {
        ReceiptAddress.closeDlg();
        ReceiptAddress.closeDlg1();
        $.initCitySelect({
            selectId: $("#detail"),
            clickId: $("#locationChoose"),
            selectArea:ReceiptAddress.address
        });
        ReceiptAddress.openBj();
        ReceiptAddress.openAdd();
        ReceiptAddress.showAddress();
    },

    closeDlg :function() {
        $(".close-dialog-btn").click(function() {
            $("#dialog-box").addClass("hide");
        });
    },

    closeDlg1 :function() {
        $(".close-dialog-btn1").click(function() {
            $("#dialog-box").addClass("hide");
        });
    },

    showAddress:function(){
        $(".list-wrap").html("");
        $.ajax({
            type:"POST",
            url:"/user/address",
            success:function(result){
                if(result.success&&result.data.graList){
                    var list = result.data.graList;
                    document.getElementById("size").innerText = list.length;
                    var  product_list_content = template("tpl-list-address", result.data);
                    $(".list-wrap").append($(product_list_content));
                    PersonCommon.delTipsFun();
                    PersonCommon.defaultTipsFun();
                    ReceiptAddress.openBj();
                    ReceiptAddress.openAdd();
                }
            }


        });

    },

    openBj:function(){
        $(".open-dialog-btn").click(function() {
            $("#dialog-box").removeClass("hide");
            var  id= $(this).attr("data-id");
            $.ajax({
                type:"POST" ,
                url:"/user/addressDetails",
                data:{id:id},
                success:function(result){
                    if(result.success && result.data.addr){
                        var addr = result.data.addr;
                        document.getElementById("addressId").value=addr.id;
                        document.getElementById("receiptName").value=addr.receiptName;
                        document.getElementById("locationChoose").value=addr.provinceName+""+addr.cityName+""+addr.areaName;
                        document.getElementById("provinceCode").value=addr.provinceCode;
                        document.getElementById("cityCode").value=addr.cityCode;
                        document.getElementById("areaCode").value=addr.areaCode;
                        document.getElementById("provinceName").value=addr.provinceName;
                        document.getElementById("cityName").value=addr.cityName;
                        document.getElementById("areaName").value=addr.areaName;
                        document.getElementById("detailAddr").value=addr.detailAddr;
                        document.getElementById("mobile").value=addr.mobile;
                        document.getElementById("fiexdPhone").value=addr.fiexdPhone;
                        document.getElementById("userId").value=addr.userId;

                    }
                }
            });
        });
    },
    address: function (province, city, area) {
        console.log(province + city + area);
        var provinceName =$("#provinceName").val(province);
        var cityName = $("#cityName").val(city);
        var areaName = $("#areaName").val(area);

        $("#provinceCode").val($.getCodeByName($.trim(province)));
        $("#cityCode").val($.getCodeByName($.trim(city)));
        $("#areaCode").val($.getCodeByName($.trim(area)));
    },


    clean:function(){
        document.getElementById("addressId").value="";
        document.getElementById("receiptName").value="";
        document.getElementById("locationChoose").value="";
        document.getElementById("detailAddr").value="";
        document.getElementById("mobile").value="";
        document.getElementById("fiexdPhone").value="";

    },
    openAdd:function(){
        $("#add").click(function () {
            $("#dialog-box").removeClass("hide");
            ReceiptAddress.clean();
        });
    },
    addAddress:function(){
        var receiptName = $("#receiptName").val();
        if (receiptName == "" || receiptName==null) {
            layer.msg("收货人不能为空！");
           return;
        }
        var detailAddr = $("#detailAddr").val();
        if (detailAddr == "" || detailAddr == null) {
            layer.msg("详细地址不能为空！");
            return;
        }
        var fiexdPhone = $("#fiexdPhone").val();
        var mobile = $("#mobile").val();
        var res =/^0[\d]{2,3}-[\d]{7,8}/;
        var res1=/^1[3,4,5,7,8]\d{9}$/;
        if (mobile=="" || mobile==null) {
            layer.msg("手机号码不能为空！");
            return;
        }
        if (!res1.test(mobile)) {
            layer.msg("请输入正确的11位手机号码！");
            return;
        }
        if (fiexdPhone == null || fiexdPhone == "") {

        }else {
            if (!res.test(fiexdPhone)) {
                layer.msg("固定电话格式不对，格式[0000-0000000]！");
                return;
            }
        }
        var provinceName =$("#provinceName").val();
        var cityName = $("#cityName").val();
        var areaName = $("#areaName").val();
        var provinceCode =$("#provinceCode").val();
        var cityCode = $("#cityCode").val();
        var areaCode = $("#areaCode").val();
            $.ajax({
                type: "POST",
                url: "/user/addAddress",
                data: {
                    "modelJsonStr": JSON.stringify({
                        provinceCode: provinceCode,
                        cityCode: cityCode,
                        areaCode: areaCode,
                        provinceName: provinceName,
                        cityName: cityName,
                        areaName: areaName,
                        receiptName: receiptName,
                        detailAddr: detailAddr,
                        mobile: mobile,
                        fiexdPhone: fiexdPhone
                    })
                },
                success: function (result) {
                    if (result.success) {
                        layer.msg("添加成功！");
                        ReceiptAddress.showAddress();
                        $("#dialog-box").addClass("hide");
                    } else {
                        layer.msg(result.msg);
                    }
                }

            });
    },
    updateAddress:function(){



        var receiptName = $("#receiptName").val();
        if (receiptName == "" || receiptName==null) {
            layer.msg("收货人不能为空！");
            return;
        }
        var detailAddr = $("#detailAddr").val();
        if (detailAddr == "" || detailAddr == null) {
            layer.msg("详细地址不能为空！");
            return;
        }
        var fiexdPhone = $("#fiexdPhone").val();
        var mobile = $("#mobile").val();
        var res =/^0[\d]{2,3}-[\d]{7,8}/;
        var res1=/^1[3,4,5,7,8]\d{9}$/;
        if (mobile=="" || mobile==null) {
            layer.msg("手机号码不能为空！");
            return;
        }
        if (!res1.test(mobile)) {
            layer.msg("请输入正确的11位手机号码！");
            return;
        }
        if (fiexdPhone == null || fiexdPhone == "") {

        }else {
            if (!res.test(fiexdPhone)) {
                layer.msg("固定电话格式不对，格式[0000-0000000]！");
                return;
            }
        }


        var  id= $("#addressId").val();
        var  userId=$("#userId").val();
        //var receiptName = $("#receiptName").val();
        //var detailAddr = $("#detailAddr").val();
        //var mobile = $("#mobile").val();
        //var fiexdPhone = $("#fiexdPhone").val();
        var provinceName =$("#provinceName").val();
        var cityName = $("#cityName").val();
        var areaName = $("#areaName").val();
        var provinceCode =$("#provinceCode").val();
        var cityCode = $("#cityCode").val();
        var areaCode = $("#areaCode").val();

        $.ajax({
            type: "POST",
            url: "/user/updateAddress",
            data: {
                "modelJsonStr": JSON.stringify({
                    id:id,
                    userId:userId,
                    provinceCode:provinceCode,
                    cityCode:cityCode,
                    areaCode:areaCode,
                    provinceName: provinceName,
                    cityName: cityName,
                    areaName: areaName,
                    receiptName: receiptName,
                    detailAddr: detailAddr,
                    mobile: mobile,
                    fiexdPhone: fiexdPhone
                })
            },
            success: function (result) {
                if (result.success) {
                    layer.msg("修改成功！");
                    ReceiptAddress.showAddress();
                    $("#dialog-box").addClass("hide");
                } else {
                    layer.msg("修改失败！");
                }
            }

        });
    },

    upAdd:function(){
        var  id= $("#addressId").val();

        if(id==null || id==""){

            ReceiptAddress.addAddress();

        }else {
            ReceiptAddress.updateAddress();
        }

    },


}

