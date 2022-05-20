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
    <title>Employee</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Employee</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Emlpee</li>
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
                    <br>
                    <div class="col-md-12">
                        <button type="button" class="btn btn-primary btn-s square btnNewEmployee pull-right"
                                data-bs-toggle="modal" data-bs-target="#employeeModal">
                            <i class="fa fa-plus"></i>Add New
                        </button>
                    </div>
                    <hr>
                    <div class="card-content">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="employeeTable" class="table table-bordered" width="100%">
                                    <thead class="custom-thead">
                                    <tr>
                                        <th>Sl.No</th>
                                        <th>Employee Id</th>
                                        <th>Name</th>
                                        <th>Gender</th>
                                        <th>Contact No</th>
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

<div class="modal fade" id="employeeModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
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
                    <form id="employeeFormId" class="employeeFormId" action="<c:url value='/api/employee'/>"
                          novalidate="novalidate">

                        <div class="row">
                            <div class="col-md-3">
                                Dzo:
                                <form:select class="form-control form-select square cursor-pointer chosen-select"
                                             path="dzongList" id="dzongkhagId" required="required" name="dzongkhagId">
                                    <form:option value="">--- Select ---</form:option>
                                    <form:options items="${dzongList}" itemValue="valueInteger" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                Geog:
                                <select class="form-control form-select square cursor-pointer" id="geogId"> </select>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3">
                                <label for="employeeId"
                                       class="col-form-label required pull-right">Employee ID</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="text" id="employeeId" class="form-control square field"
                                       name="employeeId" autocomplete="off" required>
                            </div>

                            <div class="col-md-3">
                                <label for="fullName" class="col-form-label required pull-right">Full Name</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="text" id="fullName" class="form-control square field" name="fullName"
                                       autocomplete="off" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label class="col-form-label required pull-right">Gender</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="radio" id="male" name="gender" value="M">
                                <label for="male">Male</label><br>
                                <input type="radio" id="female" name="gender" value="F">
                                <label for="female">Female</label><br>
                                <input type="radio" id="other" name="gender" value="O">
                                <label for="other">Other</label>
                            </div>
                            <div class="col-md-3">
                                <label for="contactNo"
                                       class="col-form-label required pull-right">Contact No</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="text" id="contactNo" class="form-control square field"
                                       name="contactNo" autocomplete="off" required>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
                <button type="button" class="btn btn-primary square btnSaveEmployee">
                    Save
                </button>
                <button type="button" class="btn btn-primary square hidden btnUpdateEmployee">
                    Update
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
