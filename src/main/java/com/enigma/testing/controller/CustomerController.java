package com.enigma.testing.controller;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.exceptions.EntityNotFoundException;
import com.enigma.testing.models.ResponseMessage;
import com.enigma.testing.models.customer.CustomerRequest;
import com.enigma.testing.models.customer.CustomerResponse;
import com.enigma.testing.services.customer.CustomerDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerDao service;

    @PostMapping
    public ResponseMessage<CustomerResponse> add(@RequestBody CustomerRequest customer){
        Customer entity = modelMapper.map(customer, Customer.class);
        entity = service.save(entity);
        System.out.println("test"+entity.toString());
        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<CustomerResponse> edit(@PathVariable Integer id, @RequestBody CustomerRequest customer){
        Customer entity = service.findById(id);
        if (entity == null){
            throw new EntityNotFoundException();
        }
        modelMapper.map(customer, entity);
        entity = service.save(entity);

        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<CustomerResponse> removeById(@PathVariable Integer id){
        Customer entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<CustomerResponse>> findAll(){
        List<Customer> entities = service.findAll();
        List<CustomerResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, CustomerResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(data);
    }
}
