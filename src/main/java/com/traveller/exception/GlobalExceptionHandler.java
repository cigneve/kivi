package com.traveller.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<Map<String, String>> defaultExceptionHandler(Exception e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (e instanceof MethodValidationException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) e;
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

        } else {
            errors.put("error", e.getMessage());
        }
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof org.springframework.web.server.ResponseStatusException) {
            status = ((org.springframework.web.server.ResponseStatusException) e).getStatusCode();
        }
        return new ResponseEntity<>(errors, status);
    }
}
