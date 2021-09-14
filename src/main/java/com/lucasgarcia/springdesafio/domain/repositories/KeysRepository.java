package com.lucasgarcia.springdesafio.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasgarcia.springdesafio.domain.Keys;

@Repository
public interface KeysRepository extends JpaRepository<Keys, Integer>{
	
	
}

