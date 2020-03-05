/**
 * Created by Administrator on 2017/6/16.
 */
var myCart = {
    init:function(){
        myCart.inputSwitchStatus();
        myCart.countQuntity();
        myCart.accountAll();
        myCart.editorCompleteSwitch();
        myCart.deleltAll();
        myCart.silderDelete();
    },
    /*产品列表初始化*/
    productListInit:function(){
        for(var i = 0 ; i<$("#productList>li").length;i++){
            $("#productList>li").eq(i).find(".label-check").removeClass("checkbox-active");
            /*$("#productList>li").eq(i).find(".single-quantity").html(1);*/
        }
    },
    /* 切换选中/不选中状态 */
    inputSwitchStatus:function(){
        $("#productList").on("touchstart",'.label-check',function(){
            if($(this).hasClass("checkbox-active")){
                $(this).removeClass("checkbox-active")
            }else{
                $(this).addClass("checkbox-active");
            };
            myCart.countCost();
        });
    },
    /* 点击结算  全选 */
    accountAll:function(){
        $("#accountAll").on("touchstart",function(){
            if($(this).find(".checkbox-icon").hasClass("active")){
                $(this).find(".checkbox-icon").removeClass("active");
                for(var i = 0 ; i<$("#productList>li").length;i++){
                    $("#productList>li").eq(i).find(".label-check").removeClass("checkbox-active");
                }
                myCart.countCost();
            }else{
                $(this).find(".checkbox-icon").addClass("active");
                for(var i = 0 ; i<$("#productList>li").length;i++){
                    $("#productList>li").eq(i).find(".label-check").addClass("checkbox-active");
                }
                myCart.countCost();
            }
        });
    },
    /*产品数量加减*/
    countQuntity:function(){
        $("#productList").on("touchstart",'.minus',function(){
            /*起订量  同时也是累加基数*/
            var startNum = parseInt( $(this).closest('li').attr("data-startNum") );
            /*是否累加*/
            var mulNumFlag = $(this).closest('li').attr("data-mulNumFlag");
            /*库存量*/
            var onSalesNo = parseInt( $(this).closest('li').attr("data-onSalesNo") );
            /*订购量  同时也是初始值*/
            var number = parseInt( $(this).siblings(".single-quantity").html() );

            if(mulNumFlag == 'T'){   //累减基数
                console.log("T");
                if(number > startNum ){
                    number = number - startNum;
                }else{
                    number = startNum;
                    new Toast({context:$('body'),message: "已经是最小起订量了！！"}).show();
                };
            }else if(mulNumFlag == 'F'){  //累减1
                console.log("F");
                if(number > startNum ){
                    number--;
                }else{
                    number = startNum;
                    new Toast({context:$('body'),message: "已经是最小起订量了！！"}).show();
                };
            }
            $(this).siblings(".single-quantity").html(number);
            myCart.countCost();
        });
        $("#productList").on("touchstart",'.plus',function(){
            /*起订量  同时也是累加基数*/
            var startNum = parseInt( $(this).closest('li').attr("data-startNum") );
            /*是否累加*/
            var mulNumFlag = $(this).closest('li').attr("data-mulNumFlag");
            /*库存量*/
            var onSalesNo = parseInt( $(this).closest('li').attr("data-onSalesNo") );
            /*订购量  同时也是初始值*/
            var number = parseInt( $(this).siblings(".single-quantity").html() );

            if(mulNumFlag == 'T'){   //累加基数
                console.log("T");
                if(number > onSalesNo ){
                    number = onSalesNo;
                    new Toast({context:$('body'),message: "没有库存了！！"}).show();
                }else{
                    number = number + startNum;
                };
            }else if(mulNumFlag == 'F'){  //累加1
                console.log("F");
                if(number > onSalesNo ){
                    number = onSalesNo;
                    new Toast({context:$('body'),message: "没有库存了！！"}).show();
                }else{
                    number++;
                };
            }
            $(this).siblings(".single-quantity").html( number );
            myCart.countCost();
        });
    },
    /*结算金额计算函数*/
    countCost:function(){
        /*选中的商品的类别数量*/
        var categoryCount = 0;
        /*定义总价的变量*/
        var allCost = 0.00;
        /*定义总数量的变量*/
        var quantity = 0;
        for(var i = 0 ; i<$("#productList>li").length;i++){
            if($("#productList>li").eq(i).find(".label-check").hasClass("checkbox-active")){
                var singlePrice = parseFloat( $("#productList>li").eq(i).find(".single-price").html()).toFixed(2);
                var singleQauntity = parseInt( $("#productList>li").eq(i).find(".single-quantity").html() );
                allCost += singlePrice * singleQauntity;
                quantity += singleQauntity;
                categoryCount++;
            }
        };
        if(categoryCount == $("#productList>li").length && $("#productList>li").length > 0 ){
            $("#accountAll").find(".checkbox-icon").addClass("active");
            $("#deleteAll").find(".checkbox-icon").addClass("active");
        }else{
            $("#accountAll").find(".checkbox-icon").removeClass("active");
            $("#deleteAll").find(".checkbox-icon").removeClass("active");
        }
        $("#moneyNumber").html(allCost.toFixed(2));
        $("#quantity").html("(" + quantity + ")" );
    },
    /*点击编辑 切换状态*/
    editorCompleteSwitch:function(){
        $("#editor").on("touchstart",function(){
            $(this).siblings("#complete").show();
            $("#operationDelete").show();
            $(this).hide();
            $("#operationAccount").hide();
            myCart.productListInit();
            myCart.countCost();
        });
        $("#complete").on("touchstart",function(){
            $(this).siblings("#editor").show();
            $("#operationDelete").hide();
            $(this).hide();
            $("#operationAccount").show();
            myCart.productListInit();
            myCart.countCost();
        });
    },
    /*点击删除全选*/
    deleltAll:function(){
        $("#deleteAll").on("touchstart",function(){
            if($(this).find(".checkbox-icon").hasClass("active")){
                $(this).find(".checkbox-icon").removeClass("active");
                for(var i = 0 ; i<$("#productList>li").length;i++){
                    $("#productList>li").eq(i).find(".label-check").removeClass("checkbox-active");
                }
                myCart.countCost();
            }else{
                $(this).find(".checkbox-icon").addClass("active");
                for(var i = 0 ; i<$("#productList>li").length;i++){
                    $("#productList>li").eq(i).find(".label-check").addClass("checkbox-active");
                }
                myCart.countCost();
            }
        });
    },
    /*滑动删除效果*/
    silderDelete:function(){
        /*比率*/
        $("#productList").on("touchstart",'li',function(ev){
            var sildeRate = $(this).find(".delete-btn").width();
            var ev = ev || event;
            var touchStart = ev.originalEvent.touches[0];
            var disX = Number(touchStart.pageX);
            $(this).on("touchmove",function(ev){
                var ev = ev || event;
                ev.preventDefault();
                var touchMove = ev.originalEvent.touches[0];
                /*手指一动距离*/
                var x = Number( touchMove.pageX ) - disX;
                console.log();
                if(x <= 0 && x >= -sildeRate){
                    $(this).css("margin-left",x);
                }
                $(this).on("touchend",function(ev){
                    var ev = ev || event;
                    ev.preventDefault();
                    if( x <= -sildeRate/2){
                        $(this).stop().animate({"margin-left":-sildeRate},400);
                    }else{
                        $(this).stop().animate({"margin-left":0},400);
                    }
                });
            });

        })
    }
};
