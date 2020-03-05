<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/26
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>重置密码-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/password_reset_success.css">
</head>
<body>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<!--主体-->
<div class="password-header clearfix">
  <a href="/" class="pull-left">
    <img src="/static/images/logo.png">
  </a>
</div>
<div class="nav-wrap">
  <ul class="nav clearfix">
    <li class="pull-left"><i>1</i>填写手机号</li>
    <li class="pull-left"><i>2</i>重置密码</li>
    <li class="active pull-left"><i>3</i>修改完成</li>
  </ul>
</div>
<div class="body">
  <i class="success-icon"></i>
  <span class="success-txt">
    <p>恭喜修改成功！</p>
    <a href="/passport/login">请登录》</a>
  </span>
</div>
<!--主体ending-->
<!--主体ending-->
<jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>

</body>
</html>
<script src="/static/js/lib/md5.js"></script>
<script src="/static/js/user/reset_password.js"></script>
