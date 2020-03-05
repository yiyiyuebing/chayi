<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/8/15
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评价成功-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
  <link rel="stylesheet" href="/static/css/ratesuccess.css">
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

<div class="person-wrap">
  <div class="person-cont">
    <div class="rate-success-box">
      <div class="cont-box">
        <strong class="title block">评价已完成！</strong>
        <p class="orderInfo">
          <strong class="cl6">订单号：${pov.orderNo}</strong>
          <i></i>
          <strong class="cl6">付款金额（元）：<span class="red">${pov.finalAmount}元</span></strong>
        </p>
        <a id="goback" href="/evaluate/evaluatelist" class="block orange mt10"><strong>返回待评价列表></strong></a>
      </div>
    </div>
  </div>
</div>

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
</body>
</html>
