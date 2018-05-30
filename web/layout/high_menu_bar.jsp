<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>

    <link href="${contextPath}/resources/css/wrapper/font-awesome.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/wrapper/noJS.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/wrapper/style.css" rel="stylesheet">

    <script src="resources/js/jquery-2.1.3.min.js"></script>
    <script src="resources/js/jquery-ui-1.11.2.min.js"></script>

    <link rel="stylesheet"  href="../resources/css/main.css">
    <link rel="stylesheet"  href="../resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-left">
                    <a class="navbar-brand" href="${contextPath}/welcome">${sessionScope.get("name_store")}</a>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                    <div id="dd" class="wrapper-dropdown-5" tabindex="1">Menu
                        <form method="get" action="welcome">
                            <ul class="dropdown">
                                <c:forEach items="${stores}" var="store">
                                    <li><button style="width: border-box" name="store" type="submit" value="${store.name}"><i class="icon-list"></i>${store.name}</button></li>
                                </c:forEach>
                            </ul>
                        </form>
                    </div>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
