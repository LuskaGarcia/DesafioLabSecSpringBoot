package com.lucasgarcia.springdesafio.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.AuthorityDTO;
import com.lucasgarcia.springdesafio.services.AuthorityService;

import javassist.tools.rmi.ObjectNotFoundException;


@RestController
@RequestMapping(value="/authority")
public class AuthorityResource {

	
	@Autowired
	private AuthorityService service;

	@RequestMapping(value="/{id}",method=RequestMethod.GET) //requestparam
	public ResponseEntity<Authority> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Authority obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
//    @RequestMapping(method = RequestMethod.POST) 
//        // Produz JSON como retorno
//    public Authority create(@RequestBody Authority autoridade) throws Exception{
//    	Authority auth = AuthorityBuilder.build(false, null, autoridade, certificatesRepository, keysRepository);
//    	authorityRepository.saveAll(Arrays.asList(auth));
//    	return auth;
//    }
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createAuthority(@RequestBody AuthorityDTO authority) throws Exception{
		authority = service.createAuthority(authority);
	    if(authority != null) {
    	return new ResponseEntity<>(
    		"Authority created successfully", 
    		HttpStatus.OK);
    } else {
    	return new ResponseEntity<>(
        	"Authority could not be created", 
        	HttpStatus.BAD_REQUEST);
    }
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
//		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Authority obj, @PathVariable Integer id) throws ObjectNotFoundException{
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
    
}

//http://localhost:8080/meu-servico/authority?isRoot=true&x500name=CN=root-cert
//@GetMapping("/api/foos")
//@ResponseBody
//public String getFoos(@RequestParam String id) {
//    return "ID: " + id;
//}
//@GetMapping("/meu-servico/authority")
//@ResponseBody
//public String createAuthority(@RequestParam boolean isRoot, @RequestParam String x500name) {
//    //faz todo o trabalho
//}

//@GetMapping("/meu-servico/authority")
//public String createAuthority(@RequestBody AuthorityDTO authority) {
//    if(AuthorityProcessor.process(authority)) {
//    	return new ResponseEntity<>(
//    		"Authority created successfully", 
//    		HttpStatus.OK);
//    } else {
//    	return new ResponseEntity<>(
//        	"Authority could not be created", 
//        	HttpStatus.BAD_REQUEST);
//    }
//}
//public class AuthorityDTO{
//	private boolean isRoot;
//	private String x500name;
//
//	//construtores, getters e setters
//}
//{
//	"isRoot": true,
//	"x500name": "CN=cert-root"
//}