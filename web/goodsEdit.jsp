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
    <title>编辑商品属性的页面</title>
</head>
<style>
    body {
        background-image: url("img/img2.jpg");
    }

    table {
        padding: 100px;
        background-color: silver;
        opacity: 0.7;
    }
</style>
<body>
<form action="${pageContext.request.contextPath}/goodsListServlet?method=edit" method="post"
      enctype="multipart/form-data">


    <%--隐藏字段--%>
    <input type="hidden" name="g_id" value="${goods.g_id}"/>
    <input type="hidden" name="is_delete" value="${goods.is_delete}"/>
    <input type="hidden" name="pic" value="${goods.g_goods_pic}"/>

    <table align="center" cellpadding="10" cellspacing="10" width="30%">
        <caption>编辑商品</caption>
        <tr>
            <td bgcolor="aqua">商品名称</td>
            <td><input type="text" name="g_goods_name" value="${goods.g_goods_name}"></td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品样式</td>
            <td><c:if test="${not empty goods.g_goods_pic}">
                <img src="uploadFiles/${goods.g_goods_pic}" width="80px" height="80px">
            </c:if>
                <input type="file" name="file"/>
            </td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品价格</td>
            <td><input type="text" name="g_goods_price" value="${goods.g_goods_price}"></td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品简介</td>
            <td><input type="text" name="g_goods_description" value="${goods.g_goods_description}"></td>
        </tr>

        <tr>
            <td bgcolor="aqua">商品库存</td>
            <td><input type="text" name="g_goods_stock" value="${goods.g_goods_stock}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="保存"></td>
            <td><input type="reset" value="重置"></td>
        </tr>
    </table>
</form>
</body>
</html>

