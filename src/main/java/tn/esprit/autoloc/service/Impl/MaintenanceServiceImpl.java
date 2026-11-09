package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.response.MaintenanceResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceResponseDTO create(MaintenanceRequestDTO dto) {
        if (dto.getDateDebut() != null && dto.getDateFin() != null
                && dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));
            maintenance.setVehicule(vehicule);
        }

        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toResponseDTO(saved);
    }

    @Override
    public MaintenanceResponseDTO findById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        return maintenanceMapper.toResponseDTO(maintenance);
    }

    @Override
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceMapper.toResponseDTOList(maintenanceRepository.findAll());
    }

    @Override
    public MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto) {
        Maintenance existante = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));

        if (dto.getDateDebut() != null && dto.getDateFin() != null
                && dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        maintenanceMapper.updateEntityFromDTO(dto, existante);

        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));
            existante.setVehicule(vehicule);
        } else {
            existante.setVehicule(null);
        }

        Maintenance updated = maintenanceRepository.save(existante);
        return maintenanceMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }
}