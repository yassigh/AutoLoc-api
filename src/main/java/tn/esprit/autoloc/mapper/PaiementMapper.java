package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.request.PaiementRequestDTO;
import tn.esprit.autoloc.dto.response.PaiementResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class PaiementMapper {

    public Paiement toEntity(PaiementRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.getMontant());
        paiement.setDatePaiement(dto.getDatePaiement());
        paiement.setModePaiement(dto.getModePaiement());
        return paiement;
    }

    public PaiementResponseDTO toResponseDTO(Paiement entity) {
        if (entity == null) {
            return null;
        }
        return PaiementResponseDTO.builder()
                .idPaiement(entity.getIdPaiement())
                .montant(entity.getMontant())
                .datePaiement(entity.getDatePaiement())
                .modePaiement(entity.getModePaiement())
                .idContrat(entity.getContrat() != null ? entity.getContrat().getIdContrat() : null)
                .build();
    }

    public List<PaiementResponseDTO> toResponseDTOList(List<Paiement> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }
}
