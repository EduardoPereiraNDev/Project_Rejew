package br.com.spring.rejew.projectrejew.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.spring.rejew.projectrejew.entity.Livro;
import br.com.spring.rejew.projectrejew.repository.LivroRepository;


@RestController
@RequestMapping("/livros")
public class LivroController {
    
	private static final String UPLOAD_DIR = "C:/Users/rm2965/Desktop/Project_Rejew/Database/UploadsIMG/";
	
    @Autowired
    private LivroRepository livroRepository;

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }
    
    @GetMapping("/imagem/{caminho}")
    public ResponseEntity<byte[]> retornarImagem(@PathVariable String caminho) {
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
    
 // Buscar um livro por Autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> buscarLivroPorAutor(@PathVariable String autor) {
    	List<Livro> livros = livroRepository.findByAutor(autor);
        return ResponseEntity.ok(livros);
    }

    // Salvar um novo livro
    @PostMapping
    public ResponseEntity<String> adicionarLivro(
            @RequestParam("nomeLivro") String nomeLivro,
            @RequestParam("autorLivro") String autorLivro,
            @RequestParam("sinopseLivro") String sinopseLivro,
            @RequestParam("numeroPag") int numeroPag,
            @RequestParam("anoLancamento") int anoLancamento,
            @RequestParam("generoLivro") String generoLivro,
            @RequestParam("corPrimaria") String corPrimaria,
            @RequestParam("caminhoImgCapa") MultipartFile caminhoImgCapa) {
    	
    	if (nomeLivro == null || nomeLivro.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome do livro é obrigatório.");
        }
        if (autorLivro == null || autorLivro.isEmpty()) {
            return ResponseEntity.badRequest().body("Autor do livro é obrigatório.");
        }
        if (sinopseLivro == null || sinopseLivro.isEmpty()) {
            return ResponseEntity.badRequest().body("Sinopse do Livro é obrigatório.");
        }
        if (numeroPag <= 0) {
            return ResponseEntity.badRequest().body("Número de páginas deve ser positivo.");
        }
        if (anoLancamento <= 0) {
            return ResponseEntity.badRequest().body("Ano de lançamento deve ser positivo.");
        }
        if (generoLivro == null || generoLivro.isEmpty()) {
            return ResponseEntity.badRequest().body("Gênero do livro é obrigatório.");
        }
        if (caminhoImgCapa.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo de imagem encontrado.");
        }

        if (caminhoImgCapa.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo de imagem encontrado.");
        }else if (!caminhoImgCapa.getContentType().equals("image/jpeg") && !caminhoImgCapa.getContentType().equals("image/png")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo não suportado.");
        }else if (caminhoImgCapa.getSize() > 10 * 1024 * 1024) { // Limite tamanho IMG
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo é muito grande.");
        }
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String originalFilename = caminhoImgCapa.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = generateUniqueFilename(fileExtension);
            Path path = Paths.get(UPLOAD_DIR + uniqueFilename);
            Files.write(path, caminhoImgCapa.getBytes());
            Livro livro = new Livro(nomeLivro, autorLivro, sinopseLivro ,numeroPag, anoLancamento, generoLivro, corPrimaria, uniqueFilename);
            livroRepository.save(livro);

            return ResponseEntity.ok("Livro cadastrado com sucesso ");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo.");
        }
    }

    @PutMapping("/{isbn}")
    // Atualizar um livro existente
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long isbn, 
    		@RequestParam("nomeLivro") String nomeLivro,
            @RequestParam("autorLivro") String autorLivro,
            @RequestParam("sinopseLivro") String sinopseLivro,
            @RequestParam("numeroPag") int numeroPag,
            @RequestParam("anoLancamento") int anoLancamento,
            @RequestParam("generoLivro") String generoLivro,
            @RequestParam("corPrimaria") String corPrimaria,
            @RequestParam("caminhoImgCapa") MultipartFile caminhoImgCapa) {
    		if (livroRepository.existsById(isbn)) {
    				try {
    						Livro livroAtual = livroRepository.findById(isbn).orElse(null);
    							if (livroAtual != null) {
    								if (!caminhoImgCapa.isEmpty()) {
    									String nomeAntigo = livroAtual.getCaminhoImgCapa(); //Nome antigo
    									Path arquivoAntigo = Paths.get(UPLOAD_DIR + nomeAntigo); //caminho antigo
    									String nomeOriginal = caminhoImgCapa.getOriginalFilename(); //nome do arquivo da requisiçao
    									String extensãoArquivo = nomeOriginal != null ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) : ""; //extensão do arquivo jpg, png
    									String nomeNovo = generateUniqueFilename(extensãoArquivo); //gera um novo nome
    									Files.move(arquivoAntigo, arquivoAntigo.resolveSibling(nomeNovo)); // renomeia para o novo nome
    									livroAtual.setCaminhoImgCapa(nomeNovo); //atualiza o nome salvo no objeto Livro
    									Path novoCaminho = Paths.get(UPLOAD_DIR + nomeNovo);//atualiza o caminho 
    									Files.write(novoCaminho, caminhoImgCapa.getBytes());// insere os dados tudo nesse objeto
    								}

									// atualiza os outros dados
    								livroAtual.setNomeLivro(nomeLivro);
    								livroAtual.setAutorLivro(autorLivro);
    								livroAtual.setSinopseLivro(sinopseLivro);
    								livroAtual.setNumeroPag(numeroPag);
    								livroAtual.setAnoLancamento(anoLancamento);
    								livroAtual.setGeneroLivro(generoLivro);
    								livroAtual.setCorPrimaria(corPrimaria);

									Livro livroAtualizado = livroRepository.save(livroAtual);
									return ResponseEntity.ok(livroAtualizado);
    							}
    				} catch (IOException e) {
    					e.printStackTrace();
    					return ResponseEntity.status(500).body(null);
    				}
    		}
    		return ResponseEntity.notFound().build();
    }

    // Deletar um livro por ID
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long isbn) {
        if (!livroRepository.existsById(isbn)) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            Optional<Livro> livros = livroRepository.findById(isbn);
            if (livros.isPresent()) {
                Livro livro = livros.get();
                Path pathToDelete = Paths.get(UPLOAD_DIR + livro.getCaminhoImgCapa());
                Files.deleteIfExists(pathToDelete);
                livroRepository.deleteById(isbn);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    //funcões lógicas
    //Mudar nome Function
    private String generateUniqueFilename(String extension) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        return timestamp + "-" + uniqueSuffix + extension;
    }
}