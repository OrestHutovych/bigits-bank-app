package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.common.ApplicationConstants;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.entity.Transaction;
import org.example.entity.enums.ActivityType;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.TransactionRepository;
import org.example.service.ConvertCurrencyService;
import org.example.service.TransactionalService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionalServiceImpl implements TransactionalService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ConvertCurrencyService convertCurrencyService;

    @Transactional
    @Override
    @Nullable
    public Account deposit(@NotNull final Transaction transaction, @NotNull final String number){
        Account byNumber = accountRepository.findByNumber(number);
        try {
            if(byNumber != null){
                transaction.setAccount(byNumber);
                transaction.setCurrency(byNumber.getCurrency());
                transaction.setTransactionalDate(new Date());
                transaction.setActivityType(ActivityType.DEPOSIT);
                byNumber.setBalance(byNumber.getBalance() + transaction.getAmount());
                transactionRepository.save(transaction);
                accountRepository.save(byNumber);
                return byNumber;
            }else{
                throw new AccountException("Your Account Number not valid..");
            }
        }catch (AccountException e){
            throw new AccountException(e.getMessage());
        }
    }
    @Transactional
    @Override
    @Nullable
    public Account withdraw(@NotNull final Transaction transaction, @NotNull final String number){
        Account byNumber = accountRepository.findByNumber(number);
        try {
            if (byNumber != null) {
                if(byNumber.getBalance() > 0){
                    transaction.setAccount(byNumber);
                    transaction.setCurrency(byNumber.getCurrency());
                    transaction.setTransactionalDate(new Date());
                    transaction.setActivityType(ActivityType.WITHDRAW);
                    transactionRepository.save(transaction);
                    byNumber.setBalance(byNumber.getBalance() - transaction.getAmount());
                    accountRepository.save(byNumber);
                    return byNumber;
                }
            } else {
                throw new AccountException("Your Account Number not valid..");
            }
        }catch (AccountException e){
            throw new AccountException(e.getMessage());
        }
        throw new AccountException("Your Account Balance is not enough..");
    }

    @Transactional
    @SneakyThrows
    public Account transfer(String credit_number_first, String credit_number_second, Transaction transaction){
        var first_account = accountRepository.findByNumber(credit_number_first);
        var second_account = accountRepository.findByNumber(credit_number_second);

        try{
            if(first_account != null && second_account != null){
                withdraw(transaction, credit_number_first);
                deposit(transaction, credit_number_second);
                return first_account;
            }
        }catch (AccountException e){
            throw new AccountException(e.getMessage());
        }
        throw new AccountException("Your Account Number not valid..");
    }

    public Transaction getOne(Long account_id, Long transaction_id){
        Optional<Account> byId1 = accountRepository.findById(account_id);
        if(byId1.isPresent()) {
            Optional<Transaction> byId = transactionRepository.findById(transaction_id);
            if (byId.isPresent()) {
                return byId.get();
            }
        }
        return null;
    }
    public List<Transaction> getAllTransaction(Long account_id){
        Optional<Account> account = accountRepository.findById(account_id);
        if(account.isPresent()){
            return transactionRepository.findByAccount(account.get());
        }
        throw new AccountException("Transactions not found");
    }
}