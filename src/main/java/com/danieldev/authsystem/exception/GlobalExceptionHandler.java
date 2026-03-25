package com.danieldev.authsystem.exception;

import com.danieldev.authsystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {
        return buildResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {

        ex.printStackTrace();

        String message = (ex.getMessage() != null)
                ? ex.getMessage()
                : "Erro interno do servidor";

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    private ResponseEntity<ApiResponse> buildResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse(false, message, null));
    }
}