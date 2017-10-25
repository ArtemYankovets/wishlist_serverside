package com.wishlist.serverside.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "wishlists")
public class WishList {
    private String id;
    private String name;

    // As value UserId
    private String owner;

    // As value WishId
    private List<String> wishes;
}
