<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/21
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册成功的页面</title>
</head>
<style>
    body {
        background-image: url("img/beijin.jpg");
    }
</style>
<body>
<p>恭喜你！注册成功了<span id="zhuan" style="color: red; font-size: 20px;">
    3</span>秒后跳转页面!<br/></p>
</body>
<script type="text/javascript">
    var time = 4;
    var timer = setInterval(function () {
        var str = document.getElementById("zhuan");
        if (time >= 1) {
            str.innerHTML = time;
            time--;
        } else {
            clearInterval(timer);
            location.href = "/login.jsp";
        }
    }, 1000)
</script>
</html>
