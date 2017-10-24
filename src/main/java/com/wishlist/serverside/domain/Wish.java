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

    // only users id who use current wish
    private List<String> userUsageRreferences;
}
