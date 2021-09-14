package com.lucasgarcia.springdesafio.domain;



import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;


	public class CertificateBuilder {
		
		private CertificatesRepository certificatesRepository;
	
		private X500Name certIssuer;
		private X500Name certSubject;
		@Column(length = 1000)
		private BigInteger serialNum;
		private Date startDate;
		private Date endDate;
		private PublicKey  pubKey;
		private PrivateKey privKey;
		private PublicKey  pubKey2;
		private PrivateKey privKey2;
		private String signatureAlgorithm;
		private String bcProvider;
		private boolean isRoot;
		
		public CertificateBuilder() {
			
			
		}
		
		public CertificateBuilder(X500Name certIssuer, X500Name certSubject, BigInteger serialNum, Date startDate,
				Date endDate, PublicKey pubKey, PrivateKey privKey,PublicKey pubKey2, PrivateKey privKey2, String signatureAlgorithm, String bcProvider, CertificatesRepository certificatesRepository, boolean isRoot) {
			super();
			this.certIssuer = certIssuer;
			this.certSubject = certSubject;
			this.serialNum = serialNum;
			this.startDate = startDate;
			this.endDate = endDate;
			this.pubKey = pubKey;
			this.privKey = privKey;
			this.pubKey2 = pubKey;
			this.privKey2 = privKey;
			this.signatureAlgorithm = signatureAlgorithm;
			this.bcProvider = bcProvider;
			this.certificatesRepository = certificatesRepository;
			this.isRoot = isRoot;
			

		}
		

		public X500Name getCertIssuer() {
			return certIssuer;
		}

		public void setCertIssuer(X500Name certIssuer) {
			this.certIssuer = certIssuer;
		}

		public X500Name getCertSubject() {
			return certSubject;
		}
		
		public void setCertSubject(X500Name certSubject) {
			this.certSubject = certSubject;
		}

		public BigInteger getSerialNum() {
			return serialNum;
		}

		public void setSerialNum(BigInteger serialNum) {
			this.serialNum = serialNum;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}


		public PublicKey getPubKey() {
			return pubKey;
		}

		public void setPubKey(PublicKey pubKey) {
			this.pubKey = pubKey;
		}
		
		
		 
		public boolean isRoot() {
			return isRoot;
		}

		public void setRoot(boolean isRoot) {
			this.isRoot = isRoot;
		}
		
		

		public PublicKey getPubKey2() {
			return pubKey2;
		}

		public void setPubKey2(PublicKey pubKey2) {
			this.pubKey2 = pubKey2;
		}

		public PrivateKey getPrivKey2() {
			return privKey2;
		}

		public void setPrivKey2(PrivateKey privKey2) {
			this.privKey2 = privKey2;
		}

		public X509Certificate X500Certificate() throws CertIOException, OperatorCreationException, NoSuchAlgorithmException, CertificateException, InvalidKeyException, NoSuchProviderException, SignatureException {
			
			if(isRoot == false) {
	        	 PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(certIssuer, pubKey2);
	             JcaContentSignerBuilder csrBuilder = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(bcProvider);
	     
	             // Sign the new KeyPair with the root cert Private Key
	              ContentSigner csrContentSigner = csrBuilder.build(privKey);
	              PKCS10CertificationRequest csr = p10Builder.build(csrContentSigner);
	              
	              X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(certIssuer, serialNum, startDate, endDate, csr.getSubject(), csr.getSubjectPublicKeyInfo());
	              
	  
	                       X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(csrContentSigner);
	                       X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(issuedCertHolder);
	              
	                      // Verify the issued cert signature against the root (issuer) cert
	                       issuedCert.verify(pubKey, bcProvider);
	                       
	                       
	       				Certificate certificates = new Certificate(2, serialNum.longValue(), certIssuer.toString(),certSubject.toString());
	    				
	    				this.certificatesRepository.saveAll(Arrays.asList(certificates));
	                       
	                       return issuedCert;
	              
	                      
	        }
			
			    ContentSigner rootCertContentSigner = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(bcProvider).build(privKey); //criando a assinatura 
		        X509v3CertificateBuilder rootCertBuilder = new JcaX509v3CertificateBuilder(certIssuer, serialNum, startDate, endDate, certSubject, pubKey);// cria o certificado com as caracteristicas
		        
		        // Add Extensions
		        // A BasicConstraint to mark root certificate as CA certificate
		        JcaX509ExtensionUtils rootCertExtUtils = new JcaX509ExtensionUtils(); // Crie uma classe de utilitário pré-configurada com uma calculadora de resumo SHA-1 com base na implementação padrão
		        rootCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)); //marca a extensão com critica
		        rootCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, rootCertExtUtils.createSubjectKeyIdentifier(pubKey));


		        
		        // Create a cert holder and export to X509Certificate
		        X509CertificateHolder rootCertHolder = rootCertBuilder.build(rootCertContentSigner); //recebe o criador de certificado com as caracteristicas e chama a variavel que é responsavel para assinar
		        X509Certificate certificate = new JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(rootCertHolder); // recebe o certificado
		        
   				Certificate certificates = new Certificate(2, serialNum.longValue(), certIssuer.toString(),certSubject.toString());
				
				this.certificatesRepository.saveAll(Arrays.asList(certificates));
		        
		        return certificate;
		        
		        



		       
		}
		
		
	}
