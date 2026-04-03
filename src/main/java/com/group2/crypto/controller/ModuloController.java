package com.group2.crypto.controller;

import com.group2.crypto.model.ModuloRequest;
import com.group2.crypto.model.ModuloResponse;
import com.group2.crypto.service.ModuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modulo")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new ModuloRequest());
        return "modulo";
    }

    @PostMapping
    public String process(@ModelAttribute("request") ModuloRequest request, Model model) {
        ModuloResponse response = moduloService.findInverse(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "modulo";
    }
}
