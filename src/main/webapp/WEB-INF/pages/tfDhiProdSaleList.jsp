<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 3/18/2022
  Time: 10:24 PM
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Formulation - Production/Sales/Projects 1 </title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <input type="hidden" id="myRoleId" value="${myRoleId}">
    <input type="hidden" id="adminRoleId" value="${adminRoleId}">
    <input type="hidden" id="creatorRoleId" value="${creatorRoleId}">
    <input type="hidden" id="reviewerRoleId" value="${reviewerRoleId}">
    <input type="hidden" id="boardRoleId" value="${boardRoleId}">
    <input type="hidden" id="ceoRoleId" value="${ceoRoleId}">
    <input type="hidden" id="submittedStage" value="${submittedStage}">
    <input type="hidden" id="approvedStage" value="${approvedStage}">
    <input type="hidden" id="revertedStage" value="${revertedStage}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last mb-2">
                <button type="button" class="btn btn-outline-dark btn-s square btnGoBack" title="Click to go back">
                    <i class="fa fa-arrow-left"></i> Go Back
                </button>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Formulation</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Production/Sales/Projects
                            <div class="badge bg-dark">1</div>
                        </li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="dropdown-divider"></div>
    <div class="row">
        <div class="col-md-10 form-group">
            <div class="btn-group dropdown mb-1">
                <span class="latestTextMsg"></span>
                <div id="ddown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                     data-reference="parent">
                    <div class="cursor-pointer hidden moreHere infoMsg" title="Click to see more">
                        <span class="countStage"></span> more here
                    </div>
                </div>
                <div class="dropdown-menu stageDiv" aria-labelledby="ddown">
                    <ul class="list-unstyled moreStagesUl"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-2 form-group actionBtns hidden">
            <div class="btn-group dropdown mb-1">
                <button type="button"
                        class="btn btn-info btn-sm square dropdown-toggle dropdown-toggle-split"
                        id="dropdownMenuReference" data-bs-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" data-reference="parent">
                    <span class="sr-only">Choose Action</span>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                    <c:choose>
                        <c:when test="${myRoleId==reviewerRoleId}">
                            <span class="dropdown-item successMsg cursor-pointer btnApprove">
                                <i class="fa fa-check"></i> Approve</span>
                            <div class="dropdown-divider"></div>
                            <span class="dropdown-item errorMsg cursor-pointer btnRevert">
                                <i class="fa fa-refresh errorMsg"></i> Revert</span>
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${myRoleId==creatorRoleId}">
                             <span class="dropdown-item successMsg cursor-pointer btnSubmit">
                                <i class="fa fa-mail-forward"></i> Submit</span>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <!-- Basic Horizontal form layout section start -->
    <input type="hidden" id="yearHidden" value="${year}" class="yearId" autocomplete="off">
    <input type="hidden" value="${companyId}" id="companyIdHidden" autocomplete="off">
    <section id="basic-horizontal-layouts">
        <div class="row match-height">
            <div class="col-md-12 col-12">
                <div class="card">
                    <form id="targetListForm" class="targetListForm">
                        <div class="row" style="margin-top: 10px">
                            <div class="col-md-1">
                                <label for="year" class="col-form-label pull-right">Year</label>
                            </div>
                            <div class="col-md-2 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="yearList" id="year" name="year" required="required">
                                    <form:option value="">--- Select ---</form:option>
                                    <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                            <div class="col-md-1">
                                <label for="companyId" class="col-form-label pull-right">Company</label>
                            </div>
                            <div class="col-md-5 form-group">
                                <form:select class="form-select square cursor-pointer chosen-select"
                                             path="companyList" id="companyId" name="companyId" required="required">
                                    <form:option value="">--- Please Select ---</form:option>
                                    <form:options items="${companyList}" itemValue="value" itemLabel="text"/>
                                </form:select>
                            </div>
                            <div class="col-md-3 form-group">
                                <button type="button" class="btn btn-primary btn-sm square btnSearch"
                                        id="btnSearchProdSale">
                                    <i class="fa fa-search animate icon-animated-wrench"></i> Search
                                </button>
                                <button type="button" class="btn btn-primary btn-sm square btnAddNewTarget">
                                    <i class="fa fa-plus"></i> Add New Target
                                </button>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="card-body">
                                <div class="table-responsive">
                                    <span class="stageSearch"></span>
                                    <table id="targetListTable" class="table table-bordered" width="100%">
                                        <thead>
                                        <tr>
                                            <th>Sl.No</th>
                                            <th>Particular</th>
                                            <th>Unit</th>
                                            <th><span class="preYearActualLbl"></span> Actual</th>
                                            <th><span class="preYearTargetLbl"></span> Target</th>
                                            <th><span class="curYearTargetLbl"></span> Target</th>
                                            <th>Wt.(<span class="totalWeight">0</span>%)</th>
                                            <th><span class="curYearDhiProposalLbl"></span> DHI Proposal</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="modal fade" id="attachmentModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title attachmentModalTitle"></h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="targetNameDisplay"></div>
                </div>
                <div class="fileListDiv">
                    <ul class="list-unstyled fileUl"></ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="commentModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title attachmentModalTitle"></h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="targetNameDisplay"></div>
                </div>
                <div class="commentListDiv">
                    <ul class="list-unstyled commentUl" id="commentUl"></ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="writeupModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title"><i class="fa fa-file-text"></i>Detail Writeup</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="targetNameDisplay"></div>
                </div>
                <div class="detailWriteup"></div>
                <div class="evalFormulaDisplay"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
