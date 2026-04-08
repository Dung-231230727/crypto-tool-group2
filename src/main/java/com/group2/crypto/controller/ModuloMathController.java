package com.group2.crypto.controller;

import com.group2.crypto.model.MathModuloRequest;
import com.group2.crypto.model.MathModuloResponse;
import com.group2.crypto.service.ModuloMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/modulo")
public class ModuloMathController {
 
    @Autowired
    private ModuloMathService moduloMathService;
 
    @GetMapping
    public String moduloView(Model model) {
        model.addAttribute("request", new MathModuloRequest());
        return "modulo-math";
    }
 
    @PostMapping
    public String moduloProcess(@ModelAttribute MathModuloRequest request, Model model) {
        MathModuloResponse response = moduloMathService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "modulo-math";
    }
}
