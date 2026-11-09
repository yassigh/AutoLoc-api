package tn.esprit.autoloc.dto.response;

import lombok.*;
import tn.esprit.autoloc.domain.RoleEmploye;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeResponseDTO {

    private Long idEmploye;
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Long idAgence;
    private String nomAgence;
}
