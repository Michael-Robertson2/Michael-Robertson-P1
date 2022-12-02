package com.revature.reimbursement.utils.custom_exceptions;

public class InvalidUpdateException extends RuntimeException{
    public InvalidUpdateException() {
    }

    public InvalidUpdateException(String message) {
        super(message);
    }

    public InvalidUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUpdateException(Throwable cause) {
        super(cause);
    }

    public InvalidUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
