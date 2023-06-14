package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.service.impl.TransactionalServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionalServiceImpl transactionalServiceImpl;

    @GetMapping("/{account_id}/{transaction_id}")
    public ResponseEntity<?> getOne(@PathVariable Long account_id, @PathVariable Long transaction_id){
        Transaction one = transactionalServiceImpl.getOne(account_id, transaction_id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }
    @GetMapping("/{account_id}")
    public ResponseEntity<?> getAllTransactions(@PathVariable Long account_id){
        List<Transaction> allTransaction = transactionalServiceImpl.getAllTransaction(account_id);
        return new ResponseEntity<>(allTransaction, HttpStatus.OK);
    }

    @PostMapping("/deposit/{credit_number}")
    public ResponseEntity<?> deposit(@Valid @NotNull @RequestBody final Transaction transaction,
                                     @Valid @NotNull @PathVariable final String credit_number){
        Account depositAmount = transactionalServiceImpl.deposit(transaction,credit_number);
        return new ResponseEntity<>(depositAmount, HttpStatus.OK);
    }
    @PostMapping("/withdraw/{credit_number}")
    public ResponseEntity<?> withdraw(@Valid @NotNull @RequestBody final Transaction transaction,
                                      @Valid @NotNull @PathVariable final String credit_number){
        Account withdrawAmount = transactionalServiceImpl.withdraw(transaction, credit_number);
        return new ResponseEntity<>(withdrawAmount, HttpStatus.OK);
    }
    @PostMapping("/transfer/{first_credit_card}/{second_credit_card}")
    public ResponseEntity<?> transfer(@PathVariable String first_credit_card, @PathVariable String second_credit_card, @RequestBody Transaction transaction1){
        Account transferAccount = transactionalServiceImpl.transfer(first_credit_card, second_credit_card, transaction1);
        return new ResponseEntity<>(transferAccount, HttpStatus.OK);
    }
}
