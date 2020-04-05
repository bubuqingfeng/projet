<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/20
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg")%>
<style>
    body {
        background-color: beige;
    }

    .top {
        margin-left: 160px;
        width: 1215px;
        height: 60px;
        background-color: white;
        padding-bottom: 80px;
        margin-bottom: 20px;
    }

    .nav li {
        float: right;
        list-style: none;
    }
    .nav2 li{
        float: left;
        list-style: none;
    }

    a {
        display: inline-block;
        height: 48px;
        padding: 0 10px;
        line-height: 48px;
        text-align: center;
    }

    a:hover {
        background: #F00;
    }

    .img {
        background-image: url("img/shouyebejing.png");
        width: 81%;
        height: 600px;
        margin-left: 160px;
        padding-top: 100px;
    }

    footer {
        background-color: gray;
        margin-left: 160px;
        width: 1215px;
        height: 60px;
    }

</style>
<body>
<div class="top">
    <h3 align="center">欢迎来到手机商城</h3>
    <ul class="nav">
        <li><a href="index.jsp">首页</a></li>
        <li><a href="/goodsListServlet?method=findByPageMVC">产品中心</a></li>
        <li><a href="login.jsp">登录</a></li>
        <li><a href="register.jsp">注册</a></li>
        <li><a href=""></a></li>
    </ul>
    <ul class="nav2">
        <li><img src="img/hjh_01.jpg" ></li>
        <li><img src="img/hjh_02.jpg" ></li>
        <li><img src="img/hjh_03.jpg" ></li>
        <li><img src="img/hjh_04.jpg" ></li>
        <li><img src="img/hjh_05.jpg" ></li>
        <li><img src="img/hjh_06.jpg" ></li>
    </ul>
</div>
<div class="img"></div>
<footer>
    <div class="copyright" align="center">简体 | 繁体 | English | 常见问题</div>
    <div class="copyright" align="center">小米公司版权所有-京ICP备10046444-京公网安备11010802020134号-京ICP证110507号</div>
</footer>
</body>
</html>
