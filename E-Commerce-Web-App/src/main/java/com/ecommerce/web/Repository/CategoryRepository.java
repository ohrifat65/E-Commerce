package com.ecommerce.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.web.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
