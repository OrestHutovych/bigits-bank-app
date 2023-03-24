package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.Currency;
import org.example.entity.enums.StatusLimit;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name = "creditCard")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creditNumber;
    private Long cvv;
    private double totalLimit;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime expireDate;
    private LocalDateTime cancelDate;
    @Enumerated(EnumType.STRING)
    private StatusLimit statusLimit;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
    @OneToMany(mappedBy = "creditCard", orphanRemoval = true)
    private List<TransactionForCreditCard> transactions;
}
