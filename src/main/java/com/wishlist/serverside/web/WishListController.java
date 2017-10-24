package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    // Create

    /**
     * Creates new User
     * @param user
     *        content for new User
     *
     * @return response entity with status code
     *
     */
    @RequestMapping (value = "/users/new", method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody User user) {

        User existUser = this.userRepository.findById(user.getId());
        if (existUser!=null && existUser.equals(user)) {

            // TODO Add message as json file and set it to ResponseEntity

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.userRepository.insert(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @RequestMapping (value = "/users/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // Update
    @RequestMapping (value = "/users/{id}/update/wishlist", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity udateUserWishList(@RequestParam("id") String id, @RequestBody List<String> listOfwishesId){
        this.userRepository.findById(id).setListOfWishesId(listOfwishesId);
        return new ResponseEntity<>( HttpStatus.CREATED );
    }


    // Delete
    @RequestMapping (value = "/users/remove/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable("id") String id) {
        this.userRepository.delete(id);
    }

    @RequestMapping (value = "/users/remove/all", method = RequestMethod.DELETE)
    public void removeAllUsers() {
        this.userRepository.deleteAll();
    }





    /* wishes */
    // Create
    @RequestMapping (value = "/wishes/new", method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody Wish wish) {
        if (this.wishRepository.findById(wish.getId()).equals(wish)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.wishRepository.insert(wish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @RequestMapping (value = "/wishes/all", method = RequestMethod.GET)
    public List<Wish> getAllWishes() {
        return this.wishRepository.findAll();
    }

    // Update

    // Delete
    @RequestMapping (value = "/wishes/remove/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        this.wishRepository.delete(id);
    }
}
