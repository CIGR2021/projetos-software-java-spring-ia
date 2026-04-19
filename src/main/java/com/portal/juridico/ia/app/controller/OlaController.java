package com.portal.juridico.ia.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OlaController {

    @GetMapping("/")
    public String home() {
        return "<h1>IA App Rodando!</h1><p>O servidor está ativo na porta 8081.</p>";
    }
}
