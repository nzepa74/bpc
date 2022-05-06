package com.spring.project.development.helper.Enum;

public enum ApproveStatus {

    Submitted('S', "Submitted"),
    Approved('A', "Approved"),
    Reverted('R', "Reverted");

    private final Character value;
    private final String text;

    ApproveStatus(Character value, String text) {
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
