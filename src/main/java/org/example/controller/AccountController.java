package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
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
    @PostMapping("/{customer_id}/{number}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long customer_id, @PathVariable String number, @RequestParam Double amount){
        Account depositAmount = transactionalService.deposit(customer_id, number, amount);
        return new ResponseEntity<>(depositAmount, HttpStatus.OK);
    }
    @PostMapping("/{customer_id}/{number}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long customer_id, @PathVariable String number, @RequestParam Double amount){
        Account depositAmount = transactionalService.withdraw(customer_id, number, amount);
        return new ResponseEntity<>(depositAmount, HttpStatus.OK);
    }
    @PostMapping("/{customer_id1}/{customer_id2}/{number1}/{number2}/transfer")
    public ResponseEntity<?> transfer(@PathVariable Long customer_id1, @PathVariable Long customer_id2,
                                      @PathVariable String number1, @PathVariable String number2,
                                      @RequestParam Double amount){
        Account transferAccount = transactionalService.transfer(customer_id1, customer_id2, number1, number2, amount);
        return new ResponseEntity<>(transferAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{customer_id}/{account_id}")
    public ResponseEntity<?> delete(@PathVariable Long customer_id, @PathVariable Long account_id){
        boolean delete = accountService.delete(customer_id, account_id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
