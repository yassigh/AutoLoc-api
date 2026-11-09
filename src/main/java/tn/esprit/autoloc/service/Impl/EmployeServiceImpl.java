package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.request.EmployeRequestDTO;
import tn.esprit.autoloc.dto.response.EmployeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeResponseDTO create(EmployeRequestDTO dto) {
        if (dto.getNom() == null || dto.getNom().trim().isEmpty() || dto.getRole() == null) {
            throw new IllegalArgumentException("Le nom et le rôle de l'employé sont obligatoires");
        }

        Employe employe = employeMapper.toEntity(dto);
        if (dto.getIdAgence() != null) {
            Agence agence = agenceRepository.findById(dto.getIdAgence())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getIdAgence()));
            employe.setAgence(agence);
        }

        Employe saved = employeRepository.save(employe);
        return employeMapper.toResponseDTO(saved);
    }

    @Override
    public EmployeResponseDTO findById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
        return employeMapper.toResponseDTO(employe);
    }

    @Override
    public List<EmployeResponseDTO> findAll() {
        return employeMapper.toResponseDTOList(employeRepository.findAll());
    }

    @Override
    public EmployeResponseDTO update(Long id, EmployeRequestDTO dto) {
        Employe existant = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));

        if (dto.getNom() == null || dto.getNom().trim().isEmpty() || dto.getRole() == null) {
            throw new IllegalArgumentException("Le nom et le rôle de l'employé sont obligatoires");
        }

        employeMapper.updateEntityFromDTO(dto, existant);

        if (dto.getIdAgence() != null) {
            Agence agence = agenceRepository.findById(dto.getIdAgence())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getIdAgence()));
            existant.setAgence(agence);
        } else {
            existant.setAgence(null);
        }

        Employe updated = employeRepository.save(existant);
        return employeMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }
}