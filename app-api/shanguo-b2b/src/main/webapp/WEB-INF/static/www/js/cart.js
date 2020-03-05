$(function() {
    PersonCommon.delTipsFun();
    PersonCommon.checkFun();
    PersonCommon.cutNav();
    Cart.init();
});

var Cart = {
    init: function () {
        Cart.cartCheckFun();
        Cart.lessCount();
        Cart.plusCount();
        Cart.countIptFun();
    },

    cartCheckFun : function() {
        $(".cart-check-tagt").click(function() {
            var tagt = $(this),
                myTr = tagt.parents("tr:first");
            tagt.toggleClass("active");
            myTr.toggleClass("active");

            Cart.changeBtnFun();
        });
    },

    changeBtnFun : function() {
        var countBtn = $("#count-btn");
        if ($("#cart-list tr.active").size() > 0) {
            countBtn.removeClass("disable");
        }
        else {
            countBtn.addClass("disable");
        }
    },

    lessCount : function() {
        $(".amount-less").click(function() {
            var myTagt = $(this).parents(".item-amount:first"),
                countTagt = myTagt.find(".text-amount"),
                count = parseInt(countTagt.val());
            if (count == 1) {
                return;
            }

            countTagt.val(count - 1);
        });
    },

    plusCount : function() {
        $(".amount-plus").click(function() {
            var myTagt = $(this).parents(".item-amount:first"),
                countTagt = myTagt.find(".text-amount"),
                count = parseInt(countTagt.val());
            countTagt.val(count + 1);
        });
    },

    countIptFun : function() {
        $(".text-amount").blur(function() {
            var myTagt = $(this).parents(".item-amount:first"),
                countTagt = myTagt.find(".text-amount"),
                countNumber = countTagt.val();

            var pattern = /^[1-9]*[1-9][0-9]*$/;
            var m = countNumber.match(pattern);
            if (m == null) {
                countTagt.val("1");
            }
        });
    }
}