package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.request.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.response.MaintenanceResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class MaintenanceMapper {

    public Maintenance toEntity(MaintenanceRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(dto.getDateDebut());
        maintenance.setDateFin(dto.getDateFin());
        maintenance.setDescription(dto.getDescription());
        return maintenance;
    }

    public MaintenanceResponseDTO toResponseDTO(Maintenance entity) {
        if (entity == null) {
            return null;
        }
        return MaintenanceResponseDTO.builder()
                .idMaintenance(entity.getIdMaintenance())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .description(entity.getDescription())
                .idVehicule(entity.getVehicule() != null ? entity.getVehicule().getIdVehicule() : null)
                .immatriculationVehicule(entity.getVehicule() != null ? entity.getVehicule().getImmatriculation() : null)
                .build();
    }

    public List<MaintenanceResponseDTO> toResponseDTOList(List<Maintenance> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(MaintenanceRequestDTO dto, Maintenance entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setDescription(dto.getDescription());
    }
}
