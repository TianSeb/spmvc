package tianseb.mvcrest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tianseb.mvcrest.api.v1.mapper.CustomerMapper;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.bootstrap.Bootstrap;
import tianseb.mvcrest.domain.Customer;
import tianseb.mvcrest.repositories.CategoryRepository;
import tianseb.mvcrest.repositories.CustomerRepository;
import tianseb.mvcrest.repositories.VendorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;
    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository, vendorRepository);
        bootstrap.run();//load data

        customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "Moringo";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id,customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName,updatedCustomer.getFirstname());
        assertNotEquals(originalFirstName,updatedCustomer.getFirstname());
        assertEquals(originalLastName,updatedCustomer.getLastname());
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertEquals(originalFirstName, updatedCustomer.getFirstname());
        assertNotEquals(originalLastName,updatedCustomer.getLastname());
    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers found " + customers.size());

        return customers.get(0).getId();
    }
}
