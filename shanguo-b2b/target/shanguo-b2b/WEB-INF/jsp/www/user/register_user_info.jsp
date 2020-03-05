<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/21
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>完善个人资料-优茶联</title>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">
  <link rel="stylesheet" href="/static/css/user_information_complete.css">
</head>
<body>
<!--主体-->
<div class="password-header clearfix">
  <a href="/" class="pull-left">
    <img src="/static/images/logo.png">
  </a>
</div>
<div class="nav-wrap">
  <ul class="nav clearfix" style="border-bottom: none;">
    <li class="pull-left"><i>1</i>设置用户名</li>
    <li class="active pull-left"><i>2</i>完善个人资料</li>
    <li class="pull-left"><i>3</i>平台审核</li>
    <li class="pull-left"><i>4</i>注册成功</li>
  </ul>
</div>
<div class="body clearfix">
  <div class="left-form pull-left">
    <input type="hidden" name="action" value="add"/>
    <form id="user-form">
      <input type="hidden" name="moblie" value="${moblie}"/>
      <div style="margin-bottom: 15px;">
        <div class="input-group clearfix">
          <label class="pull-left">店铺名称</label>
          <input class="shop-name pull-left" type="text" name="name" placeholder="请输入店铺名称">
          <i class="length-limit10 pull-left">0/10</i>
        </div>
        <p class="tips hide">店铺名称不能为空</p>
      </div>
      <div style="margin-bottom: 15px;">
        <div class="input-group clearfix">
          <label class="pull-left">茶掌柜</label>
          <input class="pull-left" type="text" name="userName" placeholder="请输入掌柜姓名">
        </div>
        <p class="tips hide">茶掌柜不能为空</p>
      </div>
      <div style="margin-bottom: 15px;">
        <div class="input-group clearfix">
          <label class="pull-left">门店电话</label>
          <input class="pull-left" type="text" name="phone" placeholder="请输入门店电话">
        </div>
        <p class="tips hide">门店电话不能为空</p>
      </div>
      <div style="margin-bottom: 15px;">
        <div class="input-group clearfix">
          <label class="pull-left">地址</label>
          <input  name="provinceName" type="hidden"/>
          <input  name="countryName" type="hidden"/>
          <input  name="cityName" type="hidden"/>
          <input  name="province" type="hidden"/>
          <input  name="country" type="hidden"/>
          <input  name="city" type="hidden"/>
          <input id="addressSimple" class="address-simple pull-left" type="text" placeholder="请选择门店地址" value="">
          <div class="express-wrap pull-left">
            <span class="location-choose pull-left clearfix" style="margin-bottom: 10px;" id="register-user-location-click">请选择门店地址<i></i></span>
            <div id="register-user-location-select" style="top: 50px!important;">
            </div>
          </div>
        </div>
        <p class="tips hide">地址不能为空</p>
      </div>
      <div style="margin-bottom: 15px;">
        <div class="input-group clearfix">
          <label class="pull-left">详细地址</label>
          <input class="pull-left" type="text" name="address" placeholder="请输入门店详细地址">
        </div>
        <p class="tips hide">详细地址不能为空</p>
      </div>

      <div class="shop-desc clearfix">
        <h5>店铺简介</h5>
        <textarea class="shop-input-desc" name="description" placeholder="请跟我说说你的店铺事迹吧~"></textarea>
        <i class="length-limit120">0/120</i>
      </div>
    </form>
  </div>
  <div class="right-img pull-right">
    <div class="right-img-group">
      <div class="img-group-title clearfix">
        门店照片<span>保证图片的清晰度</span>
      </div>
      <div class="shop-file clearfix">
        <div class="shop-img-list upload-img clearfix" id="head-list-view" style="float: left;">
        </div>
        <div class="btn-wrap" id="pick-head"  style="float: left;">
          <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
        </div>
      </div>
      <div class="img-count hide">共<span class="camera-img-uploaded">0</span>张，还能上传<span class="camera-img-uploadable">4</span>张</div>
    </div>
    <div class="right-img-group" style="width: 360px;">
      <div class="img-group-title clearfix">
        个人身份证照<span>保证照片正反面清晰</span>
      </div>
      <div class="input-file clearfix">
        <div class="input-file-group pull-left">
          <div class="upload-img" id="view-idcard-z">
          </div>
          <div class="btn-wrap" id="pick-idcard-z">
            <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
          </div>
        </div>
        <div class="input-file-group pull-right">
          <div class="upload-img" id="view-idcard-f">
          </div>
          <div class="btn-wrap" id="pick-idcard-f">
            <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
          </div>
        </div>
      </div>
    </div>
    <div class="right-img-group"  style="width: 360px;">
      <div class="img-group-title clearfix">
        营业执照/税务登记 （选填）<span>保证照片正反面清晰</span>
      </div>
      <div class="input-file clearfix">
        <div class="input-file-group pull-left">
          <div class="upload-img" id="view-business">
          </div>
          <div class="btn-wrap" id="pick-business">
            <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
          </div>
        </div>
        <div class="input-file-group pull-right">
          <div class="upload-img" id="view-tax">
          </div>
          <div class="btn-wrap" id="pick-tax">
            <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="clearfix"></div>
  <div class="input-submit">
    <a href="javascript:void(0)">提交资料</a>
  </div>
</div>

<jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>
</body>
</html>
<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>
<script src="/static/js/lib/citySelect/exchangeCityUtil.js"></script>
<script src="/static/js/user/register_user_info.js"></script>
