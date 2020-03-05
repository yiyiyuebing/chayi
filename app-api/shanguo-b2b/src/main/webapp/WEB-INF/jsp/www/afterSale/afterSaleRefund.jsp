<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <title>退款-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/cssresets.css">
    <link rel="stylesheet" href="/static/css/public.css">
    <link rel="stylesheet" href="/static/css/after_sale_status.css">

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
    <a class="pull-left" href="/">首页</a>
    <i class="pull-left">></i>
    <a class="pull-left" href="/user/userInfo">我的优茶联</a>
    <i class="pull-left">></i>
    <a class="pull-left" href="/order/list">进货单</a>
</div>
<div class="step-nav clearfix">
        <span class="step step-active pull-left">
            <i>1</i>买家申请退款
        </span>
    <i class="step-break step-break-active pull-left"></i>
    <span class="step pull-left">
            <i>2</i>卖家处理退款申请
        </span>
    <i class="step-break pull-left"></i>
    <span class="step pull-left">
            <i>3</i>卖家确认，退款完成
        </span>
</div>
<div class="content clearfix">
    <div class="content-left pull-left">
        <!--提交订单模块-->
        <div class="step-form">
            <div class="left-title clearfix">
                <label class="pull-left">申请服务：</label>
                <div class="server-select pull-left">
                    <span class="server-title">
                        <input class="server-input" type="text" readonly value="仅退款" data-service="refund" name="afterService" id="serverInput">
                    </span>
                    <ul class="server-list">
                        <c:if test="${goodMsgList.status == 2}">
                            <li data-service="refund" data-url="/afterSale/afterSaleRefund/${orderId}/${skuId}">仅退款</li>
                        </c:if>
                        <c:if test="${goodMsgList.status == 5}">
                            <c:if test="${empty operType}">
                                <li data-service="refund_return" data-url="/afterSale/afterSaleReturn/${orderId}/${skuId}">退款退货</li>
                                <li data-service="refund" data-url="/afterSale/afterSaleRefund/${orderId}/${skuId}">仅退款</li>
                                <li data-service="exchange" data-url="/afterSale/afterSaleExchange/${orderId}/${skuId}">换货</li>
                            </c:if>
                            <c:if test="${!empty operType}">
                                <li data-service="refund_return" data-url="/afterSale/afterSaleReturn/${orderId}/${skuId}?operType=${operType}">退款退货</li>
                                <li data-service="refund" data-url="/afterSale/afterSaleRefund/${orderId}/${skuId}?operType=${operType}">仅退款</li>
                                <li data-service="exchange" data-url="/afterSale/afterSaleExchange/${orderId}/${skuId}?operType=${operType}">换货</li>
                            </c:if>
                        </c:if>
                    </ul>
                </div>
            </div>

            <c:if test="${goodMsgList.status == 5 && goodMsgList.returnTime > 1}">
                <div class="left-tips">
                    <i class="icon-light"></i>温馨提示:仅剩一次售后机会
                </div>
            </c:if>
            <c:if test="${goodMsgList.status == 2 && goodMsgList.shipReturnTime > 1}">
                <div class="left-tips">
                    <i class="icon-light"></i>温馨提示:仅剩一次售后机会
                </div>
            </c:if>
            <div class="form-group clearfix">
                <label class="pull-left">退款原因：</label>
                <i class="tips pull-left">*</i>
                <div class="reason-wrap pull-left">
                    <input type="hidden" name="orderId" value="${orderId}">
                    <input type="hidden" name="orderListId" value="${goodMsgList.id}">
                    <input type="hidden" name="orderType" value="${goodMsgList.orderType}">
                    <input type="hidden" name="goodSkuId" value="${skuId}">
                    <input type="hidden" name="operType" value="${operType}">
                    <input type="hidden" name="flowId" value="${orderItemAsFlow.id}">
                    <c:if test="${!empty orderItemAsFlow && !empty orderItemAsFlow.returnReason}">
                        <input type="text" name="returnSeason" value="${orderItemAsFlow.returnReason}" readonly id="reasonInput">
                        <ul class="reason-list">
                            <c:forEach items="${returnReasonList}" var="returnReason">
                                <li>${returnReason.value}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <c:if test="${empty orderItemAsFlow && empty orderItemAsFlow.returnReason}">
                        <input type="text" name="returnSeason" value="请选择退款原因" readonly id="reasonInput">
                        <ul class="reason-list">
                            <c:forEach items="${returnReasonList}" var="returnReason">
                                <li>${returnReason.value}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
            </div>
            <c:if test="${goodMsgList.status == 2}">
                <div class="form-group clearfix">
                    <label class="pull-left">需要退款金额：</label>
                    <i class="tips pull-left">*</i>
                    <input type="hidden" name="refundAmount" value="${goodMsgList.refundAmount}" data-max-price="${goodMsgList.refundAmount}">
                    <span class="refund-money pull-left"><mark name="returnAmount">${goodMsgList.refundAmount}</mark> 元(最多￥${goodMsgList.refundAmount}，包含邮费￥${goodMsgList.freight})</span>
                </div>
            </c:if>
            <c:if test="${goodMsgList.status == 5}">
                <div class="form-group clearfix">
                    <label class="pull-left">退款金额：</label>
                    <i class="tips pull-left">*</i>
                    <div class="exchange pull-left clearfix">
                        <input type="text" name="refundAmount" value="${goodMsgList.refundAmount}" data-max-price="${goodMsgList.refundAmount}">
                        <span>元（最多<mark>￥${goodMsgList.refundAmount}</mark>元）</span>
                    </div>
                </div>
            </c:if>

            <div class="form-group clearfix">
                <label class="pull-left">说明：</label>
                <i class="tips pull-left"></i>
                <c:if test="${!empty orderItemAsFlow && !empty orderItemAsFlow.orderItemReplyVos}">
                    <textarea id="textContent" class="pull-left" name="operDesc" rows="2">${orderItemAsFlow.orderItemReplyVos[0].operDesc}</textarea>
                </c:if>
                <c:if test="${empty orderItemAsFlow && empty orderItemAsFlow.orderItemReplyVos}">
                    <textarea id="textContent" class="pull-left" name="operDesc" rows="2"></textarea>
                </c:if>
                <%--<textarea id="textContent" class="pull-left" name="operDesc" rows="2"></textarea>--%>
                <span class="letter-count">(<i id="letter-actual">0</i>/<i id="letter-all">200</i>字)</span>
            </div>
            <div class="form-group clearfix">
                <c:if test="${!empty orderItemAsFlow && !empty orderItemAsFlow.attachment}">
                    <input type="hidden" name="ipt-photo-list" value="${orderItemAsFlow.attachment}">
                </c:if>
                <label class="pull-left">上传凭证：</label>
                <i class="tips pull-left"></i>
                <div class="img-wrap pull-left">
                    <div class="img-box clearfix" id="photo-list">
                    </div>
                    <a href="javascript:;" id="selectImgBtn">选择凭证图片</a>
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left"></label>
                <i class="tips pull-left"></i>
                <a href="javascript:void(0)" class="submit-btn pull-left">提交申请</a>
                <a href="/order/list" class="cancel-btn pull-left">取消并返回</a>
            </div>
        </div>
    </div>
    <div class="content-right pull-left">
        <h4>商品类型</h4>
        <ul class="product-list">
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
                <span><mark>${goodMsgList.finalAmount}元*${goodMsgList.number}（数量）</mark></span>
            </div>
        </div>
    </div>
</div>
</body>

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>

<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>
<script src="/static/js/afterSale/after_sale_status.js"></script>
<script src="/static/js/afterSale/afterSaleRefund.js"></script>

</html>