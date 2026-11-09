package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.response.VehiculeResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setStatut(dto.getStatut());
        return vehicule;
    }

    public VehiculeResponseDTO toResponseDTO(Vehicule entity) {
        if (entity == null) {
            return null;
        }
        return VehiculeResponseDTO.builder()
                .idVehicule(entity.getIdVehicule())
                .immatriculation(entity.getImmatriculation())
                .marque(entity.getMarque())
                .modele(entity.getModele())
                .categorie(entity.getCategorie())
                .tarifJournalier(entity.getTarifJournalier())
                .statut(entity.getStatut())
                .idAgence(entity.getAgence() != null ? entity.getAgence().getIdAgence() : null)
                .nomAgence(entity.getAgence() != null ? entity.getAgence().getNom() : null)
                .build();
    }

    public List<VehiculeResponseDTO> toResponseDTOList(List<Vehicule> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(VehiculeRequestDTO dto, Vehicule entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setImmatriculation(dto.getImmatriculation());
        entity.setMarque(dto.getMarque());
        entity.setModele(dto.getModele());
        entity.setCategorie(dto.getCategorie());
        entity.setTarifJournalier(dto.getTarifJournalier());
        entity.setStatut(dto.getStatut());
    }
}
