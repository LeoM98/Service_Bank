package com.bankservice.app.exceptions;

public enum ExceptionsCode {

    ARGUMENTO_INVALIDO("E-001", "Invalid argument"),
    ARGUMENTO_NOT_FOUND("E-002", "Not found argument"),
    BANK_CLOSED("E-003", "Bank closed");

    private final String code;
    private final String description;

    ExceptionsCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
