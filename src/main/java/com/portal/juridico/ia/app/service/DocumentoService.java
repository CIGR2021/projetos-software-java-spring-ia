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

        // Validação simples: não salvar se o documento estiver vazio
        if (texto.trim().isEmpty()) {
            throw new Exception("O arquivo enviado está vazio ou não possui texto extraível.");
        }

        Documento doc = new Documento();
        doc.setNome(filename);
        doc.setConteudo(texto);

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
}
