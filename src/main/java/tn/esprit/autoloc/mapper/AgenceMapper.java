package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.request.AgenceRequestDTO;
import tn.esprit.autoloc.dto.response.AgenceResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class AgenceMapper {

    public Agence toEntity(AgenceRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Agence agence = new Agence();
        agence.setNom(dto.getNom());
        agence.setVille(dto.getVille());
        agence.setAdresse(dto.getAdresse());
        agence.setTelephone(dto.getTelephone());
        return agence;
    }

    public AgenceResponseDTO toResponseDTO(Agence entity) {
        if (entity == null) {
            return null;
        }
        return AgenceResponseDTO.builder()
                .idAgence(entity.getIdAgence())
                .nom(entity.getNom())
                .ville(entity.getVille())
                .adresse(entity.getAdresse())
                .telephone(entity.getTelephone())
                .build();
    }

    public List<AgenceResponseDTO> toResponseDTOList(List<Agence> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(AgenceRequestDTO dto, Agence entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNom(dto.getNom());
        entity.setVille(dto.getVille());
        entity.setAdresse(dto.getAdresse());
        entity.setTelephone(dto.getTelephone());
    }
}
