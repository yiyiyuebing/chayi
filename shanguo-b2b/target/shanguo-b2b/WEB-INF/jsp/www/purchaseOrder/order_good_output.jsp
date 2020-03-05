<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/17
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单商品出库-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" href="/static/css/order03.css">
    <link rel="stylesheet" type="text/css" href="/static/css/person.css">
</head>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/personal_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>
<body>
<div class="person-wrap">
    <div class="person-cont">
        <div class="order-state">
            <div class="state-cont">
                <div class="state-lcol">
                    <div class="state-top">订单号：51563273654</div>
                    <h3 class="state-txt red">商品出库</h3>
                        <span class="remain-time">

                        </span>
                    <div class="state-btns">
                        <a href="#" class="pay">确认收货</a>
                     <%--   <a href="order.html" class="cancel">查看物流</a>--%>
                    </div>
                </div>
                <div class="state-rcol">
                    <div class="order-process clearfix">
                        <div class="process-group pull-left submit-order">
                            <i class="active"></i>
                            <p>提交订单</p>
                            <span>2017-09-08</span>
                            <span>15:09:09</span>
                        </div>
                        <span class="process-route-line process-route-line-all  pull-left"></span>
                        <div class="process-group pull-left pay-success">
                            <i class="active"></i>
                            <p>付款成功</p>
                            <span>2017-09-08</span>
                            <span>15:09:09</span>
                        </div>
                        <span class="process-route-line process-route-line-all pull-left"></span>
                        <div class="process-group pull-left product-sending">
                            <i class="active"></i>
                            <p>商品出库</p>
                            <span>2017-09-08</span>
                            <span>15:09:09</span>
                        </div>
                        <span class="process-route-line process-route-line-half pull-left"></span>
                        <div class="process-group pull-left waiting-car">
                            <i></i>
                            <p>等待收货</p>
                        </div>
                        <span class="process-route-line pull-left"></span>
                        <div class="process-group pull-left order-complete">
                            <i></i>
                            <p>完成</p>
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
                            <li class="afterdate">
                                <i class="dot-icon"></i>
                                <span class="cl9">2017-04-25<span class="week">/周二15:13:37</span></span>
                                <strong class="ml20">您支付了订单</strong>
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
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
</html>
