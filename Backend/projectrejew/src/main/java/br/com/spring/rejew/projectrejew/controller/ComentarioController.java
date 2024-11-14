package br.com.spring.rejew.projectrejew.controller;

import br.com.spring.rejew.projectrejew.entity.Comentario;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import br.com.spring.rejew.projectrejew.repository.ComentarioRepository;
import br.com.spring.rejew.projectrejew.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todas os comentarios
    @GetMapping
    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }
   
    // Buscar um comentario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarComentarioID(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Buscar um comentario por Usuario
    @GetMapping("/usuario/{usuarioComent}")
    public ResponseEntity<List<Comentario>> buscarComentariosPorUsuario(@PathVariable String usuarioComent) {
        List<Comentario> comentarios = comentarioRepository.findByUsuarioComent(usuarioComent);
        if (comentarios.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); 
        }
        return ResponseEntity.ok(comentarios);
    }
    
    // Buscar um comentario por Usuario
    @GetMapping("/usuario/comentario/{usuarioComent}")
    public ResponseEntity<Usuario> buscarUsuarioPorComentario(@PathVariable Long usuarioComent) {
       Usuario usuario = comentarioRepository.findUsuarioByComentarioId(usuarioComent);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
    
    // Buscar um comentario por Livro
    @GetMapping("/livro/{idLivro}")
    public ResponseEntity<List<Comentario>> buscarComentariosPorLivro(@PathVariable Long idLivro) {
        List<Comentario> comentarios = comentarioRepository.findByLivroComent(idLivro);
        if (comentarios.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); 
        }
        return ResponseEntity.ok(comentarios);
    }
    
    // Salvar um novo comentario
    @PostMapping
    public ResponseEntity<Comentario> adicionarComentario(@RequestBody Comentario comentario){
    	Comentario comentarioSalvo = comentarioRepository.save(comentario);
    	Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(comentario.getUsuarioComent().getEmailEntrada());
        return ResponseEntity.ok(comentarioSalvo);
    }
    
    // Atualizar um comentario
    @PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizarMensagem(@PathVariable Long id, @RequestBody Comentario comentario) {
        if (comentarioRepository.existsById(id)) {
            comentario.setIdComentario(id);
            Comentario comentarioAtualizado = comentarioRepository.save(comentario);
            return ResponseEntity.ok(comentarioAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

 // Deletar um comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarComentario(@PathVariable Long id) {
        if (comentarioRepository.existsById(id)) {
            comentarioRepository.deleteById(id);
            return ResponseEntity.ok("Comentário deletado com sucesso!");  // Retorna uma String normal
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentário não encontrado.");
    }
}
