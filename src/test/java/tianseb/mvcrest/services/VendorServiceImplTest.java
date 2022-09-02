package tianseb.mvcrest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tianseb.mvcrest.api.v1.mapper.VendorMapper;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.controllers.VendorController;
import tianseb.mvcrest.domain.Vendor;
import tianseb.mvcrest.repositories.VendorRepository;
import tianseb.mvcrest.services.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;
    VendorService vendorService;
    Vendor vendor;

    private final Long ID = 1L;
    private final String NAME = "Bob";

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
        vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        vendor.setVendor_url(getVendorUrl(ID));
    }

    @Test
    void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(),new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.findAllVendors();

        //then
        assertEquals(2,vendorDTOS.size());
        verify(vendorRepository).findAll();
    }

    @Test
    void getVendorById() {
        //given
        Long id = 1L;
        given(vendorRepository.findById(anyLong())).willReturn(Optional.ofNullable(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(id);

        //then
        then(vendorRepository).should().findById(anyLong());
        assertEquals(NAME,vendorDTO.getName());
    }

    @Test
    void getVendorByIdNotFoundThrowsNotFoundException() {
        Long id = 2L;
        when(vendorRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException());

        assertThrows(ResourceNotFoundException.class,
                () -> vendorService.getVendorById(id));
        verify(vendorRepository).findById(anyLong());
    }

    @Test
    void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO vendorSaved = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),vendorSaved.getName());
        assertEquals(getVendorUrl(ID),vendorSaved.getVendor_url());
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    void saveVendorById() {
        //given
        Long id = 2L;
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Esponja");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(id);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDTO = vendorService.saveVendorById(id,vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),savedDTO.getName());
        assertEquals(getVendorUrl(id),savedDTO.getVendor_url());
        verify(vendorRepository).save(any(Vendor.class));

    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository).deleteById(anyLong());
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}