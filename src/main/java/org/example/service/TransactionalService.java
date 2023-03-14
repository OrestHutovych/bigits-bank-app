package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionalService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ConvertCurrencyService convertCurrencyService;

    private static final Integer MAX_VALUE_FOR_TRANSACTION = 40000;
    @Transactional
    public Account deposit(Long customer_id, String number, Double amount){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null){
                byNumber.setBalance(byNumber.getBalance() + amount);
                accountRepository.save(byNumber);
                return byNumber;
            }else{
                throw new AccountException("Your Account Number not valid..");
            }
        }
        throw new AccountException("Customer not found...");
    }
    @Transactional
    public Account withdraw(Long customer_id, String number, Double amount){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null && byNumber.getBalance() > amount){
                byNumber.setBalance(byNumber.getBalance() - amount);
                accountRepository.save(byNumber);
                return byNumber;
            }else if(byNumber != null && byNumber.getBalance() < amount){
                throw new AccountException("Your balance below than amount");
            }else if(byNumber == null){
                throw new AccountException("Your Account Number not valid..");
            }
        }
        throw new AccountException("Customer not found...");
    }
    @Transactional
    public Account transfer(Long customer_id1, Long customer_id2, String number1, String number2, Double amount){
        Optional<Customer> byId1 = customerRepository.findById(customer_id1);
        if(byId1.isPresent()){
            Optional<Customer> byId2 = customerRepository.findById(customer_id2);
            if(byId2.isPresent()) {
                Account byNumber1 = accountRepository.findByNumber(number1);
                if (byNumber1 != null && byNumber1.getBalance() > amount) {
                    Account byNumber2 = accountRepository.findByNumber(number2);
                    if(byNumber2 != null) {
                        if(amount <= MAX_VALUE_FOR_TRANSACTION) {
                            byNumber1.setBalance(byNumber1.getBalance() - amount);
                            byNumber2.setBalance(byNumber2.getBalance() + convertCurrencyService.convertCurrency(amount, byNumber1.getCurrency(), byNumber2.getCurrency()));
                            accountRepository.save(byNumber1);
                            accountRepository.save(byNumber2);
                            return byNumber1;
                        }else {
                            throw new AccountException("You cant transfer than 40.000$");
                        }
                    }
                }else if(byNumber1 != null && byNumber1.getBalance() < amount){
                    throw new AccountException("Your balance below than amount");
                }else if(byNumber1 == null){
                    throw new AccountException("Your Account Number not valid..");
                }
            }else {
                throw new AccountException("Customer for transfer not found...");
            }
        }
        throw new AccountException("Customer not found...");
    }
}
