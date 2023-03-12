package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestController
@ControllerAdvice
public class CustomResponseAccountException extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<?> handleAccountException(AccountException ax, WebRequest webRequest){
        AccountExceptionResponse accountExceptionResponse = new AccountExceptionResponse(ax.getMessage());
        return new ResponseEntity<AccountExceptionResponse>(accountExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
