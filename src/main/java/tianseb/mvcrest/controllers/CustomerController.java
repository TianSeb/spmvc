package tianseb.mvcrest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.api.v1.model.CustomerListDTO;
import tianseb.mvcrest.services.CustomerService;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO allCustomers() {
        return new CustomerListDTO(customerService.findAllCustomers());
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO customerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO newCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO,
                                                   @PathVariable Long id) {
        return customerService.saveCustomerById(id,customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO,
                                                      @PathVariable Long id) {
        return customerService.patchCustomer(id,customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
