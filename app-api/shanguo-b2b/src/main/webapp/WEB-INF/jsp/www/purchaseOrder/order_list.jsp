<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/18
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的进货单-优茶联</title>
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
      <!-- 个人中心左侧菜单栏 -->
      <%--右侧导航栏 starting--%>
      <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
      <%--右侧导航栏 ending--%>
      <!-- 个人中心主内容 -->
      <div class="right-cont-wrap">
        <div>
          <ul class="nav-menu clearfix">
            <li class="active" data-status=""><a>所有订单<span class="red ml10 allCount"></span></a></li>
            <li data-status="pay"><a>待付款<span class="red ml10 payCount"></span></a></li>
            <li data-status="ship"><a>待发货<span class="red ml10 shipCount"></span></a></li>
            <li data-status="receive"><a>待收货<span class="red ml10 receiveCount"></span></a></li>
            <li data-status="evaluate"><a>待评价<span class="red ml10 evaluateCount"></span></a></li>
          </ul>
        </div>
        <div class="list-wrap has-border-top">
          <div class="filter-box">
            <div class="search-wrap clearfix">
              <div class="search-box clearfix">
                <input class="search-ipt" type="text" name="name" placeholder="输入商品标题或订单号进行搜索">
                <label class="search-label">搜索</label>
              </div>
              <%--<a class="more-btn pull-left">更多筛选条件</a>--%>
            </div>
            <!--更多筛选条件-->
            <div class="more-part hide">
              <table class="more-table">
                <tbody>
                <tr>
                  <td>
                    <label>订单类型</label>
                    <select class="more-select ml10">
                      <option>全部</option>
                      <option>茶叶</option>
                    </select>
                  </td>
                  <td>
                    <label>成交时间</label>
                    <input class="more-ipt ml10" type="date">
                    <span>-</span>
                    <input class="more-ipt" type="date">
                  </td>
                  <td>
                    <label>评价状态</label>
                    <select class="more-select ml10">
                      <option>全部</option>
                      <option>待评价</option>
                      <option>已评价</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td>
                    <a class="orange-btn">搜索</a>
                  </td>
                  <td></td>
                  <td></td>
                </tr>
                </tbody>
              </table>
            </div>
            <!--END更多筛选条件-->
          </div>
          <table class="title-table mt20">
            <thead>
            <tr>
              <td class="long-w tx-center">宝贝</td>
              <td class="little-w tx-center">单价</td>
              <td class="little-w tx-center">数量</td>
              <td class="small-w tx-center">实付款</td>
              <td class="little-w tx-center">
                <select class="status-select">
                  <option value="">交易状态</option>
                  <option value="pay">待付款</option>
                  <option value="ship">待发货</option>
                  <option value="receive">待收货</option>
                  <option value="evaluate">待评价</option>
                </select>
              </td>
              <td class="small-w tx-center">交易操作</td>
            </tr>
            </thead>
          </table>
          <div>
            <div class="check-box">
              <div class="ml20 clearfix">
                <div class="pull-left hide">
                  <input type="checkbox" class="check-all">
                  <lalel>全选</lalel>
                </div>
                <a class="batch-btn pull-left batch-confirm-receive hide">批量确认收货</a>
              </div>
              <div class="option-wrap clearfix">
                <a class="option-btn pre" data-page-no="0">上一页</a>
                <a class="option-btn next" data-page-no="">下一页</a>
              </div>
            </div>
            <div class="order-list-div">
              <%--订单列表--%>
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


<script type="text/html" id="tpl-order-list-table">
  <table class="list-table">
    <thead>
    <tr style="border-bottom: 1px solid #dbdbdb;">
      <td class="relative" colspan="7">
        <div class="ml20">
          <input type="checkbox" name="ordercheck" value="{{id}}" data-status="{{status}}" data-order-type="{{orderType}}">
          <lalel>{{createTime}}</lalel>
          <span class="ml20">订单号：{{orderNo}}</span>
        </div>
        {{if status == 8 || status == 9 || status == 10 || status == 12 || status == 13 || dealStatus == 0 || dealStatus == 1 || dealStatus == 2}}
          <a class="del-btn" href="javascript:void(0);" onclick="orderList.doDelOrder(this)" data-id="{{id}}" data-order-type="{{orderType}}"></a>
        {{/if}}

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
          <p class="ml10">
            <div>{{orderListInfo.purGoodsName}} {{orderListInfo.purGoodsType}}</div>
            {{if orderListInfo.goodType == 'zengpin'}}
              <div class="clearfix tagWrap">
                <i class="pull-left tagCss">赠品</i>
              </div>
            {{/if}}
          </p>
        </td>
        <td class="little-w tx-center border-left-none">
          <span>¥{{(orderListInfo.purGoodsAmount).toFixed(2)}}</span>
        </td>
        <td class="little-w tx-center border-left-none">
          <span class="cl9">x{{orderListInfo.number}}</span>
        </td>
        <td class="small-w">
          <p class="tx-center">¥{{paymentAmount}}</p>
          <p class="tx-center">（含运费：¥{{carriage}}）</p>
        </td>
        <td class="little-w">
          <p class="tx-center"><span class="cl9">{{statusName}}</span></p>
          {{if status != 11}}
          <p class="tx-center"><a href="/order/detail/{{orderListInfo.orderId}}/{{orderType}}">订单详情</a></p>
          {{/if}}

        </td>
        <td class="small-w tx-center">
          {{if status == 1}}
            <a href="/order/submit/{{id}}" class="orange-btn">立即付款</a>
          {{/if}}
          {{if status == 12 }}
            <a href="/evaluate/orderEvaluation/{{orderListInfo.orderId}}" class="orange-btn">立即评价</a>
          {{/if}}
        </td>
      </tr>
    {{else}}
      <tr>
        <td class="little-w border-top">
          <div class="img-cover ml20">
            <img src="{{orderListInfo.purGoodsImgUrl}}">
          </div>
        </td>
        <td class="middle-w border-left-none border-top">
          <p class="ml10">
            <div>{{orderListInfo.purGoodsName}} {{orderListInfo.purGoodsType}}</div>
            {{if orderListInfo.goodType == 'zengpin'}}
                <div class="clearfix tagWrap">
                  <i class="pull-left tagCss">赠品</i>
                </div>
            {{/if}}
          </p>
        </td>
        <td class="little-w tx-center border-left-none border-top">
          <span>¥{{orderListInfo.purGoodsAmount}}</span>
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

<script src="/static/js/purchaseOrder/order_list.js"></script>
