<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/6/5
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>${onlineStudy.title}-优茶联</title>

  <meta name="keyword" content="${onlineStudy.title}"/>
  <meta name="description" content="提供全面、新鲜的茶叶行业资讯、新闻资料,茶商、茶友,了解茶行业动态、茶行业行情、走势,优茶联公告、优茶联促销信息尽在于此。"/>
  <jsp:include page="/WEB-INF/jsp/www/common/common_css.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/www/common/common_js.jsp"></jsp:include>
  <link rel="stylesheet" href="/static/css/article_details.css">
  <script>
    var articleType = "${type}";
    var articleInfoType = "${articleType}";
    var createTime = "<fmt:formatDate value="${onlineStudy.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>";

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
    <a class="pull-left" href="#">文章列表</a>
    <span class="pull-left"> > </span>
    <a class="pull-left" href="#">文章</a>
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
      <div class="article-content-header">
        <h4>${onlineStudy.title}</h4>
        <p class="clearfix">
          <c:if test="${articleType != 'gonggao'}">
            <span class="pull-left">来源：${onlineStudy.author}</span>
          </c:if>
          <span class="pull-left datetime hide"><fmt:formatDate value="${onlineStudy.createTime}" pattern="MM-dd"/></span>
          <span class="pull-left datetime hide"><fmt:formatDate value="${onlineStudy.createTime}" pattern="HH:mm"/></span>
          <span class="pull-left olddatetime hide"><fmt:formatDate value="${onlineStudy.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
        </p>
      </div>
      <div class="article-content-body">
        ${onlineStudy.content}
      </div>
      <div class="article-keyword clearfix">
          <c:if test="${articleType != 'gonggao'}">
            <label class="pull-left">关键词：</label>
            <div class="keyword-wrap pull-left clearfix">
            <c:if test="${!empty onlineStudy.label}">
              <span>${onlineStudy.label}</span>
              <span>#${onlineStudy.studyTypeName}#</span>
            </c:if>
            <c:if test="${empty onlineStudy.label}">
              <span>无</span>
              <span>#${onlineStudy.studyTypeName}#</span>
            </c:if>
            </div>
          </c:if>
      </div>
      <%--<img src="/static/images/article_details_img.png">--%>
    </div>
  </div>

  <%--尾部 starting--%>
  <jsp:include page="/WEB-INF/jsp/www/include/footer.jsp"></jsp:include>
  <%--尾部 ending--%>
</body>
</html>
<script src="/static/js/markting/articel_details.js"></script>
