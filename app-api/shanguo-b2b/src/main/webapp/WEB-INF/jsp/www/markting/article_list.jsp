<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/5
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${empty type}">
        <title>茶叶资讯,茶行业新闻,-优茶联</title>
        <meta name="keyword" content="茶叶资讯,茶行业新闻"/>
        <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势，尽在优茶联。"/>
    </c:if>
    <c:if test="${type == 'gonggao'}">
        <title>优茶联公告-优茶联</title>
        <meta name="keyword" content="优茶联公告"/>
        <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势，优茶联公告、优茶联促销信息尽在于此。"/>
    </c:if>
    <c:if test="${type == 'cuxiao'}">
        <title>促销信息-优茶联</title>
        <meta name="keyword" content="促销信息,优茶联促销信息"/>
        <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势，优茶联公告、优茶联促销信息尽在于此。"/>
    </c:if>
    <c:if test="${type == 'industry'}">
        <title>行业资讯-优茶联</title>
        <meta name="keyword" content="行业资讯,茶叶新闻"/>
        <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势，优茶联公告、优茶联促销信息尽在于此。"/>
    </c:if>
    <c:if test="${type == 'media'}">
        <title>平台动态-优茶联</title>
        <meta name="keyword" content="平台动态，优茶联动态"/>
        <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势,优茶联公告、优茶联促销信息尽在于此。"/>
    </c:if>

  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/article_list.css">
  <link rel="stylesheet" href="/static/js/lib/kkpager/css/kkpager.css">
    <script>
        var articleType = "${type}";
        console.log(articleType);
    </script>
</head>
<body>
  <%--头部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/header.jsp"></jsp:include>
  <%--头部 ending--%>

  <%--查询 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/search_header.jsp"></jsp:include>
  <%--查询 ending--%>

  <%--导航栏 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/nav_top_bar.jsp"></jsp:include>
  <%--导航栏 ending--%>

  <%--左侧工具栏 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/nav_left_bar.jsp"></jsp:include>
  <%--左侧工具栏 ending--%>

  <div class="product-bread-nav clearfix">
    <a class="pull-left" href="#">全部</a>
    <span class="pull-left"> > </span>
    <a class="pull-left" href="#">资讯</a>
  </div>
  <div class="article clearfix">
    <div class="article-aside pull-left">
      <ul>
        <li data-value="gonggao"><a href="/news/list/gonggao">公告</a></li>
        <li data-value="cuxiao"><a href="/news/list/cuxiao">促销</a></li>
        <li data-value="industry"><a href="/news/list/industry">行业资讯</a></li>
        <li data-value="media"><a href="/news/list/media">平台动态</a></li>
      </ul>
    </div>
    <div class="article-content pull-left">
      <div class="article-list clearfix">

        <ul class="article-list-content article-list-content-left pull-left">
         <%--
          左侧列表
          --%>
        </ul>
        <ul class="article-list-content article-list-content-right pull-left">
         <%--
          右侧列表
          --%>
        </ul>

      </div>
      <div id="kkpager" class="hide"></div>
    </div>
  </div>

  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>
</body>
</html>

<script src="/static/js/lib/kkpager/kkpager.js"></script>
<script src="/static/js/markting/articel_list.js"></script>

<script id="tpl-article-list-content-left" type="text/html">
  {{each leftArticleInfoList as articleInfo}}
      {{if articleInfo.type == 'goods'}}
          <li>
              <a href="/purchase/detail/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
            {{else if articleInfo.type == 'gonggaotw'}}
          <li>
              <a href="/news/gonggaoDetail/{{articleInfo.articleType}}/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
          {{else}}
          <li>
              <a href="/news/detail/{{articleInfo.articleType}}/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
      {{/if}}

  {{/each}}
</script>
<script id="tpl-article-list-content-right" type="text/html">
  {{each rightArticleInfoList as articleInfo}}
      {{if articleInfo.type == 'goods'}}
          <li>
              <a href="/purchase/detail/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
      {{else if articleInfo.type == 'gonggaotw'}}
          <li>
              <a href="/news/gonggaoDetail/{{articleInfo.articleType}}/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
      {{else}}
          <li>
              <a href="/news/detail/{{articleInfo.articleType}}/{{articleInfo.id}}.html"><i></i>{{articleInfo.title}}</a>
          </li>
      {{/if}}
  {{/each}}
</script>
