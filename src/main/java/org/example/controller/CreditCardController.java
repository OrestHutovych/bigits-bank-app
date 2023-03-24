package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.CreditCard;
import org.example.entity.Transaction;
import org.example.entity.TransactionForCreditCard;
import org.example.service.CreditCardService;
import org.example.service.TransactionForCreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/credit_card")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService creditCardService;

    @GetMapping("/{customer_id}/find_all")
    public ResponseEntity<?> findAll(@PathVariable Long customer_id){
        List<CreditCard> all = creditCardService.getAll(customer_id);
        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }
    @GetMapping("/{customer_id}/{credit_id}/find_one")
    public ResponseEntity<?> findOne(@PathVariable Long customer_id, @PathVariable Long credit_id){
        CreditCard one = creditCardService.getOne(customer_id, credit_id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }
    @PostMapping("/{customer_id}/create")
    public ResponseEntity<?> create(@PathVariable Long customer_id, @Valid @RequestBody CreditCard creditCard){
        CreditCard createCreditCard = creditCardService.createCreditCard(customer_id, creditCard);
        return new ResponseEntity<>(createCreditCard, HttpStatus.CREATED);
    }
    @GetMapping("/{customer_id}/{credit_id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long customer_id, @PathVariable Long credit_id){
        boolean delete = creditCardService.delete(customer_id, credit_id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}
