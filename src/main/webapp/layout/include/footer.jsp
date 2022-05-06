<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer>
    <div class="footer clearfix mb-2 text-muted" style="position: absolute; bottom: 0;">
        <div class="float-end">
            <small style="margin-top: 0 !important; margin-bottom: 1rem !important;" class="">
                <%
                    String currentDate = new SimpleDateFormat("yyyy").format(new Date());
                    out.print(currentDate);
                %> &copy; <a href="https://www.bpc.bt/" target="_blank">Bhutan Power Corporation Limited.</a>
                | Developed by
                <a href="https://thimphutechpark.bt/" target="_blank">
                    Thimphu TechPark Limited.
                </a>
            </small>
        </div>
    </div>
</footer>