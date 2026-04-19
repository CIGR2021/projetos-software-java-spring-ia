package com.portal.juridico.ia.app.controller;
import com.portal.juridico.ia.app.service.DocumentoService;
import com.portal.juridico.ia.app.service.GeminiService;
import com.portal.juridico.ia.app.dto.PerguntaDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final DocumentoService documentoService;
    private final GeminiService geminiService;
    
    @PostMapping("/perguntar")
    public ResponseEntity<String> perguntar(@RequestBody PerguntaDTO dto) {
        String contexto = documentoService.buscarTodoConteudo();
        
        // LINHA DE TESTE: Será removida depois
        System.out.println("DEBUG - Conteúdo do Banco: " + contexto);
    
        // Validação: Se o banco estiver vazio ou o contexto for nulo/vazio
        if (contexto == null || contexto.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Erro: Nenhum documento encontrado. Por favor, faça o upload de um arquivo primeiro.");
        }
        String resposta = geminiService.gerarResposta(contexto, dto.pergunta());
        return ResponseEntity.ok(resposta);
    }
}
