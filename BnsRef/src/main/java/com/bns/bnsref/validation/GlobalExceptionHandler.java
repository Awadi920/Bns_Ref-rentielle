package com.bns.bnsref.validation;


import com.bns.bnsref.dto.CodeListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static class ErrorResponse {
        private String message;
        private Map<String, String> errors;

        public ErrorResponse(String message, Map<String, String> errors) {
            this.message = message;
            this.errors = errors;
        }

        public String getMessage() {
            return message;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Global handling validation exception: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            log.warn("Validation error on field {}: {}", error.getField(), error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        if (ex.getBindingResult().getTarget() instanceof CodeListDTO && errors.containsKey("labelList")) {
            log.info("Returning 409 Conflict for duplicate labelList");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
        log.info("Returning 400 Bad Request for other validation errors");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
