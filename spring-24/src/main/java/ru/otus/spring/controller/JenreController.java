package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class JenreController {

    private final LibraryService service;

    @GetMapping("jenres")
    public String getAllJenre(Model model) {
        List<Jenre> jenre = service.getAllJenre();
        model.addAttribute("jenres", jenre);
        return "jenres";
    }

    @GetMapping("jenres/{id}")
    public String getJenre(@PathVariable long id, Model model) {
        Jenre jenre = service.getJenre(id);
        model.addAttribute("jenre", jenre);
        return "jenre";
    }

    @GetMapping("jenres/create")
    public String createJenre(Jenre jenre, Model model) {
        return "jenre_new";
    }

    @PostMapping("jenres/create")
    public String createJenre(String type, Model model) {
        Jenre jenre = service.createJenre(type);
        log.warning("Добавлен новый жанр: " + jenre.getType());
        model.addAttribute("jenres", service.getAllJenre());
        return "redirect:/jenres";
    }

    @PostMapping("jenres/update/{id}")
    public String updateJenre(@PathVariable long id, String type, Model model) {
        Jenre jenre = service.getJenre(id);
        jenre.setType(type);
        service.updateJenre(jenre);
        log.info("Жанр изменен: " + jenre.getType());
        model.addAttribute("jenres", service.getAllJenre());
        return "redirect:/jenres";
    }

    @PostMapping("jenres/delete/{id}")
    public String deleteJenre(@PathVariable long id, Model model) {
        Jenre jenre = service.deleteJenre(id);
        log.warning("Жанр удален: " + jenre.getType());
        model.addAttribute("jenres", service.getAllJenre());
        return "redirect:/jenres";
    }
}
