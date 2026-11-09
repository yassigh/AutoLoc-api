package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.request.ContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratResponseDTO;
import tn.esprit.autoloc.dto.response.PaiementResponseDTO;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContratMapper {

    private final PaiementMapper paiementMapper;

    public Contrat toEntity(ContratRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dto.getDateSignature());
        contrat.setMontantTotal(dto.getMontantTotal());
        contrat.setValide(dto.isValide());

        if (dto.getPaiements() != null) {
            Set<Paiement> paiements = dto.getPaiements().stream()
                    .map(pDto -> {
                        Paiement p = paiementMapper.toEntity(pDto);
                        p.setContrat(contrat);
                        return p;
                    })
                    .collect(Collectors.toSet());
            contrat.setPaiements(paiements);
        } else {
            contrat.setPaiements(new HashSet<>());
        }
        return contrat;
    }

    public ContratResponseDTO toResponseDTO(Contrat entity) {
        if (entity == null) {
            return null;
        }
        List<PaiementResponseDTO> paiementsDTO = entity.getPaiements() != null
                ? entity.getPaiements().stream().map(paiementMapper::toResponseDTO).toList()
                : Collections.emptyList();

        return ContratResponseDTO.builder()
                .idContrat(entity.getIdContrat())
                .dateSignature(entity.getDateSignature())
                .montantTotal(entity.getMontantTotal())
                .valide(entity.isValide())
                .idReservation(entity.getReservation() != null ? entity.getReservation().getIdReservation() : null)
                .paiements(paiementsDTO)
                .build();
    }

    public List<ContratResponseDTO> toResponseDTOList(List<Contrat> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(ContratRequestDTO dto, Contrat entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDateSignature(dto.getDateSignature());
        entity.setMontantTotal(dto.getMontantTotal());
        entity.setValide(dto.isValide());
    }
}
