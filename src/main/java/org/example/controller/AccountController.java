package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/{customer_id}")
    public ResponseEntity<?> create(@PathVariable Long customer_id, @RequestBody Account account, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> map = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Account accountOrUpdate = accountService.createAccountOrUpdate(customer_id, account);
        return new ResponseEntity<>(accountOrUpdate, HttpStatus.ACCEPTED);
    }
}
