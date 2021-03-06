package com.lucasgarcia.springdesafio.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasgarcia.springdesafio.domain.Certificate;
import com.lucasgarcia.springdesafio.services.CertificatesService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/certificados")
public class CertificatesResource {
	
	@Autowired
	private CertificatesService service;

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Certificate obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
