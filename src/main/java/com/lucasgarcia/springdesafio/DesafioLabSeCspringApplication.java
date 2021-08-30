package com.lucasgarcia.springdesafio;

import java.util.Arrays;

import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasgarcia.springdesafio.domain.Authority;
import com.lucasgarcia.springdesafio.domain.AuthorityBuilder;
import com.lucasgarcia.springdesafio.domain.repositories.AuthorityRepository;


@SpringBootApplication
public class DesafioLabSeCspringApplication implements CommandLineRunner {

		X500Name name = new X500Name("CN=root-cert");
	
		@Autowired
		private AuthorityRepository authorityRepository;
		
		//private AuthorityRepository authorityRepository;
		
		public static void main(String[] args) {
			SpringApplication.run(DesafioLabSeCspringApplication.class, args);
		}

		@Override
		public void run(String... args) throws Exception {
		
			Authority rootAuthority = AuthorityBuilder.build(true, name);
		
			authorityRepository.saveAll(Arrays.asList(rootAuthority));
}
		

}

