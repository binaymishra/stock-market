package com.stockmarket;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Binay Mishra
 *
 */
@Entity
public class Stock {
	
	@Id
	private Long id;
	private String name;
	private Double price;
	
	public Stock() {
		// Default constructor for JPA
	}
	
	public Stock(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Stock(Long id, String name, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
}
