package com.mronfim.invoicer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mronfim.invoicer.exception.ResourceNotFoundException;
import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> getCompanies() {
		return companyRepository.findAll();
	}
	
	public Company getCompany(Long id) {
		return companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
	
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}
	
	public Company updateCompany(Long id, Company companyRequest) {
		return companyRepository.findById(id)
				.map(company -> {
					company.setName(companyRequest.getName());
					company.setAddress(companyRequest.getAddress());
					return companyRepository.save(company);
				}).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
	
	public ResponseEntity<?> deleteCompany(Long id) {
		return companyRepository.findById(id)
				.map(company -> {
					companyRepository.delete(company);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
}
