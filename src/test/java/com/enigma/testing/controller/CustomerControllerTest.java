package com.enigma.testing.controller;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.models.customer.CustomerResponse;
import com.enigma.testing.services.customer.CustomerDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerDao service;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void addShouldSuccess() throws Exception {
        Customer entity = new Customer();
        entity.setId(1);
        entity.setName("xyz");
        entity.setAddress("xyz");
        entity.setEmail("xyz@gmail.com");
        entity.setPhoneNumber("081234523443");
        when(service.save(any())).thenReturn(entity);

        CustomerResponse custResp = new CustomerResponse();
        custResp.setId(entity.getId());
        custResp.setName(entity.getName());
        custResp.setAddress(entity.getAddress());
        custResp.setEmail(entity.getEmail());
        custResp.setPhoneNumber(entity.getPhoneNumber());
        when(modelMapper.map(any(Customer.class), any(Class.class))).thenReturn(custResp);

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(custResp)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.name", is(entity.getName())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldReturn_CustomerData() throws Exception {
        Customer entity = new Customer(1, "xyz", "xyz", "xyz@gmail.com", "081234523443");
        service.save(entity);
        List<Customer> customers = Arrays.asList(entity);
        given(service.findAll()).willReturn(customers);

        mockMvc.perform(get("/customer")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())));    }

}
