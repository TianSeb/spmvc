package tianseb.mvcrest.services;

import org.springframework.stereotype.Service;
import tianseb.mvcrest.api.v1.model.VendorDTO;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {
    @Override
    public List<VendorDTO> getAllVendors() {
        return null;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return null;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO saveVendorById(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {

    }
}
