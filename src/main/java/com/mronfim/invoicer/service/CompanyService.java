package com.mronfim.invoicer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.mronfim.invoicer.exception.ResourceNotFoundException;
import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.CompanyRepository;
import com.mronfim.invoicer.repository.UserAccountRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserService userService;
	
	public List<Company> getCompanies() {
		UserAccount user = userService.getCurrentUser();
		return companyRepository.findByUserId(user.getId());
	}
	
	public Company getCompany(Long id) {
		UserAccount user = userService.getCurrentUser();
		return companyRepository.findByUserIdAndId(user.getId(), id)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
	
	public Company createCompany(Company company) {
		UserAccount user = userService.getCurrentUser();
		company.setUser(user);
		return companyRepository.save(company);
	}
	
	public Company updateCompany(Long id, Company companyRequest) {
		UserAccount user = userService.getCurrentUser();
		return companyRepository.findByUserIdAndId(user.getId(), id)
				.map(company -> {
					company.setName(companyRequest.getName());
					company.setAddress(companyRequest.getAddress());
					return companyRepository.save(company);
				}).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
	
	public ResponseEntity<?> deleteCompany(Long id) {
		UserAccount user = userService.getCurrentUser();
		return companyRepository.findByUserIdAndId(user.getId(), id)
				.map(company -> {
					companyRepository.delete(company);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
}
