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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.spring.rejew.projectrejew.entity.Usuario;
import br.com.spring.rejew.projectrejew.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final String UPLOAD_DIR = "C:/Users/rm2869/Desktop/Project_Rejew/Database/UploadsIMGUsuarioPerfil/";
    private static final String UPLOAD_DIR2 = "C:/Users/rm2869/Desktop/Project_Rejew/Database/UploadsIMGUsuarioFundo/";

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/login")
    public Usuario loginUsuario(@RequestBody Usuario Usuario) {
    	Usuario usuarioEncontrado = usuarioRepository.realizarLogin(Usuario.getEmailEntrada(), Usuario.getSenhaEntrada());

        if (usuarioEncontrado != null)
        {
        	return usuarioEncontrado;
        } else {
        	Usuario usuarioVazio = new Usuario();
            return usuarioVazio;
        }
    }
    
    // Buscar um usuário por email
    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailEntrada(email);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    // Buscar um usuário por nome do perfil
    @GetMapping("/perfil/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNomePerfil(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNomePerfil(nome);
        return ResponseEntity.ok(usuarios);
    }

    // Adicionar um novo usuário
    @PostMapping
    public ResponseEntity<String> adicionarUsuario(
            @RequestParam("nomeUsuario") String nomeUsuario,
            @RequestParam("nomePerfil") String nomePerfil,
            @RequestParam("emailEntrada") String emailEntrada,
            @RequestParam("senhaEntrada") String senhaEntrada,
            @RequestParam("dataNascimento") String dataNascimentoStr,
            @RequestParam("caminhoImagem") MultipartFile caminhoImagem,
            @RequestParam("caminhoImagemFundo") MultipartFile caminhoImagemFundo,
            @RequestParam("recadoPerfil") String recadoPerfil) {

    	Optional<Usuario> existingUsuario = usuarioRepository.findByEmailEntrada(emailEntrada);
        if (existingUsuario.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado: " + emailEntrada);
        }
        if (nomeUsuario == null || nomeUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome do usuário é obrigatório.");
        }

        if (emailEntrada == null || emailEntrada.isEmpty()) {
            return ResponseEntity.badRequest().body("Email é obrigatório.");
        }

        if (senhaEntrada == null || senhaEntrada.isEmpty()) {
            return ResponseEntity.badRequest().body("Senha é obrigatória.");
        }

        // Lógica para salvar as imagens e o usuário
        try {
            File dir = new File(UPLOAD_DIR);
            File dir2 = new File(UPLOAD_DIR2);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!dir2.exists()) {
                dir2.mkdirs();
            }

            // Imagem de perfil
            String originalFilename = caminhoImagem.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = generateUniqueFilename(fileExtension);
            Path path = Paths.get(UPLOAD_DIR + uniqueFilename);
            Files.write(path, caminhoImagem.getBytes());

            // Imagem de fundo
            String originalFilename2 = caminhoImagemFundo.getOriginalFilename();
            String fileExtension2 = originalFilename2 != null ? originalFilename2.substring(originalFilename2.lastIndexOf(".")) : "";
            String uniqueFilename2 = generateUniqueFilename(fileExtension2);
            Path path2 = Paths.get(UPLOAD_DIR2 + uniqueFilename2);
            Files.write(path2, caminhoImagemFundo.getBytes());

            // Converter String para LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);

            // Criar o novo usuário e salvar no banco de dados
            Usuario usuario = new Usuario(nomeUsuario, nomePerfil, emailEntrada, senhaEntrada, dataNascimento, uniqueFilename, uniqueFilename2, recadoPerfil);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Usuário cadastrado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar os arquivos.");
        }
    }

    // Atualizar um usuário por email
    @PutMapping("/{emailUsuario}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable String emailUsuario,
            @RequestParam("nomeUsuario") String nomeUsuario,
            @RequestParam("nomePerfil") String nomePerfil,
            @RequestParam("emailEntrada") String emailEntrada,
            @RequestParam("dataNascimento") String dataNascimentoStr,
            @RequestParam("caminhoImagem") MultipartFile caminhoImagem,
            @RequestParam("caminhoImagemFundo") MultipartFile caminhoImagemFundo,
            @RequestParam("recadoPerfil") String recadoPerfil) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailEntrada(emailUsuario);
        if (optionalUsuario.isPresent()) {
            try {
                Usuario usuarioAtual = optionalUsuario.get();

                // Atualização das imagens
                if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                    String nomeAntigo = usuarioAtual.getCaminhoImagem();
                    Path arquivoAntigo = Paths.get(UPLOAD_DIR + nomeAntigo);
                    String nomeOriginal = caminhoImagem.getOriginalFilename();
                    String extensaoArquivo = nomeOriginal != null ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) : "";
                    String nomeNovo = generateUniqueFilename(extensaoArquivo);
                    Files.move(arquivoAntigo, arquivoAntigo.resolveSibling(nomeNovo));
                    usuarioAtual.setCaminhoImagem(nomeNovo);
                    Path novoCaminho = Paths.get(UPLOAD_DIR + nomeNovo);
                    Files.write(novoCaminho, caminhoImagem.getBytes());
                }

                if (caminhoImagemFundo != null && !caminhoImagemFundo.isEmpty()) {
                    String nomeAntigoFundo = usuarioAtual.getCaminhoImagemFundo();
                    Path arquivoAntigoFundo = Paths.get(UPLOAD_DIR2 + nomeAntigoFundo);
                    String nomeOriginalFundo = caminhoImagemFundo.getOriginalFilename();
                    String extensaoArquivoFundo = nomeOriginalFundo != null ? nomeOriginalFundo.substring(nomeOriginalFundo.lastIndexOf(".")) : "";
                    String nomeNovoFundo = generateUniqueFilename(extensaoArquivoFundo);
                    Files.move(arquivoAntigoFundo, arquivoAntigoFundo.resolveSibling(nomeNovoFundo));
                    usuarioAtual.setCaminhoImagemFundo(nomeNovoFundo);
                    Path novoCaminhoFundo = Paths.get(UPLOAD_DIR2 + nomeNovoFundo);
                    Files.write(novoCaminhoFundo, caminhoImagemFundo.getBytes());
                }

                // Atualização de outros dados
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
}