package br.com.spring.rejew.projectrejew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.spring.rejew.projectrejew.entity.Usuario;

/*
 * JpaRepository<Pais, Long>
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}