package com.wishlist.serverside.persistance;

import com.wishlist.serverside.domain.User;
import com.wishlist.serverside.domain.Wish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends MongoRepository<Wish, String>{
    Wish findById(String id);

    List<User> findByUserUsageId(String userId);
}
