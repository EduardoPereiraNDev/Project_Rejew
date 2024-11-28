package br.com.spring.rejew.projectrejew.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import jakarta.transaction.Transactional;

/*
 * Estende CrudRepository e fornece funcionalidades adicionais específicas do JPA.
 * Adiciona métodos como flush para sincronizar o contexto de persistência, saveAndFlush para salvar e sincronizar imediatamente, e métodos para operações de paginação e ordenação.
 * Mais adequado quando você precisa de funcionalidades específicas do JPA e quer se beneficiar das convenções adicionais fornecidas pelo Spring Data JPA.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmailEntrada(String emailEntrada);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Usuario u WHERE u.emailEntrada = :emailEntrada")
    void deleteByEmailEntrada(String emailEntrada);
    
    @Query("SELECT u FROM Usuario u WHERE u.emailEntrada LIKE %:emailEntrada")
    List<Usuario> buscarUsuarioPorEmailLike(String emailEntrada);
    
    @Query("SELECT u FROM Usuario u WHERE u.emailEntrada = :emailEntrada")
    Usuario buscarUsuarioPorEmail(String emailEntrada);

    @Query("SELECT u FROM Usuario u WHERE u.nomeUsuario LIKE %:nomeUsuario%")
    List<Usuario> buscarUsuarioPorNome(String nomeUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nomePerfil LIKE %:nomePerfil%")
    List<Usuario> buscarUsuarioPorNomePerfil(String nomePerfil);
    
    @Query("SELECT l FROM Usuario u JOIN u.livros l WHERE u.emailEntrada = :emailEntrada")
    Set<Livro> favoritadoPeloUsuario( String emailEntrada);
    
    @Query("SELECT l FROM Usuario u JOIN u.livros l WHERE u.emailEntrada = :emailEntrada")
    List<Livro> favoritadosPeloUsuario( String emailEntrada);
    
    @Query("SELECT u FROM Usuario u JOIN FETCH u.usuariosSeguindo WHERE u.emailEntrada = :emailEntrada")
    Optional<Usuario> findUsuarioComSeguindo(@Param("emailEntrada") String emailEntrada);
    
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.livros WHERE u.emailEntrada = :email")
    Usuario findByIdWithLivros(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Usuario u JOIN u.livros l WHERE u.emailEntrada = :emailUsuario")
    void deleteFavoritosDoUsuario(String emailUsuario);
    
    @Query("SELECT u FROM Usuario u WHERE u.emailEntrada = :email AND u.senhaEntrada = :password") 
    Usuario realizarLogin(@Param("email") String email, @Param("password") String password);  

}