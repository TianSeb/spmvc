package tianseb.mvcrest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tianseb.mvcrest.api.v1.mapper.VendorMapper;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.bootstrap.Bootstrap;
import tianseb.mvcrest.domain.Vendor;
import tianseb.mvcrest.repositories.CategoryRepository;
import tianseb.mvcrest.repositories.CustomerRepository;
import tianseb.mvcrest.repositories.VendorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VendorServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;
    VendorService vendorService;

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Loading Vendor Data");
        System.out.println(vendorRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository, vendorRepository);
        bootstrap.run();//load data

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void patchVendorUpdateName() {
        String updatedName = "Marshanto";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);
        //save original name
        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id,vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertNotNull(updatedVendor);
        assertEquals(updatedName,updatedVendor.getName());
        assertNotEquals(originalName,updatedVendor.getName());

    }

    private Long getVendorIdValue(){
        List<Vendor> vendors = vendorRepository.findAll();
        System.out.println("Vendors found " + vendors.size());

        return vendors.get(0).getId();
    }
}
