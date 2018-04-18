<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 3:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Third step</title>
</head>
<body>
<%@include file="layout/preloader.jsp"%>
<%@include file="layout/high_menu_bar.jsp"%>
<div class="container content">
    <div class="row wrapper-for-product" >

        <div class="col-lg-6 description-of-the-product">
            <div style="margin-top: 3%">
                <form action="third" method="get">

                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Age: </label>
                            <input name="age" class="form-control" autofocus placeholder="age" value="${sessionScope.get('age')}"
                                   pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                        </div>
                    </div>

                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">About me: </label>
                            <textarea name="about" class="form-control" placeholder="about" pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$">${sessionScope.get('about')}</textarea>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <button name="thirdButton" class="btn btn-warning" type="submit" value="Prev">Prev</button>
                    </div>

                    <div class="col-md-6">
                        <button name="thirdButton" class="btn btn-success" type="submit" value="Next">Next</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
