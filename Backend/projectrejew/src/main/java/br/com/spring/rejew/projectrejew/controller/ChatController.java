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

import br.com.spring.rejew.projectrejew.entity.Chat;
import br.com.spring.rejew.projectrejew.repository.ChatRepository;

@RestController
@RequestMapping("/chats")
public class ChatController {
    
	private static final String UPLOAD_DIR = "C:/Users/rm2869/Desktop/Project_Rejew/Database/UploadsIMGChatLogo/";
	private static final String UPLOAD_DIR2 = "C:/Users/rm2869/Desktop/Project_Rejew/Database/UploadsIMGChatFundo/";
	
    @Autowired
    private ChatRepository chatRepository;

    // Listar todos os Chats
    @GetMapping
    public ResponseEntity<List<Chat>> listarTodosChats() {
        List<Chat> chats = chatRepository.findAll();
        return ResponseEntity.ok(chats);
    }

    // Buscar um chat por ID
    @GetMapping("/{id}")
    public ResponseEntity<Chat> buscarChatPorId(@PathVariable Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // Buscar um chat por genero
    @GetMapping("/chat/{genero}")
    public ResponseEntity<List<Chat>> buscarChatPorGenero(@PathVariable String genero) {
    	List<Chat> chats = chatRepository.findByGenero(genero);
        return ResponseEntity.ok(chats);
    }

    // Salvar um novo Chat
    @PostMapping
    public ResponseEntity<String> adicionarChat(
            @RequestParam("generoChat") String generoChat,
            @RequestParam("caminhoImagemLogo") MultipartFile caminhoImagemLogo,
            @RequestParam("caminhoImagemFundoChat") MultipartFile caminhoImagemFundoChat){
    	
    	if (generoChat == null || generoChat.isEmpty()) {
            return ResponseEntity.badRequest().body("Genero do chat é obrigatório.");
        }
    	
    	 if (caminhoImagemLogo.isEmpty()) {
             return ResponseEntity.badRequest().body("Nenhum arquivo de imagem encontrado.");
         }else if (!caminhoImagemLogo.getContentType().equals("image/jpeg") && !caminhoImagemLogo.getContentType().equals("image/png")) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo não suportado.");
         }else if (caminhoImagemLogo.getSize() > 10 * 1024 * 1024) { // Limite tamanho IMG
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo é muito grande.");
         }
    	 
    	 
    	 if (caminhoImagemFundoChat.isEmpty()) {
             return ResponseEntity.badRequest().body("Nenhum arquivo de imagem encontrado.");
         }else if (!caminhoImagemFundoChat.getContentType().equals("image/jpeg") && !caminhoImagemFundoChat.getContentType().equals("image/png")) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo não suportado.");
         }else if (caminhoImagemFundoChat.getSize() > 10 * 1024 * 1024) { // Limite tamanho IMG
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo é muito grande.");
         }
        
        try {
        	File dir = new File(UPLOAD_DIR);
            File dir2 = new File(UPLOAD_DIR2);
            if (!dir.exists() && !dir2.exists()) {
                dir.mkdirs();
                dir2.mkdirs();
            }
            String originalFilename = caminhoImagemLogo.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = generateUniqueFilename(fileExtension);
            Path path = Paths.get(UPLOAD_DIR + uniqueFilename);
            Files.write(path, caminhoImagemLogo.getBytes());
            
            String originalFilename2 = caminhoImagemFundoChat.getOriginalFilename();
            String fileExtension2 = originalFilename2 != null ? originalFilename2.substring(originalFilename2.lastIndexOf(".")) : "";
            String uniqueFilename2 = generateUniqueFilename(fileExtension2);
            Path path2 = Paths.get(UPLOAD_DIR2 + uniqueFilename2);
            Files.write(path2, caminhoImagemFundoChat.getBytes());
            
            
            Chat chats = new Chat(generoChat, uniqueFilename, uniqueFilename2 );
            chatRepository.save(chats);

            return ResponseEntity.ok("Chat cadastrado com sucesso ");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar os arquivos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chat> atualizarChat(
            @PathVariable Long id,
            @RequestParam("generoChat") String generoChat,
            @RequestParam("caminhoImagemLogo") MultipartFile caminhoImagemLogo,
            @RequestParam("caminhoImagemFundoChat") MultipartFile caminhoImagemFundoChat) {
        if (chatRepository.existsById(id)) {
            try {
                Chat chatAtual = chatRepository.findById(id).orElse(null);
                if (chatAtual != null) {
                    if (caminhoImagemLogo != null && !caminhoImagemLogo.isEmpty()) {
                        String nomeAntigo = chatAtual.getCaminhoImagemLogo(); // Nome antigo
                        Path arquivoAntigo = Paths.get(UPLOAD_DIR + nomeAntigo); // Caminho antigo
                        String nomeOriginal = caminhoImagemLogo.getOriginalFilename(); // Nome do arquivo 
                        String extensaoArquivo = nomeOriginal != null ? nomeOriginal.substring(nomeOriginal.lastIndexOf(".")) : ""; // Extensão do arquivo jpg, png ou sla oq 
                        String nomeNovo = generateUniqueFilename(extensaoArquivo); // Novo nome
                        Path novoCaminho = Paths.get(UPLOAD_DIR + nomeNovo); // Novo caminho
                        Files.write(novoCaminho, caminhoImagemLogo.getBytes()); // Escreve os dados
                        chatAtual.setCaminhoImagemLogo(nomeNovo); // Atualiza o nome salvo no objeto
                        
                        // Remove o arquivo antigo
                        Files.deleteIfExists(arquivoAntigo);
                    }

                    if (caminhoImagemFundoChat != null && !caminhoImagemFundoChat.isEmpty()) {
                        String nomeAntigoF = chatAtual.getCaminhoImagemFundoChat(); // Nome antigo
                        Path arquivoAntigoF = Paths.get(UPLOAD_DIR2 + nomeAntigoF); // Caminho antigo
                        String nomeOriginalF = caminhoImagemFundoChat.getOriginalFilename(); // Nome do arquivo da requisição
                        String extensaoArquivoF = nomeOriginalF != null ? nomeOriginalF.substring(nomeOriginalF.lastIndexOf(".")) : ""; // Extensão do arquivo jpg, png
                        String nomeNovoF = generateUniqueFilename(extensaoArquivoF); // Gera um novo nome
                        Path novoCaminhoF = Paths.get(UPLOAD_DIR2 + nomeNovoF); // Novo caminho
                        Files.write(novoCaminhoF, caminhoImagemFundoChat.getBytes()); // Escreve os dados
                        chatAtual.setCaminhoImagemFundoChat(nomeNovoF); // Atualiza o nome salvo no objeto

                        // Remove o arquivo antigo
                        Files.deleteIfExists(arquivoAntigoF);
                    }

                    chatAtual.setGeneroChat(generoChat);
                    Chat chatAtualizado = chatRepository.save(chatAtual);
                    return ResponseEntity.ok(chatAtualizado);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar um livro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaChat(@PathVariable Long id) {
        if (chatRepository.existsById(id)) {
        	try {
        	Optional<Chat> chats = chatRepository.findById(id);
        	Chat chat = chats.get();
        	 Path pathToDelete = Paths.get(UPLOAD_DIR + chat.getCaminhoImagemLogo());
             Files.deleteIfExists(pathToDelete);
             Path pathToDelete2 = Paths.get(UPLOAD_DIR2 + chat.getCaminhoImagemFundoChat());
             Files.deleteIfExists(pathToDelete2);
             chatRepository.deleteById(id);
             return ResponseEntity.ok("Chat deletado com sucesso");
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
