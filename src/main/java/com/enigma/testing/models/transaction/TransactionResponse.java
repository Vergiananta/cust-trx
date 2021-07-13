package com.enigma.testing.models.transaction;

import com.enigma.testing.entity.Customer;
import com.enigma.testing.models.customer.CustomerRequest;
import com.enigma.testing.models.customer.CustomerResponse;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class TransactionResponse {

    private Integer id;

    private CustomerResponse customer;

    private Date buyDate;

    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
