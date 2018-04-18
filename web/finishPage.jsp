<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 4:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Finish Page</title>
</head>

<body>

<%@include file="layout/preloader.jsp"%>
<%@include file="layout/high_menu_bar.jsp"%>
<div class="container content">
    <div class="row wrapper-for-product" style="height: 79%">

        <div class="col-lg-6 description-of-the-product">
            <div style="margin-top: 3%">
                <form action="finish" method="get">

                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Name: </label>
                            <label class="form-control">${sessionScope.get('name')}</label>
                        </div>
                    </div>

                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Surname: </label>
                            <label class="form-control">${sessionScope.get('surname')}</label>
                        </div>
                    </div>
                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Country: </label>
                            <label class="form-control">${sessionScope.get('country')}</label>
                        </div>
                    </div>
                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Address: </label>
                            <label class="form-control">${sessionScope.get('address')}</label>
                        </div>
                    </div>
                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Telephone: </label>
                            <label class="form-control">${sessionScope.get('telephone')}</label>
                        </div>
                    </div>
                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">Age: </label>
                            <label class="form-control">${sessionScope.get('age')}</label>
                        </div>
                    </div>
                    <div class="col-md-6" style="width: 80%">
                        <div class="form-group row has-feedback">
                            <label class="label-attribute">About me: </label>
                            <label class="form-control">${sessionScope.get('about')}</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <button name="finishButton" class="btn btn-warning" type="submit" value="Prev">Prev</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
