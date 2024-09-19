package br.com.spring.rejew.projectrejew.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carbank.backserver.model.User;

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
    @Query("DELETE FROM Usuario u WHERE u.emailEntrada = emailEntrada")
    void deleteByEmailEntrada(String emailEntrada);
    
    @Query("SELECT u FROM Usuario u WHERE u.emailEntrada LIKE %:emailEntrada%")
    List<Usuario> buscarUsuarioPorEmailLike(String emailEntrada);

    @Query("SELECT u FROM Usuario u WHERE u.nomeUsuario LIKE %:nomeUsuario%")
    List<Usuario> buscarUsuarioPorNome(String nomeUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nomePerfil LIKE %:nomePerfil%")
    List<Usuario> buscarUsuarioPorNomePerfil(String nomePerfil);
    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password") 
    Usuario findByEmailAndPassword(@Param("email") String email, @Param("password") String password);  

}