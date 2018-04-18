<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 4/18/2018
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello page</title>

    <link rel="stylesheet"  href="${contextPath}/resources/css/preloader.css">

  </head>
  <body>
    <%@include file="layout/preloader.jsp"%>
    <%@include file="layout/high_menu_bar.jsp"%>
    <div class="container content">
      <div class="row wrapper-for-product" >

        <div class="col-lg-6 description-of-the-product">
          <div style="margin-top: 3%">
            <form action="index" method="get">

              <div class="col-md-6" style="width: 80%">
                <div class="form-group row has-feedback">
                  <label class="label-attribute">Name: </label>
                  <input name="name" class="form-control" autofocus placeholder="name" value="${sessionScope.get('name')}"
                         pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                </div>
              </div>

              <div class="col-md-6" style="width: 80%">
                <div class="form-group row has-feedback">
                  <label class="label-attribute">Surname: </label>
                  <input name="surname" class="form-control" autofocus placeholder="surname" value="${sessionScope.get('surname')}"
                         pattern="^[0-9а-яА-ЯёЁa-zA-Z\s-]{1,20}$"/>
                </div>
              </div>

              <button class="btn btn-success" style="margin-left: 85%" name="indexButton" type="submit">Next</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
