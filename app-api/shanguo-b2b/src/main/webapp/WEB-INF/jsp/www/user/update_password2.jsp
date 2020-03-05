<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码-优茶联</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
    <link rel="stylesheet" type="text/css" href="/static/css/person.css">
    <link rel="stylesheet" type="text/css"	href="/static/css/person_center_new_password.css">
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

<div class="person-wrap">
    <div class="person-cont">
        <div class="trading-content person-cont content  clearfix">
            <%--右侧导航栏 starting--%>
            <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
            <%--右侧导航栏 ending--%>
            <div class="change-password pull-left">
                <h4>修改密码</h4>
                <div class="change-step clearfix">
                    <div class="change-step-group pull-left">
                        <div class="step-group-inner">
                            <i>1</i>
                            <span>验证身份</span>
                        </div>
                    </div>
                    <div class="change-step-group step-active pull-left">
                        <i class="dot-left"></i>
                        <div class="step-group-inner">
                            <i>2</i>
                            <span>修改登录密码</span>
                        </div>
                        <i class="dot-right"></i>
                    </div>
                    <div class="change-step-group pull-left">
                        <div class="step-group-inner">
                            <i>3</i>
                            <span>完成</span>
                        </div>
                    </div>
                </div>
                <div class="change-form">
                    <input id="userId" type="hidden"/>
                    <div class="change-form-group clearfix">
                        <label class="pull-left">新的登录密码：</label>
                        <input type="password" id="password">
                    </div>
                    <p class="tip-gray">密码为6-20位数字及字母的组合</p>
                    <br/>
                   <%-- <p class="tips">密码格式不正确！</p>--%>
                    <div class="change-form-group clearfix">
                        <label class="pull-left">确认密码：</label>
                        <input type="password" id="password2">
                    </div>
                    <%--<p class="tips">两次密码不一致！</p>--%>
                    <br/>
                    <div class="change-form-group clearfix">
                        <label class="pull-left">验证码：</label>
                        <input class="pull-left" id="yzm" type="text">
                        <div class="code-img pull-left">
                            <img id="imgs" src="/passport/createValidateCode">
                        </div>
                        <a class="code-img-change pull-left">换一张</a>
                    </div>
                    <div class="form-submit">
                        <a class="formula"  onclick="PersonCommon.captcha2()">提交</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<script src="/static/js/lib/md5.js"></script>
<script src="/static/js/user/person.common.js"></script>
</html>