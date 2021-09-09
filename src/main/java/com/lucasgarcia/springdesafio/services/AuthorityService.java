package com.lucasgarcia.springdesafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository repo;
	
	public Authority find(Integer id) throws ObjectNotFoundException { 
		 Optional<Authority> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Authority.class.getName())); 
		}
	
}
