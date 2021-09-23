package com.saber.site.services;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> findAll();
    CustomerEntity addCustomer(CustomerDto customerDto);
    CustomerEntity updateCustomer(CustomerDto customerDto,Integer id);
    CustomerEntity findById(Integer id);
    boolean deleteCustomerById(Integer id);
}
