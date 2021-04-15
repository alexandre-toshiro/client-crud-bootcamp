package com.alexandretoshiro.clientcrud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexandretoshiro.clientcrud.dto.ClientDTO;
import com.alexandretoshiro.clientcrud.entities.Client;
import com.alexandretoshiro.clientcrud.repositories.ClientRepository;

@RestController
@RequestMapping(value = "/clients")
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	//Getall
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> clientList = clientRepository.findAll(pageRequest);
		return clientList.map(client -> new ClientDTO(client));
	}
	
	//get
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id){
		Optional<Client> obj = clientRepository.findById(id);
		Client client = obj.orElseThrow(() -> new ClientNotFoundException("Client not found"));
		return new ClientDTO(client);
	}

}
