package com.mronfim.invoicer.model;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class ClientBase extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@NotNull
	protected String fullname;
	
	protected String email;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	protected Address address;
	
	public ClientBase() {}
	public ClientBase(String fullname, String email) {
		this.fullname = fullname;
		this.email = email;
	}
	
	public Long getId() { return id; }
	
	public String getFullname() { return fullname; }
	public void setFullname(String fullname) { this.fullname = fullname; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
}
