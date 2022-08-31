package tianseb.mvcrest.services;

import org.springframework.stereotype.Service;
import tianseb.mvcrest.api.v1.mapper.CustomerMapper;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.controllers.CustomerController;
import tianseb.mvcrest.domain.Customer;
import tianseb.mvcrest.repositories.CustomerRepository;
import tianseb.mvcrest.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerById(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if(customerDTO.getFirstname() != null) {
                        customer.setFirstname(customerDTO.getFirstname());
                    }
                    if(customerDTO.getLastname() != null) {
                        customer.setLastname(customerDTO.getLastname());
                    }

                    CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customer);
                    returnDto.setCustomerUrl(getCustomerUrl(id));
                    return returnDto;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }
}
