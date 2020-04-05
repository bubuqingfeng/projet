<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/20
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<style>
    body {
        background-image: url("img/img1.jpg");
    }
</style>
<script>
    //验证用户名
    function checkUsername() {
        var reg = /^\w{3,12}/;//6-12位数字、字母或_组成
        var username = document.getElementById("username").value;

        var username_span = document.getElementById("username_span");

        if (reg.test(username)) {
            username_span.innerHTML = "<img src='img/timg.jpg' width='20' height='20'/>";
            return true;
        } else {
            username_span.innerHTML = "<font color='red'>用户名由6-12位数字、字母或_组成</font>";

            return false;
        }

    }


    //验证密码
    function checkPassword() {

        var reg = /^\w{3,12}/;//3-12位数字、字母或_组成
        var password = document.getElementById("password").value;

        var password_span = document.getElementById("password_span");

        if (reg.test(password)) {
            password_span.innerHTML = "<img src='img/timg.jpg' width='20' height='20'/>";
            return true;
        } else {
            password_span.innerHTML = "<font color='red'>密码由3-12位数字、字母或_组成</font>";

            return false;
        }

    }

    //验证确认密码
    function validate() {
        var newword = document.getElementById("newword").value;
        var password = document.getElementById("password").value;
        if (newword == password) {
            document.getElementById("tishi").innerHTML = "<img src='img/timg.jpg' width='20' height='20'/>";
        } else {
            document.getElementById("tishi").innerHTML = "<font color='red'>两次密码不相同</font>";
        }
    }

    //验证每一项都不能为空
    function check() {
        //循环次数是为表单中的元素个数,所以每一项都不能为空
        for (var i = 0; i < document.form0.elements.length - 1; i++) {
            if (document.form0.elements[i].value == "") {
                alert("当前表单不能有空项");
                document.form0.elements[i].focus();
                return false;
            }
        }
        return true;
    }
</script>
 <%-- //如果获取的值是null的话就不显示，有内容则显示出来--%>
<%=request.getAttribute("fail") == null ? "" : request.getAttribute("fail")%>
<body>
<h1 face="楷体" size="20" color="#000" align="center">注册界面</h1>
<form id="form1" name="form0" action="/registerServlet" method="post" onSubmit="return check()">
    <table width="40%" height="300" border="1" bordercolor="#A0A0A0" bgcolor="silver" align="center">
        <tr>
            <th bgcolor="aqua">用户名：</th>
            <td><input type="text" name="u_username" id="username" placeholder="请输入用户名"
                       onblur="checkUsername();"/><span id="username_span"></span></td>
        </tr>
        <tr>
            <th bgcolor="aqua">输入密码：</th>
            <td><input type="password" id="password" name="u_password" placeholder="请输入密码" maxlength="20"
                       onblur="checkPassword();"> <span id="password_span" class="error"></span></td>
        </tr>
        <tr>
            <th bgcolor="aqua">确认密码：</th>
            <td><input type="password" id="newword" name="newword" placeholder="请再次输入密码" maxlength="20"
                       onkeyup="validate();"/><span id="tishi" class="error"></span></td>
            </td>
        </tr>
        <tr>
            <th bgcolor="aqua">性别：</th>
            <td><input type="radio" name="u_sex" value="men" checked>男
                <input type="radio" name="u_sex" value="women">女
            </td>
        </tr>
        <tr>
            <th bgcolor="aqua">爱好：</th>
            <td><input type="text" name="u_hobbies" placeholder="输入你的爱好" maxlength="16"></td>
        </tr>
        <tr>
            <th bgcolor="aqua">联系方式：</th>
            <td><input type="text" id="phone" name="u_phone" placeholder="输入你的手机号码" maxlength="16"></td>
        </tr>
        <tr>
            <th bgcolor="aqua">邮箱：</th>
            <td><input type="text" id="email" name="u_email" placeholder="输入你的邮箱" maxlength="16"></td>
        </tr>
        <tr>
            <th bgcolor="aqua">住址：</th>
            <td><input type="text" id="address" name="u_address" placeholder="输入你的住址" maxlength="16"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" id="sbt" value="注  册" onclick="onclick(this)"/>
                <input type="reset" value="重  置"/></td>
        </tr>
    </table>
</form>
</body>
</body>
</html>
