package com.lucasgarcia.springdesafio.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasgarcia.springdesafio.domain.Certificate;

@Repository
public interface CertificatesRepository extends JpaRepository<Certificate, Integer>{
	
}

