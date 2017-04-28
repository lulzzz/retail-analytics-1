package com.flipkart.retail.analytics.enums;

import javax.ws.rs.core.Response.Status;

public enum ErrorCodes {

    INVALID_INVOICE_ID("No data found for given invoice id", Status.BAD_REQUEST),
    INVALID_PAYMENT_ID("No data found for given payment id", Status.BAD_REQUEST),
    SYSTEM_ERROR("Something went wrong", Status.INTERNAL_SERVER_ERROR);

    private String message;
    private Status httpStatusCode;

    private ErrorCodes(String message){
        this.message = message;
    }

    private ErrorCodes(String message, Status httpStatusCode){
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage(){ return message; }

    public Status getHttpStatusCode() { return httpStatusCode; }
}
