package tianseb.mvcrest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.services.VendorService;

import java.util.List;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public List<VendorDTO> allVendors() {
        return vendorService.findAllVendors();
    }
}
