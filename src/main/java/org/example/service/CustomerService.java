package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createOrUpdate(Customer customer){
        if(customer.getId() == null){
            customerRepository.save(customer);
        }else {
            customerRepository.save(customer);
        }
        return customer;
    }
    public List<Customer> getAll(){
        List<Customer> all = customerRepository.findAll();
        return all;
    }
}
