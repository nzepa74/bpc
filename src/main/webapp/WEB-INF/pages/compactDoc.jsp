<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title>Compact Document</title>
</head>
<body>
<div class="main-content container-fluid">
    <input type="hidden" id="stage" value="${stage}">
    <input type="hidden" id="year" value="${yId}">
    <input type="hidden" id="companyId" value="${cId}">
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
                        <li class="breadcrumb-item"><a>Compact</a></li>
                        <li class="breadcrumb-item active" aria-current="page">
                            <span class="stageName">Formulation</span>
                            <div class="badge bg-dark stageCount">1</div>
                        </li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <div class="dropdown-divider"></div>
    <div class="card">
        <div class="card-body">
            <section class="mb-4">
                <div class="row match-height">
                    <div class="col-md-12">
                        <div class="d-flex justify-content-center align-items-center
                            justify-content-around flex-column p-3" style="border: 1px solid black">
                            <h3 class="companyName mb-3 mt-3"></h3>
                            <div id="logo" class="mt-3"></div>
                            <div class="mt-5 mb-2">
                                <strong>ANNUAL COMPACT </strong><strong class="reportingYear">${yId}</strong>
                            </div>
                            <div style="border-top: 1px solid black" class="mb-4">
                                Reporting Period: January - December
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <div class="dropdown-divider"></div>
            <section>
                <div class="col-md-12 mb-3">
                    <div class="table-responsive">
                        <strong>1. Financial</strong><strong class="finTotalWeight"></strong>
                        <table id="finTarListTable" class="table table-bordered" width="100%">
                            <thead>
                            <tr>
                                <th>SL</th>
                                <th>Financial KPI</th>
                                <th><span class="preYearLbl"></span> Actual</th>
                                <th><span class="curYearLbl"></span> Target</th>
                                <th>Wt.(%)</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-12 mb-3">
                    <div class="table-responsive">
                        <strong>2. Customer Service</strong><strong class="cusSerTotalWeight"></strong>
                        <table id="cusSerTarListTable" class="table table-bordered" width="100%">
                            <thead>
                            <tr>
                                <th>SL</th>
                                <th class="hidden">id</th>
                                <th>Activity</th>
                                <th>Target/Output</th>
                                <th>Deadline</th>
                                <th>Wt.(%)</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-12 mb-3">
                    <div class="table-responsive">
                        <strong>3. Organizational Management</strong><strong class="orgMgtTotalWeight"></strong>
                        <table id="orgMgtTarListTable" class="table table-bordered" width="100%">
                            <thead>
                            <tr>
                                <th>SL</th>
                                <th class="hidden">id</th>
                                <th>Activity</th>
                                <th>Target/Output</th>
                                <th>Deadline</th>
                                <th>Wt.(%)</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-12 mb-3">
                    <div class="table-responsive">
                        <strong>4. Production/Sales/Projects</strong><strong class="prodSaleTotalWeight"></strong>
                        <table id="prodSaleTarListTable" class="table table-bordered" width="100%">
                            <thead>
                            <tr>
                                <th>SL</th>
                                <th class="hidden">id</th>
                                <th>Particular</th>
                                <th>Particular Description</th>
                                <th>Unit</th>
                                <th><span class="preYearLbl"></span> Actual</th>
                                <th><span class="curYearLbl"></span> Proposal</th>
                                <th>Wt.(%)</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </section>
            <section>
                <div class="row match-height">
                    <div class="finWriteup"></div>
                    <div class="cusSerWriteup"></div>
                    <div class="orgMgtWriteup"></div>
                    <div class="prodSaleWriteup"></div>
                </div>
            </section>
            <section>
                <div class="row">
                    <div class="dropdown-divider"></div>
                    <div class="col-md-5">
                        <button type="button" class="btn btn-info square btn-s pull-right btnDownloadWord">
                            <i class="fa fa-download"></i> Download to word <i class="fa fa-file-word-o"></i>
                        </button>
                    </div>
                    <div class="col-md-5">
                        <button type="button" class="btn btn-warning square btn-s btnDownloadWord">
                            <i class="fa fa-download"></i> Download to pdf <i class="fa fa-file-pdf-o"></i>
                        </button>
                    </div>
                    <div class="col-md-2">
                        <div class="pull-right">
                            <a href="#top">Go to top <i data-feather="chevron-up"></i></a>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
</body>
</html>
