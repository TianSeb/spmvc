package tianseb.mvcrest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tianseb.mvcrest.api.v1.model.VendorDTO;
import tianseb.mvcrest.api.v1.model.VendorListDTO;
import tianseb.mvcrest.services.VendorService;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO allVendors() {
        return new VendorListDTO(vendorService.findAllVendors());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO vendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO newVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO udpateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorById(id,vendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendorById(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(id,vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
