<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 3/16/2022
  Time: 12:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authorize access="hasAuthority('2-EDIT')" var="hasEditRole"/>
<sec:authorize access="hasAuthority('2-ADD')" var="hasAddRole"/>
<sec:authorize access="hasAuthority('2-DELETE')" var="hasDeleteRole"/>
<html>
<head>
    <title>My Profile</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>My Profile</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">My Profile</li>
                    </ol>
                </nav>
            </div>
        </div>

    </div>
    <!-- Basic Horizontal form layout section start -->
    <section id="basic-horizontal-layouts">
        <div class="row match-height">
            <div class="col-md-12 col-12">
                <div class="row">
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body"
                                 style="text-align: center;vertical-align: middle;position: relative;">
                                <%--                                <img src="<c:url value='/assets/voler/images/avatar/avatar-s-1.png' />" alt=""--%>
                                <%--                                     srcset="" style="border-radius: 50%" width="150px;" height="150px">--%>

                                <div class="avatar avatar-xl bg-light-green me-3">
                                    <span class="avatar-content">${currentUser.shortName}</span>
                                    <span class="avatar-status bg-success"></span>
                                </div>
                                <div>
                                    <ul class="list-unstyled">
                                        <li><strong class="fullNameDisplay"></strong></li>
                                        <li>Email: <strong class="emailDisplay"></strong></li>
                                        <li>Mobile No.: <strong class="mobileNoDisplay"></strong></li>
                                        <li>Role: <strong>${currentUser.roleName}</strong></li>
                                        <li><strong class="myCompany"></strong></li>
                                    </ul>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-primary btn-sm square">
                                        Change Profile Photo
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-body">
                                <ul class="nav nav-tabs" id="myTab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link active" id="tab-1" data-bs-toggle="tab" href="#tab1"
                                           role="tab" aria-controls="home" aria-selected="true">
                                            <i class="fa fa-info-circle"></i> Basic Info</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link" id="tab-2" data-bs-toggle="tab" href="#tab2"
                                           role="tab" aria-controls="Password Change" aria-selected="false">
                                            <i class="fa fa-key"></i> Change Password</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link" id="contact-tab" data-bs-toggle="tab" href="#contact"
                                           role="tab" aria-controls="contact" aria-selected="false">
                                            <i class="fa fa-cog"></i> General Settings</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="myTabContent">
                                    <br>
                                    <div class="tab-pane fade show active" id="tab1" role="tabpanel"
                                         aria-labelledby="tab-1">
                                        <p>Full Name: <strong class="fullNameDisplay"></strong></p>
                                        <p>Username: <strong class="usernameDisplay"></strong></p>
                                        <p>Email: <strong class="emailDisplay"></strong></p>
                                        <p>Mobile No.: <strong class="mobileNoDisplay"></strong></p>
                                        <p>Role: <strong>${currentUser.roleName}</strong></p>
                                        <div>Companies Mapped:
                                            <ul class="list-unstyled companyUl"></ul>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab-2">
                                        <div class="form-body">
                                            <form id="pwChangeFormId" class="pwChangeFormId" novalidate="novalidate">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <label for="oldPassword"
                                                               class="col-form-label required pull-right">Current</label>
                                                    </div>
                                                    <div class="col-md-6 form-group">
                                                        <input type="password" id="oldPassword"
                                                               class="form-control square field" name="oldPassword"
                                                               placeholder="Current" autocomplete="off" required>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <label for="newPassword"
                                                               class="col-form-label required pull-right">New</label>
                                                    </div>
                                                    <div class="col-md-6 form-group">
                                                        <input type="password" id="newPassword"
                                                               class="form-control square field" name="newPassword"
                                                               placeholder="New" autocomplete="off" required>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <label for="confirmPassword"
                                                               class="col-form-label required pull-right">
                                                            Re-type New</label>
                                                    </div>
                                                    <div class="col-md-6 form-group">
                                                        <input type="password" id="confirmPassword"
                                                               class="form-control square field"
                                                               name="confirmPassword"
                                                               placeholder="Re-type New" autocomplete="off"
                                                               required>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3"></div>
                                                    <div class="col-md-6 form-group">
                                                        <button type="button"
                                                                class="btn btn-primary btn-sm square btnChangePassword">
                                                            Change Now
                                                        </button>
                                                    </div>
                                                </div>
                                                <br>
                                            </form>
                                        </div>

                                    </div>
                                    <div class="tab-pane fade" id="contact" role="tabpanel"
                                         aria-labelledby="contact-tab">
                                        <div class="accordion" id="cardAccordion">
                                            <div class="card">
                                                <div class="card-header" id="headingOne"
                                                     data-bs-toggle="collapse"
                                                     data-bs-target="#collapseOne" aria-expanded="false"
                                                     aria-controls="collapseOne" role="button">
                                                    <strong>Name: </strong>
                                                    <span class="collapsed collapse-title fullNameDisplay"></span>
                                                    <button type="button"
                                                            class="btn btn-info btn-s square cursor-pointer pull-right ">
                                                        <i class="fa fa-edit"></i> Edit
                                                    </button>
                                                </div>
                                                <div id="collapseOne" class="collapse pt-1"
                                                     aria-labelledby="headingOne"
                                                     data-parent="#cardAccordion">
                                                    <div class="card-body">
                                                        <form id="fullNameForm" class="fullNameForm">
                                                            <div class="row">
                                                                <div class="col-md-8 form-group">
                                                                    <input type="text" id="fullName"
                                                                           class="form-control square field"
                                                                           name="fullName"
                                                                           placeholder="Your Name" autocomplete="off"
                                                                           required>
                                                                </div>
                                                                <div class="col-md-4 form-group">
                                                                    <button type="button"
                                                                            class="btn btn-primary btn-sm square btnEditFullName">
                                                                        Save Changes
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card collapse-header">
                                                <div class="card-header" id="headingTwo"
                                                     data-bs-toggle="collapse"
                                                     data-bs-target="#collapseTwo" aria-expanded="false"
                                                     aria-controls="collapseTwo" role="button">
                                                    <strong>Username: </strong>
                                                    <span class="collapsed collapse-title usernameDisplay"></span>
                                                    <button type="button"
                                                            class="btn btn-info btn-s square cursor-pointer pull-right ">
                                                        <i class="fa fa-edit"></i> Edit
                                                    </button>
                                                </div>
                                                <div id="collapseTwo" class="collapse pt-1"
                                                     aria-labelledby="headingTwo"
                                                     data-parent="#cardAccordion">
                                                    <div class="card-body">
                                                        <form id="usernameForm" class="usernameForm">
                                                            <div class="row">
                                                                <div class="col-md-8 form-group">
                                                                    <input type="text" id="username"
                                                                           class="form-control square field"
                                                                           name="username"
                                                                           placeholder="Username" autocomplete="off"
                                                                           required>
                                                                    <small>Username must be unique and must not contain
                                                                        white space in between</small>
                                                                </div>
                                                                <div class="col-md-4 form-group">
                                                                    <button type="button"
                                                                            class="btn btn-primary btn-sm square btnEditUsername">
                                                                        Save Changes
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card">
                                                <div class="card-header" id="headingThree"
                                                     data-bs-toggle="collapse"
                                                     data-bs-target="#collapseThree" aria-expanded="false"
                                                     aria-controls="collapseFour" role="button">
                                                    <strong>Email: </strong>
                                                    <span class="collapsed collapse-title emailDisplay"></span>
                                                    <button type="button"
                                                            class="btn btn-info btn-s square cursor-pointer pull-right ">
                                                        <i class="fa fa-edit"></i> Edit
                                                    </button>
                                                </div>
                                                <div id="collapseThree" class="collapse pt-1"
                                                     aria-labelledby="headingFour"
                                                     data-parent="#cardAccordion">
                                                    <div class="card-body">
                                                        <form id="emailForm" class="emailForm">
                                                            <div class="row">
                                                                <div class="col-md-8 form-group">
                                                                    <input type="email" id="email"
                                                                           class="form-control square field"
                                                                           name="email"
                                                                           placeholder="Email" autocomplete="off"
                                                                           required>
                                                                    <small>Email address must be unique and valid
                                                                        one</small>
                                                                </div>
                                                                <div class="col-md-4 form-group">
                                                                    <button type="button"
                                                                            class="btn btn-primary btn-sm square btnEditEmail">
                                                                        Save Changes
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card">
                                                <div class="card-header" id="headingFour"
                                                     data-bs-toggle="collapse"
                                                     data-bs-target="#collapseFour" aria-expanded="false"
                                                     aria-controls="collapseFour" role="button">
                                                    <strong>Mobile Number : </strong>
                                                    <span class="collapsed  collapse-title mobileNoDisplay"></span>
                                                    <button type="button"
                                                            class="btn btn-info btn-s square cursor-pointer pull-right ">
                                                        <i class="fa fa-edit"></i> Edit
                                                    </button>
                                                </div>
                                                <div id="collapseFour" class="collapse pt-1"
                                                     aria-labelledby="headingFour"
                                                     data-parent="#cardAccordion">
                                                    <div class="card-body">
                                                        <form id="mobileNoForm" class="mobileNoForm">
                                                            <div class="row">
                                                                <div class="col-md-8 form-group">
                                                                    <input type="number" id="mobileNo"
                                                                           class="form-control square field"
                                                                           name="mobileNo"
                                                                           placeholder="Mobile No" autocomplete="off"
                                                                           required>
                                                                </div>
                                                                <div class="col-md-4 form-group">
                                                                    <button type="button"
                                                                            class="btn btn-primary btn-sm square btnEditMobileNo">
                                                                        Save Changes
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

</body>
</html>
