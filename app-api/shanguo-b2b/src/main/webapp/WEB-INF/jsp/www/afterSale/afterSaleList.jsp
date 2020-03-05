<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/7/28
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>售后列表-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
  <link rel="stylesheet" type="text/css" href="/static/css/person.css">
  <link rel="stylesheet" href="/static/css/person_center_sales_return.css">
  <link rel="stylesheet" href="/static/js/lib/datetimepicker/css/bootstrap.min.css">
  <link rel="stylesheet" href="/static/js/lib/datetimepicker/css/bootstrap-datetimepicker.min.css">

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

<!--主体-->
<div class="person-wrap">
  <div class="person-cont">
    <div class="trading-content content clearfix">

      <%--右侧导航栏 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
      <%--右侧导航栏 ending--%>
      <div class="sales-return pull-left right-cont-wrap">
        <div class="sales-return-title">
          <h5>退换货管理</h5>
        </div>
        <div class="sales-return-search clearfix">
          <div class="return-search-group pull-left clearfix">
            <label class="pull-left">退款类型：</label>
            <input type="hidden" name="afterType" value="全部">
            <div class="input-select pull-left">
              <a href="javascript:;" class="list-btn clearfix" id="afterTypeBtn">全部</a>
              <input type="hidden" name="afterType" value="">
              <ul class="input-select-list" id="afterTypeList">
                <li data-afterType="全部" data-type="">全部</li>
                <li data-afterType="退款" data-type="refund">退款</li>
                <li data-afterType="换货" data-type="exchange">换货</li>
                <li data-afterType="退款退货" data-type="refund_return">退款退货</li>
              </ul>
            </div>
          </div>
          <div class="return-search-group pull-left clearfix">
            <label class="pull-left">退款状态：</label>
            <div class="input-select pull-left">
              <a href="javascript:;" class="list-btn clearfix disabled" id="afterStatusBtn">全部</a>
              <input type="hidden" name="afterStatus" value="">
              <ul class="input-select-list" id="afterStatusList">
                <li data-afterStatus="全部" data-status="">全部</li>

                <li class="after-sale-refund" data-afterStatus="请退款" data-status="1">请退款</li>
                <li class="after-sale-refund" data-afterStatus="已退款" data-status="5">已退款</li>
                <li class="after-sale-refund" data-afterStatus="同意退款" data-status="2">同意退款</li>
                <li class="after-sale-refund" data-afterStatus="已撤销退款" data-status="6">已撤销退款</li>
                <li class="after-sale-refund" data-afterStatus="已拒绝退款" data-status="7">已拒绝退款</li>

                <li class="after-sale-exchange" data-afterStatus="请换货" data-status="1">请换货</li>
                <li class="after-sale-exchange" data-afterStatus="同意换货" data-status="2">同意换货</li>
                <li class="after-sale-exchange" data-afterStatus="买家发货" data-status="3">买家发货</li>
                <li class="after-sale-exchange" data-afterStatus="确认到货" data-status="4">确认到货</li>
                <li class="after-sale-exchange" data-afterStatus="换货成功" data-status="5">换货成功</li>
                <li class="after-sale-exchange" data-afterStatus="已撤销换货" data-status="6">已撤销换货</li>
                <li class="after-sale-exchange" data-afterStatus="已拒绝换货" data-status="7">已拒绝换货</li>

                <li class="after-sale-refund_return" data-afterStatus="请退货" data-status="1">请退货</li>
                <li class="after-sale-refund_return" data-afterStatus="同意退货" data-status="2">同意退货</li>
                <li class="after-sale-refund_return" data-afterStatus="买家发货" data-status="3">买家发货</li>
                <li class="after-sale-refund_return" data-afterStatus="确认到货" data-status="4">确认到货</li>
                <li class="after-sale-refund_return" data-afterStatus="已退货" data-status="5">已退货</li>
                <li class="after-sale-refund_return" data-afterStatus="已撤销退货" data-status="6">已撤销退货</li>
                <li class="after-sale-refund_return" data-afterStatus="已拒绝退货退款" data-status="7">已拒绝退货退款</li>
              </ul>
            </div>
          </div>
          <div class="return-search-group pull-left clearfix">
            <label class="pull-left">从：</label>
            <input class="pull-left" name="startTime" type="text">
          </div>
          <div class="return-search-group pull-left clearfix">
            <label class="pull-left">到：</label>
            <input class="pull-left" name="endTime" type="text">
          </div>
        </div>
        <div class="sales-return-search-btn">
          <a href="javascript:void(0);">提交</a>
        </div>
        <div class="product-table">
          <ul class="product-table-header clearfix">
            <li class="table-header01 pull-left">宝贝</li>
            <li class="table-header02 pull-left">退款金额</li>
            <li class="table-header03 pull-left">申请时间</li>
            <li class="table-header04 pull-left">服务类型</li>
            <li class="table-header05 pull-left">退款状态</li>
            <li class="table-header06 pull-left">交易操作</li>
          </ul>
            <div class="check-box">
              <div class="option-wrap clearfix">
                <a class="option-btn pre" data-page-no="0">上一页</a>
                <a class="option-btn next" data-page-no="">下一页</a>
              </div>
            </div>
          <div class="refund-order-list">
            <%--订单商品明细--%>
            <%--<div class="product-table-group">
              <h4>订单号：12345678456789</h4>
              <ul class="product-table-content clearfix">
                <li class="product pull-left">
                  <img class="pull-left" src="/static/images/product_list_aside_img.png">
                  <span class="pull-left">铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  </span>
                </li>
                <li class="price pull-left">¥200.00</li>
                <li class="time pull-left">2017-04-24  13:40:46</li>
                <li class="server pull-left">退货退款</li>
                <li class="status pull-left">退款成功</li>
                <li class="details pull-left"><a href="#">服务详情</a></li>
              </ul>
            </div>
            <div class="product-table-group">
              <h4>订单号：12345678456789</h4>
              <ul class="product-table-content clearfix">
                <li class="product pull-left">
                  <img class="pull-left" src="/static/images/product_list_aside_img.png">
                  <span class="pull-left">铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  </span>
                </li>
                <li class="price pull-left">¥200.00</li>
                <li class="time pull-left">2017-04-24  13:40:46</li>
                <li class="server pull-left">退货退款</li>
                <li class="status pull-left">退款成功</li>
                <li class="details pull-left"><a href="#">服务详情</a></li>
              </ul>
            </div>--%>
          </div>

        </div>
      </div>
    </div>
  </div>
</div>

<!--主体ending-->


<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>

<script src="/static/js/user/person.common.js"></script>

<script src="/static/js/lib/datetimepicker/bootstrap.min.js"></script>
<script src="/static/js/lib/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/lib/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../static/js/afterSale/afterSaleList.js"></script>
<%--尾部 ending--%>
</body>
</html>


<script type="text/html" id="tpl-refund-order-list">
  <div class="product-table-group">
    <h4>订单号：{{orderNo}}</h4>
    <ul class="product-table-content clearfix">
      <li class="product pull-left">
        <img class="pull-left" src="{{purGoodsImgUrl}}">
        <span class="pull-left">{{purGoodsName}}</span>
      </li>
      <li class="price pull-left">¥{{finalAmount}}</li>
      <li class="time pull-left">{{createTime}}</li>
      <li class="server pull-left">{{flowList[0].asTypeStr.replace('中', '')}}</li>
      <li class="status pull-left">{{statusName}}</li>
      <li class="details pull-left">
        {{if flowList[0].asType == 'refund'}}
        <a href="/afterSale/afterSaleRefund/{{orderId}}/{{goodSkuId}}">服务详情</a>
        {{else if flowList[0].asType == 'exchange'}}
        <a href="/afterSale/afterSaleExchange/{{orderId}}/{{goodSkuId}}">服务详情</a>
        {{else}}
        <a href="/afterSale/afterSaleReturn/{{orderId}}/{{goodSkuId}}">服务详情</a>
        {{/if}}
      </li>
    </ul>
  </div>
</script>
