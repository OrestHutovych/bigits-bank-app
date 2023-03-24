package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.ActivityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_for_creditcard")
public class TransactionForCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Amount can't be null")
    private Double amount;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date transactionalDate;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_card_id", nullable = false)
    @JsonIgnore
    private CreditCard creditCard;
}
