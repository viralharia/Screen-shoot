package com.vharia.screenshoot.exceptions;

public class ExceptionJSONInfo {
    private String url;
    private String error;
    private int statusCode;
    private String message;

    public ExceptionJSONInfo() {
    }

    public ExceptionJSONInfo(String url, String error, int statusCode, String message) {
        this.url = url;
        this.error = error;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
