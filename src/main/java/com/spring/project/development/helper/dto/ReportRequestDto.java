package com.spring.project.development.helper.dto;

import java.sql.Connection;
import java.util.Map;

public class ReportRequestDto {
     private String userName;

	 /**
	 * Report Name (without any spaces)
	 */
	 private String reportName;

	 /**
	 * Report Saved Directory after generation
	 */
	 private String reportOutputDir;

	 /**
	 * Report output Format: pdf/xml/cvs
	 */
	 private String reportFormat;

	 /**
	 * Main Report: path
	 */
	 private String jasperReportPath;

	 /**
	 * Sub-report: header path
	 */
	 private String jasperReportHeaderPath;

	 /**
	 * Sub-report: footer path
	 */
	 private String jasperReportFooterPath;

	 /**
	 * directory where sub-reports are physically stored. This will be passed to .jrxml file
	 */
	 private String jasperSubreportDir;

	 private Connection connection;
	 private Map<String, Object> reportParams;

	 public ReportRequestDto(){

	 }

public ReportRequestDto(
	     String reportOutputDir,
		 String reportName,
		 String reportFormat,
		 String jasperReportHeaderPath,
		 String jasperReportPath,
		 String jasperReportFooterPath,
		 String jasperSubreportDir,
		 Map<String, Object> reportParams,
		 Connection connection
		 ){
		 this.reportOutputDir=reportOutputDir;
		 this.reportName= reportName;
		 this.reportFormat= reportFormat;
		 this.jasperReportHeaderPath=jasperReportHeaderPath;
		 this.jasperReportPath=jasperReportPath;
		 this.jasperReportFooterPath=jasperReportFooterPath;
		 this.jasperSubreportDir=jasperSubreportDir;
		 this.connection=connection;
		 this.reportParams=reportParams;
		 }

public ReportRequestDto(
	     String reportOutputDir,
		 String reportName,
		 String reportFormat,
		 String jasperReportHeaderPath,
		 String jasperReportPath,
		 String jasperReportFooterPath,
		 String jasperSubreportDir,
		 Map<String, Object> reportParams,
		 Connection connection,
		 String userID
		 ){
		 this.reportOutputDir=reportOutputDir;
		 this.reportName= reportName;
		 this.reportFormat= reportFormat;
		 this.jasperReportHeaderPath=jasperReportHeaderPath;
		 this.jasperReportPath=jasperReportPath;
		 this.jasperReportFooterPath=jasperReportFooterPath;
		 this.jasperSubreportDir=jasperSubreportDir;
		 this.connection=connection;
		 this.reportParams=reportParams;
		 this.userName=userID;
		 }

public String getJasperReportHeaderPath(){
     return jasperReportHeaderPath;
}

public void setJasperReportHeaderPath(String jasperReportHeaderPath){
     this.jasperReportHeaderPath=jasperReportHeaderPath;
}

public String getJasperReportFooterPath(){
     return jasperReportFooterPath;
}

public void setJasperReportFooterPath(String jasperReportFooterPath){
     this.jasperReportFooterPath=jasperReportFooterPath;
}

public String getReportOutputDir(){
     return reportOutputDir;
}

public void setReportOutputDir(String reportOutputDir){
     this.reportOutputDir=reportOutputDir;
}

public String getReportFormat(){
     return reportFormat;
}

public void setReportFormat(String reportFormat){
     this.reportFormat=reportFormat;
}

public String getJasperReportPath(){
     return jasperReportPath.replace("\\", "/");
}

public void setJasperReportPath(String jasperReportPath){
     this.jasperReportPath=jasperReportPath;
}

public Connection getConnection(){
     return connection;
}

public void setJasperReportPath(Connection connection){
     this.connection=connection;
}

public Map<String, Object> getReportParams(){
     return reportParams;
}

public void setReportParams(Map<String, Object> reportParams){
     this.reportParams=reportParams;
}

public String getJasperSubreportDir(){
     return jasperSubreportDir;
}

public void setJasperSubreportDir(String jasperSubreportDir){
     this.jasperSubreportDir=jasperSubreportDir;
}

public String getReportName(){
     //replace any space between file names
     return reportName.replaceAll("\\s", "");
}

public void setReportName(String reportName){
     this.reportName=reportName;
}

public String getUserName(){
     return userName;
}

public void setUserName(String userName){
     this.userName=userName;
}

@Override
public String toString() {
     return "ReportRequestDto{" +
	 "reportName='" + reportName + '\'' +
	 ", reportOutputDir='" + reportOutputDir + '\'' +
	 ", reportFormat='" + reportFormat + '\'' +
	 ", jasperReportPath='" + jasperReportPath + '\'' +
	 ", jasperReportHeaderPath='" + jasperReportHeaderPath + '\'' +
	 ", jasperReportFooterPath='" + jasperReportFooterPath + '\'' +
	 ", jasperSubreportDir='" + jasperSubreportDir + '\'' +
	 ", connection='" + connection + '\'' +
	 ", reportParams='" + reportParams + '\'' +
	 ", userName='" + userName +
	 '}';
}

}