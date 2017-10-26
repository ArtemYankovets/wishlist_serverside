package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.domain.WishList;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishListRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/wishlist")
public class WishListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishRepository wishRepository;

    public WishListController(UserRepository userRepository, WishListRepository wishListRepository, WishRepository wishRepository) {
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
        this.wishRepository = wishRepository;
    }

    /* -------------------- users -------------------- */
    // Create

    /**
     * Creates new User
     *
     * @param user content for new User
     * @return request with status code and message if resource is already exist     *
     */
    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    public ResponseEntity insertUser(@RequestBody User user) {

        User existUser = this.userRepository.findById(user.getId());
        if ( existUser != null && existUser.equals(user) ) {

            return new ResponseEntity<>(ConstantMessages.RESOURCE_IS_ALREADY_EXIST, HttpStatus.OK);
        }

        this.userRepository.insert(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // Update
    @RequestMapping(value = "/users/{id}/update/wishlist", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity udateUserWishList(@RequestParam("id") String id, @RequestBody List<String> wishListIds) {
        this.userRepository.findById(id).setWishListIds(wishListIds);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // Delete
    @RequestMapping(value = "/users/remove/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable("id") String id) {
        this.userRepository.delete(id);
    }

    @RequestMapping(value = "/users/remove/all", method = RequestMethod.DELETE)
    public void removeAllUsers() {
        this.userRepository.deleteAll();
    }


    /* -------------------- wishlists -------------------- */
    // Create

    /**
     * Creates new WishList
     *
     * @param wishList content for new WishList
     * @return request with status code and message if resource is already exist
     */
    @RequestMapping(value = "/lists/new", method = RequestMethod.POST)
    public ResponseEntity insertWishList(@RequestBody WishList wishList) {

        WishList existWishList = this.wishListRepository.findById(wishList.getId());
        if ( existWishList != null && existWishList.equals(wishList) ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_IS_ALREADY_EXIST, HttpStatus.OK);
        }

        this.wishListRepository.insert(wishList);

        String ownerId = wishList.getOwner();

        // add new wishlist id to
        User owner = this.userRepository.findById(ownerId);
        owner.getWishListIds().add(wishList.getId());
        this.userRepository.save(owner);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @RequestMapping(value = "/lists/all", method = RequestMethod.GET)
    public List<WishList> getAllWishLists() {
        return this.wishListRepository.findAll();
    }

    // Update
    // Delete


    /* -------------------- wishes -------------------- */
    // Create

    /**
     * Creates new Wish
     *
     * @param wish content for new Wish
     * @return request with status code and message if resource is already exist
     */
    @RequestMapping(value = "/wishes/new", method = RequestMethod.POST)
    public ResponseEntity insertWish(@RequestBody Wish wish) {

        Wish existWish = this.wishRepository.findById(wish.getId());
        if ( existWish != null && existWish.equals(wish) ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_IS_ALREADY_EXIST, HttpStatus.OK);
        }

        if ( wish.getWishListUsageId() != null ) {
            if ( !this.wishListRepository.exists(wish.getWishListUsageId()) ) {
                return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
            }

            WishList wishList = this.wishListRepository.findById(wish.getWishListUsageId());
            wishList.getWishes().add(wish.getId());
            this.wishListRepository.save(wishList);
        }

        this.wishRepository.insert(wish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read
    @RequestMapping(value = "/wishes/all", method = RequestMethod.GET)
    public List<Wish> getAllWishes() {
        return this.wishRepository.findAll();
    }

    // Update

    // Delete

    /**
     * Deletes Wish by id
     *
     * @param id of wish which should be deleted
     * @return request with status code and message if resource doesn't exist
     */
    @RequestMapping(value = "/wishes/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {

        Wish existWish = this.wishRepository.findById(id);
        if ( existWish == null ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        // each wish exist only in one wishlist
        String wishListUsageId = existWish.getWishListUsageId();
        WishList wishList = this.wishListRepository.findById(wishListUsageId);
        wishList.getWishes().remove(id);
        this.wishListRepository.save(wishList);

        this.wishRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
