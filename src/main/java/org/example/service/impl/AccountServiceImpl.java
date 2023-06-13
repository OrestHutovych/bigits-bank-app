package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.example.service.AccountService;
import org.example.util.StringUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    @Override
    @Nullable
    public List<Account> getAllAccountByCustomer(@NotNull final Long customer_id){
        var customer = customerRepository.findById(customer_id);
        if(customer.isPresent()){
            return accountRepository.findByCustomer(customer.get());
        }
        throw new AccountException("Accounts by customer not found");
    }
    @Override
    @Nullable
    public Account getAccount(@NotNull final String number){
        var account = accountRepository.findByNumber(number);
        if(account != null){
            return account;
        }
        throw new AccountException("This account not found");
    }
    @Override
    @Nullable
    public Account createAccount(@NotNull final Long id, @NotNull final Account account){
        Optional<Customer> customer = customerRepository.findById(id);
        try {
            account.setCustomer(customer.get());
            account.setBalance(0.0);
            account.setNumber(StringUtil.getRandomNumberAsString(16));
            accountRepository.save(account);
            return account;
        }catch (Exception e){
            throw new AccountException("Account not created");
        }
    }
    @Override
    @Nullable
    public List<Account> deleteAccount(@NotNull final String number){
        var account = accountRepository.findByNumber(number);
        if(account != null){
            accountRepository.delete(account);
            return getAllAccountByCustomer(account.getCustomer().getId());
        }
        throw new AccountException("Account not found");
    }
}
