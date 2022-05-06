package com.spring.project.development.voler.service;

import com.spring.project.development.helper.BaseService;
import com.spring.project.development.helper.ReportGeneration;
import com.spring.project.development.helper.dto.ReportRequestDto;
import com.spring.project.development.helper.dto.ReportResponseDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportService extends BaseService {
    public ReportResponseDto generateReport(String reportFormat, Map<String, Object> params, String reportPath, String outputPath, String userID, Integer reportType) throws JRException, ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        String headerName = "";
        String footerName = "";
        String reportName = "";
        String reportJRXML = "";
        reportPath = reportPath.replace("\\", "/");
        final String compactReport = "/jasperReports/compactMaster.jrxml";


        if (reportType == 0) {
            reportJRXML = compactReport;
            params.put("reportTitle", "Compact Document");
            reportName = "Compact Report";
        }


//        params.put("reportYear", "2021");
//        params.put("reportingPeriod", "January - December 2021");
//        headerName = ReportGeneration.portraitHeaderName;
//        footerName = ReportGeneration.portraitFooterName;

        ReportRequestDto reportRequestDto = new ReportRequestDto(outputPath, reportName, reportFormat, reportPath + headerName, reportPath + reportJRXML, reportPath + footerName, reportPath, params, connection, userID);

        return ReportGeneration.createReport(reportRequestDto);

    }

}
