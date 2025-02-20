package isis.projet.backend.controller;

import lombok.Data;
import java.io.Serializable;

@Data
public class ParticipationCreaDTO implements Serializable {
    private Integer matricule;
    private Integer codeProjet;
    private String role;
    private float pourcentage;
}