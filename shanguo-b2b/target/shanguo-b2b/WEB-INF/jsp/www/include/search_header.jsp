<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<!--顶部banner-->
	<div class="header-banner">
		<div class="header-banner-wrap">
			<%--<img src="/static/images/header_banner.png">--%>
		</div>
	</div>
	<!--搜索栏-->
	<div class="header-search clearfix">
		<div class="logo pull-left">
			<a href="/"> <img src="/static/images/logo.png">
			</a>
		</div>
		<div class="search-box pull-left">
			<div class="search-input clearfix">
				<input class="pull-left" type="text" placeholder="搜索您喜欢的好茶" value="${purchaseGoodsQuery.keyword}">
				<a href="javascript:void(0);" id="search-header" class="pull-left">搜索</a>
			</div>
			<div class="search-keywords clearfix">
				<%--关键词--%>
			</div>
		</div>
		<div class="search-message pull-right hide">
			<a href="javascript:void(0);" class="message-email-icon">
				<i>99+</i>
			</a>
			<div class="search-message-content" style="display: none;">
				<div class="search-message-content-triangle"></div>
				<div class="search-message-content-wrap">
					<div class="message-content-title clearfix">
						<a href="article_list_public.html" class="pull-left">公告</a>
						<a href="article_list_promotion.html" class="pull-left title-center">促销推送</a>
						<a href="notice.html" class="pull-left active">站内信</a>
					</div>
					<div class="message-content-body">
						<div class="message-content-body-group" style="display: none;">
							<ul>
								<li><a href="article_details.html">【福建龙井】秒杀低至29.9元</a></li>
								<li><a href="article_details.html">【福建龙井】秒杀低至29.9元</a></li>
								<li><a href="article_details.html">【福建龙井】秒杀低至29.9元</a></li>
								<li><a href="article_details.html">【福建龙井】秒杀低至29.9元</a></li>
							</ul>
							<a href="javascript:void(0);" class="more">查看更多</a>
						</div>
						<div class="message-content-body-group" style="display: none;">
							<ul>
								<li><a href="article_details.html">恭喜您！获得秒杀低至29.9元的机会</a></li>
								<li><a href="article_details.html">恭喜您！获得秒杀低至29.9元的机会</a></li>
								<li><a href="article_details.html">恭喜您！获得秒杀低至29.9元的机会</a></li>
								<li><a href="article_details.html">恭喜您！获得秒杀低至29.9元的机会</a></li>
							</ul>
							<a href="javascript:void(0);" class="more">查看更多</a>
						</div>
						<div class="message-content-body-group" style="display: block;">
							<ul>
								<li><a href="notice.html">恭喜您！新品上市团购价3折</a></li>
								<li><a href="notice.html">恭喜您！新品上市团购价3折</a></li>
								<li><a href="notice.html">恭喜您！新品上市团购价3折</a></li>
								<li><a href="notice.html">恭喜您！新品上市团购价3折</a></li>
							</ul>
							<a href="javascript:void(0);" class="more">查看更多</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<script src="/static/js/index/searchHeader.js"></script>

<script id="tpl-search-keywords" type="text/html">
	{{if linkType == 'good'}}
	<a class="pull-left hover-keywords" href="/purchase/detail/{{linkId}}.html">{{keyword}}</a>
	{{else if linkType == 'classify'}}
	<a class="pull-left hover-keywords" href="/purchase/list?classifyId={{linkId}}">{{keyword}}</a>
	{{else if linkType == 'diy'}}
	{{if linkDescribe}}
		<a class="pull-left hover-keywords" href="{{linkDescribe}}" target="_blank">{{keyword}}</a>
	{{else}}
		<a class="pull-left hover-keywords" href="javascript:void(0);">{{keyword}}</a>
	{{/if}}

	{{else}}
	<a class="pull-left hover-keywords" href="javascript:void(0)">{{keyword}}</a>
	{{/if}}

</script>

<script id="tpl-header-banner-wrap" type="text/html">
	{{if linkType == 'good'}}
	<a href="/purchase/detail/{{linkGoodId}}.html"><img src="{{imageUrl}}"/></a>
	{{else if linkType == 'classify'}}
	<a href="/purchase/list?classifyId={{lnkUrl}}"><img src="{{imageUrl}}"/></a>
	{{else if linkType == 'diy'}}
		{{if linkDescribe}}
		<a href="{{linkDescribe}}" target="_blank"><img src="{{imageUrl}}"/></a>
		{{else}}
		<a href="javascript:void(0);"><img src="{{imageUrl}}"/></a>
		{{/if}}
	{{else}}
	<a href="javascript:void(0);"><img src="{{imageUrl}}"/></a>
	{{/if}}
</script>