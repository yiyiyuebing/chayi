<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css"  href="/static/css/good_sale.css">
<% Date nowDate = new Date(); request.setAttribute("nowDate", nowDate); %>
<fmt:formatDate value="${goodPromotionalInfo.activityEnd}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="activityEnd"/>
<fmt:formatDate value="${goodPromotionalInfo.activityStart}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="activityStart"/>

<%--促销商品--%>
<div class="product-info clearfix">
  <div class="product-center-sale pull-left">
    <form>
      <h4>
        <c:if test="${!empty goods.label}">
          <i>${goods.label}</i>
        </c:if>
        ${goods.purGoodsName}
      </h4>
      <c:if test="${!empty goods.purSubtitle}"><p class="product-words">${goods.purSubtitle}</p></c:if>
      <div class="product-txt">

        <c:if test="${nowDate > activityStart}" var="na"/>
        <%--<c:if test="${!na}">--%>
        <div class="product-txt-title clearfix presell-close hide" style="background: #31ae28;">
          <div class="txt-title-left pull-left">
            <i class="presell-clock"></i>促销
          </div>
          <div class="txt-title-right pull-right">
            <span><i class="icon-clock"></i>距离活动开始</span><span class="activity-start-time"></span>
          </div>
        </div>
        <%--</c:if>--%>
        <%--<c:if test="${na}">--%>
        <div class="product-txt-title clearfix presell-normal hide">
          <div class="txt-title-left pull-left">
            <i></i>促销
          </div>
          <div class="txt-title-center pull-left">
            <i class="icon-presell"></i><mark>${goodPromotionalInfo.saleNum + goodPromotionalInfo.vmcount}</mark>人抢订
          </div>
          <div class="txt-title-right pull-right">
            <span><i class="icon-clock"></i>距活动结束</span><span class="activity-end-time"></span>
          </div>
        </div>
        <%--</c:if>--%>

        <div class="product-txt-content">
          <div class="product-orginal-price clearfix">
            <label class="pull-left">价&nbsp;&nbsp;&nbsp;格：</label>
            <c:if test="${fn:length(goods.goodSkuVoList) > 1}">
              <span class="pull-left">¥${goods.minSupplyPrice} - ${goods.maxSupplyPrice}<i></i></span>
            </c:if>
            <c:if test="${fn:length(goods.goodSkuVoList) <= 1}">
              <span class="pull-left">¥${goods.minSupplyPrice}<i></i></span>
            </c:if>
          </div>
          <div class="product-txt-group-price clearfix">
            <label class="pull-left">促销价：</label>
            <c:if test="${empty shopId}">
              <span class="pull-left">询价</span>
            </c:if>
            <c:if test="${!empty shopId}">
              <c:if test="${goodPromotionalInfo.endPrice == goodPromotionalInfo.startPrice}">
                <span class="pull-left">¥${goodPromotionalInfo.startPrice}<mark><i></i>${goodPromotionalInfo.activeName}</mark></span>
              </c:if>
              <c:if test="${goodPromotionalInfo.endPrice != goodPromotionalInfo.startPrice}">
                <span class="pull-left">¥${goodPromotionalInfo.startPrice} - ${goodPromotionalInfo.endPrice}<mark><i></i>${goodPromotionalInfo.activeName}</mark></span>
              </c:if>
            </c:if>

          </div>
          <div class="product-txt-group-state clearfix">
            <label class="pull-left">促&nbsp;&nbsp;&nbsp;销：</label>
            <span class="pull-left"><i>${goodPromotionalInfo.tag}</i>${goodPromotionalInfo.activityDesc}</span>
          </div>
          <c:if test="${fn:length(goodPromotionalInfo.activityList) > 1}">
            <div class="product-txt-group-state clearfix">
              <label class="pull-left">优&nbsp;&nbsp;&nbsp;惠：</label>
              <span class="pull-left">
                <c:forEach items="${goodPromotionalInfo.activityList}" var="activity">
                  <c:if test="${activity.activityType != 'sale'}">
                    <div><i>${activity.tag2}</i>${activity.tag2SaleDesc}</div>
                  </c:if>
                </c:forEach>
              </span>
            </div>
          </c:if>
        </div>
      </div>

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

      <div class="group size clearfix <c:if test='${totalStock <= 0}'>hide</c:if>">
        <div class="pull-left title">
          规格：
        </div>
        <div class="pull-left">
          <table class="size-content">
            <tbody class="purchase-goods-sku-list">
            <c:forEach items="${goods.goodSkuVoList}" var="sku">
              <tr data-sku-id="${sku.id}" data-sku-price="${sku.supplyPrice}">
                <td class="td-fontw">${sku.cargoSkuName}</td>
                <c:if test="${empty shopId}">
                  <td class="td-price sku-supply-price">询价</td>
                </c:if>
                <c:if test="${!empty shopId}">
                  <td class="td-price sku-supply-price">${sku.supplyPrice}元</td>
                </c:if>
                <td class="sale-num">${sku.stock}<br>可售</td>
                <td class="input-count clearfix">
                  <c:if test="${sku.onSalesNo > 0 && sku.onSalesNo > sku.startNum}">
                    <a href="javascript:void(0)"  class="subtraction pull-left">-</a>
                    <input class="pull-left select-count" type="text" data-good-id="${goods.id}"
                           data-max-num="${sku.onSalesNo}" data-sku-id="${sku.id}"
                           data-sku-price="${sku.supplyPrice}" readonly value="0" name="amount01"
                           data-sku-start-num="${sku.startNum}"
                           data-sku-numflag="${sku.mulNumFlag}"
                           data-limt-num="${sku.limitNum}" data-limt-flag="${sku.limitFlg}"
                            >
                    <a href="javascript:void(0)" class="plus pull-left" data-limt-num="${sku.limitNum}" data-limt-flag="${sku.limitFlg}" data-max-num="${sku.onSalesNo}">+</a>
                  </c:if>
                  <c:if test="${sku.onSalesNo <= 0 || sku.onSalesNo < sku.startNum}">
                    缺货
                  </c:if>
                </td>
                <td class="limit-sale">
                  <c:if test="${sku.limitFlg == 'T'}">
                    （限购数量：${sku.limitNum}${sku.limitUnit}/人）
                  </c:if>
                  <c:if test="${(empty sku.limitFlg || sku.limitFlg != 'T') && sku.mulNumFlag == 'T'}">
                    （起订量：${sku.startNum}）
                  </c:if>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
      <div class="group btn-group <c:if test='${totalStock <= 0}'>hide</c:if>">
        <%--<c:if test="${goodPromotionalInfo.activityStartFlag}">--%>
          <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.createPreOrder()" class="buy">立即购买</a>
          <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.addToCart()" class="shopping-car"><i></i>加入购物车</a>
        <%--</c:if>
        <c:if test="${!goodPromotionalInfo.activityStartFlag}">
          <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.createPreOrder()" class="buy btn-disable">立即购买</a>
          <a href="javascript:void(0);" onclick="PurchaseGoodsDetail.addToCart()" class="shopping-car"><i></i>加入购物车</a>
        </c:if>--%>

      </div>
      <div class="off-sale-txt <c:if test='${totalStock > 0}'>hide</c:if>">
        火爆了！我已被抢光！紧急补货中~
      </div>
      <div class="group btn-group <c:if test='${totalStock > 0}'>hide</c:if>">
        <a href="javascript:void(0);" onclick="history.go(-1);" class="buy">继续逛逛</a>
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
    </form>
  </div>
</div>