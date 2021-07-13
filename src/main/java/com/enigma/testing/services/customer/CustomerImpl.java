package com.enigma.testing.services.customer;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.repository.CustomerRepository;
import com.enigma.testing.services.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerImpl extends CommonServiceImpl<Customer, Integer> implements CustomerDao {

    @Autowired
    public CustomerImpl(CustomerRepository repository) {
        super(repository);
    }
}
