package org.example.repository;

import org.example.entity.CreditCard;
import org.example.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
     List<CreditCard> findByCustomer(Customer customer);
     CreditCard findByCreditNumber(String creditNumber);
}
