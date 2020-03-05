<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/8
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>设置用户名-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/register.css">
</head>
<body>
  <div class="password-header clearfix">
    <a href="/" class="pull-left">
      <img src="/static/images/logo.png">
    </a>
  </div>
  <div class="info-nav-wrap">
    <ul class="info-nav clearfix">
      <li class="active pull-left"><i>1</i>设置用户名</li>
      <li class="pull-left"><i>2</i>完善个人资料</li>
      <li class="pull-left"><i>3</i>平台审核</li>
      <li class="pull-left"><i>4</i>注册成功</li>
    </ul>
  </div>
  <div class="body">
    <div>
      <div class="input-tel clearfix">
        <label class="pull-left">手机号</label>
        <input class="pull-left" type="text" name="mobile" placeholder="请输入您的手机号码">
      </div>
      <p class="tips hide" >请输入正确的手机号码！</p>
    </div>
    <div>
      <div class="input-password clearfix" style="margin-top: 20px;">
        <label class="pull-left">设置密码</label>
        <input class="pull-left" type="password" name="password" placeholder="请输入密码（必填）">
      </div>
      <p class="tips hide" >密码为6-20位数字及字母的组合</p>
    </div>
    <div style="margin-bottom: 32px;">
      <div class="input-invite-tel clearfix" style="margin-top: 20px;">
        <label class="pull-left">邀请人</label>
        <input class="pull-left" type="text" name="invitePhone" placeholder="请输入邀请人手机号码（选填）">
      </div>
      <p class="tips hide" >请输入正确的手机号码！</p>
    </div>

    <div style="margin-bottom: 32px;">
      <div class="input-img-code clearfix" style="margin-top: 20px;">
        <label class="pull-left">校验码</label>
        <input class="pull-left" id="yzm"  type="text" placeholder="请输入校验码">
        <div class="code-img pull-left">
          <img id="imgs" src="/passport/createValidateCode">
        </div>
        <a class="code-img-change" href="javascript:;">换一张</a>
      </div>
    </div>

    <div>
      <div class="input-code clearfix">
        <label class="pull-left">手机验证码</label>
        <input class="pull-left" type="text"  name="telCode" placeholder="请输入手机验证码">
        <div class="code pull-right get-code">
          <a onclick="register.captcha()">获取验证码</a>
        </div>
      </div>
      <p class="tips hide">验证码不能为空！</p>
    </div>
    <div class="input-policy">
      <label id="policy">
        <input type="checkbox" name="policy" value="agree">
        <div class="policy-mask clearfix">
          <i class="pull-left"></i>
          <span class="pull-left">我已经阅读并同意<a href="javascript:;" id="policyBtn">《服务条款及隐私政策》</a></span>
        </div>
      </label>
    </div>
    <div class="next">
      <a class="disable" href="javascript:void(0);">下一步</a>
    </div>
  </div>

  <%--服务条款及隐私政策弹窗--%>
  <div id="serverPop" style="display: none;">
    <div class="serverPop-box">
      <div class="serverPop-wrap">
        <div class="serverPop-header">
          <h5>优茶联合作协议</h5>
          <i id="serverPopClose"></i>
        </div>
        <div class="serverPop-body">
          <h4>优茶联合作协议</h4>
          <div class="serverTxt">
            　${commonText.content}
          </div>
        </div>
        <div class="serverPop-footer">
          <a href="javascript:void(0)" class="serverPop-sure">我已阅读</a>
        </div>
      </div>
    </div>
  </div>
</body>
<jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>
<script src="/static/js/lib/md5.js"></script>
<script src="/static/js/user/register.js"></script>
</html>

