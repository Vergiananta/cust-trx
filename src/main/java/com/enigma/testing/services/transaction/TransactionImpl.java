package com.enigma.testing.services.transaction;

import com.enigma.testing.entity.Transaction;
import com.enigma.testing.repository.TransactionRepository;
import com.enigma.testing.services.CommonServiceImpl;
import com.enigma.testing.services.customer.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionImpl extends CommonServiceImpl<Transaction, Integer> implements TransactionDao{

    @Autowired
    public TransactionImpl(TransactionRepository repository) {
        super(repository);
    }
}
