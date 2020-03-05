<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/17
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>订单详情-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" href="/static/css/order01.css">
    <link rel="stylesheet" type="text/css" href="/static/css/order.css">
    <link rel="stylesheet" type="text/css" href="/static/css/person.css">

    <script>
    var timeout= "<fmt:formatDate value="${pov.timeout}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss"/>";
    var phone ="${pov.receiverPhone}";
    var expressNumber ="${pov.expressNumber}";
    </script>
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

<%--待确认 0,
	待付款 1,待发货 2,退款申请 3,待收货 5 退货申请 6,待支付尾款 20,
	退货中 7,已退款 8,已退货 9,已收货 10,已取消 11
	待评价  12,已完成 13,退款中 14
	--%>
<body>
<div class="person-wrap">
    <div class="person-cont">
        <div class="order-state">
            <input type="hidden" id="orderId" value="${pov.id}"/>
            <input type="hidden" id="orderType" value="${pov.orderType}"/>
<%--订单完成--%>
<c:if test="${pov.status == 13 || pov.status == 12}">
<div class="state-cont">
    <div class="state-lcol">
        <div class="state-top">订单号：${pov.orderNo}</div>
        <h3 class="state-txt red">交易完成</h3>
        <c:if test="${pov.statusName == '待评价'}">
        <div class="state-btns">
            <a href="/evaluate/orderEvaluation/${pov.id}" class="pay">立即评价</a>
        </div>
        </c:if>
    </div>
    <div class="state-rcol">
        <div class="order-process clearfix">
            <div class="process-group pull-left submit-order">
                <i class="active"></i>
                <p>提交订单</p>
                <span><fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all  pull-left"></span>
            <div class="process-group pull-left pay-success">
                <i class="active"></i>
                <p>付款成功</p>
                <span><fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all pull-left"></span>
            <div class="process-group pull-left product-sending">
                <i class="active"></i>
                <p>商品出库</p>
                <span><fmt:formatDate value="${pov.shipTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.shipTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all pull-left"></span>
            <div class="process-group pull-left waiting-car">
                <i class="active"></i>
                <p>确认收货</p>
                <span><fmt:formatDate value="${pov.finishTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.finishTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all pull-left"></span>
            <div class="process-group pull-left order-complete">
                <i class="active"></i>
                <p>完成</p>
            </div>
        </div>
    </div>
</div>
</c:if>
<%--订单等待付款--%>
<c:if test="${pov.status == 1 || pov.status == 20  }">
<div class="state-cont">
    <div class="state-lcol">
        <div class="state-top">订单号：${pov.orderNo}</div>
        <h3 class="state-txt red">${pov.statusName}</h3>
        <c:if test="${pov.status == 20}">
            <c:if test="${!empty pov.presellExtra}">
                <span class="remain-time">
                    <strong class="time-icon">尾款支付时间：<fmt:formatDate value="${pov.presellExtra.paymentStart}" pattern="yyyy-MM-dd"/> -<fmt:formatDate value="${pov.presellExtra.paymentEnd}" pattern="yyyy-MM-dd"/></strong>
                </span>
                <span class="remain-time" style="margin-left: -79px;">
                    <strong class="time-icon">预计发货时间：<fmt:formatDate value="${pov.presellExtra.shipTime}" pattern="yyyy-MM-dd"/></strong>
                </span>
            </c:if>
            <c:if test="${payFlag}">
                <div class="state-btns">
                    <a href="/order/submit/${pov.id}" class="pay">支付尾款</a>
                    <%--<a onclick="OrderDetail.cancel()" class="cancel">取消订单</a>--%>
                </div>
            </c:if>
        </c:if>
        <c:if test="${pov.status != 20}">
            <c:if test="${pov.orderType == 'presell'}">
                <c:if test="${fn:length(pov.paymentList) > 1}">
                    <span class="remain-time">
                        <strong class="time-icon">定金支付期：<fmt:formatDate value="${pov.paymentList[0].payStart}" pattern="yyyy-MM-dd"/> -<fmt:formatDate value="${pov.paymentList[0].payEnd}" pattern="yyyy-MM-dd"/></strong>
                    </span>
                    <div class="state-btns">
                        <a href="/order/submit/${pov.id}" class="pay">支付定金</a>
                        <a onclick="OrderDetail.cancel()" class="cancel">取消订单</a>
                    </div>
                </c:if>
                <c:if test="${fn:length(pov.paymentList) < 2}">
                    <span class="remain-time">
                        <strong class="time-icon">剩余<span class="hour"></span>小时<span class="minute"></span>分钟付款</strong>
                    </span>
                    <div class="state-btns">
                        <a href="/order/submit/${pov.id}" class="pay">付款</a>
                        <a onclick="OrderDetail.cancel()" class="cancel">取消订单</a>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${pov.orderType != 'presell'}">
                <span class="remain-time">
                    <strong class="time-icon">剩余<span class="hour"></span>小时<span class="minute"></span>分钟付款</strong>
                </span>
                <div class="state-btns">
                    <a href="/order/submit/${pov.id}" class="pay">付款</a>
                    <a onclick="OrderDetail.cancel()" class="cancel">取消订单</a>
                </div>
            </c:if>
        </c:if>
    </div>
    <div class="state-rcol">
        <div class="state-rtop cl9 hide">该订单会为您保留24小时（从下单之日算起），24小时之后如果还未付款，系统将自动取消该订单。</div>
        <div class="order-process clearfix">
            <div class="node ready">
                <i class="node-icon icon-start"></i>
                <p>提交订单</p>
                <span><fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span>
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
</c:if>
<%--订单商品出货--%>
<c:if test="${pov.status == 5 || pov.status == 7 || pov.status == 6  }">
<div class="state-cont">
    <div class="state-lcol">
        <div class="state-top">订单号：${pov.orderNo}</div>
        <h3 class="state-txt red">${pov.statusName}</h3>
        <c:if test="${pov.status == 5}">
        <div class="state-btns">
            <a onclick="OrderDetail.receiving()" class="pay receiving">确认收货</a>
        </div>
        </c:if>
    </div>
    <div class="state-rcol">
        <div class="order-process clearfix">
            <div class="process-group pull-left submit-order">
                <i class="active"></i>
                <p>提交订单</p>
                <span><fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all  pull-left"></span>
            <div class="process-group pull-left pay-success">
                <i class="active"></i>
                <p>付款成功</p>
                <span><fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all pull-left"></span>
            <div class="process-group pull-left product-sending">
                <i class="active"></i>
                <p>商品出库</p>
                <span><fmt:formatDate value="${pov.shipTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.shipTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-half pull-left"></span>
            <div class="process-group pull-left waiting-car">
                <i></i>
                <p>确认收货</p>
            </div>
            <span class="process-route-line pull-left"></span>
            <div class="process-group pull-left order-complete">
                <i></i>
                <p>完成</p>
            </div>
        </div>
    </div>
</div>
</c:if>
<%--订单付款成功--%>
<c:if test="${pov.status == 2  || pov.status == 14 || pov.status == 3 }">
<div class="state-cont">
    <div class="state-lcol">
        <div class="state-top">订单号：${pov.orderNo}</div>
        <h3 class="state-txt red">${pov.statusName}</h3>
        <c:if test="${pov.status == 2}">
            <c:if test="${!empty pov.presellExtra}">
                <span class="remain-time">
                    <strong class="time-icon">预计发货时间：<fmt:formatDate value="${pov.presellExtra.shipTime}" pattern="yyyy-MM-dd"/></strong>
                </span>
            </c:if>

            <div class="state-btns">
                <a onclick="OrderDetail.warn()" class="pay hide">提醒发货</a>
            </div>
        </c:if>

    </div>
    <div class="state-rcol">
        <div class="order-process clearfix">
            <div class="process-group pull-left submit-order">
                <i class="active"></i>
                <p>提交订单</p>
                <span><fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-all  pull-left"></span>
            <div class="process-group pull-left pay-success">
                <i class="active"></i>
                <p>付款成功</p>
                <span><fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/> </span>
            </div>
            <span class="process-route-line process-route-line-half pull-left"></span>
            <div class="process-group pull-left product-sending">
                <i></i>
                <p>商品出库</p>
            </div>
            <span class="process-route-line pull-left"></span>
            <div class="process-group pull-left waiting-car">
                <i></i>
                <p>确认收货</p>
            </div>
            <span class="process-route-line pull-left"></span>
            <div class="process-group pull-left order-complete">
                <i></i>
                <p>完成</p>
            </div>
        </div>
    </div>
</div>
</c:if>
<%--订单退货退款--%>
<c:if test="${pov.status == 8  ||  pov.status == 9 }">
    <div class="state-cont">
    <div class="state-lcol">
    <div class="state-top">订单号：${pov.orderNo}</div>
    <h3 class="state-txt red">${pov.statusName}</h3>
    </div>
    <div class="state-rcol">
    <div class="order-process clearfix">
    <div class="process-group pull-left submit-order">
    <i class="active"></i>
    <p>提交订单</p>
    <span><fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/> </span>
    <span> <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span>
    </div>
    <span class="process-route-line process-route-line-all  pull-left"></span>
   <c:if test="${pov.status == 9}">
    <div class="process-group pull-left pay-success">
    <i class="active"></i>
    <p>退货成功</p>
    <span><fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/> </span>
    <span> <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/> </span>
    </div>
    </c:if>
        <c:if test="${pov.status == 8}">
            <div class="process-group pull-left pay-success">
                <i class="active"></i>
                <p>退款成功</p>
                <span><fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/> </span>
                <span> <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/> </span>
            </div>
        </c:if>
    </div>
    </div>
    </div>
    </c:if>
<%--主体--%>
<div class="state-cont track-cont">
    <div class="state-lcol track-lcol">
        <div class="p-item clearfix">
            <div class="p-img">
                <c:forEach items="${pov.orderListVos}" begin="0" end="0" var="list">
                <img src="${list.purGoodsImgUrl}">
                </c:forEach>
            </div>
            <div class="p-info pull-left cl6">送货方式：${pov.expressCompany}</div>
            <c:if test="${!empty pov.expressNumber}">
                <div class="p-info pull-left cl6">快递单号：${pov.expressNumber}</div>
            </c:if>
        </div>
    </div>
    <div class="state-rcol track-rcol">
        <div class="track-list">
            <ul class="express">


            </ul>
            <ul>
                <c:if test="${ not empty pov.shipTime}">
                <li class="afterdate">
                    <i class="dot-icon"></i>
                    <span class="cl9"><fmt:formatDate value="${pov.shipTime}" pattern="yyyy-MM-dd"/><span class="week">/${weekShip}
                    <fmt:formatDate value="${pov.shipTime}" pattern="HH:mm:ss"/>
                    </span></span>
                    <strong class="ml20">您的商品已出库</strong>
                </li>
                    </c:if>
                <c:if test="${ not empty pov.payTime}">
                <li class="afterdate">
                    <i class="dot-icon"></i>
                    <span class="cl9"> <fmt:formatDate value="${pov.payTime}" pattern="yyyy-MM-dd"/><span class="week">/${weekPay}
                       <fmt:formatDate value="${pov.payTime}" pattern="HH:mm:ss"/>
                    </span></span>
                    <strong class="ml20">您支付了订单</strong>
                </li>
                    </c:if>
                <c:if test="${ not empty pov.createTime }">
                <li class="afterdate">
                    <i class="dot-icon"></i>
                    <span class="cl9"> <fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd"/><span class="week">/${weekCreate}
                       <fmt:formatDate value="${pov.createTime}" pattern="HH:mm:ss"/> </span></span>
                    <strong class="ml20">您提交了订单，请等待系统确认</strong>
                </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<div class="order-info">
    <div class="ui-switchable-body clearfix">
        <div class="dl">
            <strong class="dt block">收货人信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">收货人：</span>
                    <div class="info-rcol">${pov.receiver}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">地址：</span>
                    <div class="info-rcol">${pov.province}${pov.city}${pov.district}${pov.address}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">手机号码：</span>
                    <div class="info-rcol phone"><span id="phone"></span></div>
                </div>
            </div>
        </div>
        <div class="dl">
            <strong class="dt block">配送信息</strong>
            <div class="dd">
                <c:if test="${!empty pov.expressCompany}">
                    <div class="item clearfix">
                        <span class="label">配送方式：</span>
                        <div class="info-rcol">${pov.expressCompany}</div>
                    </div>
                </c:if>
                <c:if test="${!empty pov.expressNumber}">
                    <div class="item clearfix">
                        <span class="label">快递单号：</span>
                        <div class="info-rcol">${pov.expressNumber}</div>
                    </div>
                </c:if>
                <c:if test="${!empty pov.carriage}">
                    <div class="item clearfix">
                        <span class="label">运费：</span>
                        <div class="info-rcol">¥${pov.carriage}</div>
                    </div>
                </c:if>
                <c:if test="${!empty pov.shipTime}">
                    <div class="item clearfix">
                        <span class="label">送货日期：</span>
                        <div class="info-rcol"><fmt:formatDate value="${pov.shipTime}" pattern="yyyy-MM-dd"/></div>
                    </div>
                </c:if>

            </div>
        </div>
        <div class="dl">
            <strong class="dt block">付款信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">付款方式：</span>
                    <c:if test="${pov.payType == 1}">
                    <div class="info-rcol">微信支付</div>
                    </c:if>
                    <c:if test="${pov.payType == 2}">
                        <div class="info-rcol">支付宝支付</div>
                    </c:if>
                </div>
                <div class="item clearfix">
                    <span class="label">商品总额：</span>
                    <div class="info-rcol">¥${pov.totalAmount}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">应支付金额：</span>
                    <div class="info-rcol">¥${pov.finalAmount}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">实付金额：</span>
                    <div class="info-rcol">¥${pov.paymentAmount}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">运费金额：</span>
                    <div class="info-rcol">¥${pov.buyerCarriage}</div>
                </div>
                <div class="item clearfix">
                    <span class="label">订单优惠：</span>
                    <div class="info-rcol">¥${pov.discountAmount}</div>
                </div>
            </div>
        </div>
        <c:if test="${!empty pov.needInvoice && pov.needInvoice == true}">
            <div class="dl">
                <strong class="dt block">发票信息</strong>
                <div class="dd">
                    <div class="item clearfix">
                        <span class="label">发票类型：</span>
                        <div class="info-rcol">纸质发票</div>
                    </div>
                    <div class="item clearfix">
                        <span class="label">发票抬头：</span>
                        <div class="info-rcol">${pov.invoiceName}</div>
                    </div>
                    <div class="item clearfix">
                        <span class="label">发票内容：</span>
                        <div class="info-rcol">${pov.invoiceContent}</div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
<div class="order-goods">
    <div class="mc">
        <div class="goods-list">
            <table class="tb-order">
                <thead>
                <tr>
                    <td class="grap"></td>
                    <td class="good-w">宝贝</td>
                    <td>商品编号</td>
                    <td>商品总额</td>
                    <td>数量</td>
                    <td class="option-w">操作</td>
                    <td class="grap"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pov.orderListVos}" var="good">
                    <c:if test="${good.giftFlag != 'T'}">
                        <tr>
                            <td class="grap"></td>
                            <td>
                                <div class="p-item clearfix">
                                    <div class="p-img">
                                        <img src="${good.purGoodsImgUrl}">
                                    </div>
                                    <div class="p-info">
                                        <div class="p-name">
                                                ${good.purGoodsName}
                                        </div>
                                        <c:if test="${good.isSample == 'T'}">
                                            <i class="simple-tag pull-left">样品</i>
                                        </c:if>
                                        <c:if test="${good.goodType == 'zengpin'}">
                                            <i class="simple-tag pull-left">赠品</i>
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span>${good.cargoSkuId}</span>
                            </td>
                            <td>
                                <span class="f-price">¥${good.finalAmount}</span>
                            </td>
                            <td><span class="cl9">x${good.number}</span></td>
                            <td>
                                <c:if test="${good.giftFlag != 'T' && pov.isSample != 'T' && good.goodType != 'zengpin'}">
                                    <c:if test="${pov.status == 2}">
                                        <c:if test="${good.shipReturnTime == 0}">
                                            <a href="/afterSale/afterSaleRefund/${pov.id}/${good.purGoodsSkuId}">申请退款</a>
                                        </c:if>
                                        <c:if test="${good.shipReturnTime > 0}">
                                            <c:if test="${good.flowList[0].asType == 'refund'}">
                                                <a href="/afterSale/afterSaleRefund/${pov.id}/${good.purGoodsSkuId}">查看售后</a>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${pov.status == 5}">
                                        <c:if test="${good.returnTime == 0}">
                                            <a href="/afterSale/afterSaleSelect/${pov.id}/${good.purGoodsSkuId}">申请售后</a>
                                        </c:if>
                                        <c:if test="${good.returnTime > 0}">
                                            <c:if test="${good.flowList[0].asType == 'refund_return'}">
                                                <a href="/afterSale/afterSaleReturn/${pov.id}/${good.purGoodsSkuId}">查看售后</a>
                                            </c:if>
                                            <c:if test="${good.flowList[0].asType == 'exchange'}">
                                                <a href="/afterSale/afterSaleExchange/${pov.id}/${good.purGoodsSkuId}">查看售后</a>
                                            </c:if>
                                            <c:if test="${good.flowList[0].asType == 'refund'}">
                                                <a href="/afterSale/afterSaleRefund/${pov.id}/${good.purGoodsSkuId}">查看售后</a>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </c:if>
                            </td>
                            <td class="grap"></td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="goods-total clearfix">
            <c:if test="${fn:length(pov.paymentList) == 2}">
                    <ul>
                        <c:if test="${pov.paymentList[0].stageNum == 1}">
                            <li class="clearfix">
                                <c:choose>
                                <c:when test="${pov.paymentList[0].payStatus == 'T' && pov.paymentList[0].stageNum == 1 }">
                                    <span class="label">定金(已付款)：</span>
                                    <span class="txt count">¥${pov.paymentList[0].waitpayAmount}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="label">定金(等待付款)：</span>
                                    <span class="txt count">¥${pov.paymentList[0].waitpayAmount}</span>
                                </c:otherwise>
                                </c:choose>
                            </li>
                         </c:if>
                            <li class="clearfix">
                                <c:choose>
                                <c:when test="${pov.paymentList[1].payStatus == 'T' && pov.paymentList[1].stageNum == 2 }">
                                    <span class="label">尾款(已付款)：</span>
                                    <span class="txt count">¥${pov.paymentList[1].waitpayAmount}</span>
                                </c:when>
                                <c:when test="${pov.paymentList[0].payStatus == 'T' && pov.paymentList[0].stageNum == 1 }">
                                    <span class="label">尾款(等待付款)：</span>
                                    <span class="txt count">¥${pov.paymentList[1].waitpayAmount}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="label">尾款(未开始)：</span>
                                    <span class="txt count">¥${pov.paymentList[1].waitpayAmount}</span>
                                </c:otherwise>
                                </c:choose>
                            </li>

                        <li class="clearfix">
                            <span class="label">运　　费：</span>
                            <span class="txt">¥${pov.buyerCarriage}</span>
                        </li>
                        <li class="clearfix">
                            <span class="label red">实付款：</span>
                            <c:if test="${pov.paymentList[0].payStatus == 'F' && pov.paymentList[1].payStatus == 'F'  }">
                                <span class="txt count">¥0</span>
                            </c:if>
                            <c:if test="${pov.paymentList[0].payStatus == 'T' && pov.paymentList[1].payStatus == 'F'  }">
                            <span class="txt count">¥${pov.paymentList[0].waitpayAmount}</span>
                            </c:if>
                            <c:if test="${pov.paymentList[0].payStatus == 'T' && pov.paymentList[1].payStatus == 'T' }">
                                <span class="txt count">¥${pov.paymentList[0].waitpayAmount + pov.paymentList[1].waitpayAmount }</span>
                            </c:if>
                        </li>
                    </ul>
            </c:if>

            <c:if test="${fn:length(pov.paymentList) == 1 || empty pov.paymentList }">
            <ul>
                <li class="clearfix">
                    <span class="label">商品总额：</span>
                    <span class="txt">¥${pov.totalAmount}</span>
                </li>
                <li class="clearfix">
                    <span class="label">优　　惠：</span>
                    <span class="txt">-¥${pov.discountAmount}</span>
                </li>
                <li class="clearfix">
                    <span class="label">运　　费：</span>
                    <span class="txt">¥${pov.buyerCarriage}</span>
                </li>
                <li class="clearfix">
                    <span class="label">应付总额：</span>
                    <span class="txt">¥${pov.finalAmount}</span>
                </li>
                   <li>
                        <span class="label red">实付金额：</span>
                       <span class="txt count">¥${pov.paymentAmount}</span>
                   </li>
            </ul>
            </c:if>
        </div>
    </div>
</div>
<%--主体 END--%>
            </div>
    </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/purchaseOrder/order_detail.js"></script>
<script id="expressList" type="text/html">
    <li class="afterdate">
        <i class="dot-icon"></i>
                    <span class="cl9">{{ftim}} </span>
        <strong class="ml20">{{context}}</strong>
    </li>
</script>
</html>
