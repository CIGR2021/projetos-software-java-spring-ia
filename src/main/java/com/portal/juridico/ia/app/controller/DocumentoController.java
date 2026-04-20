package com.portal.juridico.ia.app.controller;
import com.portal.juridico.ia.app.service.DocumentoService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import org.apache.tika.Tika;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoController {
    private final DocumentoService service;
    private final Tika tika = new Tika();
    
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile[] files) {
        // Note o uso de [] para indicar que é um Array
        try {
            int contador = 0;
            for (MultipartFile file : files) {
                // Validação de Segurança 1: Conteúdo real
                if (!isFileSafe(file)) {
                    return ResponseEntity.badRequest().body("Arquivo inválido: " + file.getOriginalFilename());
                }
                
                // Aqui você chama a sua lógica de salvar/processar para CADA arquivo
                service.salvar(file);
                contador++;
            }
            return ResponseEntity.ok("Sucesso! " + contador + " arquivos foram processados.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar: " + e.getMessage());
        }
    }
    
    private boolean isFileSafe(MultipartFile file) {
        try {
            // O Tika verifica os "magic bytes" do arquivo, não apenas a extensão
            String detectedType = tika.detect(file.getInputStream());
            return detectedType.equals("application/pdf") || detectedType.equals("text/plain");
        } catch (Exception e) {
            return false;
        }
    }
    
    @GetMapping("/status-banco")
    public ResponseEntity<Map<String, Object>> verificarStatus() {
        long total = service.contarDocumentos();
        List<String> nomes = service.listarNomesDocumentos();

        Map<String, Object> status = Map.of(
            "total_documentos", total,
            "arquivos_na_base", nomes,
            "banco_vazio", total == 0
        );

        return ResponseEntity.ok(status);
    }
}
