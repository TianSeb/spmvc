package tianseb.mvcrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tianseb.mvcrest.api.v1.model.CustomerDTO;
import tianseb.mvcrest.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tianseb.mvcrest.controllers.AbstractRestControllerTest.asJsonString;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private final String NAME = "Bob";
    private final String LASTNAME = "Esponja";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOSList = Arrays.asList(new CustomerDTO(), new CustomerDTO());
        when(customerService.findAllCustomers()).thenReturn(customerDTOSList);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(NAME);
        customerDTO.setLastname(LASTNAME);

        when(customerService.findById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname",equalTo(LASTNAME)));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(NAME);
        customer.setLastname(LASTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(NAME);
        returnDTO.setLastname(LASTNAME);
        returnDTO.setCustomerUrl("/api/v1/customer/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customer/1")))
                .andExpect(jsonPath("$.firstname",equalTo(NAME)));
    }
}