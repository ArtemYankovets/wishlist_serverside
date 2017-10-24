package com.wishlist.serverside.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String city;
    private String country;
}
