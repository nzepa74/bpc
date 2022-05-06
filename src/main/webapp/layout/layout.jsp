<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page session="false" %>
<html class="no-js" lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <title><sitemesh:write property="title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="<c:url value='/resources/assets/voler/images/favicon.png' />"/>
    <jsp:include page="include/css.jsp"/>
</head>
<body>
<div id="app">
    <jsp:include page="include/menu.jsp"/>
    <div id="main">
        <jsp:include page="include/header.jsp"/>
        <sitemesh:write property="body"/>
        <jsp:include page="include/footer.jsp"/>
    </div>
</div>
<jsp:include page="include/js.jsp"/>
</body>
</html>