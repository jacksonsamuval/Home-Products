package com.ecommerce.homeproducts.service;

import com.ecommerce.homeproducts.config.UserPrincipal;
import com.ecommerce.homeproducts.dto.Category;
import com.ecommerce.homeproducts.dto.Role;
import com.ecommerce.homeproducts.model.Products;
import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProducerService {

    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private ImgbbService imgbbService;
    public ResponseEntity<?> addProduct(String name,
                                        String description,
                                        Integer quantity,
                                        String price,
                                        Category category,
                                        MultipartFile file) {
        String fileUrl;

        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        try{
            fileUrl = imgbbService.uploadToImgbb(file);
            System.out.println("Successfully Uploaded");
        } catch (Exception e){
            return new ResponseEntity<>("Error Uploading file",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole() == Role.PRODUCER){
            Products products = new Products();
            products.setProductName(name);
            products.setProductDescription(description);
            products.setCategory(category);
            products.setQuantity(quantity);
            products.setPrice(price);
            products.setImage(fileUrl);
            products.setUser(user);
            Products product = productsRepo.save(products);
            return ResponseEntity.ok(product);
        }else {
            return new ResponseEntity<>("not producer", HttpStatus.BAD_REQUEST);
        }
    }

    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }


}
