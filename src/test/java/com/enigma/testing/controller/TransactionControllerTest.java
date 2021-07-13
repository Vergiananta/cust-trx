package com.enigma.testing.controller;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.entity.Transaction;
import com.enigma.testing.models.customer.CustomerResponse;
import com.enigma.testing.models.transaction.TransactionRequest;
import com.enigma.testing.models.transaction.TransactionResponse;
import com.enigma.testing.services.customer.CustomerDao;
import com.enigma.testing.services.transaction.TransactionDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private TransactionDao service;

    @MockBean
    private CustomerDao custService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    MockMvc mockMvc;

    private Customer cust;
    private Transaction trx;
    private List<Transaction> transactionList;

    @BeforeEach
    private void setup() {
        cust = new Customer(1, "xyz", "xyz", "xyz@gmail.com", "081234523443");

        trx = new Transaction(1,cust, LocalDateTime.now(), 200000);

        transactionList = new ArrayList<>();
        transactionList.add(trx);

        CustomerResponse custResp = new CustomerResponse();
        custResp.setId(cust.getId());
        custResp.setName(cust.getName());
        custResp.setEmail(cust.getEmail());
        custResp.setAddress(cust.getAddress());
        custResp.setPhoneNumber(cust.getPhoneNumber());

        TransactionResponse trxResp = new TransactionResponse();
        trxResp.setId(trx.getId());
        trxResp.setBuyDate(trx.getBuyDate());
        trxResp.setTotal(trx.getTotal());
        trxResp.setCustomer(custResp);

        when(modelMapper.map(any(Transaction.class), any(Class.class))).thenReturn(trxResp);

        when(modelMapper.map(any(TransactionRequest.class), any(Class.class))).thenReturn(trx);

        when(custService.findById(1)).thenReturn(cust);
    }

    @Test
    void shouldSave_Transaction() throws Exception {
        when(service.save(any())).thenReturn(trx);

        RequestBuilder request = post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"customer\": " +cust.getId()+"}");

        mockMvc.perform(request)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.total", is(trx.getTotal())));
    }

    @Test
    void shouldReturnAll_TransactionData() throws Exception {
        List<Transaction> data = new ArrayList<>();

        when(service.findAll()).thenReturn(data);

        RequestBuilder request = get("/transactions")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data", empty()));
    }

    @Test
    void deleteShouldReturnSuccess() throws Exception {
        when(service.removeById(1)).thenReturn(trx);
        RequestBuilder request = delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
    @Test
    void editShouldReturnSuccess() throws Exception {
        when(service.findById(1)).thenReturn(trx);
        RequestBuilder request = put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customer\": " +cust.getId()+"}");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())));
    }

    @Test
    void editShouldReturnNotFound() throws Exception {
        when(service.findById(1)).thenReturn(null);
        RequestBuilder request = put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customer\": " +cust.getId()+"}");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(HttpStatus.valueOf(404).value())));
    }

    @Test
    void deleteShouldNotFound() throws Exception {
        when(service.removeById(1)).thenReturn(null);
        RequestBuilder request = delete("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(HttpStatus.valueOf(404).value())));
    }

}
