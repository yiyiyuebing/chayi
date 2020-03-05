<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/2
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="content-type" content="text/html;charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="renderer" content="webkit" />
  <meta name="baidu-site-verification" content="5SXEHqxLUt" />
  <title>优茶联—集茶叶、茶具、茶礼等多品牌茶业联合的采购、批发、销售、进货、经销渠道为一体的茶行业专业系统运营商。</title>
  <meta name="keyword" content="茶叶,茶具,采购,销售,批发,进货,经销,优茶联"/>
  <meta name="description" content="优茶联是茶行业联合运营平台,优茶联以茶为主题,为茶商、茶友提供茶叶、茶具、茶礼等多品牌茶业联合的采购、批发、销售,解决进货渠道难题。海量产品，正品保障，满足需求，让您购物更轻松！"/>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/index.css">
</head>
<body>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/search_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--导航栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_top_bar.jsp"></jsp:include>
<%--导航栏 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>
<!--banner-->
<div class="banner-wrap">
  <div class="banner">
    <div class="swiper-container">
      <div class="swiper-wrapper">

        <c:forEach items="${mainsliderAds}" var="mainsliderAd">
          <div class="swiper-slide">
            <c:if test="${mainsliderAd.linkType == 'good'}">
              <a href="/purchase/detail/${mainsliderAd.linkGoodId}.html">
                <img src="${mainsliderAd.imageUrl}">
              </a>
            </c:if>
            <c:if test="${mainsliderAd.linkType == 'diy'}">
              <c:if test="${empty mainsliderAd.linkDescribe}">
                <a href="javascript:void(0);">
                  <img src="${mainsliderAd.imageUrl}">
                </a>
              </c:if>
              <c:if test="${!empty mainsliderAd.linkDescribe}">
                <a href="${mainsliderAd.linkDescribe}"  target="_blank">
                  <img src="${mainsliderAd.imageUrl}">
                </a>
              </c:if>

            </c:if>
            <c:if test="${mainsliderAd.linkType == 'classify'}">
              <a href="/purchase/list?classifyId=${mainsliderAd.lnkUrl}">
                <img src="${mainsliderAd.imageUrl}">
              </a>
            </c:if>
            <c:if test="${empty mainsliderAd.linkType}">
              <a href="javascript:void(0);">
                <img src="${mainsliderAd.imageUrl}">
              </a>
            </c:if>
          </div>
        </c:forEach>
      </div>
      <div class="pagination"></div>
    </div>
  </div>
</div>

<div class="recommend-img-group clearfix">
  <c:forEach items="${subimagesAds}" begin="0" end="0" var="subimagesAd">
    <c:if test="${subimagesAd.linkType == 'good'}">
      <a class="pull-left" href="/purchase/detail/${subimagesAd.linkGoodId}.html">
        <img src="${subimagesAd.imageUrl}">
      </a>
    </c:if>
    <c:if test="${subimagesAd.linkType == 'diy'}">
      <c:if test="${empty subimagesAd.linkDescribe}">
        <a class="pull-left" href="javascript:void(0);">
          <img src="${subimagesAd.imageUrl}">
        </a>
      </c:if>
      <c:if test="${!empty subimagesAd.linkDescribe}">
        <a class="pull-left" href="${subimagesAd.linkDescribe}" target="_blank">
          <img src="${subimagesAd.imageUrl}">
        </a>
      </c:if>
    </c:if>
    <c:if test="${subimagesAd.linkType == 'classify'}">
      <a class="pull-left" class="pull-left" href="/purchase/list?classifyId=${subimagesAd.lnkUrl}">
        <img src="${subimagesAd.imageUrl}">
      </a>
    </c:if>
    <c:if test="${empty subimagesAd.linkType && subimagesAd.linkType == ''}">
      <a class="pull-left" href="javascript:void(0);">
        <img src="${subimagesAd.imageUrl}">
      </a>
    </c:if>


    <%--<c:if test="${fn:length(subimagesAds) == 1 }">
      <a style="width: 100%!important;" class="pull-left" href="${subimagesAd.lnkUrl}">
        <img style="width: 100%!important;" src="${subimagesAd.imageUrl}">
      </a>
    </c:if>
    <c:if test="${fn:length(subimagesAds) == 2 }">
      <a class="pull-left" href="${subimagesAd.lnkUrl}">
        <img src="${subimagesAd.imageUrl}">
      </a>
    </c:if>--%>

  </c:forEach>
</div>
<div class="recommend-product clearfix">
  <div class="recommend-product-content pull-left">
    <div class="recommend-product-content-title clearfix">

    </div>
    <div class="recommend-product-content-list-wrap">

    </div>
  </div>
  <div class="recommend-product-news pull-right">
    <div class="recommend-product-news-title clearfix">
      <span class="pull-left news-title-active">公告</span>
      <span class="pull-left">媒体报道</span>
      <span class="pull-left">行业资讯</span>
    </div>
    <div class="recommend-product-news-content">
      <div class="recommend-product-news-content-group">
        <ul>
        </ul>

      </div>
      <div class="recommend-product-news-content-group">
        <ul>
        </ul>

      </div>
      <div class="recommend-product-news-content-group">
        <ul>
        </ul>

      </div>
    </div>
  </div>
</div>
<div class="index-show-lanmu">

</div>

<!--主体  ending-->
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/index/index.js"></script>

<%--热卖栏目 标题--%>
<script id="tpl-recommend-product-content-title" type="text/html">
  {{each reIndexModelList as reIndexModelTitle index}}
      {{if index < 5}}
        {{if index == 0}}
        <span class="pull-left recommend-product-content-title-active">{{reIndexModelTitle.moduleName}}</span>
        {{else}}
        <span class="pull-left">{{reIndexModelTitle.moduleName}}</span>
        {{/if}}
      {{/if}}
  {{/each}}
</script>

<%--热卖栏目 商品  （{{modelGood.good.purGoodsSkuName}}）--%>
<script id="tpl-recommend-product-content-list-wrap" type="text/html">
  {{each reIndexModelList as reIndexModel index}}
      <ul class="recommend-product-content-list-group clearfix">
        {{each reIndexModel.indexModuleGoodVoList as modelGood index}}
        {{if index < 10}}
          <li class="pull-left" style="position: relative">
            {{if modelGood.good.stock <= 0}}
            <%--商品售罄图标--%>
            <i class="off-sale-icon-big"></i>
            <%--商品售罄图标ending--%>
            {{/if}}

            <a href="/purchase/detail/{{modelGood.good.id}}.html">
              <img src="{{modelGood.good.smallImage}}">
              <p>{{modelGood.good.purGoodsName}}</p>
                  <span>
                    {{if shopId}}
                     <sub style="font-size: 18px;">￥</sub>{{modelGood.good.supplyPrice}}<sub>元</sub>
                    {{else}}
                     <sub style="font-size: 18px;">￥</sub> <i style="color:#cc9b4b;font-style: normal;font-size: 18px;margin-left:-3px;">询价</i>
                    {{/if}}
                  </span>
            </a>
          </li>
        {{/if}}
        {{/each}}
      </ul>
  {{/each}}
</script>

<%--中间栏目处理 （{{modelGood.good.purGoodsSkuName}}）--%>
<script id="tpl-index-show-lanmu" type="text/html">
  {{each floorIndexModelList as floorIndexModel index}}
    <div class="section clearfix">
      <div class="section-left pull-left">
        <h4><i>{{index + 1}}</i>{{floorIndexModel.moduleName}}</h4>
        <div class="img-box">
          <div class="bg-img">
            <img src="{{floorIndexModel.leftAd}}">
          </div>
          {{if floorIndexModel.adType == 'good'}}
            <a href="/purchase/detail/{{floorIndexModel.adRelId}}.html" class="bg-link"></a>
          {{else if floorIndexModel.adType == 'classify'}}
            <a href="/purchase/list?classifyId={{floorIndexModel.adRelId}}" class="bg-link"></a>
          {{else if floorIndexModel.adType == 'diy'}}
            {{if floorIndexModel.leftAdUrlDescribe}}
              <a href="{{floorIndexModel.leftAdUrlDescribe}}" target="_blank" class="bg-link"></a>
            {{else}}
              <a href="javascript:void(0);" class="bg-link"></a>
            {{/if}}
          {{else}}
          <a href="javascript:void(0);" class="bg-link"></a>
          {{/if}}
          <div class="link-box">
            <div class="link-wrap clearfix">
              {{each floorIndexModel.floorKeywords as floorKeyword index}}
                {{if index < 8}}
                    <div class="link-wrap-box pull-left">
                    {{if floorKeyword.keywordType == 'good'}}
                    <a href="/purchase/detail/{{floorKeyword.keywordRelId}}.html">{{floorKeyword.keyword}}</a>
                    {{else if floorKeyword.keywordType == 'classify'}}
                    <a href="/purchase/list?classifyId={{floorKeyword.keywordRelId}}">{{floorKeyword.keyword}}</a>
                    {{else if floorKeyword.keywordType == 'diy'}}
                    {{if floorKeyword.keywordRelId}}
                    <a href="{{floorKeyword.keywordRelId}}" target="_blank">{{floorKeyword.keyword}}</a>
                    {{else}}
                    <a href="javascript:void(0);">{{floorKeyword.keyword}}</a>
                    {{/if}}
                    {{else}}
                    <a href="javascript:void(0);">{{floorKeyword.keyword}}</a>
                    {{/if}}
                  </div>
                {{/if}}
              {{/each}}
            </div>
          </div>
        </div>
      </div>
      <div class="section-right pull-left">
        <div class="section-right-title pull-right clearfix">
          {{each floorIndexModel.floorTopKeywords as floorTopKeyword index}}
            {{if index < 5}}
              {{if floorTopKeyword.keywordType == 'good'}}
                <a href="/purchase/detail/{{floorTopKeyword.keywordRelId}}.html" class="pull-left">{{floorTopKeyword.keyword}}</a>
              {{else if floorTopKeyword.keywordType == 'classify'}}
                <a href="/purchase/list?classifyId={{floorTopKeyword.keywordRelId}}" class="pull-left">{{floorTopKeyword.keyword}}</a>
              {{else if floorTopKeyword.keywordType == 'diy'}}
                {{if floorTopKeyword.keywordRelId}}
                  <a href="{{floorTopKeyword.keywordRelId}}" target="_blank" class="pull-left">{{floorTopKeyword.keyword}}</a>
                {{else}}
                  <a href="javascript:void(0);" class="pull-left">{{floorTopKeyword.keyword}}</a>
                {{/if}}
              {{else}}
                <a href="javascript:void(0);" class="pull-left">{{floorTopKeyword.keyword}}</a>
              {{/if}}
            {{/if}}
          {{/each}}
        </div>
        <div class="clearfix"></div>
        <div class="section-right-content">
          <ul class="section-right-content-group clearfix">
            {{each floorIndexModel.indexModuleGoodVoList as modelGood index}}
              {{if index < 8}}
                <li class="pull-left" style="position: relative;">

                  {{if modelGood.good.stock <= 0}}
                  <%--商品售罄图标--%>
                  <i class="off-sale-icon-big"></i>
                  <%--商品售罄图标ending--%>
                  {{/if}}
                  <a href="/purchase/detail/{{modelGood.good.id}}.html">
                    <img src="{{modelGood.good.smallImage}}">
                    <p>{{modelGood.good.purGoodsName}}</p>
                      <span>￥
                        {{if shopId}}
                          {{modelGood.good.supplyPrice}}<sub>元</sub>
                        {{else}}
                          <i class="consult-price">询价</i>
                        {{/if}}
                      </span>
                  </a>
                </li>
              {{/if}}
            {{/each}}
          </ul>
        </div>
      </div>
      <div class="clearfix"></div>
      {{if floorIndexModel.indexAdImages}}
        <div class="section-banner">
          {{if floorIndexModel.indexAdImages.linkType == 'good'}}
            <a href="/purchase/detail/{{floorIndexModel.indexAdImages.linkGoodId}}.html">
              <img src="{{floorIndexModel.indexAdImages.imageUrl}}">
            </a>
          {{else if floorIndexModel.indexAdImages.linkType == 'classify'}}
            <a href="/purchase/list?classifyId={{floorIndexModel.indexAdImages.lnkUrl}}">
              <img src="{{floorIndexModel.indexAdImages.imageUrl}}">
            </a>
          {{else if floorIndexModel.indexAdImages.linkType == 'diy'}}
            <a href="{{floorIndexModel.indexAdImages.lnkUrl}}">
              <img src="{{floorIndexModel.indexAdImages.imageUrl}}">
            </a>
          {{else}}
            <a href="javascript:void(0);">
              <img src="{{floorIndexModel.indexAdImages.imageUrl}}">
            </a>
          {{/if}}
        </div>
      {{/if}}
    </div>
  {{/each}}

</script>


<script id="tpl-news-title" type="text/html">
  {{each baseArticleInfos as baseArticleInfo index}}
      {{if index == 0}}
        <span class="pull-left news-title-active">{{baseArticleInfo.title}}</span>
      {{else}}
        <span class="pull-left">{{baseArticleInfo.title}}</span>
      {{/if}}
  {{/each}}
</script>

<script id="tpl-recommend-product-news-content-group" type="text/html">
  {{each baseArticleInfos as baseArticleInfo}}
    <div class="recommend-product-news-content-group">
      <ul>
        {{each baseArticleInfo.baseArticleInfos as articleInfo}}
        <li>
          {{if articleInfo.type == 'goods'}}
          <a href="/purchase/detail/{{articleInfo.id}}.html">【{{baseArticleInfo.title}}】{{articleInfo.title}}</a>
          {{else if articleInfo.type == 'gonggaotw'}}
          <a target="_blank" href="/news/gonggaoDetail/{{baseArticleInfo.articleType}}/{{articleInfo.id}}.html">【{{baseArticleInfo.title}}】{{articleInfo.title}}</a>
          {{else}}
          <a target="_blank" href="/news/detail/{{baseArticleInfo.articleType}}/{{articleInfo.id}}.html">【{{baseArticleInfo.title}}】{{articleInfo.title}}</a>
          {{/if}}
        </li>
        {{/each}}
      </ul>
      {{if baseArticleInfo.indexAdImages}}
        <div class="news-link-img">
          {{if baseArticleInfo.indexAdImages.linkType == 'diy'}}
            {{if baseArticleInfo.indexAdImages.linkDescribe}}
              <a href="{{baseArticleInfo.indexAdImages.linkDescribe}}" target="_blank">
                <img src="{{baseArticleInfo.indexAdImages.imageUrl}}">
              </a>
            {{else}}
              <a href="javascript:void(0);">
                <img src="{{baseArticleInfo.indexAdImages.imageUrl}}">
              </a>
            {{/if}}
          {{else if baseArticleInfo.indexAdImages.linkType == 'good'}}
            <a href="/purchase/detail/{{baseArticleInfo.indexAdImages.linkGoodId}}.html">
              <img src="{{baseArticleInfo.indexAdImages.imageUrl}}">
            </a>
          {{else if baseArticleInfo.indexAdImages.linkType == 'classify'}}
            <a href="/purchase/list?classifyId={{baseArticleInfo.indexAdImages.lnkUrl}}">
              <img src="{{baseArticleInfo.indexAdImages.imageUrl}}">
            </a>
          {{else}}
            <a href="javascript:void(0);">
              <img src="{{baseArticleInfo.indexAdImages.imageUrl}}">
            </a>
          {{/if}}
        </div>
      {{/if}}

    </div>
  {{/each}}
</script>
</body>
</html>