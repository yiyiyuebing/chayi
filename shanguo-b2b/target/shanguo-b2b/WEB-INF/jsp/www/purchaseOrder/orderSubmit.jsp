<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/12
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单明细-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>

  <link rel="stylesheet" href="/static/css/order_submit.css">
</head>
<body>
  <%--头部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
  <%--头部 ending--%>

  <!--主体-->
  <div class="order-header clearfix">
    <div class="order-header-logo pull-left">
      <a href="/">
        <img src="/static/images/logo.png">
      </a>
    </div>
    <div class="order-header-step pull-right clearfix">
      <div class="order-step-group bg-yellow pull-left">
        <div class="order-step-number">
          <i class="bg-yellow">1</i>
          <span class="color-yellow">拍下商品</span>
        </div>
      </div>
      <div class="order-step-group bg-yellow pull-left">
        <div class="interface interface-left bg-yellow"></div>
        <div class="interface interface-right bg-yellow"></div>
        <div class="order-step-number">
          <i class="bg-yellow">2</i>
          <span class="color-yellow">填写核对订单信息</span>
        </div>
      </div>
      <div class="order-step-group order-step-active bg-coffee pull-left">
        <div class="order-step-number">
          <i class="bg-coffee">3</i>
          <span class="color-coffee">成功提交订单</span>
        </div>
      </div>
    </div>
  </div>
  <div class="order-body">
    <div class="order-content clearfix">
      <div class="order-content-img pull-left">
        <img src="/static/images/order_submit_green_right.png">
      </div>
      <div class="order-content-txt pull-left">
        <h5>订单提交成功，请您尽快付款！</h5>
        <div class="order-content-group order-number clearfix">
          <input type="hidden" name="orderId" value="${purchaseOrder.id}"/>
          <span class="pull-left">订单号:${purchaseOrder.orderNo}</span>
          <i class="order-vertical-line pull-left"></i>
          <span class="pull-left">付款金额（元）：<mark>${unpayAmount}元</mark></span>
        </div>
        <div class="order-content-group order-deadline">
          <span>请您在<mark><fmt:formatDate value="${purchaseOrder.timeout}" pattern="yyyy-MM-dd HH:mm:ss"/></mark>完成交易，否则订单将自动取消！</span>
        </div>
      </div>
    </div>
    <c:forEach items="${purchaseOrder.orderListVos}" var="orderListInfo">
      <c:if test="${orderListInfo.giftFlag != 'T'}">
        <div class="order-details">
          <div class="order-details-group clearfix">
            <div class="order-product-imgTxt pull-left clearfix">
              <div class="order-product-img pull-left">
                <img src="${orderListInfo.purGoodsImgUrl}">
              </div>
              <div class="order-product-txt pull-left">
                  ${orderListInfo.purGoodsName} ${orderListInfo.purGoodsType}
              </div>
            </div>
            <div class="order-product-express pull-left">
              <div class="order-product-express-group clearfix">
                <span class="pull-left">¥<fmt:formatNumber type="number" value="${orderListInfo.finalAmount/orderListInfo.number}" maxFractionDigits="2"/></span>
                <span class="pull-right">x${orderListInfo.number}</span>
              </div>
                <%--<div class="clearfix">
                  <label class="pull-left">运送方式：</label>
                  <span class="pull-left">普通配送 快递 免邮</span>
                </div>--%>
            </div>
            <div class="order-product-price pull-right">
              费用合计 <mark>¥${orderListInfo.finalAmount}</mark>
            </div>
          </div>
        </div>
      </c:if>
    </c:forEach>


  </div>
  <div class="order-spread">
    <a href="javascript:void(0)" class="order-down-btn"><i></i>查看详情</a>
    <a href="javascript:void(0)" class="order-up-btn"><i></i>收起详情</a>
  </div>
  <form>
    <div class="order-pay">
      <h5>支付方式</h5>
      <div class="order-pay-wrap clearfix">
        <label class="order-pay-group pull-left">
          <input type="radio" name="orderPayRoute" value="wxpay">
          <div class="order-pay-mask clearfix">
            <i class="pull-left"></i>
            <img class="pull-right" src="/static/images/icon_weichart_40-40.png">
          </div>
        </label>
        <label class="order-pay-group pull-left hide">
          <input type="radio" name="orderPayRoute" value="alipay">
          <div class="order-pay-mask clearfix">
            <i class="pull-left"></i>
            <img class="pull-left" src="/static/images/icon_alipay_113-40.png">
          </div>
        </label>
      </div>
    </div>
    <div class="order-submit clearfix">
      <a href="javascript:void(0);" class="pull-right to-pay-order">立即支付</a>
    </div>
  </form>
  <!--主体  ending-->

  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>

</body>
</html>
<script src="/static/js/purchaseOrder/order_submit.js"></script>
