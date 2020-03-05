<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/16
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>评价管理-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
  <link rel="stylesheet" type="text/css" href="/static/css/person.css">
  <link rel="stylesheet" type="text/css" href="/static/css/ratelist.css">

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
    <div class="content">
      <!-- 个人中心左侧菜单栏 -->
      <%--右侧导航栏 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
      <%--右侧导航栏 ending--%>
      <!-- END个人中心左侧菜单栏 -->
      <!-- 个人中心主内容 -->
      <div class="right-cont-wrap">
        <div>
          <ul class="evaluation-nav-menu clearfix">
            <li data-control="waitingRecommend" data-status="evaluate" class="active"><a>待评价的订单<span class="red ml10 evaluateCount"></span></a></li>
            <li data-control="overRecommend" data-status="done"><a>已评价的订单<span class="red ml10 fulfill"></span></a></li>
          </ul>
        </div>
        <div class="list-wrap" id="waitingRecommend">
          <table class="title-table">
            <thead>
            <tr class="bg-gray">
              <td class="long-w">订单详情</td>
              <td>收货人</td>
              <td>支付金额</td>
              <td>交易操作</td>
            </tr>
            </thead>
          </table>
          <div class="check-box">
            <div class="option-wrap clearfix">
              <a class="option-btn pre disable" data-page-no="0">上一页</a>
              <a class="option-btn next" data-page-no="">下一页</a>
            </div>
          </div>

          <div>
            <div class="order-list-div">

            </div>
          </div>
        </div>

      </div>
    </div>
    <!-- END个人中心主内容 -->
  </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/evaluate/ratelist.js"></script>
<script id="tpl-order-list" type="text/html" >

  <table class="list-table">
    <thead>
    <tr>
      <td colspan="6">
        <span class="ml20">{{createTime}}</span>
        <span class="num">订单号：{{orderNo}}</span>
      </td>
    </tr>
    </thead>
    <tbody>
    {{each orderListVos as orderListInfo index}}
    {{if index == 0}}
    <tr>
      <td class="little-w">
        <div class="img-cover ml20">
          <img src="{{orderListInfo.purGoodsImgUrl}}">
        </div>
      </td>
      <td class="middle-w border-left-none">
        <p class="ml10">{{orderListInfo.purGoodsName}} {{orderListInfo.purGoodsType}}</p>
      </td>
      <td class="little-w tx-center border-left-none">
        <span class="cl9">x{{orderListInfo.number}}</span>
      </td>
      <td class="little-w tx-center">
        <span class="cl6">{{receiver}}</span>
      </td>
      <td class="small-w tx-center">
        <span class="cl6">¥{{totalAmount}}</span>
      </td>
      {{if status == 13}}
      <td class="small-w tx-center" >
        <p><a class="delOrder" data-orderType="{{orderType}}" data-id="{{id}}"  onclick="rateList.delOrder(this)">删除</a></p>
        <a href="/evaluate/evaluateDetails/{{id}}" class="btn-details">评价详情</a>
      </td>
      {{/if}}
      {{if status == 12}}
      <td class="small-w tx-center">
        <p><a href="/order/detail/{{orderListInfo.orderId}}/{{orderType}}">订单详情</a></p>
        <a href="/evaluate/orderEvaluation/{{orderListInfo.orderId}}" class="orange-btn">立即评价</a>
      </td>
      {{/if}}
    </tr>
    {{else}}
    <tr>
      <td class="little-w border-top">
        <div class="img-cover ml20">
          <img src="{{orderListInfo.purGoodsImgUrl}}">
        </div>
      </td>
      <td class="middle-w border-left-none border-top">
        <p class="ml10">{{orderListInfo.purGoodsName}} {{orderListInfo.purGoodsType}}</p>
      </td>
      <td class="little-w tx-center border-left-none border-top">
        <span class="cl9">x{{orderListInfo.number}}</span>
      </td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    {{/if}}

    {{/each}}
    </tbody>
  </table>
</script>

</html>
