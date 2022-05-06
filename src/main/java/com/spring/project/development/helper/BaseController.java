package com.spring.project.development.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Bikash Rai on 5/21/2020.
 */
public class BaseController {

    ResponseMessage responseMessage;
    protected CurrentUser currentUser;

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }
    protected CurrentUser getCurrentUser(HttpServletRequest request) {
        //if (currentUser == null) {
        currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        //}
        return currentUser;
    }

    protected String getReportSourcePath(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/jasperreport/");
        return path.replace("\\", "//");
    }

    protected String getReportOutputPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/resources/reports");
    }
}
