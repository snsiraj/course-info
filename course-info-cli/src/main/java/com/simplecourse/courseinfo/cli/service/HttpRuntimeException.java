package com.simplecourse.courseinfo.cli.service;

public class HttpRuntimeException extends RuntimeException {
    public HttpRuntimeException(String s,Exception e) {
        super(s,e);
    }
    public HttpRuntimeException(String s) {
        super(s);
    }
}
