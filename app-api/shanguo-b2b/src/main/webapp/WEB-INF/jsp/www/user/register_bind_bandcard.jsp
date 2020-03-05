<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/8/2
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>绑定银行卡-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/user_register_bindCard.css">
</head>
<body>

<div class="password-header clearfix">
  <a href="" class="pull-left">
    <img src="/static/images/logo.png">
  </a>
</div>
<div class="nav-wrap">
  <ul class="nav clearfix">
    <li class="pull-left"><i>1</i>完善店铺资料</li>
    <li class="active pull-left"><i>2</i>完善银行卡信息</li>
  </ul>
</div>
<div class="body clearfix">
  <div>
    <div class="input-group clearfix">
      <input type="hidden" name="shopId" value="${shopId}">
      <label class="pull-left">开户人：</label>
      <input class="normal pull-left" type="text" name="name">
      <span class="input-tips pull-left">请填写持卡人真实姓名</span>
    </div>
    <p class="tips hide">开户人不能为空</p>
  </div>
  <div>
    <div class="input-group clearfix">
      <label class="pull-left">身份证：</label>
      <input class="normal pull-left" type="text" name="idCard">
      <span class="input-tips pull-left"></span>
    </div>
    <p class="tips hide">身份证不能为空</p>
  </div>

  <div class="input-group clearfix">
    <label class="pull-left">所属银行：</label>
    <input type="hidden" name="bankName" value="建设银行">
    <div class="bank-select pull-left">
      <a href="javascript:;" id="bankClassifyBtn" class="bank-classify pull-left">建设银行</a>
      <ul id="bankClassifyList" class="bank-list">
        <li data-bankKind="中国银行">中国银行</li>
        <li data-bankKind="建设银行">建设银行</li>
        <li data-bankKind="农业银行">农业银行</li>
        <li data-bankKind="浦发银行">浦发银行</li>
        <li data-bankKind="厦门银行">厦门银行</li>
      </ul>
    </div>
  </div>
  <div>
    <div class="input-group clearfix">
      <label class="pull-left">银行卡号：</label>
      <input class="bank-number pull-left" type="text" name="bankCard">
    </div>
    <p class="tips hide">银行卡号不能为空</p>
  </div>
  <div>
    <div class="input-group clearfix">
      <label class="pull-left">手机号码：</label>
      <input class="normal pull-left" type="text" name="mobile" value="">
      <span class="input-tips pull-left">请填写该卡在银行预留的手机号码</span>
    </div>
    <p class="tips hide">手机号码不能为空</p>
  </div>
  <div>
    <div class="input-group clearfix">
      <label class="pull-left">图片验证码：</label>
      <input class="pull-left" type="text" name="codeImg">
      <img class="imgCode-img pull-left" src="/passport/createValidateCode" alt="点击更换验证码">
    </div>
    <p class="tips hide">图片验证码不能为空</p>
  </div>
  <div>
    <div class="input-group clearfix">
      <label class="pull-left">验证码：</label>
      <input class="pull-left" type="text" name="msgYzm">
      <a href="javascript:void(0);" class="getYzm pull-left">获取验证码</a>
      <a href="#" class="pull-left none-message">没有收到短信？</a>
    </div>
    <p class="tips hide">验证码不正确</p>
  </div>
  <div class="input-group clearfix">
    <label class="pull-left">&nbsp;</label>
    <a class="ensure pull-left submit-info" href="javascript:void(0);">确定</a>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>
</body>
</html>
<script src="/static/js/user/user_register_bindcard.js"></script>