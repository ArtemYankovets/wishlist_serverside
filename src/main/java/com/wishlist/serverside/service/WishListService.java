package com.wishlist.serverside.service;

import com.wishlist.serverside.domain.Address;
import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WishListService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishRepository wishRepository;

    @Override
    public void run(String... strings) throws Exception {

        initUsersCollection();
        initWishesCollection();

        User user = userRepository.findById("userAlanPupkin");
        Wish wish = wishRepository.findById("wish001");

        user.getWishList().add(wish);
        wish.getUserUsageRreferences().add(user.getId());

        userRepository.save(user);
        wishRepository.save(wish);
    }

    private void initWishesCollection() {
        Wish wishOne = new Wish(
                "wish001",
                "Finish wishlist app",
                false,
                new ArrayList<>()
        );

        Wish wishTwo = new Wish(
                "wish002",
                "Launch wishlist app",
                false,
                new ArrayList<>()
        );

        Wish wishThree = new Wish(
                "wish003",
                "Read book",
                false,
                new ArrayList<>()
        );

        Wish wishFour = new Wish(
                "wish004",
                "Go to the gim",
                false,
                new ArrayList<>()
        );

        // drop all wishes
        wishRepository.deleteAll();

        // add all wishes to the database
        List<Wish> wishes = Arrays.asList(wishOne, wishTwo, wishThree, wishFour);
        wishRepository.save(wishes);
    }

    private void initUsersCollection() {
        User alan = new User (
                "userAlanPupkin",
                "Alan",
                "Pupkin",
                new Address("Paris", "France"),
                Arrays.asList(),
                Arrays.asList(),
                false,
                new ArrayList<Wish>()
        );

        User maria = new User (
                "userMariaPupkin",
                "Maria",
                "Pupkin",
                new Address("Bucharest", "Romania"),
                Arrays.asList(),
                Arrays.asList(),
                false,
                new ArrayList<Wish>()
        );

        // drop all users
        userRepository.deleteAll();

        // all users to the database
        List<User> users = Arrays.asList(alan, maria);
        userRepository.save(users);
    }
}
