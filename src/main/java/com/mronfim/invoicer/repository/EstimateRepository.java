package com.mronfim.invoicer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mronfim.invoicer.model.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
	
	public List<Estimate> findByCompanyId(Long companyId);
}
