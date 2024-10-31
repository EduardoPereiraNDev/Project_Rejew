package br.com.spring.rejew.projectrejew.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adm")
public class Adm {
    
    @Id
    private String credencial;
    
    private int senhaadm;

    // Getters e setters
    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public int getSenhaadm() {
        return senhaadm;
    }

    public void setSenhaadm(int senhaadm) {
        this.senhaadm = senhaadm;
    }
}
