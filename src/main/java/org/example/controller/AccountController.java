package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.service.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;
    @GetMapping("/find_all/{customer_id}")
    public ResponseEntity<?> getAllAccountByCustomer(@Valid @NotNull @PathVariable final Long customer_id){
        return new ResponseEntity<>(accountServiceImpl.getAllAccountByCustomer(customer_id), HttpStatus.CREATED);
    }
    @GetMapping("/find_by_number/{customer_number}")
    public ResponseEntity<?> getAccount(@Valid @NotNull @PathVariable final String customer_number){
        return new ResponseEntity<>(accountServiceImpl.getAccount(customer_number), HttpStatus.CREATED);
    }
    @PostMapping("/create_account/{customer_id}")
    public ResponseEntity<?> createAccount(@Valid @NotNull @PathVariable final Long customer_id, @Valid @RequestBody final Account account){
        var orUpdate = accountServiceImpl.createAccount(customer_id, account);
        return new ResponseEntity<>(orUpdate, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete_account/{customer_number}")
    public ResponseEntity<?> deleteAccount(@Valid @NotNull @PathVariable final String customer_number){
        var account = accountServiceImpl.deleteAccount(customer_number);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
