/**
 * Created by cg on 17-7-8.
 */
var Footer = {
    init:function () {
        Footer.getFooterAdImages();
    },

    getFooterAdImages:function () {
      $.ajax({
          type: "POST",
          url: "/index/getFooterAdImages",
          success:function (result) {
              if(result.success){
                  $(".footer-banner").empty();
                  $.each(result.data, function (i, footerAdImages) {
                      var tpl_footer_banner = template("tpl-footer-banner", footerAdImages);
                      $(".footer-banner").append($(tpl_footer_banner));
                  })

              }
          }
      })
    },
}

$(function () {
    Footer.init();
})