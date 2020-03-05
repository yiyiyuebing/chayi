<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/12
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单等待付款-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/person.css">
  <link rel="stylesheet" href="/static/css/person.list.css">
  <link rel="stylesheet" type="text/css" href="/static/css/order.css">
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
    <div class="order-state">
      <div class="state-cont">
        <div class="state-lcol">
          <div class="state-top">订单号：51563273654</div>
          <h3 class="state-txt red">等待付款</h3>
                        <span class="remain-time">
                            <strong class="time-icon">剩余23小时2分钟付款</strong>
                        </span>
          <div class="state-btns">
            <a href="/purchaseOrder/orderSubmit" class="pay">付款</a>
            <a href="order.html" class="cancel">取消订单</a>
          </div>
        </div>
        <div class="state-rcol">
          <div class="state-rtop cl9">该订单会为您保留24.0小时（从下单之日算起），24小时之后如果还未付款，系统将自动取消该订单。</div>
          <div class="order-process clearfix">
            <div class="node ready">
              <i class="node-icon icon-start"></i>
              <p>提交订单</p>
              <p>2017-04-25</p>
              <p>10:02:37</p>
            </div>
            <div class="node">
              <i class="node-icon icon-pay"></i>
              <p class="cl9">付款成功</p>
            </div>
            <div class="node">
              <i class="node-icon icon-store"></i>
              <p class="cl9">商品出库</p>
            </div>
            <div class="node">
              <i class="node-icon icon-express"></i>
              <p class="cl9">等待收货</p>
            </div>
            <div class="node finish">
              <i class="node-icon icon-finish"></i>
              <p class="cl9">完成</p>
            </div>
          </div>
        </div>
      </div>
      <div class="state-cont track-cont">
        <div class="state-lcol track-lcol">
          <div class="p-item clearfix">
            <div class="p-img">
              <img src="/static/images/index_nav_product_img01.png">
            </div>
            <div class="p-info pull-left cl6">送货方式：普通快递</div>
          </div>
        </div>
        <div class="state-rcol track-rcol">
          <div class="track-list">
            <ul>
              <li class="afterdate">
                <i class="dot-icon"></i>
                <span class="cl9">2017-04-25<span class="week">/周二15:13:37</span></span>
                <strong class="ml20">您提交了订单，请等待系统确认</strong>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <%--订单信息 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/order_information.jsp" ></jsp:include>
      <%--订单信息 ending--%>
    </div>
  </div>
</div>
<!-- END主内容 -->

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>

</body>
</html>
