package com.ecommerce.homeproducts.controller;

import com.ecommerce.homeproducts.dto.LoginDto;
import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/greet")
    public String greet(){
        return "Good Morning";
    };

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        try{
            return userService.load(user);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto user){
        try{
            return userService.login(user);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
