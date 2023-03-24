package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.CreditCard;
import org.example.entity.Customer;
import org.example.entity.enums.Currency;
import org.example.entity.enums.StatusLimit;
import org.example.exception.AccountException;
import org.example.repository.CreditCardRepository;
import org.example.repository.CustomerRepository;
import org.example.util.StringUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository customerRepository;

    private static final double LIMIT_FOR_UAH = 50000.00;
    private static final double LIMIT_FOR_USD = 1350.00;
    private static final double LIMIT_FOR_EUR = 1215.00;

    public List<CreditCard> getAll(Long customer_id){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if(byId.isPresent()){
            return creditCardRepository.findByCustomer(byId.get());
        }
        throw new AccountException("Account not found");
    }
    public CreditCard getOne(Long customer_id, Long credit_id){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if(customer.isPresent()){
            Optional<CreditCard> credit = creditCardRepository.findById(credit_id);
            if(credit.isPresent()){
                return credit.get();
            }
        }
        return null;
    }

    public CreditCard createCreditCard(Long customer_id, CreditCard creditCard){
        Optional<Customer> findCustomer = customerRepository.findById(customer_id);
        if(findCustomer.isPresent()){
            creditCard.setCustomer(findCustomer.get());
            if(creditCard.getCurrency() == Currency.UAH){
                creditCard.setTotalLimit(LIMIT_FOR_UAH);
            }else if(creditCard.getCurrency() == Currency.USD){
                creditCard.setTotalLimit(LIMIT_FOR_USD);
            }else if(creditCard.getCurrency() == Currency.EUR){
                creditCard.setTotalLimit(LIMIT_FOR_EUR);
            }
            creditCard.setStatusLimit(StatusLimit.ACTIVE);
            creditCard.setCancelDate(LocalDateTime.now().plusYears(3));
            creditCard.setCreditNumber(StringUtil.getRandomNumberAsString(16));
            creditCard.setCvv(StringUtil.getRandomNumber(3));
            creditCardRepository.save(creditCard);
            return creditCard;
        }
        return null;
    }

    public boolean delete(Long customer_id, Long creditCard){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if(customer.isPresent()){
            Optional<CreditCard> credit = creditCardRepository.findById(customer_id);
            if(credit.isPresent()){
                creditCardRepository.delete(credit.get());
                return true;
            }
        }
        return false;
    }
}
