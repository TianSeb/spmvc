package tianseb.mvcrest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.domain.Vendor;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);
    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
