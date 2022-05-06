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
    <title>Year</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Year</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Year</li>
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
                        <h4 class="card-title">Year List</h4>
                        <button type="button" class="btn btn-primary btn-s square btnNewYear"
                                data-bs-toggle="modal" data-bs-target="#yearModal">
                            <i class="fa fa-plus"></i>New Year
                        </button>
                    </div>

                    <div class="card-content">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="yearListTable" class="table table-bordered" width="100%">
                                    <thead class="custom-thead">
                                    <tr>
                                        <th>Sl.No</th>
                                        <th class="hidden">status</th>
                                        <th>Year</th>
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

<div class="modal fade" id="yearModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalCenterTitle"></h5>
                <button type="button" class="close" data-bs-dismiss="modal"
                        aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-body">
                    <form id="yearFormId" class="yearFormId" action="<c:url value='/api/year'/>"
                          novalidate="novalidate">
                        <input type="hidden" id="yearId" class="form-control square field" autocomplete="off">
                        <div class="row">
                            <div class="col-md-2">
                                <label for="year" class="col-form-label required pull-right">Year</label>
                            </div>
                            <div class="col-md-4 form-group">
                                <select class="form-select square cursor-pointer chosen-select" required
                                        id="year" name="year">
                                </select>
                            </div>
                        </div>
                        <div class="row">
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
                        <br>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
                <button type="button" class="btn btn-primary square btnSaveYear">
                    Save
                </button>
                <button type="button" class="btn btn-primary square hidden btnUpdateYear">
                    Update
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
