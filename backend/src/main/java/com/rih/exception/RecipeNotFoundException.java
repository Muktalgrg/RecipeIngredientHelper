package com.rih.exception;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException(){

    }

    public RecipeNotFoundException(String msg){
        super(msg);
    }
}
