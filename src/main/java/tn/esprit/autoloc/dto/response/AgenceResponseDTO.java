package tn.esprit.autoloc.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgenceResponseDTO {

    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}
