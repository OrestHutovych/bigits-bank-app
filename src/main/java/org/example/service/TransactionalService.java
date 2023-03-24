package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Customer;
import org.example.entity.Transaction;
import org.example.entity.enums.ActivityType;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.AccountRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionalService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ConvertCurrencyService convertCurrencyService;

    private static final double MAX_VALUE_FOR_TRANSACTION = 1500; // $
    private static final double MAX_VALUE_FOR_TRANSACTION_BY_DAY = 10000; // $
    private static final double MAX_VALUE_FOR_TRANSACTION_BY_MONTH = 25000; // $

    private static final double COURSE_FOR_UAH = 38.2;
    private static final double COURSE_FOR_EUR = 1.05;
    @Transactional
    public Account deposit(Long customer_id, Long account_id, Transaction transaction, String number){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null){
                Optional<Account> byId1 = accountRepository.findById(account_id);

                if(byId1.isPresent()) {
                    transaction.setAccount(byId1.get());
                    transaction.setTransactionalDate(new Date());
                    transaction.setActivityType(ActivityType.DEPOSIT);
                    transactionRepository.save(transaction);
                }else {
                    throw new AccountException("Account not found");
                }

                byNumber.setBalance(byNumber.getBalance() + transaction.getAmount());

                accountRepository.save(byNumber);
                return byNumber;
            }else{
                throw new AccountException("Your Account Number not valid..");
            }
        }
        throw new AccountException("Customer not found...");
    }
    @Transactional
    public Account withdraw(Long customer_id, Long account_id, Transaction transaction, String number){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            Account byNumber = accountRepository.findByNumber(number);
            if(byNumber != null){
                Optional<Account> byId1 = accountRepository.findById(account_id);

                if(byId1.isPresent()) {
                    transaction.setAccount(byId1.get());
                    transaction.setTransactionalDate(new Date());
                    transaction.setActivityType(ActivityType.WITHDRAW);
                    transactionRepository.save(transaction);
                }else {
                    throw new AccountException("Account not found");
                }
                byNumber.setBalance(byNumber.getBalance() - transaction.getAmount());
                accountRepository.save(byNumber);
                return byNumber;
            }else{
                throw new AccountException("Your Account Number not valid..");
            }
        }
        throw new AccountException("Customer not found...");
    }
    @Transactional
    public Account transfer(Long customer_id1,
                            Long customer_id2,
                            Long account_id1,
                            Long account_id2,
                            Transaction transaction,
                            String number1,
                            String number2){
        Optional<Customer> byId1 = customerRepository.findById(customer_id1);
        if(byId1.isPresent()) {
            Optional<Customer> byId2 = customerRepository.findById(customer_id2);
            if (byId2.isPresent()) {
                Account byNumber1 = accountRepository.findByNumber(number1);
                if (byNumber1 != null) {
                    Account byNumber2 = accountRepository.findByNumber(number2);
                    if (byNumber2 != null) {
                        Optional<Account> tran1 = accountRepository.findById(account_id1);
                        Optional<Account> tran2 = accountRepository.findById(account_id2);

                        if(tran1.isPresent()) {
                            if(tran2.isPresent()) {
                                transaction.setAccount(tran1.get());
                                transaction.setAccount(tran2.get());
                                transaction.setTransactionalDate(new Date());
                                transaction.setActivityType(ActivityType.TRANSFER);
                                transactionRepository.save(transaction);
                                transactionRepository.save(transaction);
                            }
                        }else {
                            throw new AccountException("Account not found");
                        }
                        if(transaction.getAmount() < (MAX_VALUE_FOR_TRANSACTION * COURSE_FOR_UAH) && byNumber1.getCurrency() == Currency.UAH) {
                            byNumber1.setBalance(byNumber1.getBalance() - transaction.getAmount());
                            byNumber2.setBalance(byNumber2.getBalance() + convertCurrencyService.convertCurrency(transaction.getAmount(), byNumber1.getCurrency(), byNumber2.getCurrency()));
                        }else if(transaction.getAmount() < (MAX_VALUE_FOR_TRANSACTION * COURSE_FOR_EUR) && byNumber1.getCurrency() == Currency.EUR) {
                            byNumber1.setBalance(byNumber1.getBalance() - transaction.getAmount());
                            byNumber2.setBalance(byNumber2.getBalance() + convertCurrencyService.convertCurrency(transaction.getAmount(), byNumber1.getCurrency(), byNumber2.getCurrency()));
                        }else if(transaction.getAmount() < (MAX_VALUE_FOR_TRANSACTION) && byNumber1.getCurrency() == Currency.USD) {
                            byNumber1.setBalance(byNumber1.getBalance() - transaction.getAmount());
                            byNumber2.setBalance(byNumber2.getBalance() + convertCurrencyService.convertCurrency(transaction.getAmount(), byNumber1.getCurrency(), byNumber2.getCurrency()));
                        }else {
                            throw new AccountException("Your transaction amount grater then MAX LIMIT PER TRANSACTION");
                        }
                        accountRepository.save(byNumber1);
                        accountRepository.save(byNumber2);
                        return byNumber1;
                    }
                }
            }
        }
        throw new AccountException("Not found");
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