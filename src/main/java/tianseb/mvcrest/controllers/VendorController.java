package tianseb.mvcrest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";



}
