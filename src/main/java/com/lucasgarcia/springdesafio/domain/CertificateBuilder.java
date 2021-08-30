package com.lucasgarcia.springdesafio.domain;


import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

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


	public class CertificateBuilder implements Serializable{

		private static final long serialVersionUID = 1L;
	
		private X500Name certIssuer;
		private X500Name certSubject;
		private BigInteger serialNum;
		private Date startDate;
		private Date endDate;
		private PublicKey  pubKey;
		private PrivateKey privKey;
		private String signatureAlgorithm;
		private String bcProvider;
		
		public CertificateBuilder() {
			
			
		}
		
		public CertificateBuilder(X500Name certIssuer, X500Name certSubject, BigInteger serialNum, Date startDate,
				Date endDate, PublicKey pubKey, PrivateKey privKey, String signatureAlgorithm, String bcProvider) {
			super();
			this.certIssuer = certIssuer;
			this.certSubject = certSubject;
			this.serialNum = serialNum;
			this.startDate = startDate;
			this.endDate = endDate;
			this.pubKey = pubKey;
			this.privKey = privKey;
			this.signatureAlgorithm = signatureAlgorithm;
			this.bcProvider = bcProvider;
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
		 
		public X509Certificate X500Certificate() throws CertIOException, OperatorCreationException, NoSuchAlgorithmException, CertificateException {
			
			    ContentSigner rootCertContentSigner = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(bcProvider).build(privKey); //criando a assinatura 
		        X509v3CertificateBuilder rootCertBuilder = new JcaX509v3CertificateBuilder(certIssuer, serialNum, startDate, endDate, certSubject, pubKey);// cria o certificado com as caracteristicas
		        
		        // Add Extensions
		        // A BasicConstraint to mark root certificate as CA certificate
		        JcaX509ExtensionUtils rootCertExtUtils = new JcaX509ExtensionUtils(); // Crie uma classe de utilitário pré-configurada com uma calculadora de resumo SHA-1 com base na implementação padrão
		        rootCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)); //marca a extensão com critica
		        rootCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, rootCertExtUtils.createSubjectKeyIdentifier(pubKey));

		        // Create a cert holder and export to X509Certificate
		        X509CertificateHolder rootCertHolder = rootCertBuilder.build(rootCertContentSigner); //recebe o criador de certificado com as caracteristicas e chama a variavel que é responsavel para assinar
		        X509Certificate rootCert = new JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(rootCertHolder); // recebe o certificado
		        
		        // Add Extensions
		        // A BasicConstraint to mark root certificate as CA certificate
		        JcaX509ExtensionUtils rootCertExtUtils1 = new JcaX509ExtensionUtils(); // Crie uma classe de utilitário pré-configurada com uma calculadora de resumo SHA-1 com base na implementação padrão
		        rootCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)); //marca a extensão com critica
		        rootCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, rootCertExtUtils1.createSubjectKeyIdentifier(pubKey));

		        return rootCert;
		}

		public byte[] getEncoded() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
