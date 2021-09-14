package com.lucasgarcia.springdesafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasgarcia.springdesafio.domain.Keys;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class KeysService {

	@Autowired
	private KeysRepository repo;
	
	public Keys find(Integer id) throws ObjectNotFoundException { 
		 Optional<Keys> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Keys.class.getName())); 
		}
	
}
