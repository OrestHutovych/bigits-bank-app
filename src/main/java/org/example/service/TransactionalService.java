package org.example.service;

import org.example.entity.Account;
import org.example.entity.Transaction;

import javax.validation.constraints.NotNull;

public interface TransactionalService {

    Account deposit(@NotNull final Transaction transaction, @NotNull final String number);
    Account withdraw(@NotNull final Transaction transaction, @NotNull final String number);
}
