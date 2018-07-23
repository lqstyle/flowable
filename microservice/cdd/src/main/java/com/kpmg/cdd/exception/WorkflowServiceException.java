package com.kpmg.cdd.exception;

public class WorkflowServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WorkflowServiceException(String s) {
        super(s);
    }
}