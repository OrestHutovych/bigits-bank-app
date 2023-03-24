package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.example.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final static double BASIC_BALANCE = 0;
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
            if(account.getCurrency() == Currency.UAH || account.getCurrency() == Currency.EUR || account.getCurrency() == Currency.USD) {
                account.setBalance(BASIC_BALANCE);
            }
            account.setNumber(StringUtil.getRandomNumberAsString(16));
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
}
