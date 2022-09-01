package tianseb.mvcrest.services;

import org.springframework.stereotype.Service;
import tianseb.mvcrest.api.v1.mapper.VendorMapper;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.controllers.VendorController;
import tianseb.mvcrest.domain.Vendor;
import tianseb.mvcrest.repositories.VendorRepository;
import tianseb.mvcrest.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> findAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendor_url(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendor_url(getVendorUrl(id));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveVendorAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

        private VendorDTO saveVendorAndReturnDTO(Vendor vendor) {
            Vendor vendorSaved = vendorRepository.save(vendor);
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendor_url(getVendorUrl(vendorSaved.getId()));
            return vendorDTO;
        }

    @Override
    public VendorDTO saveVendorById(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        return saveVendorAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName() != null){
                        vendor.setName(vendorDTO.getName());
                    }
                    VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendor);
                    returnDTO.setVendor_url(getVendorUrl(id));
                    return returnDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

}
