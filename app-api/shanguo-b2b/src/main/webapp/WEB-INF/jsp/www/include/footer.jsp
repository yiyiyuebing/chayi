<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<div class="footer-banner">
        <%--<img src="/static/images/footer_banner.png">--%>
    </div>
    <div class="footer-list-wrap">
        <div class="footer-list clearfix">
            <dl class="pull-left">
                <dt>购物指南</dt>
                <dd><a href="javascript:void(0);">登录注册</a></dd>
                <dd><a href="javascript:void(0);">采购商品</a></dd>
                <dd><a href="javascript:void(0);">购物车</a></dd>
                <dd><a href="javascript:void(0);">下单支付</a></dd>
            </dl>
            <dl class="pull-left">
                <dt>帮助中心</dt>
                <dd><a href="javascript:void(0);">购物疑问</a></dd>
                <dd><a href="javascript:void(0);">投诉通道</a></dd>
            </dl>
            <dl class="pull-left">
                <dt>支付方式</dt>
                <dd><a href="javascript:void(0);">微信支付</a></dd>
                <dd><a href="javascript:void(0);">支付宝</a></dd>
            </dl>
            <dl class="pull-left">
                <dt>售后服务</dt>
                <dd><a href="javascript:void(0);">品质保障</a></dd>
                <dd><a href="javascript:void(0);">退换货流程</a></dd>
                <dd><a href="javascript:void(0);">退货流程</a></dd>
                <dd><a href="javascript:void(0);">咨询投诉</a></dd>
                <dd><a href="javascript:void(0);">售后说明</a></dd>
            </dl>
            <dl class="pull-left">
                <dt>配送说明</dt>
                <dd><a href="javascript:void(0);">配送范围</a></dd>
                <dd><a href="javascript:void(0);">配送费用</a></dd>
                <dd><a href="javascript:void(0);">配送时间</a></dd>
                <dd><a href="javascript:void(0);">货到付款</a></dd>
                <dd><a href="javascript:void(0);">验收说明</a></dd>
            </dl>
            <dl class="pull-left">
                <dt>关于我们</dt>
                <dd><a href="javascript:void(0);">关于优茶联</a></dd>
                <dd><a href="javascript:void(0);">总部图库</a></dd>
                <dd><a href="javascript:void(0);">联系我们</a></dd>
            </dl>
            <div class="footer-code pull-right">
                <div class="footer-code-wrap">
                    <img src="/static/images/youchalian_qcrode.jpg">
                </div>
                <p>扫一扫，关注我们</p>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/www/include/footer_link.jsp"></jsp:include>

    <jsp:include page="/WEB-INF/jsp/www/include/footer_bottom.jsp"></jsp:include>

<script src="/static/js/index/footer.js"></script>

<script id="tpl-footer-banner" type="text/html">
    {{if linkType == 'good'}}
    <a href="/purchase/detail/{{linkGoodId}}.html"><img src="{{imageUrl}}"/></a>
    {{else if linkType == 'classify'}}
    <a href="/purchase/list?classifyId={{lnkUrl}}"><img src="{{imageUrl}}"/></a>
    {{else if linkType == 'diy'}}
        {{if linkDescribe}}
        <a href="{{linkDescribe}}"  target="_blank"><img src="{{imageUrl}}"/></a>
        {{else}}
        <a href="javascript:void(0);"><img src="{{imageUrl}}"/></a>
        {{/if}}
    {{else}}
    <a href="javascript:void(0);"><img src="{{imageUrl}}"/></a>
    {{/if}}
</script>

