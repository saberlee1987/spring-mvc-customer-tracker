package com.saber.site.controllers;

import com.saber.site.dto.CustomerDto;
import com.saber.site.entities.CustomerEntity;
import com.saber.site.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/add")
    public String addCustomer(Model model){
        model.addAttribute("customer",new CustomerDto());
        return "customer/add";
    }

    @PostMapping(value = "/add")
    public ModelAndView addCustomer(@Valid CustomerDto customerDto, Errors errors, Model model){

        log.info("Request for add new Customer with body ===> {}",customerDto);

        if (errors.hasErrors()) {
            List<FieldError> errorList= errors.getFieldErrors();

            model.addAttribute("errors",errorList);
            model.addAttribute("customer",customerDto);
            model.addAttribute("message","Please Enter your Information");
            return new ModelAndView("/customer/add");
        }

        CustomerEntity customerEntity =this.customerService.addCustomer(customerDto);
        if (customerEntity!=null){
            return new ModelAndView(new RedirectView("/customer/list",true,false));
        }else{
            model.addAttribute("customer",customerDto);
            model.addAttribute("message","Can not add new Customer");
            return new ModelAndView("/customer/add");
        }
    }



    @GetMapping(value = {"list",""})
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

            CustomerDto customerDto = new CustomerDto();
            customerDto.setFirstName(customerEntity.getFirstName());
            customerDto.setLastName(customerEntity.getLastName());
            customerDto.setEmail(customerEntity.getEmail());


            model.addAttribute("customer",customerDto);
            model.addAttribute("id",String.valueOf(id));
            log.info("customer with id {} ====> {} ",id,customerEntity);
            return "customer/update";
        }else{
            model.addAttribute("message",String.format("Customer with id %s does not exist",id));
            log.info("Customer with id {} does not exist ",id);
            return "customer/list";
        }
    }

    @PostMapping(value = "/update/{id}")
    public ModelAndView updateCustomer(@Valid CustomerDto customerDto, Errors errors, @PathVariable Integer id, Model model){
        log.info("Request for update Customer with body ===> {}",customerDto);

        if (errors.hasErrors()) {
            List<FieldError> errorList= errors.getFieldErrors();

            model.addAttribute("errors",errorList);
            model.addAttribute("customer",customerDto);
            model.addAttribute("id",String.valueOf(id));
            model.addAttribute("message","Please Enter your Information");
            return new ModelAndView("/customer/update");
        }

        CustomerEntity customerEntity =this.customerService.updateCustomer(customerDto,id);
        if (customerEntity==null){
            log.error("customer does not exist ");
           return new ModelAndView(new RedirectView("/customer/list",true,false));
        }else{
            log.info("customer updated ..................");
            return new ModelAndView(new RedirectView("/customer/list",true,false));
        }
    }
    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteCustomer(Model model,@PathVariable(name = "id") Integer id){
        log.info("delete customer by id ===> {}",id);
        boolean result = this.customerService.deleteCustomerById(id);
        if (result){
            return new ModelAndView(new RedirectView("/customer/list",true,false));
        }else{
            model.addAttribute("message", String.format("customer with id %s does not exist", id));
            model.addAttribute("customers",this.customerService.findAll());
            return new ModelAndView("/customer/list");
        }

    }
}
