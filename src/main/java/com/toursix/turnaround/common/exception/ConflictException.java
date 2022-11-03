package com.toursix.turnaround.common.exception;

public class ConflictException extends BoilerplateException {

    public ConflictException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }
}
