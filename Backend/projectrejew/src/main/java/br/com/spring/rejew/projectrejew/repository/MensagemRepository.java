package br.com.spring.rejew.projectrejew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.rejew.projectrejew.entity.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
	@Query("SELECT m FROM Mensagem m WHERE m.usuarioMensagem LIKE %:usuarioMensagem%")
	List<Mensagem> findByUsuarioMensagem(String usuarioMensagem);
	
	@Query("SELECT m FROM Mensagem m WHERE m.chatMensagem LIKE %:chatMensagem%")
    List<Mensagem> findByChatMensagem(String chatMensagem);
}
