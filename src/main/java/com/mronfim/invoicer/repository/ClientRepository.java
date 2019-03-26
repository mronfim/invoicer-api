package com.mronfim.invoicer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mronfim.invoicer.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	public List<Client> findByCompanyId(Long companyId);
}
