package com.duongtai.syndiary.services;

import java.util.List;

public interface ProductService {
	
	Product saveProduct(Product product);
	
	Product editProductById(Product product);
	
	void deleteProductById(Long id);
	
	List<Product> findProductByName(String name);
	
	List<Product> findProductByCategory(Category category);

	List<Product> findProductByVendor(Vendor vendor);
	
	List<Product> findAllProduct();
	
	Product findProductById(Long id);
	
	boolean isExistByName(String name);
}
