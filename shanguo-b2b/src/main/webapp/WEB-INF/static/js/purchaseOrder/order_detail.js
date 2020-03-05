var OrderDetail= {

    init: function () {
        if (!$.checkLogin()) { //验证登录
            return;
        }
        OrderDetail.Countdown();
        OrderDetail.express();
        var mobile = phone;
        var mphone = mobile.substr(0, 3) + '****' + mobile.substr(7);
        $("#phone").html(mphone);

    },

    cancel: function () {
        layer.confirm('确定取消订单?', {title: '提示'}, function (index) {
            OrderDetail.cancel1();
            layer.close(index);

        });
    },

    cancel1: function () {
        var orderId = $("#orderId").val();
        var orderType = $("#orderType").val();
        $.ajax({
            type: "POST",
            url: "/order/cancel",
            data: {orderId: orderId},
            success: function (result) {
                if (result.success && orderType == 'normal') {
                    window.location.href = "/order/list";
                } else if (result.success && orderType == 'presell') {
                    window.location.href = "/order/precellOrderList";
                } else {
                    layer.msg(result.msg);
                }

            }
        });
    },

    warn: function () {
        layer.confirm('是否提醒发货?', {title: '提示'}, function (index) {
            OrderDetail.warn1();
            layer.close(index);

        });
    },

    warn1: function () {
        var orderId = $("#orderId").val();
        var orderType = $("#orderType").val();
        $.ajax({
            type: "POST",
            url: "/order/shipNotice",
            data: {orderId: orderId, orderType: orderType},
            success: function (result) {
                if (result.success) {
                    layer.msg("提醒发货成功！");
                } else {
                    layer.msg(result.msg);
                }

            }
        });
    },


    receiving: function () {
        layer.confirm('确认已收到商品？订单完成后不能申请售后', {title: '提示'}, function (index) {
            layer.closeAll();
            OrderDetail.receiving1();
        });
    },

    receiving1: function () {
        var orderList = [];
        var orderObj = {};
        orderObj.orderId = $("#orderId").val();
        orderObj.orderType = $("#orderType").val();
        orderObj.confirmType = "user";
        orderObj.orderBizType = "purchase";
        orderList.push(orderObj);
        $.ajax({
            type: "POST",
            url: "/order/confReceipt",
            data: {confirmInfoListJson: JSON.stringify(orderList)},
            success: function (result) {
                if (result.success) {
                    //边缘弹出
                    layer.open({
                        closeBtn: 0
                        ,type: 1
                        ,content: '<div style="padding: 20px 80px;">确认收货成功</div>'
                        ,btn: '确定'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();
                            location.reload();
                        }
                    });
                } else {
                    //边缘弹出
                    layer.open({
                        closeBtn: 0
                        ,type: 1
                        ,content: '<div style="padding: 20px 80px;">'+ result.msg +'</div>'
                        ,btn: '知道了'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();

                        }
                    });
                    //layer.msg();
                }

            }
        });


    },
    Countdown: function () {
        var timeOut = new Date(timeout).getTime();
        setInterval(function () {
            var result;
            var date = new Date().getTime();
            if (date < timeOut) {
                result = timeOut - date;
                var h = Math.floor(result / 1000 / 60 / 60 % 24);
                console.log(h);
                var m = Math.floor(result / 1000 / 60 % 60);
                console.log(m);
                if (h < 10 && h > 0) {
                    $(".hour").html("0" + h);
                } else {
                    $(".hour").html(h);
                }
                if (m < 10 && m > 0) {
                    $(".minute").html("0" + m);
                } else {
                    $(".minute").html(m);
                }
            }
        }, 1000);

    },

    formatDate: function (date, format) {
        var v = "";
        if (typeof date == "string" || typeof date != "object") {
            return;
        }
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var weekDay = date.getDay();
        var ms = date.getMilliseconds();
        var weekDayString = "";

        if (weekDay == 1) {
            weekDayString = "周一";
        } else if (weekDay == 2) {
            weekDayString = "周二";
        } else if (weekDay == 3) {
            weekDayString = "周三";
        } else if (weekDay == 4) {
            weekDayString = "周四";
        } else if (weekDay == 5) {
            weekDayString = "周五";
        } else if (weekDay == 6) {
            weekDayString = "周六";
        } else if (weekDay == 0) {
            weekDayString = "周日";
        }

        v = format;
        //Year
        v = v.replace(/yyyy/g, year);
        v = v.replace(/YYYY/g, year);
        v = v.replace(/yy/g, (year + "").substring(2, 4));
        v = v.replace(/YY/g, (year + "").substring(2, 4));

        //Month
        var monthStr = ("0" + month);
        v = v.replace(/MM/g, monthStr.substring(monthStr.length - 2));

        //Day
        var dayStr = ("0" + day);
        v = v.replace(/dd/g, dayStr.substring(dayStr.length - 2));

        //hour
        var hourStr = ("0" + hour);
        v = v.replace(/HH/g, hourStr.substring(hourStr.length - 2));
        v = v.replace(/hh/g, hourStr.substring(hourStr.length - 2));

        //minute
        var minuteStr = ("0" + minute);
        v = v.replace(/mm/g, minuteStr.substring(minuteStr.length - 2));

        //Millisecond
        v = v.replace(/sss/g, ms);
        v = v.replace(/SSS/g, ms);

        //second
        var secondStr = ("0" + second);
        v = v.replace(/ss/g, secondStr.substring(secondStr.length - 2));
        v = v.replace(/SS/g, secondStr.substring(secondStr.length - 2));

        //weekDay
        v = v.replace(/E/g, weekDayString);
        return v;
    },


    express : function (){
        if (!expressNumber) {
            return;
        }
        $.ajax({
            type: "POST",
            url: "/express/expressInfo",
            data: {num: expressNumber},
            success : function (result){
             if (result.success && result.data.data){
                 $.each(result.data.data,function(i,express) {
                     express.ftim=OrderDetail.formatDate(new Date(express.ftime),"yyyy-MM-dd/E HH:mm:ss");

                     console.log(OrderDetail.formatDate(new Date(express.ftime),"yyyy-MM-dd/E"));
                     var expresslist = template("expressList", express);
                     $(".express").append($(expresslist));
                 });
             }
            }
        });
    }
}
$(function() {
    OrderDetail.init();
})
