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
    private UsuarioRepository usuarioRepository ;
    
 // Listar todos os usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
    
 // Buscar um usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // Buscar um usuario por Nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@PathVariable String nome) {
    	List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }
    
 // Buscar um usuario por nome Perfil
    @GetMapping("/perfil/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNomePerfil(@PathVariable String nome) {
    	List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNomePerfil(nome);
        return ResponseEntity.ok(usuarios);
    }
    
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

        if (nomeUsuario == null || nomeUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome do usuário é obrigatório.");
        }

        if (emailEntrada == null || emailEntrada.isEmpty()) {
            return ResponseEntity.badRequest().body("Email é obrigatório.");
        }

        if (senhaEntrada == null || senhaEntrada.isEmpty()) {
            return ResponseEntity.badRequest().body("Senha é obrigatória.");
        }

        if (caminhoImagem.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo de imagem de perfil encontrado.");
        } else if (!caminhoImagem.getContentType().equals("image/jpeg") && !caminhoImagem.getContentType().equals("image/png")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo de imagem de perfil não suportado.");
        } else if (caminhoImagem.getSize() > 10 * 1024 * 1024) { // Limite tamanho IMG
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo de imagem de perfil é muito grande.");
        }

        if (caminhoImagemFundo.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo de imagem de fundo encontrado.");
        } else if (!caminhoImagemFundo.getContentType().equals("image/jpeg") && !caminhoImagemFundo.getContentType().equals("image/png")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo de imagem de fundo não suportado.");
        } else if (caminhoImagemFundo.getSize() > 10 * 1024 * 1024) { // Limite tamanho IMG
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo de imagem de fundo é muito grande.");
        }

        try {
            File dir = new File(UPLOAD_DIR);
            File dir2 = new File(UPLOAD_DIR2);
            if (!dir.exists() && !dir2.exists()) {
                dir.mkdirs();
                dir2.mkdirs();
            }

            String originalFilename = caminhoImagem.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = generateUniqueFilename(fileExtension);
            Path path = Paths.get(UPLOAD_DIR + uniqueFilename);
            Files.write(path, caminhoImagem.getBytes());

            String originalFilename2 = caminhoImagemFundo.getOriginalFilename();
            String fileExtension2 = originalFilename2 != null ? originalFilename2.substring(originalFilename2.lastIndexOf(".")) : "";
            String uniqueFilename2 = generateUniqueFilename(fileExtension2);
            Path path2 = Paths.get(UPLOAD_DIR2 + uniqueFilename2);
            Files.write(path2, caminhoImagemFundo.getBytes());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);

            Usuario usuario = new Usuario(nomeUsuario, nomePerfil, emailEntrada, senhaEntrada, dataNascimento, uniqueFilename, uniqueFilename2, recadoPerfil);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Usuário cadastrado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar os arquivos.");
        }
    }
  
    
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long idUsuario,
            @RequestParam("nomeUsuario") String nomeUsuario,
            @RequestParam("nomePerfil") String nomePerfil,
            @RequestParam("emailEntrada") String emailEntrada,
            @RequestParam("dataNascimento") String dataNascimentoStr,
            @RequestParam("caminhoImagem") MultipartFile caminhoImagem,
            @RequestParam("caminhoImagemFundo") MultipartFile caminhoImagemFundo,
            @RequestParam("recadoPerfil") String recadoPerfil) {

        if (usuarioRepository.existsById(idUsuario)) {
            try {
                Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
                if (optionalUsuario.isPresent()) {
                    Usuario usuarioAtual = optionalUsuario.get();

                    if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                        String nomeAntigo = usuarioAtual.getCaminhoImagem(); // Nome antigo
                        Path arquivoAntigo = Paths.get(UPLOAD_DIR + nomeAntigo); // Caminho antigo
                        String nomeOriginal = caminhoImagem.getOriginalFilename(); // Nome do arquivo da requisição
                        String extensaoArquivo = nomeOriginal != null ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) : ""; // Extensão do arquivo
                        String nomeNovo = generateUniqueFilename(extensaoArquivo); // Gera um novo nome
                        Files.move(arquivoAntigo, arquivoAntigo.resolveSibling(nomeNovo)); // Renomeia para o novo nome
                        usuarioAtual.setCaminhoImagem(nomeNovo); // Atualiza o nome salvo no objeto Usuario
                        Path novoCaminho = Paths.get(UPLOAD_DIR + nomeNovo); // Atualiza o caminho
                        Files.write(novoCaminho, caminhoImagem.getBytes()); // Insere os dados no novo caminho
                    }

                    if (caminhoImagemFundo != null && !caminhoImagemFundo.isEmpty()) {
                        String nomeAntigoFundo = usuarioAtual.getCaminhoImagemFundo(); // Nome antigo da imagem de fundo
                        Path arquivoAntigoFundo = Paths.get(UPLOAD_DIR2 + nomeAntigoFundo); // Caminho antigo da imagem de fundo
                        String nomeOriginalFundo = caminhoImagemFundo.getOriginalFilename(); // Nome do arquivo da requisição
                        String extensaoArquivoFundo = nomeOriginalFundo != null ? nomeOriginalFundo.substring(nomeOriginalFundo.lastIndexOf(".")) : ""; // Extensão do arquivo
                        String nomeNovoFundo = generateUniqueFilename(extensaoArquivoFundo); // Gera um novo nome
                        Files.move(arquivoAntigoFundo, arquivoAntigoFundo.resolveSibling(nomeNovoFundo)); // Renomeia para o novo nome
                        usuarioAtual.setCaminhoImagemFundo(nomeNovoFundo); // Atualiza o nome salvo no objeto Usuario
                        Path novoCaminhoFundo = Paths.get(UPLOAD_DIR2 + nomeNovoFundo); // Atualiza o caminho
                        Files.write(novoCaminhoFundo, caminhoImagemFundo.getBytes()); // Insere os dados no novo caminho
                    }

                    // Atualiza os outros dados
                    usuarioAtual.setNomeUsuario(nomeUsuario);
                    usuarioAtual.setNomePerfil(nomePerfil);
                    usuarioAtual.setEmailEntrada(emailEntrada);

                    // Converter String para LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
                    usuarioAtual.setDataNascimento(dataNascimento);

                    usuarioAtual.setRecadoPerfil(recadoPerfil);

                    Usuario usuarioAtualizado = usuarioRepository.save(usuarioAtual);
                    return ResponseEntity.ok(usuarioAtualizado);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    // Deletar um Usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
        	try {
        	Optional<Usuario> usuarios = usuarioRepository.findById(id);
        	 Usuario usuario = usuarios.get();
        	 Path pathToDelete = Paths.get(UPLOAD_DIR + usuario.getCaminhoImagem());
             Files.deleteIfExists(pathToDelete);
             Path pathToDelete2 = Paths.get(UPLOAD_DIR2 + usuario.getCaminhoImagemFundo());
             Files.deleteIfExists(pathToDelete2);
             usuarioRepository.deleteById(id);
             return ResponseEntity.ok("Usuario deletado com sucesso");
         } catch (IOException e) {
             e.printStackTrace();
             return ResponseEntity.status(500).body("Erro ao deletar o arquivo.");
         } catch (RuntimeException e) {
             return ResponseEntity.status(404).body(e.getMessage());
         }
        }
        return ResponseEntity.notFound().build();
    }
    
    
  //funcões lógicas
    //Mudar nome Function
    private String generateUniqueFilename(String extension) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        return timestamp + "-" + uniqueSuffix + extension;
    }

    


}
