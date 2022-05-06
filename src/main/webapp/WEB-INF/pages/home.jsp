<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html class="no-js" lang="en" xmlns="http://www.w3.org/1999/html"> <
<head>
    <title>Bhutan Power Corporation Limited</title>
</head>
<body>
<div class="main-content container-fluid">
    <div class="page-title">
        <div class="row">
            <div class="col-md-7">
                <h5>Overall Score</h5>
            </div>
            <div class="col-md-1">
                <label for="year" class="col-form-label pull-right">Year</label>
            </div>
            <div class="col-md-2 form-group">
                <form:select class="form-control form-select square cursor-pointer chosen-select"
                             path="yearList" id="year" required="required">
                    <form:option value="">--- Select ---</form:option>
                    <form:options items="${yearList}" itemValue="value" itemLabel="text"/>
                </form:select>
            </div>
            <div class="col-md-2 form-group">
                <button type="button" class="btn btn-outline-info btn-sm square pull-right" title="Click to check">
                    <i data-feather="search" class="animate icon-animated-wrench"></i> Check Score
                </button>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <section class="section">
            <div class="row">
                <div class="col-md-3 bounceIn animated">
                    <div class="card p-2">
                        <div class="row align-items-center">
                            <div class="col-auto col-md-3">
                                <div class="avatar avatar-lg bg-danger">
                                            <span class="avatar-content">
                                                <i class="fa fa-line-chart" style="font-size: 15px"></i>
                                            </span>
                                </div>
                            </div>
                            <div class="col col-md-9">
                                <div class="font-weight-medium">Score below 70%</div>
                                <div class="text-muted">
                                    <i class="fa fa-star-o errorMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star-o errorMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star-o errorMsg" style="font-size: 12px"></i>
                                    <small>3 Companies</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 bounceIn animated">
                    <div class="card p-2">
                        <div class="row align-items-center">
                            <div class="col-auto col-md-3">
                                <div class="avatar avatar-lg bg-warning">
                                            <span class="avatar-content">
                                                <i class="fa fa-line-chart" style="font-size: 17px"></i>
                                            </span>
                                </div>
                            </div>
                            <div class="col col-md-9">
                                <div class="font-weight-medium"> Score bw. 70% to 80%</div>
                                <div class="text-muted">
                                    <i class="fa fa-star warningMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star-o warningMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star-o warningMsg" style="font-size: 12px"></i>
                                    <small>6 Companies</small></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 bounceIn animated">
                    <div class="card p-2">
                        <div class="row align-items-center">
                            <div class="col-auto col-md-3">
                                <div class="avatar avatar-lg bg-light-green">
                                            <span class="avatar-content">
                                                <i class="fa fa-line-chart" style="font-size: 17px"></i>
                                            </span>
                                </div>
                            </div>
                            <div class="col col-md-9">
                                <div class="font-weight-medium">Score bw. 81% to 90%</div>
                                <div class="text-muted">
                                    <i class="fa fa-star light-green" style="font-size: 12px"></i>
                                    <i class="fa fa-star light-green" style="font-size: 12px"></i>
                                    <i class="fa fa-star-o light-green" style="font-size: 12px"></i>
                                    <small>3 Companies</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 bounceIn animated">
                    <div class="card p-2">
                        <div class="row align-items-center">
                            <div class="col-auto col-md-3">
                                <div class="avatar avatar-lg bg-success">
                                    <span class="avatar-content">
                                        <i class="fa fa-line-chart" style="font-size: 17px"></i>
                                    </span>
                                </div>
                            </div>
                            <div class="col col-md-9">
                                <div class="font-weight-medium">Score above 90%</div>
                                <div class="text-muted">
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <i class="fa fa-star successMsg" style="font-size: 12px"></i>
                                    <small>1 Company</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="section">
            <div class="card fadeInDown animated">
                <div class="card-body pt-3">
                    <div class="col-md-12">
                        <h6 class='justify-content-center'>Percentage Scored for the year 2022 by each company</h6>
                        <div id="allCompanyScoreGraph"></div>
                    </div>

                    <div style="text-align: center;vertical-align: middle;position: relative;" class="pull-right">
                        <small class="infoMsg"><i class="fa fa-info-circle"></i>
                            Click on the bar to see score for individual company</small>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>