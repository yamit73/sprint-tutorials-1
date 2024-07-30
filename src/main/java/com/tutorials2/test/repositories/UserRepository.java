package com.tutorials2.test.repositories;

import com.tutorials2.test.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

}
