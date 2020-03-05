/**
 * Created by dy on 2017/6/5.
 */
var SearchHeader = {

    init: function() {
        SearchHeader.getSearchKeyword();
        $(".pull-left").keydown(function(event){
            // event = event || window.event;
            if(event.keyCode == 13){
                SearchHeader.search();
            }
        });

        $("#search-header").click(function(event){
            // event = event || window.event;
            SearchHeader.search();
        });

        SearchHeader.getIndexAdImages();
    },

    search:function(){
        var purGoodsName = $(".search-input").find("input").val() ? $.trim($(".search-input").find("input").val()) : $(".search-input").find("input").attr("placeholder");
        var queryParam = {
            keyword: purGoodsName
        };
        var queryParamStr = $.jsonToGetParamstr(queryParam);
        window.location.href = queryParamStr&& queryParamStr.length > 0 ? "/purchase/list?" + queryParamStr : "/purchase/list";

    },

    getIndexAdImages:function () {
      $.ajax({
          type: "POST",
          url: "/index/getIndexAdImages",
          success:function (result) {
              if(result.success){
                  $(".header-banner-wrap").empty();
                  if (!result.data || !(result.data.length > 0)) {
                      $(".header-banner").hide();
                  }
                  $.each(result.data, function (i, indexAdImage) {
                      var tpl_header_banner_wrap = template("tpl-header-banner-wrap", indexAdImage);
                      $(".header-banner-wrap").append($(tpl_header_banner_wrap));
                  })

              }

          }
      })
    },

    getSearchKeyword: function() {
        $.ajax({
            type: "POST",
            url: "/search/headKeyword",
            success: function(result) {
                if (result.success) {
                    $(".search-keywords").empty();
                    if (!result.data || !(result.data.length > 0)) {
                        $(".search-keywords").hide();
                    }
                    $.each(result.data, function(i, perKeyword) {
                        var tpl_search_keywords = template("tpl-search-keywords", perKeyword);
                        $(".search-keywords").append($(tpl_search_keywords));
                    })
                }
            }
        });
    }
};

$(function() {
    SearchHeader.init();
});