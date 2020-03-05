<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- 个人中心左侧菜单栏 -->
<div class="left-nav-wrap">
    <ul id="person-left-nav" class="first-nav">
        <%--<li class="first-item active">
            <a class="first-text">账户管理</a>
        </li>--%>
        <li class="first-item">
            <a class="first-text">订单中心</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a class="child-text" href="/order/list">进货单</a>
                </li>
                <li class="child-item">
                    <a class="child-text" href="/cart/purchaseCart">购物车</a>
                </li>
                <li class="child-item">
                    <a class="child-text" href="/order/precellOrderList">预售订单</a>
                </li>
                <li class="child-item">
                    <a class="child-text" href="/evaluate/evaluatelist">评价管理</a>
                </li>
            </ul>
        </li>
        <li class="first-item">
            <a class="first-text">收藏足迹</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a class="child-text" href="/favorite/list">我的收藏</a>
                </li>
                <li class="child-item">
                    <a class="child-text" href="/goodsBrowseLog/pug">我的足迹</a>
                </li>
            </ul>
        </li>
        <%--<li class="first-item">
            <a class="first-text">我的采购</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a class="child-text">采购需求</a>
                </li>
            </ul>
        </li>
        <li class="first-item">
            <a class="first-text">我的订阅</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a class="child-text">站内信</a>
                </li>
            </ul>
        </li>--%>
        <%--active--%>
        <li class="first-item">
            <a class="first-text">账户中心</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a class="child-text" href="/user/userInfo">账户信息</a>
                </li>
               <%-- <li class="child-item">
                    <a class="child-text">充值账户</a>
                </li>
                <li class="child-item">
                    <a class="child-text">我的积分</a>
                </li>
                <li class="child-item">
                    <a class="child-text">我的红包</a>
                </li>--%>
                <li class="child-item">
                    <a class="child-text" href="/user/site">收货地址</a>
                </li>
                <li class="child-item">
                    <a class="child-text" href="/user/updatePassword" >修改密码</a>
                </li>
            </ul>
        </li>
        <li class="first-item">
            <a class="first-text">客户服务</a>
            <ul class="child-nav">
                <li class="child-item">
                    <a href="/afterSale/afterSaleList" class="child-text">退换货管理</a>
                </li>
                <%--<li class="child-item">
                    <a class="child-text">投诉建议</a>
                </li>--%>
            </ul>
        </li>
    </ul>
</div>