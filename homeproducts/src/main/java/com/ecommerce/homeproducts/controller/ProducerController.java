package com.ecommerce.homeproducts.controller;

import com.ecommerce.homeproducts.dto.Category;
import com.ecommerce.homeproducts.model.Products;
import com.ecommerce.homeproducts.service.ImgbbService;
import com.ecommerce.homeproducts.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ImgbbService imgbbService;
    @PostMapping("/addProducts")
    public ResponseEntity<?> addProducts(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam Integer quantity,
                                         @RequestParam String price,
                                         @RequestParam Category category,
                                         @RequestParam MultipartFile file){
        try {
            return producerService.addProduct(name,description,quantity,price,category,file);
        } catch (Exception e){
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
