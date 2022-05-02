package com.rih.exception;

import com.rih.entity.Recipe;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RecipeNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request){
        return new ResponseEntity<>("Recipe not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RecipeAlreadyExistException.class})
    public ResponseEntity<Object> handleRecipeAlreadyExistException(Exception ex, WebRequest request){
        return new ResponseEntity<>("Recipe already exists.", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IngredientNotFoundException.class})
    public ResponseEntity<Object> handleIngredientNotFoundException(Exception ex, WebRequest request){
        return new ResponseEntity<>("Ingredient not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IngredientAlreadyExistException.class})
    public ResponseEntity<Object> handleIngredientAlreadyExistException(Exception ex, WebRequest request){
        return new ResponseEntity<>("Ingredient already exists.", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // ----
    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

}
