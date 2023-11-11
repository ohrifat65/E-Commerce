package com.ecommerce.web;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.web.Controller.AdminController;
import com.ecommerce.web.Repository.*;
import com.ecommerce.web.UserEntity.*;

import Configuration.SecurityConfig;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
//@Slf4j
public class EcommerceWebAppApplication {
	private final Logger logger = (Logger) LoggerFactory.getLogger(AdminController.class);
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceWebAppApplication.class, args);
	}

	@PostConstruct
	public void initData() {
		logger.info("************ Starting Spring Security project ***********");
		Role role = new Role();
		role.setName("ADMIN");
		role = roleRepo.save(role);
		User user = new User();
		user.setUsername("admin");
		user.setPassword(SecurityConfig.passwordEncoder().encode("admin"));
		user.setRoles(Collections.singleton(role));
		userRepo.save(user);
	}
}