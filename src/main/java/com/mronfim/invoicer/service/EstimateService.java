package com.mronfim.invoicer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mronfim.invoicer.exception.ResourceNotFoundException;
import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.model.Estimate;
import com.mronfim.invoicer.repository.EstimateRepository;

@Service
public class EstimateService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EstimateRepository estimateRepository;
	
	// Gets all the estimates associated with a company
	public List<Estimate> getEstimates(Long companyId) {
		userService.doesUserHaveCompany(companyId);
		return estimateRepository.findByCompanyId(companyId);
	}
	
	public Estimate getEstimate(Long companyId, Long id) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return estimateRepository.findById(id)
				.filter(estimate -> estimate.getCompany().equals(company))
				.map(estimate -> estimate)
				.orElseThrow(() -> new ResourceNotFoundException("Estimate not found with id " + id + " and company id " + companyId));
	}
	
	public Estimate createEstimate(Long companyId, Estimate estimate) {
		Company company = userService.doesUserHaveCompany(companyId);
		estimate.setCompany(company);
		estimate.setEstimateNumber(company.incrementEstimateCount());
		return estimateRepository.save(estimate);
	}
	
	public Estimate updateEstimate(Long companyId, Long id, Estimate estimateRequest) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return estimateRepository.findById(id)
				.filter(estimate -> estimate.getCompany().equals(company))
				.map(estimate -> {
					estimate.setTitle(estimateRequest.getTitle());
					estimate.setClient(estimateRequest.getClient());
					estimate.setItems(estimateRequest.getItems());
					return estimateRepository.save(estimate);
				}).orElseThrow(() -> new ResourceNotFoundException("Estimate not found with id " + id + " and company id " + id));
	}
	
	public ResponseEntity<?> deleteEstimate(Long companyId, Long id) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return estimateRepository.findById(id)
				.filter(estimate -> estimate.getCompany().equals(company))
				.map(estimate -> {
					estimateRepository.delete(estimate);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Estimate not found with id " + id + " and company id " + companyId));
	}
}
