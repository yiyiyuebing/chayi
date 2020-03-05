/**
 * Created by dy on 2017/6/13.
 */
var PurchaseCart = {
    main: $(".cart-cont"),
    init: function() {

        if (!$.checkLogin()) { //验证登录
            return;
        }

        PurchaseCart.initCheckBox();
        PurchaseCart.initInputNum();
        PurchaseCart.delFromCart1();
        PurchaseCart.getCartList();
    },

    changeNum: function(target, num, operator) {
        var changeGoodNumQuery = {};
        changeGoodNumQuery.nowNum = $(target).siblings(".text-amount").eq(0).val();
        changeGoodNumQuery.skuId = $(target).siblings(".text-amount").eq(0).attr("data-sku-id");
        changeGoodNumQuery.operation = operator;
        $.ajax({
            type: "POST",
            data: {queryJson: JSON.stringify(changeGoodNumQuery)},
            url: "/purchase/changeGoodNum",
            success: function(result) {
                if (result.success) {
                    $(target).siblings(".text-amount").eq(0).val(result.data);
                    var maxNum = $(target).attr("data-max-sale-num") ? Number($(target).attr("data-max-sale-num")) : 0;
                    if (operator == "sub") {
                        if (Number(result.data) <= maxNum) {
                            $(target).closest("td").siblings(".td-checkbox").find(".queNum").remove();
                        }
                    }

                    PurchaseCart.calCartGoodPrice(target, result.data);
                    PurchaseCart.updateCartNum($(target).siblings(".text-amount").eq(0).attr("data-id"), result.data);
                } else {
                    layer.msg(result.msg);
                }
            }
        });
    },

    calCartGoodPrice: function(target, num) {
        var result;
        var orderData = {};
        var orderList = [];
        if (target) {
            $(target).siblings(".text-amount").each(function(i, perInput) {
                var orderListInfo = {};
                if (parseInt($(perInput).val())) {
                    orderListInfo.number = $(perInput).val();
                    orderListInfo.purGoodsSkuId = $(perInput).attr("data-sku-id");
                    orderList.push(orderListInfo);
                }
            });
        } else {
            $(".list-cart").find("input[name=cart-good-checkbox]:checked").each(function(i, perInput) {
                var countIpt = $(perInput).closest("td").siblings(".per-count").find("input[name=number]");
                var orderListInfo = {};
                if (parseInt($(countIpt).val())) {
                    orderListInfo.number = $(countIpt).val();
                    orderListInfo.purGoodsSkuId = $(countIpt).attr("data-sku-id");
                    orderList.push(orderListInfo);
                }
            });
        }

        if (orderList.length <= 0) {
            return;
        }

        orderData.orderListVos = orderList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(orderData)},
            url: "/cart/calGoodsPriceInfo",
            success: function(result) {
                if (result.success) {
                    var orderVo = result.data;
                    if (target) {
                        var orderListVo = result.data.orderListVos[0];
                        var price = Number(orderListVo.purGoodsAmount);
                        target.parents("td").siblings(".per-price").find(".price-original").html(orderListVo.originalAmount.toFixed(2));
                        target.parents("td").siblings(".per-price").find(".price-now").html(price.toFixed(2));
                        var finalAmount = orderVo.finalAmount ? Number(orderVo.finalAmount) : 0;
                        target.parents("td").siblings(".select").find(".price").html(finalAmount.toFixed(2));

                    } else {
                        var orderListVo = result.data.orderListVos;
                        $.each(orderListVo, function(i, perList) {
                            var price = Number(perList.purGoodsAmount);
                            $("#" + perList.goodSkuId).closest("td").siblings(".per-price").find(".price-original").html(perList.originalAmount.toFixed(2));
                            $("#" + perList.goodSkuId).closest("td").siblings(".per-price").find(".price-now").html(price.toFixed(2));
                            $("#" + perList.goodSkuId).closest("td").siblings(".select").find(".price").html(perList.finalAmount.toFixed(2));
                        });
                    }
                    //PurchaseCart.calculateTotalPrice();
                } else {
                    $.each(orderList, function(i, perList) {
                        $("#" + perList.purGoodsSkuId).removeAttr("checked");
                    });
                   layer.msg(result.msg);
                }

                PurchaseCart.sumTotalPrice();
            }
        });


    },

    sumTotalPrice: function() {
        if ($("input[name=cart-good-checkbox]:checked").length > 0) {
            var totalPrice = 0;
            var totalNumber = 0;
            $("input[name=cart-good-checkbox]:checked").each(function(i, perIpt) {
                var perTotalPrice = $(perIpt).closest("td").siblings(".select").find(".price").html();
                totalPrice += perTotalPrice ? Number(perTotalPrice) : 0;
                var number = $(perIpt).closest("td").siblings(".per-count").find("input[name=number]").val();
                totalNumber += number ? Number(number) : 0;
            });
            $("#count").html(totalNumber);
            if (totalNumber > 0 && totalPrice > 0) {
                $("#goCount").removeClass("disable");
            } else {
                $("#goCount").addClass("disable");
            }
            $("#cost").html(totalPrice.toFixed(2));
        } else {
            PurchaseCart.main.find("input[name=check-all]").removeAttr("checked");
            $("#count").html(0);
            $("#cost").html(0.00);
            $("#goCount").addClass("disable");
        }
    },

    goCreatePreOrder: function() {
        var data = {};
        var itemList = [];
        $("input[name=cart-good-checkbox]:checked").each(function(i, perIpt) {
            var item = {};
            var perInput = $(perIpt).closest(".td-checkbox").siblings("td").find(".text-amount");
            //console.log($(perIpt).closest(".td-checkbox").siblings("td").find(".text-amount").val());
            item.purGoodsSkuId = perInput.attr("data-sku-id");
            item.number = perInput.val();
            item.isSample = perInput.attr("data-is-sample") ? perInput.attr("data-is-sample") : "0";
            item.purGoodsId = perInput.attr("data-goods-id");
            itemList.push(item);
        });

        var totalPrice = $("#cost").html() ? Number($("#cost").html()) : 0;

        if (itemList.length <= 0) {
            layer.msg("请选择需要结算的商品！");
            return ;
        }

        if (totalPrice <= 0) {
            layer.msg("结算金额不能小于0！");
            return ;
        }

        data.orderListVos = itemList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(data)},
            url: "/order/createPreOrder",
            success: function(result) {
                console.log(result);
                if (result.success) {
                    window.location.href = "/order/info/" + result.data.orderKey;
                }
            }
        });

    },

    initCheckBox: function() {
        PurchaseCart.main.on("click", ".checkall", function() {
            if ($(this).attr("checked") == "checked") {
                //PurchaseCart.main.find("input[name=check-all]").attr("checked", "checked");
                PurchaseCart.main.find("input:checkbox").attr("checked", "checked");
                PurchaseCart.calCartGoodPrice();
            } else {
                //PurchaseCart.main.find("input[name=check-all]").removeAttr("checked");
                PurchaseCart.sumTotalPrice();
                PurchaseCart.main.find("input:checkbox").removeAttr("checked");
            }
        });

        PurchaseCart.main.find(".data-table").find("tbody").on("change", "input:checkbox", function() {
            if ($(this).attr("checked") == "checked") {

                PurchaseCart.calCartGoodPrice();

                if (PurchaseCart.main.find(".data-table").find("tbody").find("input:checkbox:checked").length == PurchaseCart.main.find(".data-table").find("tbody").find("input:checkbox").length) {
                    PurchaseCart.main.find("input[name=check-all]").attr("checked", "checked");
                }
                /*if ($(this).siblings(".queNum").length > 0) {
                    layer.msg($(this).siblings(".queNum").attr("data-tip-info"));
                }
                //data-good-name="{{good.name}}" data-start-num="{{good.startNum}}"
                var startNum = $(this).closest("td").siblings(".per-count").find(".text-amount").attr("data-startNum") ?
                    Number($(this).closest("td").siblings(".per-count").find(".text-amount").attr("data-startNum")) : 0;

                var currCount = $(this).closest("td").siblings(".per-count").find(".text-amount").val() ?
                    Number($(this).closest("td").siblings(".per-count").find(".text-amount").val()) : 0;
                */

            } else {
                PurchaseCart.sumTotalPrice();
                PurchaseCart.main.find("input[name=check-all]").removeAttr("checked");
            }
        });
    },

    calculateTotalPrice: function () {
        var orderData = {};
        var orderList = [];
        $(".list-cart").find("input[name=cart-good-checkbox]:checked").each(function(i, perInput) {
            var countIpt = $(perInput).closest("td").siblings(".per-count").find("input[name=number]");
            var orderListInfo = {};
            if (parseInt($(countIpt).val())) {
                orderListInfo.number = $(countIpt).val();
                orderListInfo.purGoodsSkuId = $(countIpt).attr("data-sku-id");
                orderList.push(orderListInfo);
            }
        });

        if (orderList.length <= 0) {
            return;
        }
        orderData.orderListVos = orderList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(orderData)},
            url: "/cart/calGoodsPriceInfo",
            success: function(result) {
                if (result.success) {
                    var orderVo = result.data;
                    $("#count").html(orderVo.number);
                    if (orderVo.number > 0) {
                        $("#goCount").removeClass("disable");
                    }else{
                        $("#goCount").addClass("disable");
                    }
                    $("#cost").html(orderVo.finalAmount);
                    console.log(orderVo);
                } else {
                   console.log(result.msg);
                }
            }
        });






    },


    initInputNum: function() {
        PurchaseCart.main.on("click", ".item-amount .less", function() {
            var num = $(this).siblings(".text-amount").val() ? Number($(this).siblings(".text-amount").val()) : 0;
            var maxNum = $(this).attr("data-max-sale-num") ? Number($(this).attr("data-max-sale-num")) : 0;
            PurchaseCart.changeNum($(this), num, "sub");
            if (num <= maxNum) {
                $(this).closest("td").siblings(".td-checkbox").find(".queNum").remove();
            }
        });
        PurchaseCart.main.on("click", ".item-amount .plus", function() {
            var count = $(this).siblings(".text-amount").val() ? Number($(this).siblings(".text-amount").val()) : 0;
            PurchaseCart.changeNum($(this), num, "add");
        });

        PurchaseCart.main.find(".data-table").find("tbody").on("input propertychange focus", ".item-amount .text-amount", function() {
            var initVal = $(this).val() ? Number($(this).val()) : 0;
            console.log(initVal);
            var price = $(this).siblings(".price-now").val() ? Number($(this).siblings(".price-now").val()) : 0;
            var id = $(this).attr("data-id");
            var mulNumFlag = $(this).attr("data-mulNumFlag");
            var startNum = $(this).attr("data-startNum") ? Number($(this).attr("data-startNum")) : 0;
            var result ;
            console.log(startNum);
            console.log($(this).attr("data-startNum"));
            $(this).on('blur',function(){
                var num = $(this).val() ? Number($(this).val()) : 0;
                console.log(num);
                console.log(initVal != num);
                if(initVal != num ){
                    if (mulNumFlag == "F") {
                        if (num < startNum){
                            $(this).val(startNum);
                            layer.msg("该商品最小起订量是" + startNum + ",请重新输入");
                        }
                        if (Number(num) >= startNum) {
                            result = (num * price);
                            $(this).parents("td").siblings(".select").find(".price").html(result.toFixed(2));
                            PurchaseCart.updateCartNum(id, num);
                            //PurchaseCart.count();
                        }
                    }else {
                        if (num < startNum){
                            $(this).val(startNum);
                            layer.msg("该商品最小起订量是" + startNum + ",请重新输入");
                        }
                        if (Number(num) >= startNum) {
                            var count =  startNum;
                            count += Number(num);
                            $(this).val( count );
                            result = (count * price);
                            $(this).parents("td").siblings(".select").find(".price").html(result.toFixed(2));
                            PurchaseCart.updateCartNum(id, count);
                            //PurchaseCart.count();
                        }
                    }
                }

            });
        });
    },

    getCartList:function(){
        /*$(".list-cart").html("");*/
        $.ajax({
            type:"POST",
            url:"/cart/getCartList",
            success:function(result){
                if (result.success && result.data.lcv){
                    var list = result.data.lcv;
                    document.getElementById("all").innerText = list.length;
                    $.each(result.data.lcv,function(i,cartList){
                        var price =cartList.good.price ? Number(cartList.good.price) :0;
                        var number =cartList.goodsCount ? Number(cartList.goodsCount) : 0;
                         cartList.totalPrice  = (price * number).toFixed(2);

                        if (cartList.good.cargoSkuStock <= cartList.good.onSalesNo) {
                            cartList.good.onSalesNo = cartList.good.cargoSkuStock;
                            cartList.onSalesNo = cartList.good.cargoSkuStock;
                        } else {
                            cartList.onSalesNo = cartList.good.onSalesNo;
                        }

                        if (cartList.good.onSalesNo < 0) {
                            cartList.good.onSalesNo = 0;
                            cartList.onSalesNo = 0;
                        }
                        cartList.startNum = cartList.good.startNum;
                        if (cartList.onSalesNo < cartList.startNum) {
                            cartList.isQueHuo = "T";
                        } else {
                            cartList.isQueHuo = "F";
                        }

                        if (cartList.goodsCount) {
                            cartList.goodsCount = parseInt(cartList.goodsCount);
                            if (cartList.onSalesNo < cartList.goodsCount && cartList.onSalesNo < 10) {
                                cartList.queNum = cartList.onSalesNo;
                            }
                        }

                        if (cartList.good && cartList.good.promotionalInfo && cartList.good.promotionalInfo.bestActivity) {
                            cartList.activityType = cartList.good.promotionalInfo.bestActivity.activityType;
                        } else {
                            cartList.activityType = "";
                        }

                        console.log(cartList);
                        var tpl_list_cart=template("tpl-list-cart",cartList);
                        $(".list-cart").append($(tpl_list_cart));
                    });
                }
            }
        });
    },

    delFromCart1 :function(){
        PurchaseCart.main.on("click", ".del-cart", function () {
            var $this = $(this);
            layer.confirm('确定删除?', {title: '提示'}, function (index) {
                PurchaseCart.delFromCart($this);
                layer.close(index);
            });
        });
    },


    electDel1:function(){
            layer.confirm('确定删除选中?', {title: '提示'}, function (index) {
                PurchaseCart.delFromCart2();
                layer.close(index);
            });
    },

    electDel:function(){
        var deleteIdArr = [];
        for(var i = 0 ;i < $(".data-table").find("tbody").find("tr").length;i++){
            if($(".data-table").find("tbody").find("tr").eq(i).find("input[type=checkbox]").attr("checked")){
                deleteIdArr.push($(".data-table").find("tbody").find("tr").eq(i).find(".del-cart").attr("data-id"));
            }
        };
        return deleteIdArr;
        /*在控制台打印要删除的产品的ID*/
        console.log(deleteIdArr);
    },


    delFromCart2 :function(){
        var  deleteIdArr = PurchaseCart.electDel();
        var  id=deleteIdArr.join(",");
        if(id == null|| id==""){
            layer.msg("请选择需要删除的商品！");
            return;
        }
        $.ajax({
            type:"POST",
            url:"/cart/delFromCart",
            data:{idList: id},
            success:function(result){
                if (result.success){
                    layer.msg("删除成功！");
                    AsideBar.cartCount();
                    PurchaseCart.getCartList();
                    PurchaseCart.calculateTotalPrice();
                }else {
                    layer.msg("删除失败！");

                }
            }
        });
    },

    delFromCart :function($this){
        var id= $this.attr("data-id");
        $.ajax({
            type:"POST",
            url:"/cart/delFromCart",
            data:{idList:id},
            success:function(result){
                if (result.success){
                    layer.msg("删除成功！");
                    $this.parents("tr").remove();
                    AsideBar.cartCount();
                    PurchaseCart.calculateTotalPrice();
                }else {
                    layer.msg("删除失败！");

                }
            }
        });
    },

    updateCartNum:function(id,num){
        var  id =id;
        var goodsCount =num;
        $.ajax({
            type:"POST",
            url:"/cart/updateCartNum",
            data:{"modelJsonStr": JSON.stringify({id:id,goodsCount:goodsCount})},
            success:function(result){
                if (!result.success) {
                    layer.msg(result.msg);
                }
            }
        });
    },

    addCollectId:function(){
        var addCollectId = [];
        for(var i = 0 ;i < $(".data-table").find("tbody").find("tr").length;i++){
            if($(".data-table").find("tbody").find("tr").eq(i).find("input[type=checkbox]").attr("checked")){
                console.log($(".data-table").find("tbody").find("tr").eq(i).find(".collect-cart").attr("data-id"));
                addCollectId.push($(".data-table").find("tbody").find("tr").eq(i).find(".collect-cart").attr("data-id"));
            }
        };
        return addCollectId;
    },

    addCollect:function(){
        PurchaseCart.main.on("click", ".collect-cart", function () {
            var id= $(this).attr("data-id");
            layer.confirm('是否加入收藏?', {title: '提示'}, function (index) {
                PurchaseCart.addCollect1(id);
                layer.close(index);
            });
        });
    },
    addCollectList:function(){
            layer.confirm('是否把选中商品加入收藏?', {title: '提示'}, function (index) {
                PurchaseCart.addCollectList1();
                layer.close(index);
            });
    },
    addCollect1 : function(id) {
        var data = {
            goodsId:id,
            goodsCount: 1,
            orderBizType: 'purchase'
        }
        $.ajax({
            type:"POST",
            url:"/favorite/addToFavorite",
            data: {modelJsonStr: JSON.stringify(data)},
            success:function(result) {
               if (result.success) {
                   layer.msg("收藏成功");
               }else {
                   layer.msg(result.msg);
               }
            },
            error: function(error) {
                layer.msg(error);
            }
        });

    },
    addCollectList1 : function(){
       var addCollectId =   PurchaseCart.addCollectId();
        var  id=addCollectId.join(",");
        if (id == null || id == "") {
            layer.msg("请选择需要收藏的商品");
            return;
        }
        $.ajax({
            type:"POST",
            url:"/favorite/batchAddToFavorite",
            data:{goodIdStr:id},
            success:function(result){
                if (result.success) {
                    layer.msg("收藏成功");
                }else {
                    layer.msg(result.msg);
                }
            },
            error: function(error) {
                layer.msg(error);
            }
        });
    }
};

$(function() {
    PurchaseCart.init();
});