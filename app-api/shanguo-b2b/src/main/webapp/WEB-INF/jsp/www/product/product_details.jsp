<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息-优茶联</title>
	 <meta http-equiv="content-type" content="text/html;charset=utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="stylesheet" type="text/css" href="/static/www/css/idangerous.swiper2.7.6.css">
	 <link rel="stylesheet" type="text/css" href="/static/www/css/cssresets.css">
	 <link rel="stylesheet" type="text/css" href="/static/www/css/public.css">

    <link rel="stylesheet"  type="text/css" href="/static/www/css/footer.css">
    <link rel="stylesheet"  type="text/css" href="/static/www/css/asidebar.css">
	 <link rel="stylesheet" type="text/css" href="/static/www/css/person.css">
	  <link rel="stylesheet" type="text/css"  href="/static/www/css/product_details.css">
</head>
<jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/www/include/search_header.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
<body>

<div class="nav clearfix">
        <div class="nav-wrap clearfix">
            <div class="pull-left nav-product">
                <span class="nav-product-title">所有商品类别<i></i></span>
                <div class="nav-product-wrap clearfix">
                    <div class="nav-product-aside-wrap pull-left">
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">精品茶</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">铁观音</a></li>
                                <li class="pull-left"><a href="#">红茶</a></li>
                                <li class="pull-left"><a href="#">红茶</a></li>
                            </ul>
                        </div>
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">茶具</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">个人茶杯</a></li>
                                <li class="pull-left"><a href="#">茶具套装</a></li>
                                <li class="pull-left"><a href="#">茶具套装</a></li>
                            </ul>
                        </div>
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">茶食类</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">坚果类</a></li>
                                <li class="pull-left"><a href="#">糖果类</a></li>
                                <li class="pull-left"><a href="#">糖果类</a></li>
                            </ul>
                        </div>
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">茶配套</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">山茶籽油</a></li>
                            </ul>
                        </div>
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">散茶原料</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">铁观音</a></li>
                                <li class="pull-left"><a href="#">龙井</a></li>
                                <li class="pull-left"><a href="#">龙井</a></li>
                            </ul>
                        </div>
                        <div class="nav-product-aside-group">
                            <a href="#" class="nav-product-aside-title">包装</a>
                            <ul class="clearfix">
                                <li class="pull-left"><a href="#">铁观音</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="nav-product-content pull-right">
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音11</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音22</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音33</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音44</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                        <div class="nav-product-content-group clearfix">
                            <div class="nav-product-content-group-txt pull-left">
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a class="active" href="#">全部</a>
                                    <a href="#">铁观音55</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                                <h4>品牌</h4>
                                <ul class="clearfix nav-product-brand">
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                    <li class="pull-left">
                                        <a href="#">
                                            <img src="../static/www/images/index_nav_product_img01.png">
                                            <p>山国饮艺</p>
                                        </a>
                                    </li>
                                </ul>
                                <h4>品类</h4>
                                <div class="nav-product-content-group-list">
                                    <a href="#">全部</a>
                                    <a href="#">铁观音</a>
                                    <a href="#">红茶</a>
                                    <a href="#">绿茶</a>
                                    <a href="#">白茶</a>
                                    <a href="#">岩茶</a>
                                    <a href="#">普洱茶</a>
                                    <a href="#">乌龙茶</a>
                                    <a href="#">其他茶类</a>
                                    <a href="#">五福金砖</a>
                                </div>
                            </div>
                            <div class="nav-product-content-group-img pull-right">
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                                <a href="#">
                                    <img src="../static/www/images/index_nav_product_img01.png">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <a class="pull-left nav-link nav-active" href="index.html">商城</a>
            <a class="pull-left nav-link" href="customization_list.html">定制</a>
            <a class="pull-left nav-link" href="article_list_information.html">资讯</a>
            <a class="pull-left nav-link" href="#">招商合作</a>
        </div>
    </div>
    <div class="product-bread-nav clearfix">
        <span class="pull-left">当前位置：</span>
        <a class="pull-left" href="#">商城</a>
        <i class="pull-left"> > </i>
        <a class="pull-left" href="#">铁观音</a>
        <i class="pull-left"> > </i>
        <a class="pull-left" href="#">福建铁观音</a>
    </div>
    <div class="product-info clearfix">
        <div class="product-zoom-wrap pull-left">
            <div class="product-img">
                <a href="../static/www/images/product_list_aside_img_big.png" class="jqzoom" rel="gal">
                    <img class="spic" src="../static/www/images/product_list_aside_img.png">
                </a>
                <i class="zoom-icon"></i>
            </div>
            <div class="product-swiper clearfix">
                <div class="swiper-btn-prev pull-left"></div>
                <div class="swiper-container pull-left">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <a class="zoomThumbActive" href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_aside_img.png',largeimage: '../static/www/images/product_list_aside_img_big.png'}">
                                <img src='../static/www/images/product_list_aside_img.png'>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_productimg.png',largeimage: '../static/www/images/product_list_productimg_big.png'}">
                                <img src='../static/www/images/product_list_productimg.png'>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_recommend_img.png',largeimage: '../static/www/images/product_list_recommend_img_big.png'}">
                                <img src='../static/www/images/product_list_recommend_img.png'>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_aside_img.png',largeimage: '../static/www/images/product_list_aside_img_big.png'}">
                                <img src='../static/www/images/product_list_aside_img.png'>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_productimg.png',largeimage: '../static/www/images/product_list_productimg_big.png'}">
                                <img src='../static/www/images/product_list_productimg.png'>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href='javascript:void(0);' rel="{gallery: 'gal', smallimage: '../static/www/images/product_list_recommend_img.png',largeimage: '../static/www/images/product_list_recommend_img_big.png'}">
                                <img src='../static/www/images/product_list_recommend_img.png'>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="swiper-btn-next pull-right"></div>
            </div>
            <div class="product-share clearfix">
                <span class="pull-left" id="shareBtn"><i class="product-share-icon"></i>分享</span>
                <span class="pull-left"><i class="product-collect-icon"></i>收藏（235）</span>
            </div>
        </div>
        <div class="product-center pull-left">
            <form>
                <h4>商务大红袍</h4>
                <div class="product-txt">
                    <div class="price clearfix">
                        <span class="price-left pull-left">
                        供货价：<mark>¥100.00</mark>
                        </span>
                        <span class="price-right pull-right">自营</span>
                    </div>
                    <p>起定量：10盒起售</p>
                    <p>零售价：¥99.00</p>
                </div>
                <div class="group favorable clearfix">
                    <div class="pull-left title">
                        优惠
                    </div>
                    <div class="favorable-content pull-left">
                        <span class="favorable-content-title">每笔订单满199元，优惠10元<i></i></span>
                        <ul class="favorable-datalist">
                            <li>每笔订单满299元，优惠12元</li>
                            <li>每笔订单满399元，优惠13元</li>
                            <li>每笔订单满499元，优惠14元</li>
                            <li>每笔订单满599元，优惠15元</li>
                        </ul>
                    </div>
                </div>
               <div class="group express clearfix">
                    <div class="pull-left title">
                        物流
                    </div>
                    <div class="express-content pull-left clearfix">
                        <span class="pull-left express-start">福建厦门</span>
                        <i class="pull-left express-to">至</i>
                        <div class="express-wrap pull-left">
                            <span class="location-choose pull-left" id="locationChoose">请选择<i></i></span>
                            <div class="express-choose-datalist">
                                <div class="close clearfix" id="expressClose">
                                    <span class="pull-right"></span>
                                </div>
                                <div class="location-title clearfix">
                                    <span class="province-title pull-left active" id="provinceTitle">请选择<i></i></span>
                                    <span class="city-title pull-left" id="cityTitle">请选择<i></i></span>
                                    <span class="area-title pull-left" id="areaTitle">请选择<i></i></span>
                                </div>
                                <div class="location-content">
                                    <div class="location-group province clearfix" id="province">
                                        <ul>
                                            <li class="pull-left"><span>福建</span></li>
                                            <li class="pull-left"><span>湖北</span></li>
                                            <li class="pull-left"><span>北京</span></li>
                                        </ul>
                                    </div>
                                    <div class="location-group city clearfix" id="city">
                                        <ul data-category-province="福建">
                                            <li class="pull-left"><span>厦门</span></li>
                                            <li class="pull-left"><span>漳州</span></li>
                                            <li class="pull-left"><span>泉州</span></li>
                                            <li class="pull-left"><span>龙海</span></li>
                                        </ul>
                                        <ul data-category-province="湖北">
                                            <li class="pull-left"><span>武汉</span></li>
                                            <li class="pull-left"><span>襄樊</span></li>
                                            <li class="pull-left"><span>荆州</span></li>
                                            <li class="pull-left"><span>沙市</span></li>
                                        </ul>
                                        <ul data-category-province="北京">
                                            <li class="pull-left"><span>北京</span></li>
                                        </ul>
                                    </div>
                                    <div class="location-group area clearfix" id="area">
                                        <ul data-category-city="厦门">
                                            <li class="pull-left"><span>湖里区</span></li>
                                            <li class="pull-left"><span>思明区</span></li>
                                            <li class="pull-left"><span>翔安区</span></li>
                                            <li class="pull-left"><span>海沧区</span></li>
                                            <li class="pull-left"><span>集美区</span></li>
                                        </ul>
                                        <ul data-category-city="武汉">
                                            <li class="pull-left"><span>武昌</span></li>
                                            <li class="pull-left"><span>汉口</span></li>
                                            <li class="pull-left"><span>汉阳</span></li>
                                        </ul>
                                        <ul data-category-city="北京">
                                            <li class="pull-left"><span>三环</span></li>
                                            <li class="pull-left"><span>四环</span></li>
                                            <li class="pull-left"><span>朝阳区</span></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <span class="pull-left express-cost">快递<mark>¥88</mark></span>
                    </div>
                </div> 
                <div class="group salesVolume clearfix">
                    <div class="pull-left title">
                        销量/评价
                    </div>
                    <div class="sailsVolume-content clearfix">
                        <span class="pull-left"><mark>120</mark>斤成交</span>
                        <span class="pull-left"><mark>160</mark>条评价</span>
                        <i class="pull-left sailsVolume-line"></i>
                        <i class="star star01 pull-left"></i>
                    </div>
                </div>
                <div class="group server clearfix">
                    <div class="pull-left title">
                        销量/评价
                    </div>
                    <div class="server-content pull-left clearfix">
                        <a class="pull-left" href="#">代包</a>
                    </div>
                </div>
                <div class="group size clearfix">
                    <div class="pull-left title">
                        规格：
                    </div>
                    <div class="pull-left">
                        <table class="size-content">
                            <tbody>
                                <tr>
                                <td class="td-fontw">300g</td>
                                <td>68.9元</td>
                                <td>2000可售</td>
                                <td class="input-count clearfix">
                                    <a href="javascript:void(0)"  class="subtraction pull-left disabled">-</a>
                                    <input class="pull-left" type="text" value="0" name="amount01">
                                    <a href="javascript:void(0)" class="plus pull-left">+</a>
                                </td>
                            </tr>
                                <tr>
                                <td class="td-fontw">300g</td>
                                <td>68.9元</td>
                                <td>2000可售</td>
                                <td class="input-count clearfix">
                                    <a href="javascript:void(0)"  class="subtraction pull-left disabled">-</a>
                                    <input class="pull-left" type="text" value="0" name="amount01">
                                    <a href="javascript:void(0)" class="plus pull-left">+</a>
                                </td>
                            </tr>
                                <tr>
                                <td class="td-fontw">300g</td>
                                <td>68.9元</td>
                                <td>2000可售</td>
                                <td class="input-count clearfix">
                                    <a href="javascript:void(0)"  class="subtraction pull-left disabled">-</a>
                                    <input class="pull-left" type="text" value="0" name="amount01">
                                    <a href="javascript:void(0)" class="plus pull-left">+</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="group btn-group">
                    <a href="order_infomation.html" class="buy">立即购买</a>
                    <a href="#" class="shopping-car"><i></i>加入购物车</a>
                </div>
                <div class="group pay clearfix">
                    <div class="pull-left title">
                        服务保障
                    </div>
                    <div class="pay-content clearfix">
                        <a href="#" class="change-link pull-left"><i class="change15"></i>15天包换</a>
                        <a href="#" class="express-link pull-left"><i class="time24"></i>24小时发货</a>
                        <a href="#" class="promise-link pull-left"><i class="promise-brand"></i>商家保障</a>
                    </div>
                </div>
                <div class="group pay clearfix">
                    <div class="pull-left title">
                        支付方式
                    </div>
                    <div class="promise-content clearfix">
                        <a href="#" class="change-link pull-left">支付宝</a>
                        <a href="#" class="express-link pull-left">微信</a>
                        <a href="#" class="promise-link pull-left">信用卡</a>
                        <a href="#" class="promise-link pull-left">网上银行</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="product-right pull-right">
            <div class="product-right-title">
                <i class="pull-left"></i><span class="pull-left">看一看</span><i class="pull-right"></i>
            </div>
            <div class="look">
                <div class="look-swiper">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                        <div class="swiper-slide">
                            <a href="#">
                                <div class="img-wrap">
                                    <img src="../static/www/images/product_list_recommend_img.png">
                                    <span>¥200.00</span>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="look-swiper-btn clearfix">
                    <div class="look-prev look-btn disabled pull-left"></div>
                    <div class="look-next look-btn pull-right"></div>
                </div>
            </div>
        </div>
    </div>
    <!--分享弹窗-->
    <div class="popup-wrap" id="popup">
        <form>
        <div class="popup-bg">
            <div class="popup-box">
                <div class="pop-header clearfix">
                    <span class="pull-left">分享一下</span>
                    <i class="pop-close pull-right" id="popClose"></i>
                </div>
                <div class="pop-body">
                    <h6>说说这个商品怎么样</h6>
                    <div class="text-area clearfix">
                        <textarea id="shareTxt" name="description" maxlength="72" placeholder="赶紧看看"></textarea>
                        <span class="pull-right">还可以输入<i class="number-limit">72</i>个字</span>
                    </div>
                    <h6>选择配图</h6>
                    <div class="share-swiper-wrap clearfix">
                        <div class="share-img-prev pull-left"><i></i></div>
                        <div class="share-swiper pull-left" id="shareImg">
                            <div class="swiper-wrapper pull-left">
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img01">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img02">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img03">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                                <div class="swiper-slide">
                                    <label>
                                        <input type="radio" name="shareImg" value="img04">
                                        <div class="share-img-box">
                                            <img src="../static/www/images/product_details_img.png">
                                            <i></i>
                                        </div>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="share-img-next pull-right"><i></i></div>
                    </div>
                    <div class="share-position clearfix">
                        <span class="pull-left">分享到</span>
                        <div class="share-position-input pull-left clearfix" id="shareTo">
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="qqZone">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_qqZone.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="weiblog">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_weiblog.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="share03">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_share03.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="weichart">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_weichart.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="renren">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_renren.png">
                                </div>
                            </label>
                            <label class="share-position-group pull-left clearfix">
                                <input class="pull-left" type="checkbox" name="shareTo" value="share06">
                                <div class="share-input-mask pull-left clearfix">
                                    <i class="pull-left"></i>
                                    <img class="pull-left" src="../static/www/images/icon_share06.png">
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="share-submit">
                        <input type="submit" value="分享">
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
    <!--免费拿样-->
    <div class="sample clearfix">
        <div class="sample-txt01 pull-left">
            <span>免费拿样</span>
            <i>进货先拿样，批发有保障</i>
        </div>
        <div class="sample-txt02 pull-left">
            拿样支付后15天内，二次店铺进货2800元还样品费。样品随
            机发货，不携色，挑码，且每个买家限购1件。<a href="#">拿样规则</a>
        </div>
        <div class="sample-txt03 pull-left clearfix">
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">拿样价</label>
                <span class="pull-left"><mark>35</mark>元/盒</span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">样品数量</label>
                <span class="pull-left">10盒</span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">拿样说明</label>
                <span class="pull-left"><mark>现货</mark></span>
            </div>
            <div class="sample-txt03-group pull-left clearfix">
                <label class="pull-left">运费</label>
                <span class="pull-left">与产品运费一致</span>
            </div>
        </div>
        <div class="sample-btn pull-right">
            <a href="#">立即拿样</a>
        </div>
    </div>

    <!--产品介绍-->
    <div class="product-intro clearfix">
        <div class="intro-aside pull-left">
            <a href="#" class="intro-aside-header">
                <img src="../static/www/images/product_list_aside_top-img.png">
            </a>
            <div class="intro-aside-body">
                <h6>热销榜</h6>
                <div class="intro-aside-group">
                    <a class="intro-aside-img" href="#">
                        <img src="../static/www/images/product_list_aside_img.png">
                    </a>
                    <a class="intro-aside-txt" href="#">
                        2017新茶云雾绿茶4盒共500克一杯盒.....
                    </a>
                    <span class="intro-aside-price">
                        供货价：¥200.00
                    </span>
                    <span class="intro-aside-sale">
                        销量：4567笔
                    </span>
                </div>
                <div class="intro-aside-group">
                    <a class="intro-aside-img" href="#">
                        <img src="../static/www/images/product_list_aside_img.png">
                    </a>
                    <a class="intro-aside-txt" href="#">
                        2017新茶云雾绿茶4盒共500克一杯盒.....
                    </a>
                    <span class="intro-aside-price">
                        供货价：¥200.00
                    </span>
                    <span class="intro-aside-sale">
                        销量：4567笔
                    </span>
                </div>
                <div class="intro-aside-group">
                    <a class="intro-aside-img" href="#">
                        <img src="../static/www/images/product_list_aside_img.png">
                    </a>
                    <a class="intro-aside-txt" href="#">
                        2017新茶云雾绿茶4盒共500克一杯盒.....
                    </a>
                    <span class="intro-aside-price">
                        供货价：¥200.00
                    </span>
                    <span class="intro-aside-sale">
                        销量：4567笔
                    </span>
                </div>
                <div class="intro-aside-group">
                    <a class="intro-aside-img" href="#">
                        <img src="../static/www/images/product_list_aside_img.png">
                    </a>
                    <a class="intro-aside-txt" href="#">
                        2017新茶云雾绿茶4盒共500克一杯盒.....
                    </a>
                    <span class="intro-aside-price">
                        供货价：¥200.00
                    </span>
                    <span class="intro-aside-sale">
                        销量：4567笔
                    </span>
                </div>
                <div class="intro-aside-group">
                    <a class="intro-aside-img" href="#">
                        <img src="../static/www/images/product_list_aside_img.png">
                    </a>
                    <a class="intro-aside-txt" href="#">
                        2017新茶云雾绿茶4盒共500克一杯盒.....
                    </a>
                    <span class="intro-aside-price">
                        供货价：¥200.00
                    </span>
                    <span class="intro-aside-sale">
                        销量：4567笔
                    </span>
                </div>
            </div>
        </div>
        <div class="intro-section pull-right">
            <div class="intro-recommend">
                <h6>商品评审</h6>
                <div class="intro-recommend-wrap clearfix">
                    <div class="intro-recommend-group pull-left clearfix">
                        <span class="taste pull-left">香气香</span>
                        <div class="taste-content pull-left">
                            <i class="icon-taste01"></i>
                            <!--<i class="icon-taste02"></i>
                            <i class="icon-taste03"></i>
                            <i class="icon-taste04"></i>
                            <i class="icon-taste05"></i>-->
                            <p>花香扬而不腻，持久耐久耐冲泡</p>
                        </div>
                    </div>
                    <div class="intro-recommend-group pull-left clearfix">
                        <span class="smell pull-left">滋味浓</span>
                        <div class="smell-content pull-left">
                            <i class="icon-smell01"></i>
                            <!--<i class="icon-smell02"></i>
                            <i class="icon-smell03"></i>
                            <i class="icon-smell04"></i>
                            <i class="icon-smell05"></i>-->
                            <p>茶水回甘有力度，持久耐冲泡</p>
                        </div>
                    </div>
                    <div class="intro-recommend-group pull-left clearfix">
                        <span class="intro-reason pull-left">推荐理由</span>
                        <div class="intro-reason-txt pull-left">
                            原始茶原料，根据全发酵工艺，原始自然生长，无受外界污染环境，茶水的甜感和香气的清幽，显得更有性价比。
                        </div>
                    </div>
                </div>
            </div>
            <div class="intro-title clearfix" id="introTitle">
                <span class="intro-title-active pull-left">详情信息</span>
                <span class="pull-left">评论（123）</span>
                <span class="pull-left">订购说明</span>
            </div>
            <div class="intro-content">
                <div class="intro-content-group recommend-details clearfix">
                    <ul class="intro-info clearfix">
                        <li class="pull-left">采摘季节：春季</li>
                        <li class="pull-left">产地：安溪祥华</li>
                        <li class="pull-left">产区：核心产区</li>
                        <li class="pull-left">工艺：消正</li>
                        <li class="pull-left">香型：花香</li>
                        <li class="pull-left">原料：铁观音</li>
                        <li class="pull-left">生产日期：2017年6月</li>
                        <li class="pull-left">保质期：6个月</li>
                        <li class="pull-left">装箱规格：50斤/盒</li>
                        <li class="pull-left">重量：500克</li>
                    </ul>
                    <a class="intro-info-banner" href="#">
                        <img src="../static/www/images/product_details_banner_img.png">
                    </a>
                    <div class="product-info-img">
                        <img src="../static/www/images/product_details_bannerimg02.png">
                    </div>
                </div>
                <div class="intro-content-group recommend-list">
                    <div class="product-info-recommend">
                        <h5>商品评价</h5>
                        <div class="product-recommend-star clearfix">
                            <span class="pull-left">4.98</span>
                            <i class="recommend-star recommend-star01 pull-left"></i>
                            <!--<i class="recommend-star recommend-star02 pull-left"></i>
                            <i class="recommend-star recommend-star03 pull-left"></i>
                            <i class="recommend-star recommend-star04 pull-left"></i>
                            <i class="recommend-star recommend-star05 pull-left"></i>-->
                        </div>
                        <div class="recommend-info">
                            <div class="recommend-info-title clearfix">
                                <a class="recommend-info-title-active pull-left" href="#">全部评论（1234）</a>
                                <a href="#" class="pull-left">晒图（786）</a>
                                <a href="#" class="pull-left">好评（786）</a>
                                <a href="#" class="pull-left">中评（86）</a>
                                <a href="#" class="pull-left">差评（86）</a>
                            </div>
                            <div class="recommend-info-content clearfix">
                                <ul class="recommend-info-content-list">
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="recommend-userInfo pull-left clearfix">
                                            <div class="recommend-userImg pull-left">
                                                <img src="../static/www/images/recommend_userImg.png">
                                            </div>
                                            <span class="recommend-userName pull-left">12312313</span>
                                        </div>
                                        <div class="recommend-userTxt pull-right">
                                            <div>
                                                <i class="recommend-userTxt-star recommend-userTxt-star01"></i>
                                                <!--<i class="recommend-star recommend-star02 pull-left"></i>
                                                <i class="recommend-star recommend-star03 pull-left"></i>
                                                <i class="recommend-star recommend-star04 pull-left"></i>
                                                <i class="recommend-star recommend-star05 pull-left"></i>-->
                                            </div>
                                            <div class="recommend-txt">经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。经常在这里买东西，物美价廉。送货很快！棒棒的，。。。</div>
                                            <ul class="recommend-img-list clearfix">
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                                <li class="pull-left">
                                                    <img src="../static/www/images/index_nav_product_img01.png">
                                                </li>
                                            </ul>
                                            <div class="recommend-product-info">
                                                <span>铁观音（200g）</span>
                                                <span>1</span>
                                                <span>2017-01-23 17:09</span>
                                            </div>
                                            <div class="recommend-responseTxt">
                                                解释：您好，感谢您选购本店商品，智能手机因屏幕大、运行程序多、系统负载过大等原因，会造成连续使用的时间较短的。建议您可以设置成省电模式，关闭一些不必要应用功能及程序。此外您在使用过程中有任何疑问，可以联系我们在线售后，我们将竭诚为您解决售后问题，欢迎您的下次光临，祝您生活愉快~~
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                                <div class="product-list-paging-wrap clearfix">
                                    <div class="product-list-paging pull-right clearfix">
                                        <a href="#" class="prev pull-left disable"><i></i>上一页</a>
                                        <a href="#" class="page-number disable pull-left">1</a>
                                        <a href="#" class="page-number pull-left">2</a>
                                        <a href="#" class="page-number pull-left">3</a>
                                        <a href="#" class="page-number pull-left">4</a>
                                        <span class="dot pull-left">...</span>
                                        <a href="#" class="page-number pull-left">6</a>
                                        <a href="#" class="next pull-left">下一页<i></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a class="intro-info-banner" style="margin-bottom: 20px" href="#">
                            <img src="../static/www/images/product_details_banner_img03.png">
                        </a>
                    </div>
                </div>
                <div class="intro-content-group ordering">
                    <div class="ordering-group">
                        <h4>批发说明</h4>
                        <div class="ordering-line"></div>
                        <h5>供应商支持混批，500元以上起批，或者6斤以上起批。</h5>
                        <div class="ordering-txt">
                            <h6>什么是混批？</h6>
                            <p>是指不限产品的种类和样式，买家只要采购总价（或总量）达到或高于设置金额（或数量）即可享受批发价格。</p>
                        </div>
                    </div>
                    <div class="ordering-group">
                        <h4>交易流程</h4>
                        <div class="ordering-line"></div>
                        <div class="clearfix">
                            <div class="ordering-img pull-left">
                                <img src="../static/www/images/icon_alipay.png">
                            </div>
                            <div class="ordering-txt ordering-pay pull-left">
                                <h6>付款方式：</h6>
                                <p>买家先选择支付方式（路网上银行、快捷支付、支付宝余额等）付款到支付宝，支付宝担保货款安全；但买家收到货品并确认后，再由支付宝打款给供应商。如逾期未收到商品或商品不符合要求，买家可以提出退款申请以保障自身权益。</p>
                            </div>
                        </div>
                        <div class="ordering-route clearfix">
                            <h5 class="ordering-route-title">使用支付宝付款流程</h5>
                            <div class="ordering-route-img clearfix">
                                <span class="pull-left">1 选择商品</span>
                                <i class="icon-arrows-right pull-left"></i>
                                <span class="pull-left">2 确认订单信息</span>
                                <i class="icon-arrows-right pull-left"></i>
                                <span class="pull-left">3 选择付款方式并付款</span>
                                <i class="icon-arrows-right pull-left"></i>
                                <span class="pull-left">4 收到货并验货满意</span>
                                <i class="icon-arrows-right pull-left"></i>
                                <span class="pull-left">5 确认收货</span>
                                <i class="icon-arrows-right pull-left"></i>
                                <span class="pull-left">6 交易成功</span>
                            </div>
                        </div>
                    </div>
                    <div class="ordering-group">
                        <h4>保障说明</h4>
                        <div class="ordering-line"></div>
                        <div class="ordering-imgTxt clearfix">
                            <div class="ordering-img pull-left">
                                <img src="../static/www/images/icon_promise.png">
                            </div>
                            <div class="ordering-txt pull-left">
                                <h6>买家保障：</h6>
                                <p>卖家缴纳保证金为买家提供交易保障，若卖家发生违约或不诚信行为，买家可获得先行赔付。<a href="#">了解详情 》</a></p>
                            </div>
                        </div>
                        <div class="problem-route clearfix">
                            <img src="../static/www/images/product_promise_router_img.png">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
 <script src="/static/www/js/jquery.min.js"></script>
 <script src="/static/www/js/jquery-migrate-1.2.1.js"></script>
 <script src="/static/www/js/jquery.jqzoom-core.js"></script>
 <script src="/static/www/js/idangerous.swiper2.7.6.min.js"></script>
<script src="/static/www/js/product_details.js"></script>
 
</html>