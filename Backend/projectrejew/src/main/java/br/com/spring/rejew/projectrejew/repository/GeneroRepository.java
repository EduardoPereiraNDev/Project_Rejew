package br.com.spring.rejew.projectrejew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.spring.rejew.projectrejew.entity.Genero;

/*
 * JpaRepository<Pais, Long>
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */
@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
	@Query("SELECT g FROM Genero g WHERE g.generoLivro LIKE %:genero%")
    List<Genero> findByGenero(String genero);
}