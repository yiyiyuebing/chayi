<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/14
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head lang="en">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <title>选择售后服务类型-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" href="/static/css/cssresets.css">
    <link rel="stylesheet" href="/static/css/public.css">
    <link rel="stylesheet" href="/static/css/after_sale_select.css">
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
    <div class="bread-nav clearfix">
        <span class="pull-left">你的位置：</span>
        <a class="pull-left" href="">首页</a>
        <i class="pull-left">></i>
        <a class="pull-left" href="">我的优茶联</a>
        <i class="pull-left">></i>
        <a class="pull-left" href="">进货单</a>
    </div>

    <div class="content clearfix">
        <div class="content-left pull-left">
            <h4>请选择您要申请的服务类型</h4>
            <ul class="left-btn clearfix">

                <c:if test="${goodMsgList.status == 2}">
                    <li class="pull-left">
                        <a class="pull-left" href="/afterSale/afterSaleRefund/${orderId}/${skuId}">仅退款</a>
                    </li>
                </c:if>
                <c:if test="${goodMsgList.status == 5}">
                    <li class="pull-left">
                        <a class="pull-left" href="/afterSale/afterSaleRefund/${orderId}/${skuId}">仅退款</a>
                    </li>
                    <li class="pull-left">
                        <a class="pull-left" href="/afterSale/afterSaleReturn/${orderId}/${skuId}">退款退货</a>
                    </li>
                    <li class="pull-left">
                        <a class="pull-left" href="/afterSale/afterSaleExchange/${orderId}/${skuId}">换货</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="content-right pull-left">
            <h4>商品类型</h4>
            <ul class="product-list">
                <%--<li>--%>
                    <%--<a href="#" class="right-product clearfix">--%>
                        <%--<div class="img-box pull-left">--%>
                            <%--<img src="../images/product_list_aside_img.png">--%>
                        <%--</div>--%>
                        <%--<div class="txt-box pull-left">--%>
                            <%--<p class="txt-title">$</p>--%>
                            <%--<p class="txt-sku">规格：120g/盒</p>--%>
                        <%--</div>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <li>
                    <a href="#" class="right-product clearfix">
                        <div class="img-box pull-left">
                            <img src="${goodMsgList.purGoodsImgUrl}">
                        </div>
                        <div class="txt-box pull-left">
                            <p class="txt-title">${goodMsgList.purGoodsName}</p>
                            <p class="txt-sku">规格：${goodMsgList.skuName}</p>
                        </div>
                    </a>
                </li>
            </ul>

            <div class="order-content">
                <div class="order-number">
                    <label>订单编号：</label>
                    <span style="letter-spacing: -0.5px">${orderNo}</span>
                </div>
                <div class="order-price">
                    <label><i>单</i>价：</label>
                    <span>${goodMsgList.finalAmount}<mark>元*${goodMsgList.number}（数量）</mark></span>
                </div>
            </div>
        </div>
    </div>
    <%--尾部 starting--%>
    <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
    <%--尾部 ending--%>
</body>
</html>