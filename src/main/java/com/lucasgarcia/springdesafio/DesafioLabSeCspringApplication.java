package com.lucasgarcia.springdesafio;


import java.math.BigInteger;
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
			
			Authority rootAuthority = AuthorityBuilder.build(true, "CN=root-cert");			
			authorityRepository.saveAll(Arrays.asList(rootAuthority));		
			
			Certificates certificates = new Certificates(1, BigInteger.valueOf(10), "Abcd", "ABCDD");
			certificatesRepository.saveAll(Arrays.asList(certificates));
	
		}

}

