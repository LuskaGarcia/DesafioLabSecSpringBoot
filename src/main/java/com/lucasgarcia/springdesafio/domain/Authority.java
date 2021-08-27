package com.lucasgarcia.springdesafio.domain;

import org.bouncycastle.asn1.x500.X500Name;

public class Authority {
	public X500Name rootName;
	public AuthorityBuilder acRaiz;
	
	public Authority() {
		this.rootName = new X500Name("CN=rootCert");
		this.acRaiz = new AuthorityBuilder(rootName);
	}
	
}
