package org.example.service;

import org.example.entity.Account;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccountByCustomer(@NotNull final Long customer_id);
    Account getAccount(@NotNull final String number);
    Account createAccount(@NotNull final Long id, @NotNull final Account account);
    List<Account> deleteAccount(@NotNull final String number);
}
