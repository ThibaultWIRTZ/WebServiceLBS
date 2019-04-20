package com.org.lpro.boundary;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.org.lpro.entity.Command;

public interface CommandRepository extends CrudRepository<Command,String>{		
	public Page<Command> findAllByOrderByLivraisonAscCreatedatAsc(Pageable p);
	public Page<Command> findByStatus(int status,Pageable p);	
	public Optional<Command> findById(String id);
}