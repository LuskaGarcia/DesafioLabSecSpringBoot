package com.lucasgarcia.springdesafio.domain;



	import java.io.Serializable;
	
	import java.util.Objects;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;


	@Entity
	public class Certificate implements Serializable{ 
		private static final long serialVersionUID = 1L;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@Column(length = 1000)
		private java.lang.Long serialNumber;
		private String emissor;
		private String receptor;

		
		public Certificate() {
			
			
		}
		
		//apenas armazenamento de dados

		public Certificate(Integer id, java.lang.Long serialNumber, String emissor, String receptor) {
			super();
			this.id = id;
			this.serialNumber = serialNumber;
			this.emissor = emissor;
			this.receptor = receptor;
			//armazenar APENAS certificado
			// pra passar pra outra tabela, apenas o referencial id

		}

		public int getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public java.lang.Long getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(java.lang.Long serialNumber) {
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
			Certificate other = (Certificate) obj;
			return Objects.equals(id, other.id);
		}

		@Override
		public String toString() {
			return "Certificates [id=" + id + ", serialNumber=" + serialNumber + ", emissor=" + emissor + ", receptor="
					+ receptor + "]";
		}



}
