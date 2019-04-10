package com.mronfim.invoicer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mronfim.invoicer.exception.ResourceNotFoundException;
import com.mronfim.invoicer.model.Client;
import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ClientRepository clientRepository;
	
	// Gets all the clients associated with a company
	public List<Client> getClients(Long companyId) {
		userService.doesUserHaveCompany(companyId);
		return clientRepository.findByCompanyId(companyId);
	}
	
	public Client getClient(Long companyId, Long id) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return clientRepository.findById(id)
				.filter(client -> client.getCompany().equals(company))
				.map(client -> client)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id + " and company id " + companyId));
	}
	
	public Client createClient(Long companyId, Client client) {
		Company company = userService.doesUserHaveCompany(companyId);
		client.setCompany(company);
		return clientRepository.save(client);
	}
	
	public Client updateClient(Long companyId, Long id, Client clientRequest) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return clientRepository.findById(id)
				.filter(client -> client.getCompany().equals(company))
				.map(client -> {
					client.setFullname(clientRequest.getFullname());
					client.setEmail(clientRequest.getEmail());
					client.setActive(clientRequest.getActive());
					client.setAddress(clientRequest.getAddress());
					return clientRepository.save(client);
				}).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
	}
	
	// When deleting a client, the client still remains in the database and its
	// 'active' field is set to false. The client needs to remain in the database 
	// so that any estimates or invoices that references this client can still
	// access the client information. This was done so that by deleting a client,
	// the estimates and invoices referencing this client don't have to be deleted as well.
	//
	// TODO: Maybe delete a client from the database when nothing references to it?
	//		 or keep it this way and have a history of clients, so users can retrieve one if they wish.
	public ResponseEntity<?> deleteClient(Long companyId, Long id) {
		Company company = userService.doesUserHaveCompany(companyId);
		
		return clientRepository.findById(id)
				.filter(client -> client.getCompany().equals(company))
				.map(client -> {
					if (client.getActive()) {
						client.setActive(false);
						clientRepository.save(client);
					}
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id + " and company id " + companyId));
	}
}
