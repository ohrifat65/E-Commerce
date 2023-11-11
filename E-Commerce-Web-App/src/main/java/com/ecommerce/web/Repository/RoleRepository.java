package com.ecommerce.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.web.UserEntity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
