package isis.projet.backend.controller;

import isis.projet.backend.entity.Projet;
import isis.projet.backend.dao.ProjetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*") // Permettre les requêtes depuis le frontend
public class ProjetController {

    private final ProjetRepository projetRepository;

    public ProjetController(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @GetMapping
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }
}
