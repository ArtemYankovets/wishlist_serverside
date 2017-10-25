package com.wishlist.serverside.persistance;

import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends MongoRepository<WishList, String>{
    WishList findById(String id);

    List<User> findByOwner(String userId);
}
