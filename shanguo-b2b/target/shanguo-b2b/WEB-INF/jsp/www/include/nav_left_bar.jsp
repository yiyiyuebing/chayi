<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--侧边栏-->
<div class="asidebar-wrap">
	<div class="code-wrap">
		<div class="code-content">
			<div class="code-triangle"></div>
			<img src="/static/images/app_download.png">
			<p>微信扫一扫，下载App</p>
		</div>
	</div>
	<a href="/cart/purchaseCart" class="shopping-wrap"> <i></i>
		<p>购</p>
		<p>物</p>
		<p>车</p> <span id="num">0</span>
	</a>
	<div class="person-center">
		<a href="/user/userInfo" class="center-icon"></a>
		<a href="/user/userInfo" class="center-txt">个人中心</a>
	</div>
	<div class="person-collect">
		<a href="/favorite/list" class="collect-icon"></a> <a
			href="/favorite/list" class="collect-txt">我的收藏</a>
	</div>
	<%--<div class="person-card">--%>
		<%--<a href="#" class="card-icon"></a> <a href="#" class="card-txt">我的卡券</a>--%>
	<%--</div>--%>
	<div class="person-history">
		<a href="/goodsBrowseLog/pug" class="history-icon"></a>
		<a href="/goodsBrowseLog/pug" class="history-txt" >浏览历史</a>
	</div>
	<a href="javascript:void(0);" target="_blank" class="person-qq"></a>
	<div class="home-back-wrap">
		<div class="home-back">
			<i></i> <span>TOP</span>
		</div>
	</div>
</div>
<script src="https://qiyukf.com/script/1a8fad464c1c676e72fd62c3d82937c0.js"></script>
<%--<script src="/static/js/cart/purchase_cart.js"></script>--%>
<%--<script src="/static/www/js/asidebar.js"></script>--%>