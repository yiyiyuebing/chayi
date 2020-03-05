<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/26
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>填写手机号-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/password_reset_tel.css">
</head>
<body>

  <!--主体-->
  <div class="password-header clearfix">
    <a href="/" class="pull-left">
      <img src="/static/images/logo.png">
    </a>
  </div>
  <div class="nav-password">
    <ul class="nav-password-wrap clearfix">
      <li class="active pull-left"><i>1</i>填写手机号码</li>
      <li class="pull-left"><i>2</i>重置密码</li>
      <li class="pull-left"><i>3</i>修改成功</li>
    </ul>
  </div>
  <div class="body">
    <div style="margin-bottom: 10px;">
      <div class="input-tel clearfix">
        <label class="pull-left">手机号</label>
        <input class="pull-left" type="text" name="mobile" placeholder="请输入您的手机号码">
      </div>
      <p class="tips hide">请输入正确的手机号码！</p>
    </div>
    <div style="margin-bottom: 10px;">
      <div class="input-img-code clearfix">
          <label class="pull-left">验证码</label>
          <input class="pull-left" id="yzm"  type="text" placeholder="请输入验证码">
          <div class="code-img pull-right">
            <img id="imgs" src="/passport/createValidateCode">
          </div>
          <a class="code-img-change" href="javascript:;">换一张</a>
      </div>
    </div>
    <div style="margin-bottom: 10px;">
      <div class="input-code clearfix">
        <label class="pull-left">手机验证码</label>
        <input class="pull-left" type="text" name="code" placeholder="请输入手机验证码">
        <div class="code get-code pull-right">
          <a onclick="resetPasswordTel.captcha();">获取验证码</a>
        </div>
      </div>
      <%--<p class="tips hide">验证码不能为空！</p>--%>
    </div>

    <div class="next">
      <a class="next-btn" href="javascript:void(0);">下一步</a>
    </div>
  </div>
  <!--主体ending-->
  <jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>
</body>
</html>
<script src="/static/js/user/reset_password_tel.js"></script>
