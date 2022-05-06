package com.spring.project.development.helper.Enum;

public enum CommonStatus {

    Active('A', "Active"),
    Inactive('I', "Inactive");

    private final Character value;
    private final String text;

    CommonStatus(Character value, String text) {
        this.value = value;
        this.text = text;
    }

    public Character getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
