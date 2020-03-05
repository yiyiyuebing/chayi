<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/17
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请支付订单-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/order_weichart_pay.css">
</head>
<body>

<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<!--主体-->
<div class="order-header clearfix">
  <div class="order-header-logo">
    <a href="/">
      <img src="/static/images/logo.png">
    </a>
  </div>
  <div class="content-header clearfix">
    <input type="hidden" name="orderId" value="${purchaseOrder.id}">
    <input type="hidden" name="qrcode" value="${qrcode}">
    <input type="hidden" name="stageNum" value="${orderListPayment.stageNum}">
    <span class="pull-left">订单提交成功，请尽快付款：订单号：${purchaseOrder.orderNo}</span>
    <span class="pull-right">应付金额：<mark>${orderListPayment.waitpayAmount}</mark>元</span>
  </div>
  <div class="content-body">
    <h6>微信支付</h6>
    <div class="body-content clearfix">
      <div class="code-wrap pull-left">
        <div class="code-img qrimg">
          <%--<img src="../images/gg_weichart_code.png">--%>
        </div>
        <div class="code-tips clearfix">
          <i class="code-icon pull-left"></i>
          <span class="pull-left">请用微信扫一扫扫描二维码支付</span>
        </div>
      </div>
      <div class="phone-img pull-right">
        <img src="/static/images/gg_img_phone_weichart_scan.png">
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
<script src="/static/js/purchaseOrder/wxPay.js"></script>
