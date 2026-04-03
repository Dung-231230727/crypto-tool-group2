package com.group2.crypto.controller;

import com.group2.crypto.model.DesRequest;
import com.group2.crypto.model.DesResponse;
import com.group2.crypto.service.DesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/des")
public class DesController {

    private final DesService desService;

    public DesController(DesService desService) {
        this.desService = desService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("request", new DesRequest());
        return "des";
    }

    @PostMapping
    public String process(@ModelAttribute("request") DesRequest request, Model model) {
        DesResponse response = desService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "des";
    }
}
