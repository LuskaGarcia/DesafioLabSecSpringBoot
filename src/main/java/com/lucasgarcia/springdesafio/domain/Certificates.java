package com.lucasgarcia.springdesafio.domain;



	import java.io.Serializable;
	import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;

	@Entity
	public class Certificates implements Serializable{ 
		private static final long serialVersionUID = 1L;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private BigInteger serialNumber;
		private String emissor;
		private String receptor;

		
		public Certificates() {
			
			
		}
		
		//apenas armazenamento de dados

		public Certificates(Integer id, BigInteger serialNumber, String emissor, String receptor) {
			super();
			this.id = id;
			this.serialNumber = serialNumber;
			this.emissor = emissor;
			this.receptor = receptor;

		}

		public int getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public BigInteger getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(BigInteger serialNumber) {
			this.serialNumber = serialNumber;
		}

		public String getEmissor() {
			return emissor;
		}

		public void setEmissor(String emissor) {
			this.emissor = emissor;
		}

		public String getReceptor() {
			return receptor;
		}

		public void setReceptor(String receptor) {
			this.receptor = receptor;
		}
		
		
		
		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Certificates other = (Certificates) obj;
			return Objects.equals(id, other.id);
		}

		@Override
		public String toString() {
			return "Certificates [id=" + id + ", serialNumber=" + serialNumber + ", emissor=" + emissor + ", receptor="
					+ receptor + "]";
		}



}
