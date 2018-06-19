package com.eco.portfoliomanagement.model.web;


public class CustomErrorType {

    private String errorCode;

    public CustomErrorType( String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
