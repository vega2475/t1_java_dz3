package edu.t1.chernykh.controller;

import edu.t1.chernykh.exception.AccountUnlockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerController {
    @ExceptionHandler(AccountUnlockException.class)
    public ResponseEntity<String> handleAccountUnlockException(AccountUnlockException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
