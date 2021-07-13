package com.enigma.testing.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction extends AbstractEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "buy_date")
    private Date buyDate;

    private Integer total;

    public Transaction(Integer id, Customer customer, Date buyDate, Integer total) {
        this.id = id;
        this.customer = customer;
        this.buyDate = buyDate;
        this.total = total;
    }

    public Transaction() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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
