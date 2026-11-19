package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;
import tn.esprit.autoloc.dto.response.VehiculeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {
        if (dto.getTarifJournalier() != null && dto.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }

        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        if (dto.getIdAgence() != null) {
            Agence agence = agenceRepository.findById(dto.getIdAgence())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getIdAgence()));
            vehicule.setAgence(agence);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toResponseDTO(saved);
    }

    @Override
    public VehiculeResponseDTO findById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        return vehiculeMapper.toResponseDTO(vehicule);
    }

    @Override
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeMapper.toResponseDTOList(vehiculeRepository.findAll());
    }

    @Override
    public VehiculeResponseDTO update(Long id, VehiculeRequestDTO dto) {
        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));

        if (dto.getTarifJournalier() != null && dto.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }

        vehiculeMapper.updateEntityFromDTO(dto, existant);

        if (dto.getIdAgence() != null) {
            Agence agence = agenceRepository.findById(dto.getIdAgence())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getIdAgence()));
            existant.setAgence(agence);
        } else {
            existant.setAgence(null);
        }

        Vehicule updated = vehiculeRepository.save(existant);
        return vehiculeMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule).get();
        Equipement equipement = equipementRepository.findById(idEquipement).get();
        vehicule.getEquipements().add(equipement);
        return new VehiculeDTO(vehiculeMapper.toResponseDTO(vehiculeRepository.save(vehicule)));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation).get();
        Maintenance maintenance = maintenanceRepository.findFirstByDescription(description).get();
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
        return new VehiculeDTO(vehiculeMapper.toResponseDTO(vehicule));
    }
}