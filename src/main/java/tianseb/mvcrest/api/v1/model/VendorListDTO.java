package tianseb.mvcrest.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class VendorListDTO {
    private List<VendorDTO> vendorList;
}
