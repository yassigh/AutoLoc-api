package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;
import tn.esprit.autoloc.dto.response.VehiculeResponseDTO;

import java.util.List;

public interface IVehiculeService {

    VehiculeResponseDTO create(VehiculeRequestDTO dto);

    VehiculeResponseDTO findById(Long id);

    List<VehiculeResponseDTO> findAll();

    VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto);

    void deleteById(Long id);

    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);

    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}