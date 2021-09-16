package com.lucasgarcia.springdesafio.domain;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Properties;

import com.lucasgarcia.springdesafio.DesafioLabSeCspringApplication;
import com.lucasgarcia.springdesafio.domain.repositories.KeysRepository;

public class KeysBuilder {

	private Integer id;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private PublicKey publicKey2;
	private PrivateKey privateKey2;

	private KeysRepository keysRepository;

	public static Properties getProp() throws IOException {
		Properties props = new Properties(); // Instancia a classes properties na variavel props
		InputStream file = DesafioLabSeCspringApplication.class.getResourceAsStream("/dados.properties"); // adiciona o
																											// arquivo a
																											// variavel
		props.load(file); // carrega o arquivo de propriedades na variavel
		return props;

	}

	public KeysBuilder(KeysRepository keysRepository) throws NoSuchAlgorithmException, IOException {

		this.keysRepository = keysRepository;

		String bits;

		Properties prop = getProp(); // instanciando o props
		bits = prop.getProperty("prop.choice"); // cria a variavel e chama a propriedade do arquivo

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");// gera as chaves em RSA
		generator.initialize(Integer.parseInt(bits, 10));// inicializa em 1024bits e converse a string em int

		KeyPair keyPair = generator.generateKeyPair();// gera o par de chaves e coloca no keypair
		this.publicKey = keyPair.getPublic();// extraio
		this.privateKey = keyPair.getPrivate();// extraio

		Keys keysRoot = new Keys(null, publicKey.getEncoded(), privateKey.getEncoded());

		this.keysRepository.saveAll(Arrays.asList(keysRoot));

		KeyPair keyPair2 = generator.generateKeyPair();
		this.publicKey2 = keyPair2.getPublic();
		this.privateKey2 = keyPair2.getPrivate();

		Keys keysIssued = new Keys(null, publicKey2.getEncoded(), privateKey2.getEncoded());

		this.keysRepository.saveAll(Arrays.asList(keysIssued));

		// tabela com referencia

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey2() {
		return publicKey2;
	}

	public void setPublicKey2(PublicKey publicKey2) {
		this.publicKey2 = publicKey2;
	}

	public PrivateKey getPrivateKey2() {
		return privateKey2;
	}

	public void setPrivateKey2(PrivateKey privateKey2) {
		this.privateKey2 = privateKey2;
	}

}
