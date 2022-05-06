<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="true" %>

<c:url value="/logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="get" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<nav class="navbar navbar-header navbar-expand navbar-light sticky-top">
    <a class="sidebar-toggler cursor-pointer"><span class="navbar-toggler-icon"></span></a>
    <button class="btn navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div style="padding-top: 10px;padding-left: 15px">
        <h5 style="color: #fff">BPC Compact Management System</h5>
    </div>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav d-flex align-items-center navbar-light ms-auto">
            <li class="dropdown nav-icon">
                <a href="#" data-bs-toggle="dropdown"
                   class="nav-link dropdown-toggle nav-link-lg nav-link-user">
                    <div class="d-lg-inline-blocks">
                        <i data-feather="bell" class="notificationBell warningMsg"></i>
                        <span class="nconut"></span>
                    </div>
                </a>
                <div class="dropdown-menu dropdown-menu-end dropdown-menu-large square" id="notificationDiv"
                     style="padding-bottom: 0px !important;">
                    <%--                    <h6 class='py-2 px-4'>Notifications</h6>--%>
                    <ul class="list-group rounded-none notificationList">
                        <li class="list-group-item border-0 align-items-start">
                            <%--                            <div class="avatar bg-success me-3">--%>
                            <%--                                <span class="avatar-content"><i data-feather="shopping-cart"></i></span>--%>
                            <%--                            </div>--%>
                            <%--                            <div>--%>
                            <%--                                <h6 class='text-bold'>New Notification</h6>--%>
                            <%--                                <p class='text-xs'>--%>
                            <%--                                    John Doe submitted target for 2022--%>
                            <%--                                </p>--%>
                            <%--                            </div>--%>
                        </li>
                    </ul>
                </div>
            </li>

            <li class="dropdown">
                <a href="#" data-bs-toggle="dropdown"
                   class="nav-link dropdown-toggle nav-link-lg nav-link-user">
                    <div class="avatar me-1">
                        <%--                        <img src="<c:url value='/assets/voler/images/logo.png' />" alt="" srcset="">--%>
                        <div class="avatar bg-light-green me-3">
                            <span class="avatar-content">${currentUser.shortName}</span>
                        </div>
                    </div>
                    <div class="d-none d-md-block d-lg-inline-block" style="color: #fff">${currentUser.fullName}</div>
                </a>
                <div class="dropdown-menu dropdown-menu-end square"
                     style="padding-top: 0px !important;padding-bottom: 0px;padding-left: 0px; border: 1px solid #24e14f !important; ">
                    <div style="width: 250px !important;">
                        <div class="square"
                             style="background: linear-gradient(to bottom, #24e14f, #eff4f4); ">
                            <div class="card-body d-flex justify-content-center align-items-center
                            justify-content-around flex-column">
                                <div class="avatar avatar-xl bg-dark me-3">
                                    <span class="avatar-content">${currentUser.shortName}</span>
                                    <span class="avatar-status bg-success"></span>
                                </div>
                                <div>
                                    <ul class="list-unstyled"
                                        style="text-align: center;vertical-align: middle;position: relative;">
                                        <li><span>${currentUser.fullName}</span></li>
                                        <li><span>${currentUser.email}</span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="card square"
                             style="background: #fff; margin-bottom: 0px !important;border-bottom-right-radius: 3px;border-bottom-left-radius: 3px;">
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                            <a class="dropdown-item" href="<c:url value='/myProfile'/>">
                                My Profile
                                <i class="fa fa-user successMsg pull-right"></i>
                            </a>
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                            <a class="dropdown-item" href="<c:url value='/about' />">
                                About
                                <i class="fa fa-info-circle infoMsg pull-right"></i>
                            </a>
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                            <a class="dropdown-item" href="<c:url value='/help' />">
                                Help <i class="fa fa-question-circle warningMsg pull-right"></i></a>
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                            <a class="dropdown-item" href="<c:url value='/commentPolicy' />">
                                Comment Policy <i class="fa fa-file-text infoMsg pull-right"></i></a>
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                            <a class="dropdown-item" href="javascript:$('#logoutForm').submit();">
                                Logout <i class="fa fa-power-off errorMsg pull-right"></i></a>
                            <div class="dropdown-divider" style="border-color: #f6f7f8"></div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</nav>