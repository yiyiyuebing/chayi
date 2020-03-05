/**
 * Created by Administrator on 2017/5/11.
 */
var  ProductList = {
    init:function(){
        ProductList.inputFn();
        ProductList.navFn($(".nav-product"));
        ProductList.swicthFn($(".nav-product-aside-group"),$(".nav-product-content-group"),"nav-product-aside-active");
        ProductList.productSwitch($(".recommend-product-content-title").find("span"),$(".recommend-product-content-list-group"),"recommend-product-content-title-active");
        ProductList.productSwitch($(".recommend-product-news-title").find("span"),$(".recommend-product-news-content-group"),"news-title-active");
        ProductList.sectionSwitch();
        ProductList.plusFunction();
        ProductList.imgSelectFunction();
        ProductList.cancleFunction();
        ProductList.confirmFunction();
        ProductList.moreFunction();
        ProductList.footerBtnHover();
        ProductList.collapseFn($(".footer-more"),$(".footer-less"),$(".classic-body"));
        ProductList.brandIconHover();
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
    /*商品类别切换*/
    productSwitch:function(obj,target,className){
        target.eq(0).show().siblings().hide();
        obj.hover(function(){
            $(this).addClass(className).siblings().removeClass(className);
            target.eq($(this).index()).show().siblings().hide();
            target.eq($(this).index()).hover(function(){
                $(this).show();
                obj.eq($(this).index()).addClass(className).siblings().removeClass(className);
            },function(){
                $(this).hide();
                target.eq(0).show().siblings().hide();
                obj.eq(0).addClass(className).siblings().removeClass(className);
            });
        },function(){
            $(this).removeClass(className);
            obj.eq(0).addClass(className).siblings().removeClass(className);
        })
    },
    /*section商品切换*/
    sectionSwitch:function(){
        for(var i = 0;i<$(".section").length;i++){
            Index.productSwitch($(".section").eq(i).find(".section-right-title").find("span"),$(".section").eq(i).find(".section-right-content-group"),"section-right-active");
        }
    },
    inputFn:function(){
        $("input[type=checkbox]").change(function(){
            if($(this).prop("checked")){
                $(this).closest('i').addClass("active");
            }else{
                $(this).closest('i').removeClass("active");
            }
        });
    },
    /*多选按钮切换*/
    plusFunction:function(){
        $(".plus").click(function(){
            $(this).siblings(".more").hide();
            $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-checkbox-wrap").show().siblings(".classic-select-radio").hide();
            $(this).closest(".classic-body-group").addClass("classic-body-group-active");
        });
    },
    /*更多按钮功能*/
    moreFunction:function(){
        /*初始化UL的高度*/
        for(var i = 0 ;i < $(".classic-select-radio").length;i++){
            $(".classic-select-radio").eq(i).height($(".classic-select-radio").eq(i).find("li").outerHeight(true));
        };
        for(var i = 0;i<$(".classic-btn-group").length;i++){
            /*获取数据长度*/
            var n = $(".classic-btn-group").eq(i).siblings('.classic-content-select').find(".classic-select-radio").find("li").length;
            console.log(n);
            if(n <= 10){
                $(".classic-btn-group").eq(i).find(".more").hide();
            }
        };
        $(".more").click(function(){
            /*获取单个li高度*/
            var oLi = $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-radio").find("li");
            var oUl = $(this).closest(".classic-btn-group").siblings(".classic-content-select").find(".classic-select-radio");
            /*计算行数*/
            var n = Math.ceil( oLi.length / 10 );
            if(parseInt( oLi.outerHeight(true) ) == parseInt(oUl.outerHeight(true)) ){
                oUl.height(n*oLi.outerHeight(true));
                $(this).html("<i class='up'></i>收起");
            }else{
                oUl.height(oLi.outerHeight(true));
                $(this).html("<i></i>更多");
            }
        });
    },
    /*图片多选*/
    imgSelectFunction:function(){
        var dataArray = [];
        var html;
        $(".classic-select-checkbox-imgBox").find("li").click(function(){
            if($(this).hasClass("active")){
                $(this).removeClass("active");
                /*获取关联的input-name数据(这个字段一定不一样)*/
                var inputValue = $(this).find(".mask-img").attr('data-input-value');
                for(var i = 0;i<dataArray.length;i++){
                    if(dataArray[i].inputValue == inputValue){
                        /*找出已经存在的json,从数组中移除*/
                        dataArray.splice(i,1);
                    }
                };
                /*重置HTML*/
                if(dataArray.length > 0){
                    html = '<li class="pull-left">已经选条件：</li>';
                }else{
                    html='';
                    $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').removeClass("active");
                    $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').attr("disabled",true);
                }
                for(var i=0;i<dataArray.length;i++){
                    html +=  '<li class="pull-left"><label><i class="active"><input checked="checked" type="checkbox" name="'+dataArray[i].category+'" value="'+dataArray[i].inputValue+'"></i>'+dataArray[i].name+'</label></li>';
                };
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox").html(html);
            }else{
                /*重置HTML*/
                html = '<li class="pull-left">已经选条件：</li>';
                $(this).addClass("active");
                /*获取关联name数据*/
                var name = $(this).find(".mask-img").attr("data-name");
                /*获取关联的category数据*/
                var category = $(this).find(".mask-img").attr('data-category');
                /*获取关联的input-name数据*/
                var inputValue = $(this).find(".mask-img").attr('data-input-value');
                /*获取初始状态状态下ul的innerHTML*/
                /*存到对象中*/
                var json = {name:name,category:category,inputValue:inputValue};
                /*添加到数组中*/
                dataArray.push(json);
                for(var i=0;i<dataArray.length;i++){
                    html +=  '<li class="pull-left"><label><i class="active"><input checked="checked" type="checkbox" name="'+dataArray[i].category+'" value="'+dataArray[i].inputValue+'"></i>'+dataArray[i].name+'</label></li>';
                };
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox").html(html);
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').addClass("active");
                $(this).closest(".classic-select-checkbox-imgBox").next("form").find(".classic-select-checkbox-btn").find('input').attr("disabled",false);
            }
        });
    },
    /*取消按钮的事件*/
    cancleFunction:function(){
        $(".classic-select-checkbox-btn").find("span").click(function(){
            $(this).closest(".classic-select-checkbox-wrap").hide().siblings(".classic-select-radio").show();
            var n = $(this).closest(".classic-select-checkbox-wrap").siblings('.classic-select-radio').find('li').length;
            if(n>10){
                $(this).closest(".classic-content-select").siblings(".classic-btn-group").find(".more").show();
            }
            $(this).closest(".classic-body-group").removeClass('classic-body-group-active');
        });
    },
    /*多选中确认按钮事件*/
    confirmFunction:function(){
        $(".classic-select-checkbox").find('input').change(function(){
            var li = $(this).closest('.classic-select-checkbox').find('li');
            var onOff = false;
            for(var i=0;i<li.length;i++){
                if( li.eq(i).find("i").hasClass('active') ){
                    onOff = true;
                    break;
                }else{
                    onOff = false;
                }
            };
            console.log(onOff);
            if(onOff){
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').addClass('active');
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').attr("disabled",false);
            }else{
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').removeClass('active');
                $(this).closest('.classic-select-checkbox').siblings('.classic-select-checkbox-btn').find('input').attr("disabled",true);
            }
        });
    },
    /*品牌鼠标进过效果*/
    brandIconHover:function(){
        $("#brandCategory").find(".img-box").hover(function(){
            $(this).find("img").hide();
            $(this).closest("li").css("border","2px solid #cc9b4b");
        },function(){
            $(this).find("img").show();
            $(this).closest("li").css("border","1px solid #dbdbdb");
        });
        $("#brandCategory").find(".link-img").hover(function(){
            $(this).find("img").hide();
            $(this).closest("li").css("border","2px solid #cc9b4b");
        },function(){
            $(this).find("img").show();
            $(this).closest("li").css("border","1px solid #dbdbdb");
        });
    },
    /*更多选项样式变化*/
    footerBtnHover:function(){
        $(".classic-footer-btn").hover(function(){
            $(this).closest(".classic-footer").addClass("classic-footer-active");
        },function(){
            $(this).closest(".classic-footer").removeClass("classic-footer-active");
        });
    },
    /*更多选项  和收起交互*/
    collapseFn:function(obj1,obj2,target){
        /*设置开关*/
        var onOff = true;
        /*初始化高度*/
        var height = 0;
        /*只显示4组*/
        for(var i=0;i<4;i++){
            height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
        };
        target.css("height",height);

        $(".more").click(function(){
            if(onOff){
                /*重置height的值*/
                height = 0;
                for(var i=0;i<4;i++){
                    height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
                };
                target.css("height",height);
            }
        });
        $(".plus").click(function(){
            if(onOff){
                /*重置height的值*/
                height = 0;
                for(var i=0;i<4;i++){
                    height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
                };
                target.css("height",height);
            }
        });

        obj1.click(function(){
            target.css("height","auto");
            $(this).hide();
            obj2.show();
            onOff = false;
        });
        obj2.click(function(){
            /*重置height的值*/
            height = 0;
            /*获取target子集的长度*/
            var len = target.find(".classic-body-group").length;
            for(var i=0;i<4;i++){
                height += parseInt( target.find(".classic-body-group").eq(i).outerHeight(true) );
            };
            target.css("height",height);
            $(this).hide();
            obj1.show();
            onOff = true;
        });
    }
};

$(function(){
    ProductList.init();
});