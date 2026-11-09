package tn.esprit.autoloc.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipementResponseDTO {

    private Long idEquipement;
    private String libelle;
}
