package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.request.EquipementRequestDTO;
import tn.esprit.autoloc.dto.response.EquipementResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class EquipementMapper {

    public Equipement toEntity(EquipementRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Equipement equipement = new Equipement();
        equipement.setLibelle(dto.getLibelle());
        return equipement;
    }

    public EquipementResponseDTO toResponseDTO(Equipement entity) {
        if (entity == null) {
            return null;
        }
        return EquipementResponseDTO.builder()
                .idEquipement(entity.getIdEquipement())
                .libelle(entity.getLibelle())
                .build();
    }

    public List<EquipementResponseDTO> toResponseDTOList(List<Equipement> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(EquipementRequestDTO dto, Equipement entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setLibelle(dto.getLibelle());
    }
}
