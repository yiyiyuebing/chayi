<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <title>换货状态-优茶联</title>
    <link rel="stylesheet" href="/static/css/cssresets.css">
    <link rel="stylesheet" href="/static/css/public.css">
    <link rel="stylesheet" href="/static/css/after_sale_status.css">
</head>
<body>
    <div class="bread-nav clearfix">
        <span class="pull-left">你的位置：</span>
        <a class="pull-left" href="">首页</a>
        <i class="pull-left">></i>
        <a class="pull-left" href="">我的优茶联</a>
        <i class="pull-left">></i>
        <a class="pull-left" href="">进货单</a>
    </div>
    <div class="step-nav clearfix">
        <span class="step step-success pull-left">
            <i class="icon-success"></i>买家申请换货
        </span>
        <i class="step-break step-break-success pull-left"></i>
        <span class="step step-active pull-left">
            <i>2</i>卖家处理换货申请
        </span>
        <i class="step-break step-break-active pull-left"></i>
        <span class="step pull-left">
            <i>3</i>卖家确认，换货完成
        </span>
    </div>
    <div class="content clearfix">
        <div class="content-left pull-left">
            <!--提交订单模块-->
            <div class="step-form">
                <div class="left-title clearfix">
                <label class="pull-left">申请服务：</label>
                <div class="server-select pull-left">
                    <span class="server-title">
                        <input class="server-input" type="text" readonly value="换货" name="" id="serverInput">
                    </span>
                    <ul class="server-list">
                        <li>退款退货</li>
                        <li>仅退款</li>
                        <li>换货</li>
                    </ul>
                </div>
            </div>
            <div class="left-tips">
                <i class="icon-light"></i>温馨提示:您只有一次售后维权的机会哦
            </div>
            <div class="form-group clearfix">
                <label class="pull-left">换货原因：</label>
                <i class="tips pull-left">*</i>
                <div class="reason-wrap pull-left">
                    <input type="text" name="" value="请选择换货原因" readonly id="reasonInput">
                    <ul class="reason-list">
                        <li>物流不满意</li>
                        <li>不喜欢</li>
                        <li>卖家态度不好</li>
                        <li>信息错了，重新拍</li>
                        <li>其他</li>
                    </ul>
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left">需要退款金额：</label>
                <i class="tips pull-left">*</i>
                <span class="refund-money pull-left"><mark>59.99</mark> 元(最多￥59.99)</span>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left">退货金额：</label>
                <i class="tips pull-left">*</i>
                <div class="exchange pull-left clearfix">
                    <input type="text" name="" value="25.00">
                    <span>元（最多<mark>￥25.00</mark>元）</span>
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left">说明：</label>
                <i class="tips pull-left"></i>
                <textarea id="textContent" class="pull-left" name="" rows="2"></textarea>
                <span class="letter-count">(<i id="letter-actual">0</i>/<i id="letter-all">200</i>字)</span>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left">上传凭证：</label>
                <i class="tips pull-left"></i>
                <div class="img-wrap pull-left">
                    <div class="img-box clearfix" id="photo-list">

                    </div>
                    <a href="javascript:;" id="selectImgBtn">选择凭证图片</a>
                </div>
            </div>
            <div class="form-group clearfix">
                <label class="pull-left"></label>
                <i class="tips pull-left"></i>
                <a href="#" class="submit-btn pull-left">提交申请</a>
                <a href="#" class="cancel-btn pull-left">取消并返回</a>
            </div>
            </div>
            <!--提交订单模块  ending-->
            <!--等待申请模块-->
            <div class="step-waiting">
                <h5>等待优茶联处理中...</h5>
                <div class="waiting-time">
                    如优茶联在<span>05</span>天<span>24</span>时<span>00</span>分内未处理，申请将自动达成
                </div>
                <div class="waiting-operation clearfix">
                    <label class="pull-left">您还可以：</label>
                    <a href="#" class="cancel-server pull-left">撤销申请</a>
                    <a href="#" class="change-server pull-left">修改申请</a>
                </div>
            </div>
            <!--等待申请模块  ending-->
            <!--同意申请模块-->
            <div class="step-agree">
                <h5>优茶联已同意申请</h5>
                <div class="waiting-time">
                    请在<span>05</span>天<span>24</span>时<span>00</span>分内将换货商品寄回的物流信息提交成功，否则系统将自动关闭您的换货申请
                </div>
                <div class="waiting-operation clearfix">
                    <label class="pull-left">您还可以：</label>
                    <a href="#" class="see-location pull-left" id="seeLocationBtn">查看换货地址</a>
                    <a href="#" class="editor-logistic pull-left" id="editorLogistic">填写换货物流信息</a>
                </div>
            </div>
            <!--同意申请模块 ending-->
            <!--申请被拒绝-->
            <div class="step-refuse">
                <div class="step-waiting">
                    <h5>优茶联已拒绝申请</h5>
                    <div class="waiting-time">
                        因xxxxxx，换货已拒绝
                    </div>
                    <div class="waiting-operation clearfix">
                        <label class="pull-left">您还可以：</label>
                        <a href="#" class="cancel-server pull-left">再次申请</a>
                    </div>
                </div>
            </div>
            <!--申请被拒绝模块   ending-->
            <!--换货成功模块-->
            <div class="change-success">
                <div class="step-waiting">
                    <h5>退款成功</h5>
                    <div class="waiting-operation clearfix">
                        <label class="pull-left">您还可以：</label>
                        <a href="#" class="cancel-server pull-left">查看换货物流信息</a>
                    </div>
                </div>
                <div class="success-message">
                    <h4>留言板</h4>
                    <ul class="message-list">
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                    </ul>
                </div>
            </div>
            <!--换货成功模块  ending-->
            <!--撤销申请   换货关闭-->
            <div class="change-success">
                <div class="step-waiting">
                    <h5>换货关闭</h5>
                    <p>· 因你撤销了换货申请，换货已关闭/因xxxxx，换货已关闭</p>
                    <!--<div class="waiting-operation clearfix">
                        <label class="pull-left">您还可以：</label>
                        <a href="#" class="cancel-server pull-left">查看换货物流信息</a>
                    </div>-->
                </div>
                <div class="success-message">
                    <h4>留言板</h4>
                    <ul class="message-list">
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                        <li class="clearfix">
                            <img src="../images/index_nav_product_img01.png" class="user-icon pull-left">
                            <div class="message-txt pull-left">
                                <p>小星星-买家</p>
                                <p>买家主动撤销本次售后服务申请</p>
                            </div>
                            <span class="time">2017-08-09  13:16:09</span>
                        </li>
                    </ul>
                </div>
            </div>
            <!--撤销申请  换货关闭模块-->
        </div>
        <div class="content-right pull-left">
            <h4>商品类型</h4>
            <ul class="product-list">
                <li>
                    <a href="#" class="right-product clearfix">
                        <div class="img-box pull-left">
                            <img src="../images/product_list_aside_img.png">
                        </div>
                        <div class="txt-box pull-left">
                            <p class="txt-title">铁光音 220g 精装完美及220g 精装完美及...</p>
                            <p class="txt-sku">规格：120g/盒</p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="right-product clearfix">
                        <div class="img-box pull-left">
                            <img src="../images/product_list_aside_img.png">
                        </div>
                        <div class="txt-box pull-left">
                            <p class="txt-title">铁光音 220g 精装完美及精装完美及...</p>
                            <p class="txt-sku">规格：120g/盒</p>
                        </div>
                    </a>
                </li>
            </ul>

            <div class="order-content">
                <div class="order-number">
                    <label>订单编号：</label>
                    <span>234567890765434</span>
                </div>
                <div class="order-price">
                    <label><i>单</i>价：</label>
                    <span>200.00<mark>元*1（数量）</mark></span>
                </div>
            </div>
            <div class="change-order">
                <label>订单编号：</label>
                <span>234567890765434</span>
            </div>
        </div>
    </div>

    <!--填写换货物流信息弹窗-->
    <div class="logistic-popup" id="logisticPopup">
        <div class="popup-wrap" id="logisticWrap">
            <div class="popup">
                <div class="popup-header">
                    <h5>填写换货物流信息</h5>
                    <a href="javascript:;" class="popup-close" id="popupClose"></a>
                </div>
                <div class="popup-body">
                    <div class="group">
                        <label>快递公司：</label>
                        <input class="company" type="text" name="">
                    </div>
                    <p class="tips">快递公司不能为空</p>
                    <div class="group">
                        <label>物流单号：</label>
                        <input class="logistic-number" type="text" name="">
                    </div>
                    <p class="tips">物流单号不能为空</p>
                    <div class="group">
                        <a href="#" class="logistic-submit">保存收货地址</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--查看换货地址-->
    <div class="logistic-popup" id="seeLocationPopup">
        <div class="popup-wrap" id="seeLocationWrap">
            <div class="popup">
                <div class="popup-header">
                    <h5>换货地址</h5>
                    <a href="javascript:;" class="popup-close" id="locationClose"></a>
                </div>
                <div class="popup-body">
                    <div class="location">
                        <h5>优茶联已确认换货地址：</h5>
                        <p>收货人：王小姐<br>
                            联系电话：05922176355<br>
                            收货地址：福建省厦门市海沧区新阳工业区阳和南路6号恒兴工业园1栋4楼<br>
                            备注：退货的快递费用，由买家自行承担，我们拒收到付件。<br>
                            麻烦您寄回帮我们写张纸条：订单编号+购买时收件人姓名+退回原因 +购买渠道：优茶联。 我们好及时为您登记处理哦/:^_^感谢您的配合，谢谢！</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
    <script src="/static/js/afterSale/jquery-1.9.1.min.js"></script>
    <script src="/static/js/afterSale/after_sale_status.js"></script>
</html>