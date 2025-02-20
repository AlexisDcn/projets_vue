package isis.projet.backend.controller;

import isis.projet.backend.service.ParticipationProjet;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gestion/participation")
public class ParticipationController {

    private final ParticipationProjet participationService;
    private final ModelMapper mapper;

    public ParticipationController(ParticipationProjet participationService, ModelMapper mapper) {
        this.participationService = participationService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> enregistrerParticipation(
            @RequestParam Integer matricule,
            @RequestParam Integer codeProjet,
            @RequestParam String role,
            @RequestParam float pourcentage) {
        try {
            var participation = participationService.enregistrerParticipation(matricule, codeProjet, role, pourcentage);
            var body = mapper.map(participation, ParticipationDTO.class);
            return ResponseEntity.ok(body);
        } catch (NoSuchElementException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiErrorDTO(e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiErrorDTO("Cette personne participe déjà au projet"));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ApiErrorDTO("Validation failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiErrorDTO("Une erreur est survenue : " + e.getMessage()));
        }
    }
}
