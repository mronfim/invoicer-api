package com.mronfim.invoicer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.service.CompanyService;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping("/companies")
	public List<Company> getCompanies() {
		return companyService.getCompanies();
	}
	
	@GetMapping("/companies/{id}")
	public Company getCompany(@PathVariable Long id) {
		return companyService.getCompany(id);
	}
	
	@PostMapping("/companies")
	public Company createCompany(@Valid @RequestBody Company company) {
		return companyService.createCompany(company);
	}
	
	@PutMapping("/companies/{id}")
	public Company updateCompany(@PathVariable Long id, @Valid @RequestBody Company company) {
		return companyService.updateCompany(id, company);
	}
	
	@DeleteMapping("/companies/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
		return companyService.deleteCompany(id);
	}
}
