package com.ecommerce.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.web.Model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
	List<Products> findAllByCategoryId(int Id);
}
