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
<link rel="stylesheet" type="text/css"
	<jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
	<link rel="stylesheet" type="text/css" href="/static/css/person.css">
	<link rel="stylesheet" type="text/css"	href="/static/css/person_center_password_success.css">
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
	<div class="person-cont">
		<div class="trading-content person-cont content  clearfix">
		<%--右侧导航栏 starting--%>
		<jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
		<%--右侧导航栏 ending--%>
		<div class="change-password pull-left">
			<h4>修改密码</h4>
			<div class="change-password pull-left">
				<div class="change-step clearfix">
					<div class="change-step-group bg-yellow pull-left">
						<div class="step-group-inner">
							<i class="bg-yellow">1</i> <span>验证身份</span>
						</div>
					</div>
					<div class="change-step-group bg-yellow pull-left">
						<i class="dot-left bg-yellow"></i>
						<div class="step-group-inner">
							<i class="bg-yellow">2</i> <span>修改登录密码</span>
						</div>
					</div>
					<div class="change-step-group step-active pull-left">
						<i class="dot-left"></i>
						<div class="step-group-inner">
							<i>3</i> <span>完成</span>
						</div>
						<i class="dot-right"></i>
					</div>
				</div>
				<div class="change-form">
					<img src="/static/images/send_success.png">
					<p>恭喜您，修改密码成功！</p>
				</div>
			</div>
		</div>
</div>
</body>

<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/user/person.common.js"></script>
</html>