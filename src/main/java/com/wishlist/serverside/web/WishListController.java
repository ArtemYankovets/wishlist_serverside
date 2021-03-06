package com.wishlist.serverside.web;


import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.domain.WishList;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishListRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @param user content for new User.
     * @return request with status code and message if resource is already exist.
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

    /**
     * Returns all Users in collection.
     *
     * @return request with status code and all Users from collection in json format.
     */
    @RequestMapping(value = "/users/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Returns User by id.
     *
     * @param id of User which should be returned.
     * @return request with status code and User entity in json format.
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUser(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.userRepository.findById(id), HttpStatus.OK);
    }

    // Update

    /**
     * Updates wishListIds in User.
     *
     * @param id          of User which should be updated.
     * @param wishListIds list of ids which should be updated in User.
     * @return request with status code.
     */
    @RequestMapping(value = "/users/{id}/update/wishlist", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity updateWishListIdsInUser(@PathVariable("id") String id, @RequestBody List<String> wishListIds) {
        this.userRepository.findById(id).setWishListIds(wishListIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Delete

    /**
     * Deletes User by id.
     *
     * @param id of User which should be deleted.
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/users/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        if ( !this.userRepository.exists(id) ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        // TODO delete User id from all inner WishLists

        this.userRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Deletes all Users.
     *
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/users/remove/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllUsers() {
        if ( this.userRepository.findAll().isEmpty() ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        // TODO delete User id from all inner WishLists

        this.userRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    /* -------------------- wishlists -------------------- */
    // Create

    /**
     * Creates new WishList.
     *
     * @param wishList content for new WishList.
     * @return request with status code and message if resource is already exist.
     */
    @RequestMapping(value = "/lists/new", method = RequestMethod.POST)
    public ResponseEntity insertWishList(@RequestBody WishList wishList) {

        WishList existWishList = this.wishListRepository.findById(wishList.getId());
        if ( existWishList != null && existWishList.equals(wishList) ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_IS_ALREADY_EXIST, HttpStatus.OK);
        }

        this.wishListRepository.insert(wishList);

        String ownerId = wishList.getOwner();

        // add new wishlist id to User owner entity
        User owner = this.userRepository.findById(ownerId);
        owner.getWishListIds().add(wishList.getId());
        this.userRepository.save(owner);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Read

    /**
     * Returns all WishLists in collection.
     *
     * @return request with status code and all WishLists from collection in json format.
     */
    @RequestMapping(value = "/lists/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllWishLists() {
        return new ResponseEntity<>(this.wishListRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Returns WishList by id.
     *
     * @param id of WishList which should be returned.
     * @return request with status code and WishList entity in json format.
     */
    @RequestMapping(value = "/lists/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getWishList(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.wishListRepository.findById(id), HttpStatus.OK);
    }

    // Update

    // Delete

    /**
     * Deletes WishList by id, removes WishList id from User and from all connected Wishes.
     *
     * @param id of WishList which should be deleted.
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/lists/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWishList(@PathVariable("id") String id) {
        WishList existWishList = this.wishListRepository.findById(id);
        if ( existWishList == null ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        // delete WishList id from owner User
        User owner = this.userRepository.findById(existWishList.getOwner());
        owner.getWishListIds().remove(id);
        this.userRepository.save(owner);

        // delete WishList id from all inner Wishes
        List<String> wishes = existWishList.getWishes();
        wishes.forEach(wishId -> {
            Wish wish = this.wishRepository.findById(wishId);
            wish.setWishListUsageId("");
            this.wishRepository.save(wish);
        });

        this.wishListRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /* -------------------- wishes -------------------- */
    // Create

    /**
     * Creates new Wish.
     *
     * @param wish content for new Wish.
     * @return request with status code and message if resource is already exist.
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

    /**
     * Returns all Wishes in collection.
     *
     * @return all Wishes from collection in json format.
     */
    @RequestMapping(value = "/wishes/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllWishes() {
        return new ResponseEntity<>(this.wishRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Returns Wish by id.
     *
     * @param id of Wish which should be returned.
     * @return request with status code and Wish entity in json format.
     */
    @RequestMapping(value = "/wishes/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getWish(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.wishRepository.findById(id), HttpStatus.OK);
    }

    // Update

    /**
     * Updates a one Wish. If you add connection to a WishList new changes would be making in WishList also.
     * But if a Wish was contained in a WishList and in a new version of a Wish this connection is removed
     * the WishList would not be updated.
     * For this case and for removing Wishes form a WishList must be used path statement for 'updateOneWishList'
     * or 'updateListOfWishLists' methods.
     *
     * @param wish which should be updated.
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/wishes/update", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity updateOneWish(@RequestBody Wish wish) {

        // checking existing wish in db
        if ( !this.wishRepository.exists(wish.getId()) ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        if ( !wish.getWishListUsageId().isEmpty() ) {
            if ( !this.wishListRepository.exists(wish.getWishListUsageId()) ) {
                return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
            }
            WishList wishList = this.wishListRepository.findById(wish.getWishListUsageId());
            wishList.getWishes().add(wish.getId());
            this.wishListRepository.save(wishList);
        }
        this.wishRepository.save(wish);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates a list of Wishes. If you add connections to a WishList new changes would be making in WishList also.
     * But if a Wish was contained in a WishList and in a new version of a Wish this connection is removed
     * the WishList would not be updated.
     * For this case and for removing Wishes form a WishList must be used path statement for 'updateOneWishList'
     * or 'updateListOfWishLists' methods.
     *
     * @param list of wishes which should be updated.
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/wishes/update/list", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity updateListOfWishes(@RequestBody List<Wish> list) {

        List<WishList> wishLists = new ArrayList<>();

        for ( Wish wish : list ) {
            // checking existing wish in db
            if ( !this.wishRepository.exists( wish.getId() ) ) {
                return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
            }

            if ( !wish.getWishListUsageId().isEmpty() ) {
                WishList wList = this.wishListRepository.findById( wish.getWishListUsageId() );

                if ( wList == null ) {
                    return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
                }

                // checking existence of WishId in the WishList
                if ( !wList.getWishes().contains(wish.getId()) ) {
                    wList.getWishes().add(wish.getId());
                    wishLists.add( wList );
                }
            }
        }

        if ( !list.isEmpty() ) {
            this.wishRepository.save(list);
            if ( !wishLists.isEmpty() ) this.wishListRepository.save(wishLists);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete

    /**
     * Deletes a one Wish by id.
     *
     * @param id of a Wish which should be deleted.
     * @return request with status code and message if resource doesn't exist.
     */
    @RequestMapping(value = "/wishes/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOneWish(@PathVariable("id") String id) {

        Wish existWish = this.wishRepository.findById(id);
        if ( existWish == null ) {
            return new ResponseEntity<>(ConstantMessages.RESOURCE_DOES_NOT_EXIST, HttpStatus.OK);
        }

        // each Wish exists only in one wishlist
        String wishListUsageId = existWish.getWishListUsageId();
        WishList wishList = this.wishListRepository.findById(wishListUsageId);
        wishList.getWishes().remove(id);
        this.wishListRepository.save(wishList);

        this.wishRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
