package com.duongtai.estore.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import static com.duongtai.estore.configs.MyUserDetail.getUsernameLogin;

import java.util.Collection;
import java.util.Collections;import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.duongtai.estore.configs.CustomAccessDeniedHandler;
import com.duongtai.estore.configs.Snippets;
import com.duongtai.estore.entities.Category;
import com.duongtai.estore.entities.ConvertEntity;
import com.duongtai.estore.entities.Image;
import com.duongtai.estore.entities.Product;
import com.duongtai.estore.entities.ResponseObject;
import com.duongtai.estore.entities.User;
import com.duongtai.estore.entities.UserDTO;
import com.duongtai.estore.entities.Vendor;
import com.duongtai.estore.services.impl.CategoryServiceImpl;
import com.duongtai.estore.services.impl.ImageServiceImpl;
import com.duongtai.estore.services.impl.OrderServiceImpl;
import com.duongtai.estore.services.impl.ProductServiceImpl;
import com.duongtai.estore.services.impl.StorageServiceImpl;
import com.duongtai.estore.services.impl.UserServiceImpl;
import com.duongtai.estore.services.impl.VendorServiceImpl;

@RestController
@RequestMapping("/master")
public class AdminController {
	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired 
	private OrderServiceImpl orderService;
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@Autowired
	private VendorServiceImpl vendorService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private StorageServiceImpl storageService;
	
	@Autowired
	private ImageServiceImpl imageService;

	@PostMapping("save_vendor")
	public ResponseEntity<ResponseObject> save_vendor(@RequestBody Vendor vendor){

		if(vendor != null){
			if(!vendorService.isExistByName(vendor.getName())){
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, String.format(Snippets.SAVE_SUCCESS,Snippets.VENDOR), vendorService.saveVendor(vendor))
				);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject(Snippets.FAILED, Snippets.VENDOR_EXIST, null)
			);
		}

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED, Snippets.HAVE_ERROR, null)
		);
	}

	@PostMapping("save_category")
	public ResponseEntity<ResponseObject> save_category(@RequestBody Category category){
		if(category != null){
			if(!categoryService.isExistByName(category.getName())){
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, String.format(Snippets.SAVE_SUCCESS,Snippets.CATEGORY), categoryService.saveCategory(category))
				);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject(Snippets.FAILED, Snippets.CATEGORY_EXIST, null)
			);
		}

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED, Snippets.HAVE_ERROR, null)
		);
	}

	@PostMapping("save_product")
	public ResponseEntity<ResponseObject> save_product(@RequestBody Product product){
		if(product != null){
			if(!productService.isExistByName(product.getName())){
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, String.format(Snippets.SAVE_SUCCESS,Snippets.PRODUCT), productService.saveProduct(product))
				);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject(Snippets.FAILED, Snippets.PRODUCT_EXIST, null)
			);
		}

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED, Snippets.HAVE_ERROR, null)
		);
	}

	@PutMapping("edit_product/{id}")
	public ResponseEntity<ResponseObject> edit_product(@RequestBody Product product, @PathVariable Long id){

			if(product.getName() != null &&
					!product.getName().trim().
							equalsIgnoreCase(productService.findProductById(id).getName().trim())){
				product.setId(id);
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, String.format(Snippets.UPDATED_SUCCESS,Snippets.PRODUCT), productService.editProductById(product))
				);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject(Snippets.FAILED, Snippets.PRODUCT_EXIST, null)
			);

	}

	@PutMapping("edit_category/{id}")
	public ResponseEntity<ResponseObject> edit_category(@RequestBody Category category, @PathVariable Long id){
			if(category.getName() != null &&
				!category.getName().trim().equalsIgnoreCase(categoryService.findCategoryById(id).getName().trim())){
				category.setId(id);
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, String.format(Snippets.UPDATED_SUCCESS,Snippets.PRODUCT), categoryService.editCategoryById(category))
				);
			}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED, Snippets.CATEGORY_EXIST, null)
		);
	}

	@PutMapping("edit_vendor/{id}")
	public ResponseEntity<ResponseObject> edit_vendor(@RequestBody Vendor vendor, @PathVariable Long id){
		if(vendor.getName() != null &&
			!vendor.getName().trim().equalsIgnoreCase((vendorService.findVendorById(id).getName().trim()))){
			vendor.setId(id);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, String.format(Snippets.UPDATED_SUCCESS,Snippets.VENDOR), vendorService.editVendorById(vendor))
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED, Snippets.VENDOR_EXIST, null)
		);
	}

	@DeleteMapping("delete_vendor/{id}")
	public ResponseEntity<ResponseObject> delete_vendor(@PathVariable Long id){
		if(vendorService.findVendorById(id)!= null){
			vendorService.deleteVendorById(id);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, String.format(Snippets.DELETED_SUCCESS,Snippets.VENDOR), null)
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject(Snippets.FAILED, String.format(Snippets.NOT_FOUND,Snippets.VENDOR,id), null)
		);
	}
	@DeleteMapping("delete_category/{id}")
	public ResponseEntity<ResponseObject> delete_category(@PathVariable Long id){
		if(categoryService.findCategoryById(id)!= null){
			categoryService.deleteCategoryById(id);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, String.format(Snippets.DELETED_SUCCESS,Snippets.CATEGORY), null)
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject(Snippets.FAILED, String.format(Snippets.NOT_FOUND,Snippets.CATEGORY,id), null)
		);
	}
	@DeleteMapping("delete_product/{id}")
	public ResponseEntity<ResponseObject> delete_product(@PathVariable Long id){
		if(productService.findProductById(id)!= null){
			productService.deleteProductById(id);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, String.format(Snippets.DELETED_SUCCESS,Snippets.PRODUCT), null)
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject(Snippets.FAILED, String.format(Snippets.NOT_FOUND,Snippets.PRODUCT,id), null)
		);
	}

	@PutMapping("/update_role/{username}")
	public ResponseEntity<ResponseObject> changeRole(@PathVariable String username, @RequestBody User user){
		user.setUsername(username);
		if(userService.findByUsername(username) != null) {
			UserDTO userDTO = ConvertEntity.convertToDTO(userService.changeRoleUser(user));
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, Snippets.USER_EDITED, userDTO)
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED,Snippets.YOU_DONT_HAVE_PERMISSION, null));
	}

	@PutMapping("/update_active/{username}")
	public ResponseEntity<ResponseObject> changeActive(@PathVariable String username, @RequestBody User user){
		user.setUsername(username);
		if(userService.findByUsername(username) != null) {
			UserDTO userDTO = ConvertEntity.convertToDTO(userService.changeActiveUser(user));
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, Snippets.USER_EDITED, userDTO)
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject(Snippets.FAILED,Snippets.YOU_DONT_HAVE_PERMISSION, null));
	}

	@GetMapping("/all_orders")
	public ResponseEntity<ResponseObject> getAllOrders(){
		if(orderService.findAllOrder() != null){
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(Snippets.SUCCESS, Snippets.FOUNDED, orderService.findAllOrder())
			);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
				new ResponseObject(Snippets.SUCCESS, Snippets.NOT_PERMISSION, null)
		);
	}
}
