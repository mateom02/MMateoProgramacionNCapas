package com.digis01.MMateoProgramacionNCapas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("usuario")
public class UsuarioDetalles {

    @GetMapping
    public String UsuarioDetalles() {
        return "HolaMundo";
    }

}
