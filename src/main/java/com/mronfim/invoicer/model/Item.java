package com.mronfim.invoicer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private double price;
	
	public Item() {}
	public Item(String description, double price) {
		this.description = description;
		this.price = price;
	}
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
}
