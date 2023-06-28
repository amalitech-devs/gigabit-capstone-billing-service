package com.gigacapstone.billingservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE_KEY = "message";
    private static final String STATUS_KEY = "status";

    @ExceptionHandler({EntityAlreadyExistException.class})
    protected ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistException ex){
        Map<String, Object> error = new HashMap<>();
        error.put(MESSAGE_KEY, ex.getMessage());
        error.put(STATUS_KEY, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(NotFoundException ex){
        Map<String, Object> error = new HashMap<>();
        error.put(MESSAGE_KEY, ex.getMessage());
        error.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    protected  ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request){
        Throwable specificCause = ex.getMostSpecificCause();
        Map<String, Object> error = new HashMap<>();
        if(specificCause instanceof InvalidFormatException invalidFormatException && (invalidFormatException.getTargetType().isEnum())){
                String invalidValue =invalidFormatException.getValue().toString();
                String validValues = Arrays.toString(invalidFormatException.getTargetType().getEnumConstants());
                String errorMessage = String.format("Invalid value %s for enum type %s. Valid values are %s",
                        invalidValue,invalidFormatException.getTargetType().getSimpleName(),validValues);
                error.put(MESSAGE_KEY, errorMessage);
                error.put(STATUS_KEY, HttpStatus.BAD_REQUEST);

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("invalid request body", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }

        errorResponse.put("messages", errorMessages);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OperationFailedException.class})
    protected ResponseEntity<Object> handleOperationFailedException(OperationFailedException ex){
        Map<String, Object> error = new HashMap<>();
        error.put(MESSAGE_KEY, ex.getMessage());
        error.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return this.handleMethodArgumentNotValidException(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return this.handleHttpMessageNotReadableException(ex, request);
    }
}
