package com.duongtai.estore.entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Vendor")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="vendor_id")
	private Long id;
	
	@Column(name="vendor_name", length = 255)
	private String name;
	
	private String created_at;
	
	private String last_edited;
	
	private String created_by;

	@ManyToOne
	@JoinColumn(name = "image", referencedColumnName = "image_id")
	private Image image;
	
	@OneToMany(targetEntity = Product.class, mappedBy = "vendor", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Product> products;

	
	public Vendor() {
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

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getLast_edited() {
		return last_edited;
	}

	public void setLast_edited(String last_edited) {
		this.last_edited = last_edited;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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
