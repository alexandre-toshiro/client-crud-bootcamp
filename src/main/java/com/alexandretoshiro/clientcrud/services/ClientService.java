package com.alexandretoshiro.clientcrud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexandretoshiro.clientcrud.dto.ClientDTO;
import com.alexandretoshiro.clientcrud.entities.Client;
import com.alexandretoshiro.clientcrud.repositories.ClientRepository;
import com.alexandretoshiro.clientcrud.services.exceptions.ClientNotFoundException;
import com.alexandretoshiro.clientcrud.services.exceptions.DatabaseException;

@RestController
@RequestMapping(value = "/clients")
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	//Getall
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clientList = clientRepository.findAll(pageRequest);
		return clientList.map(client -> new ClientDTO(client));
	}

	//get
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		Client client = obj.orElseThrow(() -> new ClientNotFoundException("Client not found"));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		convertDtoToEntity(entity, dto);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = clientRepository.getOne(id);
			convertDtoToEntity(entity, dto);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ClientNotFoundException("ID NOT FOUND: " + id);
		}
	}
	
	
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ClientNotFoundException("ID NOT FOUND: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("INTEGRITY VIOLATION");
		}
	}

	private void convertDtoToEntity(Client entity, ClientDTO dto) {
		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

}
