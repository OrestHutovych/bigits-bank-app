package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.service.TransactionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionalService transactionalService;

    @GetMapping("/{account_id}/{transaction_id}")
    public ResponseEntity<?> getOne(@PathVariable Long account_id, @PathVariable Long transaction_id){
        Transaction one = transactionalService.getOne(account_id, transaction_id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }
    @GetMapping("/{account_id}")
    public ResponseEntity<?> getAllTransactions(@PathVariable Long account_id){
        List<Transaction> allTransaction = transactionalService.getAllTransaction(account_id);
        return new ResponseEntity<>(allTransaction, HttpStatus.OK);
    }

    @PostMapping("/{customer_id}/{account_id}/{number}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long customer_id,
                                     @PathVariable Long account_id,
                                     @RequestBody Transaction transaction,
                                     @PathVariable String number){
        Account depositAmount = transactionalService.deposit(customer_id, account_id, transaction,number);
        return new ResponseEntity<>(depositAmount, HttpStatus.OK);
    }
    @PostMapping("/{customer_id}/{account_id}/{number}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long customer_id,
                                      @PathVariable Long account_id,
                                      @RequestBody Transaction transaction,
                                      @PathVariable String number){
        Account withdrawAmount = transactionalService.withdraw(customer_id,account_id, transaction, number);
        return new ResponseEntity<>(withdrawAmount, HttpStatus.OK);
    }
    @PostMapping("/{customer_id1}/{customer_id2}/{account_id1}/{account_id2}/{number1}/{number2}/transfer")
    public ResponseEntity<?> transfer(@PathVariable Long customer_id1, @PathVariable Long customer_id2,
                                      @PathVariable Long account_id1, @PathVariable Long account_id2,
                                      @RequestBody Transaction transaction1,
                                      @PathVariable String number1, @PathVariable String number2){
        Account transferAccount = transactionalService.transfer(customer_id1, customer_id2,account_id1,account_id2,transaction1,number1, number2);
        return new ResponseEntity<>(transferAccount, HttpStatus.OK);
    }
}
