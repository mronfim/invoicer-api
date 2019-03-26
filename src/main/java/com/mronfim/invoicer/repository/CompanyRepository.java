package com.mronfim.invoicer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mronfim.invoicer.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
