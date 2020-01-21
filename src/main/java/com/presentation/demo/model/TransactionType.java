package com.presentation.demo.model;

public enum TransactionType {
    TRANSFER("TRANSFER", 1),
    PUT_CASH("PUT_CASH", 2),
    GET_CASH("GET_CASH", 3);

    private String type;
    private String id;

    TransactionType() {
    }

    TransactionType(String type, int id) {
    }
}
