package com.org.lpro.boundary;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.org.lpro.entity.Command;
import com.org.lpro.entity.Item;

public interface ItemRepository extends CrudRepository<Item,String>{		
	public List<Item> findByCommandid(String cmdid);	
}