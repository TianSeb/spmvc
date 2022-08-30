package tianseb.mvcrest.services;

import tianseb.mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAllCustomers();
    CustomerDTO findById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
}
