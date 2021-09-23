package com.saber.site.repositories.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CustomerEntity> findAll() {
        return entityManager.
                createNamedQuery("CustomerEntity.findAll",
                        CustomerEntity.class).getResultList();

    }

    @Override
    public CustomerEntity addCustomer(CustomerDto customerDto) {
        CustomerEntity entity = createEntity(customerDto);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CustomerEntity updateCustomer(CustomerDto customerDto, Integer id) {

        return null;
    }

    @Override
    public CustomerEntity findById(Integer id) {
        return entityManager.createNamedQuery("CustomerEntity.findById",CustomerEntity.class)
                .setParameter("id",id).getSingleResult();
    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        return false;
    }

    private CustomerEntity createEntity(CustomerDto dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
