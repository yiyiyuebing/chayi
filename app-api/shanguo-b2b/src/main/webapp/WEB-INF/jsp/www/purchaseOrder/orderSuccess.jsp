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
    <title>订单支付成功-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>

  <link rel="stylesheet" href="/static/css/order_success.css">
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
        <h5>订单支付成功！</h5>
        <div class="order-content-group order-number clearfix">
          <span class="pull-left">订单号:${purchaseOrder.orderNo}</span>
          <i class="order-vertical-line pull-left"></i>
          <span class="pull-left">付款金额（元）：<mark>${orderListPayment.payAmount}元</mark></span>
        </div>
        <div class="order-content-group go-back-index">
          <a class="color-yellow" href="/">返回首页</a>
          <a class="color-blue" href="/order/detail/${purchaseOrder.id}/${purchaseOrder.orderType}">查看订单详情</a>
        </div>
      </div>
    </div>
  </div>
  <!--主体  ending-->

  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>

</body>
</html>

