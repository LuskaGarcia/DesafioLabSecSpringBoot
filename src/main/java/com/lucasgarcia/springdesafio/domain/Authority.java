package com.lucasgarcia.springdesafio.domain;

import java.io.Serializable;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;

public class Authority implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private X500Name name;
	private CertificateBuilder authorityCert;
	private X509CertificateHolder issuerCert;
	private boolean isRootAuthority;

	public Authority(X500Name name, CertificateBuilder rootCert, X509CertificateHolder rootCert2) {
		this.name = name;
		this.authorityCert = rootCert;
		this.issuerCert = rootCert2;
		this.isRootAuthority = false;

	}

	public Authority(X500Name name, CertificateBuilder authorityCert, X509CertificateHolder issuerCert, boolean rootAuthority) {
		this.name = name;
		this.authorityCert = authorityCert;
		this.issuerCert = issuerCert;
		this.isRootAuthority = rootAuthority;
	}

	public X500Name getName() {
		return name;
	}

	public void setName(X500Name name) {
		this.name = name;
	}

	public CertificateBuilder getAuthorityCert() {
		return authorityCert;
	}

	public void setAuthorityCert(CertificateBuilder authorityCert) {
		this.authorityCert = authorityCert;
	}

	public X509CertificateHolder getIssuerCert() {
		return issuerCert;
	}

	public void setIssuerCert(X509CertificateHolder issuerCert) {
		this.issuerCert = issuerCert;
	}

	public boolean isRootAuthority() {
		return isRootAuthority;
	}

	public void setRootAuthority(boolean isRootAuthority) {
		this.isRootAuthority = isRootAuthority;
	}
	
	
}
