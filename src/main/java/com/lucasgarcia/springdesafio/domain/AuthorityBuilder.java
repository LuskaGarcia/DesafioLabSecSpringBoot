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

import com.lucasgarcia.springdesafio.domain.repositories.CertificatesRepository;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;

public class AuthorityBuilder implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private static final String BC_PROVIDER = "BC";
    //private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    
    	public static Authority build(boolean isRoot, String name, Authority issuer, CertificatesRepository certificatesRepository, KeysRepository keysRepository) throws Exception {
    		
    		Authority authority = generateAuthority( isRoot, name,issuer,certificatesRepository, keysRepository);

    		return authority;
    	}
    
    
	public static Authority generateAuthority(boolean isRoot, String name, Authority issuer,CertificatesRepository certificatesRepository, KeysRepository keysRepository) throws Exception{
        // Add the BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());
        KeyBuilder chaves = new KeyBuilder(keysRepository); // instancia a classe keys
        
        X500Name rootCertIssuer,rootCertSubject, issuedCertSubject;
        BigInteger rootSerialNum,issuedSerialNum;
        String issuerPem,issuedPem;
        
     	 Calendar calendar = Calendar.getInstance();
         calendar.add(Calendar.DATE, -1);
         Date startDate = calendar.getTime();

         calendar.add(Calendar.YEAR, 1);
         Date endDate = calendar.getTime();
        
        if(issuer == null) {

             // First step is to create a root certificate
             // First Generate a KeyPair,
             // then a random serial number
             // then generate a certificate using the KeyPair
             rootSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong())); //armazena o serialnumber que é um grande inteiro
             
             // Issued By and Issued To same for root certificate
             rootCertIssuer = new X500Name(name); //o x500name é uma classe entidade que tem suporte aos atributos do x500
             rootCertSubject = rootCertIssuer; // aqui define que é um AC auto assinado, pois diz que o issuer (emissor) é o mesmo que o subject (quem quer o certificado)
             CertificateBuilder rootCert = new CertificateBuilder(rootCertIssuer, rootCertSubject, rootSerialNum, startDate, endDate, chaves.getPublicKey(), chaves.getPrivateKey(),null,null, SIGNATURE_ALGORITHM, BC_PROVIDER,certificatesRepository,isRoot);
             issuerPem = Base64.getEncoder().encodeToString(rootCert.X500Certificate().getEncoded());
             
            // exportKeyPairToKeystoreFile(chaves.privateKey, rootCert, "root-cert", "root-cert.pfx", "PKCS12", "pass");
             writeCertToFileBase64Encoded(rootCert, "root-cert.cer");
             return new Authority(null,rootCertIssuer.toString(), issuerPem, rootCertSubject.toString(), isRoot);
        }else {

         // First step is to create a root certificate
         // First Generate a KeyPair,
         // then a random serial number
         // then generate a certificate using the KeyPair
         issuedSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong())); //armazena o serialnumber que é um grande inteiro
         
         // Issued By and Issued To same for root certificate
         issuedCertSubject = new X500Name(name); //o x500name é uma classe entidade que tem suporte aos atributos do x500
         CertificateBuilder issuedCert = new CertificateBuilder(issuedCertSubject,new X500Name(issuer.getName()) , issuedSerialNum, startDate, endDate, chaves.getPublicKey(), chaves.getPrivateKey(), chaves.getPublicKey2(), chaves.getPrivateKey2(), SIGNATURE_ALGORITHM, BC_PROVIDER, certificatesRepository, isRoot);
         issuedPem = Base64.getEncoder().encodeToString(issuedCert.X500Certificate().getEncoded());
         
        // exportKeyPairToKeystoreFile(chaves.privateKey, rootCert, "root-cert", "root-cert.pfx", "PKCS12", "pass");
         writeCertToFileBase64Encoded(issuedCert, "issued-cert.cer");
        	
         return new Authority(null,issuer.getName().toString(), issuedPem, issuedCertSubject.toString(), isRoot);
        }
        
       
        
        
        
        
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




