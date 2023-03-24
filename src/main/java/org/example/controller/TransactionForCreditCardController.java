package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.CreditCard;
import org.example.entity.Transaction;
import org.example.entity.TransactionForCreditCard;
import org.example.service.TransactionForCreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction_for_credit_card")
@RequiredArgsConstructor
public class TransactionForCreditCardController {

    private final TransactionForCreditCardService transactionCredit;

    @PostMapping("/{customer_id}/{credit_card_id}/{creditNumber}/deposit_to_credit")
    private ResponseEntity<?> deposit(@PathVariable Long customer_id,
                                      @PathVariable Long credit_card_id,
                                      @RequestBody TransactionForCreditCard transaction,
                                      @PathVariable String creditNumber){
        CreditCard deposit = transactionCredit.deposit(customer_id, credit_card_id, transaction,creditNumber);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }
    @PostMapping("/{customer_id}/{credit_card_id}/{creditNumber}/withdraw_from_credit")
    private ResponseEntity<?> withdraw(@PathVariable Long customer_id,
                                       @PathVariable Long credit_card_id,
                                       @RequestBody TransactionForCreditCard transaction,
                                       @PathVariable String creditNumber){
        CreditCard withdraw = transactionCredit.withdraw(customer_id, credit_card_id, transaction, creditNumber);
        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }
    @PostMapping("/{customer_id1}/{customer_id2}/{account_id1}/{account_id2}/{number1}/{number2}/transfer")
    public ResponseEntity<?> transfer(@PathVariable Long customer_id1, @PathVariable Long customer_id2,
                                      @PathVariable Long account_id1, @PathVariable Long account_id2,
                                      @RequestBody TransactionForCreditCard transaction1,
                                      @PathVariable String number1, @PathVariable String number2){
        CreditCard transferAccount = transactionCredit.transfer(customer_id1, customer_id2,account_id1,account_id2,transaction1,number1, number2);
        return new ResponseEntity<>(transferAccount, HttpStatus.OK);
    }
}
