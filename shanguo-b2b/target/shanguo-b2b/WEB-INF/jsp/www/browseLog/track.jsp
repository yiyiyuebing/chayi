<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/23
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的足迹-优茶联</title>
    <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="/static/css/person.css">
    <link rel="stylesheet" href="/static/css/person_center_nav_position.css">
    <link rel="stylesheet" href="/static/css/pug.css">
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

<body style="height: 100%">
<div class="person-wrap">
    <div class="person-cont">
        <div class="content">
            <!-- 个人中心左侧菜单栏 -->
            <%--右侧导航栏 starting--%>
            <jsp:include page="/WEB-INF/jsp/www/include/personal_menu.jsp"></jsp:include>
            <%--右侧导航栏 ending--%>
            <!-- END个人中心左侧菜单栏 -->
            <!-- 个人中心主内容 -->
            <div class="right-cont-wrap">
                <div>
                    <ul id="top-nav" class="nav-menu clearfix">
                        <li class="active"data-all=""  onclick="Pug.classIfy()"><a >全部分类</a></li>
                        <li class="jpc" data-jpc="4" onclick="Pug.classIfy1()"><a  >精品茶</a></li>
                        <li class="dzc" data-dzc="317037212356218880" onclick="Pug.classIfy2()"><a  >定制茶</a></li>
                        <li class="cj" data-cj="317071407014764544"  onclick="Pug.classIfy3()"><a >茶具</a></li>
                        <li class="cs" data-cs="355464326862311424"  onclick="Pug.classIfy4()"><a>茶食</a></li>
                    </ul>
                </div>
                <div class="pug-wrap">
                    <div class="pug-item">
                        <p class="pug-title">
                            <span class="big-text">今天</span>
                            <span class="small-text time"></span>
                            <a class="small-text del-jt" onclick="Pug.delPugtoday()">删除</a>
                        </p>
                        <div class="list-item clearfix" id="todayWrap">
                      <%--      <div class="item-inner">
                                <a class="img-cover">
                                    <img width="160" height="160" src="/static/images/product_list_aside_img.png">
                                </a>
                                <a class="p-name">铁观音正宗选用优质的茶叶而成，茶叶清香</a>
                                <div class="p-price red">¥<strong>399.00</strong></div>
                            </div>--%>
                        </div>
                        <%--  </div>--%>
                    </div>
                    <div class="pug-item">
                        <p class="pug-title">
                            <span class="big-text">一周内</span>
                            <a class="small-text del-yz" onclick="Pug.delPugweek()">删除</a>
                        </p>
                        <div class="list-item clearfix" id="weekWrap">
                            <%--<div class="item-inner">
                                <a class="img-cover">
                                    <img width="160" height="160" src="/static/images/product_list_aside_img.png">
                                </a>
                                <a class="p-name">铁观音正宗选用优质的茶叶而成，茶叶清香</a>
                                <div class="p-price red">¥<strong>399.00</strong></div>
                            </div>--%>
                        </div>

                    </div>
                    <div class="pug-item">
                        <p class="pug-title">
                            <span class="big-text">更早之前</span>
                            <a class="small-text del-gz" onclick="Pug.delPugearlier()">删除</a>
                        </p>
                        <div class="list-item clearfix" id="earlierWrap">

                        </div>
                    </div>
                </div>
            </div>
            <!-- END个人中心主内容 -->
        </div>
    </div>
</div>
</body>
<%--尾部 starting--%>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
<%--尾部 ending--%>
<script src="/static/js/user/person.common.js"></script>
<script src="/static/js/browseLog/pug.js"></script>

<%--今天数据模板--%>
<script id="todayTpl" type="text/html">
    <div class="item-inner pull-left" data-goodId="{{goodsId}}">
        {{if good.stock > 0 && good.status == '1'}}
            <div class="item-box">
                <a class="img-cover" href="/purchase/detail/{{goodsId}}.html">
                    <img src="{{imageUrl}}">
                </a>
                <a class="p-name">{{name}}</a>
                <input type="hidden" class="jt" value="{{id}}">
                <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
            </div>
        {{else}}
            <div class="item-box">
                <a class="img-cover" style="pointer-events: none;" href="/purchase/detail/{{goodsId}}.html">
                    <img src="{{imageUrl}}">
                </a>
                <a class="p-name">{{name}}</a>
                <input type="hidden" class="jt" value="{{id}}">
                <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
            </div>
            <%--商品售罄图标--%>
            <i class="off-sale-icon"></i>
        {{/if}}

    </div>
</script>
<%--退后一周数据模板--%>
<script id="weekTpl" type="text/html">
    <div class="item-inner pull-left" data-goodId="{{goodsId}}">
        {{if good.stock > 0 && good.status == '1'}}
        <div class="item-box">
            <a class="img-cover" href="/purchase/detail/{{goodsId}}.html">
                <img src="{{imageUrl}}">
            </a>
            <a class="p-name">{{name}}</a>
            <input type="hidden" class="yz" value="{{id}}">
            <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
        </div>

        {{else}}
        <div class="item-box">
            <a class="img-cover" style="pointer-events: none;" href="/purchase/detail/{{goodsId}}.html">
                <img src="{{imageUrl}}">
            </a>
            <a class="p-name">{{name}}</a>
            <input type="hidden" class="yz" value="{{id}}">
            <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
        </div>
        <%--商品售罄图标--%>
        <i class="off-sale-icon"></i>
        {{/if}}

    </div>
</script>
<%--更早之前的数据模板--%>
<script id="earlierTpl" type="text/html">
    <div class="item-inner pull-left" data-goodId="{{goodsId}}">
        {{if good.stock > 0 && good.status == '1'}}
        <div class="item-box">
            <a class="img-cover" href="/purchase/detail/{{goodsId}}.html">
                <img src="{{imageUrl}}">
            </a>
            <a class="p-name">{{name}}</a>
            <input type="hidden" class="gz" value="{{id}}">
            <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
        </div>

        {{else}}
        <div class="item-box">
            <a class="img-cover" style="pointer-events: none;" href="/purchase/detail/{{goodsId}}.html">
                <img src="{{imageUrl}}">
            </a>
            <a class="p-name">{{name}}</a>
            <input type="hidden" class="gz" value="{{id}}">
            <div class="p-price red">¥<strong>{{goodsPrice}}</strong></div>
        </div>
        <%--商品售罄图标--%>
        <i class="off-sale-icon"></i>
        {{/if}}

    </div>
</script>

</html>
