package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public List<Account> getAllAccount(Long customer_id){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if(customer.isPresent()){
            return accountRepository.findByCustomer(customer.get());
        }
        throw new AccountException("Account not found");
    }
    public Account getOne(Long customer_id, Long account_id){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Optional<Account> byId1 = accountRepository.findById(account_id);
            if(byId1.isPresent()) {
                return byId1.get();
            }
        }
        return null;
    }

    public Account createAccountOrUpdate(Long customer_id, Account account){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            account.setCustomer(byId.get());
            accountRepository.save(account);
            return account;
        }
        throw new AccountException("Account not found");
    }

    public boolean delete(Long customer_id, Long account_id){
        Optional<Customer> byId1 = customerRepository.findById(customer_id);
        if(byId1.isPresent()) {
            Optional<Account> byId = accountRepository.findById(account_id);
            if (byId.isPresent()) {
                accountRepository.delete(byId.get());
                return true;
            }
        }
        return false;
    }
    @Transactional
    public Account deposit(Long customer_id, String number, Double amount){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null){
                byNumber.setBalance(byNumber.getBalance() + amount);
                accountRepository.save(byNumber);
                return byNumber;
            }
        }
        throw new AccountException("Customer not found... / Your Account Number not valid..");
    }
    @Transactional
    public Account withdraw(Long customer_id, String number, Double amount){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null){
                byNumber.setBalance(byNumber.getBalance() - amount);
                accountRepository.save(byNumber);
                return byNumber;
            }
        }
        throw new AccountException("Customer not found... / Your Account Number not valid..");
    }
    public Account transfer(Long customer_id1, Long customer_id2, String number1, String number2, Double amount){
        Optional<Customer> byId1 = customerRepository.findById(customer_id1);
        if(byId1.isPresent()){
            Optional<Customer> byId2 = customerRepository.findById(customer_id2);
            if(byId2.isPresent()) {
                Account byNumber1 = accountRepository.findByNumber(number1);
                if (byNumber1 != null) {
                    Account byNumber2 = accountRepository.findByNumber(number2);
                    if(byNumber2 != null) {
                        byNumber1.setBalance(byNumber1.getBalance() - amount);
                        byNumber2.setBalance(byNumber2.getBalance() + amount);
                        accountRepository.save(byNumber1);
                        accountRepository.save(byNumber2);
                        return byNumber1;
                    }
                }
            }
        }
        throw new AccountException("Customer not found... / Your Account Number not valid..");
    }
}
