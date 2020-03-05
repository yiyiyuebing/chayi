<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--正常商品--%>
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
        <c:if test="${fn:length(goodPromotionalInfo.activityList) > 1}">
          <div class="favour-wrap clearfix" style="padding-left:0;">
            <label class="pull-left">优惠：</label>
            <div class="favour-box pull-left">
              <ul class="favour-list" id="favourList">
                <c:forEach items="${goodPromotionalInfo.activityList}" var="activity">
                  <li class="clearfix"><i class="pull-left">${activity.tag2}</i><span class="pull-left">${activity.tag2SaleDesc}</span></li>
                </c:forEach>
              </ul>
              <i class="icon-slide down" id="iconSlide"></i>
            </div>
          </div>
        </c:if>
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
        <div class="price clearfix">
          <p>起定量：${goods.startNum}盒起售</p>
          <p>一口价：¥${goods.fixedPrice}</p>
        </div>
      <c:if test="${fn:length(goodPromotionalInfo.activityList) > 1}">
        <div class="favour-wrap clearfix">
          <label class="pull-left">优惠：</label>
          <div class="favour-box pull-left">
            <ul class="favour-list" id="favourList">
              <c:forEach items="${goodPromotionalInfo.activityList}" var="activity">
                <li class="clearfix"><i class="pull-left">${activity.tag2}</i><span class="pull-left">${activity.tag2SaleDesc}</span></li>
              </c:forEach>
            </ul>
            <i class="icon-slide down" id="iconSlide"></i>
          </div>
        </div>
      </c:if>
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
      火爆了！我已被抢光！紧急补货中~
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

      </div>
    </div>
    <div class="look-swiper-btn clearfix">
      <div class="look-prev look-btn disabled pull-left"></div>
      <div class="look-next look-btn pull-right"></div>
    </div>
  </div>
</div>
