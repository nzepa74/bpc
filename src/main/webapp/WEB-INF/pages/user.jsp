<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepar
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
    <title>Users</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Users</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">User</li>
                    </ol>
                </nav>
            </div>
        </div>

    </div>
    <!-- Basic Horizontal form layout section start -->
    <section id="basic-horizontal-layouts">
        <div class="row match-height">
            <div class="col-md-12 col-12">
                <div class="card">
                    <div class="card-header border-bottom d-flex justify-content-between align-items-center">
                        <h4 class="card-title">User List</h4>
                        <button type="button" class="btn btn-primary square btn-s btnNewUser"
                                data-bs-toggle="modal" data-bs-target="#userModal"><i class="fa fa-plus"></i>New User
                        </button>
                    </div>

                    <div class="card-content">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="userListTable" class="table table-bordered" width="100%">
                                    <thead class="custom-thead">
                                    <tr>
                                        <th>Sl.No</th>
                                        <th class="hidden">Id</th>
                                        <th>Full Name</th>
                                        <th>Role</th>
                                        <th>Email</th>
                                        <th>Mobile No</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="modal fade" id="userModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalCenterTitle"></h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-body">
                    <form id="userFormId" class="userFormId" action="<c:url value='/user'/>"
                          novalidate="novalidate">
                        <input type="hidden" id="userId" class="form-control square field" name="userId"
                               autocomplete="off">
                        <div class="row">
                            <div class="col-md-2">
                                <label for="email"
                                       class="col-form-label required pull-right">Email</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <input type="email" id="email" class="form-control square field" name="email"
                                       placeholder="Email" autocomplete="off" required>
                            </div>

                            <div class="col-md-2">
                                <label for="fullName" class="col-form-label required pull-right">Full
                                    Name</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <input type="text" id="fullName" class="form-control square field" name="fullName"
                                       placeholder="Full Name" autocomplete="off" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <label for="mobileNo" class="col-form-label required pull-right">Mobile No.</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <input type="number" id="mobileNo" class="form-control square field"
                                       name="mobileNo" placeholder="Mobile No" autocomplete="off">
                            </div>
                            <div class="col-md-2">
                                <label for="status" class="col-form-label required pull-right">Status</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="statusList" id="status" name="status" required="required">
                                    <form:options items="${statusList}" itemValue="valueChar" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-2">
                                <label for="password"
                                       class="col-form-label required pull-right">Password</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <input type="password" id="password" class="form-control square field"
                                       name="password" placeholder="Password" autocomplete="off">
                            </div>
                            <div class="col-md-2">
                                <label for="confirmPassword" class="col-form-label required pull-right">Confirm
                                    PW</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <input type="password" id="confirmPassword" class="form-control square field"
                                       placeholder="Confirm Password" name="confirmPassword" autocomplete="off">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2 form-group"></div>
                            <div class="col-md-10 form-group">
                                <small><i class="fa fa-info-circle infoMsg"></i>
                                    Note: If password is empty, system will generate and send mail</small>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <label for="roleId" class="col-form-label required pull-right">Role</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="roleList" id="roleId" name="roleId" required="required">
                                    <form:options items="${roleList}" itemValue="valueInteger" itemLabel="valueText"/>
                                </form:select>
                            </div>
                            <div class="col-md-2">
                                <label for="roleId" class="col-form-label required pull-right">Company</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="companyList" id="companyId" name="companyId" required="required">
                                    <form:option value="">--- Please Select ---</form:option>
                                    <form:options items="${companyList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <label for="roleId" class="col-form-label required pull-right">Company Mapping</label>
                            </div>
                            <div class="col-md-10 form-group">
                                <form:select class="form-select square chosen-select" multiple="multiple"
                                             data-placeholder="Select Company" path="companyList" id="companyMappingId"
                                             name="companyMappingId" required="required">
                                    <form:options items="${companyList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <br>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnSaveUser"> Save</button>
                <button type="button" class="btn btn-primary square hidden btnUpdateUser">Update</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
