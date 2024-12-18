package br.com.spring.rejew.projectrejew.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.spring.rejew.projectrejew.entity.Livro;

/*
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	@Query("SELECT l FROM Livro l WHERE l.nomeLivro LIKE %:nome%")
	List<Livro> findByNome(String nome);
	
	@Query("SELECT l FROM Livro l WHERE l.autorLivro LIKE %:autor%")
	List<Livro> findByAutor(String autor);
}