package com.wishlist.serverside.persistance;

import com.wishlist.serverside.domain.Wish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends MongoRepository<Wish, String>{
}
