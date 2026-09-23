package com.example.exception;

import com.example.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception exception) {
        String message = exception instanceof HttpMessageNotReadableException
                ? "Request body is invalid"
                : exception instanceof MethodArgumentNotValidException validationException
                ? validationException.getBindingResult().getFieldError().getDefaultMessage()
                : exception.getMessage();
        String type = message != null && message.contains("maximum allowed length")
                ? "https://api.add2num.example/problems/number-too-long"
                : "https://api.add2num.example/problems/invalid-number";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(type, "Invalid request", HttpStatus.BAD_REQUEST.value(), message));
    }
}