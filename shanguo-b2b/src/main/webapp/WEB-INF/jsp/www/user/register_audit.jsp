<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/23
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>平台审核-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/audit.css">
</head>
<body>

<!--主体-->
<div class="password-header clearfix">
  <a href="/" class="pull-left">
    <img src="/static/images/logo.png">
  </a>
</div>
<div class="nav-wrap">
  <ul class="nav clearfix">
    <li class="pull-left"><i>1</i>设置用户名</li>
    <li class="pull-left"><i>2</i>完善个人资料</li>
    <li class="active pull-left"><i>3</i>平台审核</li>
    <li class="pull-left"><i>4</i>注册成功</li>
  </ul>
</div>
<div class="body clearfix">
  <div class="left-img pull-left">
    <img src="/static/images/order_submit_green_right.png">
  </div>
  <div class="right-txt pull-left">
    <h5>资料审核中！</h5>
    <p>注册信息正在审核中，请耐心等待！我们将在3个工作日内短信通知审核结果，请注意查收！</p>
    <a href="/">返回主页<i></i></a>
  </div>
</div>
<!--主体ending-->
<jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>
</body>
</html>
