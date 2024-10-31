package br.com.spring.rejew.projectrejew.controller;

import br.com.spring.rejew.projectrejew.entity.Adm;
import br.com.spring.rejew.projectrejew.repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    @Autowired
    private AdmRepository admRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody Map<String, String> loginData) {
        String credencial = loginData.get("credencial");
        int senhaadm;
        
        try {
            senhaadm = Integer.parseInt(loginData.get("senhaadm"));
        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Formato de senha inválido!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Busca o administrador no banco de dados
        Optional<Adm> admin = admRepository.findByCredencialAndSenhaadm(credencial, senhaadm);

        Map<String, String> response = new HashMap<>();

        if (admin.isPresent()) {
            response.put("message", "Login de administrador bem-sucedido!");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Credenciais de administrador incorretas!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
