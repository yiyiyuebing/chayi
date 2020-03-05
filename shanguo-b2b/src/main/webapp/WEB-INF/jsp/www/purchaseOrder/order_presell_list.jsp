<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/18
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的预售订单-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
  <link rel="stylesheet" type="text/css" href="/static/css/person.css">
  <link rel="stylesheet" href="/static/css/person.list.css">
  <link rel="stylesheet" href="/static/css/person_center_nav_position.css">
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

<!-- 主内容 -->
<div class="person-wrap">
  <div class="person-cont">
    <div class="content">
      <%--右侧导航栏 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
      <%--右侧导航栏 ending--%>
      <!-- 个人中心主内容 -->
      <div class="right-cont-wrap">
        <div class="list-wrap">
          <table class="title-table">
            <thead>
            <tr>
              <td class="long-m-w tx-center">宝贝</td>
              <td class="little-w tx-center">单价</td>
              <td class="little-w tx-center">数量</td>
              <td class="small-w tx-center">实付款</td>
              <td class="little-w tx-center">
                <select class="status-select">
                  <option>交易状态</option>
                  <option>交易中</option>
                  <option>交易成功</option>
                  <option>交易关闭</option>
                </select>
              </td>
              <td class="small-w tx-center">交易操作</td>
            </tr>
            </thead>
          </table>
          <div class="check-box">
            <div class="option-wrap clearfix">
              <a class="option-btn pre disable" data-page-no="0">上一页</a>
              <a class="option-btn next disable" data-page-no="0">下一页</a>
            </div>
          </div>
          <div>
            <div class="order-list-div">

            </div>

          </div>
        </div>
      </div>
      <!-- END个人中心主内容 -->
    </div>
  </div>
</div>
<!-- END主内容 -->
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>

</body>
</html>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/purchaseOrder/order_presell_list.js"></script>

<script id="tpl-list-table" type="text/html">
  <table class="list-table">
    <thead>
    <tr>
      <td class="relative" colspan="7">
        <div class="ml20">
          <input type="checkbox">
          <lalel>{{createTime}}</lalel>
          <span class="ml20">订单号：{{orderNo}}</span>
        </div>

        {{if status == 8 || status == 9 || status == 10 || status == 12 || status == 13 || dealStatus == 0 || dealStatus == 1 || dealStatus == 2}}
        <a class="del-btn" href="javascript:void(0);" onclick="orderPresellList.doDelOrder(this)" data-id="{{id}}" data-order-type="{{orderType}}"></a>
        {{/if}}
        <%--<a class="del-btn"></a>--%>
      </td>
    </tr>
    </thead>
    <tbody>
    {{each orderListVos as orderListInfo index}}
      {{if index == 0}}
        <tr class="border-top">
          <td class="little-w">
            <div class="img-cover ml20">
              <img src="{{orderListInfo.purGoodsImgUrl}}">
            </div>
          </td>
          <td class="middle-w border-left-none">
            <p class="ml10">{{orderListInfo.purGoodsName}}</p>
          </td>
          <td class="little-w tx-center border-left-none">
            <span>¥{{(orderListInfo.finalAmount/orderListInfo.number).toFixed(2)}}</span>
          </td>
          <td class="little-w tx-center border-left-none">
            <span class="cl9">x{{orderListInfo.number}}</span>
          </td>
          <td class="small-w">
            <p class="tx-center">¥{{orderListInfo.finalAmount}}</p>
            <p class="tx-center">（含运费：¥{{carriage}}）</p>
          </td>
          <td class="little-w">
            <p class="tx-center"><span class="cl9">{{statusName}}</span></p>
            {{if status!=11 }}
            <p class="tx-center"><a href="/order/detail/{{id}}/presell">订单详情</a></p>
            {{/if}}
          </td>
          <td class="small-w tx-center">
            {{if orderListInfo.status == 'waitpay'}}
            <a href="/order/submit/{{id}}" class="orange-btn">立即付款</a>
            {{/if}}
          </td>
        </tr>

      {{else}}
        <tr class="border-top">
          <td class="little-w">
            <div class="img-cover ml20">
              <img src="{{orderListInfo.purGoodsImgUrl}}">
            </div>
          </td>
          <td class="middle-w border-left-none">
            <p class="ml10">{{orderListInfo.purGoodsName}}</p>
          </td>
          <td class="little-w tx-center border-left-none">
            <span>¥{{(orderListInfo.finalAmount/orderListInfo.number).toFixed(2)}}</span>
          </td>
          <td class="little-w tx-center border-left-none">
            <span class="cl9">x{{orderListInfo.number}}</span>
          </td>
          <td class="small-w">
            <p class="tx-center">¥{{orderListInfo.finalAmount}}</p>
            <p class="tx-center">（含运费：¥{{carriage}}）</p>
          </td>
          <td></td>
          <td></td>
        </tr>
      {{/if}}
    {{/each}}
    {{if paymentList}}
      {{each paymentList as payment index}}
      <tr class="border-top">
        <td colspan="2">
          <%--{{if paymentList.length > 1}}--%>
            <span class="cl6 ml20">{{payment.stageDesc}}</span>
          <%--{{/if}}--%>
        </td>
        <td class="little-w tx-center border-left-none">
          <span>¥{{payment.waitpayAmount}}</span>
        </td>
        <td class="little-w tx-center border-left-none">
          <span class="cl9">x{{number}}</span>
        </td>
        <td class="small-w tx-center">
          <span class="tx-center">¥{{payment.waitpayAmount}}</span>
        </td>
        <td class="small-w tx-center">
          <span class="cl6">
            {{if payment.payStatus == 'T'}}
            已付款
            {{else}}
            待付款
            {{/if}}
          </span>
        </td>
        <td></td>
      </tr>
      {{/each}}
    {{/if}}
    </tbody>
  </table>
</script>