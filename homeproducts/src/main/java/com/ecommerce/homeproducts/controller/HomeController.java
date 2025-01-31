package com.ecommerce.homeproducts.controller;

import com.ecommerce.homeproducts.jwt.JwtService;
import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/greet")
    public String greet(){
        return "Good Morning";
    };

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return userService.load(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        } else {
            return "failure";
        }
    }
}
