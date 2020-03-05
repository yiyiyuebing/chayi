<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/14
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>您访问的页面不存在-优茶联。</title>
    <meta name="keyword" content="您访问的页面不存在,茶叶采购,茶叶销售,茶叶批发,进货渠道,优茶联"/>
    <meta name="description" content="优茶联,茶行业联合运营平台,优茶联以茶为主题,为茶商、茶友提供叶销售、茶叶采购、茶叶批发、解决进货渠道难题。"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <title>Title</title>
    <link rel="stylesheet" href="/static/css/error404.css">
    <link rel="stylesheet" href="/static/css/public.css">
    <script type="text/javascript">
        var t = 5;
        function countDown(){
            var time = document.getElementById("time");
            t--;
            $("#time").html(t);
            if (t<=0) {
                location.href="/index";
                clearInterval(inter);
            };
        }
        var inter = setInterval("countDown()",1000);

    </script>
</head>
<body onload="countDown()">
<div class="error">
    <div class="logo-box">
        <a href="/">
            <img src="/static/images/logo.png">
        </a>
    </div>
    <div class="error-box">
        <i class="error-bg"></i>
        <div class="error-tips">
            <a href="/" class="back-index pull-left">点击返回首页</a>
            <div class="pull-left five">
                <span id="time"></span>s后跳转回首页
            </div>
        </div>
    </div>
</div>
</body>
<!--footer ending-->
<!--[if lt IE 9]>
<script src="../js/Response.js"></script>
<![endif]-->
<script src="/static/js/lib/jquery.min.js"></script>
</html>
