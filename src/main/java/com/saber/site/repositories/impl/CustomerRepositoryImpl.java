package com.saber.site.repositories.impl;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

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
        CustomerEntity customerEntity = findById(id);
        if (customerEntity != null) {
            customerEntity.setFirstName(customerDto.getFirstName());
            customerEntity.setLastName(customerDto.getLastName());
            customerEntity.setEmail(customerDto.getEmail());
            this.entityManager.persist(customerEntity);
            return customerEntity;
        }
        return null;
    }

    @Override
    public CustomerEntity findById(Integer id) {
        try {
            return entityManager.createNamedQuery("CustomerEntity.findById", CustomerEntity.class)
                    .setParameter("id", id).getSingleResult();

        }catch (Exception ex){
            log.error("customer does not exist with id {}",id);
            return null;
        }
    }

    @Override
    public boolean deleteCustomerById(Integer id) {
        CustomerEntity customerEntity = findById(id);
        if (customerEntity == null) {
            return false;
        }
        try {
            this.entityManager.remove(customerEntity);
            log.info("customer by id {} removed", id);
            return true;
        } catch (Exception ex) {
            log.error("Error when deleting customer by id {} ===> {}", id, ex.getMessage());
            return false;
        }
    }

    private CustomerEntity createEntity(CustomerDto dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
