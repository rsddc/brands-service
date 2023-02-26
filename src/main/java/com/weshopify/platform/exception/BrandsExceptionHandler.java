package com.weshopify.platform.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BrandsExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException> handleApiError(ApiException apiException){
        return ResponseEntity.badRequest().body(apiException);
    }


}
