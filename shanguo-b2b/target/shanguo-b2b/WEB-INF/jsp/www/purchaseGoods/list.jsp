<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/3
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <c:if test="${empty purchaseClassify.name}">
    <title>茶叶商城,采购商城,茶具商城【茶叶_茶具_茶礼_采购_批发_进货_正品】-优茶联</title>
    <meta name="keyword" content="茶叶商城,采购商城,茶具商城,茶具采购,茶礼"/>
    <meta name="description" content="完善的茶叶商城,采购商城，茶具商城，茶具采购商城，品质保证,优质的铁观音、大红袍、普洱茶、红茶、绿茶、白茶、袋泡茶、礼品茶、茶礼、茶具、茶食品,尽在优茶联."/>
  </c:if>
  <c:if test="${!empty purchaseClassify.name}">
    <title>${purchaseClassify.name}【茶叶_茶具_茶礼_采购_批发_进货_正品】-优茶联</title>
    <meta name="keyword" content="${purchaseClassify.name},茶叶,茶具,茶礼,采购,批发,进货,正品"/>
    <meta name="description" content="完善的茶叶商城,采购商城，茶具商城，茶具采购商城，品质保证,优质的铁观音、大红袍、普洱茶、红茶、绿茶、白茶、袋泡茶、礼品茶、茶礼、茶具、茶食品,尽在优茶联."/>
  </c:if>


  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/product_list.css">
  <link rel="stylesheet" href="/static/js/lib/kkpager/css/kkpager.css">
</head>
<body>

  <script>
    var purchaseGoodsQuery = {
      storeLevelId: "${purchaseGoodsQuery.storeLevelId}",
      keyword: "${purchaseGoodsQuery.keyword}",
      classifyIds: "${purchaseGoodsQuery.classifyIds}",
      classifyId: "${purchaseGoodsQuery.classifyId}",
      brandIds: "${purchaseGoodsQuery.brandIds}",
      <%--classifyAttrIds: "${purchaseGoodsQuery.classifyAttrIds}",--%>
      searchEv: "${purchaseGoodsQuery.searchEv}",
      minPrice: "${purchaseGoodsQuery.minPrice}",
      maxPrice: "${purchaseGoodsQuery.maxPrice}",
      priceSort: "${purchaseGoodsQuery.priceSort}",
      saleNumSort: "${purchaseGoodsQuery.saleNumSort}",
      pageNum: "${purchaseGoodsQuery.pageNum}",
      pageSize: "${purchaseGoodsQuery.pageSize}",
      orderIndex: "${purchaseGoodsQuery.orderIndex}",
      totalRecods: "${purchaseGoodsQuery.totalRecods}",
      totalPageNo: "${purchaseGoodsQuery.totalPageNo}"
    }

  </script>
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

  <div class="product-recommend clearfix">
    <div class="product-hot pull-left">
      <%--热卖推荐--%>
      <%--<i class="hot pull-left">热卖推荐</i>
      <div class="hot-product-group pull-left clearfix">
        <a href="#" class="product-img pull-left">
          <img src="/static/images/product_list_recommend_img.png" class="pull-left">
        </a>
        <div class="pull-right product-txt pull-right">
          <a href="product_details.html" class="product-title">2017新茶云雾绿茶4盒共500克一杯盒.雾绿茶4盒共500克一杯盒.</a>
          <span><sub>¥</sub>200.00</span>
          <a href="#" class="buy">立即购买</a>
        </div>
      </div>
      <div class="hot-product-group pull-left clearfix">
        <a href="product_details.html" class="product-img pull-left">
          <img src="/static/images/product_list_recommend_img.png" class="pull-left">
        </a>
        <div class="pull-right product-txt pull-right">
          <a href="product_details.html" class="product-title">2017新茶云雾绿茶4盒共500克一杯盒.雾绿茶4盒共500克一杯盒.</a>
          <span><sub>¥</sub>200.00</span>
          <a href="product_details.html" class="buy">立即购买</a>
        </div>
      </div>
      <div class="hot-product-group pull-left clearfix">
        <a href="product_details.html" class="product-img pull-left">
          <img src="/static/images/product_list_recommend_img.png" class="pull-left">
        </a>
        <div class="pull-right product-txt pull-right">
          <a href="product_details.html" class="product-title">2017新茶云雾绿茶4盒共500克一杯盒.雾绿茶4盒共500克一杯盒.</a>
          <span><sub>¥</sub>200.00</span>
          <a href="product_details.html" class="buy">立即购买</a>
        </div>
      </div>--%>
    </div>
    <div class="product-promote pull-right clearfix">
      <i class="promote pull-left">促销活动</i>
      <ul class="promote-list pull-left">
      </ul>
    </div>
  </div>


  <div class="classic" id="searchForm">
    <div class="classic-header clearfix">
      <span class="pull-left color-333">全部&nbsp;> </span>
      <c:if test="${!empty purchaseClassify.name}">
        <span class="pull-left">${purchaseClassify.name}&nbsp;> </span>
      </c:if>

      <%--<a class="pull-left select-condition" href="#">季节：春季 <i></i></a>--%>
      <%--<a class="pull-left select-condition" href="#">品牌：山国饮艺 <i></i></a>--%>
      <a class="pull-left clear-condition" style="display: none;" href="javascript:void(0);" onclick="ProductList.clearSearchParam()">清空筛选</a>
    </div>
    <div class="classic-body">
      <div class="classic-body-group clearfix">
        <div class="classic-title pull-left">分类：</div>
        <div class="classic-content-wrap pull-left">
          <div class="classic-content-select pull-left">
            <!--单选-->
            <ul class="classic-select-radio classify-div clearfix">
              <c:forEach var="classifyInfo" items="${classifyVoList}" varStatus="status">
                <li class="pull-left">
                  <a href="javascript:void(0);" onclick="ProductList.singleSelectSearchParam(this)" data-type-name="分类" data-name="classifyIds" data-id="${classifyInfo.id}">${classifyInfo.name}</a>
                </li>
              </c:forEach>
            </ul>
            <!--多选-->
            <div class="classic-select-checkbox-wrap" style="display: none">
              <form>
                <ul class="classic-select-checkbox clearfix">
                  <c:forEach var="classifyInfo" items="${classifyVoList}" varStatus="status">
                    <li class="pull-left">
                      <label>
                        <i><input type="checkbox" name="classic" value="${classifyInfo.id}"></i>${classifyInfo.name}
                      </label>
                    </li>
                  </c:forEach>
                </ul>
                <div class="classic-select-checkbox-btn">
                  <input type="button" value="确定" data-type="classsify" onclick="ProductList.multiSelectSearchParam(this)" disabled>
                  <span>取消</span>
                </div>
              </form>
            </div>
          </div>
          <div class="classic-btn-group pull-right clearfix">
            <span class="more pull-left"><i></i>更多</span>
            <span class="plus pull-left"><i>+</i>多选</span>
          </div>
        </div>
      </div>

      <%--品牌--%>


      <c:if test="${!empty brandVoList}">
        <div class="classic-body-group clearfix">
          <div class="classic-title pull-left">品牌：</div>
          <div class="classic-content-wrap pull-left clearfix">
            <div class="classic-content-select brand-div pull-left" id="brandCategory">
              <!--单选-->
              <ul class="classic-select-radio clearfix">

                <c:forEach var="brandInfo" items="${brandVoList}" varStatus="status">
                  <li class="pull-left">
                    <div class="link-img">
                      <a href="javascript:void(0);" onclick="ProductList.singleSelectSearchParam(this)" data-type-name="品牌" data-name="brandIds" data-id="${brandInfo.id}">
                        <img src="${brandInfo.pcLogo}">
                        <span>${brandInfo.name}</span>
                      </a>
                    </div>
                  </li>
                </c:forEach>

              </ul>
              <!--多选-->
              <div class="classic-select-checkbox-wrap" style="display: none">
                <ul class="classic-select-checkbox-imgBox clearfix">

                  <c:forEach var="brandInfo" items="${brandVoList}" varStatus="status">
                    <li class="pull-left">
                      <div class="mask-img" data-name="${brandInfo.name}" data-input-value="${brandInfo.id}"  data-category="brand">
                        <div class="img-box">
                          <img src="${brandInfo.pcLogo}">
                          <span>${brandInfo.name}</span>
                        </div>
                        <i class="icon-checked"></i>
                      </div>
                    </li>
                  </c:forEach>
                </ul>
                <form>
                  <ul class="classic-select-checkbox clearfix"></ul>
                  <div class="classic-select-checkbox-btn">
                    <input type="button" value="确定" data-type="brand" onclick="ProductList.multiSelectSearchParam(this)" disabled>
                    <span>取消</span>
                  </div>
                </form>
              </div>
            </div>
            <div class="classic-btn-group pull-right clearfix">
              <span class="more pull-left"><i></i>更多</span>
              <span class="plus pull-left"><i>+</i>多选</span>
            </div>
          </div>
        </div>
      </c:if>



      <c:if test="${!empty attrVoList && empty attrVoList}">
        <c:forEach var="attrInfo" items="${attrVoList}" varStatus="status">
          <div class="classic-body-group attr-info-${status.index} clearfix">
            <div class="classic-title pull-left">${attrInfo.name}：</div>
            <div class="classic-content-wrap pull-left">
              <div class="classic-content-select attr-div pull-left">
                <!--单选-->
                <ul class="classic-select-radio clearfix">
                  <c:forEach var="attrChildInfo" items="${attrInfo.children}" varStatus="index">
                    <li class="pull-left">
                      <a href="javascript:void(0);" onclick="ProductList.singleSelectSearchParam(this)" data-type-name="${attrInfo.name}" data-type-id="${attrInfo.id}" data-type="attr" data-name="classifyAttrIds" data-id="${attrChildInfo.id}">${attrChildInfo.name}</a>
                    </li>
                  </c:forEach>
                </ul>
                <!--多选-->
                <div class="classic-select-checkbox-wrap" style="display: none">
                  <form>
                    <ul class="classic-select-checkbox clearfix">
                      <c:forEach var="attrChildInfo" items="${attrInfo.children}" varStatus="index">
                        <li class="pull-left">
                          <label>
                            <i><input type="checkbox" name="attr" value="${attrChildInfo.id}"></i>${attrChildInfo.name}
                          </label>
                        </li>
                      </c:forEach>
                    </ul>
                    <div class="classic-select-checkbox-btn">
                      <input type="button" value="确定" data-type-id="${attrInfo.id}" data-type="attr-info-${status.index}" onclick="ProductList.multiSelectSearchParam(this)" disabled>
                      <span>取消</span>
                    </div>
                  </form>
                </div>
              </div>
              <div class="classic-btn-group pull-right clearfix">
                <span class="more pull-left"><i></i>更多</span>
                <span class="plus pull-left"><i>+</i>多选</span>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:if>



      <div class="classic-body-group clearfix">
        <div class="classic-title pull-left">价格区间：</div>
        <div class="classic-content-wrap pull-left">
          <div class="classic-content-select pull-left">
            <!--单选-->
            <ul class="classic-select-radio clearfix">
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="0" data-max-price="500" onclick="ProductList.singleSelectSearchParam(this)">0-500</a>
              </li>
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="500" data-max-price="1000" onclick="ProductList.singleSelectSearchParam(this)">500-1000</a>
              </li>
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="1000" data-max-price="1500" onclick="ProductList.singleSelectSearchParam(this)">1000-1500</a>
              </li>
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="1500" data-max-price="2000" onclick="ProductList.singleSelectSearchParam(this)">1500-2000</a>
              </li>
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="2000" data-max-price="5000" onclick="ProductList.singleSelectSearchParam(this)">2000-5000</a>
              </li>
              <li class="pull-left">
                <a href="javascript:void(0);" data-type="price" data-min-price="5000" onclick="ProductList.singleSelectSearchParam(this)">5000+</a>
              </li>
              <li class="pull-left price-input-form">
                <form>
                  <input class="price-input" style="height: 22px;" type="text" name="minPrice">
                  <i></i>
                  <input class="price-input" style="height: 22px;" type="text" name="maxPrice">
                  <input class="price-btn" type="button" value="确定">
                </form>
              </li>
            </ul>
            <!--多选-->

          </div>
          <%--<div class="classic-btn-group pull-right clearfix">
            <span class="more pull-left"><i></i>更多</span>
            <span class="plus pull-left"><i>+</i>多选</span>
          </div>--%>
        </div>
      </div>
    </div>
    <div class="classic-footer">
      <div class="classic-footer-btn footer-more">
        更多选项（规格/包装，要求类型等）<i></i>
      </div>
      <div class="classic-footer-btn footer-less">
        收起<i></i>
      </div>
    </div>
  </div>


  <div class="product-wrap clearfix">

    <div class="product-aside pull-left">

    </div>

    <%--列表 starting--%>
    <div class="product-list pull-right" id="J_main">
      <div class="product-list-header clearfix">
        <div class="product-list-order pull-left clearfix">
          <a class="pull-left" data-order-type="orderIndex" data-order="" href="javascript:void(0)">默认<i class=""></i></a>
          <a class="pull-left" data-order-type="saleNumSort" data-order="" href="javascript:void(0)">销量<i></i></a>
          <a class="pull-left" data-order-type="createTimeSort" data-order="" href="javascript:void(0)">新品<i></i></a>
          <a class="pull-left" data-order-type="priceSort" data-order="" href="javascript:void(0)">价格<i></i></a>
        </div>
        <div class="product-list-search pull-left">
          <div class="price-range-form" style="display: none;">
            <input class="price-input" type="text" name="minPrice" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
            <i></i>
            <input class="price-input" type="text" name="maxPrice" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
            <input class="price-btn" type="button" value="确定">
          </div>
        </div>
        <div class="product-header-paging pull-right clearfix" style="<c:if test='${empty purchaseGoodsQuery.totalPageNo}'>display: none</c:if>">
          <span class="pull-left"><i class="this-page-num">${purchaseGoodsQuery.pageNum}</i>/${purchaseGoodsQuery.totalPageNo}</span>
          <a href="javascript:void(0);" class="prev pull-left"> </a>
          <a href="javascript:void(0);" class="next pull-left"> </a>
        </div>
      </div>
      <div class="product-list-body">
        <ul class="product-list-content clearfix">
          <c:if test="${!empty purchaseGoodsVos}">
            <c:forEach var="purchaseGoods" items="${purchaseGoodsVos}" varStatus="status">
              <li class="pull-left" style="position: relative;">
                <c:if test="${purchaseGoods.stock <= 0}">
                  <%--商品售罄图标--%>
                  <i class="off-sale-icon-big"></i>
                  <%--商品售罄图标ending--%>
                </c:if>
                <a href="/purchase/detail/${purchaseGoods.id}.html" class="link-img">
                  <c:if test="${!empty purchaseGoods.image}">
                    <img src="${purchaseGoods.image.url}">
                  </c:if>
                  <c:if test="${empty purchaseGoods.image}">
                    <img src="/static/images/product_list_aside_img.png">
                  </c:if>
                </a>
                <c:if test="${!empty purchaseGoods.price}">
                  <span class="price price-sku-${purchaseGoods.skuId}" data-goods-sku-id="${purchaseGoods.skuId}"><i>供货价：</i>￥
                    <c:if test="${!empty shopId}">
                      <c:if test="${!empty purchaseGoods.promotionalInfo}">
                        ${purchaseGoods.promotionalInfo.price}
                      </c:if>
                      <c:if test="${empty purchaseGoods.promotionalInfo}">
                        ${purchaseGoods.price}
                      </c:if>
                    </c:if>
                    <c:if test="${empty shopId}">
                      <span class="consult-price"><sub>￥</sub>询价</span>
                    </c:if>
                  </span>
                </c:if>
                <c:if test="${empty purchaseGoods.price}">
                  <span class="price price-sku-${purchaseGoods.skuId}" data-goods-sku-id="${purchaseGoods.skuId}"><i>供货价：</i>￥
                  <c:if test="${!empty shopId}">
                    0.00
                  </c:if>
                    <c:if test="${empty shopId}">
                      <span class="consult-price"><sub>￥</sub>询价</span>
                    </c:if>
                  </span>
                </c:if>

                <a href="/purchase/detail/${purchaseGoods.id}.html" class="link-txt">
                  <c:if test="${!empty purchaseGoods.promotionalInfo
                && !empty purchaseGoods.promotionalInfo.bestActivity
                && purchaseGoods.promotionalInfo.bestActivity.activityType != 'zengpin'}">
                    <div class="promotion-category">
                      <img src="${purchaseGoods.promotionalInfo.bestActivity.tag1}">
                    </div>
                  </c:if>

                  <c:if test="${!empty purchaseGoods.promotionalInfo
                  && !empty purchaseGoods.promotionalInfo.bestActivity
                  && purchaseGoods.promotionalInfo.bestActivity.activityType != 'zengpin'}">
                    <i class="label-sku-${purchaseGoods.skuId}">${purchaseGoods.promotionalInfo.bestActivity.tag2}</i>
                  </c:if>
                  ${purchaseGoods.name}</a>
                <span class="number">销量：
                  <%--<mark>2756/1.2万/12万+</mark>--%>
                  <mark>
                    <c:if test="${purchaseGoods.saleNum >= 10000 && purchaseGoods.saleNum < 100000}">
                      <fmt:formatNumber type="number" value="${purchaseGoods.saleNum / 10000}" maxFractionDigits="1"/>万+
                    </c:if>
                    <c:if test="${purchaseGoods.saleNum >= 100000}">
                      <fmt:formatNumber type="number" value="${purchaseGoods.saleNum / 10000}" maxFractionDigits="0"/>万+
                    </c:if>
                    <c:if test="${purchaseGoods.saleNum < 10000}">
                      ${purchaseGoods.saleNum}
                    </c:if>
                  </mark>
                  笔</span>
                <c:if test="${!empty purchaseGoods.label}">
                  <div class="promote-route clearfix">
                    <i class="pull-left">${purchaseGoods.label}</i>
                  </div>
                </c:if>
                <div class="product-operation clearfix">
                  <a href="javascript:void(0);" data-id="${purchaseGoods.id}" data-sku-id="${purchaseGoods.skuId}"
                     onclick="ProductList.addToFavorite(this)" data-flag="0" class="collect pull-left collect-${purchaseGoods.id}"><i></i>收藏</a>
                  <c:if test="${empty purchaseGoods.promotionalInfo}">
                    <c:if test="${purchaseGoods.stock > 0}">
                      <a href="javascript:void(0);" data-id="${purchaseGoods.id}" data-sku-id="${purchaseGoods.skuId}"
                         data-sku-price="${purchaseGoods.price}" class="package pull-left package-sku-${purchaseGoods.skuId}"
                         onclick="ProductList.addGoodToCart(this)"><i></i>加入购物车</a>
                    </c:if>
                    <c:if test="${purchaseGoods.stock <= 0}">
                      <%--灰色的加入购物车--%>
                      <a class="off-sale-cart pull-left hide" href="javascript:void(0);"><i></i>加入购物车</a>
                    </c:if>

                  </c:if>
                </div>
                <c:if test="${!empty purchaseGoods.promotionalInfo
                && !empty purchaseGoods.promotionalInfo.bestActivity
                && purchaseGoods.promotionalInfo.bestActivity.activityType != 'zengpin'}">
                  <div class="promotion-category">
                    <img src="${purchaseGoods.promotionalInfo.bestActivity.tag1}">
                  </div>
                </c:if>

              </li>
            </c:forEach>
          </c:if>
        </ul>
        <div id="kkpager" style="<c:if test='${empty purchaseGoodsVos}'>display: none</c:if>"></div>
        </div>
      </div>
    </div>
    <%--列表 ending--%>
  </div>

  <div class="bottom-product-list">

  </div>




  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>

</body>
</html>
<script src="/static/js/lib/kkpager/kkpager.js"></script>
<script src="/static/js/purchaseGoods/purchaseGoods_list.js"></script>

<script id="product-list-pull-left" type="text/html">
  <li class="pull-left" style="position: relative;">
    {{if stock <= 0}}
      <%--商品售罄图标--%>
      <i class="off-sale-icon-big"></i>
      <%--商品售罄图标ending--%>
    {{/if}}
    <a href="/purchase/detail/{{id}}.html" class="link-img">
      {{if image && image.url}}
        <img src="{{image.url}}">
      {{else}}
        <img src="/static/images/product_list_aside_img.png">
      {{/if}}
    </a>
    {{if price}}
      <span class="price price-sku-{{skuId}}"><i>供货价：</i>￥
        {{if shopId}}
          {{if promotionalInfo && promotionalInfo.price}}
            {{promotionalInfo.price}}
          {{else}}
            {{price}}
          {{/if}}
        {{else}}
          询价
        {{/if}}
      </span>
    {{else}}
      <span class="price price-sku-{{skuId}}"><i>供货价：</i>￥
        {{if shopId}}
          0.00
        {{else}}
          询价
        {{/if}}
      </span>
    {{/if}}
      <a href="/purchase/detail/{{id}}.html" class="link-txt">
        {{if promotionalInfo && promotionalInfo.bestActivity && promotionalInfo.bestActivity.activityType != 'zengpin' && promotionalInfo.bestActivity.tag2}}
          <i class="label-sku-{{skuId}}">{{promotionalInfo.bestActivity.tag2}}</i>
        {{/if}}
        {{name}}</a>
    <span class="number">销量：
      <mark>
          {{saleNum}}
      </mark>笔
    </span>
    {{if label}}
      <div class="promote-route clearfix">
          <i class="pull-left">{{label}}</i>
      </div>
    {{/if}}
      <div class="product-operation clearfix">
        <a href="javascript:void(0);" class="collect pull-left collect-{{id}}"
           data-id="{{id}}" data-flag="0" data-sku-price="{{price}}" onclick="ProductList.addToFavorite(this)"><i></i>收藏</a>
        {{if !promotionalInfo}}
          {{if stock <= 0}}
            <%--灰色的加入购物车--%>
            <a class="off-sale-cart pull-left hide" href="javascript:void(0);"><i></i>加入购物车</a>
          {{else}}
            <a href="javascript:void(0);" data-id="{{id}}" data-sku-id="{{skuId}}"
               data-sku-price="{{price}}" onclick="ProductList.addGoodToCart(this)"
               class="package pull-left package-sku-{{skuId}}"><i></i>加入购物车</a>
          {{/if}}
        {{/if}}
      </div>
    {{if promotionalInfo && promotionalInfo.bestActivity && promotionalInfo.bestActivity.activityType != 'zengpin'}}
      <div class="promotion-category">
        <img src="{{promotionalInfo.bestActivity.tag1}}">
      </div>
    {{/if}}
  </li>
</script>
<script id="tpl-promote-list-item" type="text/html">
  {{each baseArticleInfos as baseArticleInfo}}
  {{if baseArticleInfo.type== 'goods'}}
    <li><a href="/purchase/detail/{{baseArticleInfo.id}}.html" target="_blank">【{{title}}】{{baseArticleInfo.title}}</a></li>
  {{/if}}
  {{if baseArticleInfo.type== 'gonggaotw'}}
  <li><a href="/news/gonggaoDetail/cuxiao/{{baseArticleInfo.id}}.html" target="_blank">【{{title}}】{{baseArticleInfo.title}}</a></li>
  {{/if}}
  {{if baseArticleInfo.type== 'article'}}
  <li><a href="/news/detail/cuxiao/{{baseArticleInfo.id}}.html" target="_blank">【{{title}}】{{baseArticleInfo.title}}</a></li>
  {{/if}}
  {{/each}}
</script>

<script id="tpl-img-ad" type="text/html">
  <a href="{{adUrl}}" class="aside-link">
    <img src="{{adImgUrl}}">
  </a>
</script>

<%--左侧模版--%>
<script id="tpl-left-tpl" type="text/html">
  <div class="product-aside-wrap">
    <h4>{{modelName}}</h4>
    <ul>
      {{each goodList as good index}}
      <li style="position: relative;">

        {{if good.stock <= 0}}
        <%--商品售罄图标--%>
        <i class="off-sale-icon-big"></i>
        <%--商品售罄图标ending--%>
        {{/if}}

        <a href="/purchase/detail/{{good.id}}.html" class="img-link">
          {{if good.image && good.image.url}}
          <img src="{{good.image.url}}">
          {{else}}
          <img src="/static/images/product_list_aside_img.png">
          {{/if}}
        </a>
        <a class="title-link" href="/purchase/detail/{{good.id}}.html">{{good.name}}</a>
        <span class="price">供货价：<sub>¥</sub>
          {{if shopId}}
            {{good.price}}
          {{else}}
            <span class="consult-price"><sub>￥</sub>询价</span>
          {{/if}}
        </span>
        <span class="number">
          {{if good.saleNum}}
              销量：{{good.saleNum}}笔
          {{else}}
            销量：0笔
          {{/if}}
        </span>
      </li>
      {{/each}}
    </ul>
  </div>
</script>

<%--底部模版--%>
<script id="tpl-bottom-tpl-dsp" type="text/html">
  <div class="product-may-like">
    <div class="product-may-like-header clearfix">
      <span class="may-like-left pull-left">{{modelName}}</span>
      <%--<a class="may-like-right pull-right"><i></i>换一批</a>--%>
    </div>
    <ul class="may-like-product clearfix">
      {{each goodList as good index}}
      {{if index < 5}}
      <li class="pull-left" style="position: relative;">
        {{if good.stock <= 0}}
        <%--商品售罄图标--%>
        <i class="off-sale-icon-big"></i>
        <%--商品售罄图标ending--%>
        {{/if}}
        <div class="product-wrap">
          <a href="/purchase/detail/{{good.id}}.html" class="may-like-img">
            {{if good.image && good.image.url}}
            <img src="{{good.image.url}}">
            {{else}}
            <img src="/static/images/product_list_aside_img.png">
            {{/if}}
          </a>
          <a href="/purchase/detail/{{good.id}}.html" class="may-like-txt">
            {{good.name}}
          </a>
          <span class="price"><sub>¥</sub>
            {{if shopId}}
              {{good.price}}
            {{else}}
              询价
            {{/if}}
          </span>
          <span class="sails-number">
            {{if good.saleNum}}
              销量：{{good.saleNum}}笔
            {{else}}
              销量：0笔
            {{/if}}
          </span>
        </div>
      </li>
      {{/if}}
      {{/each}}
    </ul>
  </div>
</script>

<script id="tpl-top-page-tpl" type="text/html">
  <i class="hot pull-left">{{modelName}}</i>
  {{each goodList as good index}}
    {{if index < 3}}
      <div class="hot-product-group pull-left clearfix" style="position: relative;">
        {{if good.stock <= 0}}
        <%--商品售罄图标--%>
        <i class="off-sale-icon-big"></i>
        <%--商品售罄图标ending--%>
        {{/if}}
        <a href="/purchase/detail/{{good.id}}.html" class="product-img pull-left">
          <img src="{{good.image.url}}" class="pull-left">
        </a>
        <div class="pull-right product-txt pull-right">
          <a href="/purchase/detail/{{good.id}}.html" class="product-title">{{good.name}}</a>
          <span><sub>¥</sub>
            {{if shopId}}
              {{good.price}}
            {{else}}
              询价
            {{/if}}
          </span>
          <a href="/purchase/detail/{{good.id}}.html" class="buy">立即购买</a>
        </div>
      </div>
    {{/if}}
  {{/each}}
</script>