<%@ page import="pub.makers.shop.user.utils.AccountUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script src="https://s13.cnzz.com/z_stat.php?id=1263790505&web_id=1263790505" language="JavaScript"></script>
	<!--顶部-->
	<div class="header-wrap">
		<input type="hidden" name="shopId" value="<%=AccountUtils.getCurrShopId(false)%>">
		<div class="header clearfix">
			<div class="location pull-left">
				<p class="loaction-show" >
					<span class="location-id" id="select-header-province" data-id="350000">福建<i></i></span>
				</p>
				<div class="location-wrap" id="header-location-select">
				</div>
			</div>
			<div class="pull-left welcome">
				<div class="welcome-txt clearfix" id="welcomeTxt">
					<span class="welcome-t pull-left">欢迎光临优茶联官网</span>
					<span class="welcome-t pull-left">欢迎光临优茶联官网</span>
				</div>
			</div>
			<div class="register-group clearfix pull-left">
				<%
				if (StringUtils.isNotBlank(AccountUtils.getCurrShopId(false))) {

					String shopName = AccountUtils.getCurrShopName();

					String html = "<a class=\"pull-left\" href=\"/user/userInfo\">"+ shopName +"</a>";
					html += "<i class=\"vertical-line pull-left\"></i>";
					html += "<a class=\"pull-left login-out\" onclick=\"Header.loginOut()\" href=\"javascript:void(0);\">退出</a>";
					out.write(html);
				} else {
					String html = "<a class=\"pull-left\" href=\"/passport/login\">会员登录</a>";
					html += "<i class=\"vertical-line pull-left\"></i>";
					html += "<a class=\"pull-left\" href=\"/passport/register\">免费注册</a>";
					out.write(html);
				}
				%>
				<%--<a class="pull-left" href="/passport/login">会员登录</a>--%>
				<%--<i class="vertical-line pull-left"></i>--%>
				<%--<a class="pull-left" href="/passport/register">免费注册</a>--%>
			</div>
			<div class="mineYcl pull-left">
				<a href="javascript:void(0);" class="mineYcl-span">我的优茶联<i></i></a>
				<ul class="minYcl-wrap clearfix">
					<li class="pull-left"><a href="/user/userInfo">店铺资料</a></li>
					<%--<li class="pull-left"><a href="#">子账户</a></li>--%>
					<li class="pull-left"><a href="/order/list">进货单</a></li>
					<%--<li class="pull-left"><a href="#">卡券</a></li>--%>
					<%--<li class="pull-left"><a href="rechargeaccount.html">钱包</a></li>--%>
					<%--<li class="pull-left"><a
						href="person_center_change_password.html">设置</a></li>--%>
				</ul>
			</div>
			<div class="help-list pull-left clearfix">
				<%--<a class="pull-left" href="purchase.html">采购单</a> --%>
				<i class="vertical-line pull-left"></i> <a class="pull-left" href="javascript:void(0);">商务合作</a>
				<i class="vertical-line pull-left"></i>
				<div class="pull-left cooperation hide">
					<span>手机商城</span>
					<div class="cooperation-wrap">
						<img src="/static/images/youchalian_qcrode.jpg">
						<p>微信扫一扫，关注一下</p>
					</div>
				</div>
				<i class="vertical-line pull-left"></i> <a class="pull-left"
														   href="javascript:void(0);">帮助中心</a>
			</div>
			<div class="tel pull-left clearfix">
				<a href="javascript:void(0);" class="pull-left tel-icon"></a> <span class="pull-left">客服电话：<mark>400-8396-815</mark></span>
			</div>
			<div class="collect pull-right clearfix">
				<span class="pull-left favorite hide" onclick="Header.addFavorite1()">收藏</span> <i
					class="vertical-line pull-left margin-line"></i>
				<div class="web-nav web-nav-active pull-left">
					<span>网站导航</span>
					<div class="web-nav-content clearfix">
						<div class="head-classify">
							<%--<div class="web-nav-group pull-left">
								<h5>精品茶</h5>
								<ul class="clearfix">
									<li class="pull-left"><a href="product_details.html">铁观音</a></li>
									<li class="pull-left"><a href="product_details.html">红茶</a></li>
									<li class="pull-left"><a href="product_details.html">绿茶</a></li>
									<li class="pull-left"><a href="product_details.html">白茶</a></li>
									<li class="pull-left"><a href="product_details.html">岩茶</a></li>
									<li class="pull-left"><a href="product_details.html">普洱茶</a></li>
									<li class="pull-left"><a href="product_details.html">乌龙茶</a></li>
									<li class="pull-left"><a href="product_details.html">其他茶类</a></li>
									<li class="pull-left"><a href="product_details.html">五福金砖</a></li>
								</ul>
							</div>
							<i class="pull-left web-nav-line"></i>
							<div class="web-nav-group pull-left">
								<h5>茶具</h5>
								<ul class="clearfix">
									<li class="pull-left"><a href="product_details.html">茶具套组</a></li>
									<li class="pull-left"><a href="product_details.html">茶盘</a></li>
									<li class="pull-left"><a href="product_details.html">单壶</a></li>
									<li class="pull-left"><a href="product_details.html">个人杯</a></li>
									<li class="pull-left"><a href="product_details.html">电子</a></li>
									<li class="pull-left"><a href="product_details.html">茶宠摆件</a></li>
									<li class="pull-left"><a href="product_details.html">辅助工具</a></li>
								</ul>
							</div>
							<i class="pull-left web-nav-line"></i>--%>
						</div>
						<div class="web-nav-group pull-left">
							<h5>更多精选</h5>
							<ul class="clearfix more-classify">
								<%--<li class="pull-left"><a href="product_details.html">茶食</a></li>
								<li class="pull-left"><a href="product_details.html">茶配套</a></li>
								<li class="pull-left"><a href="product_details.html">散茶</a></li>
								<li class="pull-left"><a href="product_details.html">包装</a></li>
								<li class="pull-left"><a href="product_details.html">定制茶</a></li>--%>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<script id="tpl-location-group-li" type="text/html">
	<li class="pull-left" data-id="{{code}}">{{name}}</li>
</script>

<script id="tpl-more-classify" type="text/html">
	<li class="pull-left"><a href="/purchase/list?classifyId={{id}}">{{name}}</a></li>
</script>

<script id="tpl-head-classify-list" type="text/html">
	<div class="web-nav-group pull-left">
		<h5>{{name}}</h5>
		<ul class="clearfix">
			{{if children}}
				{{each children as childClassify index}}
					{{if index < 8}}
						<li class="pull-left"><a href="/purchase/list?classifyId={{childClassify.id}}">{{childClassify.name}}</a></li>
					{{/if}}
				{{/each}}
			{{/if}}
		</ul>
	</div>
	<i class="pull-left web-nav-line"></i>
</script>
<%--<script src="/static/js/common/header.js"></script>--%>
