package br.com.spring.rejew.projectrejew.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import br.com.spring.rejew.projectrejew.entity.UsuarioLivroA;
import br.com.spring.rejew.projectrejew.entity.UsuarioLivroADTO;
import br.com.spring.rejew.projectrejew.repository.LivroRepository;
import br.com.spring.rejew.projectrejew.repository.UsuarioLivroARepository;
import br.com.spring.rejew.projectrejew.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarioLivro")
public class UsuarioLivroAController {
	
	@Autowired
    private UsuarioLivroARepository usuarioLivroARepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LivroRepository livroRepository;
	
	 // Listar todas as Avaliações
    @GetMapping
    public List<UsuarioLivroADTO> listarTodosUsuarios() {
    	 List<UsuarioLivroA> usuariosLivrosA = (List<UsuarioLivroA>) usuarioLivroARepository.findAll();
         return usuariosLivrosA.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    @GetMapping("/media/{isbnLivro}")
    public ResponseEntity<Double> calcularMedia(@PathVariable Long isbnLivro) {
        List<Double> notasLivro = usuarioLivroARepository.findNotasByLivroIsbn(isbnLivro);
        
        if (notasLivro.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }
        
        Double media = notasLivro.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        media = Double.valueOf(String.format("%.1f", media).replace(",", "."));
        return ResponseEntity.ok(media);
    }
    
    @GetMapping("/jaAvaliado/{emailUsuario}/{isbnLivro}")
    public ResponseEntity<Boolean> jaAvaliado( @PathVariable String emailUsuario, @PathVariable Long isbnLivro ) {
    	UsuarioLivroA usuarioLivroA =  usuarioLivroARepository.findJaAvaliado(isbnLivro, emailUsuario);
    	if(usuarioLivroA == null) {
    		return ResponseEntity.ok(false);
    	}
    	return ResponseEntity.ok(true);
    }
    
    
    @PostMapping("/avaliar/{email}/{isbnLivro}/{notaAvaliacao}")
    public UsuarioLivroADTO insert(@PathVariable String email ,@PathVariable Long isbnLivro , @PathVariable double notaAvaliacao) {
    	Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);
    	Optional<Livro> livroP = livroRepository.findById(isbnLivro);
    	Livro livro = livroP.get();
    	UsuarioLivroA usuarioLivroA = new UsuarioLivroA();
    	usuarioLivroA.setUsuarioA(usuario);
    	usuarioLivroA.setLivroA(livro);
    	usuarioLivroA.setNotaLivro(notaAvaliacao);
    	UsuarioLivroA savedDisciplinaAluno = usuarioLivroARepository.save(usuarioLivroA);
        return convertToDTO(savedDisciplinaAluno);
    }
    
    /*
    @PostMapping("/avaliar/{notaAvaliacao}")
    public UsuarioLivroADTO insert(@RequestBody Usuario usuario ,@RequestBody Livro livro , @PathVariable double notaAvaliacao) {
    	UsuarioLivroA usuarioLivroA = new UsuarioLivroA();
    	usuarioLivroA.setUsuarioA(usuario);
    	usuarioLivroA.setLivroA(livro);
    	usuarioLivroA.setNotaLivro(notaAvaliacao);
    	UsuarioLivroA savedDisciplinaAluno = usuarioLivroARepository.save(usuarioLivroA);
        return convertToDTO(savedDisciplinaAluno);
    }*/

    private UsuarioLivroADTO convertToDTO(UsuarioLivroA usuarioLivroA) {
    	UsuarioLivroADTO dto = new UsuarioLivroADTO();
    	   dto.setIdUsuarioLivro(usuarioLivroA.getIdUsuarioLivro());
	       dto.setLivroISBN(usuarioLivroA.getLivroA().getIsbnLivro());
	       dto.setLivroNome(usuarioLivroA.getLivroA().getNomeLivro());
	       dto.setEmailUsuario(usuarioLivroA.getUsuarioA().getEmailEntrada());
	       dto.setNomeUsuario(usuarioLivroA.getUsuarioA().getNomeUsuario());
	       dto.setNotaLivro(usuarioLivroA.getNotaLivro());
	        return dto;
	    }
    
}
