package com.group2.crypto.controller;

import com.group2.crypto.model.AesRequest;
import com.group2.crypto.model.AesResponse;
import com.group2.crypto.service.AesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/aes")
public class AesController {

    private final AesService aesService;

    public AesController(AesService aesService) {
        this.aesService = aesService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new AesRequest());
        return "aes";
    }

    @PostMapping
    public String process(@ModelAttribute("request") AesRequest request, Model model) {
        AesResponse response = aesService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "aes";
    }
}
