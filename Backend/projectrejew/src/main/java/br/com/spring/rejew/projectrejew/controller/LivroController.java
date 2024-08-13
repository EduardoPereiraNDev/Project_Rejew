package br.com.spring.rejew.projectrejew.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.repository.LivroRepository;


@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }

    // Buscar um livro por ID
    @GetMapping("/{isbn}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long isbn) {
        Optional<Livro> livro = livroRepository.findById(isbn);
        return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // Buscar um livro por Nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Livro>> buscarLivroPorNome(@PathVariable String nome) {
    	List<Livro> livros = livroRepository.findByNome(nome);
        return ResponseEntity.ok(livros);
    }

    // Salvar um novo livro
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro) {
        Livro livroSalvo = livroRepository.save(livro);
        return ResponseEntity.ok(livroSalvo);
    }

    // Atualizar um livro existente
    @PutMapping("/{isbn}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long isbn, @RequestBody Livro livro) {
        if (livroRepository.existsById(isbn)) {
            livro.setIsbn(isbn); // Supondo que Livro tem um método setId
            Livro livroAtualizado = livroRepository.save(livro);
            return ResponseEntity.ok(livroAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar um livro por ID
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long isbn) {
        if (livroRepository.existsById(isbn)) {
            livroRepository.deleteById(isbn);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}