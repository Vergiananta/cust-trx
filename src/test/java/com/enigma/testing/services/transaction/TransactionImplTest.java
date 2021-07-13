package com.enigma.testing.services.transaction;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.entity.Transaction;
import com.enigma.testing.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionImplTest {

    @InjectMocks
    TransactionImpl service;

    @Mock
    TransactionRepository repo;

    @Test
    void shouldSave(){
        Customer cust = new Customer(1, "xyz", "xyz", "xyz@gmail.com", "08123732182");
        Transaction input = new Transaction(1, cust, LocalDateTime.now(), 200000);

        service.save(input);
        repo.save(input);

        List<Transaction> trx = new ArrayList<>();
        trx.add(input);

        when(repo.findAll()).thenReturn(trx);
        assertEquals(1, repo.findAll().size());

    }

    @Test
    void getTransaction(){
        Customer cust = new Customer(1, "xyz", "xyz", "xyz@gmail.com", "08123732182");
        Transaction input = new Transaction(1, cust, LocalDateTime.now(), 200000);

        repo.save(input);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(input);

        when(service.findAll()).thenReturn(transactions);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteTransaction(){
        Customer cust = new Customer(1, "xyz", "xyz", "xyz@gmail.com", "08123732182");
        Transaction input = new Transaction(1, cust, LocalDateTime.now(), 200000);

        repo.save(input);
        service.removeById(1);

        assertEquals(0, repo.findAll().size());

    }
}
