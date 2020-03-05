<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/6
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/login.css">
</head>
<body>
  <div class="login-header clearfix">
    <a href="/" class="pull-left">
      <img src="/static/images/logo.png">
    </a>
  </div>
  <div class="login-body">
    <div class="login-body-wrap">
      <img src="/static/images/banner.jpg">
    </div>
    <div class="login-form-wrap">
      <div class="login-form">
        <h4>手机登录</h4>
        <form id="login-form">
          <div class="input-group username" style="margin-bottom: 20px;">
            <i class="icon-tel"></i>
            <input type="text" name="phone" placeholder="请输入您的手机号码" autocomplete="off">
            <p class="input-tips" style="display: none;">请输入正确的手机号码！</p>
          </div>
          <div class="input-group password" style="margin-bottom: 20px;">
            <i class="icon-pwd"></i>
            <input type="password" name="password" placeholder="请输入密码">
            <p class="input-tips" style="display: none;">密码错误！</p>
          </div>
          <div class="submit">
            <a href="javascript:void(0);" onclick="Login.doLogin()">立即登录</a>
          </div>
          <div class="forget-link clearfix">
            <div class="pull-right clearfix">
              <a href="/passport/resetPasswordTel" class="pull-left">忘记密码</a>
              <a href="/passport/register" class="pull-left">免费注册</a>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>
<script src="/static/js/lib/md5.js"></script>
<script src="/static/js/user/login.js"></script>
