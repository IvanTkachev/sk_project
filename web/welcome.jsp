<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <%@include file="layout/preloader.jsp"%>
    <%@include file="layout/high_menu_bar.jsp"%>
    <div class="container content">
        <div class="row wrapper-for-product" style="height: 60%">

            <div class="col-lg-6 description-of-the-product">
                <div style="margin-top: 3%">

                        <div class="col-md-6" style="width: 80%">
                            <label class="label-danger"> ${sessionScope.get("Error")}</label>
                                <table width="100%">
                                    <tr>
                                        <th>id</th>
                                        <th>name</th>
                                        <th>count</th>
                                    </tr>
                                    <c:forEach items="${products}" var="product">
                                        <jsp:useBean id="product" type="com.ivantk.skproj.entities.Product"/>
                                        <tr>
                                            <td>
                                                <jsp:getProperty name="product" property="id"/>
                                            </td>
                                            <td>
                                                <jsp:getProperty name="product" property="name"/>
                                            </td>
                                            <td>
                                                <jsp:getProperty name="product" property="count"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                        </div>
                    <div class="col-lg-6">
                        <label class="label-attribute">Delete product</label>
                        <form action="${contextPath}/controller" method="get">
                            <c:out value="name:"/>
                            <label>
                                <input name="delete_product" value="product">
                            </label>
                            <button type="submit" name="action" class="btn btn-warning" value="delete">Delete</button>
                        </form>
                        <div style="margin-top: 10%">
                            <label class="label-attribute">Add product</label>
                            <form action="${contextPath}/controller" method="get">
                                <label>name:
                                    <input name="add_product" value="product">
                                </label>
                                <label>count:
                                    <input name="count_product" type="number" value="10">
                                </label>
                                <button type="submit" name="action" class="btn btn-success" value="add">Add</button>
                            </form>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
</body>
</html>
