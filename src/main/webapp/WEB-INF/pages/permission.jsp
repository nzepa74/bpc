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
    <title>Permission</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" id="hasAddRole" value="${hasAddRole}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Permission</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Permission</li>
                    </ol>
                </nav>
            </div>
        </div>

    </div>
    <!-- Basic Horizontal form layout section start -->
    <div class="card">
        <div class="card-header border-bottom d-flex justify-content-between align-items-center">
            <h4 class="card-title">Permission List</h4>
            <%--                        <button type="button" class="btn btn-primary btn-s btnNewUser"--%>
            <%--                                data-bs-toggle="modal" data-bs-target="#userModal"><i class="fa fa-plus"></i>New User--%>
            <%--                        </button>--%>
        </div>

        <div class="card-content">
            <div class="card-body">
                <form id="permissionFormId" action="<c:url value='/permission'/> "
                      class="permissionFormId" novalidate="novalidate">
                    <div class="row">
                        <div class="col-md-2">
                            <label for="roleId" class="col-form-label required pull-right">Role</label>
                        </div>
                        <div class="col-md-4 form-group">
                            <form:select class="form-select square cursor-pointer chosen-select"
                                         path="roleList" id="roleId" name="roleId" required="required">
                                <form:option value="">--- Please Select ---</form:option>
                                <form:options items="${roleList}" itemValue="valueInteger"
                                              itemLabel="valueText"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="permissionTable" class="table table-bordered table-striped"
                               width="100%">
                            <thead>
                            <tr>
                                <th class="text-muted">Screen Id</th>
                                <th class="text-muted">Screen Name</th>
                                <th class="text-muted text-center">
                                    <span class="pull-left"><input type="checkbox" id="checkAllView">All</span>
                                    <label for="checkAllView">View</label>
                                </th>
                                <th class="text-muted text-center">Save</th>
                                <th class="text-muted text-center">Edit</th>
                                <th class="text-muted text-center">Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-md-2"></div>
                        <div class="col-md-4 form-group">
                            <button type="button" class="btn btn-primary square btnSavePermission">
                                Save
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
