package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping (value = "/users/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @RequestMapping (value = "/users/new", method = RequestMethod.PUT)
    public void insert(@RequestBody User user) {
        this.userRepository.insert(user);
    }

    /* wishes */
    @RequestMapping (value = "/wishes/all", method = RequestMethod.GET)
    public List<Wish> getAllWishes() {
        return this.wishRepository.findAll();
    }

    @RequestMapping (value = "/wishes/remove/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        this.wishRepository.delete(id);
    }



}
