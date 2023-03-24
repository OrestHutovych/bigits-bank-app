package org.example.repository;

import org.example.entity.enums.Currency;

public interface CurrencyConvert {
    double convertCurrency(double amount, Currency fromCurrency, Currency toCurrency);
}
