package com.flipkart.retail.analytics.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {

    private final String code;
    private final String message;
}

