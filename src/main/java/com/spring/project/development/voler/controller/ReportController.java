package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.dto.ReportResponseDto;
import com.spring.project.development.voler.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/generateReport", method = RequestMethod.GET)
    public ResponseMessage generateReport(HttpServletRequest request, String year, String companyId, String documentType) throws IOException, JRException {
        ResponseMessage responseMessage = new ResponseMessage();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Integer reportType = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("year", year);
        params.put("companyId", companyId);
//        params.put("fromDate", new Date());
//        params.put("reportingPeriod", "January - December " + year);

        Resource resource = new ClassPathResource("/reportPath.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        String reportSourcePath = props.getProperty("reportPath.reportSourcePath");

        String reportOutputPath = getReportOutputPath(request);
        List<ReportResponseDto> reportResponseDtos = new ArrayList<>();
        try {
            ReportResponseDto reportResponseDto = reportService.generateReport(documentType, params, reportSourcePath, reportOutputPath, currentUser.getUsername(), reportType);
            reportResponseDtos.add(reportResponseDto);
        } catch (Exception ex) {
            responseMessage.setText("Failed to generate report due to: " + ex);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setDTO(reportResponseDtos);
        return responseMessage;
    }

    protected String getReportOutputPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/reports");
    }
}

