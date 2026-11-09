package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.request.EquipementRequestDTO;
import tn.esprit.autoloc.dto.response.EquipementResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementResponseDTO create(EquipementRequestDTO dto) {
        if (dto.getLibelle() == null || dto.getLibelle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
        Equipement equipement = equipementMapper.toEntity(dto);
        Equipement saved = equipementRepository.save(equipement);
        return equipementMapper.toResponseDTO(saved);
    }

    @Override
    public EquipementResponseDTO findById(Long id) {
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        return equipementMapper.toResponseDTO(equipement);
    }

    @Override
    public List<EquipementResponseDTO> findAll() {
        return equipementMapper.toResponseDTOList(equipementRepository.findAll());
    }

    @Override
    public EquipementResponseDTO update(Long id, EquipementRequestDTO dto) {
        Equipement existant = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));

        if (dto.getLibelle() == null || dto.getLibelle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }

        equipementMapper.updateEntityFromDTO(dto, existant);
        Equipement updated = equipementRepository.save(existant);
        return equipementMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }
}