package org.example.repository;

import org.example.entity.TransactionForCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionForCreditCardRepository extends JpaRepository<TransactionForCreditCard, Long> {
}
