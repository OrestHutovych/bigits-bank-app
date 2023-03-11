package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public Account createAccountOrUpdate(Long customer_id, Account account){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            account.setCustomer(byId.get());
            accountRepository.save(account);
            return account;
        }
        return null;
    }
}
