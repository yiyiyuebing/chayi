<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息-优茶联</title>
	 <meta http-equiv="content-type" content="text/html;charset=utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>

    <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
    <link rel="stylesheet" type="text/css" href="/static/css/person.css">
    <link rel="stylesheet" type="text/css" href="/static/css/userinfo.css">
    <link rel="stylesheet" type="text/css" href="/static/js/lib/webuploader/webuploader.css">

<script>
var userInfo={
     url:"${buf}",
     idCardIndex:"${sv.storeRole.idCardIndex}",
     idCardBack:"${sv.storeRole.idCardBack}",
     businessLicence:"${sv.storeRole.businessLicence}",
     taxPhoto:"${sv.storeRole.taxPhoto}",
     headImgUrl:"${sv.headImgUrl}",
    provinceName:"${sv.provinceName}",
    countryName:"${sv.countryName}",
    cityName:"${sv.cityName}"
};
</script>
</head>
<%--头部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<%--头部 ending--%>

<%--查询 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/personal_header.jsp"></jsp:include>
<%--查询 ending--%>

<%--左侧工具栏 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<%--左侧工具栏 ending--%>

<body>
<div class="person-wrap">
        <div class="person-cont">
            <div class="content">
                <%--右侧导航栏 starting--%>
                <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
                <%--右侧导航栏 ending--%>
            <!-- 个人中心主内容 -->
                    <div class="right-cont-wrap">
                        <div>
                            <ul id="user-nav" class="nav-menu clearfix">
                                <li class="active"><a>个人信息</a></li>
                                <li><a>头像信息</a></li>
                            </ul>
                        </div>
                        <div id="user-tab-box">
                            <!--个人信息-->
                            <div class="user-info-wrap">
                                <div class="user-info-box" style="position: relative;">
                                    <div>
                                        <div class="enter-box">
                                        <form  id="verify"  >
                                            <table>
                                                <tr class="enter-bar option-box">
                                                    <input type="hidden" name="id" id="id" value="${sv.id}"/>
                                                    <td class="title-bar"><label>店铺名称：</label></td>
                                                    <td>
                                                        <span class="option-span">${sv.name}</span>
                                                        <input  name="name" id="name"  class="enter-ipt option-ipt hide" type="text" value="${sv.name}" />
                                                        <a class="blue-btn option-btn" style="margin-left: 10px">修改</a>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>手机号：</label></td>
                                                    <td>${sv.mobile}</td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>微信号：</label></td>
                                                    <td><span class="option-span">${sv.weixin}</span>
                                                        <input name="weixin" class="enter-ipt option-ipt hide" type="text" value="${sv.weixin}" />
                                                        <a class="blue-btn option-btn" style="margin-left: 10px">修改</a>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>茶掌柜：</label></td>
                                                    <td>
                                                        <input name="userName" id="userName" class="enter-ipt" type="text" value="${sv.userName}">
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>门店电话：</label></td>
                                                    <td><span id="phone">${sv.phone}</span></td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar option-box">
                                                    <td class="title-bar"><label>门店地址：</label></td>
                                                    <td>
                                                        <div style="position: relative;">
                                                            <input type="hidden"  id="provinceCode" value="${sv.province}"/>
                                                            <input type="hidden" id="cityCode" value="${sv.city}"/>
                                                            <input type="hidden" id="areaCode" value="${sv.country}"/>
                                                            <input type="hidden"  id="provinceName" value="${sv.provinceName}"/>
                                                            <input type="hidden" id="cityName" value="${sv.cityName}"/>
                                                            <input type="hidden" id="areaName" value="${sv.countryName}"/>
                                                            <input  type="text" id="locationChoose" readonly/>
                                                        <div class="detail-localtion" id="detail">
                                                        </div>
                                                        </div>
                                                      <%--  <span class="option-span">${sv.provinceName}&nbsp;${sv.countryName}&nbsp;${sv.cityName}</span>--%>
                                                        <%--input  name="provinceName"  id="provinceName"   class="enter-ipt option-ipt hide" type="text" value="${sv.provinceName}" /><br/>
                                                        <input  name="countryName"   id="countryName"  class="enter-ipt option-ipt hide" type="text" value="${sv.countryName}" /><br/>
                                                        <input  name="cityName"  id="cityName" class="enter-ipt option-ipt hide" type="text" value="${sv.cityName}" /><br/>--%>
                                                    </td>
                                                    <%--<td><a class="blue-btn option-btn">修改</a></td>--%>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>店铺详细地址：</label></td>
                                                    <td>
                                                        <textarea name="address" id="address" class="enter-textarea">${sv.address}</textarea>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>店铺简介：</label></td>
                                                    <td>
                                                        <textarea name="description" id="description" class="enter-textarea">${sv.description}</textarea>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td class="title-bar"><label>店铺照片：</label></td>
                                                    <td><span class="cl9 fs12">保证图片清晰度</span></td>
                                                    <td></td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td></td>
                                                    <td colspan="2">

                                                        <div id="store-photo-list" style="float: left">

                                                        </div>
                                                        <div style="float: left;">
                                                            <div class="btn-wrap" id="pick-store-photo">
                                                                <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                            </div>
                                                            <%--<div id="pick-store-photo">--%>
                                                                <%--<a href="javascript:void(0);">选择图片</a>--%>
                                                            <%--</div>--%>
                                                            <%--<div id="upload-store-photo" style="margin-left: 15px; margin-top: 10px;">--%>
                                                                <%--<a href="javascript:void(0);">上传图片</a>--%>
                                                            <%--</div>--%>
                                                        </div>

                                                    </td>
                                                </tr>
                                                <tr class="enter-bar">
                                                    <td></td>
                                                    <td class="bottom-line">
                                                        共<strong class="red total-num">0</strong>张，还能上传<strong class="red last-num">6</strong>张
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr class="enter-bar">
                                                    <td></td>
                                                    <td>
                                                    <input type="button" class="orange-btn" value="提交资料" onclick="verify()"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </table>
                                        </form>
                                        </div>
                                    </div>
                                    <div class="user-paperwork-wrap  info-position">
                                        <div>
                                            <p>个人身份证照<span class="small-tip">保证照片正方清晰</span></p>
                                            <div class="clearfix">
                                                <div class="upload-box">
                                                    <div class="upload-img" id="view-idcard-1">

                                                    </div>
                                                    <div class="btn-wrap" id="add-idcard-1">
                                                        <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                    </div>
                                                </div>
                                                <div class="upload-box">
                                                    <div class="upload-img" id="view-idcard-2">
                                                    </div>
                                                    <div class="btn-wrap" id="add-idcard-2">
                                                        <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="bottom-upload-box">
                                            <p>相关证件照<span class="small-tip">保证包含完整的登记信息</span></p>
                                            <div class="clearfix">
                                                <div class="upload-box">
                                                    <p class="tx-center">营业执照</p>
                                                    <div class="upload-img" id="view-business-license">
                                                    </div>
                                                    <div class="btn-wrap" id="pick-business-license">
                                                        <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                    </div>
                                                </div>
                                                <div class="upload-box">
                                                    <p class="tx-center">税务登记</p>
                                                    <div class="upload-img" id="view-tax">
                                                    </div>
                                                    <div class="btn-wrap" id="pick-tax">
                                                        <img src="/static/js/lib/webuploader/image/add.png" width="100" height="100"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--END个人信息-->
                            <!--头像信息-->
                            <div class="user-info-wrap webuploaderHide">
                                <div class="user-info-box clearfix">
                                    <div class="upload-header-img pull-left">
                                        <div class="choose-btn upload-icon" id="pick-head-btn">选择你要上传的头像</div>
                                        <p class="fs12 cl9">仅支持JPG PNG格式，文件应小于2M</p>
                                        <div class="has-upload" id="head-list-view"></div>
                                       <%-- <div class="mt20">
                                            <div class="recommend-wrap">
                                                <a class="save-btn">保存</a>
                                            </div>
                                        </div>--%>
                                    </div>
                                    <div class="header-result-box pull-left">
                                        <p><strong>效果预览</strong></p>
                                        <p class="fs12 cl9">你上传的图片会自动生成2种尺寸，请注意小尺寸头像是否清晰</p>
                                        <div>
                                            <div class="big-img">
                                                <img src="${sv.headImgUrl}">
                                            </div>
                                            <p class="fs12 cl6">100*100像素</p>
                                            <div class="small-img">
                                                <img src="${sv.headImgUrl}">
                                            </div>
                                            <p class="fs12 cl6">50*50像素</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--END头像信息-->
                        </div>
                    </div>
            <!-- END个人中心主内容 -->
        </div>
    </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/lib/webuploader/webuploader.min.js"></script>
<script src="/static/js/lib/webuploader/webuploaderUtil.js"></script>
<script src="/static/js/lib/citySelect/exchangeCityUtil.js"></script>
<script src="/static/js/user/userinfo.js"></script>



</html>