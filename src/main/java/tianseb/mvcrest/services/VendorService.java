package tianseb.mvcrest.services;

import tianseb.mvcrest.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> findAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorDTO saveVendorById(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}
