package com.lucasgarcia.springdesafio.domain;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AuthorityBuilder implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private static final String BC_PROVIDER = "BC";
    //private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    
    	public static Authority build(boolean isRoot, String name) throws Exception {
    		
    		Authority authority = generateAuthority( isRoot, name);

    		return authority;
    	}
    
    
	public static Authority generateAuthority(boolean isRoot, String name) throws Exception{
        // Add the BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());
        Keys chaves = new Keys(); // instancia a classe keys
        
        // Setup start date to yesterday and end date for 1 year validity
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.YEAR, 1);
        Date endDate = calendar.getTime();

        // First step is to create a root certificate
        // First Generate a KeyPair,
        // then a random serial number
        // then generate a certificate using the KeyPair
        BigInteger rootSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong())); //armazena o serialnumber que é um grande inteiro
        
        // Issued By and Issued To same for root certificate
        X500Name rootCertIssuer = new X500Name(name); //o x500name é uma classe entidade que tem suporte aos atributos do x500
        X500Name rootCertSubject = rootCertIssuer; // aqui define que é um AC auto assinado, pois diz que o issuer (emissor) é o mesmo que o subject (quem quer o certificado)
        CertificateBuilder rootCert = new CertificateBuilder(rootCertIssuer, rootCertSubject, rootSerialNum, startDate, endDate, chaves.publicKey, chaves.privateKey, SIGNATURE_ALGORITHM, BC_PROVIDER);
        String issuerPem = Base64.getEncoder().encodeToString(rootCert.X500Certificate().getEncoded());
        
       // exportKeyPairToKeystoreFile(chaves.privateKey, rootCert, "root-cert", "root-cert.pfx", "PKCS12", "pass");
        writeCertToFileBase64Encoded(rootCert, "root-cert.cer");
        
        
        return new Authority(null,name, issuerPem, null);
        
    }
	
//    static void exportKeyPairToKeystoreFile(PrivateKey privateKey, Certificate rootCert, String alias, String fileName, String storeType, String storePass) throws Exception {
//        KeyStore sslKeyStore = KeyStore.getInstance(storeType, BC_PROVIDER);
//       sslKeyStore.load(null, null);
//       sslKeyStore.setKeyEntry(alias, privateKey,null, new Certificate[]{rootCert});
//       FileOutputStream keyStoreOs = new FileOutputStream(fileName);
//       sslKeyStore.store(keyStoreOs, storePass.toCharArray());
//    }
	
    static Certificate writeCertToFileBase64Encoded(CertificateBuilder rootCert, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(rootCert.X500Certificate().getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
		return null;
    }
}
	
//        
//        writeCertToFileBase64Encoded(rootCert, "root-cert.cer");
//        
        //Até aqui é tudo direcionado a AC raiz autoassinado
        


        // Generate a new KeyPair and sign it using the Root Cert Private Key
        // by generating a CSR (Certificate Signing Request)
        //------------------------------------------------------
//          X500Name issuedCertSubject = new X500Name("CN=issued-cert");
//          BigInteger issuedCertSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong()));
//          
//
//        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(issuedCertSubject, chaves.publicKey2);
//        JcaContentSignerBuilder csrBuilder = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).setProvider(BC_PROVIDER);
//
//         Sign the new KeyPair with the root cert Private Key
//         ContentSigner csrContentSigner = csrBuilder.build(chaves.privateKey);
//         PKCS10CertificationRequest csr = p10Builder.build(csrContentSigner);
//
//         Use the Signed KeyPair and CSR to generate an issued Certificate
//         Here serial number is randomly generated. In general, CAs use
//         a sequence to generate Serial number and avoid collisions
//          X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(rootCertIssuer, issuedCertSerialNum, startDate, endDate, csr.getSubject(), csr.getSubjectPublicKeyInfo());
//
//        JcaX509ExtensionUtils issuedCertExtUtils = new JcaX509ExtensionUtils(); //Crie uma classe de utilitário pré-configurada com uma calculadora de resumo SHA-1 com base na implementação padrão
//				
        
//
//         Add Issuer cert identifier as Extension
//         issuedCertBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuedCertExtUtils.createAuthorityKeyIdentifier(rootCert));
//         issuedCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, issuedCertExtUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
//
//         Add intended key usage extension if needed
//         issuedCertBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyEncipherment));
//
//         Add DNS name is cert is to used for SSL
//        issuedCertBuilder.addExtension(Extension.subjectAlternativeName, false, new DERSequence(new ASN1Encodable[] {
//                new GeneralName(GeneralName.dNSName, "mydomain.local"),
//                new GeneralName(GeneralName.iPAddress, "127.0.0.1")
//        }));
//
//         X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(csrContentSigner);
//         X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider(BC_PROVIDER).getCertificate(issuedCertHolder);
//
//        Verify the issued cert signature against the root (issuer) cert
//        issuedCert.verify(chaves.publicKey, BC_PROVIDER);
//
//		writeCertToFileBase64Encoded(issuedCert, "issued-cert.cer");
//		exportKeyPairToKeystoreFile(chaves.privateKey2, issuedCert, "issued-cert", "issued-cert.pfx", "PKCS12", "pass");
        
     // INSERT
        
        
        //String subjectPem = Base64.getEncoder().encodeToString(issuedCert.getEncoded());
        
        //Certificates c1 = new Certificates(1, rootSerialNum, rootCertIssuer.toString(), rootCertSubject.toString(),issuerPem); // alt + shift + r  (renomeia)
        //Certificates c2 = new Certificates(2, issuedCertSerialNum, rootCertIssuer.toString(), issuedCertSubject.toString(),subjectPem);
        
       //certificatebuilder <- infos do certificado, chave publica do certificado e chave privada que vai assinar
       //AuthorityBuilder <- gera par de chaves, recebe os dados da autoridade
       // AuthorityBuilder chama o certificateBuilder 
       // Authority é o resultado do authoritybuilder
        




