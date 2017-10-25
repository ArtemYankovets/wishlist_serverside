package com.wishlist.serverside.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
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

    // only wishlist id
    private List<String> wishListIds;
}
