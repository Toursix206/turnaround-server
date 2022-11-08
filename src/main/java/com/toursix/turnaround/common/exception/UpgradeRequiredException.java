package com.toursix.turnaround.common.exception;

public class UpgradeRequiredException extends TurnaroundException {

    public UpgradeRequiredException(String message) {
        super(message, ErrorCode.UPGRADE_REQUIRED_EXCEPTION);
    }
}
