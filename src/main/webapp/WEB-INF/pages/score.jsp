<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html class="no-js" lang="en" xmlns="http://www.w3.org/1999/html"> <
<head>
    <title>Score</title>
</head>
<body>
<div class="main-content container-fluid">
    <div class="page-title">
        <h5>Overall Score</h5>
    </div>
    <section>
        <div class="card">
            <input type="hidden" id="companyIdHidden" value="${companyId}">
            <input type="hidden" id="yearHidden" value="${year}">
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
                            <i data-feather="check-circle"></i> Check Score
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section>
        <div class="row">
            <div class="col-md-8">
                <div class="card fadeInLeft animated">
                    <div class="card-body p-3 pb-0">
                        <div class="table-responsive">
                            <h6 class='justify-content-center'>Target wise score for the year 2022</h6>
                            <table id="scoreTableId" class="table table-bordered">
                                <thead style="background: #28a745; color: #fff">
                                <tr>
                                    <th>Sl. No</th>
                                    <th>Perspective</th>
                                    <th>Wt.</th>
                                    <th>Score</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Financial</td>
                                    <td>50</td>
                                    <td>50</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>Customer Service</td>
                                    <td>20</td>
                                    <td>20</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Organization Management</td>
                                    <td>10</td>
                                    <td>5</td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td>Production and Sales</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                </tbody>
                                <tfoot class="proratedScore">
                                <th colspan="3" style="text-align: right">Prorated Score</th>
                                <th id="proratedScore"><span class="badge bg-success">93.75</span></th>
                                </tfoot>
                                <tfoot>
                                <th colspan="2" style="text-align: right">Total</th>
                                <th id="totalWeightSp">80</th>
                                <th id="totalScoreSp">75</th>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card fadeInRight animated">
                    <div class="card-body p-3">
                        <div class="row align-items-center">
                            <div class="col-auto">
                                <div class="avatar avatar-xl">
                                    <%--                                    <div class="avatar-content">--%>
                                    <img src="<c:url value='/resources/assets/voler/images/favicon.png' />"
                                         alt="">
                                    <%--                                    </div>--%>
                                </div>
                            </div>
                            <div class="col">
                                <div class="font-weight-medium">Thimphu TechPark Limited</div>
                                <div class="text-muted">
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <small style="text-align: center !important;" class="scoreText"></small>
                                </div>
                                <div class="custom-progress">
                                    <div id="score"></div>
                                </div>
                            </div>
                        </div>
                        <div class="dropdown-divider"></div>
                        <small class="">Score above 90%:
                            <i class="fa fa-star successMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star successMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star successMsg" style="font-size: 10px"></i>
                            3 star
                        </small>
                        <div class="dropdown-divider"></div>
                        <small class="">Score bet. 80% to 90%:
                            <i class="fa fa-star light-green" style="font-size: 10px"></i>
                            <i class="fa fa-star light-green" style="font-size: 10px"></i>
                            <i class="fa fa-star-o light-green" style="font-size: 10px"></i>
                            2 star
                        </small>
                        <div class="dropdown-divider"></div>
                        <small class="">Score bet. 70% to 80%:
                            <i class="fa fa-star warningMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star-o warningMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star-o warningMsg" style="font-size: 10px"></i>
                            1 star
                        </small>
                        <div class="dropdown-divider"></div>
                        <small class="">Score below 70%:
                            <i class="fa fa-star-o errorMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star-o errorMsg" style="font-size: 10px"></i>
                            <i class="fa fa-star-o errorMsg" style="font-size: 10px"></i>
                            0 star
                        </small>
                        <div class="dropdown-divider"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="section">
        <div class="col-md-12">
            <div class="card fadeInDown animated">
                <div class="card-body pt-3">
                    <div class="col-md-12">
                        <h6 class='justify-content-center'>Percentage Scored as of 2022</h6>
                        <div class="dropdown-divider"></div>
                        <div id="evaluationGraph"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>