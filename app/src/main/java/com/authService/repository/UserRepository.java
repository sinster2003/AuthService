package com.authService.repository;

import com.authService.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* The UserRepository class interacts with the UserInfo entity to perform crud operations on it */

@Repository // bean + database related logic
public interface UserRepository extends CrudRepository<UserInfo, String> {
    // under the hood the spring-data-jpa & hibernate orm does the sql queries
    Optional<UserInfo> findByUsername(String username);
}
