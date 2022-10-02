package com.duongtai.estore.entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name ="Category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="category_id")
	private Long id;
	
	@Column(name ="category_name", length=255)
	private String name;
	
	@Column(length = 2000)
	private String details;
	
	private String created_at;
	
	private String created_by;
	
	private String last_edited;

	@ManyToOne
	@JoinColumn(name = "image", referencedColumnName = "image_id")
	private Image image;
	
	@OneToMany(targetEntity = Product.class, mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Product> products;

	public Category() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getLast_edited() {
		return last_edited;
	}

	public void setLast_edited(String last_edited) {
		this.last_edited = last_edited;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
