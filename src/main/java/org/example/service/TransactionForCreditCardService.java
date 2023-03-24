package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.ActivityType;
import org.example.entity.enums.Currency;
import org.example.exception.AccountException;
import org.example.repository.CreditCardRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.TransactionForCreditCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionForCreditCardService {
    private final CustomerRepository customerRepository;
    private final CreditCardRepository creditCardRepository;
    private final TransactionForCreditCardRepository tfccr;
    private final ConvertCurrencyService convertCurrencyService;

    private static final double LIMIT_FOR_UAH = 50000.00;
    private static final double LIMIT_FOR_USD = 1350.00;
    private static final double LIMIT_FOR_EUR = 1215.00;
    @Transactional
    public CreditCard deposit(Long customer_id, Long credit_card_id , TransactionForCreditCard transaction, String number) {
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if (byId.isPresent()) {
            CreditCard byNumber = creditCardRepository.findByCreditNumber(number);
            if (byNumber != null) {
                Optional<CreditCard> byId1 = creditCardRepository.findById(credit_card_id);

                if (byId1.isPresent()) {
                    transaction.setCreditCard(byId1.get());
                    transaction.setTransactionalDate(new Date());
                    transaction.setActivityType(ActivityType.DEPOSIT);
                    tfccr.save(transaction);
                } else {
                    throw new AccountException("Account not found");
                }
                    byNumber.setTotalLimit(byNumber.getTotalLimit() + transaction.getAmount());

                    if (byNumber.getCurrency() == Currency.UAH && byNumber.getTotalLimit() >= LIMIT_FOR_UAH) {
                        byNumber.setExpireDate(null);
                    } else if (byNumber.getCurrency() == Currency.USD && byNumber.getTotalLimit() >= LIMIT_FOR_USD) {
                        byNumber.setExpireDate(null);
                    } else if (byNumber.getCurrency() == Currency.EUR && byNumber.getTotalLimit() >= LIMIT_FOR_EUR) {
                        byNumber.setExpireDate(null);
                    }
                    creditCardRepository.save(byNumber);
                    return byNumber;
                } else {
                    throw new AccountException("Your Account Number not valid..");
                }
        }
        throw new AccountException("Customer not found...");
    }
    @Transactional
    public CreditCard withdraw(Long customer_id, Long credit_card_id, TransactionForCreditCard transaction, String number){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            CreditCard byNumber = creditCardRepository.findByCreditNumber(number);
            if(byNumber != null){
                Optional<CreditCard> byId1 = creditCardRepository.findById(credit_card_id);

                if(byId1.isPresent()) {
                    transaction.setCreditCard(byId1.get());
                    transaction.setTransactionalDate(new Date());
                    transaction.setActivityType(ActivityType.WITHDRAW);
                    tfccr.save(transaction);
                }else {
                    throw new AccountException("Account not found");
                }
                byNumber.setTotalLimit(byNumber.getTotalLimit() - transaction.getAmount());
                if(byNumber.getCurrency() == Currency.UAH && byNumber.getTotalLimit() < LIMIT_FOR_UAH){
                    byNumber.setExpireDate(LocalDateTime.now().plusMinutes(1));
                } else if (byNumber.getCurrency() == Currency.USD && byNumber.getTotalLimit() < LIMIT_FOR_USD) {
                    byNumber.setExpireDate(LocalDateTime.now().plusMinutes(1));
                } else if (byNumber.getCurrency() == Currency.EUR && byNumber.getTotalLimit() < LIMIT_FOR_EUR) {
                    byNumber.setExpireDate(LocalDateTime.now().plusMinutes(1));
                }
                creditCardRepository.save(byNumber);
                return byNumber;
            }else{
                throw new AccountException("Your Account Number not valid..");
            }
        }
        throw new AccountException("Customer not found...");
    }

    public CreditCard transfer(Long customer_id1,
                               Long customer_id2,
                               Long account_id1,
                               Long account_id2,
                               TransactionForCreditCard transaction,
                               String number1,
                               String number2){
        Optional<Customer> byId1 = customerRepository.findById(customer_id1);
        if(byId1.isPresent()) {
            Optional<Customer> byId2 = customerRepository.findById(customer_id2);
            if (byId2.isPresent()) {
                CreditCard byNumber1 = creditCardRepository.findByCreditNumber(number1);
                if (byNumber1 != null) {
                    CreditCard byNumber2 = creditCardRepository.findByCreditNumber(number2);
                    if (byNumber2 != null) {
                        Optional<CreditCard> tran1 = creditCardRepository.findById(account_id1);
                        Optional<CreditCard> tran2 = creditCardRepository.findById(account_id2);

                        if(tran1.isPresent()) {
                            if(tran2.isPresent()) {
                                transaction.setCreditCard(tran1.get());
                                transaction.setCreditCard(tran2.get());
                                transaction.setTransactionalDate(new Date());
                                transaction.setActivityType(ActivityType.TRANSFER);
                                tfccr.save(transaction);
                                tfccr.save(transaction);
                            }
                        }else {
                            throw new AccountException("Account not found");
                        }
                        byNumber1.setTotalLimit(byNumber1.getTotalLimit() - transaction.getAmount());
                        byNumber2.setTotalLimit(byNumber2.getTotalLimit() +  convertCurrencyService.convertCurrency(transaction.getAmount(), byNumber1.getCurrency(), byNumber2.getCurrency()));
                        if(byNumber1.getCurrency() == Currency.UAH && byNumber1.getTotalLimit() < LIMIT_FOR_UAH){
                            byNumber1.setExpireDate(LocalDateTime.now().plusMinutes(1));
                        } else if (byNumber1.getCurrency() == Currency.USD && byNumber1.getTotalLimit() < LIMIT_FOR_USD) {
                            byNumber1.setExpireDate(LocalDateTime.now().plusMinutes(1));
                        } else if (byNumber1.getCurrency() == Currency.EUR && byNumber1.getTotalLimit() < LIMIT_FOR_EUR) {
                            byNumber1.setExpireDate(LocalDateTime.now().plusMinutes(1));
                        }
                        creditCardRepository.save(byNumber1);
                        creditCardRepository.save(byNumber2);
                        return byNumber1;
                    }
                }
            }
        }
        throw new AccountException("Not found");
    }
}
