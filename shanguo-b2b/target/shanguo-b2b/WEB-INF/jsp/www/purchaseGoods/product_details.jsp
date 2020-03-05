<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${goods.purGoodsName}【茶叶_茶具_茶礼_采购_批发_进货_正品】-优茶联</title>
    <meta name="keyword" content="${goods.purGoodsName}"/>
    <meta name="description" content="完善的茶叶商城,采购商城，茶具商城，茶具采购商城，品质保证,优质的铁观音、大红袍、普洱茶、红茶、绿茶、白茶、袋泡茶、礼品茶、茶礼、茶具、茶食品,尽在优茶联."/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"  href="/static/css/product_details.css">
    <link rel="stylesheet" href="/static/js/lib/kkpager/css/kkpager.css">
    <script>
        var purchaseGoodsId = "${goods.id}";
        var purchaseImage = "${goods.showImages.images[0].url}";
        var groupId = "${goods.groupId}";
        var isSancha = "${goods.isSancha}";
    </script>
</head>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/search_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--导航栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_top_bar.jsp"></jsp:include>
<%--导航栏 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>


<body>

<div class="product-bread-nav clearfix">
    <span class="pull-left">当前位置：</span>
    <a class="pull-left" href="/purchase">商城</a>
    <i class="pull-left"> > </i>
    <%--<a class="pull-left" href="#">铁观音</a>--%>
    <%--<i class="pull-left"> > </i>--%>
    <a class="pull-left" href="/purchase/detail/${goods.id}.html"><span class="goodName">${goods.purGoodsName}</span></a>
    <input type="hidden" class="groupId" value="${goods.groupId}">
    <input type="hidden" class="goodId" name="product-id" value="${goods.id}">
    <input type="hidden" name="product-name" value="${goods.purGoodsName}">
    <input type="hidden" name="product-desc" value="${goods.purSubtitle}">
    <input type="hidden" name="product-price" value="${goods.minSupplyPrice}">
    <input type="hidden" name="product-img" value="${goods.showImages.images[0].url}">
</div>
<div class="product-info clearfix">
    <div class="product-zoom-wrap pull-left">
        <div class="product-img">
            <c:if test="${!empty goods.showImages.images}">
                <a href="${goods.showImages.images[0].url}" class="jqzoom" rel="gal">
                    <img class="spic"  id="spic" src="${goods.showImages.images[0].url}">
                </a>
            </c:if>
            <%--<i class="zoom-icon"></i>--%>
        </div>
        <div class="product-swiper clearfix">
            <div class="swiper-btn-prev pull-left"></div>
            <div class="swiper-container pull-left">
                <div class="swiper-wrapper">
                    <c:forEach items="${goods.showImages.images}" var="img">
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '${img.url}',largeimage: '${img.url}'}">
                                <img src='${img.url}'>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="swiper-btn-next pull-right"></div>
        </div>
        <div class="product-share clearfix">
            <%--<span class="pull-left" id="shareBtn"><i class="product-share-icon"></i>分享</span>--%>
            <span class="pull-left" onclick="PurchaseGoodsDetail.addToFavorite(this)"><i class="product-collect-icon product-collect-${goods.id}"></i>收藏（<span class="favorite-count" style="margin-right:0px;">0</span>）</span>
        </div>
    </div>
    <div class="product-center pull-left">
        <form>
            <h4>
                <c:if test="${!empty goods.label}">
                    <i>${goods.label}</i>
                </c:if>
            ${goods.purGoodsName}</h4>
            <c:if test="${!empty goods.purSubtitle}"><h3>${goods.purSubtitle}</h3></c:if>
            <c:if test="${goods.isSancha == 'T'}">
                <div id="sancha">
                    <table border="0" cellspacing="0">
                        <tbody>
                        <tr>
                            <td class="sancha-one">供货价：</td>
                            <td class="sancha-two redTxt">￥${goods.goodSkuVoList[0].supplyPriceList[0].supplyPrice}</td>
                            <td class="sancha-three redTxt">￥${goods.goodSkuVoList[0].supplyPriceList[1].supplyPrice}</td>
                            <td class="sancha-four redTxt">￥${goods.goodSkuVoList[0].supplyPriceList[2].supplyPrice}</td>
                            <td class="sancha-five"><span>自营</span></td>
                        </tr>
                        <tr>
                            <td class="sancha-one">起批量：</td>
                            <td class="sancha-two">${goods.goodSkuVoList[0].supplyPriceList[0].sectionStart}-${goods.goodSkuVoList[0].supplyPriceList[0].sectionEnd}斤</td>
                            <td class="sancha-three">${goods.goodSkuVoList[0].supplyPriceList[1].sectionStart}-${goods.goodSkuVoList[0].supplyPriceList[1].sectionEnd}斤</td>
                            <td class="sancha-four">
                                <c:if test="${!empty goods.goodSkuVoList[0].supplyPriceList[2].sectionEnd}">
                                    ${goods.goodSkuVoList[0].supplyPriceList[2].sectionStart}-${goods.goodSkuVoList[0].supplyPriceList[2].sectionEnd}
                                </c:if>
                                <c:if test="${empty goods.goodSkuVoList[0].supplyPriceList[2].sectionEnd}">
                                    >${goods.goodSkuVoList[0].supplyPriceList[2].sectionStart}
                                </c:if>
                                斤</td>
                            <td class="sancha-five"></td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="sancha-price">
                        零售价：￥${goods.fixedPrice}
                    </div>
                </div>
            </c:if>
            <c:if test="${goods.isSancha != 'T'}">
                <div class="product-txt">
                    <div class="price clearfix">
                        <span class="price-left pull-left">
                        供货价：<mark>
                            <c:if test="${empty shopId}">
                                询价
                            </c:if>
                            <c:if test="${!empty shopId}">
                                <c:if test="${fn:length(goods.goodSkuVoList) > 1}">
                                    ¥${goods.minSupplyPrice}-${goods.maxSupplyPrice}
                                </c:if>
                                <c:if test="${fn:length(goods.goodSkuVoList) <= 1}">
                                    ¥${goods.minSupplyPrice}
                                </c:if>
                            </c:if>

                        </mark>
                        </span>
                        <span class="price-right pull-right">自营</span>
                    </div>
                    <p>起定量：${goods.startNum}盒起售</p>
                    <p>一口价：¥${goods.fixedPrice}</p>
                </div>
            </c:if>
            <div class="group express clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
                <div class="pull-left title">
                    物流
                </div>
                <div class="express-content pull-left clearfix">
                    <span class="pull-left express-start">福建厦门</span>
                    <i class="pull-left express-to">至</i>
                    <div class="express-wrap pull-left">
                        <span class="location-choose pull-left" id="locationChoose">请选择<i></i></span>
                        <div class="detail-localtion" id="detail-location-select">
                        </div>
                    </div>
                    <span class="pull-left express-cost">免运费</span>
                </div>
            </div>
            <div class="group salesVolume clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
                <div class="pull-left title">
                    销量/评价
                </div>
                <div class="sailsVolume-content clearfix">
                    <span class="pull-left"><mark>${goods.totalSaleNum}</mark>笔成交</span>
                    <span class="pull-left evaluate-total-count"><mark>0</mark>条评价</span>
                    <i class="pull-left sailsVolume-line"></i>
                    <div class="star-mask" style="width: 93.4%;"></div>
                    <i class="star05 pull-left"></i>
                </div>
            </div>
            <%--<div class="group server clearfix">--%>
                <%--<div class="pull-left title">--%>
                    <%--销量/评价--%>
                <%--</div>--%>
                <%--<div class="server-content pull-left clearfix">--%>
                    <%--<a class="pull-left" href="#">代包</a>--%>
                <%--</div>--%>
            <%--</div>--%>


            <div class="group size clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
                <div class="pull-left title">
                    规格：
                </div>
                <div class="pull-left">
                    <table class="size-content">
                        <tbody class="purchase-goods-sku-list">
                        <c:forEach items="${goods.goodSkuVoList}" var="sku">
                            <%--<fmt:formatNumber value="${sku.onSalesNo}" var="skuSalesNo"/>--%>
                            <%--<fmt:formatNumber value="${sku.cargoSkuStock}" var="cargoSkuStock"/>--%>
                            <c:set var="skuSalesNo" value="${sku.onSalesNo}"></c:set>
                            <c:set var="cargoSkuStock" value="${sku.cargoSkuStock}"></c:set>
                            <c:if test="${cargoSkuStock <= skuSalesNo}">
                                <c:set var="skuSalesNo" value="${cargoSkuStock}"></c:set>
                            </c:if>

                            <c:if test="${cargoSkuStock >= skuSalesNo}">
                                <c:set var="skuSalesNo" value="${skuSalesNo}"></c:set>
                            </c:if>
                            <c:if test="${skuSalesNo < 0}">
                                <c:set var="skuSalesNo" value="0"></c:set>
                            </c:if>

                            <tr data-sku-id="${sku.id}" data-sku-price="${sku.supplyPrice}">
                                <td class="td-fontw">${sku.cargoSkuName}</td>

                                <c:if test="${empty shopId}">
                                    <td class="sku-supply-price">询价</td>
                                </c:if>
                                <c:if test="${!empty shopId}">
                                    <td class="sku-supply-price">${sku.supplyPrice}元</td>
                                </c:if>

                                <td><p style="text-align: center">${skuSalesNo}</p><p style="text-align: center">可售</p>
                                </td>
                                <td class="input-count clearfix">
                                    <c:if test="${skuSalesNo > 0}">
                                        <c:choose>
                                            <c:when test="${sku.mulNumFlag == 'T' && skuSalesNo >= sku.startNum}">
                                                <a href="javascript:void(0)"  class="subtraction pull-left">-</a>
                                                <input class="pull-left select-count" readonly type="text" data-good-name="${goods.purGoodsName}（${sku.cargoSkuName}）"
                                                       data-good-id="${goods.id}"
                                                       data-max-num="${skuSalesNo}" data-sku-id="${sku.id}"
                                                       data-sku-price="${sku.supplyPrice}" value="0"
                                                       data-sku-start-num="${sku.startNum}"
                                                       data-sku-numflag="${sku.mulNumFlag}"
                                                       name="amount01">
                                                <a href="javascript:void(0)" class="plus pull-left" data-max-num="${skuSalesNo}">+</a>
                                            </c:when>
                                            <c:when test="${sku.mulNumFlag == 'F'}">
                                                <a href="javascript:void(0)"  class="subtraction pull-left">-</a>
                                                <input class="pull-left select-count" readonly type="text" data-good-name="${goods.purGoodsName}（${sku.cargoSkuName}）"
                                                       data-good-id="${goods.id}"
                                                       data-max-num="${skuSalesNo}" data-sku-id="${sku.id}"
                                                       data-sku-price="${sku.supplyPrice}" value="0"
                                                       data-sku-start-num="${sku.startNum}"
                                                       data-sku-numflag="${sku.mulNumFlag}"
                                                       name="amount01">
                                                <a href="javascript:void(0)" class="plus pull-left" data-max-num="${skuSalesNo}">+</a>
                                            </c:when>
                                            <c:otherwise>
                                                缺货
                                            </c:otherwise>
                                        </c:choose>

                                        <%--<c:if test="${goods.isSancha == 'T'}">--%>
                                            <%--<c:forEach items="${sku.supplyPriceList}" var="supplyPriceItem">--%>
                                                <%--<input type="hidden" name="sancha-price" data-price="${supplyPriceItem.supplyPrice}" data-section-start="${supplyPriceItem.sectionStart}" data-section-end="${supplyPriceItem.sectionEnd}">--%>
                                            <%--</c:forEach>--%>
                                        <%--</c:if>--%>

                                    </c:if>
                                    <c:if test="${skuSalesNo <= 0}">
                                        缺货
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="group btn-group  <c:if test='${totalStock <= 0}'>hide</c:if>">
                <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.createPreOrder()" class="buy">立即购买</a>
                <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.addToCart()" class="shopping-car"><i></i>加入购物车</a>
            </div>
            <div class="group pay clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
                <div class="pull-left title">
                    服务保障
                </div>
                <div class="pay-content clearfix">
                    <a href="javascript:void(0);" class="promise-link pull-left"><i class="promise-brand"></i>正品保障</a>
                    <a href="javascript:void(0);" class="express-link pull-left"><i class="time24"></i>48小时发货</a>
                    <a href="javascript:void(0);" class="change-link pull-left"><i class="change15"></i>客服全程跟踪</a>
                </div>
            </div>
            <div class="group pay clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
                <div class="pull-left title">
                    支付方式
                </div>
                <div class="promise-content clearfix">
                    <a href="javascript:void(0);" class="change-link pull-left">支付宝</a>
                    <a href="javascript:void(0);" class="express-link pull-left">微信</a>
                    <%--<a href="#" class="promise-link pull-left">信用卡</a>--%>
                    <%--<a href="#" class="promise-link pull-left">网上银行</a>--%>
                </div>
            </div>

            <div class="off-sale-txt clearfix <c:if test='${totalStock > 0}'>hide</c:if>">
                亲，我被抢光啦！紧急补货中~
            </div>
            <div class="group btn-group  <c:if test='${totalStock > 0}'>hide</c:if>">
                <a href="javascript:void(0);" onclick="history.go(-1);" class="buy">继续逛逛</a>
            </div>

        </form>
    </div>
    <div class="product-right pull-right">
        <div class="product-right-title">
            <i class="pull-left"></i><span class="pull-left">看一看</span><i class="pull-right"></i>
        </div>
        <div class="look">
            <div class="look-swiper">
                <div class="swiper-wrapper product-rigth-goods">
                    <%--<div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img-wrap">
                                <img src="/static/images/product_list_recommend_img.png">
                                <span>¥200.00</span>
                            </div>
                        </a>
                    </div>--%>
                </div>
            </div>
            <div class="look-swiper-btn clearfix">
                <div class="look-prev look-btn disabled pull-left"></div>
                <div class="look-next look-btn pull-right"></div>
            </div>
        </div>
    </div>
</div>
<!--分享弹窗-->
<div class="popup-wrap" id="popup">
    <form>
        <div class="popup-bg">
            <div class="popup-box">
                <div class="pop-header clearfix">
                    <span class="pull-left">分享一下</span>
                    <i class="pop-close pull-right" id="popClose"></i>
                </div>
                <div class="pop-body">
                    <h6>说说这个商品怎么样</h6>
                    <div class="text-area clearfix">
                        <textarea id="shareTxt" name="description" maxlength="72" placeholder="赶紧看看"></textarea>
                        <span class="pull-right">还可以输入<i class="number-limit">72</i>个字</span>
                    </div>
                    <h6>选择配图</h6>
                    <div class="share-swiper-wrap clearfix">
                        <div class="share-img-prev pull-left"><i></i></div>
                        <div class="share-swiper pull-left" id="shareImg">
                            <div class="swiper-wrapper pull-left">
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img01">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img02">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img03">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="/static/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="share-img-next pull-right"><i></i></div>
                    </div>
                    <div class="share-position clearfix">
                        <span class="pull-left">分享到</span>
                        <div class="share-position-input pull-left clearfix" id="shareTo">
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="qqZone">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_qqZone.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="weiblog">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_weiblog.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="share03">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_share03.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="weichart">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_weichart.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="renren">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_renren.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="share06">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="/static/images/icon_share06.png">
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="share-submit">
                        <input type="submit" value="分享">
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<c:if test="${goods.isSample != '0'}">
    <!--免费拿样-->
    <div class="sample clearfix">
        <div class="sample-txt01 pull-left">
            <span>立即拿样</span>
            <i>进货先拿样，批发有保障</i>
        </div>
        <div class="sample-txt02 pull-left">
            拿样支付后15天内，二次店铺进货2800元还样品费。样品随
            机发货，不携色，挑码，且每个买家限购1件。<%--<a href="#">拿样规则</a>--%>
        </div>
        <div class="sample-txt03 pull-left clearfix">
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">拿样价</label>
                <span class="pull-left"><mark>${goods.sample.samplePrice}</mark>元/${goods.sample.unit}</span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">起订量</label>
                <span class="pull-left">${goods.sample.startNum}${goods.sample.unit}</span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">样品规格</label>
                <span class="pull-left"><mark>${goods.sample.sampleSku}</mark></span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">运费</label>
                <span class="pull-left">与产品运费一致</span>
            </div>
        </div>
        <div class="sample-btn pull-right">
            <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.createSampleOrder(this)" data-sku-id="<c:if test='${!empty goods.goodSkuVoList}'>${goods.goodSkuVoList[0].id}</c:if>" data-good-id="${goods.id}" data-start-num="${goods.sample.startNum}">立即拿样</a>
        </div>
    </div>
</c:if>


<!--产品介绍-->
<div class="product-intro clearfix">
    <div class="intro-aside pull-left">
        <%--左部页面模版内容--%>
    </div>
    <div class="intro-section pull-right">

        <%--<div class="intro-recommend">
            <h6>商品评审</h6>
            <div class="intro-recommend-wrap clearfix">
                <div class="intro-recommend-group pull-left clearfix">
                    <span class="taste pull-left">香气香</span>
                    <div class="taste-content pull-left">
                        <i class="icon-taste01"></i>
                        <!--<i class="icon-taste02"></i>
                        <i class="icon-taste03"></i>
                        <i class="icon-taste04"></i>
                        <i class="icon-taste05"></i>-->
                        <p>花香扬而不腻，持久耐久耐冲泡</p>
                    </div>
                </div>
                <div class="intro-recommend-group pull-left clearfix">
                    <span class="smell pull-left">滋味浓</span>
                    <div class="smell-content pull-left">
                        <i class="icon-smell01"></i>
                        <!--<i class="icon-smell02"></i>
                        <i class="icon-smell03"></i>
                        <i class="icon-smell04"></i>
                        <i class="icon-smell05"></i>-->
                        <p>茶水回甘有力度，持久耐冲泡</p>
                    </div>
                </div>
                <div class="intro-recommend-group pull-left clearfix">
                    <span class="intro-reason pull-left">推荐理由</span>
                    <div class="intro-reason-txt pull-left">
                        原始茶原料，根据全发酵工艺，原始自然生长，无受外界污染环境，茶水的甜感和香气的清幽，显得更有性价比。
                    </div>
                </div>
            </div>
        </div>--%>

        <div class="intro-title clearfix" id="introTitle">
            <span class="intro-title-active pull-left">详情信息</span>
            <span class="pull-left intro-evaluat-count">评价（0）</span>
            <span class="pull-left">售后保障</span>
            <%--<span class="pull-left">订购说明</span>--%>
        </div>
        <div class="intro-content">
            <div class="intro-content-group recommend-details clearfix">
                <ul class="intro-info clearfix">
                </ul>
                <div class="center-xqad hide"></div>

                <div class="product-info-img">
                    ${goods.pcDetailInfo}
                </div>
                <div class="bottom-xqad hide"></div>
            </div>
            <div class="intro-content-group recommend-list" style="display: none;">
                <div class="product-info-recommend">
                    <h5>商品评价</h5>
                    <div class="product-recommend-star clearfix">
                        <span class="pull-left" id="avgScore">0</span>
                        <div class="star-wrap recommend-star  pull-left">
                            <div class="star-mask" style="width: 20%;"></div>
                            <i class="recommend-star05 pull-left clearfix" data-star="0"></i>
                        </div>
                        <%--<i class="recommend-star recommend-star01 pull-left"></i>--%>
                    </div>
                    <div class="recommend-info">
                        <div class="recommend-info-title clearfix">
                            <a class="recommend-info-title-active pull-left" data-type="1" href="javascript:void(0);"> 全部评论（<span id="totalCount"></span>） </a>
                            <a href="javascript:void(0);" data-type="2" class="pull-left"> 晒图（<span id="imageCount"></span>）</a>
                            <a href="javascript:void(0);" data-type="3" class="pull-left"> 好评（<span id="goodCount"></span>）</a>
                            <a href="javascript:void(0);" data-type="4" class="pull-left"> 中评（<span id="middleCount"></span>）</a>
                            <a href="javascript:void(0);" data-type="5" class="pull-left"> 差评（<span id="badCount"></span>）</a>
                        </div>
                        <div class="recommend-info-content clearfix">
                            <ul class="recommend-info-content-list">

                            </ul>
                            <div id="kkpager"></div>
                        </div>
                    </div>
                    <%--<a class="intro-info-banner" style="margin-bottom: 20px" href="#">--%>
                        <%--<img src="/static/images/product_details_banner_img03.png">--%>
                    <%--</a>--%>
                </div>
            </div>
            <div class="intro-content-group recommend-details" style="display: none;">
                <div>
                    ${goods.afterSell}
                </div>
            </div>
            <%--<div class="intro-content-group ordering" style="display: none;">
                <div class="ordering-group">
                    <h4>批发说明</h4>
                    <div class="ordering-line"></div>
                    <h5>供应商支持混批，500元以上起批，或者6斤以上起批。</h5>
                    <div class="ordering-txt">
                        <h6>什么是混批？</h6>
                        <p>是指不限产品的种类和样式，买家只要采购总价（或总量）达到或高于设置金额（或数量）即可享受批发价格。</p>
                    </div>
                </div>
                <div class="ordering-group">
                    <h4>交易流程</h4>
                    <div class="ordering-line"></div>
                    <div class="clearfix">
                        <div class="ordering-img pull-left">
                            <img src="/static/images/icon_alipay.png">
                        </div>
                        <div class="ordering-txt ordering-pay pull-left">
                            <h6>付款方式：</h6>
                            <p>买家先选择支付方式（路网上银行、快捷支付、支付宝余额等）付款到支付宝，支付宝担保货款安全；但买家收到货品并确认后，再由支付宝打款给供应商。如逾期未收到商品或商品不符合要求，买家可以提出退款申请以保障自身权益。</p>
                        </div>
                    </div>
                    <div class="ordering-route clearfix">
                        <h5 class="ordering-route-title">使用支付宝付款流程</h5>
                        <div class="ordering-route-img clearfix">
                            <span class="pull-left">1 选择商品</span>
                            <i class="icon-arrows-right pull-left"></i>
                            <span class="pull-left">2 确认订单信息</span>
                            <i class="icon-arrows-right pull-left"></i>
                            <span class="pull-left">3 选择付款方式并付款</span>
                            <i class="icon-arrows-right pull-left"></i>
                            <span class="pull-left">4 收到货并验货满意</span>
                            <i class="icon-arrows-right pull-left"></i>
                            <span class="pull-left">5 确认收货</span>
                            <i class="icon-arrows-right pull-left"></i>
                            <span class="pull-left">6 交易成功</span>
                        </div>
                    </div>
                </div>
                <div class="ordering-group">
                    <h4>保障说明</h4>
                    <div class="ordering-line"></div>
                    <div class="ordering-imgTxt clearfix">
                        <div class="ordering-img pull-left">
                            <img src="/static/images/icon_promise.png">
                        </div>
                        <div class="ordering-txt pull-left">
                            <h6>买家保障：</h6>
                            <p>卖家缴纳保证金为买家提供交易保障，若卖家发生违约或不诚信行为，买家可获得先行赔付。<a href="javascript:void(0);">了解详情 》</a></p>
                        </div>
                    </div>
                    <div class="problem-route clearfix">
                        <img src="/static/images/product_promise_router_img.png">
                    </div>
                </div>
            </div>--%>
        </div>
    </div>
</div>

<div class="bottom-product-list hide">

</div>

<%--<div class="product-may-like">
    <div class="product-may-like-header clearfix">
        <span class="may-like-left pull-left">猜你喜欢</span>
        <a class="may-like-right pull-right"><i></i>换一批</a>
    </div>
    <ul class="may-like-product clearfix">
        <li class="pull-left">
            <div class="product-wrap">
                <a href="product_details.html" class="may-like-img">
                    <img src="../images/product_list_aside_img.png">
                </a>
                <a href="product_details.html" class="may-like-txt">
                    2017新茶云雾绿茶4盒共500克一杯盒.....
                </a>
                <span class="price"><sub>¥</sub>200.00</span>
                <span class="sails-number">销量：2345笔</span>
            </div>
        </li>
        <li class="pull-left">
            <div class="product-wrap">
                <a href="product_details.html" class="may-like-img">
                    <img src="../images/product_list_aside_img.png">
                </a>
                <a href="product_details.html" class="may-like-txt">
                    2017新茶云雾绿茶4盒共500克一杯盒.....
                </a>
                <span class="price"><sub>¥</sub>200.00</span>
                <span class="sails-number">销量：2345笔</span>
            </div>
        </li>
        <li class="pull-left">
            <div class="product-wrap">
                <a href="product_details.html" class="may-like-img">
                    <img src="../images/product_list_aside_img.png">
                </a>
                <a href="product_details.html" class="may-like-txt">
                    2017新茶云雾绿茶4盒共500克一杯盒.....
                </a>
                <span class="price"><sub>¥</sub>200.00</span>
                <span class="sails-number">销量：2345笔</span>
            </div>
        </li>
        <li class="pull-left">
            <div class="product-wrap">
                <a href="product_details.html" class="may-like-img">
                    <img src="../images/product_list_aside_img.png">
                </a>
                <a href="product_details.html" class="may-like-txt">
                    2017新茶云雾绿茶4盒共500克一杯盒.....
                </a>
                <span class="price"><sub>¥</sub>200.00</span>
                <span class="sails-number">销量：2345笔</span>
            </div>
        </li>
        <li class="pull-left">
            <div class="product-wrap">
                <a href="product_details.html" class="may-like-img">
                    <img src="../images/product_list_aside_img.png">
                </a>
                <a href="product_details.html" class="may-like-txt">
                    2017新茶云雾绿茶4盒共500克一杯盒.....
                </a>
                <span class="price"><sub>¥</sub>200.00</span>
                <span class="sails-number">销量：2345笔</span>
            </div>
        </li>
    </ul>
</div>--%>

</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>


<script src="/static/js/lib/kkpager/kkpager.js"></script>
<script src="/static/js/purchaseGoods/product_details.js"></script>

<script id="tpl-details-evaluat" type="text/html">


    <li class="clearfix">
        <div class="recommend-userInfo pull-left clearfix">
            <div class="recommend-userImg pull-left">
                {{if headImgUrl}}
                <img src="{{headImgUrl}}">
                {{else}}
                <img src="/static/images/youchalian.png">
                {{/if}}
            </div>
            <span class="recommend-userName pull-left">{{userName}}</span>
        </div>
        <div class="recommend-userTxt pull-left">
            <div class="star-wrap recommend-userTxt-star">
                <div class="star-mask" style="width: {{score / 5 * 100}}%;"></div>
                <i class="recommend-star05 pull-left clearfix" data-star="0"></i>
            </div>
            <div class="recommend-txt">{{content}}</div>
            <ul class="recommend-img-list clearfix" id="imglist{{id}}">

                {{if images && images.images}}
                    {{each images.images as img}}
                        <li class="pull-left">
                            <img src="{{img.url}}">
                        </li>
                    {{/each}}
                {{/if}}

            </ul>
            <div class="bigImgWrap" data-target="bigImg{{id}}">
                <div class="bigImg">
                    <img class="showImg" src="">
                    <div class="cursorBtn imgPrev"></div>
                    <div class="cursorBtn imgHide"></div>
                    <div class="cursorBtn imgNext"></div>
                </div>
            </div>
            <div class="recommend-product-info">
                <span>{{goodName}}</span>
                <%--<span>1</span>--%>
                <span>{{evaluateTime}}</span>
            </div>
            <div class="recommend-responseTxt">
                {{if repliedList}}
                    {{each repliedList as reply}}
                        解释：{{reply.content}}
                    {{/each}}
                {{/if}}
            </div>
        </div>
    </li>
</script>

</html>

<script type="text/html" id="tpl-left-ad">
    <a href="{{adUrl}}" class="intro-aside-header">
        <img src="{{adImgUrl}}">
    </a>
</script>

<script type="text/html" id="tpl-info-banner-1">
    {{#detail}}
</script>

<script type="text/html" id="tpl-info-banner-2">
    {{#detail}}
</script>

<script type="text/html" id="tpl-left-product-list">
    <div class="intro-aside-body">
        <h6>{{modelName}}</h6>
        {{each goodList as good index}}
            <div class="intro-aside-group">
                <a class="intro-aside-img" href="/purchase/detail/{{good.id}}.html">
                    {{if good.image && good.image.url}}
                    <img src="{{good.image.url}}">
                    {{else}}
                    <img src="/static/images/product_list_aside_img.png">
                    {{/if}}

                </a>
                <a class="intro-aside-txt" href="/purchase/detail/{{good.id}}.html">
                    {{good.name}}
                </a>
                        <span class="intro-aside-price">
                            {{if shopId}}
                              供货价：¥{{good.price}}
                            {{else}}
                              供货价：询价
                            {{/if}}
                        </span>
                        <span class="intro-aside-sale">
                            {{if good.saleNum}}
                                销量：{{good.saleNum}}笔
                            {{/if}}
                        </span>
            </div>
        {{/each}}
    </div>
</script>
<%--右侧看一看--%>
<script id="tpl-right-slide" type="text/html">
    <div class="swiper-slide">
        <a href="/purchase/detail/{{id}}.html">
            <div class="img-wrap">
                {{if image && image.url}}
                    <img src="{{image.url}}">
                {{else}}
                    <img src="/static/images/product_list_recommend_img.png">
                {{/if}}
                <span>¥
                    {{if shopId}}
                       {{price}}
                    {{else}}
                      询价
                    {{/if}}
                </span>
            </div>
        </a>
    </div>
</script>

<%--底部模版--%>
<script id="tpl-bottom-tpl-dsp" type="text/html">
    <div class="product-may-like">
        <div class="product-may-like-header clearfix">
            <span class="may-like-left pull-left">{{modelName}}</span>
            <%--<a class="may-like-right pull-right"><i></i>换一批</a>--%>
        </div>
        <ul class="may-like-product clearfix">
            {{each goodList as good index}}
            {{if index < 5}}
            <li class="pull-left">
                <div class="product-wrap">
                    <a href="/purchase/detail/{{good.id}}.html" class="may-like-img">
                        {{if good.image && good.image.url}}
                        <img src="{{good.image.url}}">
                        {{else}}
                        <img src="/static/images/product_list_aside_img.png">
                        {{/if}}
                    </a>
                    <a href="/purchase/detail/{{good.id}}.html" class="may-like-txt">
                        {{good.name}}
                    </a>
          <span class="price"><sub>¥</sub>
            {{if shopId}}
              {{good.price}}
            {{else}}
              询价
            {{/if}}
          </span>
          <span class="sails-number">
            {{if good.saleNum}}
              销量：{{good.saleNum}}笔
            {{else}}
              销量：0笔
            {{/if}}
          </span>
                </div>
            </li>
            {{/if}}
            {{/each}}
        </ul>
    </div>
</script>