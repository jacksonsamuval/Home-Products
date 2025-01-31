package com.ecommerce.homeproducts.service;

import com.ecommerce.homeproducts.config.UserPrincipal;
import com.ecommerce.homeproducts.jwt.JwtService;
import com.ecommerce.homeproducts.dto.LoginDto;
import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> load(User user) {
        User verifyUsername = userRepo.findByUsername(user.getUsername());
        User verifyEmail = userRepo.findByEmail(user.getEmail());
        User verifyMobileNo = userRepo.findByMobileNo(user.getMobileNo());

        if (verifyUsername != null){
            return new ResponseEntity<>("Username Availaible", HttpStatus.CONFLICT);
        } else if (verifyEmail != null) {
            return new ResponseEntity<>("Email Availaible", HttpStatus.CONFLICT);
        } else if (verifyMobileNo != null) {
            return new ResponseEntity<>("Mobile Number Availaible",HttpStatus.CONFLICT);
        } else {
            userRepo.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }

    }

    public ResponseEntity<?> login(LoginDto user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (authentication.isAuthenticated()){
            String token = jwtService.generateToken(user.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        return new ResponseEntity<>("error",HttpStatus.UNAUTHORIZED);
    }

    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }
}
