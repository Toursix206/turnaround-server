package com.toursix.turnaround.common.exception;

import lombok.Getter;

@Getter
public abstract class TurnaroundException extends RuntimeException {

    private final ErrorCode errorCode;

    public TurnaroundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return errorCode.getStatus();
    }
}
