package br.com.spring.rejew.projectrejew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.rejew.projectrejew.entity.Mensagem;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
	@Query("SELECT m FROM Mensagem m WHERE m.usuarioMensagem.emailEntrada LIKE %:usuarioEmailEntrada%")
	List<Mensagem> findByUsuarioMensagem(String usuarioEmailEntrada);
	
	@Query("SELECT m.usuarioMensagem FROM Mensagem m WHERE m.idMensagem = :idMensagem")
	Usuario findUsuarioByMensagemId(Long idMensagem);
	
	@Query("SELECT m FROM Mensagem m WHERE m.chatMensagem.generoChat LIKE %:generoChat%")
    List<Mensagem> findByChatMensagem(String generoChat);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Mensagem m WHERE m.usuarioMensagem.emailEntrada = :emailUsuario")
	void deleteByUsuarioMensagem(String emailUsuario);

}
