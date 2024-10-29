package br.com.spring.rejew.projectrejew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.spring.rejew.projectrejew.entity.Comentario;
import br.com.spring.rejew.projectrejew.entity.Usuario;

/*
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	
	@Query("SELECT c FROM Comentario c WHERE c.usuarioComent.emailEntrada LIKE %:usuarioComent%")
	List<Comentario> findByUsuarioComent(String usuarioComent);
	
	@Query("SELECT c.usuarioComent FROM Comentario c WHERE c.idComentario = :idComentario")
	Usuario findUsuarioByComentarioId(Long idComentario);
	
	@Query("SELECT c FROM Comentario c WHERE c.livroComent.isbnLivro = :idLivroComent")
	List<Comentario> findByLivroComent(Long idLivroComent);
}