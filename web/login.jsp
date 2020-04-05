<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/20
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("u_username")) {
                response.sendRedirect("/goodsListServlet?method=findByPageMVC");
            }
        }
    }
%>
<html>
<head>
    <title>登录页面</title>
</head>
<%=request.getAttribute("er") == null ? "" : request.getAttribute("er")%>
<%=request.getAttribute("error") == null ? "" : request.getAttribute("error")%>

<style>
    #login {
        width: 100%;
        height: 588px;
        background: url("img/login_bg.jpg") no-repeat center;
        margin-top: 100px;
    }

    table {
        margin-left: 800px;
        background-color: silver;
        opacity: 0.8;
    / / 透明度
    }
</style>
<script type="text/javascript">
    //用户名校验
    function checkUserName(obj) {
        var username = obj;
        if (username.trim().length == 0) {
            var checkUserNameResult = document.getElementById("checkUserNameResult");
            checkUserNameResult.innerHTML = "用户名不能为空";
        } else {
            checkUserNameResult.innerHTML = "";
        }
    }

    //密码校验
    function checkPassword(obj) {
        var password = obj;
        var checkPasswordResult = document.getElementById("checkPasswordResult");
        if (password.trim().length == 0) {
            checkPasswordResult.innerHTML = "密码不能为空";
        } else {
            checkPasswordResult.innerHTML = "";
        }
    }

    //所有项都不能为空验证
    function check() {
        for (var i = 0; i < document.frmLogin.elements.length - 1; i++) {
            if (document.frmLogin.elements[i].value == "") {
                alert("当前表单不能有空项");
                document.frmLogin.elements[i].focus();
                return false;
            }
        }
        return true;

    }

</script>

<body bgcolor="#f5f5dc">
<h1 face="楷体" size="6" color="#000" align="center">登录界面</h1>
<div id="login">
    <form action="/loginServlet" method="post" name="frmLogin" onSubmit="return check()">
        <table width="500" height="400" border="5" bordercolor="#A0A0A0">
            <tr>
                <th bgcolor="aqua">用户名：</th>
                <td>
                    <input type="text" id="username" name="u_username"
                           onblur="checkUserName(this.value);" placeholder="请输入用户名"/>
                </td>
                <td>
                    <span id="checkUserNameResult" style="color: red "></span>
                </td>
            </tr>
            <tr>
                <th bgcolor="aqua">密 码：</th>
                <td>
                    <input type="password" id="password" name="u_password"
                           onblur="checkPassword(this.value);" placeholder="请输入密码"/>
                </td>
                <td>
                    <span id="checkPasswordResult" style="color: red "></span>
                </td>
            </tr>

            <th bgcolor="aqua">验证码：</th>
            <td>
                <input type="text" name="checkcode"  placeholder="请输入验证码"/>
                <img src="/veCodeServlet" id="checkCodeImg"/>
            </td>
            </tr>
            <tr>
                <!-- 点击注册会跳转到注册界面 -->
                <td colspan="2" align="center">
                    <input type="checkbox" name="remember" value="yes"/>免登录
                    <input type="submit" id="bmt" value="登录"/>
                    <input type="button" value="注 册" onclick="window.open('register.jsp')"/>
                    <input type="reset" value="重  置"></td>
            </tr>
        </table>
    </form>
</div>
<script>
    //当点击验证码的时候就会重新生成一个新的
    window.onload = function () {
        var checkCodeImg = document.getElementById("checkCodeImg");
        checkCodeImg.onclick = function () {
            this.src = "/veCodeServlet?" + new Date().getTime();
        }
    }
</script>
</body>
</html>
