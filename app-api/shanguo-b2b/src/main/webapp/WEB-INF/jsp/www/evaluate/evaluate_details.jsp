<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/16
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>

<html>
<head>
    <title>评价订单详情-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
    <link rel="stylesheet" type="text/css" href="/static/css/ratemanage.css">
    <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">
    <script>
     var rate ={
         url:"${buf}"
     }
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

<body>
<div class="person-wrap">
    <div class="person-cont">
        <div class="detail-hd">
            <div class="orderinfo">
                <h3 class="o-title">评价订单详情</h3>
                <div class="o-info">
                    <span>订单号：<a class="cl3">${pov.orderNo}</a></span>
                    <span class="ml20"> <fmt:formatDate value="${pov.createTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/> </span>
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
                                <span class="label">商品包装</span>
                                    <span class="commstar">
                                        <span data-id="A5" data-code="1827A5" title="非常不满意" class="star star1"></span>
                                        <span data-id="A4" data-code="1827A4" title="不满意" class="star star2"></span>
                                        <span data-id="A3" data-code="1827A3" title="一般"
                                              class="star star3 &lt;%&ndash;active&ndash;%&gt;"></span>
                                        <span data-id="A2" data-code="1827A2" title="满意" class="star star4"></span>
                                        <span data-id="A1" data-code="1827A1" title="非常满意" class="star star5"></span>
                                        <span class="star-info">3分</span>
                                    </span>
                            </div>
                            <div class="item" data-question="1828" data-key="ro1828">
                                <span class="label">送货速度</span>
                                    <span class="commstar">
                                        <span data-id="A5" data-code="1827A5" title="非常不满意" class="star star1"></span>
                                        <span data-id="A4" data-code="1827A4" title="不满意" class="star star2"></span>
                                        <span data-id="A3" data-code="1827A3" title="一般" class="star star3"></span>
                                        <span data-id="A2" data-code="1827A2" title="满意"
                                              class="star star4 &lt;%&ndash;active&ndash;%&gt;"></span>
                                        <span data-id="A1" data-code="1827A1" title="非常满意" class="star star5"></span>
                                        <span class="star-info">4分</span>
                                    </span>
                            </div>
                            <div class="item" data-question="1828" data-key="ro1828">
                                <span class="label">服务态度</span>
                                    <span class="commstar">
                                        <span data-id="A5" data-code="1827A5" title="非常不满意" class="star star1"></span>
                                        <span data-id="A4" data-code="1827A4" title="不满意" class="star star2"></span>
                                        <span data-id="A3" data-code="1827A3" title="一般" class="star star3"></span>
                                        <span data-id="A2" data-code="1827A2" title="满意" class="star star4"></span>
                                        <span data-id="A1" data-code="1827A1" title="非常满意"
                                              class="star star5 &lt;%&ndash;active&ndash;%&gt;"></span>
                                        <span class="star-info">5分</span>
                                    </span>
                            </div>
                        </div>
                    </div>
                </div>--%>
                <c:forEach items="${pov.orderListVos}" var="goods">
                    <c:if test="${goods.giftFlag != 'T'}">
                        <div class="form-part1">
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
                                <%--<c:forEach items="${evaluationList}" var="el" varStatus="status">--%>
                                    <div class="fi-operate">
                                        <div class="fop-detail">
                                                <%--<div class="red success-icon">评价成功！</div>--%>
                                            <div class="dt-star clearfix">
                                                <span class="label">商品满意度</span>
                                                <span class="commstar">
                                                  <c:if test="${goods.evaluationVo.score > 0 && goods.evaluationVo.score <= 1}">
                                                      <span data-id="A5" data-code="1827A5" title="非常不满意"
                                                            class="star star1 active"></span>
                                                      <span data-id="A4" data-code="1827A4" title="不满意"
                                                            class="star star2"></span>
                                                      <span data-id="A3" data-code="1827A3" title="一般"
                                                            class="star star3"></span>
                                                      <span data-id="A2" data-code="1827A2" title="满意"
                                                            class="star star4"></span>
                                                      <span data-id="A1" data-code="1827A1" title="非常满意"
                                                            class="star star5 "></span>
                                                  </c:if>
                                                 <c:if test="${goods.evaluationVo.score > 1 && goods.evaluationVo.score <= 2}">
                                                     <span data-id="A5" data-code="1827A5" title="非常不满意"
                                                           class="star star1 active"></span>
                                                     <span data-id="A4" data-code="1827A4" title="不满意"
                                                           class="star star2"></span>
                                                     <span data-id="A3" data-code="1827A3" title="一般" class="star star3"></span>
                                                     <span data-id="A2" data-code="1827A2" title="满意" class="star star4"></span>
                                                     <span data-id="A1" data-code="1827A1" title="非常满意"
                                                           class="star star5 "></span>
                                                 </c:if>
                                                <c:if test="${goods.evaluationVo.score > 2 && goods.evaluationVo.score <= 3}">
                                                    <span data-id="A5" data-code="1827A5" title="非常不满意"
                                                          class="star star1 "></span>
                                                    <span data-id="A4" data-code="1827A4" title="不满意" class="star star2"></span>
                                                    <span data-id="A3" data-code="1827A3" title="一般"
                                                          class="star star3 active"></span>
                                                    <span data-id="A2" data-code="1827A2" title="满意" class="star star4"></span>
                                                    <span data-id="A1" data-code="1827A1" title="非常满意"
                                                          class="star star5 "></span>
                                                </c:if>
                                                 <c:if test="${goods.evaluationVo.score > 3 && goods.evaluationVo.score <= 4}">
                                                     <span data-id="A5" data-code="1827A5" title="非常不满意"
                                                           class="star star1 "></span>
                                                     <span data-id="A4" data-code="1827A4" title="不满意"
                                                           class="star star2"></span>
                                                     <span data-id="A3" data-code="1827A3" title="一般" class="star star3"></span>
                                                     <span data-id="A2" data-code="1827A2" title="满意"
                                                           class="star star4 active"></span>
                                                     <span data-id="A1" data-code="1827A1" title="非常满意"
                                                           class="star star5 "></span>
                                                 </c:if>
                                                     <c:if test="${goods.evaluationVo.score > 4 && goods.evaluationVo.score <= 5}">
                                                     <span data-id="A5" data-code="1827A5" title="非常不满意"
                                                           class="star star1 "></span>
                                                     <span data-id="A4" data-code="1827A4" title="不满意"
                                                           class="star star2"></span>
                                                         <span data-id="A3" data-code="1827A3" title="一般"
                                                               class="star star3"></span>
                                                         <span data-id="A2" data-code="1827A2" title="满意"
                                                               class="star star4 "></span>
                                                     <span data-id="A1" data-code="1827A1" title="非常满意"
                                                           class="star star5  active"></span>
                                                     </c:if>
                                                <span class="star-info">${goods.evaluationVo.score}分</span>
                                                </span>
                                            </div>
                                                <%--           <div class="dt-text clearfix">
                                                               <span class="label">买家印象</span>
                                                               <div class="dt-items clearfix">
                                                                   <div class="item active">质量很好</div>
                                                                   <div class="item">品牌产品</div>
                                                                   <div class="item">商品很实惠</div>
                                                                   <div class="item active">用料正宗</div>
                                                                   <div class="item">一般</div>
                                                                   <div class="item">味道香醇</div>
                                                                   <div class="item">物美价廉</div>
                                                               </div>
                                                           </div>--%>
                                            <div class="dt-append-comment clearfix">
                                                <span class="label">评价晒单</span>
                                                <div class="fop-item pull-left">
                                                    <textarea class="f-textarea" disabled="disabled"
                                                              placeholder="商品是否给力？快分享你的购买心得吧~">${goods.evaluationVo.content}</textarea>
                                                        <%--   <span class="comment-num">30/500</span>--%>
                                                </div>
                                            </div>
                                                <%--   <div class="m-imgshow clearfix">
                                                       <div class="thumbnail-list clearfix">
                                                           <div class="bigimg-switch pull-left clearfix">
                                                               <div class="switch-inner">
                                                                   <img class="bigimg" src="/static/images/index_nav_product_img01.png">
                                                               </div>
                                                               <div class="switch-inner">
                                                                   <img class="bigimg" src="/static/images/index_nav_product_img01.png">
                                                               </div>
                                                           </div>
                                                       </div>
                                                   </div>--%>
                                            <div class="m-imgshow clearfix evaluationvo" data-evaluation-id="${goods.evaluationVo.id}">
                                                <div class="thumbnail-list clearfix">
                                                    <c:forEach items="${goods.evaluationVo.imageUrlList}" var="imageUrl">
                                                        <input type="hidden" class="evaluationVo-${goods.evaluationVo.id}" value="${imageUrl}">
                                                    </c:forEach>
                                                    <div id="evaluation-photo-list-${goods.evaluationVo.id}" style="float: left">
                                                    </div>
                                                    <div style="float: left;">
                                                        <div class="btn-wrap" id="pick-evaluation-photo-${goods.evaluationVo.id}">
                                                                <%--  <img src="" width="100" height="100"/>--%>
                                                        </div>
                                                    </div>
                                                        <%--  <span class="btn-upload"></span>--%>
                                                        <%-- <span class="upload-num">共<span class="red total-num">0</span>张,还能上传<span class="red last-num">10</span>张</span>--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                <%--</c:forEach>--%>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/evaluate/rate.js"></script>
<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>

</html>
