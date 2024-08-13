package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

	@Entity
	@Data
	public class Genero implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id_Genero;
	    private String genero;
	    
}
