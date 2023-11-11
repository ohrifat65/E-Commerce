package com.ecommerce.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.web.UserEntity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
