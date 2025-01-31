package com.ecommerce.homeproducts.config;

import com.ecommerce.homeproducts.model.User;
import com.ecommerce.homeproducts.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Not Found");
        }
        return new UserPrincipal(user);
    }
}
