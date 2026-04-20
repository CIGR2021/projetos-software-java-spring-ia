package com.portal.juridico.ia.app.service;

import com.portal.juridico.ia.app.repository.DocumentoRepository;
import com.portal.juridico.ia.app.model.Documento;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import org.apache.pdfbox.Loader; // Para o erro do Loader
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.nio.charset.StandardCharsets; // Para o erro do StandardCharsets
        
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class DocumentoService {
    private final DocumentoRepository repository;
    public void salvar(MultipartFile file) throws Exception {
        String texto;
        String filename = file.getOriginalFilename();

        if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
            // PDFBox 3.x utiliza o Loader
            try (PDDocument pdf = Loader.loadPDF(file.getBytes())) {
                PDFTextStripper stripper = new PDFTextStripper();
                texto = stripper.getText(pdf);
            }
        } else {
            // Leitura de arquivos de texto puro com encoding correto
            texto = new String(file.getBytes(), StandardCharsets.UTF_8);
        }
        
        // AQUI você inclui a sanitização que conversamos para segurança extra
        String textoSanitizado = sanitizeText(texto);

        // Validação simples: não salvar se o documento estiver vazio
        if (textoSanitizado.trim().isEmpty()) {
            throw new Exception("O arquivo enviado está vazio ou não possui texto extraível.");
        }

        Documento doc = new Documento();
        doc.setNome(filename);
        doc.setConteudo(textoSanitizado); // Salva o texto limpo

        repository.save(doc);
    }
    
    public String buscarTodoConteudo() {
        List<Documento> documentos = repository.findAll();
        if (documentos.isEmpty()) return "Nenhum documento encontrado no banco.";

        return documentos.stream()
                .map(doc -> "NOME DO ARQUIVO: " + doc.getNome() + "\nCONTEÚDO:\n" + doc.getConteudo())
                .collect(Collectors.joining("\n\n---\n\n")); // Separa claramente um do outro
    }
    
    public long contarDocumentos() {
        return repository.count();
    }

    public List<String> listarNomesDocumentos() {
        return repository.findAll()
                         .stream()
                         .map(Documento::getNome)
                         .collect(Collectors.toList());
    }
    
    public String extrairTexto(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             PDDocument document = Loader.loadPDF(is.readAllBytes())) { // Para PDFBox 3.x

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);

        } catch (Exception e) {
            return "Erro ao extrair texto do PDF: " + e.getMessage();
        }
    }
    
    private String sanitizeText(String input) {
        if (input == null) return "";
        // Remove tags HTML/XML e caracteres de controle (segurança básica)
        String clean = input.replaceAll("<[^>]*>", "");
        clean = clean.replaceAll("[\\x00-\\x1F\\x7F]", "");

        // Limite de caracteres para não estourar o contexto da IA (ex: 20k chars)
        return clean.length() > 20000 ? clean.substring(0, 20000) : clean;
    }
}
