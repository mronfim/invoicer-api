package com.mronfim.invoicer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mronfim.invoicer.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    public List<Company> findByUserId(UUID userId);
    public Optional<Company> findByUserIdAndId(UUID userId, Long id);
}
