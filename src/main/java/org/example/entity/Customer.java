package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name can't be blank")
    @Size(min = 4, max = 20)
    private String name;
    @Size(min = 10, max = 30)
    @Column(unique = true)
    private String email;
    @NotNull(message = "Age can't be null")
    @Min(value = 18, message = "Age should not be less then 18")
    private Integer age;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "customer", orphanRemoval = true)
    private List<Account> accounts;
    @OneToMany(mappedBy = "customer")
    private List<CreditCard> creditCards;

}