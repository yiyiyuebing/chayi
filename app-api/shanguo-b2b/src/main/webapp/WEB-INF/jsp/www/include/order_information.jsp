<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/16
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单信息-优茶联</title>
</head>
<body>
<div class="order-info">
    <div class="ui-switchable-body clearfix">
        <div class="dl">
            <strong class="dt block">收货人信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">收货人：</span>
                    <div class="info-rcol">哈哈哈</div>
                </div>
                <div class="item clearfix">
                    <span class="label">地址：</span>
                    <div class="info-rcol">详细地址是很远的一个地方详细地址是很远的一个地方</div>
                </div>
                <div class="item clearfix">
                    <span class="label">手机号码：</span>
                    <div class="info-rcol">133344444</div>
                </div>
            </div>
        </div>
        <div class="dl">
            <strong class="dt block">配送信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">配送方式：</span>
                    <div class="info-rcol">普通快递</div>
                </div>
                <div class="item clearfix">
                    <span class="label">运费：</span>
                    <div class="info-rcol">¥0.00</div>
                </div>
                <div class="item clearfix">
                    <span class="label">送货日期：</span>
                    <div class="info-rcol">2017-5-25</div>
                </div>
            </div>
        </div>
        <div class="dl">
            <strong class="dt block">付款信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">付款方式：</span>
                    <div class="info-rcol">在线支付</div>
                </div>
                <div class="item clearfix">
                    <span class="label">商品总额：</span>
                    <div class="info-rcol">¥0.00</div>
                </div>
                <div class="item clearfix">
                    <span class="label">应支付金额：</span>
                    <div class="info-rcol">¥0.00</div>
                </div>
                <div class="item clearfix">
                    <span class="label">运费金额：</span>
                    <div class="info-rcol">¥0.00</div>
                </div>
                <div class="item clearfix">
                    <span class="label">订单优惠：</span>
                    <div class="info-rcol">¥0.00</div>
                </div>
            </div>
        </div>
        <div class="dl">
            <strong class="dt block">发票信息</strong>
            <div class="dd">
                <div class="item clearfix">
                    <span class="label">发票类型：</span>
                    <div class="info-rcol">电子发票</div>
                </div>
                <div class="item clearfix">
                    <span class="label">发票抬头：</span>
                    <div class="info-rcol">个人</div>
                </div>
                <div class="item clearfix">
                    <span class="label">发票内容：</span>
                    <div class="info-rcol">明细</div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="order-goods">
    <div class="mc">
        <div class="goods-list">
            <table class="tb-order">
                <thead>
                <tr>
                    <td class="grap"></td>
                    <td class="good-w">宝贝</td>
                    <td>商品编号</td>
                    <td>商品总额</td>
                    <td>数量</td>
                    <td class="option-w">操作</td>
                    <td class="grap"></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="grap"></td>
                    <td>
                        <div class="p-item clearfix">
                            <div class="p-img">
                                <img src="/static/images/index_nav_product_img01.png">
                            </div>
                            <div class="p-info">
                                <div class="p-name">铁观音绿茶109g 金砖  精致包装铁观音绿茶109g 金砖  精致包装</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <span>3133847</span>
                    </td>
                    <td>
                        <span class="f-price">¥5799.00</span>
                    </td>
                    <td><span class="cl9">x1</span></td>
                    <td>
                        <a>申请售后</a>
                    </td>
                    <td class="grap"></td>
                </tr>
                <tr>
                    <td class="grap"></td>
                    <td>
                        <div class="p-item clearfix">
                            <div class="p-img">
                                <img src="/static/images/index_nav_product_img01.png">
                            </div>
                            <div class="p-info">
                                <div class="p-name">铁观音绿茶109g 金砖  精致包装</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <span>3133847</span>
                    </td>
                    <td>
                        <span class="f-price">¥5799.00</span>
                    </td>
                    <td><span class="cl9">x1</span></td>
                    <td>
                        <a>申请售后</a>
                    </td>
                    <td class="grap"></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="goods-total clearfix">
            <ul>
                <li class="clearfix">
                    <span class="label">商品总额：</span>
                    <span class="txt">¥5799.00</span>
                </li>
                <li class="clearfix">
                    <span class="label">优　　惠：</span>
                    <span class="txt">-¥0.00</span>
                </li>
                <li class="clearfix">
                    <span class="label">运　　费：</span>
                    <span class="txt">¥0.00</span>
                </li>
                <li class="clearfix">
                    <span class="label red">应付总额：</span>
                    <span class="txt count">¥5799.00</span>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
