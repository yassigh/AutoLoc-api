package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.dto.response.ClientResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO dto) {
        if (dto.getNom() == null || dto.getNom().trim().isEmpty()
                || dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom et l'email du client sont obligatoires");
        }
        Client client = clientMapper.toEntity(dto);
        Client saved = clientRepository.save(client);
        return clientMapper.toResponseDTO(saved);
    }

    @Override
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientMapper.toResponseDTOList(clientRepository.findAll());
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client existant = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));

        if (dto.getNom() == null || dto.getNom().trim().isEmpty()
                || dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom et l'email du client sont obligatoires");
        }

        clientMapper.updateEntityFromDTO(dto, existant);
        Client updated = clientRepository.save(existant);
        return clientMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }
}