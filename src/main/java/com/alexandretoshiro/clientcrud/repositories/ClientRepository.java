package com.alexandretoshiro.clientcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandretoshiro.clientcrud.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
