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
import tianseb.mvcrest.services.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
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
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOSList = Arrays.asList(new CustomerDTO(), new CustomerDTO());
        when(customerService.findAllCustomers()).thenReturn(customerDTOSList);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(NAME);
        customerDTO.setLastname(LASTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname",equalTo(LASTNAME)));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(NAME);
        customer.setLastname(LASTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(NAME);
        returnDTO.setLastname(LASTNAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer_url",equalTo(CustomerController.BASE_URL + "/1")))
                .andExpect(jsonPath("$.firstname",equalTo(NAME)));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(NAME);
        customer.setLastname(LASTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomerById(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_url",equalTo(CustomerController.BASE_URL + "/1")))
                .andExpect(jsonPath("$.firstname",equalTo(NAME)))
                .andExpect(jsonPath("$.lastname",equalTo(LASTNAME)));
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(NAME);
        customer.setLastname(LASTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname("Diego");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_url",equalTo(CustomerController.BASE_URL + "/1")))
                .andExpect(jsonPath("$.firstname",equalTo(NAME)))
                .andExpect(jsonPath("$.lastname",equalTo("Diego")));
    }

    @Test
    void testDeleteCustomerById() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}