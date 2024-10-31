package br.com.spring.rejew.projectrejew.repository;

import br.com.spring.rejew.projectrejew.entity.Adm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmRepository extends JpaRepository<Adm, String> {
    Optional<Adm> findByCredencialAndSenhaadm(String credencial, int senhaadm);
}
