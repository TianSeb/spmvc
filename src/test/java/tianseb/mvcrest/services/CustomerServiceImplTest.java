package tianseb.mvcrest.services;

import tianseb.mvcrest.api.v1.mapper.CustomerMapper;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.domain.Customer;
import tianseb.mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private final String NAME = "Bob";
    private final String LASTNAME = "Esponja";
    private final Long ID = 1L;

    @Mock
    CustomerRepository customerRepository;
    CustomerService customerService;

    Customer customer;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void allCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(),new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerListDTO = customerService.findAllCustomers();

        //then
        assertEquals(2,customerListDTO.size());
        verify(customerRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        //given
        customer = Customer.builder().id(ID).firstname(NAME).lastname(LASTNAME).build();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));

        //when
        CustomerDTO customerDTO = customerService.findById(ID);

        //then
        assertEquals(LASTNAME, customerDTO.getLastname());
        verify(customerRepository,times(1)).findById(anyLong());
    }

    @Test
    void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(NAME);

        Customer savedCustomer = Customer.builder()
                                            .id(ID)
                                            .firstname(NAME)
                                            .lastname(LASTNAME)
                                            .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        assertEquals(customerDTO.getFirstname(),savedDto.getFirstname());
        assertEquals("/api/v1/customer/1",savedDto.getCustomerUrl());
    }
}