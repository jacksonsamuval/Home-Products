package com.ecommerce.homeproducts.service;

import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String load(User user) {
        userRepo.save(user);
        return "Success";
    }
}
