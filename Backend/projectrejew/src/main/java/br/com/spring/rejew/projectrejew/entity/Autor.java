package br.com.spring.rejew.projectrejew.entity;

/*
public class Autor extends Usuario{
	
    private int qtdLivros;

    public Autor() {}

    public int getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(int qtdLivros) {
        this.qtdLivros = qtdLivros;
    }
    
}
*/

import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@Data
public class Autor extends Usuario  {

    private static final long serialVersionUID = 1L;
    
	private int qtdLivros;
    
    
   
}