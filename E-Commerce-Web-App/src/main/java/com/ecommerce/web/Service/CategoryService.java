package com.ecommerce.web.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.web.Model.Category;
import com.ecommerce.web.Repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepo;

	public List<Category> getAllCategory() {
		return categoryRepo.findAll();
	}

	public Optional<Category> getCategoryById(int id) {
		return categoryRepo.findById(id);
	}

}
