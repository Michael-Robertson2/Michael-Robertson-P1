package com.revature.reimbursement.utils.custom_exceptions;

public class InvalidTicketException extends RuntimeException {

    public InvalidTicketException() {
    }

    public InvalidTicketException(String message) {
        super(message);
    }

    public InvalidTicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTicketException(Throwable cause) {
        super(cause);
    }

    public InvalidTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
