<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>Formulation - Organization Management 1</title>
</head>
<body>
<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-2 order-last mb-1">
                <button type="button" class="btn btn-outline-dark btn-s square btnGoBack" title="Click to go back">
                    <i class="fa fa-arrow-left"></i> Go Back
                </button>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Formulation</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Organization Management
                            <div class="badge bg-dark">1</div>
                        </li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="dropdown-divider"></div>
    <div class="card">
        <div class="card-content">
            <input type="hidden" id="yearHidden" value="${year}" class="yearId" autocomplete="off">
            <input type="hidden" value="${companyId}" id="companyIdHidden" autocomplete="off">
            <div class="collapse-icon accordion-icon-rotate">
                <div class="accordion" id="cardAccordion">
                    <div class="card-accordion mb-1 open">
                        <div class="accordoin-header btn-nav-accordion collapsed" id="headingOne"
                             data-bs-toggle="collapse"
                             data-bs-target="#collapseOne" aria-expanded="true"
                             aria-controls="collapseOne" role="button">
                            <span class="collapsed collapse-title">
                                <span id="displayLogoTargetDetail" class="pull-left"></span>&nbsp;
                                Target Detail
                            </span>
                            <small>(Total weightage for the year <span class="headingYear"></span> must be <span
                                    class="headingWt"></span>)</small>
                            <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                        </div>
                        <div id="collapseOne" class="collapse show pt-1"
                             aria-labelledby="headingOne" data-parent="#cardAccordion">
                            <div class="card-body accordoin-body">
                                <form id="targetForm" class="targetForm"
                                      action="<c:url value='/api/targetForm'/>" novalidate="novalidate">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <label for="year" class="col-form-label required pull-right">Year</label>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <form:select class="form-select square cursor-pointer chosen-select"
                                                         path="yearList" id="year" required="required">
                                                <form:option value="">--- Select ---</form:option>
                                                <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                                            </form:select>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="companyId"
                                                   class="col-form-label required pull-right">Company</label>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <form:select class="form-select square cursor-pointer chosen-select"
                                                         path="companyList" id="companyId" required="required">
                                                <form:option value="">--- Please Select ---</form:option>
                                                <form:options items="${companyList}" itemValue="value"
                                                              itemLabel="text"/>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <label for="serialNo" class="col-form-label required pull-right">
                                                Serial No.</label>
                                        </div>
                                        <div class="col-md-2 form-group">
                                            <input type="number" id="serialNo" class="form-control square field"
                                                   name="serialNo" required autocomplete="off">
                                        </div>
                                        <div class="col-md-2">
                                            <label for="activity" class="col-form-label required pull-right">
                                                Activity</label>
                                        </div>
                                        <div class="col-md-6 form-group">
                                            <textarea id="activity" class="form-control square field"
                                                      name="activity" autocomplete="off" required
                                                      onkeyup="textAreaAdjust(this)"></textarea>
                                        </div>
                                    </div>
                                    <table id="subTargetTable" style="width: 100%;">
                                        <tbody>
                                        <tr>
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label for="subTarget"
                                                               class="col-form-label required pull-right">
                                                            Target Output</label>
                                                    </div>
                                                    <div class="col-md-6 form-group">
                                                        <textarea id="subTarget" class="form-control square field"
                                                                  name="subTargetDtos[0].subTarget" autocomplete="off"
                                                                  required onkeyup="textAreaAdjust(this)"></textarea>
                                                        <div class="form-check">
                                                            <div class="pull-left custom-control custom-checkbox">
                                                                <input type="checkbox"
                                                                       class='form-check-input form-check-danger cursor-pointer'
                                                                       id="isNegative"
                                                                       name="subTargetDtos[0].isNegativeBoolean">
                                                                <label for="isNegative" class="cursor-pointer">
                                                                    <small style="text-transform: none;">
                                                                        Mark as negative target</small>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label for="deadline"
                                                               class="col-form-label required pull-right">
                                                            Deadline</label>
                                                    </div>
                                                    <div class="col-md-2 form-group">
                                                        <input type="text" id="deadline"
                                                               class="form-control datepicker square field"
                                                               name="subTargetDtos[0].deadline" required
                                                               autocomplete="off">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label for="weightage"
                                                               class="col-form-label required pull-right">
                                                            Weightage(%)</label>
                                                    </div>
                                                    <div class="col-md-2 form-group">
                                                        <input type="number" id="weightage"
                                                               class="form-control square field"
                                                               name="subTargetDtos[0].weightage" required
                                                               autocomplete="off">
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label for="attachedFiles"
                                                               class="col-form-label pull-right logoLabel">Attachment</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <input type="file" id="attachedFiles"
                                                               name="subTargetDtos[0].attachedFiles"
                                                               accept='image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls'
                                                               multiple
                                                               class="form-control square field cursor-pointer"/>
                                                        <small><i class="fa fa-info-circle infoMsg"></i>
                                                            Note: Accept PDF/DOCX/XLXS/PNG/JPG only
                                                        </small>
                                                    </div>
                                                    <div class="col-md-3"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="row">
                                        <div class="col-md-2"></div>
                                        <div class="col-md-10">
                                            <button type="button" class="btn btn-success btn-s square btnAddMore">
                                                <i class="fa fa-plus"></i> Add more sub target
                                            </button>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <label for="explanation" class="col-form-label pull-right">
                                                Explanation</label>
                                        </div>
                                        <div class="col-md-10 form-group">
                                            <textarea id="explanation" class="form-control square field"
                                                      name="explanation" autocomplete="off"
                                                      onkeyup="textAreaAdjust(this)"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="card-accordion mb-1">
                        <div class="accordoin-header btn-nav-accordion collapsed" id="headingTwo"
                             data-bs-toggle="collapse" role="button"
                             data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            <span class="collapsed  collapse-title">
                                <span id="displayLogoWriteup" class="pull-left"></span>&nbsp;
                                Detail Writeup
                            </span>
                            <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                        </div>
                        <div id="collapseTwo" class="collapse pt-1" aria-labelledby="headingTwo"
                             data-parent="#cardAccordion">
                            <div class="card-body accordoin-body">
                                <div class="form-group">
                                    <textarea class="form-control contents field" id="background"
                                              name="background"></textarea>
                                </div>
                                <div class="row">
                                    <strong>b) Target Output</strong>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control contents field" id="output"
                                              name="output"></textarea>
                                </div>
                                <div class="row">
                                    <strong>c) Risks Associated</strong>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control contents field" id="risks"
                                              name="risks"></textarea>
                                </div>
                                <div class="row">
                                    <strong>d) Evaluation Method </strong>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control contents field" id="evalMethod"
                                              name="evalMethod"></textarea>
                                </div>
                                <div class="row">
                                    <div class="col-md-2 form-group">
                                        <strong>Evaluation Formula </strong>
                                    </div>
                                    <%--                                <div class="row">--%>
                                    <div class="col-md-6 form-group">
                                        <div class="form-check form-check-success">
                                            <input type="radio" class="form-check-input cursor-pointer"
                                                   name="evalFormula" id="formulaTypeOne" value="1" checked>
                                            <label class="form-check-label cursor-pointer" for="formulaTypeOne"
                                                   style="text-transform: capitalize;">
                                                50% weight+(50% weight-((delayed month/target month)*50% weight))
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col-md-4 form-group">
                                        <div class="form-check form-check-success">
                                            <input type="radio" class="form-check-input cursor-pointer"
                                                   name="evalFormula" id="formulaTypeOthers" value="3">
                                            <label class="form-check-label cursor-pointer" for="formulaTypeOthers"
                                                   style="text-transform: capitalize;">
                                                Others
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-2">
                                        <label class="col-form-label"><strong>Is Proratable?</strong></label>
                                    </div>

                                    <div class="col-md-2 form-group">
                                        <div class="form-check form-check-success">
                                            <input type="radio" class="form-check-input cursor-pointer"
                                                   name="isProratable" id="isProratableYes" value="Y" checked>
                                            <label class="form-check-label cursor-pointer" for="isProratableYes"
                                                   style="text-transform: none;">
                                                Yes, it is proratable
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col-md-3 form-group">
                                        <div class="form-check form-check-success">
                                            <input type="radio" class="form-check-input cursor-pointer"
                                                   name="isProratable" id="isProratableNo" value="N">
                                            <label class="form-check-label cursor-pointer" for="isProratableNo"
                                                   style="text-transform: none;">
                                                No, it is not proratable
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-md-2"></div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-primary btn-block btn-sm square btnAddTarget">
                                <i class="fa fa-plus"></i>Add Target Now
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
