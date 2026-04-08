package com.group2.crypto.controller;

import com.group2.crypto.model.ClassicalCipherRequest;
import com.group2.crypto.model.ClassicalCipherResponse;
import com.group2.crypto.service.ClassicalCipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClassicalCipherController {

    @Autowired
    private ClassicalCipherService classicalCipherService;

    @GetMapping("/classical")
    public String classicalView(Model model) {
        model.addAttribute("request", new ClassicalCipherRequest());
        return "classical";
    }

    @PostMapping("/classical")
    public String classicalProcess(@ModelAttribute ClassicalCipherRequest request, Model model) {
        ClassicalCipherResponse response = classicalCipherService.process(request);
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        return "classical";
    }
}
