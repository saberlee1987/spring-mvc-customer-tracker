package com.saber.site.controllers;

import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = {"/list",""})
    public String findAll(Model model){
        List<CustomerEntity> customers = this.customerService.findAll();
        log.info("customers ====> {} ",customers);
        model.addAttribute("customers",customers);
        return "customer/list";
    }
    @GetMapping(value = "/update/{id}")
    public String updateCustomer(Model model, @PathVariable(name = "id")Integer id){
        CustomerEntity customerEntity = this.customerService.findById(id);
        if (customerEntity!=null){
            model.addAttribute("customer",customerEntity);
            log.info("customer with id {} ====> {} ",id,customerEntity);
            return "customer/update";
        }else{
            model.addAttribute("message",String.format("Customer with id %s does not exist",id));
            log.info("Customer with id {} does not exist ",id);
            return "customer/list";
        }
    }
}
