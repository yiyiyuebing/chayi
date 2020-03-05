<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收货地址-优茶联</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="/static/css/person_header.css">
	<link rel="stylesheet" type="text/css" href="/static/css/person.css">
	<link rel="stylesheet" type="text/css"
	href="/static/css/receipt_address.css">
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
			<!-- 个人中心左侧菜单栏 -->
			<%--右侧导航栏 starting--%>
			<jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
			<%--右侧导航栏 ending--%>
			<!-- END个人中心左侧菜单栏 -->
			<!-- 个人中心主内容 -->
			<div class="right-cont-wrap receipt-address-wrap">
				<div id="dialog-box" class="dialog-wrap hide">
					<div class="cont">
						<div class="title">
							<strong>新建收货地址</strong>
							<a class="del-btn close-dialog-btn"></a>
						</div>
						<table class="data-table">
                          <form id="address">
							<input type="hidden"  id="addressId" />
							<tr>
								<td>
									<p><label class="title-bar">收货人：</label></p>
									<p><input class="enter-ipt" type="text"  id="receiptName" ></p>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<p><label class="title-bar">所在地区：</label></p>
									<div style="position: relative;">
										<input type="hidden"  id="provinceCode" />
										<input type="hidden" id="cityCode"/>
										<input type="hidden" id="areaCode"/>
										<input type="hidden"  id="provinceName" />
										<input type="hidden" id="cityName"/>
										<input type="hidden" id="areaName"/>
										<input  type="text" id="locationChoose" readonly/>
									    <div class="detail-localtion" id="detail">
									    </div>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<p><label class="title-bar">详细地址：</label></p>
									<p><input class="enter-ipt" type="text" id="detailAddr" ></p>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<p><label class="title-bar">手机号码：</label></p>
									<p><input class="enter-ipt" type="text" id="mobile"  ></p>
								</td>
								<td><span class="cl9 middle-line">或</span></td>
								<td>
									<p><label class="title-bar">国定电话：</label></p>
									<p><input class="enter-ipt" type="text" id="fiexdPhone" ></p>
								</td>
							</tr>
							<tr>
								<td><input  type="button" class="commit-btn" value="保存收货地址" onclick="ReceiptAddress.upAdd()"/>
									</td>
								<td></td>
								<td></td>
							</tr>
						  </form>
						</table>
					</div>
				</div>
				<div class="clearfix">
					<a class="add-btn open-dialog-btn1" id="add" >新增收货地址</a>
					<span class="title-tip">您已创建<span class="blue" id="size" ></span>个收货地址，最多可创建<span class="blue">20</span>个</span>
				</div>
				<ul class="list-wrap">

				</ul>
			</div>
			<!-- END个人中心主内容 -->
		</div>
	</div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>

</html>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/lib/citySelect/exchangeCityUtil.js"></script>
<script src="/static/js/user/receiptaddress.js"></script>
<script id="tpl-list-address" type="text/html">

	{{each graList as gra}}
	<li>
		<p>{{gra.receiptName}}-{{gra.provinceName}}{{if gra.status == 1}}<i class="default-icon">默认地址</i>{{/if}}</p>
		<div class="cont">
			<input  type="hidden" id="userId"  value="{{gra.userId}}" />
			<p class="clearfix"><span class="left-title">收货人：</span><span class="pull-left">{{gra.receiptName}}</span></p>
			<p class="clearfix"><span class="left-title">所在地区：</span><span class="pull-left">{{gra.provinceName}}&nbsp;{{gra.cityName}}&nbsp;{{gra.areaName}}</span></p>
			<p class="clearfix"><span class="left-title">地址：</span><span class="pull-left">{{gra.detailAddr}}</span></p>
			<p class="clearfix"><span class="left-title">手机：</span><span class="pull-left">
				{{if gra.mobile}}
					{{gra.mobile}}
				{{/if}}
			</span></p>
			<p class="clearfix"><span class="left-title">固定电话：</span>
				<span class="pull-left">
					{{if gra.fiexdPhone}}
						{{gra.fiexdPhone}}
					{{/if}}
				</span>
			</p>
		</div>
		<div class="option-wrap">
				{{if gra.status == 0}}
				<a class="blue mr-tagt" data-id="{{gra.id}}">设置默认</a>
				{{/if}}
			<a class="blue open-dialog-btn" data-id="{{gra.id}}"  id="bj" >编辑</a>
			<a class="blue del-tagt" data-id="{{gra.id}}" >删除</a>
		</div>
	</li>
	{{/each}}

</script>