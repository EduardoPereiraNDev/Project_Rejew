package br.com.spring.rejew.projectrejew.controller;

	import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	import br.com.spring.rejew.projectrejew.entity.Autor;
import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.repository.AutorRepository;


	@RestController
	@RequestMapping("/autores")
	public class AutorController {

	    @Autowired
	    private AutorRepository autorRepository;

	    // Listar todos os autores
	    @GetMapping
	    public ResponseEntity<List<Autor>> listarTodosAutores() {
	        List<Autor> autores = autorRepository.findAll();
	        return ResponseEntity.ok(autores);
	    }

	    // Buscar um autor por ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Autor> buscarAutoresPorId(@PathVariable Long id) {
	        Optional<Autor> autor = autorRepository.findById(id);
	        return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    
	 // Buscar um autor por ID Usuario
	    @GetMapping("/usuarios/{usuarioEmailEntrada}")
	    public ResponseEntity<List<Autor>> buscarAutorEmailUsuario(@PathVariable String usuarioEmailEntrada) {
	        List<Autor> autor = autorRepository.buscarAutorEmail(usuarioEmailEntrada);
	        return ResponseEntity.ok(autor);
	    }

	    // Salvar uma nova mensagem
	    @PostMapping
	    public ResponseEntity<Autor> tornarAutor(@RequestBody Autor autor) {
	    	Autor autorSalvo = autorRepository.save(autor);
	        return ResponseEntity.ok(autorSalvo);
	    }

	    // Atualizar uma mensagem existente
	    @PutMapping("/{id}")
	    public ResponseEntity<Autor> atualizarAutor(@PathVariable Long id, @RequestBody Autor autor) {
	        if (autorRepository.existsById(id)) {
	        	autor.setIdAutor(id);
	            Autor autorAtualizado = autorRepository.save(autor);
	            return ResponseEntity.ok(autorAtualizado);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    // Deletar mensagem por ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
	        if (autorRepository.existsById(id)) {
	            autorRepository.deleteById(id);
	            return ResponseEntity.noContent().build(); 
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	}

