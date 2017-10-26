package com.wishlist.serverside.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "Wishes")
public class Wish {

    @Id
    private String id;

    private String name;
    private boolean done;

    // wishList id where is current wish
    private String wishListUsageId;

    // TODO Don't know what is it
    private List<String> subpoint;
}
