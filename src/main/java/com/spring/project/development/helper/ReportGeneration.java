package com.spring.project.development.helper;

import com.spring.project.development.crypto.DBPasswordProtectorUtil;
import com.spring.project.development.helper.dto.ReportRequestDto;
import com.spring.project.development.helper.dto.ReportResponseDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;


public class ReportGeneration {
    public static final String portraitHeaderName = "/PotraitHeader.jrxml";
    public static final String portraitFooterName = "/PotraitFooter.jrxml";
    private static final String REPORT_OPTION_PDF = "pdf";
    private static final String REPORT_OPTION_XLS = "xls";
    private static final String REPORT_OPTION_HTML = "html";
    private static final String REPORT_OPTION_DOCX = "docx";
    private static final String REPORT_OPTION_PREVIEW = "Preview";

    //region static

    private static ReportGenerationStatus status = ReportGenerationStatus.GENERATION_FAILED;

    //endregion

    public static ReportResponseDto createReport(ReportRequestDto reportRequestDto) throws JRException,
            ClassNotFoundException, SQLException {
        String reportSourceFile = reportRequestDto.getJasperReportPath();
        Map<String, Object> params = reportRequestDto.getReportParams();

        String enc = "";
        //compile: main jasper reportN
        if (reportRequestDto.getUserName() != null) {
            enc = DBPasswordProtectorUtil.encrypt(reportRequestDto.getUserName()).replaceAll("[/.=]", "");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceFile);

        JasperPrint print = JasperFillManager.fillReport(
                jasperReport,
                params, reportRequestDto.getConnection());

        //Make sure the output directory exists.
        File outDir = new File(reportRequestDto.getReportOutputDir());
        outDir.mkdirs();

        String path = reportRequestDto.getReportOutputDir() + "/" + reportRequestDto.getReportName() + enc +
                reportRequestDto.getUserName() + "." + reportRequestDto.getReportFormat();

        if (REPORT_OPTION_PDF.equals(reportRequestDto.getReportFormat())) {
            status = exportAsPdf(print, path);
        } else if (REPORT_OPTION_XLS.equals(reportRequestDto.getReportFormat())) {
            status = exportAsExcel(print, path);
        } else if (REPORT_OPTION_HTML.equals(reportRequestDto.getReportFormat())) {
            status = exportAsHTML(print, path);
        } else if (REPORT_OPTION_PREVIEW.equals(reportRequestDto.getReportFormat())) {
            status = exportAsHTML(print, path);
        }  else if (REPORT_OPTION_DOCX.equals(reportRequestDto.getReportFormat())) {
            status = exportAsDocx(print, path);
        } else {
            return new ReportResponseDto(status, "", "");
        }
        return new ReportResponseDto(
                status,
                path,
                reportRequestDto.getReportName() + enc + reportRequestDto.getUserName() + "." + reportRequestDto.getReportFormat());
    }

    private static ReportGenerationStatus exportAsDocx(JasperPrint print, String path) throws JRException {

        JRDocxExporter export = new JRDocxExporter();
        export.setExporterInput(new SimpleExporterInput(print));
        export.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(path)));

        SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
        export.setConfiguration(config);
        export.exportReport();

        return ReportGenerationStatus.GENERATION_SUCCESS;
    }


    private static ReportGenerationStatus exportAsPdf(JasperPrint print, String path) throws JRException {

        JRPdfExporter exporter = new JRPdfExporter();

        ExporterInput exporterInput = new SimpleExporterInput(print);

        //Exporter Input
        exporter.setExporterInput(exporterInput);

        //Exporter Output
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(path);

        //Output
        exporter.setExporterOutput(exporterOutput);

        //
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return ReportGenerationStatus.GENERATION_SUCCESS;
    }


    private static ReportGenerationStatus exportAsExcel(JasperPrint print, String path) throws JRException {
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setWhitePageBackground(false);

        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setExporterInput(new SimpleExporterInput(print));
        exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
        exporterXLS.setConfiguration(configuration);
        exporterXLS.exportReport();
        return ReportGenerationStatus.GENERATION_SUCCESS;
    }

    public static ReportGenerationStatus exportAsHTML(JasperPrint print, String path) throws JRException {
        SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();

        configuration.setHtmlHeader("<html>\n" +
                "<head>\n" +
                " <title></title>\n" +
                "<link href=\"/afs.web/resources/images/favicon/favicon-32X32.ico\" rel=\"shortcut icon\"" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "  <style type=\"text/css\">\n" +
                "    a {text-decoration: none}\n" +
                "*{font-family: Arial Narrow !important};" +
                "  </style>\n" +
                "</head>\n" +
                "</head>\n" +
                "<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">\n" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">");
        HtmlExporter exporterHTML = new HtmlExporter();
        exporterHTML.setExporterInput(new SimpleExporterInput(print));
        exporterHTML.setExporterOutput(new SimpleHtmlExporterOutput(path));
        exporterHTML.setConfiguration(configuration);
        exporterHTML.exportReport();
        return ReportGenerationStatus.GENERATION_SUCCESS;
    }
}
