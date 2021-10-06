package com.lucasgarcia.springdesafio.domain;

import java.util.Arrays;


import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;

public class AuthorityProcessor {

	public Authority process(AuthorityDTO authority, AuthorityRepository authRepo, CertificatesRepository certRepo, KeysRepository keysRepo) throws Exception {
		if(authority != null) {
			authority.printDTO();
	Authority auth = AuthorityBuilder.build(authority.isRoot(), authority.getName(),authority.getIssuer(),certRepo,keysRepo);	
	authRepo.saveAll(Arrays.asList(auth)); 
	return auth;
		}else {
			return null;
		}
	}
}
