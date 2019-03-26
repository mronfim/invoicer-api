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

import com.mronfim.invoicer.model.Client;
import com.mronfim.invoicer.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping("/companies/{companyId}/clients")
	public List<Client> getClients(@PathVariable Long companyId) {
		return clientService.getClients(companyId);
	}
	
	@GetMapping("/companies/{companyId}/clients/{id}")
	public Client getClient(@PathVariable Long companyId, @PathVariable Long id) {
		return clientService.getClient(companyId, id);
	}
	
	@PostMapping("/companies/{companyId}/clients")
	public Client createClient(@PathVariable Long companyId, @Valid @RequestBody Client client) {
		return clientService.createClient(companyId, client);
	}
	
	@PutMapping("/companies/{companyId}/clients/{id}")
	public Client createClient(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody Client client) {
		return clientService.updateClient(companyId, id, client);
	}
	
	@DeleteMapping("/companies/{companyId}/clients/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long companyId, @PathVariable Long id) {
		return clientService.deleteClient(companyId, id);
	}
}
