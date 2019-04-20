package com.org.lpro.boundary;

import java.util.Optional;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.org.lpro.entity.Sandwich;
import com.org.lpro.exception.NotFound;

@RestController
@ExposesResourceFor(Sandwich.class)
public class CategoryRepresentation {
	
	private final CategoryRepository cr;
	private final SandwichRepository sr;
	
	public CategoryRepresentation(CategoryRepository cr,SandwichRepository sr) {
		super();
		this.cr = cr;		
		this.sr=sr;
	}
	
	/**
	 * Get every Category
	 * @return
	 */
	@GetMapping(value="categories")
	public ResponseEntity<?> getEveryCategories(){		
		return new ResponseEntity<>(cr.findAll(),HttpStatus.OK);		
	}
	
	/**
	 * Get description of a Category
	 * @param id
	 * @return
	 */
	@GetMapping(value="category/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long id){
		return Optional.ofNullable(cr.findById(id))
				.filter(Optional::isPresent)				
				.map(category -> new ResponseEntity<>(category,HttpStatus.OK))
				.orElseThrow( () -> new NotFound("Categorie inexistante"));
	}	
	
	/**
	 * Get description of a Category
	 * @param id
	 * @return
	 */
	@GetMapping(value="category/{categoryId}/sandwichs")
	public ResponseEntity<?> getSandwichesByCategoryId(@PathVariable("categoryId") Long id){
		return Optional.ofNullable(sr.findByCategories_id(id))
				.filter(Optional::isPresent)
				.map(sandwiches -> new ResponseEntity<>(sandwiches,HttpStatus.OK))
				.orElseThrow( () -> new NotFound("Aucun sandwich pour cette catégorie"));
	}	
}