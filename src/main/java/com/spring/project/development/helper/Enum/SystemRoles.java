package com.spring.project.development.helper.Enum;

public enum SystemRoles {

    Admin(1, "Admin"),
    Creator(2, "Creator"),
    Reviewer(3, "Reviewer"),
    BoardMember(4, "BoardMember"),
    CompanyCeo(5, "CompanyCeo");

    private final Integer value;
    private final String text;

    SystemRoles(Integer value, String text) {
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
