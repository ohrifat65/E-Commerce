package com.ecommerce.web.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.web.Dto.ProductDTO;
import com.ecommerce.web.Model.Category;
import com.ecommerce.web.Model.Products;
import com.ecommerce.web.Repository.CategoryRepository;
import com.ecommerce.web.Repository.ProductDTORepository;
import com.ecommerce.web.Repository.ProductsRepository;
import com.ecommerce.web.Service.CategoryService;
import com.ecommerce.web.Service.ProductService;

@Controller
public class AdminController {

	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	ProductsRepository productsRepo;
	@Autowired
	ProductService pService;
	@Autowired
	CategoryService cService;
	@Autowired
	Products products;

	@Autowired
	ProductDTORepository productDTORepo;

	@GetMapping("/admin")
	public String start() {
		return "adminHome";
	}

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	// **************************This is Category Section***********************

	// Find all Category and Display categories.html page
	@GetMapping("/admin/categories")
	public String categories(Model model) {
		model.addAttribute("ctg", cService.getAllCategory());
		return "categories";
	}

	// Open addCategory.html and binding Category Model with addCategory page
	@GetMapping("/admin/add-category")
	public String AddCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addCategory";
	}

	// Add/save Category from Database
	@PostMapping("/admin/add-category")
	public String saveCategory(@ModelAttribute("category") Category category) {
		categoryRepo.save(category);
		return "redirect:/admin/add-category";
	}

	// Open updateCategoryPage.html
	@GetMapping("/admin/update-category/{Id}")
	public String updatePage(@PathVariable int Id, Model model) {
		model.addAttribute("category", categoryRepo.findById(Id));
		return "updateCategoryPage";
	}

	// Update category
	@PostMapping("/admin/update-category")
	public String updateCategory(@ModelAttribute Category category) {
		Category ctr = categoryRepo.getById(category.getId());
		ctr.setId(category.getId());
		ctr.setCategoryName(category.getCategoryName());
		categoryRepo.save(ctr);
		return "redirect:/admin/categories";
	}

	// Delete By Id Category
	@GetMapping("/admin/delete-category/{Id}")
	public String deleteCategory(@PathVariable int Id) {
		categoryRepo.deleteById(Id);
		return "redirect:/admin/categories";
	}

	// ********************** This is products Section************************
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("product", pService.getAllProducts());
		return "products";
	}

	@GetMapping("/admin/addProducts")
	public String productAddGet(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryRepo.findAll());
		return "addProducts";
	}

	// Insert new Product
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDto,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws IOException {

		Products product = new Products();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setCategory(cService.getCategoryById(productDto.getCategoryId()).get());
		product.setPrice(productDto.getPrice());
		product.setWeight(productDto.getWeight());
		product.setDescription(productDto.getDescription());

		String imageUUID;

		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());

		} else {
			imageUUID = imgName;
		}

		product.setImageName(imageUUID);
		pService.addProduct(product);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/login")
	public String s() {
		return "login";
	}

	// Update Product
	@GetMapping("/admin/products/update/{Id}")
	public String getUpdateProduct(@PathVariable int Id, Model model) {
		Products product = productsRepo.getById(Id);
		ProductDTO productDto = new ProductDTO();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setPrice(product.getPrice());
		productDto.setWeight(product.getWeight());
		productDto.setDescription(product.getDescription());
		productDto.setImageName(product.getImageName());
		model.addAttribute("productDTO", productDto);
		model.addAttribute("categories", cService.getAllCategory());
		return "addProducts";
	}

	@GetMapping("/admin/products/delete/{Id}")
	public String deleteProduct(@PathVariable int Id) {
		productsRepo.deleteById(Id);
		return "redirect:/admin/products";
	}
}
