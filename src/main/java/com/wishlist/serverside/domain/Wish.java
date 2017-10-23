package com.wishlist.serverside.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Wishes")
public class Wish {

    @Id
    private String id;

    private String name;
    private boolean done;

    // only users id who use current wish
    private List<String> userUsageRreferences;

    public Wish() {
    }

    public Wish(String name, boolean done, List<String> userUsageRreferences) {

        this.name = name;
        this.done = done;
        this.userUsageRreferences = userUsageRreferences;
    }

    public String getId() {
        return id;
    }


    public boolean isDone() {
        return done;
    }

    public List<String> getUserUsageRreferences() {
        return userUsageRreferences;
    }
}
