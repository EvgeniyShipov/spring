package ru.otus.spring.domain.jenre;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class JenreController {

    private final LibraryService service;

    @GetMapping("jenre")
    public String getAllJenre(Model model) {
        List<Jenre> jenre = service.getAllJenre();
        model.addAttribute("jenre", jenre);
        return "jenre";
    }

    @GetMapping("jenre/{id}")
    public String getJenre(@PathVariable String id, Model model) {
        Jenre jenre = service.getJenre(id);
        model.addAttribute("jenre", jenre);
        return "jenre";
    }

    @PostMapping("jenre")
    public String createJenre(String name, Model model) {
        Jenre jenre = service.createJenre(name);
        log.warning("Добавлен новый жанр: " + jenre.getType());
        model.addAttribute("jenre", jenre);
        return "jenre";
    }

    @DeleteMapping("jenre/{id}")
    public String deleteJenre(String id, Model model) {
        Jenre jenre = service.deleteJenre(id);
        log.warning("Жанр удален: " + jenre.getType());
        model.addAttribute("jenre", jenre);
        return "jenre";
    }
}
