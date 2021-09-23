package com.saber.site.services.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import com.saber.site.services.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findAll() {
        return this.customerRepository.findAll();
    }

    @Override
    @Transactional
    public CustomerEntity addCustomer(CustomerDto customerDto) {
        return this.customerRepository.addCustomer(customerDto);
    }

    @Override
    @Transactional
    public CustomerEntity updateCustomer(CustomerDto customerDto, Integer id) {
        return this.customerRepository.updateCustomer(customerDto,id);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerEntity findById(Integer id) {
        return this.customerRepository.findById(id);
    }

    @Override
    @Transactional()
    public boolean deleteCustomerById(Integer id) {
        return this.customerRepository.deleteCustomerById(id);
    }
}
