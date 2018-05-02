<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Second Step</title>
</head>
<body>
    <%@include file="layout/preloader.jsp"%>
    <%@include file="layout/high_menu_bar.jsp"%>
    <div class="container content">
        <div class="row wrapper-for-product" >

            <div class="col-lg-6 description-of-the-product">
                <div style="margin-top: 3%">
                    <%
                        String user = request.getSession().getAttribute("name").toString();

                        if(user == null || "".equals(user)){
                            throw new ServletException("Mandatory Parameter missing");
                        }

                    %>

                    <form action="second" method="get">

                        <div class="col-md-6" style="width: 80%">
                            <div class="form-group row has-feedback">
                                <label class="label-attribute">Country: </label>
                                <input name="country" class="form-control" autofocus placeholder="country" value="${sessionScope.get('country')}"
                                       pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                            </div>
                        </div>

                        <div class="col-md-6" style="width: 80%">
                            <div class="form-group row has-feedback">
                                <label class="label-attribute">Address: </label>
                                <input name="address" class="form-control" autofocus placeholder="address" value="${sessionScope.get('address')}"
                                       pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                            </div>
                        </div>

                        <div class="col-md-6" style="width: 80%">
                            <div class="form-group row has-feedback">
                                <label class="label-attribute">Telephone: </label>
                                <input name="telephone" class="form-control" autofocus placeholder="telephone" value="${sessionScope.get('telephone')}"
                                       pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <button name="secondButton" class="btn btn-warning" type="submit" value="Prev">Prev</button>
                        </div>

                        <div class="col-md-6">
                            <button name="secondButton" class="btn btn-success" type="submit" value="Next">Next</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
