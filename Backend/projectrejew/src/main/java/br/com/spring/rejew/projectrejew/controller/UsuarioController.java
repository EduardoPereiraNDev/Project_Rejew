package br.com.spring.rejew.projectrejew.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.entity.Usuario;
import br.com.spring.rejew.projectrejew.repository.LivroRepository;
import br.com.spring.rejew.projectrejew.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final String UPLOAD_DIR = "C:/Users/rm2877/Desktop/Project_Rejew/Database/UploadsIMGUsuarioPerfil/";
    private static final String UPLOAD_DIR2 = "C:/Users/rm2877/Desktop/Project_Rejew/Database/UploadsIMGUsuarioFundo/";
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LivroRepository livroRepository;
    
    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/login")
    public Usuario loginUsuario(@RequestBody Usuario usuario) {
    	
    	Usuario usuarioEncontrado = usuarioRepository.realizarLogin(usuario.getEmailEntrada(), usuario.getSenhaEntrada());

        if (usuarioEncontrado != null)
        {
        	return usuarioEncontrado;
        } else {
        	Usuario usuarioVazio = new Usuario();
            return usuarioVazio;

        }
    }
    
    public void seguirUsuario(String usuarioSeguindoEmail, String usuarioSeguidoEmail) {
        Optional<Usuario> usuarioSeguindoOpt = usuarioRepository.findById(usuarioSeguindoEmail);
        Optional<Usuario> usuarioSeguidoOpt = usuarioRepository.findById(usuarioSeguidoEmail);

        if (usuarioSeguindoOpt.isPresent() && usuarioSeguidoOpt.isPresent()) {
            Usuario usuarioSeguindo = usuarioSeguindoOpt.get();
            Usuario usuarioSeguido = usuarioSeguidoOpt.get();

            usuarioSeguindo.getUsuariosSeguindo().add(usuarioSeguido);
            usuarioRepository.save(usuarioSeguindo);
        }
    }

    public void deixarSeguirUsuario(String usuarioSeguindoEmail, String usuarioSeguidoEmail) {
        Optional<Usuario> usuarioSeguindo2 = usuarioRepository.findById(usuarioSeguindoEmail);
        Optional<Usuario> usuarioSeguido2 = usuarioRepository.findById(usuarioSeguidoEmail);

        if (usuarioSeguindo2.isPresent() && usuarioSeguido2.isPresent()) {
            Usuario usuarioSeguindo = usuarioSeguindo2.get();
            Usuario usuarioSeguido = usuarioSeguido2.get();

            usuarioSeguindo.getUsuariosSeguindo().remove(usuarioSeguido);
            usuarioRepository.save(usuarioSeguindo);
        }
    }
    
    // Buscar um usuário por email
    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // Buscar um usuário por email like
    @GetMapping("/email/{emailEntrada}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorEmailLike(@PathVariable String emailEntrada) {
        List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorEmailLike(emailEntrada);
        return ResponseEntity.ok(usuarios);
    }
    

    // Buscar um usuário por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/imagem/{caminho}")
    public ResponseEntity<byte[]> retornarImagemPerfil(@PathVariable String caminho) {
        if (caminho == null || caminho.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        File imagemArquivo = new File(UPLOAD_DIR + caminho);
        if (!imagemArquivo.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            byte[] bytes = Files.readAllBytes(imagemArquivo.toPath());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/imagem/fundo/{caminho}")
    public ResponseEntity<byte[]> retornarImagemFundo(@PathVariable String caminho) {
        if (caminho == null || caminho.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        File imagemArquivo = new File(UPLOAD_DIR2 + caminho);
        if (!imagemArquivo.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            byte[] bytes = Files.readAllBytes(imagemArquivo.toPath());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Buscar um usuário por nome do perfil
    @GetMapping("/perfil/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNomePerfil(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNomePerfil(nome);
        return ResponseEntity.ok(usuarios);
    }

    // Adicionar um novo usuário APP
    @PostMapping
    public ResponseEntity<Map<String, String>> adicionarUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> existingUsuario = usuarioRepository.findByEmailEntrada(usuario.getEmailEntrada());
        
        if (existingUsuario.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email já cadastrado: " + usuario.getEmailEntrada()));
        }
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nome do usuário é obrigatório."));
        }
        if (usuario.getEmailEntrada() == null || usuario.getEmailEntrada().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email é obrigatório."));
        }
        if (usuario.getSenhaEntrada() == null || usuario.getSenhaEntrada().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Senha é obrigatória."));
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(Map.of("message", "Usuário cadastrado com sucesso."));
    }
    
    @PostMapping("/{emailEntrada}/favoritar/{isbnLivro}")
    public void favoritarLivro(@PathVariable String emailEntrada, @PathVariable Long isbnLivro) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(emailEntrada);

        Optional<Livro> optionalLivro = livroRepository.findById(isbnLivro);     
        if (optionalLivro.isPresent()) {
            Livro livro = optionalLivro.get(); 
            
            if (!usuario.getLivros().contains(livro)) {
                usuario.getLivros().add(livro);
                usuarioRepository.save(usuario); 
            }
        } 
    }


    @PostMapping("/{emailEntrada}/desfavoritar/{isbnLivro}")
    public void desfavoritarLivro(@PathVariable String emailEntrada, @PathVariable Long isbnLivro) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(emailEntrada);
        Optional<Livro> optionalLivro = livroRepository.findById(isbnLivro);
       
        if (optionalLivro.isPresent()) {
            Livro livro = optionalLivro.get(); 
            
            if (!usuario.getLivros().contains(livro)) {
                usuario.getLivros().remove(livro);
                usuarioRepository.save(usuario); 
            }
        }            
    }

    @PutMapping("/{emailUsuario}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable String emailUsuario,
            @RequestParam("nomeUsuario") String nomeUsuario,
            @RequestParam("nomePerfil") String nomePerfil,
            @RequestParam("emailEntrada") String emailEntrada,
            @RequestParam("dataNascimento") String dataNascimentoStr,
            @RequestParam(value = "caminhoImagem", required = false) MultipartFile caminhoImagem,
            @RequestParam(value = "caminhoImagemFundo", required = false) MultipartFile caminhoImagemFundo,
            @RequestParam("recadoPerfil") String recadoPerfil) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailEntrada(emailUsuario);
        if (optionalUsuario.isPresent()) {
            try {
                Usuario usuarioAtual = optionalUsuario.get();
                if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                    if (usuarioAtual.getCaminhoImagem() != null && !usuarioAtual.getCaminhoImagem().isEmpty()) {
                        Path arquivoAntigo = Paths.get(UPLOAD_DIR + usuarioAtual.getCaminhoImagem());
                        Files.deleteIfExists(arquivoAntigo);
                    }
                    String nomeNovo = generateUniqueFilename(extensaoArquivo(caminhoImagem.getOriginalFilename()));
                    Path path = Paths.get(UPLOAD_DIR + nomeNovo);
                    Files.write(path, caminhoImagem.getBytes());
                    usuarioAtual.setCaminhoImagem(nomeNovo);
                }
                if (caminhoImagemFundo != null && !caminhoImagemFundo.isEmpty()) {
                    if (usuarioAtual.getCaminhoImagemFundo() != null && !usuarioAtual.getCaminhoImagemFundo().isEmpty()) {
                        Path arquivoAntigoFundo = Paths.get(UPLOAD_DIR2 + usuarioAtual.getCaminhoImagemFundo());
                        Files.deleteIfExists(arquivoAntigoFundo);
                    }
                    // Adiciona a nova imagem de fundo
                    String nomeNovoFundo = generateUniqueFilename(extensaoArquivo(caminhoImagemFundo.getOriginalFilename()));
                    Path pathFundo = Paths.get(UPLOAD_DIR2 + nomeNovoFundo);
                    Files.write(pathFundo, caminhoImagemFundo.getBytes());
                    usuarioAtual.setCaminhoImagemFundo(nomeNovoFundo);
                }
                usuarioAtual.setNomeUsuario(nomeUsuario);
                usuarioAtual.setNomePerfil(nomePerfil);
                usuarioAtual.setEmailEntrada(emailEntrada);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
                usuarioAtual.setDataNascimento(dataNascimento);
                usuarioAtual.setRecadoPerfil(recadoPerfil);
                Usuario usuarioAtualizado = usuarioRepository.save(usuarioAtual);
                return ResponseEntity.ok(usuarioAtualizado);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar um usuário por email
    @DeleteMapping("/{emailUsuario}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable String emailUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailEntrada(emailUsuario);
        if (usuario.isPresent()) {
            try {
                Usuario usuarioToDelete = usuario.get();
                Path pathToDelete = Paths.get(UPLOAD_DIR + usuarioToDelete.getCaminhoImagem());
                Files.deleteIfExists(pathToDelete);
                Path pathToDelete2 = Paths.get(UPLOAD_DIR2 + usuarioToDelete.getCaminhoImagemFundo());
                Files.deleteIfExists(pathToDelete2);
                usuarioRepository.deleteByEmailEntrada(emailUsuario);
                return ResponseEntity.noContent().build(); 
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Funções auxiliares
    private String generateUniqueFilename(String extension) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        return timestamp + "-" + uniqueSuffix + extension;
    }
    
    private String extensaoArquivo(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}