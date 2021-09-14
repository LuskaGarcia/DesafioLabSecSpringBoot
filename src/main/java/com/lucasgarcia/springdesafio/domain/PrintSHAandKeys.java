package com.lucasgarcia.springdesafio.domain;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.boot.SpringApplication;

import com.lucasgarcia.springdesafio.DesafioLabSeCspringApplication;

public class PrintSHAandKeys {
	public static void main(String[] args) throws Exception{
	SpringApplication.run(DesafioLabSeCspringApplication.class, args);
	MessageDigest algorithm = MessageDigest.getInstance("SHA-256");//declara que o resumo sera em sha256
	//String senha = "admin"; 	
	InputStream is = DesafioLabSeCspringApplication.class.getResourceAsStream("/docdecod.txt"); //inicia o inputstream que armazena o arquivo
	byte[] buffer = new byte[8192]; // armazena o tamanho limite do arquivo
	int read = 0; 
	try {
		while( (read = is.read(buffer)) > 0) { //lendo o arquivo com o tamanho do buffer
			algorithm.update(buffer, 0, read);//vai fazendo a atualização do algorithm
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	byte[] sha = algorithm.digest(); //resumo de algorithm
	BigInteger bigInt = new BigInteger(1, sha);
	String output = bigInt.toString(16);
	System.out.println("SHA-256: " + output);

	//byte messageDigest[] = algorithm.digest(doc.getBytes("UTF-8"));
}
}
