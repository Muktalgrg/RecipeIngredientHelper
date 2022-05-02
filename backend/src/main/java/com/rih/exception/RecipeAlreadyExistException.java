package com.rih.exception;

public class RecipeAlreadyExistException extends RuntimeException {
    public RecipeAlreadyExistException() {
    }

    public RecipeAlreadyExistException(String message) {
        super(message);
    }

    public RecipeAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public RecipeAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
