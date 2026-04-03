package com.group2.crypto.controller;

import com.group2.crypto.model.RsaRequest;
import com.group2.crypto.model.RsaResponse;
import com.group2.crypto.service.RsaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rsa")
public class RsaController {

    private final RsaService rsaService;

    public RsaController(RsaService rsaService) {
        this.rsaService = rsaService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new RsaRequest());
        return "rsa";
    }

    @PostMapping
    public String process(@ModelAttribute("request") RsaRequest request, Model model) {
        RsaResponse response = rsaService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "rsa";
    }
}
