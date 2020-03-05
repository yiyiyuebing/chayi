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
        PurchaseCart.getCartList();
        PurchaseCart.count();
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
                    PurchaseCart.calCartGoodPrice(target, result.data);
                    PurchaseCart.updateCartNum($(target).siblings(".text-amount").eq(0).attr("data-id"), result.data);
                } else {
                    layer.msg(result.msg);
                }
                console.log(result);
            }
        });
    },

    calCartGoodPrice: function(target, num) {
        var result;
        var orderData = {};
        var orderList = [];
        $(target).siblings(".text-amount").each(function(i, perInput) {
            var orderListInfo = {};
            if (parseInt($(perInput).val())) {
                orderListInfo.number = $(perInput).val();
                orderListInfo.purGoodsSkuId = $(perInput).attr("data-sku-id");
                orderList.push(orderListInfo);
            }
        });
        orderData.orderListVos = orderList;
        $.ajax({
            type: "POST",
            data: {orderJson: JSON.stringify(orderData)},
            url: "/cart/calGoodsPriceInfo",
            success: function(result) {
                if (result.success) {
                    var orderVo = result.data;
                    var orderListVo = result.data.orderListVos[0];
                    var price = Number(orderListVo.finalAmount) / Number(orderListVo.buyNum);
                    target.parents("td").siblings(".per-price").find(".price-original").html(price.toFixed(2));
                    target.parents("td").siblings(".per-price").find(".price-now").html(price.toFixed(2));
                    target.parents("td").siblings(".select").find(".price").html(orderVo.finalAmount);
                    PurchaseCart.count();
                } else {
                    layer.msg(result.msg);
                }
                console.log(result);
            }
        });


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

        if (itemList.length <= 0) {
            layer.msg("请选择需要结算的商品！");
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
        PurchaseCart.main.find("input[name=check-all]").on("change", function() {
            if ($(this).attr("checked") == "checked") {
                //PurchaseCart.main.find("input[name=check-all]").attr("checked", "checked");
                PurchaseCart.main.find("input:checkbox").attr("checked", "checked");

            } else {
                //PurchaseCart.main.find("input[name=check-all]").removeAttr("checked");
                PurchaseCart.main.find("input:checkbox").removeAttr("checked");
            }
             PurchaseCart.count();
        });

        PurchaseCart.main.find(".data-table").find("tbody").find("input:checkbox").on("change", function() {
            if ($(this).attr("checked") == "checked") {
                if (PurchaseCart.main.find(".data-table").find("tbody").find("input:checkbox:checked").length == PurchaseCart.main.find(".data-table").find("tbody").find("input:checkbox").length) {
                    PurchaseCart.main.find("input[name=check-all]").attr("checked", "checked");
                }
            } else {
                PurchaseCart.main.find("input[name=check-all]").removeAttr("checked");
            }
            PurchaseCart.count();
        });
    },

    count:function () {
        /*商品件数*/
        var count = 0;
        /*商品总价格*/
        var cost = 0;
        for(var i=0;i<$(".data-table").find("tbody").find("tr").length;i++){
            if( $(".data-table").find("tbody").find("tr").eq(i).find("input[type=checkbox]").attr("checked")){
                count = count + parseInt( $(".data-table").find("tbody").find("tr").eq(i).find(".text-amount").val() );
                /*console.log($(".data-table").find("tbody").find("tr").eq(i).find(".price"));*/
                cost = cost + parseFloat( $(".data-table").find("tbody").find("tr").eq(i).find(".price").html() );
            }
        };
        if (count>0){
         $("#goCount").removeClass("disable");
        }else{
          $("#goCount").addClass("disable");
        }
        $("#count").html(count);
        $("#cost").html(cost.toFixed(2));
    },


    initInputNum: function() {
        PurchaseCart.main.find(".data-table").find("tbody").find(".item-amount").on("click", ".less", function() {
            var num = $(this).siblings(".text-amount").val() ? Number($(this).siblings(".text-amount").val()) : 0;
            //var price = $(this).siblings(".price-now").val() ? Number($(this).siblings(".price-now").val()) : 0;
            //var id = $(this).attr("data-id");
            //var mulNumFlag = $(this).attr("data-mulNumFlag");
            //var startNum = $(this).attr("data-startNum") ? Number($(this).attr("data-startNum")) : 0;
            //
            //if (mulNumFlag == "F") {
            //    if (Number(num) > startNum) {
            //        num--;
            //        $(this).siblings(".text-amount").val(num);
            //    }
            //} else {
            //    if (Number(num) > startNum) {
            //        var num = num-startNum;
            //        $(this).siblings(".text-amount").val(num);
            //    }
            //}
            PurchaseCart.changeNum($(this), num, "sub");
            //PurchaseCart.calCartGoodPrice($(this), num);
        });
        PurchaseCart.main.find(".data-table").find("tbody").find(".item-amount").on("click", ".plus", function() {
            var count = $(this).siblings(".text-amount").val() ? Number($(this).siblings(".text-amount").val()) : 0;
            //var price = $(this).siblings(".price-now").val() ? Number($(this).siblings(".price-now").val()) : 0;
            //var id = $(this).attr("data-id");
            //var mulNumFlag = $(this).attr("data-mulNumFlag");
            //var onSaleNo = $(this).attr("data-max-sale-num");
            //var startNum = $(this).attr("data-startNum") ? Number($(this).attr("data-startNum")) : 0;
            //
            //if (mulNumFlag == "F") {
            //    count++;
            //} else {
            //    count += startNum;
            //}
            //if (count < onSaleNo) {
            //    $(this).siblings(".text-amount").val(count);
            //} else if (count == onSaleNo) {
            //    $(this).siblings(".text-amount").val(count);
            //}
            PurchaseCart.changeNum($(this), num, "add");
            //PurchaseCart.calCartGoodPrice($(this), count); //计算价格
        });

        PurchaseCart.main.find(".data-table").find("tbody").find(".item-amount").on("input propertychange focus", ".text-amount", function() {
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
                            PurchaseCart.count();
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
                            PurchaseCart.count();
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
                        }

                        if (cartList.good.onSalesNo < 0) {
                            cartList.good.onSalesNo = 0;
                        }



                        var tpl_list_cart=template("tpl-list-cart",cartList);
                        $(".list-cart").append($(tpl_list_cart));
                    });
                    PurchaseCart.initCheckBox();
                    PurchaseCart.initInputNum();
                    PurchaseCart.delFromCart1();
                    PurchaseCart.addCollect();
                }
            }
        });
    },

    delFromCart1 :function(){
        $(".del-cart").click(function () {
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
                    PurchaseCart.count();
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
                    PurchaseCart.count();
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
        console.log(addCollectId);
        return addCollectId;
        /*在控制台打印要收藏的产品的ID*/

    },

    addCollect:function(){
        $(".collect-cart").click(function () {
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