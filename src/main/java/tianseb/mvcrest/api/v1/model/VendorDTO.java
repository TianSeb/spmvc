package tianseb.mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VendorDTO {
    private String name;
    @JsonProperty("vendor_url")
    private String vendor_url;
}
