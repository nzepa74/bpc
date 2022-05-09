<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:url value="/logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="get" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div id="sidebar" class='active'>
    <div class="sidebar-wrapper active">
        <div class="sidebar-header">
            <img src="<c:url value='/resources/assets/voler/images/logo.png' />" alt="" srcset="">
            <%--            <span class="text-muted">Dashboard</span>--%>
        </div>
        <div class="sidebar-menu">
            <ul class="menu">
                <li class="sidebar-item">
                    <a href="<c:url value='/' />" class='sidebar-link'>
                        <i class="fa fa-dashboard" width="20"></i>
                        Dashboard
                    </a>
                </li>
                <li class="sidebar-item has-sub">
                    <a href="#" class='sidebar-link'>
                        <i class="fa fa-database" width="20"></i> Master Data
                    </a>
                    <ul class="submenu" style="padding-left:0px !important;">
                        <li class="submenu-item">
                            <a href="<c:url value='/year' />" class='submenu-link'><i class="fa fa-calendar"></i>
                                Year</a>
                        </li>
                        <li class="submenu-item">
                            <a href="<c:url value='/user' />" class='submenu-link'><i class="fa fa-users"></i> User</a>
                        </li>
                        <li class="submenu-item">
                            <a href="<c:url value='/permission' />" class='submenu-link'><i class="fa fa-eye"></i>
                                Permission</a>
                        </li>
                        <li class="submenu-item">
                            <a href="<c:url value='/company' />" class='submenu-link'><i class="fa fa-university"></i>
                                Company</a>
                        </li>
                        <li class="submenu-item">
                            <a href="<c:url value='/weightageSetup' />" class='submenu-link'><i class="fa fa-check"></i>
                                Weightage Setup
                            </a>
                        </li>
                        <li class="submenu-item">
                            <a href="<c:url value='/commentPolicy' />" class='submenu-link'>
                                <i class="fa fa-file-text"></i>
                                Comment Policy
                            </a>
                        </li>
                    </ul>
                </li>
                <li class='sidebar-title'>General Dashboard</li>
                <li class="sidebar-item">
                    <a href="<c:url value='/targetDashboard' />" class='sidebar-link'>
                        <i class="fa fa-bar-chart" width="20"></i> Target
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<c:url value='/score' />" class='sidebar-link'>
                        <i class="fa fa-line-chart" width="20"></i> Score
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<c:url value='/report' />" class='sidebar-link'>
                        <i class="fa fa-file-pdf-o" width="20"></i> Report
                    </a>
                </li>
            </ul>
        </div>
        <button class="sidebar-toggler btn x"><i data-feather="x"></i></button>
        <div style="background: #28a745; padding: 10px; bottom: 0;width: 260px; border-top: solid 2px #fdac42;
                clear: both;display: block; position: fixed;">
            <div class="row">
                <div class="col-md-3 d-flex align-items-center justify-content-center" title="My Profile">
                    <a class="dropdown-item" href="<c:url value='/myProfile'/>">
                        <i class="fa fa-user successMsg"></i>
                    </a>
                </div>
                <div class="col-md-3 d-flex align-items-center justify-content-center" title="About">
                    <a class="dropdown-item" href="<c:url value='/about'/>">
                        <i class="fa fa-info-circle infoMsg"></i>
                    </a>
                </div>
                <div class="col-md-3 d-flex align-items-center justify-content-center" title="Help">
                    <a class="dropdown-item" href="<c:url value='/help'/>">
                        <i class="fa fa-question-circle warningMsg"></i>
                    </a>
                </div>
                <div class="col-md-3 d-flex align-items-center justify-content-center" title="Logout">
                    <a class="dropdown-item" href="javascript:$('#logoutForm').submit();">
                        <i class="fa fa-power-off errorMsg pull"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
