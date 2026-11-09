package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.EquipementRequestDTO;
import tn.esprit.autoloc.dto.response.EquipementResponseDTO;

import java.util.List;

public interface IEquipementService {

    EquipementResponseDTO create(EquipementRequestDTO dto);

    EquipementResponseDTO findById(Long id);

    List<EquipementResponseDTO> findAll();

    EquipementResponseDTO update(Long id, EquipementRequestDTO dto);

    void deleteById(Long id);
}