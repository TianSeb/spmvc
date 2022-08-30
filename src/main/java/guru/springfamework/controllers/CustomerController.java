package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> allCustomers() {
        return new ResponseEntity<> (
                new CustomerListDTO(customerService.findAllCustomers()),HttpStatus.OK);
    }

    @GetMapping({"{id}"})
    public ResponseEntity<CustomerDTO> customerById(@PathVariable Long id) {
        return new ResponseEntity<>(
                customerService.findById(id),HttpStatus.OK);
    }
}