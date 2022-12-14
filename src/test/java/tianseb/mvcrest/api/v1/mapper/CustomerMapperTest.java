package tianseb.mvcrest.api.v1.mapper;

import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    private final String NAME = "Bob";
    private final String LASTNAME = "Esponja";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    Customer customer;

    @Test
    void customerToCustomerDTO() {
        customer = Customer.builder()
                .firstname(NAME)
                .lastname(LASTNAME)
                .build();

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(NAME,customerDTO.getFirstname());
        assertEquals(LASTNAME,customerDTO.getLastname());
    }
}