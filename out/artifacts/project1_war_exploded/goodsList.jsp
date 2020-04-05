<%--
  Created by IntelliJ IDEA.
  User: 步步清风
  Date: 2019/12/21
  Time: 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <title>商品展示页面</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>

    <style>
        #top {
            width: 1530px;
            height: 50px;
            background-color: black;
            margin-bottom: 20px;
        }

        .nav li {
            float: right;
            list-style: none;
        }

        .nav2 li {
            float: left;
            list-style: none;
        }
        li{
            list-style: none;
            float: left;
        }

        a {
            display: inline-block;
            height: 48px;
            padding: 0 10px;
            line-height: 48px;
            text-align: center;
        }

        .nav, .nav a:hover {
            background: #F00;
        }
    </style>
    <script>
        function remove() {
            document.getElementById("form").submit();
        }

        //全选
        function selectAll(choiceBtn) {
            //document.getElementsByTagName()
            var arr = document.getElementsByName("choiceid");
            for (var i = 0; i < arr.length; i++) {
                arr[i].checked = choiceBtn.checked;//循环遍历看是否全选
            }
        }
    </script>
</head>
<body>
<div id="top">
    <ul class="nav">
        <li><a href="/logoutServlet" target="_parent">注销</a></li>
        <li><a href="/index.jsp">首页</a></li>
        <li><a href="https://www.mi.com/">关于我们</a></li>
        <li><a href="https://www.baidu.com">联系我们</a></li>
        <li><a href="${pageContext.request.contextPath}/goodsListServlet?method=list">商品展示列表</a></li>
    </ul>
    <ul class="nav2">
        <li><img src="img/logo_top.jpg" height="50"></li>
        <li><h3 style="background-color: #FF0000" align="center">欢迎您的光临</h3></li>
    </ul>
</div>
<c:if test="${not empty pb.list}">
<div>
    <a href="${pageContext.request.contextPath}/goodsListServlet?method=toAdd">添加</a>
    <a href="javascript:void (0);" onclick="remove();">批量删除</a>
</div>
<form id="form" action="${pageContext.request.contextPath}/goodsListServlet?method=remove" method="post"/>
<table id="table" border="1" width="100%" align="center" cellspacing="0" cellpadding="0">
    <caption>商品信息表</caption>
    <tr>
        <td>
            <input type="checkbox" name="choiceid" onclick="selectAll(this)"/>全选
        </td>
        <td align="center">编号</td>
        <td align="center">商品名字</td>
        <td align="center">图片</td>
        <td align="center">价格</td>
        <td align="center">商品简介</td>
        <td align="center">库存</td>
        <td align="center">编辑</td>
        <td align="center">删除</td>
    </tr>
    <c:forEach items="${pb.list }" var="pro" varStatus="vs">
    <tr onmouseover="this.style.backgroundColor = 'yellow'"
        onmouseout="this.style.backgroundColor = '#F5FAFE';">
        <td style="height: 8px" width="6%">
            <input type="checkbox" name="choiceid" value="${pro.g_id}"/>
        </td>
            <%--  商品序号--%>
        <td style="cursor: hand; height: 8px" align="center"
            width="5%">${vs.count }</td>
            <%-- 商品的名称--%>
        <td style="cursor: hand; height: 8px" align="center"
            width="15%">${pro.g_goods_name }</td>
            <%-- 商品图片--%>
        <td style="cursor: hand; height: 30px" align="center"
            width="20%">
            <img width="60" height="60" src="uploadFiles/${pro.g_goods_pic }">
        </td>
            <%-- 商品的价格--%>
        <td style="cursor: hand; height: 8px" align="center"
            width="15%">${pro.g_goods_price }</td>
            <%--  商品简介--%>
        <td style="cursor: hand; height: 8px" align="center"
            width="17%">${pro.g_goods_description }</td>

            <%--商品的库存--%>
        <td style="cursor: hand; height: 8px" align="center"
            width="13%">${pro.g_goods_stock }</td>

            <%--商品的编辑--%>
        <td align="center" style="height: 8px">
            <a href="${ pageContext.request.contextPath }/goodsListServlet?method=toEdit&g_id=${pro.g_id}">
                <img src="${pageContext.request.contextPath}/img/edit.jpg"
                     width="16" height="16"
                     border="0" style="cursor: hand">
            </a></td>

            <%-- 商品的删除--%>
        <td align="center" style="height: 8px">
            <a href="${pageContext.request.contextPath}/goodsListServlet?method=remove&choiceid=${pro.g_id}">
                <img src="${pageContext.request.contextPath}/img/delete.jpg"
                     width="16" height="16" border="0" style="cursor: hand">
            </a>
        </td>
    </tr>
    </c:forEach>
</table>


    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                <li class="disabled">
                    </c:if>

                    <c:if test="${pb.currentPage != 1}">
                <li>
                    </c:if>

                        <c:if test="${pb.currentPage != 1}">
                    <li>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/goodsListServlet?method=findByPage&currentPage=${pb.currentPage - 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>


                    <c:forEach begin="1" end="${pb.totalPage}" var="i" >
                        <c:if test="${pb.currentPage == i}">
                            <li class="active"><a href="${pageContext.request.contextPath}/goodsListServlet?method=findByPage&currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                        </c:if>
                        <c:if test="${pb.currentPage != i}">
                            <li><a href="${pageContext.request.contextPath}/goodsListServlet?method=findByPage&currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                        </c:if>
                    </c:forEach>


                    <li>
                        <a href="${pageContext.request.contextPath}/goodsListServlet?method=findByPage&currentPage=${pb.currentPage + 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>

                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>
    </div>

    </c:if>

</body>
</html>
