package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.dto.response.ClientResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setNumPermis(dto.getNumPermis());
        client.setDateInscription(dto.getDateInscription());
        return client;
    }

    public ClientResponseDTO toResponseDTO(Client entity) {
        if (entity == null) {
            return null;
        }
        return ClientResponseDTO.builder()
                .idClient(entity.getIdClient())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .numPermis(entity.getNumPermis())
                .dateInscription(entity.getDateInscription())
                .build();
    }

    public List<ClientResponseDTO> toResponseDTOList(List<Client> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(ClientRequestDTO dto, Client entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setNumPermis(dto.getNumPermis());
        entity.setDateInscription(dto.getDateInscription());
    }
}
