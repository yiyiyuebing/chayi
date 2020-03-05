<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/13
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>我的购物车-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/person.css">
  <link rel="stylesheet" href="/static/css/person.list.css">
  <link rel="stylesheet" type="text/css" href="/static/css/cart.css">
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
<div class="cart-cont">
  <ul class="cart-nav clearfix">
    <li class="active"><a>全部商品<span class="red num" id="all"></span></a></li>
  <%--  <li><a>降价商品<span class="red num"></span></a></li>--%>
  </ul>
  <div>
    <table class="data-table">
      <thead>
      <tr>
        <td class="middle-td">
          <input type="checkbox" name="check-all" class="checkall">
          <lalel>全选</lalel>
        </td>
        <td class="long-td">商品</td>
        <td class="middle-td">单价</td>
        <td class="middle-td">数量</td>
        <td class="middle-td">总额</td>
        <td class="middle-td">操作</td>
      </tr>
      </thead>
      <tbody class="list-cart">
      <%--购物车商品明细列表--%>
      <%--售罄商品--%>

      <%--售罄商品 ending--%>
      </tbody>
    </table>
    <div class="bottom-wrapper clearfix">
      <div class="cart-checkbox">
        <input type="checkbox" name="check-all" class="checkall">
        <lalel>全选</lalel>
        <a class="left-btn del" onclick=" PurchaseCart.electDel1()" >删除选中的商品</a>
        <a class="left-btn collect" onclick=" PurchaseCart.addCollectList()">加入到我的收藏</a>
      </div>
      <div class="right-box">
        <div>
          <span class="choose-box">已选择<span class="red" id="count">0</span>件商品</span>
          <div class="total-box">
            <p><span>总价：<span class="red total-price">¥<span class="total" id="cost">0</span></span></span></p>
            <p><span>优惠：¥0</span></p>
          </div>
        </div>
        <!--去掉disable就是正常可点击状态-->
        <a href="javascript:void(0);"  id="goCount" onclick="PurchaseCart.goCreatePreOrder(this)" class="count-btn disable">去结算</a>
      </div>
    </div>
  </div>
</div>
<!-- END主内容 -->

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>

</body>
</html>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/cart/purchase_cart.js"></script>
<script id="tpl-list-cart" type="text/html">

  {{if isStockEnough == 'T'}}
    {{if isValid == 'T'}}
    <tr>
    {{else}}
    <tr class="off-sale-wrap">
    {{/if}}
      <td class="td-checkbox">
        <div class="clearfix" style="position: relative">
          {{if good.status == '1'}}
            {{if isValid == 'T'}}
              <input class="pull-left" name="cart-good-checkbox" id="{{goodsId}}" type="checkbox">
            {{else}}
              <i class="off-sale-icon pull-left">失效</i>
            {{/if}}
          {{/if}}
          <div class="img-cover">
            {{if good.image}}
            <img src="{{good.image.url}}">
            {{else}}
            <img src="">
            {{/if}}
          </div>
          {{if queNum}}
            <div class="queNum" data-tip-info="{{good.name}}商品库存不足，请重新选择数量">
              仅剩<span>{{queNum}}</span>份
            </div>
          {{/if}}
        </div>
      </td>
      <td>
        <p class="good-name">{{good.name}}</p>
        {{if good.label}}
        <p>
          <span class="dai-icon">{{good.label}}</span>
        </p>
        {{/if}}
      </td>
      <td class="per-price">
        <p>¥<span class="price-original">{{good.originalPrice.toFixed(2)}}</span></p>
        <p>¥<span class="price-now">{{good.price.toFixed(2)}}</span></p>
        <div class="promo-box hide">
          {{if good.promotionalInfo && good.promotionalInfo.bestActivity.activityType == 'manjian'}}
          <a class="promo-text">卖家促销</a>
            {{each good.promotionalInfo.activityList as activity}}
              {{if activity.activityType == 'manjian' && activity.discountAmount}}
                <div class="promo-info">
                  <p>卖家促销：{{activity.tag2}}</p>
                  <p>优惠：¥{{activity.discountAmount.toFixed(2)}}</p>
                </div>
              {{/if}}
            {{/each}}
          {{/if}}
        </div>
      </td>
      <td class="per-count">
        {{if isValid != 'T'}}
        <%--商品售罄  按钮模块显示 --%>
        <div class="item-amount clearfix">
          <div class="off-sale-count">{{goodsCount}}</div>
        </div>
        {{else}}
          {{if good.status == '1'}}
          <div class="item-amount clearfix">
            <a class="amount-btn less"  data-id="{{id}}" data-sku-id="{{goodsId}}" data-max-sale-num="{{good.onSalesNo}}">-</a>
            <input class="price-now" type="hidden" value="{{good.price}}">
            <input  data-id="{{id}}"  data-mulNumFlag="{{good.mulNumFlag}}" readonly data-startNum="{{good.startNum}}"
                    class="text-amount" name="number" type="text" value="{{goodsCount}}"
                    data-good-name="{{good.name}}"
                    data-sku-id="{{goodsId}}" data-goods-id="{{good.id}}"
                    data-is-sample="{{isSample}}">
            <a class="amount-btn plus" data-id="{{id}}" data-sku-id="{{goodsId}}" data-max-sale-num="{{good.onSalesNo}}">+</a>
          </div>
          {{else}}
          <div class="item-amount clearfix">
            <a class="amount-btn less"  data-id="{{id}}" data-mulNumFlag="{{good.mulNumFlag}}" data-startNum="{{good.startNum}}" style="pointer-events: none;">-</a>
            <input class="price-now" type="hidden" value="{{good.price}}">
            <input  data-id="{{id}}"  data-mulNumFlag="{{good.mulNumFlag}}" readonly data-startNum="{{good.startNum}}" class="text-amount" name="number" type="text" value="{{goodsCount}}" data-sku-id="{{goodsId}}" data-goods-id="{{good.id}}" data-is-sample="{{isSample}}"
                    onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'0')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'0')}">
            <a class="amount-btn plus" data-id="{{id}}" data-mulNumFlag="{{good.mulNumFlag}}" data-startNum="{{good.startNum}}" data-max-sale-num="{{good.onSalesNo}}" style="pointer-events: none;">+</a>
          </div>
          {{/if}}
        {{/if}}
      </td>
      <td class="select">
        <p class="price-now">¥<span class="price">{{totalPrice}}</span></p>
      </td>
      <td>
        <p><a class="del-cart" data-id="{{id}}" >删除</a></p>
        <p><a class="collect-cart"  data-id="{{good.id}}">加入收藏</a></p>
      </td>
    </tr>
  {{else}}
    <tr class="off-sale-wrap">
      <td class="td-checkbox">
        <div class="clearfix">
          {{if isValid == 'F'}}
           <i class="off-sale-icon pull-left">{{message}}</i>
          {{/if}}
          <div class="img-cover">
            {{if good.image}}
              <img src="{{good.image.url}}">
            {{else}}
              <img src="">
            {{/if}}
          </div>
        </div>
      </td>
      <td>
        <p class="good-name">{{good.name}}</p>
      </td>
      <td class="per-price">
        <p>¥<span class="price-original">{{good.price}}</span></p>
        <p>¥<span class="price-now">{{good.price}}</span></p>
      </td>
      <td>
        <%--商品售罄  按钮模块显示 --%>
        <div class="item-amount clearfix">
          <div class="off-sale-count">{{goodsCount}}</div>
        </div>
      </td>
      <td class="select">
        <p class="price-now">¥<span class="price">{{totalPrice}}</span></p>
      </td>
      <td>
        <p><a class="del-cart" data-id="{{id}}">删除</a></p>
        <p><a class="collect-cart" data-id="{{id}}">加入收藏</a></p>
      </td>
    </tr>
  {{/if}}


</script>
