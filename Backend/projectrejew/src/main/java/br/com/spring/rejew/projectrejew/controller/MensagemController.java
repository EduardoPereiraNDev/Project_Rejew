package br.com.spring.rejew.projectrejew.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.rejew.projectrejew.entity.Chat;
import br.com.spring.rejew.projectrejew.entity.Mensagem;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import br.com.spring.rejew.projectrejew.repository.ChatRepository;
import br.com.spring.rejew.projectrejew.repository.MensagemRepository;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;
    
    @Autowired
    private ChatRepository chatRepository;

    // Listar todas as mensagens
    @GetMapping
    public ResponseEntity<List<Mensagem>> listarTodasMensagens() {
        List<Mensagem> mensagens = mensagemRepository.findAll();
        return ResponseEntity.ok(mensagens);
    }

    // Buscar uma mensagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarMensagemPorId(@PathVariable Long id) {
        Optional<Mensagem> mensagem = mensagemRepository.findById(id);
        return mensagem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Buscar mensagens por chat
    @GetMapping("/chat/{chat}")
    public ResponseEntity<List<Mensagem>> buscarMensagensPorChat(@PathVariable String chat) {
        List<Mensagem> mensagens = mensagemRepository.findByChatMensagem(chat);
        return ResponseEntity.ok(mensagens);
    }
    
 // Buscar mensagens por usuário
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<Mensagem>> buscarMensagensPorUsuario(@PathVariable String usuario) {
        List<Mensagem> mensagens = mensagemRepository.findByUsuarioMensagem(usuario);
        return ResponseEntity.ok(mensagens);
    }
    
    @GetMapping("/usuario/mensagem/{idMensagem}")
    public ResponseEntity<Usuario> buscarMensagensPorUsuario(@PathVariable Long idMensagem) {
        Usuario usuario  = mensagemRepository.findUsuarioByMensagemId(idMensagem);
        return ResponseEntity.ok(usuario);
    }

    // Salvar uma nova mensagem
    @PostMapping
    public ResponseEntity<Mensagem> salvarMensagem(@RequestBody Mensagem mensagem) {
    	System.out.println(mensagem.getDataMensagem() + mensagem.getTextoMensagem());
        if (mensagem.getChatMensagem() != null && mensagem.getChatMensagem().getIdChat() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (mensagem.getChatMensagem().getIdChat() == null) {
            Chat chatSalvo = mensagem.getChatMensagem();
            chatSalvo = chatRepository.save(chatSalvo);
            mensagem.setChatMensagem(chatSalvo); 
        }

        Mensagem mensagemSalva = mensagemRepository.save(mensagem);
        return ResponseEntity.ok(mensagemSalva);
    }


    // Atualizar uma mensagem existente
    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> atualizarMensagem(@PathVariable Long id, @RequestBody Mensagem mensagem) {
        if (mensagemRepository.existsById(id)) {
            mensagem.setIdMensagem(id);
            Mensagem mensagemAtualizada = mensagemRepository.save(mensagem);
            return ResponseEntity.ok(mensagemAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar uma mensagem por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long id) {
        if (mensagemRepository.existsById(id)) {
            mensagemRepository.deleteById(id);
        }
        return ResponseEntity.notFound().build();
    }
}