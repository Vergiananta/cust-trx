package com.enigma.testing.services.customer;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerImplTest {

    @Mock
    private CustomerRepository repo;

    @InjectMocks
    private CustomerImpl service;

    @Test
    public void shouldSave() {
        Customer input = new Customer();
        input.setName("xyz");
        input.setAddress("xyz");
        input.setEmail("xyz@gmail.com");
        input.setPhoneNumber("081234523443");

        service.save(input);
        repo.save(input);

        List<Customer> customers = new ArrayList<>();
        customers.add(input);

        when(repo.findAll()).thenReturn(customers);
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void getCustomer(){
        Customer input = new Customer();
        input.setName("xyz");
        input.setAddress("xyz");
        input.setEmail("xyz@gmail.com");
        input.setPhoneNumber("081234523443");

        repo.save(input);
        List<Customer> customers = new ArrayList<>();
        customers.add(input);

        when(service.findAll()).thenReturn(customers);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteCustomer(){
        Customer input = new Customer();
        input.setName("xyz");
        input.setAddress("xyz");
        input.setEmail("xyz@gmail.com");
        input.setPhoneNumber("081234523443");

        repo.save(input);
        service.removeById(1);

        assertEquals(0, repo.findAll().size());
    }

}
