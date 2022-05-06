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
    <title>Formulation - Organization Management 2</title>
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
            <div class="col-12 col-md-6 order-md-2 order-last mb-1">
                <button type="button" class="btn btn-outline-dark btn-s square btnGoBack" title="Click to go back">
                    <i class="fa fa-arrow-left"></i> Go Back
                </button>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Formulation</a></li>
                        <li class="breadcrumb-item active" aria-current="page"> Customer Service
                            <div class="badge bg-dark">2</div>
                        </li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="dropdown-divider"></div>
    <div class="row">
        <div class="col-md-8 form-group">
            <div class="btn-group dropdown mb-1">
                <span class="latestTextMsg"></span>
                <div id="ddown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                     data-reference="parent">
                    <div class="cursor-pointer hidden moreHere infoMsg" title="Click to see more">
                        <span class="countStatus"></span>
                        more here
                    </div>
                </div>
                <div class="dropdown-menu" aria-labelledby="ddown">
                    <ul class="list-unstyled moreStatusUl">
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3 form-group">
            <div class="btn-group dropdown mb-1 hidden editHistoryDiv">
                <div class="cursor-pointer dropdown-toggles dropdown-toggle-split"
                     id="editHistoryDropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                     data-reference="parent" title="Click to see more">
                    <i class="fa fa-history"></i> Edited <span class="historyCount"></span>
                </div>
                <div class="dropdown-menu" aria-labelledby="editHistoryDropdown">
                    <ul class="list-unstyled editHistoryUl"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-1 form-group">
            <div class="btn-group dropdown mb-1 pull-right chooseActionBtn">
                <button type="button"
                        class="btn btn-info btn-sm square dropdown-toggle dropdown-toggle-split"
                        id="dropdownMenuReference" data-bs-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" data-reference="parent">
                    Choose Action
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                            <span class="dropdown-item infoMsg cursor-pointer actionBtnEdit">
                                <i class="fa fa-edit"></i> Edit</span>
                    <div class="dropdown-divider"></div>
                    <span class="dropdown-item errorMsg cursor-pointer actionBtnDelete">
                                <i class="fa fa-trash errorMsg"></i> Delete</span>
                    <%--                    <c:choose>--%>
                    <%--                        <c:when test="${myRoleId!=creatorRoleId}">--%>
                    <div class="dropdown-divider actionBtn actionBtnClose"></div>
                    <span class="dropdown-item successMsg cursor-pointer actionBtn actionBtnClose">
                                <i class="fa fa-close"></i> Close</span>
                    <div class="dropdown-divider actionBtn actionBtnReopen"></div>
                    <span class="dropdown-item warningMsg cursor-pointer actionBtn actionBtnReopen">
                                <i class="fa fa-refresh"></i> Reopen</span>
                    <%--                        </c:when>--%>
                    <%--                    </c:choose>--%>
                </div>
            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-content">
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
                                <div class="editMode hidden">
                                    <form id="targetForm" class="targetForm" novalidate="novalidate">
                                        <input type="hidden" value="${targetAuditId}" id="targetAuditId"
                                               name="targetAuditId" autocomplete="off">
                                        <input type="hidden" id="targetId" name="targetId"
                                               autocomplete="off">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <label for="year"
                                                       class="col-form-label required pull-right">Year</label>
                                            </div>
                                            <div class="col-md-2 form-group">
                                                <form:select class="form-select square cursor-pointer"
                                                             path="yearList" id="year" required="required">
                                                    <form:option value="">--- Select ---</form:option>
                                                    <form:options items="${yearList}" itemValue="value"
                                                                  itemLabel="text"/>
                                                </form:select>
                                            </div>
                                            <div class="col-md-2">
                                                <label for="companyId"
                                                       class="col-form-label required pull-right">Company</label>
                                            </div>
                                            <div class="col-md-6 form-group">
                                                <form:select class="form-select square cursor-pointer"
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
                                            <tbody> </tbody>
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
                                <div class="displayMode">
                                    <div class="targetDetail"></div>

                                    <table id="displayTable" width="100%">
                                        <tbody></tbody>
                                    </table>
                                    <div class="row">
                                        <div class="col-md-12"><span>Explanation:</span> <strong
                                                class="explanationDisplay"> </strong></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-accordion mb-1">
                        <div class="accordoin-header btn-nav-accordion collapsed" id="headingTwo"
                             data-bs-toggle="collapse" role="button"
                             data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            <span class="collapsed collapse-title">
                                <span id="displayLogoWriteup" class="pull-left"></span>&nbsp;
                                Detail Writeup
                            </span>
                            <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                        </div>
                        <div id="collapseTwo" class="collapse pt-1" aria-labelledby="headingTwo"
                             data-parent="#cardAccordion">
                            <div class="card-body accordoin-body">
                                <div class="editMode hidden">
                                    <div class="row">
                                        <strong>a) Background</strong>
                                    </div>
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
                                        <div class="col-md-2">
                                            <strong>Evaluation Formula </strong>
                                        </div>
                                        <%--                                    <div class="row">--%>
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
                                <div class="displayMode">
                                    <div class="detailWriteup"></div>
                                    <div class="evalFormulaDisplay"></div>
                                    <div class="isProratableDisplay"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mb-2 hidden btnCancelDiv">
                        <div class="col-md-2 offset-2">
                            <button type="button" class="btn btn-warning btn-block btn-sm square btnCancelEdit"
                                    title="Click to cancel edit">
                                <i class="fa fa-times"></i> Cancel
                            </button>
                        </div>

                        <div class="col-md-2">
                            <button type="button" class="btn btn-primary btn-block btn-sm square btnEditTarget"
                                    title="Click to save changes">
                                <i class="fa fa-edit"></i> Save Changes
                            </button>
                        </div>
                    </div>

                    <div class="card-accordion mb-1">
                        <div class="accordoin-header btn-nav-accordion collapsed" id="headingThree"
                             data-bs-toggle="collapse" role="button"
                             data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseTwo">
                            <span id="displayLogoComment" class="pull-left"></span>&nbsp;
                            <span class="collapsed collapse-title commentTitle">
                                <span class="commentCount">0</span> <i class="fa fa-comment"></i> Comment
                            </span>
                            <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                        </div>
                        <div id="collapseThree" class="collapse pt-1" aria-labelledby="headingTwo"
                             data-parent="#cardAccordion">
                            <div class="card-body accordoin-body">
                                <form id="commentFormId" class="commentFormId" novalidate="novalidate">
                                    <div class="form-group">
                                        <textarea class="form-control contents field" id="comments"
                                                  name="comments"></textarea>
                                    </div>
                                    <div class="row mb-2 col-md-2 offset-2">
                                        <button type="button" title="Click to post comment"
                                                class="btn btn-primary btn-block btn-sm square btnAddComment">
                                            <i class="fa fa-comment"></i> Post Comment
                                        </button>
                                    </div>
                                </form>
                            </div>
                            <div style="padding-right: 30px;margin-left: -6px">
                                <ul id="commentUl"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="attachmentModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title attachmentModalTitle" id="exampleModalCenterTitle">
                    <i class="fa fa-paperclip"></i>Attachment List</h5>
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

<div class="modal fade" id="remarkModalEditMode" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title">Reviewer's Remark</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="targetNameDisplay"></div>
                </div>
                <form id="remarkFormId" class="remarkFormId" novalidate="novalidate">
                    <input type="hidden" id="remarkId" class="form-control square field" name="remarkId"
                           autocomplete="off">
                    <input type="hidden" id="subTargetId" name="subTargetId" autocomplete="off">
                    <div class="form-group">
                        <textarea class="form-control contents field" id="remark"
                                  name="remark"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnAddRemark"> Save</button>
                <button type="button" class="btn btn-primary square hidden btnEditRemark">Save Changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="remarkModalDisplayMode" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title">Reviewer's Remark</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="targetNameDisplay"></div>
                </div>
                <div id="displayRemark"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editHistoryModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-light">
                <h5 class="modal-title editHistoryModalTitle"></h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="targetDetailHistory"></div>
                <div class="subTargetDetailHistory"></div>
                <div class="detailWriteupHistory"></div>
                <div class="evalFormulaDisplayHistory"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="readCommentPolicyModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title"><i class="fa fa-file-text"></i> Comment Policy</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div id="policyDisplay"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="right-bottom-popup col-md-4 bounceInUp animated" id="commentPopupId">
    <div class="card square" style="border: 1px solid #528cfa;">
        <div class="card-body pt-2">
            <div class="card-header border-bottom d-flex justify-content-between align-items-center p-1 pt-0">
                <h4 class="card-title"><i class="fa fa-info-circle infoMsg"></i> System Information</h4>
            </div>

            <div class="card-content">
                <div class="form-group">
                    <div class="font-weight-medium">
                        You are seeing this because we noticed you attempting to comment for the first time. Please read
                        our comment policy before posting new comment.
                        We are urging all the new commenter to read and understand the comment policy before adding
                        comment to avoid consequences due to wrong comments.
                        If you have already read the comment policy you may close this notice.
                    </div>
                </div>
                <div class="form-group">
                    <span class="badge bg-info cursor-pointer btnCommentPolicy" data-bs-toggle="modal"
                          data-bs-target="#readCommentPolicyModal">Click here to read comment policy</span>
                </div>
                <div class="dropdown-divider"></div>
                <div class="form-group">
                    <button type="button" class="btn btn-warning square pull-right" onclick="closeCommentPolicy()">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function closeCommentPolicy() {
        document.getElementById("commentPopupId").style.display = "none";
    }
</script>

</body>
</html>
