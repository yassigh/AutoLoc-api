package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.response.MaintenanceResponseDTO;

import java.util.List;

public interface IMaintenanceService {

    MaintenanceResponseDTO create(MaintenanceRequestDTO dto);

    MaintenanceResponseDTO findById(Long id);

    List<MaintenanceResponseDTO> findAll();

    MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO dto);

    void deleteById(Long id);
}