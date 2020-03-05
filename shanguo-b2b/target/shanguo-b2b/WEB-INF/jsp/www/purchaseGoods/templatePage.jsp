<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/8/18
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>页面跳转中...-优茶联</title>
</head>
<body>
<script type="text/javascript">
  function goPAGE() {
    if ((navigator.userAgent.
                    match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
      window.location.href="/sumgotea/goods_detail.html?id=${goodId}";
    }
    else {
      window.location.href="/purchase/detail/${goodId}.html";	}
  }
  goPAGE();
</script>
</body>
</html>
