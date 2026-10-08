package com.Bank.BankBackend.shared.interfaces.advice;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
        return ResponseEntity
                .status(resolveStatus(ex.getCode()))
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos");
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    private HttpStatus resolveStatus(String code) {
        if (code.endsWith("_NOT_FOUND") || code.endsWith("_NOT_EXIST") || code.endsWith("_NOT_EXISTS")) {
            return HttpStatus.NOT_FOUND;
        }
        if (code.contains("ALREADY") || code.equals("CLIENT_ALREADY_EXISTS") || code.equals("PROFILE_ALREADY_EXISTS")) {
            return HttpStatus.CONFLICT;
        }
        if (code.equals("INCORRECT_CREDENTIALS")) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
