package tn.esprit.autoloc.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContratDTO extends ContratResponseDTO {

    public ContratDTO(ContratResponseDTO dto) {
        super(
                dto.getIdContrat(),
                dto.getDateSignature(),
                dto.getMontantTotal(),
                dto.isValide(),
                dto.getIdReservation(),
                dto.getPaiements()
        );
    }
}