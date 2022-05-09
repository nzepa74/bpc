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
    <title>403 - Forbidden</title>
    <link rel="shortcut icon" href="<c:url value='/resources/assets/voler/images/favicon.png' />"/>
</head>
<body>
<div id="error">
    <div class="container text-center pt-32">
        <h1 class='error-title errorMsg'>403</h1>
        <h2>Forbidden</h2>
        <p>The page you are looking for is forbidden</p>
        <a href="<c:url value='/' />" class='btn btn-info square shake animated'>
            <i class="fa fa-arrow-left"></i> Go Home</a>
    </div>
</div>
</body>
</html>
