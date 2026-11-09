package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.request.EmployeRequestDTO;
import tn.esprit.autoloc.dto.response.EmployeResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class EmployeMapper {

    public Employe toEntity(EmployeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Employe employe = new Employe();
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setRole(dto.getRole());
        return employe;
    }

    public EmployeResponseDTO toResponseDTO(Employe entity) {
        if (entity == null) {
            return null;
        }
        return EmployeResponseDTO.builder()
                .idEmploye(entity.getIdEmploye())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .role(entity.getRole())
                .idAgence(entity.getAgence() != null ? entity.getAgence().getIdAgence() : null)
                .nomAgence(entity.getAgence() != null ? entity.getAgence().getNom() : null)
                .build();
    }

    public List<EmployeResponseDTO> toResponseDTOList(List<Employe> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(EmployeRequestDTO dto, Employe entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setRole(dto.getRole());
    }
}
