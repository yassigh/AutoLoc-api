package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.request.AgenceRequestDTO;
import tn.esprit.autoloc.dto.response.AgenceResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDTO create(AgenceRequestDTO dto) {
        if (dto.getNom() == null || dto.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
        Agence agence = agenceMapper.toEntity(dto);
        Agence saved = agenceRepository.save(agence);
        return agenceMapper.toResponseDTO(saved);
    }

    @Override
    public AgenceResponseDTO findById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        return agenceMapper.toResponseDTO(agence);
    }

    @Override
    public List<AgenceResponseDTO> findAll() {
        return agenceMapper.toResponseDTOList(agenceRepository.findAll());
    }

    @Override
    public AgenceResponseDTO update(Long id, AgenceRequestDTO dto) {
        Agence existante = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));

        if (dto.getNom() == null || dto.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }

        agenceMapper.updateEntityFromDTO(dto, existante);
        Agence updated = agenceRepository.save(existante);
        return agenceMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }
}
