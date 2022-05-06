<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page session="false" %>
<html class="no-js" lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <title><sitemesh:write property="title"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="description" content="">
    <link rel="shortcut icon" href="<c:url value='/resources/assets/voler/images/favicon.png' />"/>
    <jsp:include page="include/css.jsp"/>
    <jsp:include page="include/js.jsp"/>
</head>

<body>
<%--<section class="wrapper container">--%>
<%--    <jsp:include page="include/header.jsp"/>--%>
<sitemesh:write property="body"/>
<%--    <jsp:include page="include/footer.jsp"/>--%>
<%--</section>--%>
</body>
</html>