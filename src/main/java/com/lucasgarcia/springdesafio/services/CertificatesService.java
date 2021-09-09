package com.lucasgarcia.springdesafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasgarcia.springdesafio.domain.Certificates;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CertificatesService {

	@Autowired
	private CertificatesRepository repo;
	
	public Certificates find(Integer id) throws ObjectNotFoundException { 
		 Optional<Certificates> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Certificates.class.getName())); 
		}
	
}
