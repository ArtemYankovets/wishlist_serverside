package com.wishlist.serverside.persistance;

import com.wishlist.serverside.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

    User findById(String id);
}
