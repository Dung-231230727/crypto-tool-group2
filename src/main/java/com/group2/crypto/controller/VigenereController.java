package com.group2.crypto.controller;

import com.group2.crypto.model.VigenereRequest;
import com.group2.crypto.model.VigenereResponse;
import com.group2.crypto.service.VigenereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vigenere")
public class VigenereController {

    private final VigenereService vigenereService;

    public VigenereController(VigenereService vigenereService) {
        this.vigenereService = vigenereService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new VigenereRequest());
        return "vigenere";
    }

    @PostMapping
    public String process(@ModelAttribute("request") VigenereRequest request, Model model) {
        VigenereResponse response = vigenereService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "vigenere";
    }
}
