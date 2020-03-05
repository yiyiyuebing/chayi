<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/12
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单明细-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>

  <link rel="stylesheet" href="/static/css/order_information.css">
</head>
<body>
  <%--头部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
  <%--头部 ending--%>
  <%--${orderInfo.orderListVos[0].number}--%>
  <div id="orderInfo">
    <c:forEach items="${orderInfo.orderListVos}" var="goodSku">
      <input id="ipt-${goodSku.purGoodsSkuId}" type="hidden" name="skuInfo" data-sku-id="${goodSku.purGoodsSkuId}" data-number="${goodSku.number}" data-isSample="${goodSku.isSample}" data-goods-id="${goodSku.purGoodsId}"/>
    </c:forEach>
    <input type="hidden" name="province">
    <input type="hidden" name="city">
    <input type="hidden" name="town">
    <input type="hidden" name="address">
    <input type="hidden" name="orderType" value="${orderInfo.orderType}">
  </div>
  <!--主体-->
  <div class="order-header clearfix">
    <div class="order-header-logo pull-left">
      <a href="/">
        <img src="/static/images/logo.png">
      </a>
    </div>
    <div class="order-header-step pull-right clearfix">
      <div class="order-step-group bg-yellow pull-left">
        <div class="order-step-number">
          <i class="bg-yellow">1</i>
          <span class="color-yellow">拍下商品</span>
        </div>
      </div>
      <div class="order-step-group order-step-active bg-coffee pull-left">
        <div class="interface interface-left bg-coffee"></div>
        <div class="interface interface-right bg-coffee"></div>
        <div class="order-step-number">
          <i class="bg-coffee">2</i>
          <span class="color-coffee">填写核对订单信息</span>
        </div>
      </div>
      <div class="order-step-group bg-gray pull-left">
        <div class="order-step-number">
          <i class="bg-gray">3</i>
          <span class="color-gray">成功提交订单</span>
        </div>
      </div>
    </div>
  </div>
  <div class="order-body">
    <div class="order-body-title">
      填写并核对订单信息
    </div>
    <div class="order-body-content">
      <div class="order-content-header clearfix">
        <h4 class="pull-left">收货人信息</h4>
        <a href="javascript:void(0);" class="pull-right" id="buildNewAddress">新建收货人地址</a>
      </div>
      <div class="order-content order-address" id="orderAddress">
        <%--收货人信息--%>
      </div>
      <div class="order-address-collapseBtn">
        <span class="address-down">显示全部地址<i></i></span>
        <span class="address-up">收起<i></i></span>
      </div>
    </div>
    <div class="order-body-content">
      <div class="order-content-header clearfix">
        <h4>支付方式</h4>
      </div>
      <div class="order-pay clearfix">
        <label class="pull-left order-pay-active">
          <input type="radio" checked name="payRouter" value="在线支付" data-checked="true">
          <div class="pay-router">
            在线支付<i></i>
          </div>
        </label>
        <%--<label class="pull-left">
          <input type="radio" checked name="payRouter" value="在线支付"  data-checked="false">
          <div class="pay-router">
            在线支付<i></i>
          </div>
        </label>--%>
      </div>
    </div>
    <div class="order-body-content">
      <div class="order-content-header clearfix">
        <h4>送货清单</h4>
      </div>
      <ul class="order-info clearfix">
        <%--订单明细--%>
      </ul>
      <div class="customer-message clearfix">
        <div class="customer-message-group pull-left clearfix">
          <label class="pull-left">留言：</label>
          <textarea class="pull-left" name="buyerRemark" placeholder="选填：对本次交易的说明（建议填写已和卖家协商一致的内容）"></textarea>
        </div>
        <div class="customer-message-group pull-left clearfix ">
            <label class="pull-left">配送方式：</label>
            <input type="hidden" name="buyerCarriage">
            <input type="hidden" name="expressId">
            <input type="hidden" name="expressCompany">
            <span class="pull-left post-service">暂无</span>
        </div>
        <div class="customer-message-group pull-left clearfix" style="margin-right: 0">
          <div class="mes">
            <label class="pull-left">运费共计：</label>
            <span class="pull-left color-red order-carrige-price blodRed">¥0.00</span>元
          </div>
          <div class="mes">
            <label class="pull-left">货品总金额：</label>
            <span class="pull-left color-red order-total-price blodRed">¥0.00</span>元
          </div>
        </div>
      </div>
    </div>
    <div class="order-body-content">
      <div class="order-content-header clearfix">
        <h4>发票信息</h4>
      </div>
      <div class="order-bill clearfix">
        <div class="bill-wrap pull-left">
          <input type="hidden" name="needInvoice" value="0">
          <span class="bill-title">不要发票<i></i></span>
          <ul class="bill-list">
            <li>不要发票</li>
            <li>普通发票（纸质）</li>
          </ul>
        </div>
        <span class="bill-category pull-left" data-invoice-name="person">个人</span>
        <span class="bill-editor pull-left">修改</span>
      </div>
    </div>
  </div>
  <div class="order-footer clearfix">
    <div class="order-footer-cost pull-right clearfix">

    </div>
    <div class="order-footer-cost pull-right clearfix">
      <div>
        <label class="pull-left">优&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;惠：</label>
        <span class="pull-left discount-amount favourPrice">-¥0.00</span>
      </div>
      <div>
        <label class="pull-left">应付总额：</label>
        <span class="pull-left total-payment">¥0.00</span>
      </div>
    </div>
    <div class="clearfix"></div>
    <div class="order-footer-address pull-right clearfix">
      <span class="pull-left address-info">寄送至：</span>
      <span class="pull-left receive-name">收货人：</span>
    </div>
    <div class="clearfix"></div>
  </div>
  <div class="order-submit clearfix">
    <a href="javascript:void(0)" onclick="orderInformation.submitOrderInfo()" class="pull-right">提交订单</a>
  </div>

  <!--新增地址弹窗-->
  <div class="address-build" id="newAddress" style="display: none">
    <div class="address-build-mask">
      <div class="address-build-wrap">
        <div class="address-build-header clearfix">
          <span class="pull-left">新建收货地址</span>
          <i class="address-build-close pull-right"></i>
        </div>
        <form id="add-recevie-address-info">
          <div class="address-build-body">
          <div class="address-input-group">
            <div class="address-input-label">收货人：</div>
            <div class="address-input-input">
              <input type="text" name="receiptName" data-rule="required" data-validate-info="收货人不能为空">
            </div>
          </div>
          <div class="address-input-group">
            <div class="address-input-label">所在地区：</div>
            <div class="address-location">
              <div class="express-wrap">

                <input type="hidden" name="provinceCode"  id="add-provinceCode"/>
                <input type="hidden" name="cityCode" id="add-cityCode"/>
                <input type="hidden" name="areaCode" id="add-areaCode"/>
                <input type="hidden" name="provinceName"  id="add-provinceName" data-rule="required" data-validate-info="省不能为空"/>
                <input type="hidden" name="cityName" id="add-cityName" data-rule="required" data-validate-info="市不能为空"/>
                <input type="hidden" name="areaName" id="add-areaName"/>

                <span class="location-choose" id="add-order-info-city-click">请选择省市区<i></i></span>
                <i class="gray-line"></i>
                <div class="express-choose-datalist" id="add-order-info-city-select">
                  <%--地址信息--%>
                </div>
              </div>
            </div>
          </div>
          <div class="address-input-group">
            <div class="address-input-label">详细地址：</div>
            <div class="address-input-input">
              <input type="text" name="detailAddr">
            </div>
          </div>
          <div class="clearfix">
            <div class="address-input-group pull-left">
              <div class="address-input-label">手机号码：</div>
              <div class="address-input-input">
                <input type="text" name="mobile" data-rule="phone" data-validate-info="手机号码格式不正确">
              </div>
            </div>
            <span class="else pull-left">或</span>
            <div class="address-input-group pull-left">
              <div class="address-input-label">固定电话：</div>
              <div class="address-input-input">
                <input type="text" name="fiexdPhone" data-rule="tel" data-validate-info="固定电话格式不正确">
              </div>
            </div>
          </div>
        </div>
        </form>
        <div class="address-build-footer">
          <input type="button" onclick="orderInformation.saveReceiveAddressInfo(this)" data-form-id="add-recevie-address-info" value="保存收货地址">
        </div>
      </div>
    </div>
  </div>
  <!--编辑地址-->
  <div class="address-build" id="editorAddress" style="display: none">
    <div class="address-build-mask">
      <div class="address-build-wrap">
        <div class="address-build-header clearfix">
          <span class="pull-left">修改收货地址</span>
          <i class="address-build-close pull-right"></i>
        </div>
        <form id="edit-receive-address-info">
          <div class="address-build-body">
          <div class="address-input-group">
            <div class="address-input-label">收货人：</div>
            <div class="address-input-input">
              <input type="hidden" name="id">
              <input type="hidden" name="userId">
              <input type="text" name="receiptName" data-rule="required" data-validate-info="收货人不能为空">
            </div>
          </div>
          <div class="address-input-group">
            <div class="address-input-label">所在地区：</div>
            <div class="address-location">
              <div class="express-wrap">
                <input type="hidden" name="provinceCode"  id="edit-provinceCode"/>
                <input type="hidden" name="cityCode" id="edit-cityCode"/>
                <input type="hidden" name="areaCode" id="edit-areaCode"/>
                <input type="hidden" name="provinceName"  id="edit-provinceName" data-rule="required" data-validate-info="省不能为空"/>
                <input type="hidden" name="cityName" id="edit-cityName" data-rule="required" data-validate-info="市不能为空"/>
                <input type="hidden" name="areaName" id="edit-areaName"/>
                <span class="location-choose" id="edit-order-info-city-click">请选择省市区<i></i></span>
                <i class="gray-line"></i>
                <div class="express-choose-datalist" id="edit-order-info-city-select">
                  <%--地址信息--%>
                </div>
              </div>
            </div>
          </div>
          <div class="address-input-group">
            <div class="address-input-label">详细地址：</div>
            <div class="address-input-input">
              <input type="text" name="detailAddr">
            </div>
          </div>
          <div class="clearfix">
            <div class="address-input-group pull-left">
              <div class="address-input-label">手机号码：</div>
              <div class="address-input-input">
                <input type="text" name="mobile" data-rule="phone" data-validate-info="手机号码格式不正确">
              </div>
            </div>
            <span class="else pull-left">或</span>
            <div class="address-input-group pull-left">
              <div class="address-input-label">固定电话：</div>
              <div class="address-input-input">
                <input type="text" name="fiexdPhone" data-rule="tel" data-validate-info="固定电话格式不正确">
              </div>
            </div>
          </div>
        </div>
        </form>
        <div class="address-build-footer">
          <input type="button" onclick="orderInformation.saveReceiveAddressInfo(this)" data-form-id="edit-receive-address-info" value="保存收货地址">
        </div>
      </div>
    </div>
  </div>
  <!--发票修改弹窗-->
  <div class="bill-change" id="billChange" style="display: none">
    <div class="bill-change-wrap">
      <div class="bill-change-content">
        <div class="bill-change-title clearfix">
          <span class="pull-left">发票信息</span>
          <i class="bill-close pull-right"></i>
        </div>
        <div class="bill-change-body">
          <div class="bill-change-category clearfix">
            <label class="bill-change-category-group pull-left">
              <input type="radio" name="invoiceName" data-value="person" value="个人">
              个人
            </label>
            <label class="bill-change-category-group pull-left">
              <input type="radio" name="invoiceName" data-value="company" value="单位">
              单位
            </label>
          </div>
          <div class="bill-txt invoice-company-css hide">
            <label>发票抬头：</label>
            <input type="text" placeholder="请输入单位名称" name="invoiceContent">
          </div>
          <div class="bill-txt invoice-company-css hide">
            <label>税号：</label>
            <input type="text" placeholder="请输入税号" name="invoiceDutyParagraph">
          </div>
          <div class="bill-txt invoice-company-css hide" id="billTxt">
            <sub>*</sub>自2017年7月1日起，您若开具增值税普通发票，须同时提供企业抬头及税号，否则将无法开具发票，该规定不含个人、政府机构、事业单位中的非企业单位等。
          </div>
        </div>
        <div class="bill-change-footer">
          <a href="javascript:void(0);" onclick="orderInformation.selectedBill(event)">确认</a>
        </div>
      </div>
    </div>
  </div>
  <!--主体  ending-->

  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>

</body>
</html>
<script src="/static/js/purchaseOrder/order_infomation.js"></script>


<script type="text/html" id="tpl-order-address-group">
  <div class="order-address-group clearfix">
    {{if status == "1"}}
      <label class="pull-left address-active clearfix">
        <input class="addressInput" data-province="{{provinceName}}" data-city="{{cityName}}"
               data-area="{{areaName}}" data-address="{{detailAddr}}" data-mobile="{{mobile}}"
               data-receiptname="{{receiptName}}" class="pull-left" checked data-checked="true"
               data-address-default="true"  type="radio" name="address" value="address1">
    {{else}}
      <label class="pull-left clearfix">
        <input class="addressInput" data-province="{{provinceName}}" data-city="{{cityName}}"
               data-area="{{areaName}}" data-address="{{detailAddr}}" data-mobile="{{mobile}}"
               data-receiptname="{{receiptName}}" class="pull-left" data-checked="false"
               data-address-default="false"  type="radio" name="address" value="address1">
    {{/if}}

      <div class="input-mask pull-left">
        {{receiptName}}-{{provinceName}}
        <i></i>
      </div>
      <span class="address-txt pull-left">{{receiptName}}  {{provinceName}} {{cityName}} {{areaName}} {{detailAddr}}    {{mobile}}</span>
      <span class="address-default pull-left">默认地址</span>
    </label>
                    <span class="address-operation pull-right clearfix">
                      {{if status != "1"}}
                      <a href="javascript:void(0);" onclick="orderInformation.setAddressDefault(this)" data-id="{{id}}" class="address-set-default pull-left">设置默认地址</a>
                      {{else}}
                      <a href="javascript:void(0);" onclick="orderInformation.setAddressDefault(this)" data-id="{{id}}" class="address-set-default hide pull-left">设置默认地址</a>
                      {{/if}}
                        <a href="javascript:void(0);" data-id="{{id}}" onclick="orderInformation.openEditAddress(this)" data-address-info="{{addressInfo}}" class="address-edit pull-left">修改</a>
                        <a href="javascript:void(0);" data-id="{{id}}" onclick="orderInformation.delReceiveAddresInfo(this)" class="address-delete pull-left">删除</a>
                    </span>
  </div>
</script>

<script id="tpl-product-info" type="text/html">
  <li class="clearfix good-sku-item">
    <div class="product-info pull-left clearfix">
      <div class="product-img pull-left">
          <img src="{{purGoodsImgUrl}}">
      </div>
      <div class="product-txt pull-left">
        <div class="product-title">
          {{purGoodsName}}  {{purGoodsType}}
        </div>
        {{if goodType == 'zengpin'}}
          <div class="product-dai clearfix">
            <i class="pull-left">赠品</i>
            <%--<i class="pull-left">代</i>--%>
          </div>
        {{/if}}
      </div>
    </div>
    <div class="product-price pull-left">
      ¥<span class="price-span">{{price}}</span>
    </div>
    <div class="product-count pull-left clearfix">
      {{if goodType != 'zengpin'}}
        <a href="javascript:void(0)" class="subtraction pull-left">-</a>
        <input class="pull-left ipt-sku-num" name="number" type="text" value="{{number}}" data-discount-amount="{{discountAmount}}" readonly data-price="{{price}}" data-sku-id="{{purGoodsSkuId}}" data-good-type="{{goodType}}" data-is-sample="{{isSample}}">
        <a href="javascript:void(0)" class="plus pull-left">+</a>
      {{else}}
        <a href="javascript:void(0)" class="subtraction pull-left disabled">-</a>
          <input class="pull-left ipt-sku-num" name="number" type="text" value="{{number}}" readonly data-discount-amount="0" data-price="0" data-sku-id="{{purGoodsSkuId}}" data-good-type="{{goodType}}" data-is-sample="{{isSample}}">
        <a href="javascript:void(0)" class="plus pull-left disabled">+</a>
      {{/if}}
    </div>
    <div class="product-favorable pull-left">
      <%--{{if !promotionalInfo}}
        <div class="product-favorable-title pull-left clearfix">
          {{if promotionalInfo.bestActivity.activityType == 'presell'}}
            <span class="product-favorable-btn" data-activity-type="{{promotionalInfo.bestActivity.activityType}}"
                  data-limit-num="{{promotionalInfo.bestActivity.limitNum}}"
                data-precell-type="{{promotionalInfo.bestActivity.presellType}}"
                data-limit-flag="{{promotionalInfo.bestActivity.limitFlg}}"
                data-first-amount="{{promotionalInfo.bestActivity.firstAmount}}">
              {{promotionalInfo.bestActivity.activityName}}<i></i>
            </span>
          {{else}}
            <span class="product-favorable-btn" data-activity-type="promotionalInfo.bestActivity.activityType">
              {{promotionalInfo.bestActivity.activityName}}<i></i>
            </span>
          {{/if}}
          <ul class="product-favorable-list">
            {{if promotionalInfo.activityList}}
              {{each promotionalInfo.activityList as activity index}}
                {{if activity.activityType == 'presell'}}
                  <li data-activity-type="{{activity.activityType}}"
                      data-limit-num="{{activity.limitNum}}"
                      data-limit-flag="{{activity.limitFlg}}"
                      data-precell-type="{{activity.presellType}}"
                      data-first-amount="{{activity.firstAmount}}" data-presell-amount="{{activity.presellAmount}}" data-activity-name="{{activity.activityName}}">
                    {{activity.activityName}}
                  </li>
                  {{else}}
                  <li data-activity-type="{{activity.activityType}}" data-activity-name="{{activity.activityName}}">{{activity.activityName}}</li>
                {{/if}}

              {{/each}}
            {{/if}}
          </ul>
        </div>
        <i class="pull-right  favorable-decoration"></i>
      {{/if}}--%>
    </div>
    <div class="product-cost pull-right">
      {{sumAmount}}
    </div>
    {{if messageList && messageList.length > 0}}
      <div class="clearfix"></div>
      <div class="gift">
        {{each messageList as message}}
        <div><span>{{message}}</span></div>
        {{/each}}
      </div>
    {{/if}}
  </li>
</script>
