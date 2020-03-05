$(function() {
    PersonCommon.delTipsFun();
    PersonCommon.checkFun();
    PersonCommon.cutNav();
    Pug.init();
});

var Pug = {
    init : function() {
        Pug.showBatchBox();
        Pug.hideBatchBox();
        Pug.pugList1(null);
        Pug.pugList2(null);
        Pug.scrollAjax(null);
    },

    showBatchBox : function() {
        $("#batch-btn").click(function() {
            $("#title-batch-box").addClass("check");
            $("#pug-list-box").addClass("check");

            Pug.checkActive();
        });
    },

    hideBatchBox :function() {
        $("#complete-btn").click(function () {
            $("#title-batch-box").removeClass("check");
            $("#pug-list-box").removeClass("check");
            $("#pug-list-box .list-item").removeClass("active");

            Pug.unBindCheckActive();
        });
    },

    checkActive : function() {
        $("#pug-list-box .list-item").click(function() {
           $(this).toggleClass("active");
        });
    },

    unBindCheckActive : function() {
        $("#pug-list-box .list-item").unbind("click");
    },

    classIfy : function () {
        var all = $(".active").attr("data-all");
        $(".active").click(function(){
            Pug.pugList1(all);
            Pug.pugList2(all);
            Pug.scrollAjax(all);
        });

    },
    classIfy1 : function () {
        var jpc = $(".jpc").attr("data-jpc");
        $(".jpc").click(function(){
            Pug.pugList1(jpc);
            Pug.pugList2(jpc);
            Pug.scrollAjax(jpc);
        });

    },
    classIfy2 : function () {
        var dzc = $(".dzc").attr("data-dzc");
        $(".dzc").click(function(){
            Pug.pugList1(dzc);
            Pug.pugList2(dzc);
            Pug.scrollAjax(dzc);
        });

    },
    classIfy3 : function () {
        var cj = $(".cj").attr("data-cj");
        $(".cj").click(function(){
            Pug.pugList1(cj);
            Pug.pugList2(cj);
            Pug.scrollAjax(cj);

        });

    },
    classIfy4 : function () {
        var cs = $(".cs").attr("data-cs");
        $(".cs").click(function(){
            Pug.pugList1(cs);
            Pug.pugList2(cs);
            Pug.scrollAjax(cs);
        });

    },



    /*今天 数据请求*/
    pugList1 : function (classifyId) {
        var startDate = $.formatDate("yyyy-MM-dd",new Date( new Date().setHours(0,0,0,0)));
        var endDate =  $.formatDate("yyyy-MM-dd",new Date( new Date().setHours(0,0,0,0) + 23*3600*1000 ));
        var time  = $.formatDate("yyyy-MM-dd", new Date());
        $(".time").html(time);
        /*今天   数据列表ID集合*/
        /*POST请求参数*/
        $.ajax(
            {
                type:"POST",
                url:"/goodsBrowseLog/getBrowseLogList",
                data:{startDate:startDate,endDate:endDate,classifyId:classifyId},
                success:function (result) {
                    $("#todayWrap").empty();
                    if(result.success && result.data.logList ){
                        var data = result.data.logList;
                        if (data!="" || data !=null) {
                        $.each(data,function(index,item){
                            var tpl = template("todayTpl",item);
                            $("#todayWrap").append(tpl);
                        });
                        }
                    }else{
                        $("#todayWrap").append("今天还没逛过！！！");
                    }
                }
            }
        );
    },
    /*退后一周  数据请求*/
    pugList2 : function (classifyId) {
        var url = "/goodsBrowseLog/getBrowseLogList";
        var startDate = $.formatDate("yyyy-MM-dd", new Date( new Date().setHours(0,0,0,0) -8*24*3600*1000 ));
        var endDate =  $.formatDate("yyyy-MM-dd", new Date( new Date().setHours(0,0,0,0)-1 ));
        /*POST请求参数*/
        var modelJson = {"startDate":startDate,"endDate":endDate,classifyId:classifyId};
        $.post(url,modelJson,function(result){
            $("#weekWrap").empty();
            if(result.success && result.data.logList){
                var data = result.data.logList;
                if (data!="" || data !=null) {
                    $.each(data, function (index, item) {
                        var weekTpl = template("todayTpl", item);
                        $("#weekWrap").append(weekTpl);
                    });
                }
            }else{
                console.log("xinqi");
                $("#weekWrap").append("<div class='week-tips'>您已经一周没来逛过了！！</div>");
            }
        },'json');
    },

    /*滚动 Ajax 请求*/
    scrollAjax:function(x,classifyId){
        var isScroll = function (el) {//判断当前是否有滚动条
                // 判断的目标
                var elems = el ? [el] : [document.documentElement, document.body];
                var scrollX = false, scrollY = false;
                for (var i = 0; i < elems.length; i++) {
                    var o = elems[i];
                    //判断当前是否有水平滚动条
                    var sl = o.scrollLeft;
                    o.scrollLeft += (sl > 0) ? -1 : 1;
                    o.scrollLeft !== sl && (scrollX = scrollX || true);
                    o.scrollLeft = sl;
                    //判断当前是否有垂直滚动条
                    var st = o.scrollTop;
                    o.scrollTop += (st > 0) ? -1 : 1;
                    o.scrollTop !== st && (scrollY = scrollY || true);
                    o.scrollTop = st;
                }
                // ret
                return {
                    scrollX: scrollX,
                    scrollY: scrollY
                };
            };
        if(!x){
            x = -100;//底部fixed导航的高度;$(xxx).height();
        }
        if(isScroll().scrollY){//如果存在垂直滚动条
            var n = 0;
            var url = "/goodsBrowseLog/getBrowseLogList";
            $(document).bind("scroll",function () {//滚动开始
                /*滚动距离*/
                var t = $('body').scrollTop();
                /*可视区高度*/
                var wh = $(window).height();
                /*底部的高度*/
                var footerBanner = $(".footer-banner").offset().top;
                if(n <= 10){
                    if(t > footerBanner - wh - x && footerBanner - wh - x > 0){//刚滚动至底部距离xPX时
                        n++;
                        console.log(n);
                        var startDate = $.formatDate( "yyyy-MM-dd",new Date( new Date().setHours(0,0,0,0) - 8*24*3600*1000 -3*(n+1)*24*3600*1000 ) );
                        var endDate = $.formatDate( "yyyy-MM-dd",new Date(new Date().setHours(0,0,0,0) - 8*24*3600*1000 - 3*n*24*3600*1000) );
                        /*POST请求参数*/
                        var modelJson = {"startDate":startDate,"endDate":endDate,classifyId:classifyId};
                        /*初始状态下的HTML*/
                        $.post(url,modelJson,function(result) {//post请求
                            if(result &&  result.data.logList){
                                var data = result.data.logList;
                                if (data!="" && data !=null) {
                                    $.each(data, function (index, item) {
                                        var earlierTpl = template("earlierTpl", item);
                                        $("#earlierWrap").append(earlierTpl);
                                    });
                                }
                            }
                        },'json');
                    }
                }else{
                    $(this).unbind("scroll");
                    layer.msg("没有更多了足迹了...");
                }

            })
        }
    },

    delPugtoday: function () {
        $(".del-jt").click(function () {
            layer.confirm('确定删除今天的足迹?', {title: '提示'}, function (index) {
                Pug.delPugtoday1();
                layer.close(index);

            });
        });
    },

    delPugweek: function () {
        $(".del-yz").click(function () {
            layer.confirm('确定删除一周内的足迹?', {title: '提示'}, function (index) {
                Pug.delPugweek1();
                layer.close(index);

            });
        });
    },


    delPugearlier: function () {
        $(".del-gz").click(function () {
            layer.confirm('确定删除更早之前的足迹?', {title: '提示'}, function (index) {
               Pug.delPugearlier1();
                layer.close(index);

            });
        });
    },

    delPugtoday1 : function(){
        var startDate = $.formatDate("yyyy-MM-dd",new Date( new Date().setHours(0,0,0,0)));
        var endDate =  $.formatDate("yyyy-MM-dd",new Date( new Date().setHours(0,0,0,0) + 23*3600*1000 ));
        $.ajax({
            type:"POST",
            url:"/goodsBrowseLog/delBrowseLogByDate",
            data:{startDate:startDate,endDate:endDate},
            success:function (result) {
                if(result.success) {
                    layer.msg("删除成功！");
                    Pug.pugList1(null);
                    Pug.pugList2(null);
                    Pug.scrollAjax(null);

                }else{
                    layer.msg("删除失败！");
                }
            }

        });


    },
    delPugweek1 : function(){
        var startDate = $.formatDate("yyyy-MM-dd", new Date( new Date().setHours(0,0,0,0) -8*24*3600*1000 ));
        var endDate =  $.formatDate("yyyy-MM-dd", new Date( new Date().setHours(0,0,0,0)-1 ));
        $.ajax({
            type:"POST",
            url:"/goodsBrowseLog/delBrowseLogByDate",
            data:{startDate:startDate,endDate:endDate},
            success:function (result) {
                if(result.success) {
                    layer.msg("删除成功！");
                    Pug.pugList1(null);
                    Pug.pugList2(null);
                    Pug.scrollAjax(null);
                }else{
                    layer.msg("删除失败！");
                }
            }

        });


    },

    delPugearlier1 : function(){
        var startDate =  $.formatDate("yyyy-MM-dd", new Date( new Date().setHours(0,0,0,0) -7*n*24*3600*1000 ));
        var endDate = $.formatDate("yyyy-MM-dd", new Date(new Date().setHours(0,0,0,0) - 7*(n+1)*24*3600*1000));
        $.ajax({
            type:"POST",
            url:"/goodsBrowseLog/delBrowseLogByDate",
            data:{startDate:startDate,endDate:endDate},
            success:function (result) {
                if(result.success) {
                    layer.msg("删除成功！");
                    Pug.pugList1(null);
                    Pug.pugList2(null);
                    Pug.scrollAjax(null);
                }else{
                    layer.msg("删除失败！");
                }
            }

        });


    },



}