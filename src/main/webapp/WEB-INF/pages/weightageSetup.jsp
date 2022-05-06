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
    <title>Weightage Setup</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Weightage Setup</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Weightage Setup</li>
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
                    <div class="row">
                        <div class="col-md-2">
                            <label for="filterYear" class="col-form-label pull-right">Filter Year:</label>
                        </div>
                        <div class="col-md-2 form-group">
                            <form:select class="form-select square cursor-pointer chosen-select"
                                         path="yearList" id="filterYear" required="required">
                                <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>
                        <div class="col-md-7 form-group">
                            <button type="button" class="btn btn-primary btn-s square btnNewWeightage pull-right"
                                    data-bs-toggle="modal" data-bs-target="#weightageModal">
                                <i class="fa fa-plus"></i>Add New
                            </button>
                        </div>
                    </div>
                    <hr>
                    <div class="card-content">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="weightageTable" class="table table-bordered" width="100%">
                                    <thead class="custom-thead">
                                    <tr>
                                        <th>Sl.No</th>
                                        <th class="hidden">wId</th>
                                        <th>Year</th>
                                        <th>Company</th>
                                        <th>Financial (Wt.%)</th>
                                        <th>Customer Ser. (Wt.%)</th>
                                        <th>Org. Mgt (Wt.%)</th>
                                        <th>Production (Wt.%)</th>
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

<div class="modal fade" id="weightageModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
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
                    <form id="weightageFormId" class="weightageFormId" action="<c:url value='/api/weightage'/>"
                          novalidate="novalidate">
                        <input type="hidden" id="weightageSetupId" name="weightageSetupId"
                               class="form-control square field" autocomplete="off">
                        <div class="row">
                            <div class="col-md-3">
                                <label for="year" class="col-form-label required pull-right">Year:</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="yearList" id="year" name="year" required="required">
                                    <form:option value="">--- Please Select ---</form:option>
                                    <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label for="companyId" class="col-form-label required pull-right">Company</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="companyList" id="companyId" name="companyId" required="required">
                                    <form:option value="">--- Please Select ---</form:option>
                                    <form:options items="${companyList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label for="financialWt"
                                       class="col-form-label required pull-right">Financial (Wt.%)</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="number" id="financialWt" class="form-control square field"
                                       name="financialWt" placeholder="Financial" autocomplete="off" required>
                                <small>Put zero if not applicable</small>
                            </div>

                            <div class="col-md-3">
                                <label for="customerWt" class="col-form-label required pull-right">Customer
                                    Ser. (Wt.%)</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="number" id="customerWt" class="form-control square field" name="customerWt"
                                       placeholder="Customer Service" autocomplete="off" required>
                                <small>Put zero if not applicable</small>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label for="productionWt" class="col-form-label required pull-right">
                                    Production (Wt.%)</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="number" id="productionWt" class="form-control square field"
                                       name="productionWt" placeholder="Production" autocomplete="off" required>
                                <small>Put zero if not applicable</small>
                            </div>
                            <div class="col-md-3">
                                <label for="orgManagementWt"
                                       class="col-form-label required pull-right">Org. Mgt (Wt.%)</label>
                            </div>
                            <div class="col-md-3 form-group">
                                <input type="number" id="orgManagementWt" class="form-control square field"
                                       name="orgManagementWt" placeholder="Org. Management" autocomplete="off" required>
                                <small>Put zero if not applicable</small>
                            </div>
                        </div>
                        <div style="text-align: center;vertical-align: middle;position: relative;">
                            <span class="badge bg-info"><i
                                    class="fa fa-info-circle"></i> Note: Total weight must be 100</span>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
                <button type="button" class="btn btn-primary square btnSaveWeightage">
                    Save
                </button>
                <button type="button" class="btn btn-primary square hidden btnUpdateWeightage">
                    Update
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
