package isis.projet.backend.controller;

import isis.projet.backend.entity.Personne;
import isis.projet.backend.dao.PersonneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "*") // Permettre les requêtes depuis le frontend
public class PersonneController {

    private final PersonneRepository personneRepository;

    public PersonneController(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @GetMapping
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }
}
