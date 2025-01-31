package com.ecommerce.homeproducts.repo;

import com.ecommerce.homeproducts.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Products,Integer> {
}
