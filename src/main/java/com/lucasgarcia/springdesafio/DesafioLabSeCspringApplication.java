package com.lucasgarcia.springdesafio;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.AuthorityBuilder;
import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;




@SpringBootApplication
public class DesafioLabSeCspringApplication implements CommandLineRunner {
		
	@Autowired
	private  AuthorityRepository authorityRepository;

	@Autowired
	private  CertificatesRepository certificatesRepository;

	@Autowired
	private  KeysRepository keysRepository;	
	
	
		public static void main(String[] args) {
			SpringApplication.run(DesafioLabSeCspringApplication.class, args);
			
		}
		
		@Override
		public void run(String... args) throws Exception {
			
			
			//endpoint de configuracao passando os dados da acraiz
			// setar a ac
			//apartir disso posso criar ac intermediarias (parametrizado)
			
			Authority rootAuthority = AuthorityBuilder.build(true, "CN=root-cert",null,certificatesRepository, keysRepository);	
			authorityRepository.saveAll(Arrays.asList(rootAuthority));		
			
			Authority intermediateAuthority = AuthorityBuilder.build(false, "CN=subject-cert", rootAuthority, certificatesRepository, keysRepository);			
			authorityRepository.saveAll(Arrays.asList(intermediateAuthority));	
			
			Authority authority2 = AuthorityBuilder.build(true, "CN=seconde-root-cert",null,certificatesRepository, keysRepository);	
			authorityRepository.saveAll(Arrays.asList(authority2));		
			
			Authority intermediateAuthorityTwo = AuthorityBuilder.build(false, "CN=subject-cert-second", rootAuthority, certificatesRepository, keysRepository);			
			authorityRepository.saveAll(Arrays.asList(intermediateAuthorityTwo));	
			
			Authority intermediateAuthorityThree = AuthorityBuilder.build(false, "CN=subject-cert-three", authority2, certificatesRepository, keysRepository);			
			authorityRepository.saveAll(Arrays.asList(intermediateAuthorityThree));	
			
		}
}

