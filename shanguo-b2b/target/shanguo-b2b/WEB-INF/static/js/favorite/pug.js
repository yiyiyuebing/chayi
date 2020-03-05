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
        Pug.pugList1();
        Pug.pugList2();
        Pug.scrollAjax();
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

    parentId : function () {
        $.ajax({
            type:"",
            url:"",
            data:{},
            success : function (result) {

            }


        });
    },

    /*今天 数据请求*/
    pugList1 : function () {
        var url = "/favorite/pugList";
        var startDate = new Date( new Date().setHours(0,0,0,0) );
        var endDate = new Date( new Date().setHours(0,0,0,0) + 24*3600*1000 );
        var time  = $.formatDate("yyyy-MM-dd", new Date());
        $(".time").html(time);
        /*今天   数据列表ID集合*/
        /*POST请求参数*/
        var params = {"startDate":startDate,"endDate":endDate};
        $.post(url,params,function(result){
            if(result.success && result.data.res.gblv.length > 0 ){
                var p = result.data.res.price;
                console.log("输出"+ p);
                var data = result.data.res.gblv;
                console.log(data);
                $.each(data,function(index,item){
                    var tpl = template("todayTpl",item);
                    $("#todayWrap").append(tpl);
                });
            }else{
                $("#todayWrap").append("今天还没逛过！！！");
            }
        },'json');

    },
    /*退后一周  数据请求*/
    pugList2 : function () {
        var url = "/favorite/pugList";
        var startDate = new Date( new Date().setHours(0,0,0,0) -8*24*3600*1000 );
        var endDate =  new Date( new Date().setHours(0,0,0,0)-1 );
        /*POST请求参数*/
        var params = {"startDate":startDate,"endDate":endDate};
        $.post(url,params,function(result){
            if(result.success && result.data.res.gblv.length > 0){
                var data = result.data.res.gblv;
                $.each(data,function(index,item){
                    var weekTpl = template("todayTpl",item);

                    $("#weekWrap").append(weekTpl);
                });
            }else{
                console.log("xinqi");
                $("#weekWrap").append("<div class='week-tips'>您已经一周没来逛过了！！</div>");
            }
        },'json');
    },

    /*滚动 Ajax 请求*/
    scrollAjax:function(x){
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
            x = 10;//底部fixed导航的高度;$(xxx).height();
        }
        if(isScroll().scrollY){//如果存在垂直滚动条
            var n = 2;
            var url = "/favorite/pugList";
            $(document).scroll(function () {//滚动开始
                /*滚动距离*/
                n++;
                var startDate = new Date( new Date().setHours(0,0,0,0) -7*n*24*3600*1000 );
                var endDate = new Date(new Date().setHours(0,0,0,0) - 7*(n+1)*24*3600*1000);
                /*POST请求参数*/
                var params = {"startDate":startDate,"endDate":endDate};
                var t = $('body').scrollTop();
                /*页面总高度*/
                var th = document.body.scrollHeight || document.documentElement.scrollHeight;
                /*可视区高度*/
                var wh = document.body.clientHeight || document.documentElement.scrollHeight;

                /*初始状态下的HTML*/

                if(t > th - wh - x){//刚滚动至底部距离xPX时
                    var ll= layer.msg("加载中");
                    $.post(url,params,function(result) {//post请求
                       if(result &&  result.data.res.gblv.length > 0){
                           layer.close(ll);
                           var data = result.data.res.gblv;
                           $.each(data,function(index,item){
                               var earlierTpl = template("earlierTpl",item);
                               $("#earlierWrap").append(earlierTpl);
                           });
                       }else{
                           layer.close(ll);
                           var lay = layer.msg("已经没有更多足迹了");
                           setTimeout(function(){
                               layer.close(lay);
                           },2000)
                       }
                   },'json');
                }
            })
        }
    }
}