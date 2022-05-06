<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 4/1/2022
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../layout/include/css.jsp"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Not Found</title>
    <link rel="shortcut icon" href="<c:url value='/assets/voler/images/favicon.png' />"/>
</head>
<body>
<div id="error">
    <div class="container text-center pt-32">
        <h1 class='error-title errorMsg'>404</h1>
        <h2>Oop! You just found an error page</h2>
        <p>We couldn't find the page you are looking for</p>
        <a href="<c:url value='/' />" class='btn btn-info square shake animated'>
            <i class="fa fa-arrow-left"></i> Go Home</a>
    </div>
</div>
</body>
</html>
