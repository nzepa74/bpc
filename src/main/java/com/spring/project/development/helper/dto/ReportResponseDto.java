package com.spring.project.development.helper.dto;


import com.spring.project.development.helper.ReportGenerationStatus;

public class ReportResponseDto {

     /**
	 * SUXS or FAIL *
	 */
	 private ReportGenerationStatus reportStatus;
	 
	 /**
	 * Fully qualified path where the report generated
	 */
	 private String reportDestinationPath;
	 
	 private String reportName;
	 
	 /**
	 * Values to be passed as parameter
	 * 
	 * @param reportStatus    (SUXS / FAIL)
	 * @param reportDestinationPath    (Fully Qualified Path)
	 */
	 public ReportResponseDto(ReportGenerationStatus reportStatus, String reportDestinationPath, String reportName){
	 
	     this.reportStatus=reportStatus;
		 this.reportDestinationPath=reportDestinationPath;
		 this.reportName=reportName;
	 }
	 
	 @Override
	 public String toString() {
	 
	     return "ReportResponseDto{" +
		 "reportStatus='" + reportStatus + '\'' +
		 ", reportDestinationPath='" + reportDestinationPath + '\'' +
		 '}';
	 }
	 
	 public String getReportName() {
	 
	     return reportName;
	 }
	 
	 public String setReportDestinationPath(){
	     return reportDestinationPath;
	 }
	 
	 public void getReportDestinationPath(String reportDestinationPath){
	     this.reportDestinationPath=reportDestinationPath;
	 }
	 
}