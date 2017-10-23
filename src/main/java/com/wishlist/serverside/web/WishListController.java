package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/wishlist")
public class WishListController {

    @Autowired
    private UserRepository userRepository;

    public WishListController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* users */
    @GetMapping(value = "/users/all")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

}
