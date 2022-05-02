package com.rih.exception;

public class IngredientAlreadyExistException extends RuntimeException {
    public IngredientAlreadyExistException() {
    }

    public IngredientAlreadyExistException(String message) {
        super(message);
    }

    public IngredientAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IngredientAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public IngredientAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
