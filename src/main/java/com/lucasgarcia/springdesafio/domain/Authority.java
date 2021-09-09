package com.lucasgarcia.springdesafio.domain;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Authority implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(length = 1000)
	private String authorityCert; //fazer em formato de lista depois
	private String issuerCert; //fazer em formato de lista depois transformar em uma string 
	private boolean isRootAuthority;

	public Authority(Integer id, String name, String authorityCert, String issuerCert) {
		this.id = id;
		this.name = name;
		this.authorityCert = authorityCert;
		this.issuerCert = issuerCert;
		this.isRootAuthority = false; 	

	}

	public Authority(Integer id, String name, String authorityCert, String issuerCert, boolean rootAuthority) {
		this.id = id;
		this.name = name;
		this.authorityCert = authorityCert;
		this.issuerCert = issuerCert;
		this.isRootAuthority = rootAuthority;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorityCert() {
		return authorityCert;
	}

	public void setAuthorityCert(String authorityCert) {
		this.authorityCert = authorityCert;
	}

	public String getIssuerCert() {
		return issuerCert;
	}

	public void setIssuerCert(String issuerCert) {
		this.issuerCert = issuerCert;
	}

	public boolean isRootAuthority() {
		return isRootAuthority;
	}

	public void setRootAuthority(boolean isRootAuthority) {
		this.isRootAuthority = isRootAuthority;
	}
	
	@Override
	public String toString() {
		return "Certificates [id=" + id + ", certificateAuthority=" + authorityCert + ", emissor=" + name + ", certificateIssuer="
				+ issuerCert + "]";
	}
	
	
}
