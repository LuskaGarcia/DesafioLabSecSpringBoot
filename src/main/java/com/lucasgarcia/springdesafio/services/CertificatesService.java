package com.lucasgarcia.springdesafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasgarcia.springdesafio.domain.Certificate;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CertificatesService {

	@Autowired
	private CertificatesRepository repo;
	
	public Certificate find(Integer id) throws ObjectNotFoundException { 
		 Optional<Certificate> obj = repo.findById(id); //retorna o objeto completo
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Certificate.class.getName())); 
		}
	
}
