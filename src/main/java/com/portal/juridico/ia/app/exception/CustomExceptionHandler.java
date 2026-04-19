package com.portal.juridico.ia.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        // Você pode até formatar melhor a mensagem aqui
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("Limite excedido: O sistema aceita arquivos de no máximo 10MB.");
    }
}
