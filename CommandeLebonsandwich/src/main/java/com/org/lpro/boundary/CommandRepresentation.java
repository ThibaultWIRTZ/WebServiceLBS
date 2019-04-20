package com.org.lpro.boundary;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.lpro.entity.Command;
import com.org.lpro.exception.BadRequest;
import com.org.lpro.exception.NotFound;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@ExposesResourceFor(Command.class)
public class CommandRepresentation {

	private final CommandRepository cr;
	private final ItemRepository ir;
	
	public CommandRepresentation(CommandRepository cr, ItemRepository ir) {
		super();
		this.cr = cr;
		this.ir = ir;
	}

	@GetMapping(value="/command/{idCommand}")
	public ResponseEntity<?> getCommandById(@RequestHeader(value="X-lbs-token", required=false) String xlbstoken ,@RequestParam(value="token",required=false) String token ,@PathVariable(value="idCommande") String id){
		if(( token!=null && !token.isEmpty() ) || ( xlbstoken!=null && !xlbstoken.isEmpty() )) {
			return Optional.ofNullable(cr.findById(id))
                .filter(Optional::isPresent)                
                .map(commande -> new ResponseEntity<>(commandByIdResponse(commande,token,xlbstoken),HttpStatus.OK))
                .orElseThrow(() -> new BadRequest("La commande n'existe pas"));
		}else {
			throw new BadRequest("Token manquant");
		}		
	}
	
	@PutMapping(value="/command/{idCommand}/modification")
	public ResponseEntity<?> modifyCommandById(@RequestHeader(value="X-lbs-token", required=false) String xlbstoken ,@RequestParam(value="token",required=false) String token ,@PathVariable(value="idCommande") String id,@RequestBody Command CommandeUpdated){
		if(( token!=null && !token.isEmpty() ) || ( xlbstoken!=null && !xlbstoken.isEmpty() )) {
			return cr.findById(id)
	                .map(Commande -> {	                	
	                	Commande=CommandeUpdated;
	                	cr.save(Commande);
	                    return new ResponseEntity<>(Commande,HttpStatus.OK);          
	                }).orElseThrow(() -> new NotFound("commande inexistant"));
		}else {
			throw new BadRequest("Token manquant");
		}		
	}
	
	@PutMapping(value="/command/{idCommand}/changeState")
	public ResponseEntity<?> changeState(@RequestHeader(value="X-lbs-token", required=false) String xlbstoken ,@RequestParam(value="token",required=false) String token ,@PathVariable(value="idCommande") String id,@RequestBody Integer state){
		if(( token!=null && !token.isEmpty() ) || ( xlbstoken!=null && !xlbstoken.isEmpty() )) {
			return cr.findById(id)
	                .map(Commande -> {	                	
	                	Commande.setStatus(state);;
	                	cr.save(Commande);
	                    return new ResponseEntity<>(Commande,HttpStatus.OK);          
	                }).orElseThrow(() -> new NotFound("commande inexistant"));
		}else {
			throw new BadRequest("Token manquant");
		}		
	}
	
	public HashMap<String,Object> commandByIdResponse(Optional<Command> commande,String token,String xlbstoken){
		Command cmd = commande.get();
		HashMap<String,Object> response = new HashMap<String,Object>();		
		HashMap<String,Object> links = new HashMap<String,Object>();
		
		//Verify if tokens are identical
		if(token==cmd.getToken()) {
			links.put("self", linkTo(methodOn(this.getClass()).getCommandById(null,cmd.getToken(),cmd.getId())).toUri());
			links.put("items", linkTo(methodOn(this.getClass()).getCommandById(null,cmd.getToken(),cmd.getId())).toUri()+"/items/");		
		}else if(xlbstoken==cmd.getToken()) {
			links.put("self", linkTo(methodOn(this.getClass()).getCommandById(cmd.getToken(),null,cmd.getId())).toUri());
			links.put("items", linkTo(methodOn(this.getClass()).getCommandById(cmd.getToken(),null,cmd.getId())).toUri()+"/items/");
		}else throw new BadRequest("Le token donné n'est pas bon");
		
		cmd.setItems(ir.findByCommandid(cmd.getId()));
		
		response.put("type", "resource");
		response.put("links",links);
		response.put("command",cmd);  				
    
		return response;
	}
	
	@GetMapping(value="/commands")
	public ResponseEntity<?> getCommandsCollection(@RequestParam(value="s",required=false) Integer status,@RequestParam(value="page",required=false) Integer page,@RequestParam(value="size",required=false) Integer size){		
		Page<Command> collection;
		Pageable pr = PageRequest.of(0, 10);
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
		
		if(status!=null) {			
			collection = cr.findByStatus(status,pr);
		}else{
			collection = cr.findAllByOrderByLivraisonAscCreatedatAsc(pr);
		}			
		
		HashMap<String,Object> links = new HashMap<>();
		if(collection.hasNext()) {
			links.put("next",linkTo(methodOn(CommandRepresentation.class).getCommandsCollection(status,collection.nextPageable().getPageNumber(),size)).toUri().toString());
		}
		if(collection.hasPrevious()) {
			links.put("previous",linkTo(methodOn(CommandRepresentation.class).getCommandsCollection(status,collection.previousPageable().getPageNumber(),size)).toUri().toString());
		}
		links.put("first",linkTo(methodOn(CommandRepresentation.class).getCommandsCollection(status,1,size)).toUri().toString());
		links.put("last",linkTo(methodOn(CommandRepresentation.class).getCommandsCollection(status,collection.getTotalPages(),size)).toUri().toString());
		
		
		HashMap<String,Object> response = new HashMap<>();
		response.put("count", cr.count());
		response.put("type", "collection");		
		response.put("size", pr.getPageSize());
		response.put("links",links);
		response.put("commands", collection.getContent());
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	private Resources<Resource<Command>> Command2Resource(Iterable<Command> Commands) {
        Link selfLink = linkTo(CommandRepresentation.class).withSelfRel();
        List<Resource<Command>> CommandResources = new ArrayList<>();
        Commands.forEach(Command
                -> CommandResources.add(CommandToResource(Command, false)));
        return new Resources<>(CommandResources, selfLink);
    }
    
    private Resource<Command> CommandToResource(Command Command, Boolean collection) {
        Link selfLink = linkTo(CommandRepresentation.class)
                .slash(Command.getId())
                .withSelfRel();
        if (collection) {
        Link collectionLink = linkTo(CommandRepresentation.class).withRel("collection");
        return new Resource<>(Command, selfLink, collectionLink);
    } else {
            return new Resource<>(Command, selfLink);
        }
    }
    
    
    @PostMapping(value="/command/creation")
    public ResponseEntity<?> postMethod(@RequestBody Command commande) {
    	String jwtToken;
    	
    	//Set UUID
    	commande.setId(UUID.randomUUID().toString());
    	
    	//Set Token
        jwtToken = Jwts.builder().setSubject(commande.getId()).claim("roles", "commande").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();               
        commande.setToken(jwtToken);
        

        //Setup created_at
        SimpleDateFormat format = new SimpleDateFormat("Y-M-d H:m");
        String now = format.format(new Date());        
        commande.setCreatedat(now);
        
        commande.setStatus(1);
        
        //Save in repo
        Command saved = cr.save(commande);        
        
        //Set location to header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(CommandRepresentation.class).slash(saved.getId()).toUri());
        
        return new ResponseEntity<>(saved,responseHeaders, HttpStatus.CREATED);
    }
}