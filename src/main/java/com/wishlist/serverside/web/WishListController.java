package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/wishlist")
public class WishListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishRepository wishRepository;

    public WishListController(UserRepository userRepository, WishRepository wishRepository) {
        this.userRepository = userRepository;
        this.wishRepository = wishRepository;
    }

    /* users */
    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /* wishes */
    @RequestMapping(value = "/wishes/all", method = RequestMethod.GET, produces = "application/json")
    public List<Wish> getAllWishes() {
        return this.wishRepository.findAll();
    }
}
