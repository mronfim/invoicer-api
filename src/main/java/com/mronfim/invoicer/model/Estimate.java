package com.mronfim.invoicer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "estimates")
public class Estimate extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private int estimateNumber;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	@JsonBackReference
	private Company company;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "estimate_client_id")
	private EstimateClient client;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private List<Item> items;
	
	public Estimate() {}
	public Estimate(String title, int number) {
		this.title = title;
		this.estimateNumber = number;
	}
	
	public Long getId() { return id; }
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public int getEstimateNumber() { return estimateNumber; }
	public void setEstimateNumber(int number) { estimateNumber = number; }
	
	public Company getCompany() { return company; }
	public void setCompany(Company company) { this.company = company; }
	
	public EstimateClient getClient() { return client; }
	public void setClient(EstimateClient client) { this.client = client; }
	
	public List<Item> getItems() { return items; }
	public void setItems(List<Item> items) { this.items = items; }
	public void addItem(Item item) {
		items.add(item);
	}
}
