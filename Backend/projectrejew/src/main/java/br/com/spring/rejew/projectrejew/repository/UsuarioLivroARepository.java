package br.com.spring.rejew.projectrejew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.spring.rejew.projectrejew.entity.UsuarioLivroA;

/*
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */

@Repository
public interface UsuarioLivroARepository extends JpaRepository<UsuarioLivroA, Long> {
	
	 @Query("SELECT u.NotaLivro FROM UsuarioLivroA u WHERE u.livroA.isbnLivro = :isbnLivro")
	    List<Double> findNotasByLivroIsbn(@Param("isbn") Long isbnLivro);
	 
	 @Query("SELECT u FROM UsuarioLivroA u WHERE u.livroA.isbnLivro = :isbnLivro AND u.usuarioA.emailEntrada = :emailEntrada") 
	    UsuarioLivroA findJaAvaliado(@Param("isbn") Long isbnLivro, String emailEntrada);

}
