package tn.esprit.autoloc.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehiculeDTO extends VehiculeResponseDTO {

    public VehiculeDTO(VehiculeResponseDTO dto) {
        super(
                dto.getIdVehicule(),
                dto.getImmatriculation(),
                dto.getMarque(),
                dto.getModele(),
                dto.getCategorie(),
                dto.getTarifJournalier(),
                dto.getStatut(),
                dto.getIdAgence(),
                dto.getNomAgence()
        );
    }
}