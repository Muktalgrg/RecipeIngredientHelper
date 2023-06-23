package com.rih.exception;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException() {
    }

    public IngredientNotFoundException(String msg) {
        super(msg);
    }
}
