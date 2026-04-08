package com.group2.crypto.controller;

import com.group2.crypto.model.PublicKeyRequest;
import com.group2.crypto.model.PublicKeyResponse;
import com.group2.crypto.service.PublicKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicKeyController {

    @Autowired
    private PublicKeyService publicKeyService;

    @GetMapping("/public-key")
    public String publicKeyView(Model model) {
        model.addAttribute("request", new PublicKeyRequest());
        return "public-key";
    }

    @PostMapping("/public-key")
    public String publicKeyProcess(@ModelAttribute PublicKeyRequest request, Model model) {
        PublicKeyResponse response = publicKeyService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "public-key";
    }
}
