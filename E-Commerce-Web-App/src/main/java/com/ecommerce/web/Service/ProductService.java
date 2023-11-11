package com.ecommerce.web.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.web.Model.Products;
import com.ecommerce.web.Repository.ProductsRepository;

@Service
public class ProductService {

	@Autowired
	ProductsRepository pRepo;

	public void addProduct(Products product) {
		pRepo.save(product);
	}

	public List<Products> getAllProducts() {
		return pRepo.findAll();
	}

	public void removeProductById(int Id) {
		pRepo.deleteById(Id);
	}

	public Optional<Products> getProductById(int Id) {
		return pRepo.findById(Id);
	}

	public List<Products> getAllProductsByCategoryId(int Id) {
		return pRepo.findAllByCategoryId(Id);

	}

}
