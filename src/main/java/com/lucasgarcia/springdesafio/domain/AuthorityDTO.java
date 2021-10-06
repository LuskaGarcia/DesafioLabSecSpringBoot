package com.lucasgarcia.springdesafio.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthorityDTO {
	
	
	boolean isRoot;
	String name;
	int issuerId;
	Authority issuer;
	
	public AuthorityDTO() {

	}
	

	public void printDTO(){
		System.out.println(this.isRoot);
		System.out.println(this.name);
		System.out.println(this.issuerId);
	}


	public AuthorityDTO(boolean isRoot, String name, int issuerId) {
		this.isRoot = isRoot;
		this.name = name;
		this.issuerId = issuerId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(int issuerId) {
		this.issuerId = issuerId;
	}

	@JsonProperty("isRoot")
	public boolean isRoot() {
		return isRoot;
	}
	
	@JsonProperty("isRoot")
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}



	public Authority getIssuer() {
		return issuer;
	}



	public void setIssuer(Authority issuer) {
		this.issuer = issuer;
	}


	}
	
