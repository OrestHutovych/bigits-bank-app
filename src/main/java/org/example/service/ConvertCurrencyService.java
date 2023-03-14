package org.example.service;

import org.example.entity.enums.Currency;
import org.springframework.stereotype.Service;

@Service
public class ConvertCurrencyService implements CurrencyConvert{
    private static final double USD_TO_UAH_RATE = 38.2;
    private static final double UAH_TO_USD_RATE = 0.03;
    private static final double USD_TO_EUR_RATE = 0.93;
    private static final double EUR_TO_USD_RATE = 1.05;
    private static final double UAH_TO_EUR_RATE = 0.02;
    private static final double EUR_TO_UAH_RATE = 40.5;
    @Override
    public double convertCurrency(double amount, Currency fromCurrency, Currency toCurrency) {
        if(fromCurrency == Currency.USD && toCurrency == Currency.UAH){
            return amount * USD_TO_UAH_RATE;
        }else if(fromCurrency == Currency.UAH && toCurrency == Currency.USD){
            return amount * UAH_TO_USD_RATE;
        }else if(fromCurrency == Currency.USD && toCurrency == Currency.EUR) {
            return amount * USD_TO_EUR_RATE;
        }else if(fromCurrency == Currency.EUR && toCurrency == Currency.USD) {
            return amount * EUR_TO_USD_RATE;
        }else if(fromCurrency == Currency.UAH && toCurrency == Currency.EUR) {
            return amount * UAH_TO_EUR_RATE;
        }else if(fromCurrency == Currency.EUR && toCurrency == Currency.UAH) {
            return amount * EUR_TO_UAH_RATE;
        }else {
            throw new IllegalArgumentException("Unsupported currency conversion");
        }
    }
}
