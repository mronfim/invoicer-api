package com.mronfim.invoicer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "clients")
public class Client extends ClientBase {
	
	@Column(nullable = false)
	private boolean active = true;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	@JsonBackReference
	private Company company;
	
	public Client() {}
	public Client(String fullname, String email) {
		super(fullname, email);
	}
	
	public boolean getActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	
	public Company getCompany() { return company; }
	public void setCompany(Company company) { this.company = company; }
}
