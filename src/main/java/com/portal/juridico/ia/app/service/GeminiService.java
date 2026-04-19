package com.portal.juridico.ia.app.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
* Método MVP: recebe o contexto (documentos)
* e a pergunta do usuário e retorna uma resposta simulada.
*/

@Service
public class GeminiService {
    // Coloque aqui a API KEY.
    private static final String API_KEY = "AIzaSyDpkhj0svADSay6y7asGeYl4aJDhuDQar0";
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";
    private final RestTemplate resTemplate = new RestTemplate();
    
    public String gerarResposta(String contexto, String pergunta) {
        String prompt = """
                        Você é um assistente jurídico.
                        Responda APENAS com base no texto abaixo.
                        Se a resposta não estiver no texto, diga que a informação não consta.
                        
                        TEXTO:
                        %s
                        
                        PERGUNTA:
                        %s
                        """.formatted(contexto, pergunta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Mudança crucial: enviando a chave no Header como o seu curl mostrou
        headers.set("X-goog-api-key", API_KEY);
        
        Map<String, Object> body = Map.of(
            "contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            }
        );
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        
        // Extração simples do texto retornado
        try {
            ResponseEntity<Map> response = resTemplate.postForEntity(GEMINI_URL, request, Map.class);
            
            // Navegando no mapa de resposta do Google
            var bodyResponse = response.getBody();
            var candidates = (java.util.List<?>) bodyResponse.get("candidates");
            var firstCandidate = (Map<?, ?>) candidates.get(0);
            var content = (Map<?, ?>) firstCandidate.get("content");
            var parts = (java.util.List<?>) content.get("parts");
            var firstPart = (Map<?, ?>) parts.get(0);
            
            return firstPart.get("text").toString();
        } catch (Exception e) {
            return "Erro ao processar a IA: " + e.getMessage();
        }
    }
}
