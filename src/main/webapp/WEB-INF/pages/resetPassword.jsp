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
    <title>Reset Password</title>
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
                            <h5>Reset Password</h5>
                        </div>
                        <form id="resetPasswordForm" class="resetPasswordForm">
                            <div class="form-group">
                                <input type="hidden" name="email" class="form-control field" id="email"
                                       value="${email}"/>
                                <input type="hidden" name="requestId" class="form-control field"
                                       id="requestId" value="${requestId}"/>
                            </div>

                            <div class="form-group">
                                <input type="password" name="newPassword" class="form-control square"
                                       id="newPassword" autocomplete="off" required placeholder="New Password"/>
                            </div>

                            <div class="form-group">
                                <input type="password" name="password" class="form-control square"
                                       id="confirmPassword" autocomplete="off" required
                                       placeholder="Confirm Password"/>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="checkbox" class='form-check-input cursor-pointer' id="checkbox1"
                                           onclick="showPasswordFn()">
                                    <label for="checkbox1" class="cursor-pointer"> <small
                                            style="text-transform: capitalize;">Show Password</small></label>
                                </div>
                                <div class="col-md-6">
                                    <button type="button"
                                            class="btn btn-primary btn-sm btn-block square btnResetNow">
                                        Reset Now
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="loginLinkDiv hidden">
                            <div class="row">
                                <div class="alert alert-success"
                                     style="text-align: center;vertical-align: middle;position: relative;">
                                    <span class="displaySucessMsg"></span>
                                </div>
                            </div>
                            <div class="row" style="text-align: center;vertical-align: middle;position: relative;">
                                <a href="<c:url value='/login' />" class='pull-right'>
                                    Please click <span style="text-decoration: underline">here</span> to login with new
                                    password
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function showPasswordFn() {
        let x = document.getElementById("newPassword");
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
        let y = document.getElementById("confirmPassword");
        if (y.type === "password") {
            y.type = "text";
        } else {
            y.type = "password";
        }
    }
</script>
</body>
</html>
