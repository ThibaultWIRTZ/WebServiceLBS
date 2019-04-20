package com.org.lpro.boundary;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.org.lpro.entity.Sandwich;

public interface SandwichRepository extends CrudRepository<Sandwich,Long>{	
	public Page<Sandwich> findAll(Pageable p);
	public Page<Sandwich> findByTypepain(String typePain, Pageable p);
	public Page<Sandwich> findByTarifGreaterThan(Integer tarif, Pageable p);
	public Page<Sandwich> findByTypepainAndTarifGreaterThan(String typePain, Integer tarif, Pageable p);
	public Optional<Iterable<Sandwich>> findByCategories_id(Long id);	
}