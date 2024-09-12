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

import br.com.spring.rejew.projectrejew.entity.Genero;
import br.com.spring.rejew.projectrejew.repository.GeneroRepository;
import br.com.spring.rejew.projectrejew.repository.LivroRepository;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

    // Listar todos os generos
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodosGeneros() {
        List<Genero> generos = generoRepository.findAll();
        return ResponseEntity.ok(generos);
    }

    // Buscar um genero por ID
    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarGeneroPorId(@PathVariable Long id) {
        Optional<Genero> genero = generoRepository.findById(id);
        return genero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // Buscar um genero por tipo
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Genero>> buscarGeneroPorNome(@PathVariable String genero) {
    	List<Genero> generos = generoRepository.findByGenero(genero);
        return ResponseEntity.ok(generos);
    }

    // Salvar um novo genero
    @PostMapping
    public ResponseEntity<Genero> salvarGenero(@RequestBody Genero genero) {
        Genero generoSalvo = generoRepository.save(genero);
        return ResponseEntity.ok(generoSalvo);
    }

    // Atualizar um genero existente
    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizarGenero(@PathVariable Long id, @RequestBody Genero genero) {
        if (generoRepository.existsById(id)) {
        	genero.setIdGenero(id);
            Genero generoAtualizado = generoRepository.save(genero);
            return ResponseEntity.ok(generoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar um genero por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGenero(@PathVariable Long id) {
        if (generoRepository.existsById(id)) {
        	generoRepository.deleteById(id);
        }
        return ResponseEntity.notFound().build();
    }
}
