package com.wishlist.serverside.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestApiController {

    @RequestMapping( )
    public ResponseEntity test(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
