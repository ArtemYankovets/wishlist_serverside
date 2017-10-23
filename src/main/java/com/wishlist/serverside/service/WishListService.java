package com.wishlist.serverside.service;

import com.wishlist.serverside.domain.Address;
import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.persistance.UserRepository;
import com.wishlist.serverside.persistance.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WishListService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... strings) throws Exception {
        User alan = new User (
            "Alan",
            "Pupkin",
            new Address("Paris", "France"),
            Arrays.asList(),
            Arrays.asList(),
            false,
            Arrays.asList()
        );

        User maria = new User (
                "Maria",
                "Pupkin",
                new Address("Bucharest", "Romania"),
                Arrays.asList(),
                Arrays.asList(),
                false,
                Arrays.asList()
        );



        // drop all users
        userRepository.deleteAll();

        // all users to the database
        List<User> users = Arrays.asList(alan, maria);
        userRepository.save(users);
    }
}
