package com.flipkart.retail.analytics.exception;

import com.flipkart.retail.analytics.enums.ErrorCodes;
import lombok.Data;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Data
public class AuthServiceException extends Exception {

    private ErrorCodes errorCode = ErrorCodes.SYSTEM_ERROR;
    private String message;
    private String code;

    public AuthServiceException(String code, String message) {
        super(message);
        this.code=code;
        this.message=message;
    }

    public AuthServiceException(ErrorCodes errorCode){
        this.errorCode = errorCode;
    }

    public Status getHttpStatusCode() {
        return errorCode.getHttpStatusCode();
    }

    public Error toJson() {
        return new Error(errorCode.name(), errorCode.getMessage());
    }
}
