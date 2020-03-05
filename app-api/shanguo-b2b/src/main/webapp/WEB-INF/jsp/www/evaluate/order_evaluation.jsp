<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/16
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>

<html>
<head>
    <title>评价订单-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
    <link rel="stylesheet" type="text/css" href="/static/css/ratemanage.css">
    <link rel="stylesheet" type="text/css" href="/static/css/star.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">

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

<script>
    var skuCount = '${fn:length(pov.orderListVos)}';
    var orderId = '${pov.id}';
</script>

<body>
<div class="person-wrap">
    <div class="person-cont">
        <div class="detail-hd">
            <div class="orderinfo">
                <h3 class="o-title">评价订单</h3>
                <div class="o-info">
                    <span>订单号：<a class="cl3">${pov.orderNo}</a></span>
                    <span class="ml20"> <fmt:formatDate value="${pov.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
                </div>
            </div>
            <div class="mycomment-form">
                <%--<div class="f-item">
                    <div class="fi-info">
                        <div class="comment-service clearfix">
                            <div class="s-img">
                                <img src="/static/images/kuaidi.png">
                            </div>
                            <div class="s-main">
                                <strong class="s-name block">配送服务</strong>
                                <div class="cl9">配送小哥的服务您还满意吗？</div>
                            </div>
                        </div>
                    </div>
                    <div class="fi-operate">
                        <div class="commstar-group clearfix">
                            <div class="item">
                                <div id="star-good" class="star-wrap">
                                    <span>商品包装</span>
                                    <ul>
                                        <li><a href="javascript:;">1</a></li>
                                        <li><a href="javascript:;">2</a></li>
                                        <li><a href="javascript:;">3</a></li>
                                        <li><a href="javascript:;">4</a></li>
                                        <li><a href="javascript:;">5</a></li>
                                    </ul>
                                    <span></span>
                                    <p></p>
                                 </div>
                            </div>
                            <div class="item" data-question="1828" data-key="ro1828">
                                <div id="star-sh" class="star-wrap">
                                    <span>送货速度</span>
                                    <ul>
                                        <li><a href="javascript:;">1</a></li>
                                        <li><a href="javascript:;">2</a></li>
                                        <li><a href="javascript:;">3</a></li>
                                        <li><a href="javascript:;">4</a></li>
                                        <li><a href="javascript:;">5</a></li>
                                    </ul>
                                    <span ></span>
                                    <p></p>
                                </div>
                            </div>
                            <div class="item" data-question="1828" data-key="ro1828">
                                <div id="star-service" class="star-wrap">
                                    <span>服务态度</span>
                                    <ul>
                                        <li><a href="javascript:;">1</a></li>
                                        <li><a href="javascript:;">2</a></li>
                                        <li><a href="javascript:;">3</a></li>
                                        <li><a href="javascript:;">4</a></li>
                                        <li><a href="javascript:;">5</a></li>
                                    </ul>
                                    <span ></span>
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>--%>
                <c:forEach items="${pov.orderListVos}" var="goods" varStatus="status">
                    <c:if test="${goods.giftFlag != 'T'}">
                        <div class="form-part1 evaluation-info-list">
                            <input type="hidden" class="skuId" value="${goods.purGoodsSkuId}"/>
                            <input type="hidden" class="orderId" value="${pov.id}"/>
                            <div class="f-item f-goods clearfix">
                                <div class="fi-info">
                                    <div class="comment-goods">
                                        <div class="p-img">
                                            <img src="${goods.purGoodsImgUrl}">
                                        </div>
                                        <div class="p-name">
                                            <a class="cl6">${goods.purGoodsName}${goods.purGoodsType}</a>
                                        </div>
                                        <div class="p-price">供货价：¥${goods.supplyPrice}</div>
                                    </div>
                                </div>
                                <div class="fi-operate">
                                    <div class="fop-detail">
                                            <%-- <div class="red dot-icon">请至少填写一件商品的评价</div>--%>
                                        <div class="dt-star clearfix">
                                            <div id="star-myd-${status.index}" class="star-wrap star-myd">
                                                <span>商品满意度</span>
                                                <ul>
                                                    <li><a href="javascript:;">1</a></li>
                                                    <li><a href="javascript:;">2</a></li>
                                                    <li><a href="javascript:;">3</a></li>
                                                    <li><a href="javascript:;">4</a></li>
                                                    <li><a href="javascript:;">5</a></li>
                                                </ul>
                                                <span ></span>
                                                <p></p>
                                            </div>
                                        </div>
                                            <%--   <div class="dt-text clearfix">
                                                   <span class="label">买家印象</span>
                                                   <div id="dt-items" class="dt-items clearfix">
                                                       <a class="item">质量很好</a>
                                                       <a class="item">品牌产品</a>
                                                       <a class="item">商品很实惠</a>
                                                       <a class="item">用料正宗</a>
                                                       <a class="item">一般</a>
                                                       <a class="item">味道香醇</a>
                                                       <a class="item">物美价廉</a>
                                                   </div>
                                               </div>--%>
                                        <div class="dt-append-comment clearfix">
                                            <span class="label">评价晒单</span>
                                            <div class="fop-item pull-left">
                                                <textarea class="f-textarea" placeholder="商品是否给力？快分享你的购买心得吧~"></textarea>
                                                    <%--   <span class="comment-num">0/500</span>--%>
                                            </div>
                                        </div>
                                        <div class="m-imgshow clearfix">
                                            <div class="thumbnail-list clearfix">
                                                <div class="evaluation-photo-list-${status.index}" style="float: left">

                                                </div>
                                                <div style="float: left;">
                                                    <div class="btn-wrap pick-store-photo-${status.index}">
                                                        <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                    </div>
                                                </div>
                                                    <%--  <span class="btn-upload"></span>--%>
                                                <span class="upload-num">共<span class="red total-num">0</span>张,还能上传<span class="red last-num">10</span>张</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
                <div class="f-btnbox z-submit-fail">
                    <a onclick="rateList.addEvaluation()" class="btn-submit">提交</a>
                    <span class="check-icon check-tagt ml20"></span>
                    <%--<label class="cl6">匿名评价</label>--%>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/evaluate/ratemanage.js"></script>
<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>
<script src="/static/js/evaluate/ratelist.js"></script>
</html>
