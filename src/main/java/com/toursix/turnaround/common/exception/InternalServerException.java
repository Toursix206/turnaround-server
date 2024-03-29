package com.toursix.turnaround.common.exception;

public class InternalServerException extends TurnaroundException {

    public InternalServerException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
