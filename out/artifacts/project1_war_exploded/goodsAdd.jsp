<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/21
  Time: 2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>增加商品的页面</title>
</head>
<style>
    body {
        background-image: url("img/img2.jpg");
    }

    table {
        padding: 80px;
        background-color: silver;
    }
</style>
<body>
<form action="${pageContext.request.contextPath}/goodsListServlet?method=add" method="post"
      enctype="multipart/form-data">

    <table align="center" cellspacing="10" cellpadding="10" width="30%">
        <caption>新增你喜欢的商品</caption>
        <tr>
            <td bgcolor="aqua">商品名称</td>
            <td><input type="text" name="g_goods_name" placeholder="请输入名称"></td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品图片</td>
            <td><input type="file" name="g_goods_pic" value="上传文件" onchange="change(event)">
                <img id="image" src="img/zanwu.jpg" width="80px" height="80px">
            </td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品价格</td>
            <td><input type="text" name="g_goods_price" placeholder="请输入商品的价格"></td>
        </tr>
        <tr>
            <td bgcolor="aqua">商品简介</td>
            <td><input type="text" name="g_goods_description" placeholder="这个商品的简介"></td>
        <tr>
            <td bgcolor="aqua">商品库存</td>
            <td><input type="text" name="g_goods_stock" placeholder="输入库存"></td>
        </tr>
        <tr>
            <td><input type="submit" value="保存"></td>
            <td><input type="reset" value="重置"></td>
        </tr>
    </table>
</form>
</body>
<script>
    function change(event) {
        var image = document.getElementById("image");
         image.src = URL.createObjectURL(event.target.files[0]);
    }
</script>
</html>


