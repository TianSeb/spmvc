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
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.services.VendorService;

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
class VendorControllerTest {

    public static final String NAME = "Moringo";
    public static final Long ID = 1L;
    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;
    VendorDTO vendor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        vendor = new VendorDTO();
        vendor.setName(NAME);

    }

    @Test
    void testGetAllVendors() throws Exception {
        List<VendorDTO> vendorDTOS = Arrays.asList(new VendorDTO(), new VendorDTO());
        when(vendorService.findAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));
    }

    @Test
    void testGetVendorById() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendor.getName())));
    }

    @Test
    void testCreateNewVendor() throws Exception {
        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME);
        returnDTO.setVendor_url(VendorController.BASE_URL + "/" + ID);

        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(vendor.getName())));
    }

    @Test
    void testUpdateVendorById() throws Exception {
        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME);
        returnDTO.setVendor_url(VendorController.BASE_URL + "/" + ID);

        when(vendorService.saveVendorById(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL + "/" + ID)))
                .andExpect(jsonPath("$.name",equalTo(vendor.getName())));
    }

    @Test
    void testPatchVendor() throws Exception {
        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME);
        returnDTO.setVendor_url(VendorController.BASE_URL + "/" + ID);

        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL + "/" + ID)))
                .andExpect(jsonPath("$.name",equalTo(vendor.getName())));
    }

    @Test
    void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }
}