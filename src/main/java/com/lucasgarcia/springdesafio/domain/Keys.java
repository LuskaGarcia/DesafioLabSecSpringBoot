package com.lucasgarcia.springdesafio.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Keys implements Serializable{ 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 10000)
	private byte[] publicKey;
	@Column(length = 10000)
	private byte[] privateKey;
	
	
	public Keys(Integer id, byte[] publicKey, byte[] privateKey) {
		super();
		this.id = id;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public byte[] getPublicKey() {
		return publicKey;
	}


	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}


	public byte[] getPrivateKey() {
		return privateKey;
	}


	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	} 





}


