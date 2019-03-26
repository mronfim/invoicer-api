package com.mronfim.invoicer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "estimate_clients")
public class EstimateClient extends ClientBase {

	public EstimateClient() {}
	public EstimateClient(String fullname, String email) {
		super(fullname, email);
	}
}
