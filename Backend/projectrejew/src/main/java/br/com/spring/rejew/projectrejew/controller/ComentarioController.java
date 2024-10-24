package br.com.spring.rejew.projectrejew.controller;

import br.com.spring.rejew.projectrejew.entity.Comentario;
import br.com.spring.rejew.projectrejew.repository.ComentarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Listar todas os comentarios
    @GetMapping
    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }
   
    // Buscar um comentario por ID
    @GetMapping("/{isbn}")
    public ResponseEntity<Comentario> buscarComentarioID(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Buscar um comentario por Usuario
    @GetMapping("/usuario/{usuarioComent}")
    public ResponseEntity<List<Comentario>> buscarComentariosPorUsuario(@PathVariable String usuarioComent) {
        List<Comentario> comentarios = comentarioRepository.findByUsuarioComent(usuarioComent);
        if (comentarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comentarios);
    }
    
    // Buscar um comentario por Livro
    @GetMapping("/livro/{idLivro}")
    public ResponseEntity<List<Comentario>> buscarComentariosPorLivro(@PathVariable Long idLivro) {
        List<Comentario> comentarios = comentarioRepository.findByLivroComent(idLivro);
        if (comentarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comentarios);
    }
    
    // Salvar um novo comentario
    @PostMapping
    public ResponseEntity<Comentario> adicionarComentario(@RequestBody Comentario comentario){
    	Comentario comentarioSalvo = comentarioRepository.save(comentario);
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
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long id) {
        if (comentarioRepository.existsById(id)) {
        	comentarioRepository.deleteById(id);
        	return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}