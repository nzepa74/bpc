<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 9/22/2021
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CommentPolicy</title>
</head>

<body>
<div class="main-content container-fluid">
    <section id="basic-horizontal-layouts">
        <div class="row match-height">
            <div class="col-md-12 col-12">
                <div class="contentDisplay"></div>
                <br>
                <div class="text-center">
                    <%--                            <sec:authorize access="hasAuthority('25-ADD')">--%>
                    <button type="button" class="btn btn-primary btn-sm square btnAddCommentPolicyModal"
                            data-bs-toggle="modal" data-bs-target="#commentPolicyModal">
                        Add Comment Policy
                    </button>
                    <%--                            </sec:authorize>--%>
                    <%--                            <sec:authorize access="hasAuthority('25-EDIT')">--%>
                    <button type="button" class="btn btn-primary btn-sm square btnEditCommentPolicyModal"
                            data-bs-toggle="modal"
                            data-bs-target="#commentPolicyModal">
                        Update Comment Policy
                    </button>
                    <%--                            </sec:authorize>--%>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="modal fade" id="commentPolicyModal" tabindex="-1" role="dialog" data-bs-backdrop="static"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title">CommentPolicy</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                    <i data-feather="x"></i>
                </button>
            </div>
            <div class="modal-body">
                <form id="commentPolicyFormId" class="commentPolicyFormId" novalidate="novalidate">
                    <input type="hidden" id="commentPolicyId" class="form-control square field" name="commentPolicyId"
                           autocomplete="off">
                    <div class="form-group">
                         <textarea class="form-control contents field" id="editorCommentPolicy"
                                   name="contents"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning square" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary square btnSaveCommentPolicy"> Save</button>
                <button type="button" class="btn btn-primary square hidden btnEditCommentPolicy">Save Changes</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
