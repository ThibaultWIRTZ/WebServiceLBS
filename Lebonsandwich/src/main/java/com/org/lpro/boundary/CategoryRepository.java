package com.org.lpro.boundary;

import org.springframework.data.repository.CrudRepository;

import com.org.lpro.entity.Category;

public interface CategoryRepository extends CrudRepository<Category,Long>{
	
}