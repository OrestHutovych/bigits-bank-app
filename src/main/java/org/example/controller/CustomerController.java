package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping
    public List<Customer> findAll(){
        return customerService.getAll();
    }
}
