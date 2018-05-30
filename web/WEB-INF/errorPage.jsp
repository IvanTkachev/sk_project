<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 5/2/2018
  Time: 6:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>ErrorPage</title>
</head>
<body>
<%@include file="../layout/preloader.jsp"%>
<%@include file="../layout/high_menu_bar.jsp"%>

<div class="container content">
    <div class="row wrapper-for-product">
        <div class="col-lg-6 description-of-the-product">
            <div style="margin-top: 3%">
                <header-info:text headerSize="1">Something wrong :(</header-info:text>
                <% if(response.getStatus() == 500){ %>
                <font color="red">Error: <%=exception.getMessage() %></font><br>
                <%}%>

                    Hi There, error code is <%=response.getStatus() %><br>
                    Please go to <a href="../index.jsp">home page</a>

    </div>
        </div>
    </div>
</div>


</body>
</html>
