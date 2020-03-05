
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的收藏-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/css/person.css">
  <link rel="stylesheet" href="/static/css/person_center_nav_position.css">
  <link rel="stylesheet" href="/static/js/lib/kkpager/css/kkpager.css">
  <link rel="stylesheet" type="text/css" href="/static/css/collect.css">
</head>
<body>

<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/personal_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>

<!-- 主内容 -->
<div class="person-wrap">
  <div class="person-cont">
    <div class="content">
      <%--右侧导航栏 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
      <%--右侧导航栏 ending--%>

      <!-- 个人中心主内容 -->
      <div class="right-cont-wrap">
        <div>
          <ul id="top-nav" class="nav-menu clearfix">
            <li class="active"><a>收藏的商品</a></li>
            <%--<li><a>收藏的店铺</a></li>--%>
          </ul>
          <div class="top-right-box clearfix">
            <!-- 添加check出现批量操作面板 -->
            <div id="title-batch-box" class="option-wrap pull-left">
              <div class="nomal-box">
                <a id="batch-btn" class="batch-btn">批量处理</a>
              </div>
              <div class="check-box">
                <div class="pull-left">
                  <%--<span class="check-icon check-tagt"></span>--%>
                  <lalel>
                    <input type="checkbox" class="check-all">
                    全选
                  </lalel>
                </div>
                <%--<a class="option-btn cart">加入购物车</a>--%>
                <a class="option-btn collect" onclick="Collect.cancelFavorite()">取消收藏</a>
                <a id="complete-btn" class="batch-btn">完成</a>
              </div>
            </div>
            <div class="page-box pull-right">
              <span class="pull-left"><span id="current-page" class="orange">1</span>/<span id="total-page">1</span></span>
              <a id="page-pre" href="javascript:void(0);" class="page-btn pre disable" data-page-no="0"></a>
              <a id="page-next" href="javascript:void(0);" class="page-btn next disable" data-page-no=""></a>
            </div>
          </div>
        </div>
        <!-- 添加check出现操作样式 -->
        <div id="collect-list-box" class="collect-list-wrap clearfix">
        
        </div>
        <div id="kkpager"></div>
      </div>
    </div>
      <!-- END个人中心主内容 -->
  </div>
</div>
<!-- END主内容 -->
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
</body>
</html>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/lib/kkpager/kkpager.js"></script>
<script src="/static/js/favorite/favorite_list.js"></script>

<script id="tpl-list-item" type="text/html">
  {{if good.stock > 0 && good.status == '1'}}
    <div class="list-item" data-id="{{id}}">
      <div class="item-inner">
        <a class="img-cover" href="/purchase/detail/{{good.id}}.html">
          {{if good.image}}
          <img width="160" height="160" src="{{good.image.url}}">
          {{else}}
          <img width="160" height="160" src="">
          {{/if}}
        </a>
        <a class="p-name" href="/purchase/detail/{{good.id}}.html">{{good.name}}</a>
        <div class="p-price red">¥<strong>{{good.price.toFixed(2)}}</strong></div>
        <div class="p-operate clearfix">
          <div class="op-btn">
            <a class="btn-compare" data-sku-id="{{good.skuId}}" data-id="{{good.id}}" data-sku-price="{{good.price}}"onclick="Collect.addToCartFromFavorite(this)">加入购物车</a>
          </div>
          <div class="op-btn">
            <a class="btn-cut" data-id="{{id}}" data-good-id="{{good.id}}" onclick="Collect.cancelFavorite(this)">取消收藏</a>
          </div>
        </div>
      </div>
      <div class="item-check">
        <i class="i-check"></i>
      </div>
    </div>
  {{else}}
    <div class="list-item" data-id="{{id}}">
      <div class="item-inner">
        <span class="img-cover">
          {{if good.image}}
          <img width="160" height="160" src="{{good.image.url}}">
          {{else}}
          <img width="160" height="160" src="">
          {{/if}}
        </span>
        <a class="p-name" style="pointer-events: none;" href="/purchase/detail/{{good.id}}.html">{{good.name}}</a>
        <div class="p-price red">¥<strong>{{good.price.toFixed(2)}}</strong></div>
        <div class="off-sale">
          商品已售罄
        </div>
        <%--商品已售罄图标--%>
        <i class="off-sale-icon"></i>
      </div>
      <div class="item-check">
        <i class="i-check"></i>
      </div>
    </div>
  {{/if}}


</script>
