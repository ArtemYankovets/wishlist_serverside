package com.wishlist.serverside.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "Users")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private Address address;

    private List<String> emails;
    private List<String> phones;

    private boolean autorized;

    // only wishes id
    private List<String> wishList;

    public User() {
    }

    public User(String firstName, String lastName, Address address, List<String> emails, List<String> phones, boolean autorized, List<String> wishList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emails = emails;
        this.phones = phones;
        this.autorized = autorized;

        this.wishList = wishList;
    }
}
