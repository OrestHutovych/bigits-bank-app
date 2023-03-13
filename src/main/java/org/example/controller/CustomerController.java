package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class CustomerController{
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> map = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Customer orUpdate = customerService.createOrUpdate(customer);
        return new ResponseEntity<>(orUpdate, HttpStatus.CREATED);
    }
    @PutMapping("/{customer_id}")
    public ResponseEntity<?> update(@PathVariable Long customer_id, @Valid @RequestBody Customer customer,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> map = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        customer.setId(customer_id);
        Customer update = customerService.createOrUpdate(customer);
        return new ResponseEntity<Customer>(update, HttpStatus.CREATED);
    }
    @GetMapping
    public List<Customer> findAll(){
        return customerService.getAll();
    }
    @GetMapping("/{customer_id}")
    public ResponseEntity<?> getOne(@PathVariable Long customer_id){
        Customer one = customerService.getOne(customer_id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }
    @DeleteMapping("/{customer_id}")
    public ResponseEntity<?> delete(@PathVariable Long customer_id){
        boolean delete = customerService.delete(customer_id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        boolean all = customerService.deleteAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
