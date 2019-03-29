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

import com.mronfim.invoicer.model.Estimate;
import com.mronfim.invoicer.service.EstimateService;
import com.mronfim.invoicer.dto.EstimateListDto;

@RestController
public class EstimateController {

	@Autowired
	private EstimateService estimateService;
	
	@GetMapping("/companies/{companyId}/estimates")
	public EstimateListDto getEstimates(@PathVariable Long companyId) {
		return new EstimateListDto(estimateService.getEstimates(companyId));
	}
	
	@GetMapping("/companies/{companyId}/estimates/{id}")
	public Estimate getEstimate(@PathVariable Long companyId, @PathVariable Long id) {
		return estimateService.getEstimate(companyId, id);
	}
	
	@PostMapping("/companies/{companyId}/estimates")
	public Estimate createEstimate(@PathVariable Long companyId, @Valid @RequestBody Estimate estimate) {
		return estimateService.createEstimate(companyId, estimate);
	}
	
	@PutMapping("/companies/{companyId}/estimates/{id}")
	public Estimate createClient(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody Estimate estimate) {
		return estimateService.updateEstimate(companyId, id, estimate);
	}
	
	@DeleteMapping("/companies/{companyId}/estimates/{id}")
	public ResponseEntity<?> deleteEstimate(@PathVariable Long companyId, @PathVariable Long id) {
		return estimateService.deleteEstimate(companyId, id);
	}
}
