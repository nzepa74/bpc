<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sec:authorize access="hasAuthority('26-ADD')" var="hasAddRole"/>
<sec:authorize access="hasAuthority('26-EDIT')" var="hasEditRole"/>
<sec:authorize access="hasAuthority('26-DELETE')" var="hasDeleteRole"/>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Help</title>
</head>

<body class="nav-md">
<input type="hidden" id="hasAddRole" value="${hasAddRole}">
<input type="hidden" id="hasEditRole" value="${hasEditRole}">
<input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">

<div class="main-content container-fluid">
    <div class="mb-3"></div>
    <section id="basic-horizontal-layouts">
        <div class="row match-height">
            <div class="col-md-12 mb-3">
                <div class="row">
                    <div class="col-md-4">
                        <h5 class="help-title"><i class="fa fa-cog"></i> General Support</h5>
                        <div class="generalSupportDisplay"></div>
                        <div class="text-center">
                            <%--                                <sec:authorize access="hasAuthority('26-EDIT')">--%>
                            <button type="button" class="btn btn-primary btn-sm square btnGeneralSupportModal"
                                    data-bs-toggle="modal"
                                    data-bs-target="#generalSupportModal">
                                Update Detail
                            </button>
                            <%--                                </sec:authorize>--%>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <h5 class="help-title"><i class="fa fa-cogs"></i> Technical Support</h5>

                        <div class="technicalSupportDisplay"></div>
                        <div class="text-center">
                            <%--                                    <sec:authorize access="hasAuthority('26-EDIT')">--%>
                            <button type="button" class="btn btn-primary btn-sm square btnTechnicalSupportModal"
                                    data-bs-toggle="modal" data-bs-target="#technicalSupportModal">
                                Update Detail
                            </button>
                            <%--                                    </sec:authorize>--%>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <h5 class="help-title"><i class="fa fa-cloud-download"></i> Downloads</h5>
                        <div class="fileListDiv">
                            <ul class="list-unstyled fileUl"></ul>
                        </div>
                        <br/>
                        <div class="text-center">
                            <%--                                    <sec:authorize access="hasAuthority('26-EDIT')">--%>
                            <button type="button" class="btn btn-primary btn-sm square filesModal"
                                    data-bs-toggle="modal" data-bs-target="#filesModal">
                                Add Files
                            </button>
                            <%--                                    </sec:authorize>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="modal fade" id="generalSupportModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title"><i class="fa fa-cog"></i> General Support</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <form id="generalSupportFormId" class="generalSupportFormId" novalidate="novalidate">
                    <div class="form-group">
                        <textarea class="form-control contents field" id="generalSupportEditor"
                                  name="contents"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnSaveGeneralSupport"> Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="technicalSupportModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title"><i class="fa fa-cogs"></i> Technical Support</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <form id="technicalSupportFormId" class="technicalSupportFormId" novalidate="novalidate">
                    <div class="form-group">
                        <textarea class="form-control contents field" id="technicalSupportEditor"
                                  name="contents"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnSaveTechnicalSupport"> Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="filesModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title"><i class="fa fa-plus"></i> Add Files</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <form id="filesFormId" class="filesFormId" enctype="multipart/form-data">
                        <table class="table card-table table-vcenter text-nowrap" id="fileAttachmentTableId">
                            <tbody>
                            <tr style='border-top: hidden;border-bottom: hidden'>
                                <td>
                                    <input type="file" class="form-control attachedFile" id="attachedFile"
                                           name='fileAttachmentDTOs[0].attachedFile'
                                           accept="image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls"
                                           required>
                                </td>
                                <td>
                                    <button class='btn btn-s square btn-danger btn-s hidden' type='button'
                                            id='btnRemove'><i class='fa fa-trash'></i> Remove
                                    </button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class='btn btn-s square btn-success' type='button' id='btnAddMore'>
                                        <i class='fa fa-plus'></i> Add More
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnSaveFiles"> Save</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
