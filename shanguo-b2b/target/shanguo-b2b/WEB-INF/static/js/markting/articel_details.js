/**
 * Created by Administrator on 2017/5/11.
 */
var  MarktingDetail = {
    init: function() {
        initNavBar("markting");
        var d1 = new Date("2017-08-01 00:00:00");
        var d2 = new Date("2016-08-01 00:00:00");
        console.log(d1.getTime() - d2.getTime());
        console.log(createTime);
        if (createTime) {
            var nowDateLong = new Date().getTime();
            var createDateLong = new Date(createTime);
            var sublong = nowDateLong - createDateLong;
            if (sublong <= 31536000000 ) {
                $(".datetime").removeClass("hide");
            } else {
                $(".olddatetime").removeClass("hide");
            }
        }

        $(".article .article-aside").find("li[data-value="+ articleType +"]").addClass("active");
    }
};

$(function(){
    MarktingDetail.init();
});