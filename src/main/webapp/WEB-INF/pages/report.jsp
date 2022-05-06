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
<html>
<head>
    <title>Report</title>
</head>
<body>

<div class="main-content container-fluid">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Report</h5>
                <%--                <p class="text-subtitle text-muted">There's a lot of form layout that you can use</p>--%>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a>Setting</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Report</li>
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
                    <div class="card-content">
                        <div class="card-body">
                            <form id="reportFormId" class="reportFormId" action="<c:url value='/api/report'/>"
                                  novalidate="novalidate">
                                <input type="hidden" id="yearId" class="form-control square field" autocomplete="off">
                                <div class="row">

                                    <div class="col-md-4 form-group">
                                        <div class="col-md-7 required-field">
                                            &nbsp; <input type="radio" id="pdf" value="pdf" name="documentType" checked>
                                            <label for="pdf" class="cursor-pointer">PDF </label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;<input type="radio" id="docx" value="docx" name="documentType">
                                            <label for="docx" class="cursor-pointer">Docx </label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;<input type="radio" id="xls" class="hidden" value="xls"
                                                         name="documentType">
                                            <label for="xls" id="xlsLabel" class="cursor-pointer" hidden>Excel </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 form-group">
                                        <button type="button" class="btn btn-primary square btnGenerate">
                                            Generate
                                        </button>
                                    </div>
                                </div>
                                <br>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

</body>
</html>
