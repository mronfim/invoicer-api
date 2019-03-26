package com.mronfim.invoicer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mronfim.invoicer.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
