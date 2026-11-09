package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.AgenceRequestDTO;
import tn.esprit.autoloc.dto.response.AgenceResponseDTO;

import java.util.List;

public interface IAgenceService {

    AgenceResponseDTO create(AgenceRequestDTO dto);

    AgenceResponseDTO findById(Long id);

    List<AgenceResponseDTO> findAll();

    AgenceResponseDTO update(Long id, AgenceRequestDTO dto);

    void deleteById(Long id);
}
