package tianseb.mvcrest.controllers;

import org.springframework.web.bind.annotation.*;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.api.v1.model.CustomerListDTO;
import tianseb.mvcrest.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/v1/customers")
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

    @GetMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> customerById(@PathVariable Long id) {
        return new ResponseEntity<>(
                customerService.findById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> newCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO>(
                customerService.createNewCustomer(customerDTO),HttpStatus.CREATED);
    }
}
