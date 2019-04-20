package com.org.lpro.boundary;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.lpro.entity.Sandwich;
import com.org.lpro.exception.BadRequest;
import com.org.lpro.exception.NotFound;

@RestController
@ExposesResourceFor(Sandwich.class)
public class SandwichRepresentation {

	private final SandwichRepository sr;
	
	public SandwichRepresentation(SandwichRepository sr) {
		super();
		this.sr = sr;
	}
	
	/**
	 * Get list of every sandwiches (bread type or price limit can bind results)
	 * 
	 * @param typePain
	 * @param tarif
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value="/sandwiches")
	public ResponseEntity<?> getSandwiches(@RequestParam(value="breadType",required=false) String typePain,@RequestParam(value="maxPrice",required=false) Integer tarif,@RequestParam(value="page",required=false) Integer page,@RequestParam(value="size",required=false) Integer size){
		Pageable pr = PageRequest.of(0, 10);
		Page<Sandwich> sandwiches;
		
		if(page!=null) {
			if(page>0) {				
				if(size!=null) {
					pr=PageRequest.of(page.intValue(), size.intValue());
				}else pr = PageRequest.of(page.intValue(), 10);
			}else {
				pr=PageRequest.of(0, size.intValue());
			}
		}else if(size!=null) {
			pr=PageRequest.of(0, size.intValue());
		}
		
		if(typePain!=null) {
			if(tarif!=null) {
				sandwiches = sr.findByTypepainAndTarifGreaterThan(typePain, tarif, pr);				
			}else {
				sandwiches = sr.findByTypepain(typePain, pr);				
			}
		}else {
			if(tarif!=null) {
				sandwiches = sr.findByTarifGreaterThan(tarif, pr);				
			}else {
				sandwiches = sr.findAll(pr);				
			}
		}
				
		return new ResponseEntity<>(sandwiches.getContent(),HttpStatus.OK);
	}
	
	/**
	 * Get sandwich by id
	 * @param id
	 * @return
	 */
	@GetMapping(value="sandwich/{sandwichId}")
	public ResponseEntity<?> getSandwichById(@PathVariable("sandwichId") Long id){		
		return Optional.ofNullable(sr.findById(id))
				.filter(Optional::isPresent)				
				.map(sandwich -> new ResponseEntity<>(createResponse(sandwich),HttpStatus.OK))
				.orElseThrow( () -> new BadRequest("Sandwich inexistante"));
	}
	
	/**
	 * Create response of getSandwichById
	 * 
	 * @param sandwich
	 * @return response
	 */
	private HashMap<String,Object> createResponse(Optional<Sandwich> sandwich){
		HashMap<String,Object> response = new HashMap<>();	
		HashMap<String,Object> links = new HashMap<>();
		links.put("sandwiches", linkTo(methodOn(SandwichRepresentation.class).getSandwiches(null,null,null,null)).toUri().toString());
		links.put("self", linkTo(methodOn(SandwichRepresentation.class).getSandwichById(sandwich.get().getId())).toUri().toString());
		
		response.put("type","resource");
		response.put("sandwich", sandwich.get());
		response.put("links", links);
		return response;
	}

	/**
	 *  Create sandwich
	 *  
	 * @param sandwich
	 * @return
	 */
	@PostMapping(value="/sandwich")
    public ResponseEntity<?> postMethod(@RequestBody Sandwich sandwich) {		
        Sandwich saved = sr.save(sandwich);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
	
	/**
	 * Modify sandwich by Id
	 * 
	 * @param id
	 * @param SandwichUpdated
	 * @return
	 * @throws NotFound
	 * @throws BadRequest
	 */
	@PutMapping(value = "sandwich/{sandwichId}")
    public ResponseEntity<?> putMethod(@PathVariable("sandwichId") Long id,
            @RequestBody Sandwich SandwichUpdated) throws NotFound,BadRequest {
        return sr.findById(id)
                .map(Sandwich -> {
                	if(SandwichUpdated.getDesc()==null || SandwichUpdated.getNom()==null) {
                		throw new BadRequest("Tous les paramètres doivent avoir une valeur");
                	}
                	
                	Sandwich.setNom(SandwichUpdated.getNom());
                	Sandwich.setDesc(SandwichUpdated.getDesc());                    
                    sr.save(Sandwich);
                    return new ResponseEntity<>(Sandwich,HttpStatus.OK);          
                }).orElseThrow(() -> new NotFound("Sandwich inexistant"));
    }


	/**
	 * Delete sandwich by id
	 * 
	 * @param id
	 * @return
	 * @throws NotFound
	 */
    @DeleteMapping(value = "sandwich/{sandwichId}")
    public ResponseEntity<?> deleteMethod(@PathVariable("sandwichId") Long id) throws NotFound {
        return sr.findById(id)
                .map(Sandwich -> {
                    sr.delete(Sandwich);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow(() -> new NotFound("Sandwich inexistant"));
    }
}