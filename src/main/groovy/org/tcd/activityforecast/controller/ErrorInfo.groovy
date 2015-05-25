package org.tcd.activityforecast.controller;

public class ErrorInfo {
    String url
    String message

    public ErrorInfo() {
    }
    
    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.message = ex.getLocalizedMessage();
    }
}
