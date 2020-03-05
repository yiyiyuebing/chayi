<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/2
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="nav clearfix">
  <div class="nav-wrap clearfix">
    <jsp:include page="/WEB-INF/jsp/www/purchaseClassfiy/purchaseClassify.jsp"></jsp:include>
    <a class="pull-left nav-link nav-active" data-value="goods" href="/">商城</a>
    <%--<a class="pull-left nav-link" data-value="dingzhi" href="customization_list.html">定制</a>--%>
    <a class="pull-left nav-link" data-value="markting" href="/news">资讯</a>
    <a class="pull-left nav-link" data-value="cooperation" href="javascript:void(0);">招商合作</a>
  </div>
</div>

<script type="text/javascript">
  function initNavBar(page) {
    $(".nav-wrap").find(".nav-link").removeClass("nav-active");
    $(".nav-wrap").find(".nav-link[data-value=" + page + "]").addClass("nav-active");
  }
</script>