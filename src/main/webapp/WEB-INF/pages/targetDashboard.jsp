<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 3/18/2022
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Overall Dashboard</title>
</head>
<body>

<div class="main-content container-fluid">
    <input type="hidden" id="myCompanyId" value="${myCompanyId}">
    <input type="hidden" id="myRoleId" value="${myRoleId}">
    <input type="hidden" id="adminRoleId" value="${adminRoleId}">
    <input type="hidden" id="creatorRoleId" value="${creatorRoleId}">
    <input type="hidden" id="reviewerRoleId" value="${reviewerRoleId}">
    <input type="hidden" id="boardRoleId" value="${boardRoleId}">
    <input type="hidden" id="ceoRoleId" value="${ceoRoleId}">
    <input type="hidden" id="yId" value="${yId}">
    <input type="hidden" id="cId" value="${cId}">
    <div class="page-title">
        <div class="row">
            <div class="col-12 col-md-6 order-md-1 order-last">
                <h5>Overall Status</h5>
            </div>
            <div class="col-12 col-md-6 order-md-2 order-first">
                <nav aria-label="breadcrumb" class='breadcrumb-header'>
                    <ol class="breadcrumb">
                    </ol>
                </nav>
            </div>
        </div>
    </div>
    <section>
        <div class="card">
            <div class="card-body p-2 pb-0">
                <div class="row">
                    <div class="col-md-1">
                        <span id="displayLogoSearch" class="pull-left"></span>
                    </div>
                    <div class="col-md-1">
                        <label for="year" class="col-form-label">Year</label>
                    </div>
                    <div class="col-md-2 form-group">
                        <form:select class="form-control form-select square cursor-pointer chosen-select"
                                     path="yearList" id="year" required="required">
                            <form:option value="">--- Select ---</form:option>
                            <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                        </form:select>
                    </div>

                    <div class="col-md-1">
                        <label for="companyId" class="col-form-label">Company</label>
                    </div>
                    <div class="col-md-5 col-sm-5 form-group">
                        <form:select class="form-control form-select square cursor-pointer chosen-select"
                                     path="companyList" id="companyId" required="required">
                            <form:option value="">--- Please Select ---</form:option>
                            <form:options items="${companyList}" itemValue="value" itemLabel="text"/>
                        </form:select>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-success btn-sm square btnCheck"
                                title="Click to check status">
                            <i data-feather="check-circle"></i> Check Status
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section>
        <div class="collapse-icon accordion-icon-rotate">
            <div class="accordion" id="cardAccordion">
                <div class="card-accordion open mb-1">
                    <div class="accordoin-header btn-nav-accordion collapsed" id="headingOne"
                         data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true"
                         aria-controls="collapseOne" role="button">
                    <span class="collapsed collapse-title">
                        <span id="displayLogoFin" class="pull-left"></span>&nbsp;Financial Target
                    </span>
                        <span class="financialWt"></span>
                        <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                    </div>
                    <div id="collapseOne" class="collapse show pt-1"
                         aria-labelledby="headingOne" data-parent="#cardAccordion">
                        <div class="card-body accordoin-body dashboard-accordoin-body">
                            <div class="row">
                                <div class="col-12 col-md-2 cursor-pointer tfDhiFinancial">
                                    <div class="card box-shadow square">
                                        <div class="card-body p-2" title="Click to view">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">1</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Formulation</small>
                                                    </div>
                                                </div>
                                                <div class="card-content">
                                                    <small class="shortName">BOB</small> <small> & DHI</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class=" d-flex align-items-center successMsg">
                                                        <i data-feather="check-circle"></i><small>Completed</small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 cursor-pointer tfBcpmFinancial">
                                    <div class="card box-shadow">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">2</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Formulation</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class="card-right d-flex align-items-center successMsg">
                                                        <i data-feather="check-circle"></i><small>Completed </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2">
                                    <div class="card glow-shadow">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">3</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Midterm Review</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small> <small> & DHI</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class="card-right d-flex align-items-center warningMsg">
                                                        <i data-feather="check-circle"></i><small>Pending </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2">
                                    <div class="card">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">4</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Midterm Review</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class="card-right d-flex align-items-center errorMsg">
                                                        <i data-feather="circle"></i><small>Not Started </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2">
                                    <div class="card">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">5</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Annual Review</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small><small> & DHI</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class="card-right d-flex align-items-center errorMsg">
                                                        <i data-feather="circle"></i><small>Not Started </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2">
                                    <div class="card">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">6</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Annual Review</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small><small> & DHI</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class="card-right d-flex align-items-center errorMsg">
                                                        <i data-feather="circle"></i><small>Not Started </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-accordion mb-1">
                    <div class="accordoin-header btn-nav-accordion collapsed" id="headingTwo"
                         data-bs-toggle="collapse" role="button"
                         data-bs-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                     <span class="collapsed  collapse-title">
                         <span id="displayLogoCusSer" class="pull-left"></span>&nbsp;Customer Service
                     </span>
                        <span class="customerWt"></span>
                        <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                    </div>
                    <div id="collapseTwo" class="collapse show pt-1" aria-labelledby="headingTwo"
                         data-parent="#cardAccordion">
                        <div class="card-body accordoin-body dashboard-accordoin-body">
                            <div class="row">
                                <div class="col-12 col-md-2 cursor-pointer tfDhiCusSer">
                                    <div class="card box-shadow">
                                        <div class="card-body p-2" title="Click to view">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">1</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Formulation</small>
                                                    </div>
                                                </div>
                                                <div class="card-content">
                                                    <small class="shortName">BOB</small> <small> & DHI</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                    <div class=" d-flex align-items-center successMsg">
                                                        <i data-feather="check-circle"></i><small>Completed</small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 cursor-pointer tfBcpmCusSer">
                                    <div class="card box-shadow">
                                        <div class="card-body p-2">
                                            <div class="d-flex flex-column">
                                                <div class='d-flex justify-content-between mb-1'>
                                                    <div class="avatar avatar-sm bg-dark">
                                                        <span class="avatar-content">2</span>
                                                    </div>
                                                    <div class="card-right d-flex align-items-center">
                                                        <small>Formulation</small>
                                                    </div>
                                                </div>
                                                <div>
                                                    <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                                </div>
                                                <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center successMsg">
                                                    <i data-feather="check-circle"></i><small>Completed </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card glow-shadow">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">3</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small> <small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center warningMsg">
                                                    <i data-feather="check-circle"></i><small>Pending </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">4</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">5</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">6</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-accordion mb-1">
                <div class="accordoin-header btn-nav-accordion collapsed" id="headingThree"
                     data-bs-toggle="collapse" role="button"
                     data-bs-target="#collapseThree" aria-expanded="true" aria-controls="collapseTwo">
                    <span class="collapsed collapse-title">
                        <span id="displayLogoOrgMgt" class="pull-left"></span>&nbsp;Organization Management
                    </span>
                    <span class="orgManagementWt"></span>
                    <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                </div>
                <div id="collapseThree" class="collapse show pt-1" aria-labelledby="headingTwo"
                     data-parent="#cardAccordion">
                    <div class="card-body accordoin-body dashboard-accordoin-body">
                        <div class="row">
                            <div class="col-12 col-md-2 cursor-pointer tfDhiOrgMgt">
                                <div class="card box-shadow">
                                    <div class="card-body p-2" title="Click to view">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">1</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Formulation</small>
                                                </div>
                                            </div>
                                            <div class="card-content">
                                                <small class="shortName">BOB</small> <small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class=" d-flex align-items-center successMsg">
                                                    <i data-feather="check-circle"></i><small>Completed</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2 cursor-pointer tfBcpmOrgMgt">
                                <div class="card box-shadow">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">2</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Formulation</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center successMsg">
                                                    <i data-feather="check-circle"></i><small>Completed </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card glow-shadow">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">3</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small> <small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center warningMsg">
                                                    <i data-feather="check-circle"></i><small>Pending </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">4</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">5</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">6</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-accordion mb-1">
                <div class="accordoin-header btn-nav-accordion collapsed" id="headingFour"
                     data-bs-toggle="collapse" role="button"
                     data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseTwo">
                    <span class="collapsed collapse-title">
                        <span id="displayLogoProdSale" class="pull-left"></span>&nbsp;
                        Production/Sales/Project</span>
                    <span class="productionWt"></span>
                    <i data-feather="chevron-up" class="fa-chevron-up pull-right"></i>
                </div>
                <div id="collapseFour" class="collapse show pt-1" aria-labelledby="headingTwo"
                     data-parent="#cardAccordion">
                    <div class="card-body accordoin-body dashboard-accordoin-body">
                        <div class="row">
                            <div class="col-12 col-md-2 cursor-pointer tfDhiProdSale">
                                <div class="card box-shadow">
                                    <div class="card-body p-2" title="Click to view">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">1</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Formulation</small>
                                                </div>
                                            </div>
                                            <div class="card-content">
                                                <small class="shortName">BOB</small> <small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class=" d-flex align-items-center successMsg">
                                                    <i data-feather="check-circle"></i><small>Completed</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2 cursor-pointer tfBcpmProdSale">
                                <div class="card box-shadow">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">2</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Formulation</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center successMsg">
                                                    <i data-feather="check-circle"></i><small>Completed </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card glow-shadow">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">3</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small> <small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center warningMsg">
                                                    <i data-feather="check-circle"></i><small>Pending </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">4</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Midterm Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small>, DHI & BCPM</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">5</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div class="card">
                                    <div class="card-body p-2">
                                        <div class="d-flex flex-column">
                                            <div class='d-flex justify-content-between mb-1'>
                                                <div class="avatar avatar-sm bg-dark">
                                                    <span class="avatar-content">6</span>
                                                </div>
                                                <div class="card-right d-flex align-items-center">
                                                    <small>Annual Review</small>
                                                </div>
                                            </div>
                                            <div>
                                                <small class="shortName">BOB</small><small> & DHI</small>
                                            </div>
                                            <div class='d-flex justify-content-between'>
                                                <div class="card-right d-flex align-items-center errorMsg">
                                                    <i data-feather="circle"></i><small>Not Started </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-12 col-md-2 tfDhiTargetDoc">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button" class="btn btn-outline-info square btn-s btnCompactDocOne">
                                        <i class="fa fa-eye"></i>View Compact
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-2">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button"
                                            class="btn btn-outline-info square btn-s btnGenerate">
                                        <i class="fa fa-eye"></i>View Document
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-2">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button"
                                            class="btn btn-outline-info square btn-s btnGenerate">
                                        <i class="fa fa-eye"></i>View Document
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-2">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button"
                                            class="btn btn-outline-info square btn-s btnGenerate">
                                        <i class="fa fa-eye"></i>View Document
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-2">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button"
                                            class="btn btn-outline-info square btn-s btnGenerate">
                                        <i class="fa fa-eye"></i>View Document
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-2">
                    <div class="card">
                        <div class="card-body p-2 cursor-pointer" title="Click to view">
                            <div class="d-flex flex-column">
                                <div class='d-flex justify-content-between mb-1'>
                                    <div class="avatar avatar-sm bg-dark">
                                        <span class="avatar-content">1</span>
                                    </div>
                                    <div class="card-right d-flex align-items-center">
                                        <small>Formulation</small>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <button type="button"
                                            class="btn btn-outline-info square btn-s btnGenerate">
                                        <i class="fa fa-eye"></i>View Document
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>
