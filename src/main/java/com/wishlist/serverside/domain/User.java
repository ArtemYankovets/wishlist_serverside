package com.wishlist.serverside.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private Address address;

    private List<String> emails;
    private List<String> phones;

    private boolean autorized;

    private List<User> friends;

    // only wishes id
    private List<String> wishList;

    public User() {
    }

    public User(String firstName, String lastName, Address address, List<String> emails, List<String> phones, boolean autorized, List<User> friends, List<String> wishList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emails = emails;
        this.phones = phones;
        this.autorized = autorized;
        this.friends = friends;
        this.wishList = wishList;
    }
}
