package com.spring.project.development.helper;

public enum ReportType {
    CALL_LOG_REPORT(0, "Call Log"),
    STRANDED_REPORT(1, "Stranded"),
    CALL_LOG_REPORT_SUMMARY(2, "Call Log Summary");

    private final Integer value;
    private final String text;

    ReportType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
