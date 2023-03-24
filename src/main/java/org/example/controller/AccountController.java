package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.service.AccountService;
import org.example.service.TransactionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TransactionalService transactionalService;
    @GetMapping("/{customer_id}")
    public ResponseEntity<?> findAll(@PathVariable Long customer_id){
        return new ResponseEntity<>(accountService.getAllAccount(customer_id), HttpStatus.CREATED);
    }
    @GetMapping("/{customer_id}/{account_id}")
    public ResponseEntity<?> getOne(@PathVariable Long customer_id, @PathVariable Long account_id){
        return new ResponseEntity<>(accountService.getOne(customer_id, account_id), HttpStatus.CREATED);
    }

    @PostMapping("/{customer_id}")
    public ResponseEntity<?> create(@PathVariable Long customer_id, @Valid @RequestBody Account account, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> map = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Account orUpdate = accountService.createAccountOrUpdate(customer_id, account);
        return new ResponseEntity<>(orUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customer_id}/{account_id}")
    public ResponseEntity<?> delete(@PathVariable Long customer_id, @PathVariable Long account_id){
        boolean delete = accountService.delete(customer_id, account_id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
