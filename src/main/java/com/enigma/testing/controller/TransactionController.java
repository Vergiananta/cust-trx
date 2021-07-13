package com.enigma.testing.controller;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.entity.Transaction;
import com.enigma.testing.exceptions.EntityNotFoundException;
import com.enigma.testing.models.ResponseMessage;
import com.enigma.testing.models.transaction.TransactionRequest;
import com.enigma.testing.models.transaction.TransactionResponse;
import com.enigma.testing.services.customer.CustomerDao;
import com.enigma.testing.services.transaction.TransactionDao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionDao services;

    @Autowired
    private CustomerDao customerDao;

    @PostMapping
    public ResponseMessage<TransactionResponse> add(@RequestBody TransactionRequest transaction){
        Transaction entity = modelMapper.map(transaction, Transaction.class);
        Customer customer = customerDao.findById(transaction.getCustomer());
        entity.setCustomer(customer);

        entity = services.save(entity);

        TransactionResponse data = modelMapper.map(entity, TransactionResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<TransactionResponse> removeById(@PathVariable Integer id){
        Transaction entity = services.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        TransactionResponse data = modelMapper.map(entity, TransactionResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<TransactionResponse> edit(@PathVariable Integer id, @RequestBody TransactionRequest transaction){
        Transaction entity = services.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        Customer customer = customerDao.findById(transaction.getCustomer());
        entity.setCustomer(customer);

        modelMapper.map(transaction, entity);
        entity = services.save(entity);

        TransactionResponse data = modelMapper.map(entity, TransactionResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<TransactionResponse>> getAllTrx(){
        List<Transaction> entities = services.findAll();
        List<TransactionResponse> data = entities.stream()
                .map(e -> modelMapper.map(e, TransactionResponse.class))
                .collect(Collectors.toList());
        return ResponseMessage.success(data);
    }
}
