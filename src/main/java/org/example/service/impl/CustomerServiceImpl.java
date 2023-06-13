package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer){
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
    public Customer getOne(Long customer_id){
        Optional<Customer> byId = customerRepository.findById(customer_id);
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }
    public boolean delete(Long id){
        Optional<Customer> byId = customerRepository.findById(id);
        if(byId.isPresent()){
            customerRepository.delete(byId.get());
            return true;
        }
        return false;
    }
    public boolean deleteAll(){
        List<Customer> all = customerRepository.findAll();
        if(!all.isEmpty()) {
            customerRepository.deleteAll();
            return true;
        }
        return false;
    }
}
