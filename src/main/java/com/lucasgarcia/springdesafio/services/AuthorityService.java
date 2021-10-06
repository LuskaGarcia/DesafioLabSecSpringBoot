package com.lucasgarcia.springdesafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.AuthorityDTO;
import com.lucasgarcia.springdesafio.domain.AuthorityProcessor;
import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;
import com.lucasgarcia.springdesafio.services.exception.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository repoAuth;
	
	@Autowired
	private CertificatesRepository repoCert;
	
	@Autowired
	private KeysRepository repoKey;
	
	public Authority find(Integer id) throws ObjectNotFoundException { 
		 Optional<Authority> obj = repoAuth.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Authority.class.getName())); 
		}
	public AuthorityDTO createAuthority(AuthorityDTO obj) throws Exception {
		if(obj.isRoot()) {
			AuthorityProcessor authorityProcessor = new AuthorityProcessor();
			authorityProcessor.process(obj,repoAuth,repoCert,repoKey);
			return obj;
		}else {	
		Authority issuer = find(obj.getIssuerId());
		obj.setIssuer(issuer);
		AuthorityProcessor authorityProcessor = new AuthorityProcessor();
		authorityProcessor.process(obj,repoAuth,repoCert,repoKey);
		return obj;
		}

	}
	
	public Authority update(Authority obj) throws ObjectNotFoundException {
		find(obj.getId());
		return repoAuth.save(obj);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repoAuth.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos!");
		}

	}
	
}
