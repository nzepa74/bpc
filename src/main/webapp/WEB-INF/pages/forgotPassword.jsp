<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nzepa
  Date: 4/1/2022
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
<div id="auth">
    <div class="container">
        <div class="row">
            <div class="col-md-5 col-sm-12 mx-auto">
                <div class="card py-2 square">
                    <div class="card-body">
                        <div class="text-center mb-3">
                            <img src="<c:url value='/resources/assets/voler/images/logo.png' />" height="48"
                                 class='mb-3' alt="">
                            <h3>BPC Compact Management System</h3>
                            <h5>Forgot Password</h5>
                            <span>Please enter your email to receive password reset link.</span>
                        </div>
                        <form id="forgotPasswordForm" class="forgotPasswordForm">
                            <div class="form-group">
                                <label for="email" style="text-transform: none;" class="required">Email</label>
                                <input type="email" id="email" class="form-control square" name="email"
                                       autocomplete="off"
                                       required>
                            </div>
                            <div class="alert alert-success square successMsgDiv hidden">
                                <small class="displaySucessMsg"></small>
                            </div>
                            <div class="alert alert-danger square errorMsgDiv hidden">
                                <small class="displayErrorMsg"></small>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <a href="<c:url value='/login' />" class="pull-left">
                                        <i class="fa fa-arrow-left"></i> Back to login</a>
                                </div>
                                <div class="col-md-6">
                                    <button class="btn btn-primary btn-sm btn-block square float-end btnRequestPwReset">
                                        Submit
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
