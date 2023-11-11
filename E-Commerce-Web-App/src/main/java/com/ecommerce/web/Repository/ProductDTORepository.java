package com.ecommerce.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.web.Dto.ProductDTO;

@Repository
public interface ProductDTORepository extends JpaRepository<ProductDTO, Integer> {

}
