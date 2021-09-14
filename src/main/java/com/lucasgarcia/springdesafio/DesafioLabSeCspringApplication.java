package com.lucasgarcia.springdesafio;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.AuthorityBuilder;
import com.lucasgarcia.springdesafio.domain.Certificates;
import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;
import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;


@SpringBootApplication
public class DesafioLabSeCspringApplication implements CommandLineRunner {
	

		@Autowired
		private  AuthorityRepository authorityRepository;
		
		@Autowired
		private  CertificatesRepository certificatesRepository;
		
		
		public static void main(String[] args) {
			SpringApplication.run(DesafioLabSeCspringApplication.class, args);
			
		}
		
		@Override
		public void run(String... args) throws Exception {
			
			
		   //CertificateBuilder builder = new CertificateBuilder(certificatesRepository);
			
			
			Authority rootAuthority = AuthorityBuilder.build(true, "CN=root-cert",null,certificatesRepository);			
			authorityRepository.saveAll(Arrays.asList(rootAuthority));		
			
			Authority intermediateAuthority = AuthorityBuilder.build(false, "CN=subject-cert", rootAuthority, certificatesRepository);			
			authorityRepository.saveAll(Arrays.asList(intermediateAuthority));	
			
			Certificates certificates = new Certificates(1, java.lang.Long.valueOf(10), "Abcd", "ABCDD");
			certificatesRepository.saveAll(Arrays.asList(certificates));
			
	
		}

}

