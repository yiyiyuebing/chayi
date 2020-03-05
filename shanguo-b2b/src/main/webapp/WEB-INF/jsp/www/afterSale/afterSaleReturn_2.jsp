<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head lang="en">
  <meta http-equiv="content-type" content="text/html;charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="renderer" content="webkit" />
  <title>退款退货-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">
  <link rel="stylesheet" href="/static/css/cssresets.css">
  <link rel="stylesheet" href="/static/css/public.css">
  <link rel="stylesheet" href="/static/css/after_sale_status_new.css">
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
          <span class="step step-success pull-left">
              <i class="icon-success"></i>买家申请退款退货
          </span>
          <i class="step-break step-break-transition pull-left"></i>
          <span class="step step-success pull-left">
              <i class="icon-success"></i>卖家处理退款退货申请
          </span>
          <i class="step-break step-break-success pull-left"></i>
          <span class="step step-active pull-left">
              <i>3</i>买家退货给卖家
          </span>
  <%--<i class="step-break step-break-active pull-left"></i>--%>
      <%--<span class="step pull-left">--%>
              <%--<i>4</i>卖家确认收货，并退款处理--%>
          <%--</span>--%>
          <i class="step-break step-break-active pull-left"></i>
          <span class="step pull-left">
              <i>4</i>退款成功
          </span>
</div>

<div class="content clearfix">
  <div class="content-left pull-left">
    <c:if test="${orderItemAsFlow.flowStatus == 'return_refund'}">
      <!--同意申请模块-->
      <div class="step-agree">
        <h5>优茶联已同意申请</h5>
        <div class="waiting-time">
          <input type="hidden" id="ipt-endTime" value="<fmt:formatDate value="${orderItemAsFlow.timeout}" pattern="yyyy-MM-dd HH:mm:ss"/>">
          请在<span id="return-day">0</span>天<span id="return-hour">0</span>时<span id="return-min">0</span>分内提交退货商品的物流信息，否则系统将自动关闭您的退货申请
        </div>
        <div class="waiting-operation clearfix">
          <label class="pull-left">您还可以：</label>
          <a href="javascript:void(0);" class="see-location pull-left" id="seeLocationBtn">查看退货地址</a>
          <a href="javascript:void(0);" class="editor-logistic pull-left" id="editorLogistic">填写退货物流信息</a>
        </div>
      </div>
      <!--同意申请模块 ending-->
    </c:if>
    <c:if test="${orderItemAsFlow.flowStatus == 'ret_shipping' || orderItemAsFlow.flowStatus == 'ret_confirm_receive'}">
      <!--同意申请模块-->
      <div class="step-waiting">
        <h5>提交成功，等待优茶联处理中...</h5>
        <div class="waiting-time">
          优茶联需在收到商品确认无问题后，将给您退款
        </div>
        <div class="waiting-operation clearfix">
            <%--<label class="pull-left">您还可以：</label>--%>
            <%--<a href="#" class="cancel-server pull-left">撤销申请</a>--%>
            <%--<a href="#" class="change-server pull-left">修改申请</a>--%>
        </div>
      </div>
      <!--同意申请模块 ending-->
    </c:if>
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
        <span style="letter-spacing: -0.5px">${goodMsgList.orderNo}</span>
      </div>
      <div class="order-price">
        <label><i>单</i>价：</label>
        <span>${goodMsgList.finalAmount}<mark>元*${goodMsgList.number}（数量）</mark></span>
      </div>
    </div>
  </div>
</div>


<!--填写换货物流信息弹窗-->
<div class="logistic-popup" id="logisticPopup">
  <div class="popup-wrap" id="logisticWrap">
    <div class="popup">
      <div class="popup-header">
        <h5>填写物流信息</h5>
        <a href="javascript:;" class="popup-close" id="popupClose"></a>
      </div>
      <div class="popup-body">
        <div class="group-express-company">
          <div class="group">
            <label>快递公司：</label>
            <input class="company" type="text" name="">
          </div>
          <p class="tips hide">快递公司不能为空</p>
        </div>
        <div class="group-express-number">
          <div class="group">
            <label>物流单号：</label>
            <input class="logistic-number" type="text" name="">
          </div>
          <p class="tips hide">物流单号不能为空</p>
        </div>
        <div class="group">
          <a href="javascript:void(0);" data-flow-id="${orderItemAsFlow.id}" class="logistic-submit submit">保存收货地址</a>
        </div>
        <div class="group">
          <a href="javascript:void(0);" class="logistic-submit cancel">取消保存</a>
        </div>
      </div>
    </div>
  </div>
</div>
<!--查看换货地址-->
<div class="logistic-popup" id="seeLocationPopup">
  <div class="popup-wrap" id="seeLocationWrap">
    <div class="popup">
      <div class="popup-header">
        <h5>退货地址</h5>
        <a href="javascript:;" class="popup-close" id="locationClose"></a>
      </div>
      <div class="popup-body">
        <div class="location">
          <h5>优茶联已确认退货地址：</h5>
          <p>收货人：${deliveryAddress.name}<br>
            联系电话：${deliveryAddress.mobile}<br>
            收货地址：${deliveryAddress.address}<br>
            备注：${deliveryAddress.note}</p>
        </div>
      </div>
    </div>
  </div>
</div>

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
</body>
<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>
<script src="/static/js/afterSale/after_sale_status.js"></script>
<script src="/static/js/afterSale/afterSaleReturn.js"></script>
</html>
